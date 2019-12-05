package thunder;

import java.io.IOException;
import java.net.Socket;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 *
 * @author fedef
 */
public class Thunder extends Application {
    //private final String Ip_Adress="172.16.94.49";
    private final String Ip_Adress="localhost";
    private final int Port=7654;
    private SelectorController selectorController;
    private Container_ c;
   
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();
        root.setMaxWidth(600);
        FXMLLoader TableLoader = new FXMLLoader(getClass().getResource("TableView.fxml"));
        FXMLLoader ViewLoader = new FXMLLoader(getClass().getResource("email_viewer.fxml"));
        root.setCenter(TableLoader.load());
        root.setBottom(ViewLoader.load());
        FXMLLoader selector=new FXMLLoader(getClass().getResource("container.fxml"));
        FXMLLoader wArea=new FXMLLoader(getClass().getResource("WArea.fxml"));
        SplitPane wAreaPane=(SplitPane)wArea.load();
        wAreaPane.setMaxWidth(600);
        Pane left=new Pane();
        left.setMaxWidth(181);
        left.setMinWidth(181);
        left.getChildren().add(selector.load());
        SplitPane container= new SplitPane(left,root);
        SplitPane thunder=new SplitPane(container);
        thunder.setMaxWidth(790);
        selectorController=selector.getController();
        EmailViewerController ViewerController=ViewLoader.getController();
        TableController tableController=TableLoader.getController();
        WAreaController wareaController=wArea.getController();
        Manager man= new Manager();
        man.setIp(Ip_Adress, Port);
        EmailModel model=new EmailModel();
        model.setContainer(man.getContainer());
        c=man.getContainer();
        man.setPane(wAreaPane, thunder);
        man.setController(selectorController, ViewerController, tableController, wareaController, model);
        man.setModel();
        man.initControllers();
        Scene scene = new Scene(thunder);
        scene.getStylesheets().add(Thunder.class.getResource("ThunderStyle.css").toExternalForm());
        stage.setResizable(false);
        man.setView(stage, scene);
        man.initGraphics();
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void stop()throws Exception{
        super.stop(); 
        String nome=selectorController.getName();
        Socket s=null;
        try{
            s=new Socket(Ip_Adress,Port);
        }catch(IOException ex){}
        Listner l=new Listner(s,nome,4,c);
        l.start();          
    }    
}