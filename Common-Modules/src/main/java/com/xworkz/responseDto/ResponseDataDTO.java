package com.xworkz.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@ToString
public class ResponseDataDTO implements Serializable {
    private boolean found;
}
