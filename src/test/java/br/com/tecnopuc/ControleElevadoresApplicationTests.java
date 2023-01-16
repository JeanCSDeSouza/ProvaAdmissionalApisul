package br.com.tecnopuc;

import br.com.tecnopuc.controller.ElevadorController;
import br.com.tecnopuc.servico.ElevadorService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringRunner.class)
@WebMvcTest(value= ElevadorController.class)
class ControleElevadoresApplicationTests {
	@MockBean
	private ElevadorService elevadorService;
	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}
	@Test
	public void dadoElevadores_quandoMinBuscaUtilizacaoAndares_entaoJsonEStatus200() throws Exception {
		given( elevadorService.andarMenosUtilizado() ).willReturn(Arrays.asList( 1,2,3 ) );
		mockMvc.perform( get( "/api/elevadores/andar/{max}", "min" )
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect( status().isOk())
				.andExpect( ( jsonPath( "$", is(Arrays.asList( 1,2,3 ) ) ) ) );
		verify( elevadorService, VerificationModeFactory.times(1) ).andarMenosUtilizado();
		reset( elevadorService );
	}
	@Test
	public void quandoMaxBuscaUtilizacaoAndares_entaoVazioEStatus501() throws Exception {
		mockMvc.perform( get( "/api/elevadores/andar/{max}", "max" )
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect( status().isNotImplemented())
				.andExpect( jsonPath("$").doesNotExist() );
		verify( elevadorService, VerificationModeFactory.times(0) ).andarMenosUtilizado();
		reset( elevadorService );
	}


}
