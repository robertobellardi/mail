package thunder;
/**
 *
 * @author fedef
 */
import java.util.*;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import common.Email;
import java.io.IOException;
import java.net.Socket;

public class EmailModel {
    private EmailViewerController emvc;
    private Container_ c;
    private String maxID;
    private String path;
    private String Ip;
    private int Port;
    
    private final ObservableList<Email> emailList = FXCollections.observableArrayList(email -> 
        new Observable[] {
                        email.idProperty(),
                        email.dateProperty(),
                        email.senderProperty(),
                        email.receiverProperty(), 
                        email.textProperty(),
                        email.argumentProperty()});
    

    private final ObjectProperty<Email> currentEmail = new SimpleObjectProperty<>(null);
    public ObjectProperty<Email> currentEmailProperty() {
        return currentEmail ;
    }
    public void setData(String Ip,int Port,EmailViewerController emvc){
        this.Ip=Ip;
        this.Port=Port;
        this.emvc=emvc;
        
    }
    public final Email getCurrentEmail() {
        return currentEmailProperty().get();
    }

    public final void setCurrentEmail(Email email) {
        emvc.enableButton();
        currentEmailProperty().set(email);
    }

    public ObservableList<Email> getEmailList() {
        return emailList ;
    }
    public void removeEmail(Email email){
        
        Socket soc=null;
        
        try {
            soc=new Socket(Ip,Port);
        } catch (IOException ex) {
            System.out.println("Error Socket EmailMOdel.java "+ex.getStackTrace()[0].getLineNumber());
        }       
        emailList.remove(email);
        Listner l=new Listner(email,soc,email.getReceiver(),3,c);
        l.start();
    }
    
    public void addEmail(Email email){
        emailList.add(0,email);    
    }
    
    public void loadData(List<Email> array){
       
        for(int i=0;array!=null&&i<array.size();i++){
            emailList.add(0,array.get(i));
        }
    }
    
    public void setContainer(Container_ c){
        this.c=c;
    }
}
