Feature: Process task changes

  Scenario: Registering new task
    Given the communication service and the MSBill are running
    And an MSCalc instance with id "mscalc1" is registered
    And its original price is noted
    When new task is published for the same MSCalc instance
    Then the published price report should contain an increased price for that MSCalc instance compared to the price before publishing the task
    
  Scenario: Removing completed task
    Given the communication service and the MSBill are running
  	And an MSCalc instance with id "mscalc2" is registered
  	And a task is already registered for the same MSCalc instance
  	When that MSCalc instance completes a task
  	Then the published price report should contain a decreased price for that MSCalc instance compared to the price before completing the task