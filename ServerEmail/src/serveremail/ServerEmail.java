package serveremail;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
/**
 *
 * @author fedef
 */
public class ServerEmail extends Application{
    private static final int num_thread=6;
    private ExecutorService exec;
    public void start(Stage stage) throws Exception{
        BorderPane root = new BorderPane();
        root.setMaxWidth(600);
        FXMLLoader ServerLoader = new FXMLLoader(getClass().getResource("server.fxml"));
        root.setCenter(ServerLoader.load());
        ServerEmailController SController=ServerLoader.getController();
        Scene scene = new Scene(root);
        exec = Executors.newFixedThreadPool(num_thread);
        ServerController sc= new ServerController(SController,exec);
        sc.setDaemon(true);
        sc.start();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
    public void stop() throws Exception{
        super.stop();
        exec.shutdown();
    }
    public static  void main(String []arg){
        launch();
    }    
}
