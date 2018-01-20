package com.avs.adpass.services.security;

import com.avs.adpass.domain.security.Role;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RoleService {


    Role findById(Long id);

    void deleteById(Long Id);

    List<Role> getAll();

    Role saveContent(Role role);

    Role findByRole(String role);
}
