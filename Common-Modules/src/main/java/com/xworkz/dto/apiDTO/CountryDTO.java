package com.xworkz.dto.apiDTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CountryDTO implements Serializable {

    @JsonProperty("country_name")
    private String countryName;

    @JsonProperty("country_short_name")
    private String countryShortName;

    @JsonProperty("country_phone_code")
    private int countryPhoneCode;
}
