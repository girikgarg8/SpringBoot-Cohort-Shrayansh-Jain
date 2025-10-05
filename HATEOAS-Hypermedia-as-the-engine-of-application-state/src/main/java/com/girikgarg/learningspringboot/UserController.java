package com.girikgarg.learningspringboot;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class UserController {
    @GetMapping("/addUser")
    public ResponseEntity <UserResponse> addUser() {
        UserResponse response = new UserResponse("123", "Girik", "Unverified");

        // our business logic to determine which Verify API to be invoked

//        Link verifyLink = WebMvcLinkBuilder.linkTo(UserController.class)
//                .slash("sms-verify-finish")
//                .withRel("verify")
//                .withType("POST");

        Link verifyLink = Link.of("/api/sms-verify-finish").withRel("verify").withType("POST");

        response.addLink(verifyLink);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
