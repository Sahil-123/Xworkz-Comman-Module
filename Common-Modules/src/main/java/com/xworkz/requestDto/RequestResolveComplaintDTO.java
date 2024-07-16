package com.xworkz.requestDto;

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
public class RequestResolveComplaintDTO implements Serializable {
    @NotNull(message = "complaint id should not empty")
    private Long complaintId;

    @NotNull(message = "comment should not empty")
//    @Size(message = "otp should be only 6 digits", min=10 , max = 6)
    private String comment;

    @NotNull(message = "otp should not empty")
    @Size(message = "otp should be only 6 digits", min=6 , max = 6)
    private String otp;
}
