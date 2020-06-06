Feature: Account Holder withdraws cash

  Scenario: Account has sufficient funds
    Given the account balance is $100
    And the card is valid
    When the Account Holder requests $20
    Then the ATM should dispense $20
    And the machine contains enough money
    And the account balance should be $80
