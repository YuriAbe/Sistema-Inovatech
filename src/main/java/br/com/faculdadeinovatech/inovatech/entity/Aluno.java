package br.com.faculdadeinovatech.inovatech.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idAluno;
    
    @NotBlank(message = "O nome do aluno é obrigatório")
    @Size(max = 40, message = "O nome deve ter no máximo 40 caracteres")
    @Column(nullable = false, length = 40)
    private String nomeAluno;
        
    @Email(message = "Formato de e-mail inválido")
    @Size(max = 30, message = "O e-mail deve ter no máximo 30 caracteres")
    @Column(length = 30)
    private String emailAluno;
    
    @NotBlank(message = "O telefone é obrigatório")
    @Pattern(regexp = "\\d{10,11}", message = "O telefone deve conter 10 ou 11 dígitos numéricos")
    @Column(nullable = false, length = 11)
    private String telefoneAluno;
    
    @NotBlank(message = "O CPF é obrigatório")
    @CpfValido(message = "CPF inválido. Verifique os dígitos informados")
    @Column(nullable = false, length = 11)
    private String cpfAluno;
    
    @NotBlank(message = "O endereço é obrigatório")
    @Size(max = 100, message = "O endereço deve ter no máximo 100 caracteres")
    @Column(nullable = false, length = 100)
    private String enderecoAluno;
    
    @NotBlank(message = "A cidade é obrigatória")
    @Size(max = 30, message = "A cidade deve ter no máximo 30 caracteres")
    @Column(nullable = false, length = 30)
    private String cidadeAluno;   

    @ManyToOne // Muitos alunos para Um curso
    @JoinColumn(name = "idCurso_fk")
    private Curso curso;
    
}

