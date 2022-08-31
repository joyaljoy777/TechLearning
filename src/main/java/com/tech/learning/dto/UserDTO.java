package com.tech.learning.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserDTO {

    @NotEmpty
    private String userName;
    @NotEmpty
    private String password;
    @NotEmpty
    private String email;
    @NotEmpty
    private String firstName;
    private String lastName;
    private int statusCode;
    private String status;
}
