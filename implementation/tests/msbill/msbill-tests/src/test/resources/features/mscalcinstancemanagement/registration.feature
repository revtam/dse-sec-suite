Feature: Process MSCalc creation

  Scenario: Registering new MSCalc instance
  	Given the communication service and the MSBill are running
  	When an MSCalc instance with id "mscalc3" is created
  	Then the new instance should be present in the published price report within 10 seconds