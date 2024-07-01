package com.xworkz.responseDto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ResponseDTO implements Serializable {
    private String status;
    private ResponseDataDTO data;

    public ResponseDTO(){
        System.out.println("Response DTO object is created.");
    }
}
