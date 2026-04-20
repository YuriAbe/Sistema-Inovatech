package br.com.faculdadeinovatech.inovatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.faculdadeinovatech.inovatech.entity.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
    
}
