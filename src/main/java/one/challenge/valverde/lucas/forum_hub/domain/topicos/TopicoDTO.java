package one.challenge.valverde.lucas.forum_hub.domain.topicos;

import jakarta.validation.constraints.NotBlank;
import one.challenge.valverde.lucas.forum_hub.domain.usuario.Usuario;

public record TopicoDTO(
        @NotBlank
        String titulo,

        @NotBlank
        String mensagem,

        @NotBlank
        String autor,

        @NotBlank
        String curso
) {
}
