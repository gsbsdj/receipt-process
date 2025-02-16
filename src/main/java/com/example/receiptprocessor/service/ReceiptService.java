package com.example.receiptprocessor.service;

import com.example.receiptprocessor.model.Receipt;
import com.example.receiptprocessor.repository.ReceiptRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReceiptService {

    private final ReceiptRepository receiptRepository;

    public ReceiptService(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    public String processReceipt(Receipt receipt) {
        int points = calculatePoints(receipt);
        String receiptId = UUID.randomUUID().toString();
        receiptRepository.save(receiptId, points);
        return receiptId;
    }

    public Integer getPoints(String id) {
        return receiptRepository.findById(id);
    }

    private int calculatePoints(Receipt receipt) {
        int points = 0;

        // Rule 1: One point for each alphanumeric character in the retailer name
        points += receipt.getRetailer().replaceAll("[^a-zA-Z0-9]", "").length();

        // Rule 2: 50 points if total is a round dollar amount with no cents
        if (receipt.getTotal().matches("\\d+\\.00")) {
            points += 50;
        }

        // Rule 3: 25 points if total is a multiple of 0.25
        double total = Double.parseDouble(receipt.getTotal());
        if (total % 0.25 == 0) {
            points += 25;
        }

        // Rule 4: 5 points for every two items
        points += (receipt.getItems().size() / 2) * 5;

        // Rule 5: Points for item descriptions with lengths that are multiples of 3
        for (var item : receipt.getItems()) {
            if (item.getShortDescription().trim().length() % 3 == 0) {
                points += Math.ceil(Double.parseDouble(item.getPrice()) * 0.2);
            }
        }

        // Rule 6: 6 points if the purchase date day is odd
        int day = Integer.parseInt(receipt.getPurchaseDate().split("-")[2]);
        if (day % 2 != 0) {
            points += 6;
        }

        // Rule 7: 10 points if purchase time is between 2:00pm and 4:00pm
        String[] timeParts = receipt.getPurchaseTime().split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);
        if (hour == 14 || (hour == 15 && minute == 0)) {
            points += 10;
        }

        return points;
    }
}
