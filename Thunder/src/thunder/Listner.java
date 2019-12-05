package thunder;

import common.Email;
import common.Shell;
import java.io.*;
import java.net.*;
import javafx.application.Platform;
/**
 *
 * @author itach
 */
public class Listner extends Thread{
    
    private Socket s;
    private EmailModel model;
    private String name;
    private int mode;
    private ObjectOutputStream out=null;
    private ObjectInputStream in=null;
    private Email e;
    private Container_ c;
    private boolean error= false;
    
    public Listner(EmailModel em,Socket socket,String n,int mode,Container_ c){  
                                                                    //mode==0 inizializzazione
                                                                    //mode==1 refresh
                                                                    //mode==2 attende risposta dal server
                                                                    //mode==3 manda richiesta di cancellazione
                                                                    //mode==4 chiudo il cliente e lo comunico al server

        model=em;
        s=socket;
        name=n;
        this.mode=mode;
        this.c=c;
    }
    
    public Listner(Email e,Socket socket,String n,int mode,Container_ c){        
                                                                    //mode==0 inizializzazione
                                                                    //mode==1 refresh
                                                                    //mode==2 attende risposta dal server
                                                                    //mode==3 manda richiesta di cancellazione
                                                                    //mode==4 chiudo il cliente e lo comunico al server

        s=socket;
        name=n;
        this.mode=mode;  
        this.e=e;
        this.c=c;
    }
     public Listner(Socket socket,String n,int mode,Container_ c){               
                                                                    //mode==0 inizializzazione
                                                                    //mode==1 refresh
                                                                    //mode==2 attende risposta dal server
                                                                    //mode==3 manda richiesta di cancellazione
                                                                    //mode==4 chiudo il cliente e lo comunico al server
        s=socket;
        name=n;
        this.mode=mode;
        this.c=c;
    }
     
    public void run() {  
        synchronized(this){
            if(mode==0&&s!=null){
                System.out.println("Client --> Connected to server");           
                firstContact();
                waitAnswer();
                try {
                    s.close();
                } catch (IOException ex) {
                    System.out.println("Error close socket firstContact Listner.java "+ex.getStackTrace()[0].getLineNumber());
                }
            }
            if(mode==1&&s!=null){
                refresh();
                waitAnswer();
            }
            if(mode==2&&s!=null){
                send();
                waitAnswer();        
            }
            if(mode==3&&s!=null){
                sendDelete();
                waitAnswer(); 
            }
            if(mode==4&&s!=null){
                sendClose();
                waitAnswer();
            }
        }
   }
    
    public void firstContact(){

        try {
            out=new ObjectOutputStream(s.getOutputStream());                
        } catch (IOException ex) {
            System.out.println("Error opening stream Out Listner.java "+ex.getStackTrace()[0].getLineNumber());
        }
        
        Shell shellout=new Shell();
                
        shellout.setHeader(name);       

        try {
            out.writeObject(shellout);
            out.flush();
            System.out.println("Client --> Send request first contact");

        } catch (IOException ex) {
            System.out.println("Error write Listner.java "+ex.getStackTrace()[0].getLineNumber());
        }    
    }
    
    public void refresh(){

        try {
            out=new ObjectOutputStream(s.getOutputStream());                
        } catch (IOException ex) {
            System.out.println("Error opening stream Out Listner.java 101");
        }
        
        Shell shellout=new Shell();
                
        shellout.setHeader(name);
        shellout.setRefresh(true);

        try {
            out.writeObject(shellout);
            out.flush();
            System.out.println("Client --> Send request refresh");

        } catch (IOException ex) {
            System.out.println("Error write Listner.java "+ex.getStackTrace()[0].getLineNumber());
        }   
    }
    
    public void send(){
                
        try {
             out=new ObjectOutputStream(s.getOutputStream());
        } catch (IOException ex) {
            System.out.println("Error send Listner.java "+ex.getStackTrace()[0].getLineNumber());
        }
        
        Shell shellout=new Shell();
        shellout.setHeader(name);
        shellout.setEmail(e);        

        try {
            out.writeObject(shellout);
            out.flush();
            System.out.println("Client --> Send conplete");

        } catch (IOException ex) {
            System.out.println("Error write Listner.java "+ex.getStackTrace()[0].getLineNumber());
        } 

    }
    
    public void sendClose(){ 
        
        try {
             out=new ObjectOutputStream(s.getOutputStream());
        } catch (IOException ex) {
            System.out.println("Error send Listner.java "+ex.getStackTrace()[0].getLineNumber());
        }
        
        
        Shell shellout=new Shell();
        shellout.setHeader(name);
        shellout.setDisconnected(true); 

        try {
            out.writeObject(shellout);
            out.flush();
            System.out.println("Client --> Send complete");

        } catch (IOException ex) {
            System.out.println("Error write Listner.java "+ex.getStackTrace()[0].getLineNumber());
        } 

    }
    
    public void waitAnswer(){
           
        try {
            in=new ObjectInputStream(s.getInputStream());
        } catch (IOException ex) {
            System.out.println("Error stream In Listner.java "+ex.getStackTrace()[0].getLineNumber());
        }
        
        Shell shellin=null;
        try {
            System.out.println("Client --> Waiting answer");
            shellin=(Shell)in.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Error read object stream Listner.java "+ex.getStackTrace()[0].getLineNumber());
            c.getSc().writeLabel("Client --> Server offline");
            System.out.println("Client --> Server offline");
        }

        if(shellin.isSetError()){

            if(!shellin.getError().equals("")){
                c.getSc().writeLabel(shellin.getError());
                c.getWc().setErrore(true);
            }else{
                c.getWc().setErrore(false);
                Platform.runLater(()->{
                    c.getWc().closePanel();
                });
            }
        }
        
        if(shellin.isSetEmail()){
            Email e=shellin.getEmail();
            System.out.println(e.getSender());
            model.addEmail(shellin.getEmail());
        }
        else{
            if(shellin.isSetEmailList())
                model.loadData(shellin.getEmailList());
            else{
                if(shellin.isSetDisconnected()){
                    System.out.println("Disconnected");
                }
            }
        }
        
        try {
            in.close();
            out.close();
            s.close();
        } catch (IOException ex) {
            System.out.println("Error close stream Input Listner.java "+ex.getStackTrace()[0].getLineNumber());
        }  
        System.out.println("Client --> Received answer");
    }
    
    public void sendDelete(){
        try {
             out=new ObjectOutputStream(s.getOutputStream());
        } catch (IOException ex) {
            System.out.println("Error send Listner.java "+ex.getStackTrace()[0].getLineNumber());
        }
        
        Shell shellout=new Shell();
        shellout.setHeader(name);
        shellout.setDeleted(e.getID());       

        try {
            out.writeObject(shellout);
            out.flush();
            System.out.println("Client --> Send request delete");
        } catch (IOException ex) {
            System.out.println("Error write Listner.java "+ex.getStackTrace()[0].getLineNumber());
        } 
    }
}