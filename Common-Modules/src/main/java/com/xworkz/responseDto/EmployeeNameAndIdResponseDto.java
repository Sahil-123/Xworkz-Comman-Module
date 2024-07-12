package com.xworkz.responseDto;


import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeNameAndIdResponseDto implements Serializable {

    private Long id;

    private String employeeName;
}
