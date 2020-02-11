package com.example.books.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Roles implements GrantedAuthority {

    @Id
    @GeneratedValue
    Long id;
    String role;

    @Override
    public String getAuthority() {
        return getRole();
    }

//    @JsonIgnore
//    @NotFound(action = NotFoundAction.IGNORE)
//    @ManyToMany(mappedBy = "roles",cascade = CascadeType.ALL)

}
