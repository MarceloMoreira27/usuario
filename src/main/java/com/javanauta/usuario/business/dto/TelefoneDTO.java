package com.javanauta.usuario.business.dto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class TelefoneDTO {

    private Long id;
    private String numero;
    private String ddd;

}
