package br.com.faculdadeinovatech.inovatech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.faculdadeinovatech.inovatech.repository.AlunoRepository;
import br.com.faculdadeinovatech.inovatech.repository.CategoriaRepository;
import br.com.faculdadeinovatech.inovatech.repository.CursoRepository;
import br.com.faculdadeinovatech.inovatech.repository.ProdutoRepository;
import br.com.faculdadeinovatech.inovatech.service.ProdutoService;

@Controller
public class HomeController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    // Home institucional pública
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("produtosDestaque",
                produtoService.listarPaginado(PageRequest.of(0, 4)).getContent());

        model.addAttribute("totalProdutos", produtoRepository.count());
        model.addAttribute("totalCategorias", categoriaRepository.count());
        model.addAttribute("totalAlunos", alunoRepository.count());
        model.addAttribute("totalCursos", cursoRepository.count());

        return "public/home";
    }
}
