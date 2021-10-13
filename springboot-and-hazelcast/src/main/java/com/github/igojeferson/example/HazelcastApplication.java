package com.github.igojeferson.example;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ConcurrentMap;

@SpringBootApplication
public class HazelcastApplication {

    public static void main(String[] args) {
        SpringApplication.run(HazelcastApplication.class, args);
    }

    @Bean
    public HazelcastInstance hazelcastInstance(){
        return Hazelcast.newHazelcastInstance();
    }

    @Bean
    public ConcurrentMap<String,String> retrieveMap(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap("map");
    }
}
