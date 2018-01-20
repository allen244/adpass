package com.avs.adpass.converters;

import com.avs.adpass.commands.RegistrationForm;
import com.avs.adpass.domain.User;
import com.avs.adpass.domain.UserAccount;
import com.avs.adpass.services.security.EncryptionService;
import com.avs.adpass.services.security.RoleService;
import com.avs.adpass.services.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

@Component
public class RegisterUserToUser implements Converter<RegistrationForm, User> {


    private EncryptionService encryptionService;
    private RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setEncryptionService(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }


    @Override
    public User convert(RegistrationForm registrationForm) {
        User user = new User();

        if (registrationForm != null) {
            user.setUsername(registrationForm.getUsername());
            user.setPassword(registrationForm.getPassword());
            user.setEmail(registrationForm.getEmail());
            user.setEncryptedPassword(encryptionService.encryptString(user.getPassword()));
            user.setEnabled(true);

            UserAccount userAccount1 = new UserAccount();
            userAccount1.setAccountName("Yo account");
            userAccount1.setAccountBalance(new BigDecimal(10.0));
            userAccount1.setUser(user);
            user.setUserAccount(userAccount1);

            user.addRole(roleService.findByRole("USER"));

            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

            user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getRole()));
            });

            registrationForm.setAuthorities(authorities);
        }
        return user;
    }


}