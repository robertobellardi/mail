package thunder;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
/**
 *
 * @author fedef
 */
public class SelectorController {
    private SplitPane warea;
    private SplitPane thunder;
    private EmailModel model;
    private EmailViewerController ewc;
    private Stage stage;
    private Scene scene;
    private FXMLLoader wareaLoader;
    private WAreaController wac;
    private Container_ c;
    private boolean loadcount=false;
    private String Ip_Adress;
    private String name;
    private int Port;
    private boolean press=false;
    private boolean enable=true;
            
    @FXML
    private TextField utente;
    @FXML
    private Button r_button;    //write email
    @FXML
    private Button load;
    @FXML
    private Label labelerror;
    @FXML
    private void handleButtonAction(ActionEvent event) {
        Button obj=(Button)event.getSource();
            
        if(obj.getId().equals(load.getId()) && !loadcount){
            loadcount=true;
            this.name=utente.getText();
            wac.setName(this.name);
            utente.setEditable(false);
            r_button.setDisable(false);
            load.setDisable(true);  
            
            Timeout t=new Timeout(Ip_Adress,Port,this.name,model,c);
            t.setDaemon(true);
            t.start();         
        }else{
            if(obj.getId().equals(r_button.getId())){
                if(!press){
                    press=true;
                    stage.setScene(scene);
                    stage.setWidth(1500);
                    stage.setHeight(840);
                    thunder.getItems().add(warea);
                    wac.clearFields();
                    wac.makeEmail(); 
                    ewc.disableButton();
                    enable=false;
                }          
            }           
        }
    }
    public void init(Container_ cont){
        this.c=cont;
        this.stage=cont.getStage();
        this.scene=cont.getScene();
        this.thunder=cont.getThunder();
        this.warea=cont.getWarea();
        this.wac=cont.getWc();
        this.model=cont.getEm();
        this.Ip_Adress=cont.getIp();
        this.Port=cont.getPort();
        this.ewc=cont.getEwc();
    }
    
    public String getName(){
        return this.name;
    }
    
    public void close(){
        stage.setScene(scene);
        stage.setWidth(790);
        stage.setHeight(840);
        thunder.getItems().remove(warea);
        press=false;
    }
    public void disableWrite(){        
        r_button.setDisable(true);
    }
    public void enabledWrite(){
        enable=true;
        r_button.setDisable(false);
    }
    public boolean getEnable(){
        return this.enable;
    }     
    public void writeLabel(String str){
        Platform.runLater(()->{
            labelerror.setText(str);
        });
        
    }
}