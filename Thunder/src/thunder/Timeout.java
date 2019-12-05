package thunder;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.*;
import javafx.application.Platform;
/**
 *
 * @author itach
 */
public class Timeout extends Thread{
    private String Ip;
    private int Port;
    private String name;
    private EmailModel em;
    private Container_ c;
    
    public Timeout(String Ip,int Port,String name,EmailModel em,Container_ c){
        this.Ip=Ip;
        this.Port=Port;
        this.name=name;
        this.em=em;
        this.c=c;
    }

    public void run(){
        int i=1;
        Listner l=null;
        Socket s=null;
        boolean first=true;
        while(i>=0){
            i=i*3;

            while(s==null){ 
                try { 
                    s=new Socket(Ip,Port);
                } catch (IOException ex) { 
                    System.out.println("Client --> Server offline");
                    Platform.runLater(()->{
                        c.getSc().writeLabel("Server offline");
                        c.getEwc().disableDelete();
                    });               
                    c.getWc().setOffline(true);                    
                } 
                if(s==null){
                    try {
                        System.out.println("Client --> Waiting 5 seconds");
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException ex) {
                        System.out.println("Error Timer Timeout.java "+ex.getStackTrace()[0].getLineNumber());
                    }
                }
            }
            Platform.runLater(()->{
                 c.getEwc().enableDelete();
                    });
            c.getWc().setOffline(false);
            if(!c.getWc().getErrore()){
                Platform.runLater(()->{c.getSc().writeLabel("");
                });
            }
            if(first){
                l=new Listner(em,s,name,0,c);
                l.start();
                first=false;
            }else {
                l=new Listner(em,s,name,1,c); 
                l.start();
            }
            try {
                TimeUnit.SECONDS.sleep(15);
                System.out.println("Client --> Waiting 15 seconds");
            } catch (InterruptedException ex) {
                System.out.println("Error Timer Timeout.java "+ex.getStackTrace()[0].getLineNumber());
            }            
            s=null;
            i--;
        }
    }
}
