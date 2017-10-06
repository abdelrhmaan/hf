package com.mycompany.sms;

import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author mohamed
 */

@Named(value = "regAccount")
@RequestScoped
public class RegAccount {
    //String v,w,x,y,z,v2,w2,x2,y2,z2;
    String genderEnc;
    private int err =  0 ; 
    private String username,password,repassword,email = "",gender,code,msg = "no" ,
            UNE = "" ,
            PE = "" ,
            ME = "" ,
            PFE = "";
    String userEnc,passEnc,mailEnc,codeEnc;
    private PreparedStatement pstmt ;
    private PreparedStatement finduser , findMail ;
Connection  conn ;
public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
public static final Pattern VALID_USERNAME_REGEX = 
    Pattern.compile("^[a-z0-9_-]{3,15}$");

public static final Pattern VALID_PASSWORD_REGEX = 
    Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})");


    

    public RegAccount() {
        initializeJdbc();
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
    public void setRepassword(String repassword){
        this.repassword =  repassword ; 
    }
    public String getRepassword(){
        return this.repassword ;
    }
       public void setEmail(String mail){
        this.email = mail ; 
    }
    public String getEmail(){
        return this.email ;
    }
    
    public void setGender(String gender){
        this.gender = gender ;
        //genderEnc=Encryption.encrypt(gender);
    }
    public String getGender() {
        //genderEnc=Encryption.encrypt(gender);
        return this.gender ;
        
    }
    public String getUNE(){
        return this.UNE ; 
    }
    public String getPE(){
        return this.PE ; 
    }
    public String getME(){
        return this.ME ; 
    }
    public String getPFE(){
        return this.PFE ; 
    }
    public String printMsg(){
        return this.msg ; 
    }
    
    
    
    private void initializeJdbc() {       
        try {
            Class.forName("com.mysql.jdbc.Driver");
            msg = "connected" ; 
            conn =  DriverManager.getConnection("jdbc:mysql://node4913-env-5224318.p4d.click/smsapp", "user", "uofypOtvwkPvHULq");
            //conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/smsapp", "root", "");
            pstmt = conn.prepareStatement("insert into user(username,"
            + "userpassword,mail,gender,code) "
            + " values(?, ?, ?, ?, ?)") ;  
            finduser = conn.prepareStatement("select username from user where username = ?");
            findMail = conn.prepareStatement("select mail from user where mail = ?");
        }catch (Exception ex) {
            msg = ex.toString()+"sherif";
        }
    }
    
    public void storeUser() throws SQLException{
     try{
         pstmt.setString(1,userEnc);
         pstmt.setString(2,passEnc);
         pstmt.setString(3,mailEnc);
         pstmt.setString(4,gender);
         pstmt.setString(5,codeEnc);
         pstmt.executeUpdate();
         //conn.close();
     }catch (Exception ex) {
         msg= ex.toString();
     }
    }
    public String Submit() throws SQLException, Exception{
     if(username != null|| password != null|| email != null){
         RandomCode ConfirmCode = new RandomCode();
         code = ConfirmCode.nextString();
         userEnc=Encryption.encrypt(username);
         passEnc=Encryption.encrypt(password);
         mailEnc=Encryption.encrypt(email);
         codeEnc=Encryption.encrypt(code);
       if(!(isUnUsed(userEnc))){
          this.UNE = "This Username is already used" ; 
          err++ ; 
       }
       if(!isValidUsername(this.username)){
           this.UNE = "invalid username username sholudn't have spaces and only you can't use sympols except _ and numbers " ;
           err++ ;
       }
       if(!(validate(this.email))){
           this.ME = "InValid Mail" ; 
           err++ ; 
        }
       if(!(isUnUsedMail(mailEnc))){
          this.ME = "this mail is already used" ; 
          err++ ; 
       }
       /*
        if(!isValidPassword(this.password)){
            this.PFE = "Password should be at least 6 chars contains at least one upper case , onelower case and one number";
            err++ ; 
        }
       */
        if(!(this.password).equals(this.repassword)){
           this.PE = "Password feilds doesn't matches"   ;  
           err++ ; 
        }
        if(err > 0)
            return "" ;         
        storeUser();
        msg = "registered" ; 
        //return "faces/registered";
        return "faces/inbox.xhtml";
     }
     return "" ;     
    }
    
    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }
    private boolean  isUnUsed(String username) throws SQLException, Exception{
        finduser.setString(1,username);
        ResultSet R = finduser.executeQuery();
        while(R.next()){
            msg = R.getString("username");
            if(username.equals(R.getString("username")))
                return false ;
        }
        return true ;
    }
    private boolean  isUnUsedMail(String mail) throws SQLException, Exception{
        findMail.setString(1,mail);
        ResultSet R = findMail.executeQuery();
        while(R.next()){
            msg = R.getString("mail");
            if(mail.equals(R.getString("mail")))
                return false;
        }
        return true ; 
    }
    private boolean isValidUsername(String usernameStr){
        Matcher matcher = VALID_USERNAME_REGEX .matcher(usernameStr);
        return matcher.find();
    }  /*
     private boolean isValidPassword(String PASSREGX){
        Matcher matcher = VALID_PASSWORD_REGEX .matcher(PASSREGX);
        return matcher.find();
    }  */
}