package com.avs.adpass.services;

import com.avs.adpass.domain.User;

public interface UserService  extends AvsService<User>{
    User findByUserName(String userName);
    User findByEmail(String email);
}
