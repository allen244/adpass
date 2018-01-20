package com.avs.adpass.services;

import com.avs.adpass.commands.RegistrationForm;
import com.avs.adpass.domain.Partner;
import com.avs.adpass.domain.User;
import com.avs.adpass.services.security.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService  extends AvsService<User>{
    User findByUserName(String userName);
    User findByEmail(String email);
    UserDetailsImpl updateUserAccount(User domainObject);
    User saveOrUpdate(User domainObject);
    User registerUser(Partner partner, RegistrationForm registrationForm);
}
