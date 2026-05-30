package br.com.faculdadeinovatech.inovatech.dto;

import java.io.Serializable;

public class CarrinhoItem implements Serializable {
    private Integer idProduto;
    private String descricaoProduto;
    private double valorProduto;
    private String imagemUrl;
    private int quantidade;

    public CarrinhoItem() {}

    public CarrinhoItem(Integer idProduto, String descricaoProduto, double valorProduto, String imagemUrl, int quantidade) {
        this.idProduto = idProduto;
        this.descricaoProduto = descricaoProduto;
        this.valorProduto = valorProduto;
        this.imagemUrl = imagemUrl;
        this.quantidade = quantidade;
    }

    public double getSubtotal() {
        return valorProduto * quantidade;
    }

    // Getters and setters
    public Integer getIdProduto() { return idProduto; }
    public void setIdProduto(Integer idProduto) { this.idProduto = idProduto; }
    public String getDescricaoProduto() { return descricaoProduto; }
    public void setDescricaoProduto(String descricaoProduto) { this.descricaoProduto = descricaoProduto; }
    public double getValorProduto() { return valorProduto; }
    public void setValorProduto(double valorProduto) { this.valorProduto = valorProduto; }
    public String getImagemUrl() { return imagemUrl; }
    public void setImagemUrl(String imagemUrl) { this.imagemUrl = imagemUrl; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
}
