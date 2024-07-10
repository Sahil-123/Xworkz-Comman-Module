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
public class RequestUpdateComplaintDTO implements Serializable {
    @NotNull(message = "complaint id should not empty")
    private Long complaintId;

    @NotEmpty(message = "complaint description should not empty")
    private String description;
}
