package com.example.restdemo.service;

import com.example.restdemo.dao.LookupRepository;
import com.example.restdemo.entity.LookupEntity;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Messages {

    private final LookupRepository lookupRepository;
    private Map<String, String> messages;

    public Messages(LookupRepository lookupRepository) {
        this.lookupRepository = lookupRepository;
    }

    @PostConstruct
    public void init() {
        messages = new HashMap<>();
        List<LookupEntity> userMessages = lookupRepository.findByType("PRODUCT_MESSAGE");
        userMessages.forEach(m -> messages.put(m.getName(), m.getInfo()));
    }

    public String getMessage(String msgName) {
        return messages.get(msgName);
    }
}
