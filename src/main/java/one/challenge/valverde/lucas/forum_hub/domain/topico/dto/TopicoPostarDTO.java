package one.challenge.valverde.lucas.forum_hub.domain.topico.dto;

import jakarta.validation.constraints.NotBlank;

public record TopicoPostarDTO(
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
