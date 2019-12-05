package thunder;

import common.Email;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;
/**
 *
 * @author fedef
 */
public class EmailViewerController {
    @FXML
    private Label senderLabel ;
    @FXML
    private Label objectLabel ;
    @FXML
    private Label receiverLabel ;
    @FXML
    private TextArea emailArea ;
    @FXML
    private Button delButton1;
    @FXML
    private Button replay;
    @FXML
    private Button replayall;
    @FXML    
    private void handleButtonAction(ActionEvent event) {  
        Button obj=(Button)event.getSource();
        
        if(model.getCurrentEmail()!=null){
            if(obj.getId().equals(delButton1.getId())){
                model.removeEmail(model.getCurrentEmail());
            }else{
                if(obj.getId().equals(replay.getId())){
                    pressreplay=true;
                    if(!press){
                        press=true;
                        stage.setScene(scene);
                        stage.setWidth(1500);
                        stage.setHeight(840);
                        thunder.getItems().add(warea); 
                        wac.makeEmail(model.getCurrentEmail());
                        disableButton();
                        sc.disableWrite();
                     }
                }

                if(obj.getId().equals(replayall.getId())){
                    pressreplayall=true;
                    Email mail=model.getCurrentEmail();
                    Email re=new Email();
                    re.setArgument(mail.getArgument());
                    String reciver=mail.getReceiver();
                    re.setSender(reciver.replaceAll(wac.getName(),mail.getSender()));                    
                    if(!press){
                        press=true;
                        stage.setScene(scene);
                        stage.setWidth(1500);
                        stage.setHeight(840);
                        thunder.getItems().add(warea);    
                        wac.makeEmail(re);
                        disableButton();
                        sc.disableWrite();
                    }
                }       
            }
        }
    }
    private Container_ cont;
    private SplitPane warea;
    private SplitPane thunder;
    private Stage stage;
    private Scene scene;
    private WAreaController wac;
    private SelectorController sc;
    private FXMLLoader wareaLoader;
    private EmailModel model ;
    private boolean press=false;
    private boolean pressreplay=false;
    private boolean pressreplayall=false;
    
    public void essentianl(){
        this.stage=cont.getStage();
        this.scene=cont.getScene();
        this.thunder=cont.getThunder();
        this.warea=cont.getWarea(); 
        this.wac=cont.getWc();
        this.sc=cont.getSc();
    }
    public void initModel(Container_ cont) {
        this.cont=cont;
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once EmailViewerController 99");
        }
        this.model = cont.getEm() ;
        this.model.currentEmailProperty().addListener((obs, oldEmail, newEmail) -> {
            if (oldEmail != null) {
                senderLabel.textProperty().unbind();
                objectLabel.textProperty().unbind();
                receiverLabel.textProperty().unbind();
                emailArea.textProperty().unbind();
            }
            if (newEmail == null) {
                senderLabel.setText("");
                objectLabel.setText("");
                receiverLabel.setText("");
                emailArea.setText("");
            } else {
                senderLabel.textProperty().bind(newEmail.senderProperty());
                objectLabel.textProperty().bind(newEmail.argumentProperty());
                receiverLabel.textProperty().bind(newEmail.receiverProperty());
                emailArea.textProperty().bind(newEmail.textProperty());
            }
        });
    }
    
    public void enableButton(){
        if(sc.getEnable()){
            delButton1.setDisable(false);
            replay.setDisable(false);
            replayall.setDisable(false);
        }
    }
    
    public void disableButton(){
        delButton1.setDisable(true);
        replay.setDisable(true);
        replayall.setDisable(true);   
    }
    
    public void disableDelete(){
        delButton1.setDisable(true);
    }
    
    public void enableDelete(){
        delButton1.setDisable(false);
    }
    
    public void setpressreplay(boolean press){
        pressreplay=press;
    }
 
    public void setpressreplayall(boolean press){
        pressreplayall=press;
    }
    
    public boolean getpressreplay(){
        return pressreplay;
    }
 
    public boolean getpressreplayall(){
        return pressreplayall;
    }

    public void close(){
        stage.setScene(scene);
        stage.setWidth(790);
        stage.setHeight(840);
        thunder.getItems().remove(warea);
        press=false;
    }
}
