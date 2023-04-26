package at.ac.univie.dse.cs.network.api.http.server;

import at.ac.univie.dse.cs.broker.exception.InvalidPayloadException;
import at.ac.univie.dse.cs.broker.exception.SubscriptionNotFoundException;
import at.ac.univie.dse.cs.broker.model.ErrorResponse;
import at.ac.univie.dse.cs.network.api.http.dto.PublishRequest;
import at.ac.univie.dse.cs.network.api.http.dto.SubscriptionRequest;
import at.ac.univie.dse.cs.network.api.http.model.SubscriberPendingResponseKey;
import at.ac.univie.dse.cs.network.api.http.services.PendingSubscriptionsResponsesService;
import at.ac.univie.dse.cs.network.api.http.validation.PayloadValidator;
import at.ac.univie.dse.cs.network.api.invoker.Invoker;
import at.ac.univie.dse.cs.network.model.EventKeyValueMap;
import at.ac.univie.dse.cs.network.model.EventKeyValueMapBuilder;
import at.ac.univie.dse.cs.network.model.EventType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.logging.Logger;


@Controller
@CrossOrigin(origins = "http://localhost:8080")
public class HttpServerRequestHandler {

    private static final Logger LOGGER = Logger.getLogger(HttpServerRequestHandler.class.getName());
    private final Invoker httpBrokerInvoker;
    private final PendingSubscriptionsResponsesService pendingSubscriptionsResponsesService;
    private final PayloadValidator payloadValidator;

    public HttpServerRequestHandler(Invoker httpBrokerInvoker, PendingSubscriptionsResponsesService pendingSubscriptionsResponsesService, PayloadValidator payloadValidator) {
        this.httpBrokerInvoker = httpBrokerInvoker;
        this.pendingSubscriptionsResponsesService = pendingSubscriptionsResponsesService;
        this.payloadValidator = payloadValidator;
    }


    @PostMapping("/api/v1/subscribe")
    public @ResponseBody ResponseEntity<Void> subscribe(@Valid @RequestBody SubscriptionRequest subscriptionRequest){
        EventKeyValueMap eventKeyValueMap = buildSubscriptionEventKeyValueMap(subscriptionRequest);
        LOGGER.info("Calling HTTP Invoker with: ");
        LOGGER.info(eventKeyValueMap.toString());
        this.httpBrokerInvoker.invoke(eventKeyValueMap.getEventData());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/v1/publish")
    public @ResponseBody ResponseEntity<Void> publish(@Valid @RequestBody PublishRequest publishRequest){
        this.payloadValidator.validateJsonPayloadStructure(publishRequest.getPayload());
        EventKeyValueMap eventKeyValueMap = buildPublishEventKeyValueMap(publishRequest);
        this.httpBrokerInvoker.invoke(eventKeyValueMap.getEventData());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/v1/unsubscribe/{eventName}/{identifier}")
    public @ResponseBody ResponseEntity<Void> unSubscribe(@PathVariable("eventName") String eventName,
                                                          @PathVariable("identifier") String subscriberIdentifier){

        EventKeyValueMap eventKeyValueMap = buildUnsubscribeEventKeyValueMap(eventName,subscriberIdentifier);
        this.httpBrokerInvoker.invoke(eventKeyValueMap.getEventData());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/v1/subscriptions")
    public @ResponseBody ResponseEntity<String[]> pollSubscriptionResponse(@Valid @RequestBody SubscriberPendingResponseKey subscriberPendingResponseKey,
                                                                         @RequestHeader("Fetch-Mode")String fetchMode){
            Optional<String[]> subscriptionResponsePayload = this.pendingSubscriptionsResponsesService
                    .getPendingSubscriptions(subscriberPendingResponseKey,fetchMode);
            return subscriptionResponsePayload.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    private EventKeyValueMap buildSubscriptionEventKeyValueMap(SubscriptionRequest subscriptionRequest) {
        EventKeyValueMapBuilder eventKeyValueMapBuilder = new EventKeyValueMapBuilder();
        return eventKeyValueMapBuilder
                .withEventName(subscriptionRequest.getEventName())
                .withEventType(EventType.SUBSCRIBE.toString())
                .withPublishTarget(subscriptionRequest.getPublishTarget())
                .withSenderIdentifier(subscriptionRequest.getIdentifier())
                .build();
    }

    private EventKeyValueMap buildPublishEventKeyValueMap(PublishRequest publishRequest) {
        EventKeyValueMapBuilder eventKeyValueMapBuilder = new EventKeyValueMapBuilder();
        return eventKeyValueMapBuilder
                .withEventName(publishRequest.getEventName())
                .withEventType(EventType.PUBLISH.toString())
                .withPublishTarget(publishRequest.getPublishTarget())
                .withPayLoad(publishRequest.getPayload())
                .build();
    }

    private EventKeyValueMap buildUnsubscribeEventKeyValueMap(String eventName,String subscriberIdentifier) {
        EventKeyValueMapBuilder eventKeyValueMapBuilder = new EventKeyValueMapBuilder();
        return eventKeyValueMapBuilder
                .withEventName(eventName)
                .withSenderIdentifier(subscriberIdentifier)
                .withEventType(EventType.UNSUBSCRIBE.toString())
                .build();
    }

    @ExceptionHandler({ SubscriptionNotFoundException.class, InvalidPayloadException.class})
    public ResponseEntity<ErrorResponse> handleException(RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.getReasonPhrase(),e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleFieldsValidationException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.getReasonPhrase(),"Invalid requestBody: ".concat(e.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }



}
