package com.avs.adpass.services;

import com.avs.adpass.commands.RegistrationForm;
import com.avs.adpass.converters.RegisterUserToUser;
import com.avs.adpass.domain.Partner;
import com.avs.adpass.domain.User;
import com.avs.adpass.repositories.PartnerRepository;
import com.avs.adpass.repositories.UserRepository;
import com.avs.adpass.services.security.EncryptionService;
import com.avs.adpass.services.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private EncryptionService encryptionService;
    private Converter<User, UserDetails> userUserDetailsConverter;

    private RegisterUserToUser registerUserToUser;


    @Autowired
    public void setRegisterUserToUser(RegisterUserToUser registerUserToUser) {
        this.registerUserToUser = registerUserToUser;
    }

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Autowired
    public void setUserUserDetailsConverter(Converter<User, UserDetails> userUserDetailsConverter) {
        this.userUserDetailsConverter = userUserDetailsConverter;
    }


    @Autowired
    public void setEncryptionService(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }


    public List<User> listAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add); //fun with Java 8
        return users;
    }


    public User getById(Long id) {
        return userRepository.findById(id).get();
    }


    public User saveOrUpdate(User domainObject) {

        if (domainObject.getPassword() != null) {
            domainObject.setEncryptedPassword(encryptionService.encryptString(domainObject.getPassword()));
        }
        return userRepository.save(domainObject);
    }

    @Override
    public User registerUser(Partner partner, RegistrationForm registrationForm) {
        User user = registerUserToUser.convert(registrationForm);
        partner.addUserToPartner(user);
       User savedUser= userRepository.save(user);
        return savedUser;
    }


    public UserDetailsImpl updateUserAccount(User domainObject) {

        User user = userRepository.save(domainObject);

        return (UserDetailsImpl) userUserDetailsConverter.convert(user);

    }


    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }


    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
