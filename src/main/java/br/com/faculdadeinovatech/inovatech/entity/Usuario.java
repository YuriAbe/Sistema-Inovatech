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
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idUsuario;
    
    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 50, message = "O nome deve ter no máximo 50 caracteres")
    @Column(nullable = false, length = 50)
    private String nomeUsuario;

    @NotBlank(message = "O CPF é obrigatório")
    @CpfValido(message = "CPF inválido. Verifique os dígitos informados")
    @Column(nullable = false, length = 11)
    private String cpfUsuario;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Formato de e-mail inválido")
    @Size(max = 50, message = "O e-mail deve ter no máximo 50 caracteres")
    @Column(nullable = false, length = 50)
    private String loginUsuario;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, max = 150, message = "A senha deve ter no mínimo 6 caracteres")
    @Column(nullable = false, length = 150)
    private String senhaUsuario;

    private String role = "ROLE_USER";

}

