package com.xworkz.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestDepartmentDTO implements Serializable {

    @NotBlank(message = "Department name is mandatory")
    @Size(max = 100, message = "Department name must be less than or equal to 100 characters")
    private String departmentName;

    @Size(max = 100, message = "Area must be less than or equal to 100 characters")
    private String area;

    @Size(max = 255, message = "Address must be less than or equal to 255 characters")
    private String address;
}
