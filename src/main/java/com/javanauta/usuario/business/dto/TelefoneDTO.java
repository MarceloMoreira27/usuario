package com.javanauta.usuario.business.dto;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TelefoneDTO {

    private Long id;
    private  String ddd;
    private String numero;
    private Long usuario_id;

}
