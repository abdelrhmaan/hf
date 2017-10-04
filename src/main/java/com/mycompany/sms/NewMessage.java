package com.mycompany.sms;

import java.sql.*;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
@Named(value = "SendMsg")
@RequestScoped
public class NewMessage {
    java.util.Date date ; 
    private String sender, reciver , body , subject ,msgDate ; 
    private PreparedStatement pstmt ;
    Connection  conn ;
    
    public NewMessage(){
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
    
    public void initDB(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn =  DriverManager.getConnection("jdbc:mysql://node4913-env-5224318.p4d.click/smsapp", "user", "uofypOtvwkPvHULq");
            //conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smsapp", "root", "");
            pstmt = conn.prepareStatement("insert into message(sender,"
            + "receiver,msgsubject,body,msgdate) "
            + " values(?, ?, ?, ?,?)") ;  
        }
        catch(Exception ex){
    
        }
    }
    
    public void send() throws SQLException {
        this.sender = SessionUtils.getUserName() ;
        msgDate = new java.util.Date().toString() ; 
        pstmt.setString(1, this.sender );
        pstmt.setString(2, this.reciver);
        pstmt.setString(3, this.subject);
        pstmt.setString(4, this.body);
        pstmt.setString(5, this.msgDate);
        pstmt.executeUpdate() ;
        conn.close();
    }
}
