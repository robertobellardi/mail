package serveremail;
/**
 *
 * @author itach
 */
import common.Email;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import javafx.application.Platform;
public class FileEmail{
        private String path_file="";
        private String separator="/";
        private String extention=".dat";
        private String maxName="1000";
        private String usr;
        private ServerEmailController sec;
        public  FileEmail(String path_file,ServerEmailController sec){
            this.path_file=path_file;
            this.sec= sec;
        }
    	public  ArrayList<Email> read(int mode){                        //0 legge dalla cartella usr
                                                                        //1 legge dalla cartella tmp
	    File folder=null;
            ArrayList<Email> ar=new ArrayList<>();
            try{
                if(mode==0){
                    folder=new File(path_file+separator+usr);
                }else{
                   folder=new File(path_file+separator+usr+separator+"tmp"); 
                }
            } catch (NullPointerException ex) {
                            System.out.println("NUllPoint"+ex.getStackTrace()[0].getLineNumber());					
            }
            for (File fileEntry : folder.listFiles()) {
                if(fileEntry.getName().endsWith(extention)){				
                    try{
                        
                        ObjectInputStream in =new ObjectInputStream(new FileInputStream((mode==0)?path_file+separator+usr+separator+fileEntry.getName():path_file+separator+usr+separator+"tmp"+separator+fileEntry.getName()));
                        Email r=(Email)in.readObject();
                        log("read "+usr+separator+"tmp"+separator+fileEntry.getName());
                        ar.add(r);
                        in.close();
                        maxName=fileEntry.getName();
                    } catch (IOException ex) {
                            System.out.println("IOEception "+ex.getStackTrace()[0].getLineNumber());						
                    } catch (ClassNotFoundException ex) {
                        System.out.println("ClassNotFoundException "+ex.getStackTrace()[0].getLineNumber());	
                    }
                }
            }
            setLastFile(getLastFile());
            return ar; 		
    }
    public void returnMaxName(){
            File folder=null;
            try{
                folder=new File(path_file+separator+usr);
            } catch (NullPointerException e) {
                            System.out.println(e.getMessage());					
            } 
            if(folder.listFiles().length!=1){
                setLastFile(folder.listFiles()[folder.listFiles().length-2].getName());
            }
    }
    public void setLastFile(String name){
        maxName=String.valueOf(Integer.valueOf(name.replaceAll(extention,""))+1);
    }
    public String getLastFile(){
        return maxName;
    }
    public  void write(Email email){

        try {
                returnMaxName();
                ObjectOutputStream out= new ObjectOutputStream(new FileOutputStream(path_file+separator+usr+separator+"tmp"+separator+maxName+extention));
                email.setID(maxName);
                out.writeObject(email);
                out.close();
                setLastFile(getLastFile());
                log("writed email "+maxName+" to "+usr+separator+"tmp");
        } 
        catch(IOException | RuntimeException ex) {
            System.out.println("Error write"+ex.getStackTrace()[0].getLineNumber());
        }
        
    }
    public boolean isUser(String usr){
        File dir=null;
        dir=new File(path_file+separator+usr);
        if(!dir.exists()){
            log(usr+" not exist");
            return false;
        }else{
            this.usr=usr;
            return true;
        }     
    }
    public void usrSet(String usr){
        File dir=null;
        this.usr=usr;
        dir=new File(path_file+separator+usr);
        if(!dir.exists()){
            log("create directory for new user");
            makeUserDir();
        }
    }
    public void makeUserDir(){
        File dir=null;
        File tmp=null;
        dir=new File(path_file+separator+usr);
        tmp=new File(path_file+separator+usr+separator+"tmp");
        dir.mkdir();
        tmp.mkdir();
    }
    public void remove(String id){
        Path path = FileSystems.getDefault().getPath(path_file+separator+usr+separator+id+extention);
        try{
        Files.delete(path);
        log("deleted email "+id);
        } catch (IOException ex) {
            System.out.println("Error file "+ex.getStackTrace()[0].getLineNumber());						
        }
    }
    public void moveToUser(){
        File folder=null;
        String src=path_file+separator+usr+separator+"tmp";
        String trg=path_file+separator+usr;
        try{
            folder=new File(src);
        } catch (NullPointerException ex) {
                        System.out.println("Error open "+ex.getStackTrace()[0].getLineNumber());					
        }
        for (File fileEntry : folder.listFiles()) {
            try {
                log("move file "+fileEntry.getName()+"from tmp to "+usr);
                Files.move(FileSystems.getDefault().getPath(src+separator+fileEntry.getName()),FileSystems.getDefault().getPath(trg+separator+fileEntry.getName()),StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                System.out.println("Error move "+ex.getStackTrace()[0].getLineNumber());
            }
        }
            
    }
    
    public void log(String str){
        Platform.runLater(()->{
            sec.writeLog(str);        
        });
    }
    
}

