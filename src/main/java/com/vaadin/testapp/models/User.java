package com.vaadin.testapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_account")

public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "Name could not empty  ")
    @Size(min = 3, max = 16, message = "Name should be between 3 and 16 characters")
    @Pattern(regexp = "^[a-zA-Z]{3,16}$", message = "Latin characters only")
    private String username;

    @NotNull
    private String password;

    @Pattern(regexp = "^[A-Z][a-z]{1,16}$", message = "Example: Li")
    @Size(min = 1, max = 16, message = "Password should be between 1 and 16 characters")
    private String firstName;

    @Pattern(regexp = "^[A-Z][a-z]{1,16}$", message = "Example: Li")
    @Size(min = 1, max = 16, message = "Password should be between 1 and 16 characters")
    private String lastName;


    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private Date createdAt;

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private Date updatedAt;

    @Enumerated(value = EnumType.STRING)
    private Status status;


    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    private boolean important;
    public boolean isImportant() {
        return important;
    }
    public void setImportant(boolean important) {
        this.important = important;
    }


    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }
}

