package com.xworkz.service.apiService;


import com.xworkz.api.geocodingApi.GeoCoding;
import com.xworkz.dto.apiDTO.CityDTO;
import com.xworkz.dto.apiDTO.CountryDTO;
import com.xworkz.dto.apiDTO.StateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

@Service
public class GeoCoadingServiceImpl implements GeoCoadingService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GeoCoding geoCoding;

    @Cacheable(cacheNames = "shortLivedCache", key = "'countries'")
    @Override
    public List<CountryDTO> getCountries() throws URISyntaxException {

        System.out.println("Fetching Countries from server");
        String url = "https://www.universal-tutorial.com/api/countries";

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Application");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Bearer " + geoCoding.getAccessTocken());

        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<List<CountryDTO>> responseEntity = restTemplate.exchange(new URI(url), HttpMethod.GET, entity,
                new ParameterizedTypeReference<List<CountryDTO>>() {
                });

        System.out.println(responseEntity.getBody());
        return responseEntity.getBody();
    }

    @Cacheable(cacheNames = "shortLivedCache", key = "#country")
    @Override
    public List<StateDTO> getStates(String country) throws URISyntaxException {
        System.out.println("Fetching States from server");

        String url = "https://www.universal-tutorial.com/api/states/" + country;

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Application");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Bearer " + geoCoding.getAccessTocken());

        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<List<StateDTO>> responseEntity = restTemplate.exchange(new URI(url), HttpMethod.GET, entity,
                new ParameterizedTypeReference<List<StateDTO>>() {
                });

        System.out.println(responseEntity.getBody());
        return responseEntity.getBody();
    }

    @Cacheable(cacheNames = "shortLivedCache", key = "#state")
    @Override
    public List<CityDTO> getCities(String state) throws URISyntaxException {
        System.out.println("Fetching Cities from server");

        String url = "https://www.universal-tutorial.com/api/cities/" + state;

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", "Application");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Bearer " + geoCoding.getAccessTocken());

        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<List<CityDTO>> responseEntity = restTemplate.exchange(new URI(url), HttpMethod.GET, entity,
                new ParameterizedTypeReference<List<CityDTO>>() {
                });

        System.out.println(responseEntity.getBody());
        return responseEntity.getBody();
    }
}
