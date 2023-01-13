package com.example.uberapp.core.dto;

import com.example.uberapp.core.model.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter @Setter
@NoArgsConstructor
public class UserSimplifiedOut {
    private Integer id;
    private String email;

    public UserSimplifiedOut(Integer id, String email) {
        super();
        this.id = id;
        this.email = email;
    }

    public UserSimplifiedOut(User user) {
        this(user.getId(), user.getEmail());
    }
}
