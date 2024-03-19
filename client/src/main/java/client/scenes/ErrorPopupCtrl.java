package client.scenes;

import client.utils.LanguageConf;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
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
            this.errorImage.setImage(new Image(String.valueOf(
                    getClass().getResource("/client/scenes/icons8-error-96.png"))));
            Properties prop = new Properties();
            prop.load(fis);
            this.errorHeader.setText(errorHeader);
            this.errorDescription.setText(errorDescription);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
