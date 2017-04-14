package com.example;

import java.lang.invoke.MethodHandles;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.infinispan.AdvancedCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

    Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    @Qualifier("cache1")
    AdvancedCache<String, String> cache1;

    @Autowired
    @Qualifier("cache2")
    AdvancedCache<String, String> cache2;

    @PostConstruct
    public void addRandomValuesToCaches() {
        cache1.put(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        cache2.put(UUID.randomUUID().toString(), UUID.randomUUID().toString());

        logger.info("Initializing caches: {} {}", cache1.cacheEntrySet(), cache2.cacheEntrySet());
    }

    @RequestMapping("/")
    public String index() {
        StringBuilder sb = new StringBuilder();
        sb.append("Hello from Spring Boot!").append("\n");
        sb.append(cache1.cacheEntrySet()).append("\n");
        sb.append(cache2.cacheEntrySet()).append("\n");
        return sb.toString();
    }

}