package br.com.faculdadeinovatech.inovatech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.faculdadeinovatech.inovatech.entity.Disciplina;
import br.com.faculdadeinovatech.inovatech.repository.DisciplinaRepository;

@Service
public class DisciplinaService {
    
    // Injeção de dependência do repositório de disciplinas
    @Autowired
    private DisciplinaRepository disciplinaRepository;

    // Método para salvar uma disciplina
    public Disciplina save(Disciplina disciplina) {
        return disciplinaRepository.save(disciplina);
    }
    
    // Método para listar todos as disciplinas
    public List<Disciplina> findAll() {
        return disciplinaRepository.findAll();
    }

    //  Método para encontrar uma disciplina por id
    public Disciplina findById(Integer id) {
        return disciplinaRepository.findById(id).orElse(null);
    }

    // Método para excluir uma disciplina por ID
    public void deleteById(Integer id) {
        disciplinaRepository.deleteById(id);
    }
}
