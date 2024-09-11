package com.xworkz.api.geocodingApi;

import com.xworkz.dto.apiDTO.AuthTokenDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

@Component
@Getter
@Setter
public class GeoCoding {

    private static final String tokenURL = "https://www.universal-tutorial.com/api/getaccesstoken";

    //    @Value("${geocoding.apikey}")
    private String apiKey = "RM9Dua9Xu2BUfc8pXm18yPyuaC-pjMKyar8-yBqyjR3VjWR7KIPsDJxKJFxSXdxbnHE";

    //    @Value("${geocoding.mail}")
    private String mail = "jiwaxa7837@furnato.com";

    private String accessTocken;

    @Autowired
    public GeoCoding(RestTemplate restTemplate) {

        if (apiKey == null || mail == null) {
            throw new IllegalStateException("API Key or Mail not configured properly");
        }

        try {
            System.out.println(apiKey);
            System.out.println(mail);

            HttpHeaders headers = new HttpHeaders();
            headers.set("api-token", apiKey);
            headers.set("user-email", mail);
            headers.set("User-Agent", "Application");

            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));


            HttpEntity<String> entity = new HttpEntity<String>(headers);

            ResponseEntity<AuthTokenDTO> responseEntity = restTemplate.exchange(new URI(tokenURL), HttpMethod.GET, entity, AuthTokenDTO.class);

            this.accessTocken = responseEntity.getBody().getAuthToken();

            System.out.println("Geocode accessTocken is fetched :"+accessTocken);
            System.out.println(responseEntity);

        } catch (
                HttpClientErrorException e) {
            System.err.println("Error occurred during API call: " + e.getStatusCode());
            System.err.println("Response body: " + e.getResponseBodyAsString());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }
}
