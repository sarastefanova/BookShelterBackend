package com.example.books.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.management.relation.Role;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String userName;
    String name;
    String surname;
    String address;
    String number;
    String password;
    @Column(nullable = true)
    @Lob
    private byte[] file;

    //String encryptedPassword;
    String email;
   // private byte[] imageUser;

//    @JsonIgnore
//    @ManyToMany(cascade = CascadeType.ALL)
    @ManyToOne
    Roles roles;

//    @ManyToMany
//            @JsonIgnore
//    List<Book>likedBooks;
//
//    @ManyToMany
//    @JsonIgnore
//    List<Book>orderedBooks;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Roles> roles = new ArrayList<>();
        roles.add(getRoles());
        return (Collection<? extends GrantedAuthority>) roles;
    }

    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
