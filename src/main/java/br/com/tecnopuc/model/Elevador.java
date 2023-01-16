package br.com.tecnopuc.model;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Elevador {
    @Getter
    private Integer andar;
    @Getter
    private Character elevador;
    @Getter
    private Character turno;



}
