package br.com.tecnopuc;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.tecnopuc.model.Elevador;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Slf4j
public class ControleElevadoresApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControleElevadoresApplication.class, args);
	}

	/**
	 * Inicia arquivo input.json localizado no claspath da aplicação e faz o parse para
	 * a classe ELevador.
	 * @return Lista de elevadores
	 */
	@Bean
	public List<Elevador> elevadorList(){
		//Abre o arquivo no classpath
		File json = new File("src/main/resources/static/input.json");
		InputStream jsonFile = null;
		try {
			jsonFile = new FileInputStream(json);
		} catch (FileNotFoundException e) {
			log.info("Não foi possível ler o arquivo no classpath da aplicação");
		}
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<Elevador> elevadores = null;
		try {
			elevadores = mapper.readValue( jsonFile, mapper.getTypeFactory().constructCollectionType( List.class, Elevador.class ) );
		} catch (IOException e) {
			log.info("Não foi possível converter o conteúdo do arquivo input.json");
		}
		return elevadores;
	}
}
