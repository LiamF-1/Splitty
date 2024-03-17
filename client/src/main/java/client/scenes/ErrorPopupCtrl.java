package client.scenes;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class ErrorPopupCtrl {

    @FXML
    private BorderPane errorPopupPane;
    @FXML
    private Text errorHeader;
    @FXML
    private Button errorButton;
    @FXML
    private Text errorDescription;
    @FXML
    private ImageView errorImage;

    /**
     *
     * @param pane
     */
    public void showPopup(Pane pane, String errorHeader, String errorDescription){
        Stage base = (Stage) pane.getScene().getWindow();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/scenes/ErrorPopup.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Error!");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();
            this.errorDescription.textProperty().bind(Bindings.concat(errorDescription));
            this.errorHeader.setText(errorHeader);
            stage.setX(base.getX() - scene.getWidth()/2 + base.getWidth()/2);
            stage.setY(base.getY() - scene.getHeight()/2 + base.getHeight()/2);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
