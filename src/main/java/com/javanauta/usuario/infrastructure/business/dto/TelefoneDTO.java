package com.javanauta.usuario.infrastructure.business.dto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class TelefoneDTO {

    private String numero;
    private String ddd;

}
