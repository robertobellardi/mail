package serveremail;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import javafx.application.Platform;
/**
 *
 * @author fedef
 */
public class ServerController extends Thread{
    private static final int num_thread=6;
    private static final String path="C://Lettura";
    private ServerEmailController sec;
    private ExecutorService exec;
    private boolean kill=false;
    public ServerController(ServerEmailController sec,ExecutorService exec){
        this.sec=sec;
        this.exec= exec;
    }
    public void run(){
        
        Platform.runLater(()->{
            sec.writeLog("Server --> Online");        
        });
        
        ObjectInputStream inStream = null;
        ObjectOutputStream outStream = null;
        Object obj=null;
        ServerSocket s;
        Runnable task=null;
        try {
            s = new ServerSocket(7654);
            Socket incoming=null;
            do{
                incoming=s.accept();
                inStream = new ObjectInputStream(incoming.getInputStream());
                outStream = new ObjectOutputStream(incoming.getOutputStream());
                task= (Runnable)new Manager(incoming,sec,inStream,outStream,path);
                exec.execute(task);
            }while(!kill);
        }catch (SocketException ex) {
            System.out.println("Errore socket"+ex.getStackTrace()[0].getLineNumber());
        } catch (IOException ex) {
            System.out.println("IOException"+ex.getStackTrace()[0].getLineNumber());
        } 
    } 
    public void kill(){
        kill=true;
    }
}
