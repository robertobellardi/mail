package common;
import java.util.*;
import java.io.Serializable;  
/**
 *
 * @author fedef
 */
public class Shell implements Serializable{
    private String header;
    private Email email;
    private String idDelete;
    private Boolean refresh;
    private String error;
    private Boolean disconnected;
    private ArrayList<Email> emaillist;
    public Shell(){
        header=null;
        email=null;
        idDelete=null;
        refresh=null;
        error=null;
        disconnected=null;
        emaillist=null;
    }
    public void setHeader(String header){
        this.header=header;
    }
    public String getHeader(){
        return this.header;
    }
    public String getError(){
        return error;
    }
    public void setError(String err){
        this.error= err;
    }
    public void setEmail(Email email){
        this.email=email;
    }
    public Email getEmail(){
        return this.email;
    }
    public boolean getRefresh(){
        return this.refresh;
    }
    public void setEmailList(ArrayList<Email> emaillist){
        this.emaillist=emaillist;
    }
    public ArrayList<Email> getEmailList(){
        return this.emaillist;
    }
    public void setDeleted(String del){
        this.idDelete=del;
    }
    public void setRefresh(Boolean ref){
        this.refresh=ref;
    }
    public String getDeleted(){
        return this.idDelete;
    }
    public void setDisconnected(boolean disconnected){
        this.disconnected=disconnected;
    }
    public boolean getDisconnected(){
        return disconnected;
    }
    public boolean isSetHeader(){
        return (this.header!=null);
    }
    public boolean isSetEmail(){
        return (this.email!=null);
    }
    public boolean isSetEmailList(){
        return (this.emaillist!=null);
    }
    public boolean isSetDelete(){
        return (this.idDelete!=null);
    }
    public boolean isSetRefresh(){
        return (this.refresh!=null);
    }
    public boolean isSetError(){
        return (this.error!=null);
    }
    public boolean isSetDisconnected(){
        return (this.disconnected!=null);
    }
}
