/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sms;

/**
 *
 * @author mohamed
 */

public class msg {
   private  String subject ;
   private  String from ; 
   private  String to ;
   private  String date ; 
   private  String body ;
   
   public msg( String subject ,String from ,String date ,String body ){
       this.subject = subject ; 
       this.from = from ; 
       this.date = date ;
       this.body = body; 
   }
   public msg( String subject ,String from ,String date ,String body ,String to){
       this.subject = subject ; 
       this.from = from ; 
       this.date = date ;
       this.body = body; 
       this.to = to ; 
   }
   
   public String getSubject(){
       return this.subject ; 
   }
   public String getFrom(){
       return this.from ; 
   }
   public String getTo(){
       return this.to ; 
   }
   public String getDate(){
       return this.date ; 
   }
   public String getBody(){
       return this.body ; 
   }

}
