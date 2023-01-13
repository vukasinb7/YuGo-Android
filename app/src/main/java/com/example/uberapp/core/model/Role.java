package com.example.uberapp.core.model;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Setter
@Getter
public class Role /*implements GrantedAuthority*/ {
    private static final long serialVersionUID = 1L;
    Integer id;
    String name;

    public String getAuthority() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }

    public Integer getId() {
        return this.id;
    }
}