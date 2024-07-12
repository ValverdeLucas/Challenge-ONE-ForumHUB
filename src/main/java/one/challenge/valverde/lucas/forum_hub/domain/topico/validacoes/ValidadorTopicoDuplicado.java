package one.challenge.valverde.lucas.forum_hub.domain.topico.validacoes;

import one.challenge.valverde.lucas.forum_hub.domain.topico.dto.TopicoPostarDTO;
import one.challenge.valverde.lucas.forum_hub.domain.topico.TopicoRepository;
import one.challenge.valverde.lucas.forum_hub.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorTopicoDuplicado implements IValidacoesTopicos {

    @Autowired
    private TopicoRepository repository;

    public void validar(TopicoPostarDTO dados) {

        var tituloDuplicado = repository.existsByTituloContainingIgnoreCase(dados.titulo().toLowerCase());
        var mensagemDuplicado = repository.existsByMensagemContainingIgnoreCase(dados.mensagem().toLowerCase());

        if (tituloDuplicado && mensagemDuplicado) {
            throw new ValidacaoException("Tópico duplicado! Não é possível criar um novo tópico com titulo e mensagem já existentes!");
        }
    }
}
