package com.avs.adpass.repositories;

import com.avs.adpass.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {


    User findByUsername(String username);


    User findByEmail(String email);
}
