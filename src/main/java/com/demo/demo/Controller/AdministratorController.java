package com.demo.demo.Controller;

import com.demo.demo.Model.Roles;
import com.demo.demo.Repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/admin")
public class AdministratorController{

    @Autowired
    RolesRepository rolesRepository;

    //Create
    @PostMapping("/createRole")
    public String createRole(@Valid @RequestBody Roles roles) {
        boolean userExist = rolesRepository.roleExists(roles.getRoleId());
        if(userExist)
        {
            return "Role Already Exist";
        }else {
            rolesRepository.save(roles);
            return "Role successfully saved";
        }
    }

    //GetAll
    @GetMapping("/roles")
    public List<Roles> getAllRoles() {
        return rolesRepository.findAll();
    }

    //Read user by role id
    @GetMapping("/roles/{roleId}") //getOne user by email
    public Roles getByRoleId(@PathVariable Long roleId){
        return rolesRepository.findById(roleId).get();
    }

    //update role
    @PutMapping("updateRole/{roleId}")
    public String updateRole(@PathVariable Long roleId, @Valid @RequestBody Roles roles) {
        Roles updatedRole = rolesRepository.findById(roleId).get();
        try {
            updatedRole.setRoleId(roles.getRoleId());
            updatedRole.setRoleName(roles.getRoleName());
            rolesRepository.save(updatedRole);
            return "Role successfully updated...";

        } catch (Exception e) {
            return ("This role does not already exists!");
        }
    }

    //Delete user by role id
    @DeleteMapping("/deleteRole/{roleId}") //delete (find role by role id)
    public String deleteRoleById(@PathVariable Long roleId) {
        Roles deleteRole = rolesRepository.findById(roleId).get();
        String msg;

        if (deleteRole != null) {
            rolesRepository.delete(deleteRole);
            msg = "Role has been successfully deleted";
        } else {
            msg = "Role does not exist!";
        }

        return msg;
    }
}
