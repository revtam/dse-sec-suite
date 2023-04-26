Feature: Process cracking requests

  Scenario: Testing end-to-end crack hash functionality
    Given the communication service is running
    When an instance with id "primary" and port "8104" is created
    And a crack hash request with task id "123" with hash "2345" is received
    Then the result for task id "123" and hash "2345" is sent back to the client and it should have a valid value
    
  Scenario: Testing end-to-end plain text attack functionality
    Given the communication service is running
    When an instance with id "primary" and port "8104" is created
    And a plain text attack request with id "345" with plain text "hello" and cipher text "tqxxa" is received
    Then the result of task with id "345" is sent back to the client and its value should be "12"

