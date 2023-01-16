package br.com.tecnopuc.controller;

import br.com.tecnopuc.servico.IElevadorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/elevadores")
@Slf4j
public class ElevadorController {
    @Autowired
    private IElevadorService elevadorService;

    @GetMapping("/andar/{max}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Integer>> buscaUtilizacaoAndares(@PathVariable(value="max") String max){
        if ( max.equals("min") ) {
            return ResponseEntity.status(HttpStatus.OK).body(elevadorService.andarMenosUtilizado());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
        }
    }

    /**
     * Reorna a lista de Elevadores que é mais frequentado ou menos frequentado e status 200 OK dependendo do valor da
     * variável {max} que pode ser "max" ou "min". Caso este path seja chamado com outros valores para a variável {max}
     * responde com 501 (NOT_IMPLEMENTED)
     * @param max
     * @return Lista de caracteres dos elevadores
     */
    @GetMapping("/{max}")
    public ResponseEntity<List<Character>> buscaUtilizacaoElevador(@PathVariable(value="max") String max){
        if( max.equalsIgnoreCase("max")){
            return ResponseEntity.status(HttpStatus.OK).body(elevadorService.elevadorMaisFrequentado());
        } else if ( max.equalsIgnoreCase("min") ) {
            return ResponseEntity.status(HttpStatus.OK).body(elevadorService.elevadorMenosFrequentado());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
        }

    }

    /**
     * Retorna os períodos de maior fluxo dos elevadores.
     * @param maxElevador elevador mais ou menos
     * @param maxFluxo
     * @return Lista de caracteres repesentando os periodos de fluxo
     */
    @GetMapping("/{maxElevador}/fluxo/{maxFluxo}")
    public ResponseEntity<List<Character>> buccaPeriodoUtilizacaoElevador(@PathVariable(value="maxElevador") String maxElevador, @PathVariable(value="maxFluxo") String maxFluxo){
        if( maxElevador.equals("max") && maxFluxo.equals("max") ){
            return ResponseEntity.status(HttpStatus.OK).body( elevadorService.periodoMaiorFluxoElevadorMaisFrequentado() );
        } else if ( maxElevador.equals("min") && maxFluxo.equals("min") ) {
            return ResponseEntity.status(HttpStatus.OK).body( elevadorService.periodoMenorFluxoElevadorMenosFrequentado() );
        }else{
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(null);
        }
    }

    /**
     * Retorna os periodos de maiorutilização dos elevadores
     * @return Lista de caracteres dos
     */
    @GetMapping("/periodo/max")
    public ResponseEntity<List<Character>> buscaMaiorPeriodoUtilizacao(){
        return ResponseEntity.status(HttpStatus.OK).body( elevadorService.periodoMaiorUtilizacaoConjuntoElevadores() );
    }

    /**
     * Retorna o percentual de uso dos elevadores
     * @param nome
     * @return
     */
    @GetMapping("/uso/{nome}")
    public ResponseEntity<Float> buscaPercentualDeUso(@PathVariable(value="nome") Character nome){
        nome = Character.toUpperCase(nome);
        switch (nome) {
            case 'A': return ResponseEntity.status(HttpStatus.OK).body( elevadorService.percentualDeUsoElevadorA() );
            case 'B': return ResponseEntity.status(HttpStatus.OK).body( elevadorService.percentualDeUsoElevadorB() );
            case 'C': return ResponseEntity.status(HttpStatus.OK).body( elevadorService.percentualDeUsoElevadorC() );
            case 'D': return ResponseEntity.status(HttpStatus.OK).body( elevadorService.percentualDeUsoElevadorD() );
            case 'E': return ResponseEntity.status(HttpStatus.OK).body( elevadorService.percentualDeUsoElevadorE() );
            default  : return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body( null );
        }
    }
}
