package br.com.faculdadeinovatech.inovatech.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;
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

    @NotBlank(message = "O nome da disciplina é obrigatório")
    @Size(max = 40, message = "O nome deve ter no máximo 40 caracteres")
    @Column(nullable = false, length = 40)
    private String nomeDisciplina;

    @NotBlank(message = "A sigla é obrigatória")
    @Size(max = 5, message = "A sigla deve ter no máximo 5 caracteres")
    @Column(nullable = false, length = 5)
    private String siglaDisciplina;

    @NotNull(message = "A carga horária é obrigatória")
    @Min(value = 1, message = "A carga horária deve ser no mínimo 1 hora")
    @Column(nullable = false)
    private Integer cargaHorariaDisciplina;

    @ManyToOne // Muitas diciplinas para Um curso
    @JoinColumn(name = "idCurso_fk")
    private Curso curso;

    @ManyToOne // Muitas diciplinas para Um professor
    @JoinColumn(name = "idProfessor_fk")
    private Professor professor;
}
