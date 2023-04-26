Feature: Process MSCalc instance termination

  Scenario: Removing terminated MSCalc instance
    Given the communication service and the MSBill are running
    And an MSCalc instance with id "mscalc4" is created
    When the same MSCalc instance terminates
    Then the instance should be gone from the published price report within 10 seconds
 