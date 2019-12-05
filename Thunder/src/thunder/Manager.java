package thunder;

import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;
/**
 *
 * @author itach
 */
public class Manager {
    
    private Container_ cont;
    private SelectorController sc;
    private EmailViewerController ewc;
    private TableController tc;
    private WAreaController wc;
    private EmailModel em;

    public Manager(){
        this.cont= new Container_();
    }
    
    public void setController(SelectorController sc,EmailViewerController ewc,TableController tc,WAreaController wc,EmailModel em){
        this.em=em;
        this.sc=sc;
        this.ewc=ewc;
        this.tc=tc;
        this.wc=wc;
        cont.setSc(sc);
        cont.setEwc(ewc);
        cont.setTc(tc);
        cont.setWc(wc);
        cont.setEm(em);    
    }
    
    public void setIp(String ip,int Port){
       cont.setIp(ip);
       cont.setPort(Port);    
    }
    
    public void setView(Stage stage,Scene scene){
        cont.setScene(scene);
        cont.setStage(stage);    
    }
    
    public void setPane(SplitPane warea,SplitPane thunder){
        cont.setWarea(warea);
        cont.setThunder(thunder);
    }
    public void setModel(){
        em.setData(cont.getIp(),cont.getPort(),cont.getEwc());
    }
    public void initControllers(){
        wc.init(cont);
        wc.makeMessage();
        tc.initModel(cont);
        ewc.initModel(cont);
    }
    public void initGraphics(){
        sc.init(cont);
        ewc.essentianl();
    }
    
    public Container_ getContainer(){
        return cont;
    }
}
