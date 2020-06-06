Feature: Hear shoutfeatures bdd tests example

  Scenario: Listener is within range
    Given Lucy is located 15 meters from Sean
    When Sean shouts "free bagels at Sean's"
    Then Lucy hears Sean's message

  Scenario: Listener is within range
    Given Lucy is located 15 meters from Sean
    When Sean shouts "Free coffee!"
    Then Lucy hears Sean's message

