package com.javanauta.usuario.infrastructure.business;


import com.javanauta.usuario.infrastructure.business.conversor.UsuarioConverter;
import com.javanauta.usuario.infrastructure.business.dto.UsuarioDTO;
import com.javanauta.usuario.infrastructure.entity.Usuario;
import com.javanauta.usuario.infrastructure.entity.exceptions.ConfictException;
import com.javanauta.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        try {
            Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
            emailExiste(usuario.getEmail());
            return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
        } catch (ConfictException e) {
            throw new ConfictException("Email já cadastrado", e.getCause());
        }
    }

    public boolean emailExiste(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public void emailJaExiste(String email){
        try {
            if (emailExiste(email)) {
                throw new ConfictException("Email já cadastrado"+ email);
            }
        } catch (ConfictException e) {
            throw new ConfictException("Email já cadastrado", e.getCause());
        }
    }







}