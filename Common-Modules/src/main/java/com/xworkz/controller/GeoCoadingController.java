package com.xworkz.controller;

import com.xworkz.dto.apiDTO.CityDTO;
import com.xworkz.dto.apiDTO.CountryDTO;
import com.xworkz.dto.apiDTO.StateDTO;
import com.xworkz.service.apiService.GeoCoadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@Controller
@RequestMapping("/geocoading")
public class GeoCoadingController {

    @Autowired
    private GeoCoadingService geoCoadingService;

    @GetMapping("/countries")
    @ResponseBody
    public List<CountryDTO> getCountries() throws URISyntaxException {
        return geoCoadingService.getCountries();
    }

    @GetMapping("/states/{country}")
    @ResponseBody
    public List<StateDTO> getStates(@PathVariable String country) throws URISyntaxException {
        return geoCoadingService.getStates(country);
    }

    @GetMapping("/cities/{state}")
    @ResponseBody
    public List<CityDTO> getCities(@PathVariable String state) throws URISyntaxException {
        return geoCoadingService.getCities(state);
    }

}
