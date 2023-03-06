package dev.hirzel.sso.entity;

import dev.hirzel.sso.dto.UserDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.nio.charset.StandardCharsets;

@Entity
@Table(name = "users")
public class User {
    public User() {}

    public User(UserDto dto) {
        var encoder = new BCryptPasswordEncoder();
        var passwordHash = encoder
                .encode(dto.password)
                .getBytes(StandardCharsets.UTF_8);

        this.username = dto.username;
        this.passwordHash = passwordHash;
        this.firstName = dto.firstName;
        this.lastName = dto.lastName;
    }
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "username")
    private String username;

    @Column(name = "password_hash")
    private byte[] passwordHash;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(byte[] passwordHash) {
        this.passwordHash = passwordHash;
    }
}
