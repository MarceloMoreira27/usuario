package com.javanauta.usuario.business;

import com.javanauta.usuario.business.dto.UsuarioDTO;
import com.javanauta.usuario.business.mapper.UsuarioMapper;
import com.javanauta.usuario.infrastructure.entity.Usuario;
import com.javanauta.usuario.infrastructure.entity.exceptions.ConflictException;
import com.javanauta.usuario.infrastructure.entity.exceptions.ResourceNotFoundExeption;
import com.javanauta.usuario.infrastructure.repository.UsuarioRepository;
import com.javanauta.usuario.infrastructure.security.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){

        emailJaCadastrado(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));

        Usuario usuario = mapper.paraUsuario(usuarioDTO);
        usuarioRepository.save(usuario);
        return mapper.paraUsuarioDTO(usuario);

    }


    public boolean emailExiste(String email){
        return usuarioRepository.existsByEmail(email);

    }

    public void emailJaCadastrado(String email) {
        try {

            if ( emailExiste(email)) {
                   throw new ConflictException(" Erro de duplicidade no e-mail: " + email);
            }

        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado: "+ email,e.getCause());
        }
    }

    public UsuarioDTO buscarUsuarioPorEmail(String email){
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                ()->new ResourceNotFoundExeption("Usuario não ecnontrado: "+ email));

        return mapper.paraUsuarioDTO(usuario);
    }

    @Transactional
    public void deletarUsurioPorEmail(String email){
        if(!emailExiste(email)){
            throw new ResourceNotFoundExeption("Usuario não ecnontrado: "+ email);
        }
        usuarioRepository.deleteByEmail(email);
    }



}
