package org.booking.authservice.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.booking.authservice.model.enums.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    private String username;
    private String password;
    private Role role;

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = Role.USER;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeUsername(String username) {
        this.username = username;
    }

    public void changeRole(Role role) {
        this.role = role;
    }
}
