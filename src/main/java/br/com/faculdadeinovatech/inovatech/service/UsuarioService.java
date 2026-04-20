package br.com.faculdadeinovatech.inovatech.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.faculdadeinovatech.inovatech.entity.PasswordResetToken;
import br.com.faculdadeinovatech.inovatech.entity.Usuario;
import br.com.faculdadeinovatech.inovatech.repository.PasswordResetTokenRepository;
import br.com.faculdadeinovatech.inovatech.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    public Usuario save(Usuario usuario) {
        // Criptografar a senha antes de salvar
        usuario.setSenhaUsuario(passwordEncoder.encode(usuario.getSenhaUsuario()));

        return usuarioRepository.save(usuario);
    }

    // Métodos para criar e validar tokens de redefinição de senha, e para alterar a
    // senha do usuário.
    public Optional<Usuario> findByLoginUsuario(String loginUsuario) {
        return usuarioRepository.findByLoginUsuario(loginUsuario);
    }

    // Método CRIAR modificado para CRIAR ou ATUALIZAR o token
    public void createOrUpdatePasswordResetTokenForUser(Usuario usuario, String token) {

        // 1. Tenta encontrar um token existente para o usuário
        PasswordResetToken existingToken = passwordResetTokenRepository.findByUsuario(usuario);

        if (existingToken != null) {
            // 2. Se um token existe, atualiza-o
            existingToken.setToken(token);
            existingToken.setExpiryDate(calculateExpiryDate(PasswordResetToken.EXPIRATION)); // Recalcula a data de
                                                                                             // expiração
            passwordResetTokenRepository.save(existingToken);
        } else {
            // 3. Se não existe, cria um novo token
            PasswordResetToken newToken = new PasswordResetToken(token, usuario);
            passwordResetTokenRepository.save(newToken);
        }
    }

    // Método auxiliar para calcular a data de expiração (pode ser movido para a
    // entidade se preferir)
    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordResetTokenRepository.findByToken(token);

        return !isTokenFound(passToken) ? "invalidToken"
                : isTokenExpired(passToken) ? "expired"
                        : null;
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }

    public Optional<Usuario> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token)).map(PasswordResetToken::getUsuario);
    }

    public void changeUserPassword(Usuario usuario, String password) {
        usuario.setSenhaUsuario(passwordEncoder.encode(password));
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void deletePasswordResetToken(Usuario usuario) {
        passwordResetTokenRepository.deleteByUsuario(usuario);
    }
}
