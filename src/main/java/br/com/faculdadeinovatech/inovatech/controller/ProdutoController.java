package br.com.faculdadeinovatech.inovatech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import br.com.faculdadeinovatech.inovatech.dto.ProdutoRequestDTO;
import br.com.faculdadeinovatech.inovatech.dto.ProdutoResponseDTO;
import br.com.faculdadeinovatech.inovatech.service.ProdutoService;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute ProdutoRequestDTO dto) {

        produtoService.save(dto);

        return "redirect:/produtos/listar";
    }

    @GetMapping("/listar")
    public String listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);

        Page<ProdutoResponseDTO> produtos = produtoService.listarPaginado(pageable);

        model.addAttribute("produtos", produtos.getContent());

        model.addAttribute("paginaAtual", page);

        model.addAttribute("totalPaginas",
                produtos.getTotalPages());

        model.addAttribute("totalItens",
                produtos.getTotalElements());

        return "produto/listarProdutos";
    }

    @GetMapping("/criar")
    public String criarForm(Model model) {

        model.addAttribute(
                "produto",
                new ProdutoRequestDTO(
                        null,
                        null,
                        null,
                        null));

        return "produto/formularioProduto";
    }
}