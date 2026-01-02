package com.concepts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    RestTemplate restTemplate;

    // ==================== PLAIN JAVA HttpURLConnection (LEGACY WAY) ====================
    @GetMapping("/plain-java/{id}")
    public ResponseEntity<String> getOrder(@PathVariable String id) {
        
        // Creates an Object of HttpURLConnection,
        // consider it like an envelope or request, in which we specify
        // all the details like URL, Request Method, timeouts etc.
        HttpURLConnection httpURLConnection = null;
        
        try {
            String url = "http://localhost:8082/products/" + id;
            
            // Setting http request method and header
            URL obj = new URL(url);
            httpURLConnection = (HttpURLConnection) obj.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            
            // Max time to establish TCP connection, timeout in millisecond
            httpURLConnection.setConnectTimeout(100);
            
            // Max time to wait for server response after connection is established, timeout in millisecond
            httpURLConnection.setReadTimeout(500);
            
            // Opens the TCP connection trigger the http request and Read response
            // Here it opens up a TCP connection and send the HTTP request, also reads the response.
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String responseLine;
            
            while ((responseLine = in.readLine()) != null) {
                response.append(responseLine);
            }
            
            in.close();
            System.out.println("Response: " + response.toString());
            
            return ResponseEntity.ok("order call successful");
            
        } catch (Exception e) {
            // Exception handling here
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error calling product service: " + e.getMessage());
            
        } finally {
            // If Response is fully read properly, the TCP Connection i.e. HttpClient is returned back to
            // KeepAlive cache else TCP connection get closed.
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
    }

    // ==================== RESTTEMPLATE WAY (MODERN APPROACH) ====================
    @GetMapping("/rest-template/{id}")
    public ResponseEntity<String> getOrderUsingRestTemplate(@PathVariable String id) {
        
        // Invoke product API
        String url = "http://localhost:8082/products/" + id;
        String response = restTemplate.getForObject(url, String.class);
        
        System.out.println("Response from Product API called from order service: " + response);
        
        return ResponseEntity.ok("order call successful");
    }
}

