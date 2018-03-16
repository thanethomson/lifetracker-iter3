package com.thanethomson.lifetracker.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    /**
     * We store a salted hash of the user's password instead of the raw password itself.
     */
    @JsonIgnore
    @Column(name = "password_hash")
    private String passwordHash;

    /**
     * Has the user's e-mail address been confirmed?
     */
    @Column(name = "email_confirmed")
    private Boolean emailConfirmed = false;

    /**
     * The token to use when verifying the user's e-mail address.
     */
    @JsonIgnore
    @Column(name = "email_confirmation_token")
    private String emailConfirmationToken;

    /**
     * A unique token to e-mail to a user to allow them to reset their password.
     */
    @JsonIgnore
    @Column(name = "password_reset_token")
    private String passwordResetToken;

}
