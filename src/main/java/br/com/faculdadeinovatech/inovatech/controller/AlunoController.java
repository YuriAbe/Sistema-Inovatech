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

import br.com.faculdadeinovatech.inovatech.entity.Aluno;
import br.com.faculdadeinovatech.inovatech.service.AlunoService;
import br.com.faculdadeinovatech.inovatech.service.CursoService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

    // Injeção de depência  do service de alunos
    @Autowired
    private AlunoService alunoService;
    
    // Injeção de depência  do service de cursos
    @Autowired
    private CursoService cursoService;

    // Método para salvar um aluno
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Aluno aluno,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("cursos", cursoService.findAll());
            return "aluno/formularioAluno";
        }
        alunoService.save(aluno);
        redirectAttributes.addFlashAttribute("sucesso", "Aluno salvo com sucesso!");
        return "redirect:/alunos/listar";
    }

    // Método para listar todos os alunos
    @GetMapping("/listar")
    public String listar(Model model) {
        List<Aluno> alunos = alunoService.findAll();
        model.addAttribute("alunos", alunos);
        return "aluno/listaAlunos";
    }

    // Método para abrir o formulário de cadastro de aluno
    @GetMapping("/criar")
    public String criarForm(Model model) {
        model.addAttribute("aluno", new Aluno());
        model.addAttribute("cursos", cursoService.findAll());
        return "aluno/formularioAluno";
    }

    // Metodo para abrir o formulário de edição de aluno
    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Integer id, Model model) {
        Aluno aluno = alunoService.findById(id);
        model.addAttribute("aluno", aluno);
        model.addAttribute("cursos", cursoService.findAll());
        return "aluno/formularioAluno";
    }

    // Método para excluir um aluno
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        alunoService.deleteById(id);
        redirectAttributes.addFlashAttribute("sucesso", "Aluno excluído com sucesso!");
        return "redirect:/alunos/listar";
    }

}
