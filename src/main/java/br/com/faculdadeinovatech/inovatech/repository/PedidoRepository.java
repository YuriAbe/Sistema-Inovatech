package br.com.faculdadeinovatech.inovatech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.faculdadeinovatech.inovatech.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    // Carrega os itens junto para a listagem (open-in-view está desligado)
    @Query("select distinct p from Pedido p left join fetch p.itens order by p.idPedido desc")
    List<Pedido> findAllComItens();
}
