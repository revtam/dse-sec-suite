package at.ac.univie.dse.cs.network.api.http.validation;

import at.ac.univie.dse.cs.broker.exception.InvalidPayloadException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PayloadValidator {

    private final ObjectMapper objectMapper;

    public PayloadValidator(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void validateJsonPayloadStructure(String payload){
        try{
            this.objectMapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
            this.objectMapper.readTree(payload);
        }catch (JsonProcessingException e){
            throw new InvalidPayloadException("Error when parsing the payload, ".concat(e.getMessage()));
        }

    }
}
