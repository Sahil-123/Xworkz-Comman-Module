package com.xworkz.requestDto;

import lombok.*;
import javax.validation.constraints.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestDepartmentAdminDTO implements Serializable {

    @NotBlank(message = "First name is mandatory")
    @Size(max = 50, message = "First name should not exceed 50 characters")
    private String fname;

    @NotBlank(message = "Last name is mandatory")
    @Size(max = 50, message = "Last name should not exceed 50 characters")
    private String lname;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Mobile number is mandatory")
    @Pattern(regexp = "^\\d{10}$", message = "Mobile number should be 10 digits")
    private String mobile;

    @NotNull(message = "Department ID is mandatory")
    private Long departmentId;


}
