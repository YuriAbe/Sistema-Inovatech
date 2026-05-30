package br.com.faculdadeinovatech.inovatech.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.faculdadeinovatech.inovatech.dto.CategoriaRequestDTO;
import br.com.faculdadeinovatech.inovatech.dto.CategoriaResponseDTO;
import br.com.faculdadeinovatech.inovatech.entity.Categoria;
import br.com.faculdadeinovatech.inovatech.repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Page<CategoriaResponseDTO> listar(Pageable pageable) {
        return categoriaRepository.findAll(pageable).map(CategoriaResponseDTO::fromEntity);
    }

    public CategoriaResponseDTO salvar(CategoriaRequestDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNomeCategoria(dto.nomeCategoria());
        return CategoriaResponseDTO.fromEntity(categoriaRepository.save(categoria));
    }
    
    public Optional<CategoriaResponseDTO> buscarPorId(Integer id) {
        return categoriaRepository.findById(id)
                .map(CategoriaResponseDTO::fromEntity);
    }
    
    public CategoriaResponseDTO editar(Integer id, CategoriaRequestDTO dto) {
        Categoria existente = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        existente.setNomeCategoria(dto.nomeCategoria());
    
        Categoria atualizada = categoriaRepository.save(existente);
        return CategoriaResponseDTO.fromEntity(atualizada);
    }
    
    public void deletar(Integer id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Não é possível deletar: Categoria não encontrada com ID " + id);
        }
        categoriaRepository.deleteById(id);
    }

    public Page<CategoriaResponseDTO> buscarPorNome(String nomeCategoria, Pageable pageable) {
        return categoriaRepository.findByNomeCategoriaContainingIgnoreCase(nomeCategoria, pageable)
                .map(CategoriaResponseDTO::fromEntity);
    }

}
