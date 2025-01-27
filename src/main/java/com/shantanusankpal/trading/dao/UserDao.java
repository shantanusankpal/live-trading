package com.shantanusankpal.trading.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shantanusankpal.trading.domain.USER_ROLE;
import com.shantanusankpal.trading.dto.TwoFactoAuth;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "Users")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserDao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String fullName;

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String mobileNo;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride( name = "isEnabled", column = @Column(name = "twoFA_isEnabled")),
            @AttributeOverride( name = "verificationType", column = @Column(name = "twoFA_verificationType"))
    })
    private TwoFactoAuth twoFactoAuth = new TwoFactoAuth();

    private USER_ROLE userRole = USER_ROLE.ROLE_CUSTOMER;


}
;