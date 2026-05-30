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

import br.com.faculdadeinovatech.inovatech.entity.Disciplina;
import br.com.faculdadeinovatech.inovatech.service.DisciplinaService;
import br.com.faculdadeinovatech.inovatech.service.CursoService;
import br.com.faculdadeinovatech.inovatech.service.ProfessorService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/disciplinas")
public class DisciplinaController {
    
    // Injeção de depência  do service de disciplinas
    @Autowired
    private DisciplinaService disciplinaService;
    
    // Injeção de depência  do service de cursos
    @Autowired
    private CursoService cursoService;

    // Injeção de depência do service de professores
    @Autowired
    private ProfessorService professorService;

    // Método para salvar uma disciplina
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Disciplina disciplina,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("cursos", cursoService.findAll());
            model.addAttribute("professores", professorService.findAll());
            return "disciplina/formularioDisciplina";
        }
        disciplinaService.save(disciplina);
        redirectAttributes.addFlashAttribute("sucesso", "Disciplina salva com sucesso!");
        return "redirect:/disciplinas/listar";
    }

    // Método para listar todos as disciplinas
    @GetMapping("/listar")
    public String listar(Model model) {
        List<Disciplina> disciplinas = disciplinaService.findAll();
        model.addAttribute("disciplinas", disciplinas);
        return "disciplina/listaDisciplinas";
    }

    // Método para abrir o formulário de cadastro de disciplina
    @GetMapping("/criar")
    public String criarForm(Model model) {
        model.addAttribute("disciplina", new Disciplina());
        model.addAttribute("cursos", cursoService.findAll());
        model.addAttribute("professores", professorService.findAll());
        return "disciplina/formularioDisciplina";
    }

    // Metodo para abrir o formulário de edição de disciplina
    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Integer id, Model model) {
        Disciplina disciplina = disciplinaService.findById(id);
        model.addAttribute("disciplina", disciplina);
        model.addAttribute("cursos", cursoService.findAll());
        model.addAttribute("professores", professorService.findAll());
        return "disciplina/formularioDisciplina";
    }

    // Método para excluir uma disciplina
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        disciplinaService.deleteById(id);
        redirectAttributes.addFlashAttribute("sucesso", "Disciplina excluída com sucesso!");
        return "redirect:/disciplinas/listar";
    }

}
