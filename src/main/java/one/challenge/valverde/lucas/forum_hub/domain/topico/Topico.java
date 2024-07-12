package one.challenge.valverde.lucas.forum_hub.domain.topico;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import one.challenge.valverde.lucas.forum_hub.domain.topico.dto.TopicoPostarDTO;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensagem;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime data;

    @Enumerated(EnumType.STRING)
    private EstadoTopico estadoTopico;

    private String autor;
    private String curso;

    public Topico(TopicoPostarDTO dados) {
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.data = LocalDateTime.now();
        this.autor = dados.autor();
        this.curso = dados.curso();
        this.estadoTopico = EstadoTopico.ABERTO;
    }

    public void atualizar(TopicoPostarDTO dados) {
        this.titulo = dados.titulo();
        this.mensagem = dados.mensagem();
        this.data = LocalDateTime.now();
        this.autor = dados.autor();
        this.curso = dados.curso();
        this.estadoTopico = EstadoTopico.ABERTO;
    }

    public void excluir() {
        this.estadoTopico = EstadoTopico.FECHADO;
    }
}