package br.com.faculdadeinovatech.inovatech.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import br.com.faculdadeinovatech.inovatech.validation.CpfValido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idProfessor;

    @NotBlank(message = "O nome do professor é obrigatório")
    @Size(max = 40, message = "O nome deve ter no máximo 40 caracteres")
    @Column(nullable = false, length = 40)
    private String nomeProfessor;

    @NotBlank(message = "O telefone é obrigatório")
    @Pattern(regexp = "\\d{10,11}", message = "O telefone deve conter 10 ou 11 dígitos numéricos")
    @Column(nullable = false, length = 11)
    private String telefoneProfessor;

    @NotBlank(message = "O CPF é obrigatório")
    @CpfValido(message = "CPF inválido. Verifique os dígitos informados")
    @Column(nullable = false, length = 11)
    private String cpfProfessor;

    @NotBlank(message = "A graduação é obrigatória")
    @Size(max = 200, message = "A graduação deve ter no máximo 200 caracteres")
    @Column(nullable = false, length = 200)
    private String graduacaoProfessor;
}
