package com.hov.project_scope.model;


import com.hov.project_scope.model.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String surname;

    private String email;

    private String password;

    private String profilePic;

    @Enumerated( value = EnumType.STRING)
    private UserType userType;


}
