package br.com.faculdadeinovatech.inovatech.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.faculdadeinovatech.inovatech.entity.Aluno;
import br.com.faculdadeinovatech.inovatech.entity.Categoria;
import br.com.faculdadeinovatech.inovatech.entity.Produto;
import br.com.faculdadeinovatech.inovatech.entity.Usuario;
import br.com.faculdadeinovatech.inovatech.repository.AlunoRepository;
import br.com.faculdadeinovatech.inovatech.repository.CategoriaRepository;
import br.com.faculdadeinovatech.inovatech.repository.ProdutoRepository;
import br.com.faculdadeinovatech.inovatech.repository.UsuarioRepository;

/**
 * Popula o banco com dados aleatórios de teste na primeira execução.
 * Cada bloco só roda se a respectiva tabela estiver vazia, então é seguro
 * rodar a aplicação várias vezes sem duplicar registros.
 */
@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedDatabase(
            CategoriaRepository categoriaRepository,
            ProdutoRepository produtoRepository,
            AlunoRepository alunoRepository,
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {
            Random rnd = new Random();

            // ---- Usuário administrador para login (senha com BCrypt) ----
            if (usuarioRepository.findByLoginUsuario("admin").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setNomeUsuario("Administrador Inovatech");
                admin.setCpfUsuario("00000000000");
                admin.setLoginUsuario("admin");
                admin.setSenhaUsuario(passwordEncoder.encode("admin123"));
                admin.setRole("ROLE_ADMIN");
                usuarioRepository.save(admin);
            }

            // ---- Usuário padrão para teste do carrinho ----
            if (usuarioRepository.findByLoginUsuario("usuario@email.com").isEmpty()) {
                Usuario user = new Usuario();
                user.setNomeUsuario("Usuário Teste");
                user.setCpfUsuario("12345678909");
                user.setLoginUsuario("usuario@email.com");
                user.setSenhaUsuario(passwordEncoder.encode("123456"));
                user.setRole("ROLE_USER");
                usuarioRepository.save(user);
            }

            // ---- Categorias ----
            List<Categoria> categorias = new ArrayList<>();
            if (categoriaRepository.count() == 0) {
                for (String nome : new String[] { "Vestuário", "Material Escolar", "Livros",
                        "Alimentos", "Acessórios" }) {
                    Categoria c = new Categoria();
                    c.setNomeCategoria(nome);
                    categorias.add(categoriaRepository.save(c));
                }
            } else {
                categorias = categoriaRepository.findAll();
            }

            // ---- Produtos aleatórios ----
            if (produtoRepository.count() == 0 && !categorias.isEmpty()) {
                String[] nomes = { "Camiseta Institucional", "Caderno Inovatech", "Kit Lápis de Cor",
                        "Garrafa Térmica", "Mochila Escolar", "Caneta Premium", "Agenda Anual",
                        "Boné da APM", "Estojo Duplo", "Livro de Atividades", "Régua 30cm",
                        "Squeeze Esportivo" };
                String[] marcas = { "APM", "Inovatech", "EduMais", "Escolar+", "Campus" };

                for (String nome : nomes) {
                    Produto p = new Produto();
                    p.setDescricaoProduto(nome);
                    p.setValorProduto(Math.round((5 + rnd.nextDouble() * 95) * 100.0) / 100.0);
                    p.setMarcaProduto(marcas[rnd.nextInt(marcas.length)]);
                    p.setCategoria(categorias.get(rnd.nextInt(categorias.size())));
                    produtoRepository.save(p);
                }
            }

            // ---- Alunos aleatórios ----
            if (alunoRepository.count() == 0) {
                String[] nomes = { "Ana Souza", "Bruno Lima", "Carla Mendes", "Diego Rocha",
                        "Eduarda Alves", "Felipe Santos", "Gabriela Dias", "Henrique Costa" };
                String[] cidades = { "São Paulo", "Campinas", "Santos", "Sorocaba" };

                for (String nome : nomes) {
                    Aluno a = new Aluno();
                    a.setNomeAluno(nome);
                    a.setEmailAluno(nome.toLowerCase().replace(" ", ".") + "@email.com");
                    a.setTelefoneAluno(String.format("%011d", (long) (rnd.nextDouble() * 1e11)));
                    a.setCpfAluno(String.format("%011d", (long) (rnd.nextDouble() * 1e11)));
                    a.setEnderecoAluno("Rua das Flores, " + (rnd.nextInt(900) + 100));
                    a.setCidadeAluno(cidades[rnd.nextInt(cidades.length)]);
                    alunoRepository.save(a);
                }
            }
        };
    }
}
