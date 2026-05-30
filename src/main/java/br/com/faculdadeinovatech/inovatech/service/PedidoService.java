package br.com.faculdadeinovatech.inovatech.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.faculdadeinovatech.inovatech.entity.ItemDoPedido;
import br.com.faculdadeinovatech.inovatech.entity.Pedido;
import br.com.faculdadeinovatech.inovatech.entity.Produto;
import br.com.faculdadeinovatech.inovatech.repository.PedidoRepository;
import br.com.faculdadeinovatech.inovatech.repository.ProdutoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public Pedido save(Pedido pedido) {
        pedido.setDataPedido(LocalDate.now());

        for(ItemDoPedido item : pedido.getItens()) {
            Produto produto = produtoRepository.findById(item.getProduto()
                .getIdProduto()).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
            
                item.setProduto(produto);
                item.setPreco(produto.getValorProduto());
                item.atualizarSubTotal();
                item.setPedido(pedido);
        }
        pedido.atualizarTotal();;
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Pedido findById(Integer id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    public void deleteById(Integer id) {
        pedidoRepository.deleteById(id);
    }

}
