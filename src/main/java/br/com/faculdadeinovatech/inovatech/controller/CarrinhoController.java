package br.com.faculdadeinovatech.inovatech.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.faculdadeinovatech.inovatech.dto.CarrinhoItem;
import br.com.faculdadeinovatech.inovatech.entity.ItemDoPedido;
import br.com.faculdadeinovatech.inovatech.entity.Pedido;
import br.com.faculdadeinovatech.inovatech.entity.Produto;
import br.com.faculdadeinovatech.inovatech.service.CarrinhoService;
import br.com.faculdadeinovatech.inovatech.service.PedidoService;
import br.com.faculdadeinovatech.inovatech.service.ProdutoService;
import br.com.faculdadeinovatech.inovatech.service.AlunoService;

@Controller
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private AlunoService alunoService;

    @GetMapping
    public String verCarrinho(Model model) {
        model.addAttribute("itens", carrinhoService.getItens());
        model.addAttribute("total", carrinhoService.getTotal());
        model.addAttribute("alunos", alunoService.findAll());
        return "carrinho/carrinho";
    }

    @PostMapping("/adicionar")
    public String adicionar(@RequestParam Integer idProduto,
                            @RequestParam(defaultValue = "1") int quantidade,
                            RedirectAttributes redirectAttributes) {
        Produto produto = produtoService.buscarEntity(idProduto);
        if (produto != null) {
            carrinhoService.adicionar(produto, quantidade);
            redirectAttributes.addFlashAttribute("sucesso",
                    produto.getDescricaoProduto() + " adicionado ao carrinho!");
        }
        return "redirect:/produtos";
    }

    @PostMapping("/remover")
    public String remover(@RequestParam Integer idProduto) {
        carrinhoService.remover(idProduto);
        return "redirect:/carrinho";
    }

    @PostMapping("/atualizar")
    public String atualizar(@RequestParam Integer idProduto,
                            @RequestParam int quantidade) {
        if (quantidade <= 0) {
            carrinhoService.remover(idProduto);
        } else {
            carrinhoService.alterarQuantidade(idProduto, quantidade);
        }
        return "redirect:/carrinho";
    }

    @PostMapping("/finalizar")
    public String finalizar(@RequestParam Integer idAluno,
                            RedirectAttributes redirectAttributes) {
        if (carrinhoService.isEmpty()) {
            redirectAttributes.addFlashAttribute("erro", "O carrinho está vazio!");
            return "redirect:/carrinho";
        }

        var aluno = alunoService.findById(idAluno);
        if (aluno == null) {
            redirectAttributes.addFlashAttribute("erro", "Selecione um aluno válido!");
            return "redirect:/carrinho";
        }

        Pedido pedido = new Pedido();
        pedido.setDataPedido(LocalDate.now());
        pedido.setAluno(aluno);

        List<ItemDoPedido> itensPedido = new ArrayList<>();
        for (CarrinhoItem ci : carrinhoService.getItens()) {
            ItemDoPedido item = new ItemDoPedido();
            Produto prod = new Produto();
            prod.setIdProduto(ci.getIdProduto());
            item.setProduto(prod);
            item.setQuantidade(ci.getQuantidade());
            item.setPreco(ci.getValorProduto());
            item.setSubTotal(ci.getSubtotal());
            item.setPedido(pedido);
            itensPedido.add(item);
        }
        pedido.setItens(itensPedido);
        pedido.atualizarTotal();

        pedidoService.save(pedido);
        carrinhoService.limpar();

        redirectAttributes.addFlashAttribute("sucesso", "Pedido realizado com sucesso!");
        return "redirect:/carrinho/confirmacao";
    }

    @GetMapping("/confirmacao")
    public String confirmacao() {
        return "carrinho/confirmacao";
    }
}
