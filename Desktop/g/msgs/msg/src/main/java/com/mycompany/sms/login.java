package com.mycompany.sms;

import java.io.IOException;
import java.io.Serializable;
import javax.inject.Named;
import java.sql.*;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@Named(value = "login")
@SessionScoped
public class login implements Serializable {
    boolean auth = false ; 
    private String username,password,email,confirmationCode,newPassword,reNewPassword;
    String userEnc,passEnc,mailEnc,codeEnc,newpassEnc;
    Connection con;
    Statement st;
    ResultSet rs;
    String name;
    String pass;
    private String msg = "no",msg2="no",msg12="no",msg13="no";
    
    public login() {
        DBConnect();
    }
    
    private void DBConnect() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://node4913-env-5224318.p4d.click/smsapp", "user", "uofypOtvwkPvHULq");
            st = con.createStatement();
            msg = "Connected";
        }catch(Exception ex){
            msg = ex.toString();
        }
    }
    
    public void setUsername(String username){
        this.username = username ; 
    }
    public String getUsername(){
        return this.username ;
    }
    public void setPassword(String password){
        this.password =  password ; 
    }
    public String getPassword(){
        return this.password ;
    }
    public void setEmail(String email){
        this.email =  email ; 
    }
    public String getEmail(){
        return this.email ;
    }
    public void setConfirmationCode(String confirmationCode){
        this.confirmationCode =  confirmationCode ; 
    }
    public String getConfirmationCode(){
        return this.confirmationCode ;
    }
    public void setNewPassword(String newPassword){
        this.newPassword =  newPassword ; 
    }
    public String getNewPassword(){
        return this.newPassword ;
    }
    public void setReNewPassword(String reNewPassword){
        this.reNewPassword =  reNewPassword ; 
    }
    public String getReNewPassword(){
        return this.reNewPassword ;
    }
    /*
    public String Submit() throws SQLException, Exception{
        if(username != null || password != null){
            userEnc=Encryption.encrypt(username);
            passEnc=Encryption.encrypt(password);
            if(searchData(userEnc, passEnc)){
                con.close();
                return "faces/inbox.xhtml";
            }
        }
        return "";
    }
    */
    String mail1;
    public String sendConfirm() throws SQLException, Exception{
        if(email != null ){
            mailEnc=Encryption.encrypt(email);
            try {
                String query = "select * from user where mail like '"+mailEnc+"'";
                rs = st.executeQuery(query);
                if(rs.next()){
                    mail1 = Encryption.decrypt(rs.getString("mail"));
                    RandomCode code = new RandomCode();
                    String ActivationCode = code.nextString();
                    codeEnc=Encryption.encrypt(ActivationCode);
                    msg12 = "   ActivationCode : "+ActivationCode;
                    changeCode(mailEnc,codeEnc);
                    SendEmail SendEmail2 = new SendEmail("mymsgsproj@gmail.com", "mymsgs31395",mail1,"Confirmation Code",ActivationCode);
                    msg12 = "Done Sending Confirmation code : "+SendEmail2.msg+"   ActivationCode : "+ActivationCode;
                    return "faces/forget2.xhtml";
                }else{
                    msg12 = "Invalid email";
                }
                //con.close();
            }catch(Exception ex) {
                msg12 = ex.toString();
            }
            //return "faces/registered" ;
        }
        return "#"; 
    }
    public void changePassword() throws Exception {
        if(mail1 != null || confirmationCode != null || newPassword != null || reNewPassword != null){
            if(checkCode()){
                if(newPassword.equals(reNewPassword)){
                    try {
                        newpassEnc=Encryption.encrypt(newPassword);
                        String query = "update user set userpassword = '"+newpassEnc+"' where mail = '"+mailEnc+"'";
                        st.execute(query);
                        msg13="Done";
                        //con.close();
                    } catch(SQLException e) {
                        msg13 = e.toString();
                    }
                }else{
                    msg13 = "newPassword and reNewPassword don\'t match";
                }
            }else{
                msg13 = "invalid confirmation code";  
            }
        }else{
            msg13="Error : Empty Field";
        }
    }
    
    public void changeCode(String mail3 , String code3) throws Exception {
        try {
            String query = "update user set code = '"+code3+"' where mail = '"+mail3+"'";
            st.execute(query);
        } catch(SQLException e) {
            msg12 = e.toString();
        }
    }
    
    public boolean checkCode() {
        try {
             String query = "select * from user where mail like '"+mailEnc+"'";
             rs = st.executeQuery(query);
             if(rs.next()){
                 String Code3 = Encryption.decrypt(rs.getString("code"));
                 if(Code3.equals(confirmationCode)){
                     return true;
                 }
            }
         } catch(Exception ex) {
             msg = ex.toString();
        }
        return false;
    }
    
    public boolean searchData(String name1 , String password1) {
         try {
             String query = "select * from user where username like '"+name1+"'";
             rs = st.executeQuery(query);
             if(rs.next()){
                 name = rs.getString("username");
                 pass = rs.getString("userpassword");
                 if(pass.equals(password1)){
                     msg = "Done : " + "name = "+name+" , password = "+ pass;
                     auth = true ; 
                     return true;
                 }else{
                     msg = "Invalid password";
                 }
             }else{
                 msg = "Invalid username";
             }
         } catch(Exception ex) {
             msg = ex.toString();
          }
         return false;
    }
    
    public String validateUsernamePassword() throws Exception {
        if(username != null || password != null){
            userEnc=Encryption.encrypt(username);
            passEnc=Encryption.encrypt(password);
            boolean valid = searchData(userEnc, passEnc) ;
            if (valid) {
                HttpSession session = SessionUtils.getSession();
                session.setAttribute("username", username);
                return "inbox";
            } else {
                FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Incorrect Username and Passowrd",
							"Please enter correct username and Password"));
		return "login";
            }
        } else {
                FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Empty Username or Passowrd",
							"Please enter correct username and Password"));
		return "login";
        }
    }
    
    public String printMsg(){
        return this.msg ;
    }
    public String printMsg2(){
        return this.msg12 ;
    }
    
    public String printMsg3(){
        return this.msg13 ;
    }
    
    
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        auth = false ; 
        return "faces/index.xhtml";
    }
    
    public boolean getAuth(){
        return this.auth ; 
    }
    
    public void redirect() throws IOException{
        if(auth==true){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/faces/inbox.xhtml");
        }
    }
    
    public void restrict() throws IOException{
        if(auth==false){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/faces/index.xhtml");
        }
    }
    
}
