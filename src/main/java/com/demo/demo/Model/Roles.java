package com.demo.demo.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name="ROLES")
@NoArgsConstructor
public class Roles implements Serializable {
    @Id
    @Column(name = "ROLE_ID", insertable = false, nullable = false)
    private Long roleId;

    @Column(name = "ROLENAME", nullable = false)
    private String roleName;

    public Roles(String roleName) {
        this.roleName = roleName;
    }
}
