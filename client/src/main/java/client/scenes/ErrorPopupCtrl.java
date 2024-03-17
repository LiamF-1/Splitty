package client.scenes;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ErrorPopupCtrl {

    @FXML
    private ImageView errorCloseButton;
    @FXML
    private BorderPane errorPopupPane;

    public void errorClose(){

    }

    public void showPopup(Pane pane){
        Stage base = (Stage) pane.getScene().getWindow();
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/scenes/errorPopup.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Error!");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            scene.setFill(Color.TRANSPARENT);

            stage.show();
            stage.setX(base.getX() - scene.getWidth()/2 + base.getWidth()/2);
            stage.setY(base.getY() - scene.getHeight()/2 + base.getHeight()/2);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
