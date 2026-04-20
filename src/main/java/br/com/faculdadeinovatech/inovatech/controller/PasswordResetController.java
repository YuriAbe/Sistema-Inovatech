package br.com.faculdadeinovatech.inovatech.controller;

import br.com.faculdadeinovatech.inovatech.dto.PasswordDto;
import br.com.faculdadeinovatech.inovatech.entity.Usuario;
import br.com.faculdadeinovatech.inovatech.service.EmailService;
import br.com.faculdadeinovatech.inovatech.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Controller
public class PasswordResetController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private MessageSource messages;

    // Exibe a página para solicitar a recuperação de senha
    @GetMapping("/forgotPassword")
    public String showForgotPasswordPage() {
        return "forgotPassword"; // Nome da sua view forgotPassword.html
    }

    // Lida com a solicitação de recuperação de senha (envio do e-mail)
    @PostMapping("/resetPassword")
    public String processForgotPasswordRequest(
            HttpServletRequest request, @RequestParam("email") String userEmail,
            RedirectAttributes redirectAttributes) {

        Optional<Usuario> userOptional = usuarioService.findByLoginUsuario(userEmail);
        if (userOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    messages.getMessage("message.userNotFound", null, Locale.getDefault()));
            return "redirect:/forgotPassword";
        }

        Usuario user = userOptional.get();
        String token = UUID.randomUUID().toString();
        usuarioService.createOrUpdatePasswordResetTokenForUser(user, token);

        String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath();
        String resetUrl = appUrl + "/changePassword?token=" + token;
        String message = messages.getMessage("message.resetPassword", null, Locale.getDefault()) + "\r\n" + resetUrl;

        emailService.sendEmail(user.getLoginUsuario(), "Redefinição de Senha", message);

        redirectAttributes.addFlashAttribute("successMessage",
                messages.getMessage("message.resetPasswordEmailSent", null, Locale.getDefault()));
        return "redirect:/forgotPassword";
    }

    // Exibe a página para redefinir a senha com o token
    @GetMapping("/changePassword")
    public String showChangePasswordPage(Locale locale, Model model, @RequestParam("token") String token,
            RedirectAttributes redirectAttributes) {

        String result = usuarioService.validatePasswordResetToken(token);

        if (result != null) {
            return "redirect:/tokenExpired"; // Redireciona para a view de erro Token Expirado
        } else {
            model.addAttribute("token", token);
            return "updatePassword"; // Nome da sua view updatePassword.html
        }
    }

    // Lida com a redefinição da senha
    @PostMapping("/updatePassword")
    public String savePassword(Locale locale, PasswordDto passwordDto, RedirectAttributes redirectAttributes) {
        // Para validar o campo confirmação de senha
        if (!passwordDto.getNewPassword().equals(passwordDto.getConfirmPassword())) {
            redirectAttributes.addFlashAttribute("errorMessage", "As senhas não coincidem");
            return "redirect:/changePassword?token=" + passwordDto.getToken();
        }

        // Para validar token
        String result = usuarioService.validatePasswordResetToken(passwordDto.getToken());

        if (result != null) {
            return "redirect:/tokenExpired"; // Redireciona para a view de erro Token Expirado
        }

        // Para buscar o usuário
        Optional<Usuario> user = usuarioService.getUserByPasswordResetToken(passwordDto.getToken());
        if (user.isPresent()) {
            // Para atualizar a senha
            usuarioService.changeUserPassword(user.get(), passwordDto.getNewPassword());

            // Para deletar o token após o uso
            usuarioService.deletePasswordResetToken(user.get());

            redirectAttributes.addFlashAttribute("successMessage",
                    messages.getMessage("message.resetPasswordSuc", null, locale));
            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage",
                    messages.getMessage("auth.message.invalid", null, locale));
            return "redirect:/login";
        }
    }

    // Exibe a página de Token Expirado
    @GetMapping("/tokenExpired")
    public String tokenExpiredPage() {
        return "tokenExpired";
    }
}