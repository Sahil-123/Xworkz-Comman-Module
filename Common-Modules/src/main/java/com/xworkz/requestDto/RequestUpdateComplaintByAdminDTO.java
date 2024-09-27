package com.xworkz.requestDto;

import com.xworkz.enums.ComplaintStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@ToString
public class RequestUpdateComplaintByAdminDTO implements Serializable {
    @NotNull(message = "complaint id should not empty")
    private Long complaintId;

    @NotNull(message = "department ID should not null")
    private Long department;

    @NotEmpty(message = "Status should not empty")
    private ComplaintStatus status;
}
