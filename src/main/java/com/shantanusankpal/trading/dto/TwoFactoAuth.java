package com.shantanusankpal.trading.dto;

import com.shantanusankpal.trading.domain.VerificationType;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class TwoFactoAuth {
    private boolean isEnabled = false;
    private VerificationType verificationType;
}
