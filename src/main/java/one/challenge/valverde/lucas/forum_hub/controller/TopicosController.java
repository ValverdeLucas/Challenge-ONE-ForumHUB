package one.challenge.valverde.lucas.forum_hub.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import one.challenge.valverde.lucas.forum_hub.domain.topicos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity registrarTopico(@RequestBody @Valid TopicoDTO dados, UriComponentsBuilder uriBuilder) {

        var topico = new Topico(dados);
        repository.save(topico);

        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDetalhadoDTO(topico));

    }

    @GetMapping
    public ResponseEntity<Page<ListagemTopicosDTO>> listarTodosOsTopicos(@PageableDefault(size = 10, sort = {"data"}) Pageable paginacao) {
        var pagina = repository.findAll(paginacao).map(ListagemTopicosDTO::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity listarTopicoEspecifico(@PathVariable Long id) {
        var topico = repository.getReferenceById(id);
        return ResponseEntity.ok(new TopicoDetalhadoDTO(topico));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizarTopico(@RequestBody @Valid TopicoAtualizarDTO dados, @PathVariable Long id) {
        var topico = repository.getReferenceById(id);
        topico.atualizar(dados);
        return ResponseEntity.ok(new TopicoDetalhadoDTO(topico));
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity exclusaoLogicaTopico(@PathVariable Long id) {
        var topico = repository.getReferenceById(id);
        topico.excluir();
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity deletarTopicoDoDB(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();

    }

}
