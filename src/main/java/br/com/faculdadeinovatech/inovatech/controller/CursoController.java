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

import br.com.faculdadeinovatech.inovatech.entity.Curso;
import br.com.faculdadeinovatech.inovatech.service.CursoService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/cursos")
public class CursoController {
    // Injeção de depência  do service de cursos
    @Autowired
    private CursoService cursoService;

    // Método para salvar um curso
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Curso curso,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "curso/formularioCurso";
        }
        cursoService.save(curso);
        redirectAttributes.addFlashAttribute("sucesso", "Curso salvo com sucesso!");
        return "redirect:/cursos/listar";
    }

    // Método para listar todos os cursos
    @GetMapping("/listar")
    public String listar(Model model) {
        List<Curso> cursos = cursoService.findAll();
        model.addAttribute("cursos", cursos);
        return "curso/listaCursos";
    }

    // Método para abrir o formulário de cadastro de curso
    @GetMapping("/criar")
    public String criarForm(Model model) {
        model.addAttribute("curso", new Curso());
        return "curso/formularioCurso";
    }

    // Metodo para abrir o formulário de edição de curso
    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Integer id, Model model) {
        Curso curso = cursoService.findById(id);
        model.addAttribute("curso", curso);
        return "curso/formularioCurso";
    }

    // Método para excluir um curso
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        cursoService.deleteById(id);
        redirectAttributes.addFlashAttribute("sucesso", "Curso excluído com sucesso!");
        return "redirect:/cursos/listar";
    }

}
