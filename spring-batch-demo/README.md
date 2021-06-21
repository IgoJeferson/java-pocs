# Spring Batch Demo

* @EnableBatchProcessing: This annotation allows Spring to assemble all the structure needed to run the batch. All those components that we saw in the Spring Batch architecture will be configured automatically, we will just need to change what we want for our application (e.g. Database).
* jobBuilderFactory and stepBuilderFactory: We inject these components to fluently build the job and its steps.
* step(): Injects (@Bean) and sets up the steps of the job. In our example, a simple tasklet is created that prints Hello World.
* job(): This method is injected with @Bean to return the job that will be built from the configured steps.

## To install the dependencies
``` mvn clean install```

## To execute the program
``` mvn spring-boot:run```