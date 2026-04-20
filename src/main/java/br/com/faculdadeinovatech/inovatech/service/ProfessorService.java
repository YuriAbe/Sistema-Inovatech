package br.com.faculdadeinovatech.inovatech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.faculdadeinovatech.inovatech.entity.Professor;
import br.com.faculdadeinovatech.inovatech.repository.ProfessorRepository;

@Service
public class ProfessorService {
    // Injeção de dependência do repositório de professores

    @Autowired
    private ProfessorRepository professorRepository;

    // Método  para salvar um professor
    public Professor save(Professor professor) {
        return professorRepository.save(professor);
    }
    
    // Método para listar todos os professors
    public List<Professor> findAll() {
        return professorRepository.findAll();
    }

    //  Método para encontrar um professor por id
    public Professor findById(Integer id) {
        return professorRepository.findById(id).orElse(null);
    }

    // Método para excluir um professor por ID
    public void deleteById(Integer id) {
        professorRepository.deleteById(id);
    }
}
