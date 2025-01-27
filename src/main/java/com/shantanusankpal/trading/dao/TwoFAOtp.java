package com.shantanusankpal.trading.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Entity
public class TwoFAOtp {
    @Id
    private String id;

    private String otp;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne
    private UserDao userDao;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String jwt;
}
