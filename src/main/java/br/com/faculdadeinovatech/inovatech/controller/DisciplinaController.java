package br.com.faculdadeinovatech.inovatech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.faculdadeinovatech.inovatech.entity.Disciplina;
import br.com.faculdadeinovatech.inovatech.service.DisciplinaService;
import br.com.faculdadeinovatech.inovatech.service.CursoService;
import br.com.faculdadeinovatech.inovatech.service.ProfessorService;

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
    public String salvar(@ModelAttribute Disciplina disciplina) {
        disciplinaService.save(disciplina); // Salva a disciplina
        return "redirect:/disciplinas/listar"; // Redireciona para a página de listagem de disciplinas
    }

    // Método para listar todos as disciplinas
    @GetMapping("/listar")
    public String listar(Model model) {
        List<Disciplina> disciplinas = disciplinaService.findAll(); // Obtém a lista de disciplinas;
        model.addAttribute("disciplinas", disciplinas); // Adiciona a lista de disciplinas ao modelo
        return "disciplina/listaDisciplinas"; // Retorna a view para listar as disciplinas
    }

    // Método para abrir o formulário de cadastro de disciplina
    @GetMapping("/criar")
    public String criarForm(Model model) {
        model.addAttribute("disciplina", new Disciplina()); // Adiciona uma nova disciplina ao modelo
        model.addAttribute("cursos", cursoService.findAll()); // Adiciona a lista de cursos ao modelo
        model.addAttribute("professores", professorService.findAll()); // Adiciona a lista de professores ao modelo
        return "disciplina/formularioDisciplina"; // Retorna a view para o formulario de cadastro
    }

    // Metodo para abrir o formulário de edição de disciplina
    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Integer id, Model model) {
        Disciplina disciplina = disciplinaService.findById(id); // Obtém disciplina pelo ID
        model.addAttribute("disciplina", disciplina); // Adiciona a disciplina ao modelo
        model.addAttribute("cursos", cursoService.findAll()); // Adiciona a lista de cursos ao modelo
        model.addAttribute("professores", professorService.findAll()); // Adiciona a lista de professores ao modelo
        return "disciplina/formularioDisciplina"; // Retorna a view para o formulário de edição
    }

    // Método para excluir um modelo
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id) {
        disciplinaService.deleteById(id); // Exclui a disciplina direto pelo id
        return "redirect:/disciplinas/listar"; // Redireciona para a página de listagem de disciplinas.
    }

}
