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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;


@Import(TestConfig.class)
@TestPropertySource(locations="classpath:http-api.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PollPublishedDataForSubscriptionWithoutPublishingIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private RestTemplate restTemplate;


    @Test
    public void pollPublishedDataForSubscription_SubscribingWithGenericPublishTargetAndPublishingNothing_Receive204NOCONTENTResponse(){

        //Given
        final var subscriptionRequest = new SubscriptionRequest("TEST","1","*");
        this.restTemplate.postForEntity("http://localhost:"+this.port+"/api/v1/subscribe", subscriptionRequest,Void.class);


        //When
        final var subscriberPendingResponseKey = new SubscriberPendingResponseKey("TEST","1","*");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Fetch-Mode", "ALL");
        HttpEntity<SubscriberPendingResponseKey> request = new HttpEntity<>(subscriberPendingResponseKey, headers);
        ResponseEntity<String[]> response = this.restTemplate
                .postForEntity("http://localhost:"+this.port+"/api/v1/subscriptions", request,String[].class);


        //Then
        Assertions.assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
    }
}

