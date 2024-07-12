package one.challenge.valverde.lucas.forum_hub.domain.topico;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Boolean existsByTituloContainingIgnoreCase(String titulo);

    Boolean existsByMensagemContainingIgnoreCase(String mensagem);
}
