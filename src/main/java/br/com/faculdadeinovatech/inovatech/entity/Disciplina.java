package br.com.faculdadeinovatech.inovatech.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Disciplina {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idDisciplina;

    @Column(nullable = false, length = 40)
    private String nomeDisciplina;

    @Column(nullable = false, length = 5)
    private String siglaDisciplina;

    @Column(nullable = false)
    private Integer cargaHorariaDisciplina;

    @ManyToOne // Muitas diciplinas para Um curso
    @JoinColumn(name = "idCurso_fk")
    private Curso curso;

    @ManyToOne // Muitas diciplinas para Um professor
    @JoinColumn(name = "idProfessor_fk")
    private Professor professor;
}
