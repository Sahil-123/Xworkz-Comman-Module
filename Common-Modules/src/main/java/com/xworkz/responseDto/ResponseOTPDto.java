package com.xworkz.responseDto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseOTPDto {
   private boolean status;
   private String message;
}
