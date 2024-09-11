package com.xworkz.dto.apiDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CityDTO  implements Serializable {
    @JsonProperty("city_name")
    private String cityName;

}
