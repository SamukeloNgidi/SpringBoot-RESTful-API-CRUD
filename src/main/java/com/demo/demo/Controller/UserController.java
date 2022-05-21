package com.demo.demo.Controller;

import com.demo.demo.Model.Users;
import com.demo.demo.Repository.UserRepository;
import com.demo.demo.Service.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController //defines the RESTful webservices. It servers JASON, XML ...
@RequestMapping("api") //specifies the route url
public class UserController {

    @Autowired //handles all dependency injection (using methods of one class in another without creating an instance of that object)
    private UserRepository userRepo; //inject the repository

    @Autowired
    private EmailSender emailSender;

    @GetMapping(value = "/users") //getAll users
    //@CrossOrigin(origins = "http://localhost:4200", exposedHeaders="Access-Control-Allow-Origin")
    public List<Users> getUsers(){
        //throw new ApiRequestException("Oops cannot get all users with custom exception");
        return userRepo.findAll();
    }

    @GetMapping("/users/{id}") //getOne user by id
    public Users getById(@PathVariable long id){
        return userRepo.findById(id).get();
    }

    @GetMapping("/usersByEmail/{emailAddress}") //getOne user by email
    public Users getByEmail(@PathVariable String emailAddress){
        return userRepo.findByEmailAddress(emailAddress);
    }

    @PostMapping(value = "/save") //whenever we save to db it should be a post
    public String saveUser(@Valid @RequestBody Users user) throws MessagingException, IOException { //@RequestBody what gets sent through the body is an object

        boolean userExists = userRepo.userEmailAddressExists(user.getEmailAddress());
        if(userExists){
            return "User with email address already exists!";
        }else{
            userRepo.save(user);
            emailSender.sendmail(user.getEmailAddress(), user.getPassword());
            return "User successfully saved...";
        }
    }

    @PutMapping(value = "update/{emailAddress}")
    public String updateUser(@PathVariable String emailAddress, @RequestBody Users user){ //@RequestBody annotation tells Spring to deserialize an incoming request body into an object passed as a parameter to the handler method.

        try{
            Users updatedUser = userRepo.findByEmailAddress(emailAddress); //find the user
            updatedUser.setEmailAddress(user.getEmailAddress());//set user parameters
            updatedUser.setFirstName(user.getFirstName());
            updatedUser.setLastName(user.getLastName());
            updatedUser.setPassword(user.getPassword());
            userRepo.save(updatedUser);
            return "User has been updated...";

        }catch (Exception e){
            return "User with email address already exists!";
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable long id) { //@PathVariable indicates that a method parameter should be bound to a URI template variable
        Users deleteUser = userRepo.findById(id).get();
        userRepo.delete(deleteUser);
        return "Deleted user with the id: " + id;
    }

    @DeleteMapping(value = "/deleteByEmail/{emailAddress}") //delete (find user by email)
    public String deleteUserByEmail(@PathVariable String emailAddress) { //@PathVariable indicates that a method parameter should be bound to a URI template variable
        Users deleteUser = userRepo.findByEmailAddress(emailAddress);
        String msg;

        if(deleteUser != null){
            userRepo.delete(deleteUser);
            msg = "User has been successfully deleted!";
        }else{
            msg = "User does not exist!";
        }
        return msg;
    }
}
