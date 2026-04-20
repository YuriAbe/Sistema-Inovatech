package br.com.faculdadeinovatech.inovatech.security;

import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.faculdadeinovatech.inovatech.repository.PasswordResetTokenRepository;

@Component
public class TokenPurgeScheduler {

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;

    @Scheduled(cron = "${purge.cron.expression}") // Ex: "0 0 0 * * ?" para todo dia à meia-noite
    public void purgeExpired() {
        Date now = Date.from(Instant.now());
        passwordResetTokenRepository.deleteByExpiryDateLessThan(now);
    }
}