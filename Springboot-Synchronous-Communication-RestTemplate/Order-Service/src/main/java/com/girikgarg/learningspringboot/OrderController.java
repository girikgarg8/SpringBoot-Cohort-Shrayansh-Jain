package com.girikgarg.learningspringboot;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @GetMapping("/{id}")
    public ResponseEntity <String> getOrderById(@PathVariable("id") String id) throws MalformedURLException, IOException {
        HttpURLConnection httpURLConnection = null;
        try {
            String url = "http://localhost:8081/products/" + id;

            URL obj = new URL(url);

            // Creates an object of HTTP Url connection, consider it like an envelope of request, in which we specify the details like URL, request method, timeout etc
            httpURLConnection = (HttpURLConnection) obj.openConnection();

            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Accept", "application/json");

            // connection timeout - maximum time for the TCP connection to be established, in milliseconds
            httpURLConnection.setConnectTimeout(100);

            // max time to wait for server response after connection have been established, timeout in milliseconds
            httpURLConnection.setReadTimeout(500);

            // Opens the TCP connection, trigger the HTTP request and read response
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String responseLine;

            while ((responseLine = in.readLine()) != null) {
                response.append(responseLine.trim());
            }
            in.close();
            System.out.println("Response is: "+ response.toString());
        }
        catch (Exception e) {
            // exception handling here
        }
        finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return ResponseEntity.ok("Order call successful");
    }
}
