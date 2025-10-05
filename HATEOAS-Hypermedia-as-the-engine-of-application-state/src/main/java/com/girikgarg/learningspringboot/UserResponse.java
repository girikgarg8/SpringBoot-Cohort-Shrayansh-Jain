package com.girikgarg.learningspringboot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponse extends HateoasLinks {
    private String userId;
    private String name;
    private String verifyStatus;
}
