package com.demo.demo.Service;

import com.demo.demo.Exception.UserNotFoundException;
import com.demo.demo.Model.Users;
import com.demo.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository; //inject user repository dependency

    @Autowired
    public UserService(UserRepository usersRepository) { //hover over 'usersRepository' and click add constructor
        this.userRepository = usersRepository;
    }

    public Users addUser(Users user){ return userRepository.save(user); }

    public List<Users> findAllUsers(){
        return userRepository.findAll();
    }

    public Users findByEmailAddress(@PathVariable String emailAddress){ return userRepository.findByEmailAddress(emailAddress); }

    //public Users updateUser(Users user) { return userRepository.save(user); }

    //public void deleteUser(Long id){ userRepository.deleteUserById(id); }

    public boolean userEmailExists(String emailAddress){ return userRepository.userEmailAddressExists(emailAddress); }

    public Users findUserById(Long id){
        return userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User by id " + id + "was not found.")); //return user or throw exception if no user found
    }

    public Users updateUser(Users user, @PathVariable String emailAddress){

        Users updatedUser = userRepository.findByEmailAddress(emailAddress); //find the user
        updatedUser.setEmailAddress(user.getEmailAddress());//set user parameters
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setPassword(user.getPassword());
        return userRepository.save(updatedUser);
    }

    public void deleteUser(@PathVariable String emailAddress) {
        Users deleteUser = userRepository.findByEmailAddress(emailAddress);
        userRepository.delete(deleteUser);
    }
}
