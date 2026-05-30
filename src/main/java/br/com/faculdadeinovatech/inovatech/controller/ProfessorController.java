package br.com.faculdadeinovatech.inovatech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.faculdadeinovatech.inovatech.entity.Professor;
import br.com.faculdadeinovatech.inovatech.service.ProfessorService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/professores")
public class ProfessorController {
    
    // Injeção de depência  do service de professores
    @Autowired
    private ProfessorService professorService;
    
    // Método para salvar um professor
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Professor professor,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "professor/formularioProfessor";
        }
        professorService.save(professor);
        redirectAttributes.addFlashAttribute("sucesso", "Professor salvo com sucesso!");
        return "redirect:/professores/listar";
    }

    // Método para listar todos os professores
    @GetMapping("/listar")
    public String listar(Model model) {
        List<Professor> professores = professorService.findAll();
        model.addAttribute("professores", professores);
        return "professor/listaProfessores";
    }

    // Método para abrir o formulário de cadastro de professor
    @GetMapping("/criar")
    public String criarForm(Model model) {
        model.addAttribute("professor", new Professor());
        return "professor/formularioProfessor";
    }

    // Metodo para abrir o formulário de edição de professor
    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Integer id, Model model) {
        Professor professor = professorService.findById(id);
        model.addAttribute("professor", professor);
        return "professor/formularioProfessor";
    }

    // Método para excluir um professor
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        professorService.deleteById(id);
        redirectAttributes.addFlashAttribute("sucesso", "Professor excluído com sucesso!");
        return "redirect:/professores/listar";
    }

}
