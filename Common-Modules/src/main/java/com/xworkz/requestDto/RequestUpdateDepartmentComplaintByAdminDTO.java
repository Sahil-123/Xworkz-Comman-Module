package com.xworkz.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@ToString
public class RequestUpdateDepartmentComplaintByAdminDTO implements Serializable {
    @NotNull(message = "complaint id should not empty")
    private Long complaintId;

    @NotNull(message = "department ID should not null")
    private Long employeeId;

    @NotEmpty(message = "Status should not empty")
    private String status;
}
