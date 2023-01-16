package br.com.tecnopuc;

import br.com.tecnopuc.servico.ElevadorService;
import br.com.tecnopuc.model.Elevador;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main( String... args ){

        ElevadorService elevadorService = new ElevadorService(jsonToElevadorLista());
        elevadorService.composicao().forEach(
                (k,v) -> System.out.println(k + v)
        );
    }
    public static ArrayList<Elevador> jsonToElevadorLista(){
        File json = new File("src/main/resources/static/input.json");
        InputStream jsonFile = null;
        try {
            jsonFile = new FileInputStream(json);
        } catch (FileNotFoundException e) {
            e.getMessage();
        }
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Elevador> elevadores = null;
        try {
            elevadores = mapper.readValue( jsonFile, mapper.getTypeFactory().constructCollectionType( List.class, Elevador.class ) );
        } catch (IOException e) {
            e.getMessage();
        }
        return elevadores;
    }
}
