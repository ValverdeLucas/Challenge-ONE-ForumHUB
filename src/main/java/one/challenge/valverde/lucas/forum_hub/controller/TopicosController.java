package one.challenge.valverde.lucas.forum_hub.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import one.challenge.valverde.lucas.forum_hub.domain.topico.*;
import one.challenge.valverde.lucas.forum_hub.domain.topico.dto.ListagemTopicosDTO;
import one.challenge.valverde.lucas.forum_hub.domain.topico.dto.TopicoDetalhadoDTO;
import one.challenge.valverde.lucas.forum_hub.domain.topico.dto.TopicoPostarDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("topicos")
@Tag(name = "Topicos Controller")
@SecurityRequirement(name = "bearer-key")
public class TopicosController {

    @Autowired
    private TopicoService topicoService;

    @Autowired
    private TopicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity registrarTopico(@RequestBody @Valid TopicoPostarDTO dados, UriComponentsBuilder uriBuilder) {

        var novoTopico = topicoService.postar(dados);
        System.out.println(novoTopico.getId());

        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(novoTopico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDetalhadoDTO(novoTopico));
    }

    @GetMapping
    public ResponseEntity<Page<ListagemTopicosDTO>> listarTodosOsTopicos(@PageableDefault(size = 10, sort = {"data"}) Pageable paginacao) {
        var pagina = repository.findAll(paginacao).map(ListagemTopicosDTO::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity listarTopicoEspecifico(@PathVariable Long id) {
        var topico = topicoService.listarTopicoEspecifico(id);
        return ResponseEntity.ok(new TopicoDetalhadoDTO(topico));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizarTopico(@RequestBody @Valid TopicoPostarDTO dados, @PathVariable Long id) {

        var topicoAtualizado = topicoService.atualizarTopico(dados, id);
        return ResponseEntity.ok(new TopicoDetalhadoDTO(topicoAtualizado));
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity exclusaoLogicaTopico(@PathVariable Long id) {
        topicoService.fecharTopico(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity deletarTopicoDoDB(@PathVariable Long id) {
        topicoService.deletarTopico(id);
        return ResponseEntity.noContent().build();
    }

}
