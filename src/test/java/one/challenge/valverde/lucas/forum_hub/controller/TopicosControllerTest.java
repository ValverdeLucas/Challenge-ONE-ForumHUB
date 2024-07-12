package one.challenge.valverde.lucas.forum_hub.controller;

import one.challenge.valverde.lucas.forum_hub.domain.topico.EstadoTopico;
import one.challenge.valverde.lucas.forum_hub.domain.topico.Topico;
import one.challenge.valverde.lucas.forum_hub.domain.topico.TopicoRepository;
import one.challenge.valverde.lucas.forum_hub.domain.topico.TopicoService;
import one.challenge.valverde.lucas.forum_hub.domain.topico.dto.TopicoDetalhadoDTO;
import one.challenge.valverde.lucas.forum_hub.domain.topico.dto.TopicoPostarDTO;
import one.challenge.valverde.lucas.forum_hub.infra.exception.ValidacaoException;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@WithMockUser
class TopicosControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<TopicoPostarDTO> dadosPostarJson;

    @Autowired
    private JacksonTester<TopicoDetalhadoDTO> dadosDetalhesJson;

    @MockBean
    private TopicoService topicoService;

    @MockBean
    private TopicoRepository repository;

    @Test
    @DisplayName("Deveria retornar 400 por não enviar nenhuma informação")
    void registrarTopicoCenario1() throws Exception {

        var response = mvc.perform(post("/topicos")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria retornar 200 por enviar as informações corretas")
    void registrarTopicoCenario2() throws Exception {

        var dadosTopico = new TopicoPostarDTO(
                "Esse e um titulo teste",
                "Essa e a mensagem de teste",
                "Autor Teste",
                "Curso teste");

        var topico = new Topico(dadosTopico);

        var topicoDetalhado = new TopicoDetalhadoDTO(topico);

        when(topicoService.postar(any())).thenReturn(topico);

        var response = mvc.perform(post("/topicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosPostarJson.write(dadosTopico).getJson()))
                .andReturn().getResponse();


        var jsonEsperado = dadosDetalhesJson.write(
                topicoDetalhado).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);

    }

    @Test
    @DisplayName("Deveria retornar 400 por não enviar a ID correta do tópico")
    void listarTopicoEspecificoCenario1() throws Exception {

        when(topicoService.listarTopicoEspecifico(anyLong())).thenThrow(new ValidacaoException("Topico não localizado, por favor conferir se a ID está correta e tentar novamente!"));

        var response = mvc.perform(get("/topicos/{id}", 1234L)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria retornar 200 por enviar a ID correta do tópico")
    void listarTopicoEspecificoCenario2() throws Exception {

        var dadosTopico = new TopicoPostarDTO(
                "Esse e um titulo teste",
                "Essa e a mensagem de teste",
                "Autor Teste",
                "Curso teste");

        var topico = new Topico(dadosTopico);

        var topicoDetalhado = new TopicoDetalhadoDTO(topico);

        when(topicoService.listarTopicoEspecifico(anyLong())).thenReturn(topico);

        var response = mvc.perform(get("/topicos/{id}", 1L)).andReturn().getResponse();

        var jsonEsperado = dadosDetalhesJson.write(
                topicoDetalhado).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    @Test
    @DisplayName("Deveria retornar 400 por não enviar a ID correta do tópico para atualizar")
    void atualizarTopicoCenario1() throws Exception {

        when(topicoService.listarTopicoEspecifico(anyLong())).thenThrow(new ValidacaoException("Topico não localizado, por favor conferir se a ID está correta e tentar novamente!"));

        var response = mvc.perform(put("/topicos/{id}", 1234L)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria retornar 500 ao enviar a ID correta do tópico, mas os dados incompletos para atualizar")
    void atualizarTopicoCenario2() throws Exception {

        var dadosTopico = new TopicoPostarDTO(
                "",
                "Essa e a mensagem de teste",
                "Autor Teste",
                "Curso teste");

        when(topicoService.listarTopicoEspecifico(anyLong())).thenThrow(new ValidacaoException("Topico não localizado, por favor conferir se a ID está correta e tentar novamente!"));

        var response = mvc.perform(put("/topicos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosPostarJson.write(dadosTopico).getJson())).
                andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

//    @Test
//    @DisplayName("Deveria retornar 400 ao enviar a ID correta do tópico, mas os dados duplicados para atualizar")
//    void atualizarTopicoCenario3() throws Exception {
//
//        when(topicoService.listarTopicoEspecifico(anyLong())).thenThrow(new ValidacaoException("Topico não localizado, por favor conferir se a ID está correta e tentar novamente!"));
//
//        var response = mvc.perform(put("/topicos/{id}", 1234L)).andReturn().getResponse();
//
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
//    }
//
//    @Test
//    @DisplayName("Deveria retornar 200 por enviar a ID correta do tópico e os dados estão corretos e não duplicados")
//    void atualizarTopicoCenario4() throws Exception {
//
//        var dadosTopico = new TopicoPostarDTO(
//                "Esse e um titulo teste",
//                "Essa e a mensagem de teste",
//                "Autor Teste",
//                "Curso teste");
//
//        var topico = new Topico(dadosTopico);
//
//        var topicoDetalhado = new TopicoDetalhadoDTO(topico);
//
//        when(topicoService.listarTopicoEspecifico(anyLong())).thenReturn(topico);
//
//        var response = mvc.perform(put("/topicos/{id}", 1L)).andReturn().getResponse();
//
//        var jsonEsperado = dadosDetalhesJson.write(
//                topicoDetalhado).getJson();
//
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
//    }

}
