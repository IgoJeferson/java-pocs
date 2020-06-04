# Java Hello Cucumber

Cucumber is a tool that supports Behaviour-Driven Development(BDD). 
If you’re new to Behaviour-Driven Development read our BDD introduction first.


## Key points


1. To create this project using maven we will use cucumber-archetype Maven plugin. Open a terminal, go to the directory where you want to create your project, and run the following command:
```
mvn archetype:generate                      \
   "-DarchetypeGroupId=io.cucumber"           \
   "-DarchetypeArtifactId=cucumber-archetype" \
   "-DarchetypeVersion=5.6.0"               \
   "-DgroupId=com.github.igojeferson.cucumber"                  \
   "-DartifactId=java-hello-cucumber"               \
   "-Dpackage=com.github.igojeferson.cucumber"                  \
   "-Dversion=1.0.0-SNAPSHOT"                 \
   "-DinteractiveMode=false"
```

2. Execute the tests:

```
mvn test
```

3. We should se something like:
```
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running com.github.igojeferson.cucumber.RunCucumberTest
jun 03, 2020 11:27:46 PM io.cucumber.junit.Cucumber <init>
ADVERTÊNCIA: By default Cucumber is running in --non-strict mode.
This default will change to --strict and --non-strict will be removed.
You can use --strict or @CucumberOptions(strict = true) to suppress this warning

Scenario Outline: Today is or is not Friday # com/github/igojeferson/cucumber/is_it_friday_yet.feature:11
  Given today is "Friday"                   # com.github.igojeferson.cucumber.StepDefinitions.today_is(java.lang.String)
  When I ask whether it's Friday yet        # com.github.igojeferson.cucumber.StepDefinitions.i_ask_whether_it_s_Friday_yet()
  Then I should be told "TGIF"              # com.github.igojeferson.cucumber.StepDefinitions.i_should_be_told(java.lang.String)

Scenario Outline: Today is or is not Friday # com/github/igojeferson/cucumber/is_it_friday_yet.feature:12
  Given today is "Sunday"                   # com.github.igojeferson.cucumber.StepDefinitions.today_is(java.lang.String)
  When I ask whether it's Friday yet        # com.github.igojeferson.cucumber.StepDefinitions.i_ask_whether_it_s_Friday_yet()
  Then I should be told "Nope"              # com.github.igojeferson.cucumber.StepDefinitions.i_should_be_told(java.lang.String)

Scenario Outline: Today is or is not Friday # com/github/igojeferson/cucumber/is_it_friday_yet.feature:13
  Given today is "anything else!"           # com.github.igojeferson.cucumber.StepDefinitions.today_is(java.lang.String)
  When I ask whether it's Friday yet        # com.github.igojeferson.cucumber.StepDefinitions.i_ask_whether_it_s_Friday_yet()
  Then I should be told "Nope"              # com.github.igojeferson.cucumber.StepDefinitions.i_should_be_told(java.lang.String)

3 Scenarios (3 passed)
9 Steps (9 passed)
0m0,129s


Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.506 sec

Results :

Tests run: 3, Failures: 0, Errors: 0, Skipped: 0

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  3.539 s
[INFO] Finished at: 2020-06-03T23:27:46-03:00
[INFO] ------------------------------------------------------------------------
```

## Credits

https://cucumber.io/docs/guides/10-minute-tutorial/