package com.example.receiptprocessor.storage;

import java.util.concurrent.ConcurrentHashMap;

public class DataStorage {
    public static final ConcurrentHashMap<String,Integer> receiptPoints=new ConcurrentHashMap<>();
}
