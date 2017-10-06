/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sms;

/**
 *
 * @author Abaza
 */
public class users {
    private  String name ;
   private  String pass ; 
   private  String mail ; 
   private  String gender ;
   private  String code ;
   
   public users( String name ,String pass ,String mail ,String gender ,String code ){
       this.name = name ; 
       this.pass = pass ; 
       this.mail = mail ;
       this.gender = gender; 
       this.code = code; 
   }
   
   public String getName(){
       return this.name ; 
   }
   public String getPass(){
       return this.pass ; 
   }
   public String getMail(){
       return this.mail ; 
   }
   public String getGender(){
       return this.gender ; 
   }
   public String getCode(){
       return this.code ; 
   }
}