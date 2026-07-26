package com.javanauta.usuario.infrastructure.entity.exceptions;

public class ResourceNotFoundExeption extends  RuntimeException{

   public ResourceNotFoundExeption(String mensagem){
       super(mensagem);
   }

   public ResourceNotFoundExeption(String mensagem, Throwable causa){
       super(mensagem,causa);
   }

}
