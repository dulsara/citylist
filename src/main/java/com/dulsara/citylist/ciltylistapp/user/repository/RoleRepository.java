package com.dulsara.citylist.ciltylistapp.user.repository;

import com.dulsara.citylist.ciltylistapp.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
