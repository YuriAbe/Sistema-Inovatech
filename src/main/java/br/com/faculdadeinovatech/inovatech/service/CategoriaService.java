package br.com.faculdadeinovatech.inovatech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.faculdadeinovatech.inovatech.dto.CategoriaResponseDTO;
import br.com.faculdadeinovatech.inovatech.entity.Categoria;
import br.com.faculdadeinovatech.inovatech.repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Lista de entidades para a tela administrativa de categorias
    public List<Categoria> listarTodasEntidades() {
        return categoriaRepository.findAll();
    }

    // Lista de DTOs para popular filtros e selects nas telas de produto
    public List<CategoriaResponseDTO> listarTodas() {
        return categoriaRepository.findAll().stream()
                .map(CategoriaResponseDTO::fromEntity)
                .toList();
    }

    public Categoria salvar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria buscarEntity(Integer id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com ID " + id));
    }

    public void deletar(Integer id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Não é possível deletar: Categoria não encontrada com ID " + id);
        }
        categoriaRepository.deleteById(id);
    }
}
