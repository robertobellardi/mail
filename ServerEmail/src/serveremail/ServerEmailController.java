package serveremail;

import javafx.fxml.FXML;
import javafx.scene.control.*;
/**
 *
 * @author fedef
 */
public class ServerEmailController {
    @FXML
    private TextArea serverLog;
    public ServerEmailController(){}
    
    public void writeLog(String message){
        serverLog.appendText(message+"\n");
    }
}
