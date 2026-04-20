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

import br.com.faculdadeinovatech.inovatech.entity.Curso;
import br.com.faculdadeinovatech.inovatech.service.CursoService;

@Controller
@RequestMapping("/cursos")
public class CursoController {
    // Injeção de depência  do service de cursos
    @Autowired
    private CursoService cursoService;

    // Método para salvar um curso
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Curso curso) {
        cursoService.save(curso); // Salva o curso
        return "redirect:/cursos/listar"; // Redireciona para a página de listagem de cursos
    }

    // Método para listar todos os cursos
    @GetMapping("/listar")
    public String listar(Model model) {
        List<Curso> cursos = cursoService.findAll(); // Obtém a lista de cursos;
        model.addAttribute("cursos", cursos); // Adiciona a lista de cursos ao modelo
        return "curso/listaCursos"; // Retorna a view para listar os cursos
    }

    // Método para abrir o formulário de cadastro de curso
    @GetMapping("/criar")
    public String criarForm(Model model) {
        model.addAttribute("curso", new Curso()); // Adiciona um novo curso ao modelo
        return "curso/formularioCurso"; // Retorna a view para o formulario de cadastro
    }

    // Metodo para abrir o formulário de edição de curso
    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Integer id, Model model) {
        Curso curso = cursoService.findById(id); // Obtém curso pelo ID
        model.addAttribute("curso", curso); // Adiciona o curso ao modelo
        return "curso/formularioCurso"; // Retorna a view para o formulário de edição
    }

    // Método para excluir um modelo
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id) {
        cursoService.deleteById(id); // Exclui o curso direto pelo id
        return "redirect:/cursos/listar"; // Redireciona para a página de listagem de cursos.
    }

}
