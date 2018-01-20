package com.avs.adpass.commands;

import com.avs.adpass.services.security.UserDetailsImpl;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegistrationForm extends UserDetailsImpl{

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 75)
    private String username;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String repeatpassword;

}
