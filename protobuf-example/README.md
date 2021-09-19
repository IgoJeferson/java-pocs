# Protocol Buffers - Google's data interchange format


## Overview

Protocol Buffers (a.k.a., protobuf) are Google's language-neutral, platform-neutral, extensible mechanism for serializing structured data

## How to run

```
./mvnw clean install
```

```
./mvnw compile exec:java -Dexec.mainClass="com.github.igojeferson.protobuf.Main"
```

> **_NOTE:_** If you are using windows, run **`./mvnw.cmd`** instead of `./mvnw`
 
## Advantages

* Protobuf performs up to 6 times faster than JSON
* Clear, cross-application schemes
* Backward and forward compatibility
* Flexibility and comfort
* Easy language interoperability
* RPC support:  Server RPC interfaces can be declared as part of protocol files.
* Structure validation. Having a predefined and larger, when compared to JSON, set of data types, messages serialized on Protobuf can be automatically validated by the code that is responsible to exchange them.

## Disadvantages 

* Partially human-readable format
* Small community
* Lack of support
* It loses in some aspects when compared to more globally known formats like JSON

Protobuf protocol to exchange data between services can bring great performance.