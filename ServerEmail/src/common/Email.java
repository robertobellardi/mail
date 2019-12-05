package common;
/**
 *
 * @author fedef
 */
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.io.Serializable;  
public class Email implements Serializable{
    transient StringProperty id=new SimpleStringProperty();
    transient StringProperty sender=new SimpleStringProperty();
    transient StringProperty receiver=new SimpleStringProperty();
    transient StringProperty date=new SimpleStringProperty();
    transient StringProperty text=new SimpleStringProperty();
    transient StringProperty argument=new SimpleStringProperty();
    
    
    public Email(String id,String sender,String receiver,String date,String argument,String text){
        setID(id);
        setSender(sender);
        setReceiver(receiver);
        setDate(date);
        setText(text);
        setArgument(argument);
    }
    public Email(){
        setID("");
        setSender("");
        setReceiver("");
        setDate("");
        setText("");
        setArgument("");
    }
    public String getID(){
        return id.get();
    }
    public void setID(String val){
        id.set(val);
    }
    public String getSender(){
        return sender.get();
    }
    public void setSender(String val){
        sender.set(val);
    }
    public String getReceiver(){
        return receiver.get();
    }
    public void setReceiver(String val){
        receiver.set(val);
    }
    public String getArgument(){
        return argument.get();
    }
    public void setArgument(String val){
        argument.set(val);
    }
    public String getText(){
        return text.get();
    }
    public void setText(String val){
        text.set(val);
    }
    public String getDate(){
        return date.get();
    }
    public void setDate(String val){
        date.set(val);
    }
    public StringProperty idProperty() {return id;}
    public StringProperty senderProperty() {return sender;}
    public StringProperty receiverProperty() {return receiver;}
    public StringProperty textProperty() {return text;}
    public StringProperty dateProperty() {return date;}
    public StringProperty argumentProperty() {return argument;}
    
    private void writeObject(ObjectOutputStream obj) throws IOException {
        System.out.println("scrivo");
        obj.defaultWriteObject();
        obj.writeUTF(idProperty().getValueSafe());
        obj.writeUTF(senderProperty().getValueSafe());
        obj.writeUTF(receiverProperty().getValueSafe());
        obj.writeUTF(argumentProperty().getValueSafe());
        obj.writeUTF(textProperty().getValueSafe());
        obj.writeUTF(dateProperty().getValueSafe());
    }
    private void initProperty(){
        id=new SimpleStringProperty();
        sender=new SimpleStringProperty();
        receiver=new SimpleStringProperty();
        date=new SimpleStringProperty();
        text=new SimpleStringProperty();
        argument=new SimpleStringProperty(); 
    }
    private void readObject(ObjectInputStream obj) throws IOException,ClassNotFoundException {
        System.out.println("leggo");
        initProperty();
        id.set(obj.readUTF());
        sender.set(obj.readUTF());
        receiver.set(obj.readUTF());
        argument.set(obj.readUTF());
        text.set(obj.readUTF());
        date.set(obj.readUTF());
    }
}