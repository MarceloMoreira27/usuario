package com.javanauta.usuario.infrastructure.business.dto;

import lombok.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class UsuarioDTO {



    private String nome;
    private String email;
    private String senha;
    List<EnderecoDTO> enderecos;
    List<TelefoneDTO>telefones;

}
