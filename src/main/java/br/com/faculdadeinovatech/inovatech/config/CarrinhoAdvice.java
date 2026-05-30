package br.com.faculdadeinovatech.inovatech.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import br.com.faculdadeinovatech.inovatech.service.CarrinhoService;

@ControllerAdvice
public class CarrinhoAdvice {

    @Autowired
    private CarrinhoService carrinhoService;

    @ModelAttribute("carrinhoQtd")
    public int carrinhoQuantidade() {
        return carrinhoService.getQuantidadeTotal();
    }
}
