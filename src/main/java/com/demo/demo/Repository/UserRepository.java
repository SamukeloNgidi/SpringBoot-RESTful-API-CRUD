package com.demo.demo.Repository;

import com.demo.demo.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository //data access layer
public interface UserRepository extends JpaRepository<Users, Long> { //pass the user class & it's id
    Users findByEmailAddress(String emailAddress);
    Optional<Users> findUserById(Long id); //Optional is used in case it doesn't find the user
    void deleteUserById(Long id);
    @Query("select case when (count (u)>0) then true else false end from Users u where u.emailAddress=?1")
    boolean userEmailAddressExists(String userEmailAddress);
    @Query("SELECT u FROM Users u WHERE  u.emailAddress=?1")
    Users getUserByUsername(String username);
    @Query("select case when (count (u)>0) then true else false end from Users u where u.password=?1 AND u.emailAddress=?2")
    boolean passwordIsCorrect(String password, String username);
}
