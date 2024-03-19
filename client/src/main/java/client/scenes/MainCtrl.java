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
import commons.Event;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainCtrl {

    private Stage primaryStage;
    private StartScreenCtrl startScreenCtrl;
    private Scene startScreen;
    private LanguageConf languageConf;
    private EventPageCtrl eventPageCtrl;
    private Scene eventPage;
    private ErrorPopupCtrl errorPopupCtrl;
    private Scene errorPopup;

    /**
     * Initializes the UI
     *
     * @param primaryStage stage
     //* @param overview controller and parent
     //* @param add controller and parent
     * @param languageConf the language config
     * @param startScreen controller and scene

     * @param eventPage controller and scene for eventpage
     *
     */
    public void initialize(
            Stage primaryStage,
            LanguageConf languageConf,
            Pair<StartScreenCtrl, Parent> startScreen,
            Pair<EventPageCtrl, Parent> eventPage,
            Pair<ErrorPopupCtrl, Parent> errorPopup
    ) {

        this.primaryStage = primaryStage;
        this.languageConf = languageConf;
        //this.overviewCtrl = overview.getKey();
        //this.overview = new Scene(overview.getValue());

        //this.addCtrl = add.getKey();
        //this.add = new Scene(add.getValue());

        this.startScreenCtrl = startScreen.getKey();
        this.startScreen = new Scene(startScreen.getValue());

        this.eventPageCtrl = eventPage.getKey();
        this.eventPage = new Scene(eventPage.getValue());

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
        primaryStage.setTitle(languageConf.get("StartScreen.title"));
        primaryStage.setScene(startScreen);
    }


    /**
     * shows the event page
     * @param eventToShow the event to display
     */
    public void showEventPage(Event eventToShow) {
        eventPageCtrl.displayEvent(eventToShow);
        primaryStage.setScene(eventPage);
    }

    public void showErrorPopup(Pane pane, String header, String description){
        errorPopupCtrl.showPopup(pane, header, description);
        Stage stage = new Stage();
        stage.setScene(errorPopup);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Display overview
     */

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