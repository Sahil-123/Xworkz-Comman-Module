package com.xworkz.service.apiService;

import com.xworkz.dto.apiDTO.CityDTO;
import com.xworkz.dto.apiDTO.CountryDTO;
import com.xworkz.dto.apiDTO.StateDTO;

import java.net.URISyntaxException;
import java.util.List;

public interface GeoCoadingService {
    List<CountryDTO> getCountries() throws URISyntaxException;

    List<StateDTO> getStates(String country) throws URISyntaxException;

    List<CityDTO> getCities(String state) throws URISyntaxException;
}
