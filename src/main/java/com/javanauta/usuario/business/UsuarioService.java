package com.javanauta.usuario.business;


import com.javanauta.usuario.business.conversor.UsuarioConverter;
import com.javanauta.usuario.business.dto.UsuarioDTO;
import com.javanauta.usuario.infrastructure.entity.Usuario;
import com.javanauta.usuario.infrastructure.entity.exceptions.ConfictException;
import com.javanauta.usuario.infrastructure.entity.exceptions.ResourceNotFoundException;
import com.javanauta.usuario.infrastructure.repository.UsuarioRepository;
import com.javanauta.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private  final JwtUtil jwtUtil;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        try {
            emailJaCadastrado(usuarioDTO.getEmail());
            usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
            Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
            return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
        } catch (ConfictException e) {
            throw new ConfictException("Email já cadastrado", e.getCause());
        }
    }

    public void emailJaCadastrado(String email){
        try {
            if (emailExiste(email)) {
                throw new ConfictException("Email já cadastrado"+ email);
            }
        } catch (ConfictException e) {
            throw new ConfictException("Email já cadastrado", e.getCause());
        }
    }


    public boolean emailExiste(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario buscarUsuarioPorEmail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(
                ()-> new ResourceNotFoundException("Email não encontrado "+ email));
    }

    public void deletarUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario(String token,UsuarioDTO usuarioDTO){

        //Aqui buscamos o email do usuário através do token (tirar obrigatoridade do email)
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        //Busca os dados do usuário do banco de dados
        Usuario entity = usuarioRepository.findByEmail(email).orElseThrow(
                ()-> new ResourceNotFoundException("Email não localizado")
        );

        //Criptografia de senha
        usuarioDTO.setSenha(usuarioDTO.getSenha() != null ? passwordEncoder.encode(usuarioDTO.getSenha()) : null);

        //Mesclou os dados que recebemos na requisição DTO com os dados do banco de dados
        Usuario usuario = usuarioConverter.updateUsuario(usuarioDTO,entity);

        //Salvou os dados do usuário convertido e depois pegou o retorno e converteu para UsuarioDTO
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));

    }





}