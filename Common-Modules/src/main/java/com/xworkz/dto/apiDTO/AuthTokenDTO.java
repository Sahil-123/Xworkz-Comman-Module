package com.xworkz.dto.apiDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@ToString
public class AuthTokenDTO implements Serializable {

//    @Column(name = "auth_token")
//    private String authToken;

    @JsonProperty("auth_token")
    private String authToken;
}
