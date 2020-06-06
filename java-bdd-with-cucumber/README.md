# Cucumber-Java Skeleton

This is the simplest possible build script setup for Cucumber using Java.
There is nothing fancy like a webapp or browser testing. All this does is to show you how
to install and run Cucumber!


## Get the code

Open a command window and run:

    mvn test

This runs Cucumber features using Cucumber's JUnit runner. The `@RunWith(Cucumber.class)` annotation on the 
`RunCucumberTest` class tells JUnit to kick off Cucumber.

## Specify the Scenario

    Feature: Hear shoutfeatures bdd tests example
    
      Scenario: Listener is within range
        Given Lucy is located 15 meters from Sean
        When Sean shouts "free bagels at Sean's"
        Then Lucy hears Sean's message
    
      Scenario: Listener is within range
        Given Lucy is located 15 meters from Sean
        When Sean shouts "Free coffee!"
        Then Lucy hears Sean's message

        
## Create the corresponding tests

    @Given("Lucy is located {int} meters from Sean")
    public void lucy_is_located_meters_from_Sean(Integer distance) {
        ...
    }

    @When("Sean shouts {string}")
    public void sean_shouts(String message) {
        ...
    }

    @Then("Lucy hears Sean's message")
    public void lucy_hears_Sean_s_message() {
        ...
    }
    
After that, fill the methods with the corresponding rules

    
### Credits

    https://school.cucumber.io/courses/take/bdd-with-cucumber-java/
