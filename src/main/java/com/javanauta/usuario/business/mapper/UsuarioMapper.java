package com.javanauta.usuario.business.mapper;

import com.javanauta.usuario.business.dto.EnderecoDTO;
import com.javanauta.usuario.business.dto.TelefoneDTO;
import com.javanauta.usuario.business.dto.UsuarioDTO;
import com.javanauta.usuario.infrastructure.entity.Endereco;
import com.javanauta.usuario.infrastructure.entity.Telefone;
import com.javanauta.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class UsuarioMapper {

    public Usuario paraUsuario(UsuarioDTO usuarioDTO){
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEndereco(usuarioDTO.getEnderecos()))
                .telefones(paraListaTelefone(usuarioDTO.getTelefones()))
                .build();
    }

    public List<Endereco>paraListaEndereco(List<EnderecoDTO>enderecoDTOS){
        return enderecoDTOS.stream().map(this::paraEndereco).toList();
    }


    public Endereco paraEndereco(EnderecoDTO enderecoDTO){
        return Endereco.builder()
                .id(enderecoDTO.getId())
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .complemento(enderecoDTO.getComplemento())
                .cidade(enderecoDTO.getCidade())
                .estado(enderecoDTO.getEstado())
                .cep(enderecoDTO.getCep())
                .usuario_id(enderecoDTO.getUsuario_id())
                .build();
    }

    public List<Telefone>paraListaTelefone(List<TelefoneDTO> telefoneDTOS){
        List<Telefone> lista = new ArrayList<>();
        for (TelefoneDTO telefoneDTO : telefoneDTOS){
            lista.add(paraTelefone(telefoneDTO));
        }
        return lista;
    }

    public Telefone paraTelefone(TelefoneDTO telefoneDTO){
        return Telefone.builder()
                .usuario_id(telefoneDTO.getUsuario_id())
                .ddd(telefoneDTO.getDdd())
                .numero(telefoneDTO.getNumero())
                .build();
    }

    //Para Dtos
    public UsuarioDTO paraUsuarioDTO(Usuario usuario){
        return UsuarioDTO.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .enderecos(paraListaEnderecoDTO(usuario.getEnderecos()))
                .telefones(paraListaTelefoneDTO(usuario.getTelefones()))
                .build();
    }

    public List<EnderecoDTO>paraListaEnderecoDTO(List<Endereco>enderecos){
        return enderecos.stream().map(this::paraEnderecoDTO).toList();
    }

    public EnderecoDTO paraEnderecoDTO(Endereco endereco){
        return EnderecoDTO.builder()
                .id(endereco.getId())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento())
                .cidade(endereco.getCidade())
                .estado(endereco.getEstado())
                .cep(endereco.getCep())
                .usuario_id(endereco.getUsuario_id())
                .build();
    }

    public List<TelefoneDTO>paraListaTelefoneDTO(List<Telefone> telefones){
        List<TelefoneDTO> lista = new ArrayList<>();
        for (Telefone telefone: telefones){
            lista.add(paraTelefoneDTO(telefone));
        }
        return lista;
    }

    public TelefoneDTO paraTelefoneDTO(Telefone telefone){
        return TelefoneDTO.builder()
                .usuario_id(telefone.getUsuario_id())
                .ddd(telefone.getDdd())
                .numero(telefone.getNumero())
                .build();
    }

}
