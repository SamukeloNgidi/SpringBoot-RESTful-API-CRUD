package com.demo.demo.Controller;

import com.demo.demo.Model.Roles;
import com.demo.demo.Repository.DataCapturerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/data")
public class DataCapturerController {
    @Autowired
    DataCapturerRepo dataCapturerRepo;

    @PostMapping("/createRole")
    public String createRole(@Valid @RequestBody Roles roles) {
        //return rolesRepository.save(roles);
        boolean userExist = dataCapturerRepo.roleExists(roles.getRoleId());
        if(userExist)
        {
            return "Role Already Exist";
        }else {
            dataCapturerRepo.save(roles);
            return "Role successfully saved";
        }
    }

    //Read GetAll
    @GetMapping("/roles")
    public List<Roles> getAllRoles() {
        return dataCapturerRepo.findAll();
    }

    //Read user by role id
    @GetMapping("/roles/{roleId}") //getOne user by email
    public Roles getByRoleId(@PathVariable Long roleId){
        return dataCapturerRepo.findById(roleId).get();

    }
}
