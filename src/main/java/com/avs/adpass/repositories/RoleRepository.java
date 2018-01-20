package com.avs.adpass.repositories;

import com.avs.adpass.domain.security.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long>  {

    Role findByRole(String role);
}
