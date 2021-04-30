package com.epam.esm.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    private long id;
    private String name;
    private String email;
    private String password;
    private Role role;

}