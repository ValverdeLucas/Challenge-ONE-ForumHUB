package one.challenge.valverde.lucas.forum_hub.domain.topicos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ListagemTopicosDTO(
        String titulo,
        String mensagem,

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime data,
        EstadoTopico estadoTopico,
        String autor,
        String curso
) {
    public ListagemTopicosDTO(Topico topico) {
        this(topico.getTitulo(), topico.getMensagem(), topico.getData(), topico.getEstadoTopico(), topico.getAutor(), topico.getCurso());
    }
}
