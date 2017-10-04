/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sms;


import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;


/**
 *
 * @author Abaza
 */
//@Named(value = "DumbDB")
@ManagedBean(name="DumbDB")
@SessionScoped
public class DumbDB implements Serializable {
    private List<users> user; 
    private List<msg> msgs; 
    String msg;
    String msg2;
    Connection con;
    Statement st;
    ResultSet rs;
    public DumbDB() {
        DBConnect();
    }
    
    private void DBConnect() {
        try{
            user = new ArrayList<>();
            msgs = new ArrayList<>();
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://node4913-env-5224318.p4d.click/smsapp","user","uofypOtvwkPvHULq");
            //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/smsapp", "root", "");
            st = con.createStatement();
            msg = "Connected";
            String query = "select * from user";
             rs = st.executeQuery(query);
             msg="";
             while (rs.next()) {
                 String name = rs.getString("username");
                 String pass = rs.getString("userpassword");
                 String mail = rs.getString("mail");
                 String gender = rs.getString("gender");
                 String code = rs.getString("code");
                 user.add(new users(name,pass,mail,gender,code));
             }
             String query2 = "select * from message";
             rs = st.executeQuery(query2);
             msg2="";
             while (rs.next()) {
                 String sender = rs.getString("sender");
                 String receiver = rs.getString("receiver");
                 String msgSubject = rs.getString("msgsubject");
                 String MsgDate = rs.getString("msgdate");
                 String body = rs.getString("body");
                 msgs.add(new msg(msgSubject,sender,MsgDate,body,receiver));
             }
             con.close();
        }catch(Exception ex){
            msg = ex.toString();
        }
    }
    public List<users> getUsers(){
        return user ; 
    }
    public List<msg> getMsgs(){
        return msgs ; 
    }
}

