/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.scenes;

import client.utils.LanguageConf;
import client.utils.UserConfig;
import client.utils.Websocket;
import com.google.inject.Inject;
import commons.Event;
import commons.Participant;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.stage.Modality;

import java.io.File;

public class MainCtrl {

    private Stage primaryStage;
    private StartScreenCtrl startScreenCtrl;
    private Scene startScreen;
    private Scene adminLogin;

    private LanguageConf languageConf;

    private AdminLoginCtrl adminLoginCtrl;
    private EditParticipantsCtrl editParticipantsCtrl;
    private Scene editParticipants;

    private EventPageCtrl eventPageCtrl;
    private Scene eventPage;
    private UserConfig userConfig;

    private Scene adminOverview;
    private AdminOverviewCtrl adminOverviewCtrl;
    private Websocket websocket;

    private ErrorPopupCtrl errorPopupCtrl;
    private Scene errorPopup;


    /**
     * @param websocket the websocket instance
     */
    @Inject
    public MainCtrl(Websocket websocket) {
        this.websocket = websocket;

    }

    /**
     * Initializes the UI
     *
     * @param primaryStage         stage
     * @param languageConf         the language config
     * @param userConfig           the user configuration
     * @param startScreen          controller and scene
     * @param eventPage            controller and scene for event page
     * @param adminLogin           admin login controller and scene
     * @param editParticipantsPage controller and scene for editParticipants
     * @param adminOverview        admin overview controller and scene
     * @param errorPopup controller and scene for errorPopup
     */
    public void initialize(
            Stage primaryStage,
            LanguageConf languageConf,
            UserConfig userConfig,
            Pair<StartScreenCtrl, Parent> startScreen,
            Pair<EventPageCtrl, Parent> eventPage,
            Pair<AdminLoginCtrl, Parent> adminLogin,
            Pair<EditParticipantsCtrl, Parent> editParticipantsPage,
            Pair<AdminOverviewCtrl, Parent> adminOverview,
            Pair<ErrorPopupCtrl, Parent> errorPopup
    ) {

        this.primaryStage = primaryStage;
        this.languageConf = languageConf;
        this.userConfig = userConfig;


        this.adminLoginCtrl = adminLogin.getKey();
        this.adminLogin = new Scene(adminLogin.getValue());

        this.startScreenCtrl = startScreen.getKey();
        this.startScreen = new Scene(startScreen.getValue());

        this.eventPageCtrl = eventPage.getKey();
        this.eventPage = new Scene(eventPage.getValue());


        this.editParticipantsCtrl = editParticipantsPage.getKey();
        this.editParticipants = new Scene(editParticipantsPage.getValue());

        this.adminOverviewCtrl = adminOverview.getKey();
        this.adminOverview = new Scene(adminOverview.getValue());

        this.errorPopupCtrl = errorPopup.getKey();
        this.errorPopup = new Scene(errorPopup.getValue());

        //showOverview();
        showStartScreen();
        primaryStage.show();


    }

    /**
     * Display start screen
     */
    public void showStartScreen() {
        websocket.disconnect();
        primaryStage.setTitle(languageConf.get("StartScreen.title"));
        startScreenCtrl.reset();
        primaryStage.setScene(startScreen);

    }

    /**
     * Display admin login
     */
    public void showAdminLogin() {
        primaryStage.setTitle(languageConf.get("AdminLogin.title"));
        primaryStage.setScene(adminLogin);
    }

    /**
     * shows the event page
     *
     * @param eventToShow the event to display
     */
    public void showEventPage(Event eventToShow) {
        userConfig.setMostRecentEventCode(eventToShow.getId());
        websocket.connect(eventToShow.getId());
        eventPageCtrl.displayEvent(eventToShow);
        for (Participant p :
                eventToShow.getParticipants()) {
            System.out.println(p.getParticipantId() + " " + p.getName());


        }
        primaryStage.setScene(eventPage);
    }

    /**
     * this method is used to switch back to the event
     * page from the participant/expense editors
     * @param event the event to show
     */
    public void goBackToEventPage(Event event) {
        eventPageCtrl.displayEvent(event);
        primaryStage.setScene(eventPage);
    }

    /**
     * shows the participant editor page
     *
     * @param eventToShow the event to show the participant editor for
     */
    public void showEditParticipantsPage(Event eventToShow) {
        editParticipantsCtrl.displayEditParticipantsPage(eventToShow);
        primaryStage.setTitle(languageConf.get("EditP.editParticipants"));
        primaryStage.setScene(editParticipants);
    }

    /**
     * shows the admin overview
     * @param password admin password
     */
    public void showAdminOverview(String password) {
        adminOverviewCtrl.setPassword(password);
        adminOverviewCtrl.loadAllEvents(); // the password needs to be set before this method
        primaryStage.setTitle("Admin Overview");
        primaryStage.setScene(adminOverview);
    }

    /**
     * Show error popup for general usage
     * @param type type of error
     * @param place place of error
     * Check ErrorPopupCtrl for more detailed documentation
     */
    public void showErrorPopup(String type, String place){
        errorPopupCtrl.generatePopup(type, place);
        Stage stage = new Stage();
        stage.setScene(errorPopup);
        stage.setResizable(false);
        stage.setTitle("Error");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    /**
     * Show error popup for general usage
     * @param type type of error
     * @param place place of error
     * @param limit
     * Check ErrorPopupCtrl for more detailed documentation
     */
    public void showWordLimitErrorPopup(String type, String place, int limit){
        errorPopupCtrl.generatePopup(type, place, limit);
        Stage stage = new Stage();
        stage.setScene(errorPopup);
        stage.setResizable(false);
        stage.setTitle("Error");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }


    /**
     * Opens the system file chooser to save something
     *
     * @param fileChooser file chooser
     * @return opened file
     */
    public File showSaveFileDialog(FileChooser fileChooser) {
        return fileChooser.showSaveDialog(primaryStage);
    }


    /**
     * Getter for startScreenCtrl
     *
     * @return startScreenCtrl
     */
    public StartScreenCtrl getStartScreenCtrl() {
        return startScreenCtrl;
    }


    /**
     * setter for startScreenCtrl
     *
     * @param startScreenCtrl start screen controller
     */
    public void setStartScreenCtrl(StartScreenCtrl startScreenCtrl) {
        this.startScreenCtrl = startScreenCtrl;
    }

    /**
     * Display overview
     */


    /**
     * AdminLoginCtrl getter
     *
     * @return admin login controller
     */
    public AdminLoginCtrl getAdminLoginCtrl() {
        return adminLoginCtrl;
    }

    /**
     * setter for adminLoginCtrl
     *
     * @param adminLoginCtrl admin login controller
     */
    public void setAdminLoginCtrl(AdminLoginCtrl adminLoginCtrl) {
        this.adminLoginCtrl = adminLoginCtrl;
    }


//    public void showOverview() {
//        primaryStage.setTitle("Quotes: Overview");
//        primaryStage.setScene(overview);
//        overviewCtrl.refresh();
//    }
//
//    /**
//     * display adding quote scene
//     */
//    public void showAdd() {
//        primaryStage.setTitle("Quotes: Adding Quote");
//        primaryStage.setScene(add);
//        add.setOnKeyPressed(e -> addCtrl.keyPressed(e));
//    }
}