package br.com.faculdadeinovatech.inovatech.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idCurso;
    
    @NotBlank(message = "O nome do curso é obrigatório")
    @Size(max = 40, message = "O nome deve ter no máximo 40 caracteres")
    @Column(nullable = false, length = 40)
    private String nomeCurso;
        
    @NotNull(message = "A carga horária é obrigatória")
    @Min(value = 1, message = "A carga horária deve ser no mínimo 1 hora")
    @Column(nullable = false)
    private Integer cargaHorariaCurso; 

    // Um curso para Muitos alunos
}


