package br.com.faculdadeinovatech.inovatech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.faculdadeinovatech.inovatech.dto.ProdutoResponseDTO;
import br.com.faculdadeinovatech.inovatech.entity.Produto;
import br.com.faculdadeinovatech.inovatech.service.CategoriaService;
import br.com.faculdadeinovatech.inovatech.service.ProdutoService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CategoriaService categoriaService;

    // ===================== ÁREA PÚBLICA =====================

    // Vitrine pública de produtos: busca, filtro por categoria, ordenação e paginação
    @GetMapping
    public String vitrine(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Integer idCategoria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            @RequestParam(defaultValue = "descricaoProduto,asc") String sort,
            Model model) {

        String[] sortParts = sort.split(",");
        Sort.Direction direction = sortParts.length > 1 && sortParts[1].equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortParts[0]));

        Page<ProdutoResponseDTO> produtos = produtoService.buscarVitrine(q, idCategoria, pageable);

        model.addAttribute("produtos", produtos.getContent());
        model.addAttribute("categorias", categoriaService.listarTodas());
        model.addAttribute("paginaAtual", page);
        model.addAttribute("totalPaginas", produtos.getTotalPages());
        model.addAttribute("totalItens", produtos.getTotalElements());
        model.addAttribute("q", q);
        model.addAttribute("idCategoria", idCategoria);
        model.addAttribute("sort", sort);
        model.addAttribute("size", size);

        return "produto/vitrine";
    }

    // Página de detalhe pública do produto
    @GetMapping("/detalhe/{id}")
    public String detalhe(@PathVariable Integer id, Model model) {
        ProdutoResponseDTO produto = produtoService.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        model.addAttribute("produto", produto);
        return "produto/detalheProduto";
    }

    // ===================== ÁREA ADMINISTRATIVA =====================

    @GetMapping("/listar")
    public String listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ProdutoResponseDTO> produtos = produtoService.listarPaginado(pageable);

        model.addAttribute("produtos", produtos.getContent());
        model.addAttribute("paginaAtual", page);
        model.addAttribute("totalPaginas", produtos.getTotalPages());
        model.addAttribute("totalItens", produtos.getTotalElements());

        return "produto/listarProdutos";
    }

    @GetMapping("/criar")
    public String criarForm(Model model) {
        model.addAttribute("produto", new Produto());
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "produto/formularioProduto";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Integer id, Model model) {
        model.addAttribute("produto", produtoService.buscarEntity(id));
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "produto/formularioProduto";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Produto produto,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("categorias", categoriaService.listarTodas());
            return "produto/formularioProduto";
        }
        produtoService.salvarEntity(produto);
        redirectAttributes.addFlashAttribute("sucesso", "Produto salvo com sucesso!");
        return "redirect:/produtos/listar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        produtoService.deleteById(id);
        redirectAttributes.addFlashAttribute("sucesso", "Produto excluído com sucesso!");
        return "redirect:/produtos/listar";
    }
}
