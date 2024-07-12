package one.challenge.valverde.lucas.forum_hub.domain.topico.validacoes;

import one.challenge.valverde.lucas.forum_hub.domain.topico.dto.TopicoPostarDTO;

public interface IValidacoesTopicos {
    void validar(TopicoPostarDTO dados);
}
