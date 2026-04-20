package br.com.faculdadeinovatech.inovatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.faculdadeinovatech.inovatech.entity.PasswordResetToken;
import br.com.faculdadeinovatech.inovatech.entity.Usuario;

import java.util.Date;
import java.util.stream.Stream;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUsuario(Usuario usuario);

    Stream<PasswordResetToken> findAllByExpiryDateLessThan(Date now);

    void deleteByExpiryDateLessThan(Date now);

    void deleteByUsuario(Usuario usuario);
}