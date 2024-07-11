package one.challenge.valverde.lucas.forum_hub.domain.topicos;

import com.fasterxml.jackson.annotation.JsonFormat;
import one.challenge.valverde.lucas.forum_hub.domain.usuario.Usuario;

import java.time.LocalDateTime;

public record TopicoDetalhadoDTO(
        Long id,
        String titulo,
        String mensagem,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime data,
        EstadoTopico estadoTopico,
        String autor,
        String curso
) {

    public TopicoDetalhadoDTO(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getData(), topico.getEstadoTopico(), topico.getAutor(), topico.getCurso());
    }
}
