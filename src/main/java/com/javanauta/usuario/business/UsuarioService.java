package com.javanauta.usuario.business;

import com.javanauta.usuario.business.dto.EnderecoDTO;
import com.javanauta.usuario.business.dto.TelefoneDTO;
import com.javanauta.usuario.business.dto.UsuarioDTO;
import com.javanauta.usuario.business.mapper.UsuarioMapper;
import com.javanauta.usuario.infrastructure.entity.Endereco;
import com.javanauta.usuario.infrastructure.entity.Telefone;
import com.javanauta.usuario.infrastructure.entity.Usuario;
import com.javanauta.usuario.infrastructure.entity.exceptions.ConflictException;
import com.javanauta.usuario.infrastructure.entity.exceptions.ResourceNotFoundException;
import com.javanauta.usuario.infrastructure.repository.EnderecoRepository;
import com.javanauta.usuario.infrastructure.repository.TelefoneRepository;
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
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

    @Transactional
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
                   throw new ConflictException("Erro de duplicidade no e-mail: " + email);
            }

        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado: "+ email,e.getCause());
        }
    }


    public UsuarioDTO buscarUsuarioPorEmail(String email){
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                ()->new ResourceNotFoundException("Usuario não ecnontrado: "+ email));

        return mapper.paraUsuarioDTO(usuario);
    }

    @Transactional
    public void deletarUsurioPorEmail(String email){
        if(!emailExiste(email)){
            throw new ResourceNotFoundException("Usuario não ecnontrado: "+ email);
        }
        usuarioRepository.deleteByEmail(email);
    }


    @Transactional
    public UsuarioDTO atualizarUsuario(UsuarioDTO usuarioDTO,String token){
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        Usuario entity = usuarioRepository.findByEmail(email).orElseThrow(
                ()-> new ResourceNotFoundException("Email não loacalizado! email: "+ email)
        );

        usuarioDTO.setSenha(usuarioDTO.getSenha() != null? passwordEncoder.encode(usuarioDTO.getSenha()) : null);

        Usuario usuario = mapper.updateUsuario(entity,usuarioDTO);
        return mapper.paraUsuarioDTO(usuarioRepository.save(usuario));
    }


    @Transactional
    public EnderecoDTO atualizarEndereco(Long id, EnderecoDTO enderecoDTO){

        Endereco entity = enderecoRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Id do endereço não encontrado!  id :" + id ));

        Endereco endereco = mapper.updateEndereco(entity,enderecoDTO);
        return mapper.paraEnderecoDTO( enderecoRepository.save(endereco));
    }


    @Transactional
    public TelefoneDTO atualizarTelefone(Long id, TelefoneDTO telefoneDTO){

        Telefone entity = telefoneRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Id do telefone não encontrado!  id :" + id ));

        Telefone telefone = mapper.updateTelefone(entity,telefoneDTO);
        return mapper.paraTelefoneDTO( telefoneRepository.save(telefone));
    }


    @Transactional
    public EnderecoDTO cadastraEndereco(EnderecoDTO dto ,String token){

        String email = jwtUtil.extrairEmailToken(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                ()-> new ResourceNotFoundException("Email não loacalizado! email: "+ email)
        );

       Endereco enderecoEntity = mapper.paraEnderecoEntity(dto, usuario.getId());
       return mapper.paraEnderecoDTO(enderecoRepository.save(enderecoEntity));
    }

    @Transactional
    public TelefoneDTO cadastraTelefone(TelefoneDTO  dto ,String token){

        String email = jwtUtil.extrairEmailToken(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                ()-> new ResourceNotFoundException("Email não loacalizado! email: "+ email)
        );

        Telefone telefoneEntity = mapper.paraTelefoneEntity(dto, usuario.getId());
        return mapper.paraTelefoneDTO(telefoneRepository.save(telefoneEntity));
    }







}
