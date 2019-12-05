package thunder;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import common.Email;
/**
 *
 * @author fedef
 */
public class TableController implements Initializable{
    private EmailModel model;
    @FXML
    private TableView<Email> table;
    @FXML
    private TableColumn<Email, String> id;
    @FXML
    private TableColumn<Email, String>  object;
    @FXML
    private TableColumn<Email, String>  sender;
    @FXML
    private TableColumn<Email, String>  date;
    
        
    public void initModel(Container_ cont) {
        // ensure model is only set once:
        if (this.model != null) {
            throw new IllegalStateException("Model can only be initialized once TableController 32");
        }
        this.model = cont.getEm() ;
        table.setItems(model.getEmailList());

        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> 
        model.setCurrentEmail(newSelection));

        model.currentEmailProperty().addListener((obs, oldPerson, newEmail) -> {
            if (newEmail == null) {
                table.getSelectionModel().clearSelection();
            } else {
                table.getSelectionModel().select(newEmail);
            }
        });
        


    }
        @Override
    public void initialize(URL url, ResourceBundle rb) {
        id.setCellValueFactory(
            new PropertyValueFactory<Email, String>("id"));
        object.setCellValueFactory(
            new PropertyValueFactory<Email, String>("argument"));
        sender.setCellValueFactory(
            new PropertyValueFactory<Email, String>("sender"));
        date.setCellValueFactory(
            new PropertyValueFactory<Email, String>("date"));
    }       
}
