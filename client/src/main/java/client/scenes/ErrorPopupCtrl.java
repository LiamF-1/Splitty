package client.scenes;

import client.utils.LanguageConf;
import com.google.inject.Inject;
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

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class ErrorPopupCtrl {

    private final MainCtrl mainCtrl;
    private final LanguageConf languageConf;

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

    @FXML
    public void initialize(){

    }

    @Inject
    public ErrorPopupCtrl(MainCtrl mainCtrl, LanguageConf languageConf) {
        this.mainCtrl = mainCtrl;
        this.languageConf = languageConf;
    }

    public ErrorPopupCtrl() {
        mainCtrl = null;
        languageConf = null;
    }

    /**
     *
     * @param pane
     */
    public void showPopup(Pane pane, String errorHeader, String errorDescription){
        Stage base = (Stage) pane.getScene().getWindow();
        String languageURL = Objects.requireNonNull(getClass().getResource
                ("/languages_" + languageConf.getCurrentLocaleString() + ".properties")).getPath();
        try(FileInputStream fis = new FileInputStream(languageURL)){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/scenes/ErrorPopup.fxml"));

            Properties prop = new Properties();
            prop.load(fis);

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Error!");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();
            //this.errorDescription.textProperty().bind(Bindings.concat(errorDescription));
            this.errorHeader.setText(errorHeader);
            this.errorDescription.setText(errorDescription);
            stage.setX(base.getX() - scene.getWidth()/2 + base.getWidth()/2);
            stage.setY(base.getY() - scene.getHeight()/2 + base.getHeight()/2);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
