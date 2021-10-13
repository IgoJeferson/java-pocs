package com.github.igojeferson.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentMap;

@RestController
public class CommandController {

    @Autowired
    private ConcurrentMap<String,String> distributedMap;

    @PostMapping("/put")
    public CommandResponse put(@RequestParam(value = "key") String key, @RequestParam(value = "value") String value) {
        distributedMap.put(key, value);
        return new CommandResponse(value);
    }

    @GetMapping("/get")
    public CommandResponse get(@RequestParam(value = "key") String key) {
        String value = distributedMap.get(key);
        return new CommandResponse(value);
    }


}
