# Java Simple example SpringBoot + Hazelcast

Simple Spring Boot application, using Hazelcast as a Distributed Map.

*Hazelcast* provides central and predictable scaling of applications via in-memory access to frequently used data and across an elastically scalable data grid. These techniques both reduce the query load on databases and help improve application speed.

## Running the Sample Application
Run the application using Maven on a terminal:

```./mvnw clean install```

```
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Dserver.port=8080"
```

Here you need to set a different value for the server.port.

```
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Dserver.port=8081"
```

After both application instances are initialized, you should see the Hazelcast cluster formation in the output similar to below:

```
Members {size:2, ver:2} [
    Member [172.30.217.152]:5701 - ff554edb-49ca-4165-990b-4bc549d438da
    Member [172.30.217.152]:5702 - 22a6c9b1-efed-4a85-9f96-bd548975712e this
]
```

## Testing 

Now, you can issue HTTP requests to put and get data back. Run the following command to put the data into Hazelcast distributed map:

```curl "localhost:8080/put" --data "key=key1&value=hazelcast" ```

You will see the value in the output. Then run the command below to get the data back. Note that the call is made to the other application instance:

```curl "localhost:8081/get?key=key1"```

## Testing the Application

To run the integration tests, run the following command in terminal. But before, make sure to kill the running application instances.

```mvn verify -Ptests```

If the tests pass, you’ll see a similar output to the following:

```
2021-10-13 21:34:10.830  INFO 3739 --- [o-auto-1-exec-1] c.h.i.p.impl.PartitionStateManager       : [172.30.217.152]:5701 [hazelcast-cluster] [4.1] Initializing cluster partition table arrangement...
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 12.704 s - in com.github.igojeferson.example.CommandControllerIT
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO]
[INFO] --- maven-failsafe-plugin:2.22.2:verify (default) @ springboot-and-hazelcast ---
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  17.827 s
[INFO] Finished at: 2021-10-13T21:34:13+02:00
[INFO] ------------------------------------------------------------------------

```

# Hazelcast Alternatives 
* Redis
* Apache Spark
* Cassandra
* Memcached
* Apache Ignite

## Useful links

- https://hazelcast.com/clients/java/
- https://stackshare.io/hazelcast