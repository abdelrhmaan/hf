
package com.mycompany.sms;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.*;
import javax.faces.context.FacesContext;

/**
 *
 * @author mohamed
 */
@Named(value = "inbox")
@SessionScoped
public class Inbox implements Serializable {
    //String to;
    String authUser ; 
    String name = SessionUtils.getUserName() ;
    private String err = "" ; 
    private List<msg> incomingMsgs ; 
    Connection  conn ;
    private PreparedStatement pstmt ;
    
    public Inbox() {
        initialize() ; 
    }
    
    private void initialize(){
        //to = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("hidden1");
        try {
            incomingMsgs = new ArrayList<>();
            Class.forName("com.mysql.jdbc.Driver");

            conn =  DriverManager.getConnection("jdbc:mysql://node4913-env-5224318.p4d.click/smsapp", "user", "uofypOtvwkPvHULq");
            //conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smsapp", "root", "");
            pstmt = conn.prepareStatement("select * from message where receiver = ? ");
            pstmt.setString(1 ,this.name);
            ResultSet Rs = pstmt.executeQuery() ; 
            while(Rs.next()){
                //incomingMsgs.add(new msg(Rs.getString("msgSubject"),Rs.getString("reciver"),Rs.getString("MsgDate"),Rs.getString("body")));
                incomingMsgs.add(new msg(Rs.getString("msgsubject"),Rs.getString("sender"),Rs.getString("msgdate"),Rs.getString("body")));
                
                
            }
            conn.close();
        }
        catch (Exception ex) {
            err = ex.toString();
        }
    }
    
    public List<msg> getIncomingMsgs(){
        return incomingMsgs ; 
    }
    
    public String getName(){
    return this.name ;
         }
    
    public void setName(String name){
        this.name = name ; 
    }
    
    public String getErr(){
        return this.err ; 
    }
     public String getAUN(){
        return this.name ; 
    }
}




