package br.com.faculdadeinovatech.inovatech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.faculdadeinovatech.inovatech.entity.Usuario;
import br.com.faculdadeinovatech.inovatech.service.UsuarioService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Usuario usuario,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "usuario/formularioUsuario";
        }
        // Verificar e-mail duplicado
        if (usuarioService.findByLoginUsuario(usuario.getLoginUsuario()).isPresent()) {
            result.rejectValue("loginUsuario", "duplicate", "Este e-mail já está cadastrado");
            return "usuario/formularioUsuario";
        }
        usuarioService.save(usuario);
        redirectAttributes.addFlashAttribute("sucesso", "Conta criada com sucesso! Faça login com seu e-mail.");
        return "redirect:/login";
    }

    @GetMapping("/criar")
    public String criarForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario/formularioUsuario";
    }

}
