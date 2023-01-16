package br.com.tecnopuc.service;

import br.com.tecnopuc.model.Elevador;
import br.com.tecnopuc.servico.ElevadorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ElevadorServiceTest {
    @BeforeEach
    public void setup(){
        System.out.println("Inicio do teste");
    }

    /**
     * Testa retorno do método andarMenosUtilizado com o elevador A com 1 uso para andares
     * diferentes. Todos os três andares devem ser retornados como menos utilizados.
     */
    @Test
    public void testaAndarMenosUtilizadoCase1(){
        //given
        List<Elevador> elevadorList = Arrays.asList(
                Elevador.builder().elevador('A').andar(1).turno('M').build(),
                Elevador.builder().elevador('A').andar(2).turno('M').build(),
                Elevador.builder().elevador('A').andar(3).turno('M').build()
        );
        ElevadorService elevadorService = new ElevadorService(elevadorList);
        //when
        List<Integer> result = elevadorService.andarMenosUtilizado();
        //then
        assertEquals(Arrays.asList(1,2,3), result,  "Deve retornar verdadeiro");
    }

    /**
     * Testa o método andarMenosutilizado com o elevador A com diferença mínima(2/1) entre andares.
     */
    @Test
    public void testaAndarMenosUtilizadoCase2(){
        //given
        List<Elevador> elevadorList = Arrays.asList(
                Elevador.builder().elevador('A').andar(1).turno('M').build(),
                Elevador.builder().elevador('A').andar(2).turno('M').build(),
                Elevador.builder().elevador('A').andar(1).turno('M').build()
        );
        ElevadorService elevadorService = new ElevadorService(elevadorList);

        //when
        List<Integer> result = elevadorService.andarMenosUtilizado();
        //then
        assertEquals( Arrays.asList(2),result, "Deve retornar verdadeiro");
    }

    /**
     * Testa o método andarMenosUtilizado com lista vazia
     */
    @Test
    public void testaAndarMenosUtilizadoCase3(){
        //given
        List<Elevador> elevadorList = new ArrayList<>();
        ElevadorService elevadorService = new ElevadorService(elevadorList);

        //when
        List<Integer> result = elevadorService.andarMenosUtilizado();
        //then
        assertEquals( Arrays.asList(),result, "Deve retornar verdadeiro");
    }
    @Test
    public void testaPercentualDeUsoCaso1(){
        //given
        List<Elevador> elevadorList = Arrays.asList(
                Elevador.builder().elevador('A').andar(1).turno('M').build(),
                Elevador.builder().elevador('A').andar(2).turno('M').build(),
                Elevador.builder().elevador('A').andar(1).turno('M').build()
        );
        ElevadorService elevadorService = new ElevadorService(elevadorList);
        //when
        float result = elevadorService.percentualDeUsoElevadorA();

        DecimalFormat formatador = new DecimalFormat("0.00");
        float percent = ( 3.0f/ elevadorList.size() );
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        formatador.setDecimalFormatSymbols(  decimalFormatSymbols);
        //then
        assertEquals( Float.parseFloat( formatador.format(percent) ), result, "Deve retornar verdadeiro");
    }
    @Test
    public void testaPercentualDeUsoCaso2(){
        //given
        List<Elevador> elevadorList = Arrays.asList(
                Elevador.builder().elevador('A').andar(1).turno('M').build(),
                Elevador.builder().elevador('B').andar(2).turno('M').build(),
                Elevador.builder().elevador('A').andar(1).turno('M').build()
        );
        ElevadorService elevadorService = new ElevadorService(elevadorList);
        //when
        float resultA = elevadorService.percentualDeUsoElevadorA();
        float percentA = ( 2.0f/ elevadorList.size() );
        float resultB = elevadorService.percentualDeUsoElevadorB();
        float percentB = ( 1.0f/ elevadorList.size() );

        DecimalFormat formatador = new DecimalFormat("0.00");
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        formatador.setDecimalFormatSymbols(  decimalFormatSymbols);

        //then
        assertEquals( Float.parseFloat( formatador.format(percentA) ), resultA, "Deve retornar verdadeiro para A");
        assertEquals( Float.parseFloat( formatador.format(percentB) ), resultB, "Deve retornar verdadeiro para B");
        assertEquals( 1.0f, resultA+resultB, "Deve retornar verdeiro para a soma dos resultados " );
    }

}
