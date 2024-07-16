package com.xworkz.requestDto;

import com.xworkz.enums.EmployeeComplaintStatus;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class RequestOtherStatusComplaintDTO implements Serializable {
    @NotNull(message = "complaint id should not empty")
    private Long complaintId;

    @NotNull(message = "comment should not empty")
    private String comment;

    @NotNull(message = "status should not empty")
    private EmployeeComplaintStatus status;
}
