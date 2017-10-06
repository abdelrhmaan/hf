package com.mycompany.sms;

import java.sql.*;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
@Named(value = "SendMsg")
@RequestScoped
public class NewMessage {
    java.util.Date date ; 
    private String sender, reciver , body , subject ,msgDate ;
    private String senderEnc, reciverEnc , bodyEnc , subjectEnc ,msgDateEnc ;
    private PreparedStatement pstmt ;
    Connection  conn ;
    
    public NewMessage() throws SQLException, ClassNotFoundException{
        initDB();
    }
    public void setSender(String sender){
        this.sender = sender ; 
    }
    public String getsender(){
        return this.sender ;
    }
    public void setReciver(String reciver){
        this.reciver = reciver ; 
    }
    public String getReciver(){
        return this.reciver ;
    }
    public void setBody(String body){
        this.body =  body ; 
    }
    public String getBody(){
        return this.body ;
    }
    public void setSubject(String subject){
        this.subject=  subject ; 
    }
    public String getSubject(){
        return this.subject;
    }
    
    public void initDB() throws SQLException, ClassNotFoundException{
            Class.forName("com.mysql.jdbc.Driver");
            conn =  DriverManager.getConnection("jdbc:mysql://node4913-env-5224318.p4d.click/smsapp", "user", "uofypOtvwkPvHULq");
            pstmt = conn.prepareStatement("insert into message(sender,"
            + "receiver,msgsubject,body,msgdate) "
            + " values(?, ?, ?, ?,?)") ;  
    }
    
    public void send() throws SQLException, Exception {
        this.sender = SessionUtils.getUserName();
        msgDate = new java.util.Date().toString();
        
        senderEnc=Encryption.encrypt(this.sender);
        reciverEnc=Encryption.encrypt(this.reciver);
        bodyEnc=Encryption.encrypt(this.subject);
        subjectEnc=Encryption.encrypt(this.body);
        msgDateEnc=Encryption.encrypt(this.msgDate);
        
        pstmt.setString(1,senderEnc );
        pstmt.setString(2,reciverEnc);
        pstmt.setString(3,bodyEnc);
        pstmt.setString(4,subjectEnc);
        pstmt.setString(5,msgDateEnc);
        pstmt.executeUpdate();
        //conn.close();
    }
}
