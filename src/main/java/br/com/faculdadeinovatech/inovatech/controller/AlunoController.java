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

import br.com.faculdadeinovatech.inovatech.entity.Aluno;
import br.com.faculdadeinovatech.inovatech.service.AlunoService;
import br.com.faculdadeinovatech.inovatech.service.CursoService;

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
    public String salvar(@ModelAttribute Aluno aluno) {
        alunoService.save(aluno); // Salva o aluno
        return "redirect:/alunos/listar"; // Redireciona para a página de listagem de alunos
    }

    // Método para listar todos os alunos
    @GetMapping("/listar")
    public String listar(Model model) {
        List<Aluno> alunos = alunoService.findAll(); // Obtém a lista de alunos;
        model.addAttribute("alunos", alunos); // Adiciona a lista de alunos ao modelo
        return "aluno/listaAlunos"; // Retorna a view para listar os alunos
    }

    // Método para abrir o formulário de cadastro de aluno
    @GetMapping("/criar")
    public String criarForm(Model model) {
        model.addAttribute("aluno", new Aluno()); // Adiciona um novo aluno ao modelo
        model.addAttribute("cursos", cursoService.findAll()); // Adiciona a lista de cursos ao modelo
        return "aluno/formularioAluno"; // Retorna a view para o formulario de cadastro
    }

    // Metodo para abrir o formulário de edição de aluno
    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Integer id, Model model) {
        Aluno aluno = alunoService.findById(id); // Obtém aluno pelo ID
        model.addAttribute("aluno", aluno); // Adiciona o aluno ao modelo
        model.addAttribute("cursos", cursoService.findAll()); // Adiciona a lista de cursos ao modelo
        return "aluno/formularioAluno"; // Retorna a view para o formulário de edição
    }

    // Método para excluir um modelo
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id) {
        alunoService.deleteById(id); // Exclui o aluno direto pelo id
        return "redirect:/alunos/listar"; // Redireciona para a página de listagem de alunos.
    }

}
