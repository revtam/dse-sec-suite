package at.ac.univie.dse.cs.integration;


import at.ac.univie.dse.cs.broker.model.ErrorResponse;
import at.ac.univie.dse.cs.network.api.http.config.HttpAPiConfig;
import at.ac.univie.dse.cs.network.api.http.dto.SubscriptionRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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
public class SubscriptionIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void subscriptionRequest_ValidSubscriptionRequestIsSent_Receive2XXResponse(){

        //Given
        final var subscriptionRequest = new SubscriptionRequest("TEST","1","*");

        //When
        ResponseEntity<Void> response = this.restTemplate
                .postForEntity("http://localhost:"+this.port+"/api/v1/subscribe", subscriptionRequest,Void.class);

        //Then
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void subscriptionRequest_InvalidSubscriptionRequestIsSent_ThrowsBadRequest4XXError(){

        //Given
        final var subscriptionRequest = new SubscriptionRequest(null,"1","*");

        //When
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
            this.restTemplate.postForEntity("http://localhost:"+this.port+"/api/v1/subscribe", subscriptionRequest,Void.class);
        });

        //Then
        Assertions.assertEquals(HttpStatus.BAD_REQUEST,exception.getStatusCode());
    }
}
