package com.example.receiptprocessor.controller;

import com.example.receiptprocessor.model.Receipt;
import com.example.receiptprocessor.service.ReceiptService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }


    @PostMapping("/process")
    public ResponseEntity<Map<String, String>> processReceipt(@RequestBody Receipt receipt) {
        //System.out.println("Processing receipt: " + receipt);
        String receiptId = receiptService.processReceipt(receipt);
        return ResponseEntity.ok(Map.of("id", receiptId));
    }

    @GetMapping("/{id}/points")
    public ResponseEntity<Map<String, Integer>> getPoints(@PathVariable String id) {
        Integer points = receiptService.getPoints(id);
        if (points == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of("points", points));
    }
}
