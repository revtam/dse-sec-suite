package at.ac.univie.dse.cs.integration;

import at.ac.univie.dse.cs.network.api.http.dto.PublishRequest;
import at.ac.univie.dse.cs.network.api.http.dto.SubscriptionRequest;
import at.ac.univie.dse.cs.network.api.http.model.SubscriberPendingResponseKey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Import(TestConfig.class)
@TestPropertySource(locations="classpath:http-api.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UnsubscribeIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private RestTemplate restTemplate;


    @Test
    public void unsubscribe_afterSubscribingToEvent_UnsubscribeSuccessfullyAndReceive2XXResponse(){

        //Given
        final var subscriptionRequest = new SubscriptionRequest("TEST","1","*");
        this.restTemplate.postForEntity("http://localhost:"+this.port+"/api/v1/subscribe", subscriptionRequest,Void.class);

        //When
        ResponseEntity<Void> response = this.restTemplate
                .exchange("http://localhost:"+this.port+"/api/v1/unsubscribe/TEST/1",
                        HttpMethod.DELETE,
                        null,
                        Void.class);

        //Then
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void unsubscribe_NoExistingSubscription_ThrowsExceptionAndReturn4XXBADREQUEST(){

        //Given no subscription

        //When
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
            ResponseEntity<Void> response = this.restTemplate
                    .exchange("http://localhost:"+this.port+"/api/v1/unsubscribe/TEST_NOT_SUBSCRIBED/1",
                            HttpMethod.DELETE,
                            null,
                            Void.class);
        });

        //Then
        Assertions.assertEquals(HttpStatus.BAD_REQUEST,exception.getStatusCode());
    }
}