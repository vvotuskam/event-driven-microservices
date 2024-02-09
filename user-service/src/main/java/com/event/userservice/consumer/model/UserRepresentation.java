package com.event.userservice.consumer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRepresentation {
    private String id;
    private String email;
    private String firstname;
    private String lastname;
    private String action;
}
