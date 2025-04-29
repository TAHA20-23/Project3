package com.example.project3.DTO;


import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDTO {

    private Integer user_id;

    @Column(nullable = false, unique = true)
    @NotNull(message = "Username can't be null")
    @Size(min = 4, max = 10,message = "Username must be between 4 and 10 characters")
    private String username;

    @Column(nullable = false)
    @NotNull(message = "Password can't be null")
    @Size(min = 6, message = "password must be at least 6 characters")
    private String password;

    @Column(nullable = false)
    @NotNull(message = "Name cant' be null")
    @Size(min = 2,max = 20, message = "Length must be between 2 and 20 characters. ")
    private String name;

    @Column(nullable = false, unique = true)
    @Email(message = "Email must be valid")
    private String email;

    @Column(nullable = false)
    @Pattern(regexp = "CUSTOMER|EMPLOYEE|ADMIN",message = "Role must be CUSTOMER, EMPLOYEE, or ADMIN")
    private String role;


    @NotNull(message = "Phone number cannot be null")
    @Pattern(regexp = "^05\\d{8}$", message = "Phone number must start with 05 and be 10 digits")
    private String phoneNumber;





}


