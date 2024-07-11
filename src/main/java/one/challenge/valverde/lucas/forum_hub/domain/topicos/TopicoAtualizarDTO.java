package one.challenge.valverde.lucas.forum_hub.domain.topicos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TopicoAtualizarDTO(@NotBlank
                                 String titulo,

                                 @NotBlank
                                 String mensagem,

                                 @NotBlank
                                 String autor,

                                 @NotBlank
                                 String curso) {

}
