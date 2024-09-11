package com.xworkz.dto.apiDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class StateDTO implements Serializable {

    @JsonProperty("state_name")
    private String stateName;
}
