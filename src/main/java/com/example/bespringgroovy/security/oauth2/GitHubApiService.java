package com.example.bespringgroovy.security.oauth2;

import com.example.bespringgroovy.security.oauth2.dto.EmailGithubResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.Email;
import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * Class <br>
 *
 * @author Tuyen Tran
 * @function_ID:
 * @screen_ID:
 */
@Service
public class GitHubApiService {
  @Autowired
  private RestTemplate restTemplate;
  @Autowired
  private ModelMapper modelMapper;


  public List<EmailGithubResponse> getUserEmails(String accessToken) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + accessToken);

    RequestEntity<Object> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, URI.create("https://api.github.com/user/emails"));
    ResponseEntity<Object> response = restTemplate.exchange(requestEntity, Object.class);
    List<LinkedHashMap<String, Object>> listResponse = (List<LinkedHashMap<String, Object>>) response.getBody();
    return Arrays.asList(Objects.requireNonNull(modelMapper.map(listResponse, EmailGithubResponse[].class)));
  }

}
