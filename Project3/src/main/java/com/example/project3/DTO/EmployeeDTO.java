package com.example.project3.DTO;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeDTO {

    @NotNull(message = "Position cannot be null")
    private String position;

    @NotNull(message = "Salary cannot be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Salary must be a non-negative number")
    private Double salary;

    @NotNull
    private UserDTO userDTO;
}
