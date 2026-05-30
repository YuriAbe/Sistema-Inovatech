package br.com.faculdadeinovatech.inovatech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.faculdadeinovatech.inovatech.entity.Categoria;
import br.com.faculdadeinovatech.inovatech.service.CategoriaService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("categorias", categoriaService.listarTodasEntidades());
        return "categoria/listaCategorias";
    }

    @GetMapping("/criar")
    public String criarForm(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categoria/formularioCategoria";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Integer id, Model model) {
        model.addAttribute("categoria", categoriaService.buscarEntity(id));
        return "categoria/formularioCategoria";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Categoria categoria,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "categoria/formularioCategoria";
        }
        categoriaService.salvar(categoria);
        redirectAttributes.addFlashAttribute("sucesso", "Categoria salva com sucesso!");
        return "redirect:/categorias/listar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id, RedirectAttributes redirect) {
        try {
            categoriaService.deletar(id);
            redirect.addFlashAttribute("sucesso", "Categoria excluída com sucesso!");
        } catch (DataIntegrityViolationException e) {
            redirect.addFlashAttribute("erro",
                    "Não é possível excluir esta categoria porque existem produtos vinculados a ela.");
        }
        return "redirect:/categorias/listar";
    }
}
