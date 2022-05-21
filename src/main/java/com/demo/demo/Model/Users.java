package com.demo.demo.Model;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity //default entity name is the class name if not specified
@Getter //create getter methods
@Setter //create setter methods
@Table(name= "users")//,
        //uniqueConstraints = { @UniqueConstraint(name= "emailAddress_unique", columnNames = "email_address")}
        //) //db table name & unique constraint on email column

public class Users implements Serializable { //Serializable interface store a copy of the object, send them to another process which runs on the same system or over the network

    //@SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    //@GeneratedValue(strategy = SEQUENCE, generator = "user_seq")

    @Id //primary key / unique value
    @Column(name= "userid", insertable = false, nullable = false) //database column rename
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autogenerate pk values, identity works with sequences
    private long id;

    @Email
    @NotEmpty(message = "Email cannot be empty!")
    @Column(name= "email_address", nullable = false/*, unique = true*/)
    private String emailAddress;

    @NotNull(message = "First Name cannot be empty!")
    @Size(min = 2, message = "First name must have at least 2 characters!")
    @Column(name= "first_name", nullable = false)
    private String firstName;

    @NotNull(message = "Last Name cannot be empty!")
    @Size(min = 2, max = 50, message = "Last name must have at least 2 characters & a max of 50")
    @Column(name= "last_name", nullable = false)
    private String lastName;

    //@ValidPassword
    @NotNull(message = "Last Name cannot be empty!")
    @Size(min = 6, message = "Password must have at least 6 characters!")
    private String password;

    @Column(name = "role_id")
    private Long roleId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id", insertable = false,updatable = false)
    private Roles roles;

    public Users(){ //empty constructor (gives an instance of the object)
    }

    public Users(String emailAddress, String firstName, String lastName, String password, Long roleId, Roles roles) {
        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.roleId = roleId;
        this.roles = roles;
    }

    @Override
    public String toString() { //displays the content of the Users object
        return "Users{" +
                "id=" + id +
                ", emailAddress='" + emailAddress + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
