package com.epam.esm.entity;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//@Relation(itemRelation = "user", collectionRelation = "users")
public class UserDto {
        //extends RepresentationModel<UserDto> {

    private long id;
    private String name;
    private String email;
    private String password;
    private Role role;

}