package com.javanauta.usuario.infrastructure.entity.exceptions;

public class ResourceNotFoundException extends  RuntimeException{

   public ResourceNotFoundException(String mensagem){
       super(mensagem);
   }

   public ResourceNotFoundException(String mensagem, Throwable causa){
       super(mensagem,causa);
   }

}
