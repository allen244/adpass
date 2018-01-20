package com.avs.adpass.services.security;

import com.avs.adpass.domain.security.Role;
import com.avs.adpass.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {


    private RoleRepository roleRepsoitory;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepsoitory = roleRepository;
    }


    @Override
    public Role findById(Long id) {
        Optional<Role> role = roleRepsoitory.findById(id);
        if (!role.isPresent()) {
            throw new RuntimeException("Role" +
                    " Not Found!");

        }


        return role.get();
    }

    @Override
    public void deleteById(Long id) {
        roleRepsoitory.deleteById(id);
    }

    @Override
    public List<Role> getAll() {
        List<Role> roles = new ArrayList<>();
        roleRepsoitory.findAll().forEach(roles::add); //fun with Java 8
        return roles;
    }

    @Override
    public Role saveContent(Role role) {
        return roleRepsoitory.save(role);
    }

    @Override
    public Role findByRole(String role) {
        Role r = roleRepsoitory.findByRole(role);
        return r;
    }
}
