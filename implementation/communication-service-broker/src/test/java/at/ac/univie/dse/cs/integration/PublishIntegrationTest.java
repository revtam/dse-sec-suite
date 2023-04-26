package at.ac.univie.dse.cs.integration;


import at.ac.univie.dse.cs.network.api.http.dto.PublishRequest;
import at.ac.univie.dse.cs.network.api.http.dto.SubscriptionRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Import(TestConfig.class)
@TestPropertySource(locations="classpath:http-api.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PublishIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void publishRequest_ValidPublishRequest_Receive2XXResponse(){

        //Given
        final var jsonPayload = "{\"name\":\"testName\"}";
        final var publishRequest = new PublishRequest("TEST","*",jsonPayload);

        //When
        ResponseEntity<Void> response = this.restTemplate
                .postForEntity("http://localhost:"+this.port+"/api/v1/publish", publishRequest,Void.class);

        //Then
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void publishRequest_InvalidJsonPayload_ThrowsBadRequest4XXError(){

        //Given
        final var jsonPayload = "{'invalid#json#payload'}";
        final var publishRequest = new PublishRequest("TEST","*",jsonPayload);

        //When
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity<Void> response = this.restTemplate
                    .postForEntity("http://localhost:"+this.port+"/api/v1/publish", publishRequest,Void.class);
        });

        //Then
        Assertions.assertEquals(HttpStatus.BAD_REQUEST,exception.getStatusCode());
    }
}
