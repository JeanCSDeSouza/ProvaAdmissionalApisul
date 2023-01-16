package br.com.tecnopuc.servico;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import br.com.tecnopuc.model.Elevador;

@Service
@Slf4j
public class ElevadorService implements IElevadorService {
    private final List<Elevador> elevadorList;
    private static final DecimalFormat formatador = new DecimalFormat("0.00");

    @Autowired
    public ElevadorService(List<Elevador> elevadorList) {
        this.elevadorList = elevadorList;
    }

    @Override
    public List<Integer> andarMenosUtilizado() {
        Map<Integer, Long> map = elevadorList.stream()
                .collect( Collectors.groupingBy( Elevador::getAndar,  Collectors.counting() ) );
        Optional<Map.Entry<Integer,Long>> minEntry = map.entrySet().stream()
                .min(Comparator.comparing( Map.Entry::getValue ) );
        return map.entrySet().stream()
                .filter( e -> Objects.equals(e.getValue(), minEntry.get().getValue()))
                .map(Map.Entry::getKey)
                .toList();
    }

    @Override
    public List<Character> elevadorMaisFrequentado() {
        return retornaMaximaOcorrencia( Elevador::getElevador );
    }

    @Override
    public List<Character> periodoMaiorFluxoElevadorMaisFrequentado() {
        Map<Character, Long> map = elevadorList.stream()
                .filter( e ->  elevadorMaisFrequentado().contains(e.getElevador()) )
                .collect( Collectors.groupingBy( Elevador::getTurno,  Collectors.counting() ) );
        return retornaComparacaoMaxima(map);
    }

    @Override
    public List<Character> elevadorMenosFrequentado() {
        Map<Character, Long> map = elevadorList.stream()
                .collect( Collectors.groupingBy( Elevador::getElevador,  Collectors.counting() ) );
        return retornaComparacaoMinima(map);
    }

    @Override
    public List<Character> periodoMenorFluxoElevadorMenosFrequentado() {
        Map<Character, Long> map = elevadorList.stream()
                .filter( e ->  elevadorMenosFrequentado().contains(e.getElevador()) )//e.getElevador().equals( elevadorMaisFrequentado() )
                .collect( Collectors.groupingBy( Elevador::getTurno,  Collectors.counting() ) );
        return retornaComparacaoMinima(map);
    }

    @Override
    public List<Character> periodoMaiorUtilizacaoConjuntoElevadores() {
       return retornaMaximaOcorrencia( Elevador::getTurno );
    }

    @Override
    public float percentualDeUsoElevadorA() {
        return percentualDeUso('A');
    }

    @Override
    public float percentualDeUsoElevadorB() {
        return percentualDeUso('B');
    }

    @Override
    public float percentualDeUsoElevadorC() {
        return percentualDeUso('C');
    }

    @Override
    public float percentualDeUsoElevadorD() {
        return percentualDeUso('D');
    }

    @Override
    public float percentualDeUsoElevadorE() {
        return percentualDeUso('E');
    }

    /**
     * Calcula o percentual de usos do Elevador passado como par&acirc;metro
     * @param   @Character que representa o Elevador
     * @return  @float percenrual de uso.
     */
    private float percentualDeUso(Character elevador){
        float contador = elevadorList.stream()
                .filter( elemento -> elemento.getElevador().equals(elevador) )
                .count();
        float percent = ( contador/elevadorList.size() );
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        formatador.setDecimalFormatSymbols(  decimalFormatSymbols);
        return Float.parseFloat( formatador.format(percent) );
    }
    private List<Character> retornaMaximaOcorrencia(Function<Elevador,Character> function){
        Map<Character, Long> map = elevadorList.stream()
                .collect( Collectors.groupingBy( function,  Collectors.counting() ) );
        return retornaComparacaoMaxima(map);

    }
    private List<Character> retornaComparacaoMaxima( Map<Character, Long> map  ){
        Optional<Map.Entry<Character,Long>> maxEntry = map.entrySet().stream()
                .max(Comparator.comparing( Map.Entry::getValue ) );
        return map.entrySet().stream()
                .filter( e -> Objects.equals(e.getValue(), maxEntry.get().getValue()))
                .map(Map.Entry::getKey)
                .toList();
    }
    private List<Character> retornaComparacaoMinima( Map<Character, Long> map  ){
        Optional<Map.Entry<Character,Long>> minEntry = map.entrySet().stream()
                .min(Comparator.comparing( Map.Entry::getValue ) );
        return map.entrySet().stream()
                .filter( e -> Objects.equals(e.getValue(), minEntry.get().getValue()))
                .map(Map.Entry::getKey)
                .toList();
    }
    public Map<String, Object > composicao(){
        Map<String, Object> composicao = new HashMap<String, Object>();
        composicao.put( "Andar menos utilizado: ", andarMenosUtilizado() );
        composicao.put( "Elevador mais utilizado: ", elevadorMaisFrequentado());
        composicao.put( "Período de maior fluxo do elevador mais utilizado: ", periodoMaiorFluxoElevadorMaisFrequentado() );
        composicao.put( "Elevador menos utilizado: ", elevadorMenosFrequentado() );
        composicao.put( "Período de menor fluxo do elevador menos utilizado: ", periodoMenorFluxoElevadorMenosFrequentado() );
        composicao.put( "Período de maior utilização dos elevadores: ", periodoMaiorUtilizacaoConjuntoElevadores() );
        composicao.put( "Porcentagem de uso do elevador A: ", percentualDeUsoElevadorA());
        composicao.put( "Porcentagem de uso do elevador B: ", percentualDeUsoElevadorB());
        composicao.put( "Porcentagem de uso do elevador C: ", percentualDeUsoElevadorC());
        composicao.put( "Porcentagem de uso do elevador D: ", percentualDeUsoElevadorD());
        composicao.put( "Porcentagem de uso do elevador E: ", percentualDeUsoElevadorE());
        return composicao;
    }
}
