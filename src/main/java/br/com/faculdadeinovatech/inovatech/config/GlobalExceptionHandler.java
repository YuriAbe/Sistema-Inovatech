package br.com.faculdadeinovatech.inovatech.config;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrityViolation(DataIntegrityViolationException ex, 
                                                RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("erro", 
            "Não foi possível completar a operação. O registro pode estar vinculado a outros dados.");
        return "redirect:/home";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException ex, 
                                          RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("erro", 
            "Ocorreu um erro inesperado: " + ex.getMessage());
        return "redirect:/home";
    }
}
