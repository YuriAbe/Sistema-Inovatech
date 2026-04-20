package br.com.faculdadeinovatech.inovatech.service;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    // Métodos para criar e validar tokens de redefinição de senha, e para alterar a senha do usuário.
    public Optional<Usuario> findByLoginUsuario(String loginUsuario) {
        return usuarioRepository.findByLoginUsuario(loginUsuario);
    }

    public void createPasswordResetTokenForUser(Usuario usuario, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, usuario);
        passwordResetTokenRepository.save(myToken);
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

}
