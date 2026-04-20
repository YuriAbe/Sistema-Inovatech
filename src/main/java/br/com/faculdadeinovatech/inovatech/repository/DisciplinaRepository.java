package br.com.faculdadeinovatech.inovatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.faculdadeinovatech.inovatech.entity.Disciplina;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Integer>{
    
}
