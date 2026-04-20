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

import br.com.faculdadeinovatech.inovatech.entity.Professor;
import br.com.faculdadeinovatech.inovatech.service.ProfessorService;

@Controller
@RequestMapping("/professores")
public class ProfessorController {
    
    // Injeção de depência  do service de professores
    @Autowired
    private ProfessorService professorService;
    
    // Método para salvar um professor
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Professor professor) {
        professorService.save(professor); // Salva o professor
        return "redirect:/professores/listar"; // Redireciona para a página de listagem de professores
    }

    // Método para listar todos os professores
    @GetMapping("/listar")
    public String listar(Model model) {
        List<Professor> professores = professorService.findAll(); // Obtém a lista de professores;
        model.addAttribute("professores", professores); // Adiciona a lista de professores ao modelo
        return "professor/listaProfessores"; // Retorna a view para listar os professores
    }

    // Método para abrir o formulário de cadastro de professor
    @GetMapping("/criar")
    public String criarForm(Model model) {
        model.addAttribute("professor", new Professor()); // Adiciona um novo professor ao modelo
        return "professor/formularioProfessor"; // Retorna a view para o formulario de cadastro
    }

    // Metodo para abrir o formulário de edição de professor
    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Integer id, Model model) {
        Professor professor = professorService.findById(id); // Obtém professor pelo ID
        model.addAttribute("professor", professor); // Adiciona o professor ao modelo
        return "professor/formularioProfessor"; // Retorna a view para o formulário de edição
    }

    // Método para excluir um modelo
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id) {
        professorService.deleteById(id); // Exclui o professor direto pelo id
        return "redirect:/professores/listar"; // Redireciona para a página de listagem de professores.
    }

}
