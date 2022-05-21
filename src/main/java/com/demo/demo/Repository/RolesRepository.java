package com.demo.demo.Repository;

import com.demo.demo.Model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RolesRepository extends JpaRepository<Roles, Long> {

    @Query("select case when (count (r)>0) then true else false end from Roles r where r.roleId=?1")
    boolean roleExists(Long roleId);
    @Query("Select r.roleName from Roles r where r.roleId=?1")
    String getRoleName(Long roleId);
}

