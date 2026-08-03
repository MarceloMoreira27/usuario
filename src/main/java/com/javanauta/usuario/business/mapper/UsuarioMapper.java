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
                //.id(enderecoDTO.getId())
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .complemento(enderecoDTO.getComplemento())
                .cidade(enderecoDTO.getCidade())
                .estado(enderecoDTO.getEstado())
                .cep(enderecoDTO.getCep())
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
                //.usuario_id(telefoneDTO.getUsuario_id())
                //.id(telefoneDTO.getId())
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
                .id(telefone.getId())
                .ddd(telefone.getDdd())
                .numero(telefone.getNumero())
                .build();
    }

    public Usuario updateUsuario(Usuario entity,UsuarioDTO dto){
        return Usuario.builder()
                .nome(dto.getNome()   != null? dto.getNome()  : entity.getNome())
                .email(dto.getEmail() != null? dto.getEmail() : entity.getEmail())
                .senha(dto.getSenha() != null? dto.getSenha() : entity.getSenha())
                .enderecos(entity.getEnderecos())
                .telefones(entity.getTelefones())
                .id(entity.getId())
                .build();
    }


    public Endereco updateEndereco(Endereco entity, EnderecoDTO dto) {
        return Endereco.builder()
                .id(entity.getId())
                .usuario_id(entity.getUsuario_id())
                .rua(dto.getRua() != null ? dto.getRua() : entity.getRua())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .complemento(dto.getComplemento() != null ? dto.getComplemento() : entity.getComplemento())
                .cidade(dto.getCidade() != null ? dto.getCidade() : entity.getCidade())
                .estado(dto.getEstado() != null ? dto.getEstado() : entity.getEstado())
                .cep(dto.getCep() != null ? dto.getCep() : entity.getCep())
                .build();
    }

    public Telefone updateTelefone(Telefone entity,TelefoneDTO dto){
        return Telefone.builder()
                .id(entity.getId())
                .usuario_id(entity.getUsuario_id())
                .ddd(dto.getDdd() != null ? dto.getDdd() : entity.getDdd())
                .numero(dto.getNumero() != null ? dto.getNumero() : entity.getNumero())
                .build();
    }

}
