package thunder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import java.util.*;
import common.Email;
import java.io.IOException;
import java.net.Socket;
/**
 *
 * @author fedef
 */
public class WAreaController {
      
    @FXML
    private Button sndButton;
    @FXML
    private Button close;
    @FXML
    private TextField receiverEmail;
    @FXML
    private TextField objectField;
    @FXML
    private TextArea emailField;
    @FXML
    private void handleButtonAction(ActionEvent event) {
        
        Button obj=(Button)event.getSource();
        if(obj.getId().equals(sndButton.getId())){
            if(!offline){
                Date d=new Date();
                email.setDate(d.toString());
                String []vett=d.toString().split(" ");
                String tim=vett[2]+"-"+month(vett[1])+"-"+vett[5]+" "+vett[3];
                email.setSender(name);
                email.setDate(tim);
                Listner l=null; 
                try {
                    l = new Listner(email,new Socket(Ip,Port),name,2,cont);
                    l.start();
                } catch (IOException ex) {
                    System.out.println("Error sender Button WareaController.java "+ex.getStackTrace()[0].getLineNumber());
                }
            }
        }
        else{
            if(obj.getId().equals(close.getId())){
                closePanel();
            }
        }
        ev.enableButton();
        sc.enabledWrite();
    }
    private String name;
    private Email email;
    private EmailModel model;
    private String Ip;
    private int Port;
    private SelectorController sc;
    private EmailViewerController ev;
    private Container_ cont;
    private Boolean errore=false;
    private boolean offline=false;
    private boolean open=false;
    public void init(Container_ cont){
        this.cont=cont;
        model=cont.getEm();  
        this.Ip=cont.getIp();
        this.Port=cont.getPort();
    
    }
    
    public void makeEmail(){
        open=true;
        email=new Email();
        objectField.textProperty().bindBidirectional(email.argumentProperty());
        receiverEmail.textProperty().bindBidirectional(email.receiverProperty());
        emailField.textProperty().bindBidirectional(email.textProperty());
    }
    
    public void makeEmail(Email email){
        open=true;
        this.email=new Email();
        this.email.setReceiver(email.getSender());
        objectField.textProperty().bindBidirectional(this.email.argumentProperty());
        receiverEmail.textProperty().bindBidirectional(this.email.receiverProperty());
        emailField.textProperty().bindBidirectional(this.email.textProperty());
    }
    
    public void makeMessage(){
        this.sc=cont.getSc();
        this.ev=cont.getEwc();
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setName(String name){
        this.name=name;
    }
    
    public void unBind(){
        objectField.textProperty().unbind();
        receiverEmail.textProperty().unbind();
        emailField.textProperty().unbind();

    }
    
    public void clearFields(){
        objectField.setText("");
        receiverEmail.setText("");
        emailField.setText("");
    }
    
    public void setErrore(Boolean er){
          errore=er;     
    }
    public void setOffline(boolean off){
        offline=off;
    }
    public boolean getOffline(){
        return offline;
    }
    public boolean getErrore(){
        return errore;
    }
    public void closePanel(){
        if(open){
            unBind();
            if(ev.getpressreplay()){
                ev.setpressreplay(false);
                    ev.close();
            }else{
                if(ev.getpressreplayall()){
                ev.setpressreplayall(false);
                    ev.close();
                }
                else{
                    sc.close();
                }
            }
            sc.writeLabel("");
            open=false;
            errore=false;
        }
    }
     public static String month(String m){
        if(m.equals("Jen"))
            return "1";
        else{
            if(m.equals("Feb"))
                return "2";
            else{
                if(m.equals("Mar"))
                    return "3";
                else{
                    if(m.equals("Apr"))
                        return "4";
                    else{
                        if(m.equals("May"))
                            return "5";
                        else{
                            if(m.equals("Jun"))
                                return "6";
                            else{
                                if(m.equals("Jul"))
                                    return "7";
                                else{
                                    if(m.equals("Aug"))
                                        return "8";
                                    else{
                                        if(m.equals("Sep"))
                                            return "9";
                                        else{
                                            if(m.equals("Oct"))
                                                return "10";
                                            else{
                                                if(m.equals("Nov"))
                                                    return "11";
                                                else{
                                                    return "12";
                                                }
                                            }
                                        }
                                    }
                                }             
                            }              
                        }             
                    }             
                }
            }           
        }    
    }   
}