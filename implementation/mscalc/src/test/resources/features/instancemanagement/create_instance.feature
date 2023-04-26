Feature: Create Primary Microservice Instance

  Scenario: Test microservice creation and the subsequent notifying of the CS
    Given the communication service is running
    When an instance with id "primary" and port "8104" is created
    Then the instance is started and event for this is received




