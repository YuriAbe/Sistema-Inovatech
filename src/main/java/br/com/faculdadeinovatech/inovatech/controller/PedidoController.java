package br.com.faculdadeinovatech.inovatech.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.faculdadeinovatech.inovatech.entity.Aluno;
import br.com.faculdadeinovatech.inovatech.entity.Pedido;
import br.com.faculdadeinovatech.inovatech.entity.Produto;
import br.com.faculdadeinovatech.inovatech.service.AlunoService;
import br.com.faculdadeinovatech.inovatech.service.PedidoService;
import br.com.faculdadeinovatech.inovatech.service.ProdutoService;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    @ResponseBody
    public String salvarPedido(@RequestBody Pedido pedido) {

        pedidoService.save(pedido);

        return "Pedido salvo com sucesso!";
    }

    // Abrir a tela de cadastro
    @GetMapping("/criar")
    public String criarForm(Model model) {

        model.addAttribute("pedido", new Pedido());

        List<Aluno> alunos = alunoService.findAll();
        model.addAttribute("alunos", alunos);

        List<Produto> produtos = produtoService.findAll();
        model.addAttribute("produtos", produtos);

        return "pedido/formularioPedido";

    }

}