package br.com.faculdadeinovatech.inovatech.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import br.com.faculdadeinovatech.inovatech.dto.CarrinhoItem;
import br.com.faculdadeinovatech.inovatech.entity.Produto;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CarrinhoService implements Serializable {

    private final List<CarrinhoItem> itens = new ArrayList<>();

    public void adicionar(Produto produto, int quantidade) {
        Optional<CarrinhoItem> existente = itens.stream()
                .filter(i -> i.getIdProduto().equals(produto.getIdProduto()))
                .findFirst();

        if (existente.isPresent()) {
            existente.get().setQuantidade(existente.get().getQuantidade() + quantidade);
        } else {
            itens.add(new CarrinhoItem(
                    produto.getIdProduto(),
                    produto.getDescricaoProduto(),
                    produto.getValorProduto(),
                    produto.getImagemUrl(),
                    quantidade));
        }
    }

    public void remover(Integer idProduto) {
        itens.removeIf(i -> i.getIdProduto().equals(idProduto));
    }

    public void alterarQuantidade(Integer idProduto, int quantidade) {
        itens.stream()
                .filter(i -> i.getIdProduto().equals(idProduto))
                .findFirst()
                .ifPresent(i -> i.setQuantidade(quantidade));
    }

    public List<CarrinhoItem> getItens() {
        return itens;
    }

    public double getTotal() {
        return itens.stream().mapToDouble(CarrinhoItem::getSubtotal).sum();
    }

    public int getQuantidadeTotal() {
        return itens.stream().mapToInt(CarrinhoItem::getQuantidade).sum();
    }

    public void limpar() {
        itens.clear();
    }

    public boolean isEmpty() {
        return itens.isEmpty();
    }
}
