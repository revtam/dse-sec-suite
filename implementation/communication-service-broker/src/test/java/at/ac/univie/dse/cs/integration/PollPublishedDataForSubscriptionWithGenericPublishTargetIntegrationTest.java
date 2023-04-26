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
public class PollPublishedDataForSubscriptionWithGenericPublishTargetIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private RestTemplate restTemplate;


    @Test
    public void pollPublishedDataForSubscription_SubscribingWithGenericPublishTargetAndPublishingTwiceWithGenericPublishTarget_ReceiveExactlyTwoResultsWhenPolling(){

        //Given
        final var subscriptionRequest = new SubscriptionRequest("TEST","1","*");
        this.restTemplate.postForEntity("http://localhost:"+this.port+"/api/v1/subscribe", subscriptionRequest,Void.class);

        final var jsonPayload = "{\"name\":\"testName\"}";
        final var publishRequest = new PublishRequest("TEST","*",jsonPayload);
        this.restTemplate.postForEntity("http://localhost:"+this.port+"/api/v1/publish", publishRequest,Void.class);
        this.restTemplate.postForEntity("http://localhost:"+this.port+"/api/v1/publish", publishRequest,Void.class);


        //When
        final var subscriberPendingResponseKey = new SubscriberPendingResponseKey("TEST","1","*");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Fetch-Mode", "ALL");
        HttpEntity<SubscriberPendingResponseKey> request = new HttpEntity<>(subscriberPendingResponseKey, headers);
        ResponseEntity<String[]> response = this.restTemplate
                .postForEntity("http://localhost:"+this.port+"/api/v1/subscriptions", request,String[].class);


        //Then
        String[] publishedDataToSubscription = response.getBody();
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertNotNull(publishedDataToSubscription);
        Assertions.assertEquals(2, publishedDataToSubscription.length);
        Assertions.assertEquals(jsonPayload, publishedDataToSubscription[0]);
        Assertions.assertEquals(jsonPayload, publishedDataToSubscription[1]);
    }
}
