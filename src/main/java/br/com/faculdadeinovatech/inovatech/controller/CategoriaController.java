package br.com.faculdadeinovatech.inovatech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.faculdadeinovatech.inovatech.dto.CategoriaRequestDTO;
import br.com.faculdadeinovatech.inovatech.dto.CategoriaResponseDTO;
import br.com.faculdadeinovatech.inovatech.service.CategoriaService;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/busca")
    public ResponseEntity<Page<CategoriaResponseDTO>> buscar(
            @RequestParam(required = false) String nome,
            @PageableDefault(size = 5, sort = "nome") Pageable paginacao) {
        if (nome == null || nome.isBlank()) {
            return ResponseEntity.ok(categoriaService.listar(paginacao));
        }

        return ResponseEntity.ok(categoriaService.buscarPorNome(nome, paginacao));
    }

    @GetMapping
    public ResponseEntity<Page<CategoriaResponseDTO>> listar(
            @PageableDefault(page = 0, size = 10, sort = "nome", direction = Sort.Direction.ASC) Pageable paginacao) {
        return ResponseEntity.ok(categoriaService.listar(paginacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscar(@PathVariable Integer id) {
        return categoriaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> criar(@Valid @RequestBody CategoriaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.salvar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> update(@PathVariable Integer id,
            @Valid @RequestBody CategoriaRequestDTO dto) {
        CategoriaResponseDTO atualizado = categoriaService.editar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        categoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
