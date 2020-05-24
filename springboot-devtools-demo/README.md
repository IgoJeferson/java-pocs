# SpringBoot DevTools Demo

In Spring Boot 2.3, a new feature was added that enables you to create Docker Images from your 
application using Cloud Native Buildpacks.  This is really valuable because you can now create an 
immutable artifact that ensures that your application is run in an identical environment from your 
laptop, to your test environment, to production.  Now imagine if you could get that same level of 
consistency during actual development as well.  The framework for doing in-process refreshing of 
Spring Boot applications to quickly iterate within a consistent container when developing locally.

## Steps

1. ```./mvnw springboot:build-image```

2. ```docker run --tty --publish 8080:8080 springboot-devtools-demo:0.0.1-SNAPSHOT```    

3. Create an "org.springframework.boot.devtools.RemoteSpringApplication" pointing to your localhost environment (Program Arguments - http://localhost:8080)