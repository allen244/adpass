package com.avs.adpass.bootstrap;

import com.avs.adpass.domain.*;
import com.avs.adpass.domain.security.Role;
import com.avs.adpass.services.PartnerService;
import com.avs.adpass.services.UserService;
import com.avs.adpass.services.security.EncryptionService;
import com.avs.adpass.services.security.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class AdPassBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private UserService userService;


    private PartnerService partnerService;
    private RoleService roleService;


    @Autowired
    public void setPartnerService(PartnerService partnerService) {
        this.partnerService = partnerService;
    }


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setEncryptionService(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadUsersAndParnter();
        loadRoles();
        assignUsersToDefaultRole();
        assignUsersToAdminRole();


    }

    private void loadRoles() {
        Role role = new Role();
        role.setRole("USER");
        roleService.saveContent(role);

        Role adminRole = new Role();
        adminRole.setRole("ADMIN");
        roleService.saveContent(adminRole);
    }

    private EncryptionService encryptionService;


    public void loadUsersAndParnter() {

        Address address1 = new Address();
        address1.setAddressLine1("test");
        address1.setZipCode("11702");
        address1.setState("ny");

        Address address2 = new Address();
        address2.setAddressLine1("ptest");
        address2.setZipCode("11702");
        address2.setState("ny");


        Address address3 = new Address();
        address3.setAddressLine1("ttt");
        address3.setZipCode("11702");
        address3.setState("ny");


        User user1 = new User();
        user1.setUsername("al");
        user1.setPassword("welcome");
        user1.setEmail("alsmaldone@yahoo.com");
        user1.setEncryptedPassword(encryptionService.encryptString(user1.getPassword()));
        user1.addAddressToUser(address1);
        address1.setUser(user1);


        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setUrl("google.com");


        UserAccount userAccount1 = new UserAccount();
        userAccount1.setAccountName("Al account");
        userAccount1.setAccountBalance(new BigDecimal(10.0));
        userAccount1.addToAccountDetails(accountDetails);
        accountDetails.setUserAccount(userAccount1);

        userAccount1.setUser(user1);
        user1.setUserAccount(userAccount1);


//        User user2 = new User();
//        user2.setUsername("test");
//        user2.setPassword("test");
//        user2.setEmail("test@yahoo.com");
//        user2.setEncryptedPassword(encryptionService.encryptString(user2.getPassword()));
//        user2.addAddressToUser(address3);


        Partner partner = new Partner();
        partner.setPartnerName("NYDN");
        partner.setEmail("alsmaldone@yahoo.com");
        partner.setPhoneNumber("305.333.0101");
        address2.setPartner(partner);
        user1.setPartner(partner);
      //  user2.setPartner(partner);
        partner.addAddressToPartner(address2);

        partner.addUserToPartner(user1);
      //  partner.addUserToPartner(user2);


        partnerService.savePartner(partner);


    }


    private void assignUsersToAdminRole() {
        List<Role> roles = (List<Role>) roleService.getAll();
        List<User> users = (List<User>) userService.listAll();

        roles.forEach(role -> {
            if (role.getRole().equalsIgnoreCase("ADMIN")) {
                users.forEach(user -> {
                    if (user.getUsername().equals("al")) {
                        user.addRole(role);
                        userService.saveOrUpdate(user);
                    }
                });
            }
        });
    }


    private void assignUsersToDefaultRole() {
        List<Role> roles = (List<Role>) roleService.getAll();
        List<User> users = (List<User>) userService.listAll();

        roles.forEach(role -> {
            if (role.getRole().equalsIgnoreCase("USER")) {
                users.forEach(user -> {
                    user.addRole(role);
                    userService.saveOrUpdate(user);
                });
            }

//            if(role.getRole().equalsIgnoreCase("ADMIN")){
//                users.forEach(user -> {
//                    if(user.getUsername().equals("fglenanne")){
//                        user.addRole(role);
//                        userService.saveOrUpdate(user);
//                    }
//                });
//            }
        });
    }


}
