package thunder;

import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;
/**
 *
 * @author itach
 */
public class Container_ {
    private SelectorController sc;
    private EmailViewerController ewc;
    private TableController tc;
    private WAreaController wc;
    private EmailModel em;
    private String ip;
    private int port;
    private Scene scene;
    private Stage stage;
    private SplitPane warea;
    private SplitPane thunder;

    public SelectorController getSc(){
        return sc;
    }
    
    public EmailViewerController getEwc(){
        return ewc;
    }
    
    private TableController getTc(){
        return tc;
    }
    
    public WAreaController getWc(){
        return wc;
    }
    
    public EmailModel getEm(){
        return em;
    }
    
    public String getIp(){
        return ip;
    }
    
    public int getPort(){
        return port;
    }
    
    public Scene getScene(){
        return scene;
    }
    
    public Stage getStage(){
        return stage;
    }
    
    public SplitPane getWarea(){
        return warea;
    }
    
    public SplitPane getThunder(){
        return thunder;
    }
    
    public void setSc(SelectorController sc){
        this.sc=sc;
    }
    
    public void setEwc(EmailViewerController ewc){
        this.ewc=ewc;
    }
    
    public void setTc(TableController tc){
        this.tc=tc;
    }
    
    public void setWc(WAreaController wc){
        this.wc=wc;
    }
    
    public void setEm(EmailModel em){
        this.em=em;
    }
    
    public void setIp(String ip){
        this.ip=ip;
    }
    
    public void setPort(int port){
        this.port=port;
    }
    
    public void setScene(Scene scene){
        this.scene=scene;
    }
    
    public void setStage(Stage stage){
        this.stage=stage;
    }
    
    public void setWarea(SplitPane warea){
        this.warea=warea;
    }
    
    public void setThunder(SplitPane thunder){
        this.thunder=thunder;
    }  
}