package serveremail;
import java.io.ObjectInputStream;
import java.net.*;
import common.Email;
import common.Shell;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;
import javafx.application.Platform;
/**
 *
 * @author fedef
 */
public class Manager implements Runnable{
    private Socket sck;
    private String path;
    private ObjectInputStream stream;
    private ObjectOutputStream outstream;
    private ServerEmailController sec;
    
    public Manager(Socket sck,ServerEmailController sec,ObjectInputStream stream,ObjectOutputStream outstream,String path){
        this.sck=sck;
        this.sec=sec;
        this.stream=stream;
        this.outstream=outstream;
        this.path=path;
    }
    public void run(){
        log(Thread.currentThread().getName()+"started");
        FileEmail fe=new FileEmail(path,sec);
        ArrayList<Email> emaillist=null;
        Shell rcvShell=null;
        Shell sndShell=new Shell();
        
        try {
            rcvShell=(Shell)stream.readObject();
            if(!rcvShell.isSetDisconnected()&&!rcvShell.isSetRefresh()&&!rcvShell.isSetEmail()&&!rcvShell.isSetEmailList()&&!rcvShell.isSetDelete()){
                log("request first contact");
                sendAll(sndShell,rcvShell,fe);
            }else if(rcvShell.isSetEmail()){
                log("received email");
                rcvEmail(sndShell,rcvShell,fe);
            }else if(rcvShell.isSetDelete()){
                log("request for delete");
                delEmail(sndShell,rcvShell,fe);
            }else if(rcvShell.isSetRefresh()){
                log("request for refresh");
                refresh(sndShell,rcvShell,fe);
            }else if(rcvShell.isSetDisconnected()){
                log("the user "+rcvShell.getHeader()+" has logged off");
                clientDisconnected(sndShell,rcvShell);
            }
            outstream.flush();
            
        } catch (IOException ex) {
            System.out.println("IOException"+ex.getStackTrace()[0].getLineNumber());
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFound"+ex.getStackTrace()[0].getLineNumber());
        }
            log(Thread.currentThread().getName()+" "+rcvShell.getHeader());
            log("stopped");
    }
    public void sendAll(Shell sndShell,Shell rcvShell,FileEmail fe)throws  IOException{
        System.out.println("send all to"+rcvShell.getHeader());
        fe.usrSet(rcvShell.getHeader());
        sndShell.setEmailList(fe.read(0));
        sndShell.setHeader(rcvShell.getHeader());
        sndShell.setError("");
        outstream.writeObject(sndShell);
        log("replay with all email");
    }
    public void refresh(Shell sndShell,Shell rcvShell,FileEmail fe) throws IOException{
        System.out.println("refresh for "+rcvShell.getHeader());
        sndShell.setHeader(rcvShell.getHeader());
        fe.usrSet(rcvShell.getHeader());
        sndShell.setEmailList(fe.read(1));
        fe.moveToUser();
        outstream.writeObject(sndShell);
        log("replay with new email");
    }
    public void rcvEmail(Shell sndShell,Shell rcvShell,FileEmail fe) throws IOException{
        log("receiver : "+rcvShell.getEmail().getReceiver()+" header: "+rcvShell.getHeader());
        String receiver=rcvShell.getEmail().getReceiver();
        String []receivers=receiver.split(" ");
        String error="";
        for(int i=0;i<receivers.length;i++){
            if(fe.isUser(receivers[i])){
                fe.write(rcvShell.getEmail());
            }else{
                error= receivers[i]+" not exist";
            }
        }
        sndShell.setHeader(rcvShell.getHeader());
        sndShell.setError(error);
        outstream.writeObject(sndShell); 
        log("replay with shell");
    }
    public void delEmail(Shell sndShell,Shell rcvShell,FileEmail fe) throws IOException{
        fe.usrSet(rcvShell.getHeader());
        fe.remove(rcvShell.getDeleted());
        log("delete "+rcvShell.getDeleted());
        sndShell.setHeader(rcvShell.getHeader());
        sndShell.setError("");
        outstream.writeObject(sndShell);
    }
    public void clientDisconnected(Shell sndShell,Shell rcvShell) throws IOException{
        if(rcvShell.getDisconnected()){
           sndShell.setHeader(rcvShell.getHeader());
           sndShell.setDisconnected(rcvShell.getDisconnected());
           outstream.writeObject(sndShell);
        }
    }
    public void log(String str){
        Platform.runLater(()->{
            sec.writeLog(str);        
        });
    }
}
