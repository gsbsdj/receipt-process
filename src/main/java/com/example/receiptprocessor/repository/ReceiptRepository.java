package com.example.receiptprocessor.repository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ReceiptRepository {

    private final Map<String, Integer> receiptData = new HashMap<>();

    public void save(String id, int points) {
        receiptData.put(id, points);
    }

    public Integer findById(String id) {
        return receiptData.get(id);
    }
}
