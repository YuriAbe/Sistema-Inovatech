package br.com.faculdadeinovatech.inovatech.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.faculdadeinovatech.inovatech.entity.Categoria;
import br.com.faculdadeinovatech.inovatech.entity.Curso;
import br.com.faculdadeinovatech.inovatech.entity.Professor;
import br.com.faculdadeinovatech.inovatech.repository.CategoriaRepository;
import br.com.faculdadeinovatech.inovatech.repository.CursoRepository;
import br.com.faculdadeinovatech.inovatech.repository.ProfessorRepository;

/**
 * Converte o id enviado pelos <select> dos formulários na entidade gerenciada
 * correspondente. Sem isto o Spring não conseguiria vincular Aluno->Curso,
 * Disciplina->Curso/Professor e Produto->Categoria a partir do id.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final CursoRepository cursoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProfessorRepository professorRepository;

    public WebConfig(CursoRepository cursoRepository, CategoriaRepository categoriaRepository,
            ProfessorRepository professorRepository) {
        this.cursoRepository = cursoRepository;
        this.categoriaRepository = categoriaRepository;
        this.professorRepository = professorRepository;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(String.class, Curso.class,
                id -> id == null || id.isBlank() ? null : cursoRepository.findById(Integer.valueOf(id)).orElse(null));
        registry.addConverter(String.class, Categoria.class,
                id -> id == null || id.isBlank() ? null : categoriaRepository.findById(Integer.valueOf(id)).orElse(null));
        registry.addConverter(String.class, Professor.class,
                id -> id == null || id.isBlank() ? null : professorRepository.findById(Integer.valueOf(id)).orElse(null));
    }
}
