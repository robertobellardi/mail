package thunder;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import common.Email;
/**
 *
 * @author fedef
 */
public class FXMLDocumentController implements Initializable {
    
    private EmailModel model;
    @FXML
    private ListView<Email> list1;
    
    public void initModel(EmailModel model) {
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once FXMLDocumentController 22");
        }
        this.model = model ;
        list1.setItems(model.getEmailList());
        list1.setCellFactory(lv -> new ListCell<Email>() {
            @Override
            public void updateItem(Email email, boolean empty) {
                super.updateItem(email, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(email.getID() + " " + email.getSender());
                }
            }
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
}