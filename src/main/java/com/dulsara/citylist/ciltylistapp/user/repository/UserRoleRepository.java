package com.dulsara.citylist.ciltylistapp.user.repository;

import com.dulsara.citylist.ciltylistapp.user.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

}
