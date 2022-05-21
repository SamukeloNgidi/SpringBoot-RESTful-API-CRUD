package com.demo.demo.Controller;

import com.demo.demo.Model.Users;
import com.demo.demo.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//represents the user controller
@RestController
@RequestMapping("/user")
public class UserResource {
    private final UserService userService;

    public UserResource(UserService userService) { //inject service into the constructor so that we can autowire the service inside of a class
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Users>> getAllUsers(){ //RequestEntity specifies that a method will return http response
        List<Users> users = userService.findAllUsers(); //call the service and return list of users
        return new ResponseEntity<>(users, HttpStatus.OK); //return users and http status
    }

    @GetMapping("/find/{emailAddress}")
    public Users getByEmail(@PathVariable String emailAddress){
        return userService.findByEmailAddress(emailAddress);
    }

    @PostMapping(value = "/add")
    public String saveUser(@Valid @RequestBody Users user){

        boolean userExists = userService.userEmailExists(user.getEmailAddress());
        if(userExists){ //true
            return "User with email address already exists!";
        }else{
            userService.addUser(user);
            return "User successfully saved...";
        }
    }

    @PutMapping(value = "/update/{emailAddress}")
    public String updateUser(@RequestBody Users user, @PathVariable String emailAddress){
        try{
            userService.updateUser(user, emailAddress);
            return "User has been updated...";
        }catch (Exception e){
            return "User with email address already exists!";
        }
    }

    @DeleteMapping(value = "/delete/{emailAddress}")
    public String deleteUser(@PathVariable String emailAddress) {
        Users deleteUser = userService.findByEmailAddress(emailAddress);
        String msg;

        if(deleteUser != null){
            userService.deleteUser(deleteUser.getEmailAddress());
            msg = "User has been successfully deleted!";
        }else{
            msg = "User does not exist!";
        }
        return msg;
    }

    /*@GetMapping("/find/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable("id") Long id){ //@PathVariable - parameter id should match id in @GetMapping
        Users oneUser = userService.findUserById(id);
        return new ResponseEntity<>(oneUser, HttpStatus.OK);
    }*/

    /*@PostMapping("/add")
    public ResponseEntity<Users> addUser(@RequestBody Users user){
        Users newUser = userService.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED); //CREATED means we created something on the server
    }*/

    /*@PutMapping("/update")
    public ResponseEntity<Users> updateUser(@RequestBody Users user){
        Users updateUser = userService.updateUser(user);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }*/

    /*
    @DeleteMapping
    @PutMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){ //<?> means that a method does not return anything
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }*/
}
