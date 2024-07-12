package one.challenge.valverde.lucas.forum_hub.domain.topico;

import jakarta.validation.Valid;
import one.challenge.valverde.lucas.forum_hub.domain.topico.dto.TopicoPostarDTO;
import one.challenge.valverde.lucas.forum_hub.domain.topico.validacoes.IValidacoesTopicos;
import one.challenge.valverde.lucas.forum_hub.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository repository;

    @Autowired
    private List<IValidacoesTopicos> validador;

    public Topico postar(TopicoPostarDTO dados) {

        validador.forEach(v -> v.validar(dados));

        var topico = new Topico(dados);
        repository.save(topico);

        return topico;
    }

    public Topico listarTopicoEspecifico(Long id) {
        return validacaoIdExistente(id);
    }

    public Topico atualizarTopico(@RequestBody @Valid TopicoPostarDTO dados, @PathVariable Long id) {

        var topico = validacaoIdExistente(id);

        validador.forEach(v -> v.validar(dados));
        topico.atualizar(dados);

        return topico;

    }

    public void fecharTopico(@PathVariable Long id) {

        var topico = validacaoIdExistente(id);
        topico.excluir();

    }

    public void deletarTopico(@PathVariable Long id) {

        var topico = validacaoIdExistente(id);
        repository.deleteById(topico.getId());

    }

    public Topico validacaoIdExistente(Long id) {
        var topico = repository.existsById(id);
        var topicoEspecifico = repository.getReferenceById(id);

        if (topico) {
            return topicoEspecifico;
        } else {
            throw new ValidacaoException("Topico não localizado, por favor conferir se a ID está correta e tentar novamente!");
        }
    }

}
