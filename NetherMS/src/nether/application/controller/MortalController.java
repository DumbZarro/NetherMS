/**
 * Sample Skeleton for 'mortal.fxml' Controller Class
 */

package nether.application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import nether.application.AlertBox;
import nether.application.Main;

public class MortalController implements Initializable{

    @FXML // fx:id="Reincarnation"
    private Button Reincarnation; // Value injected by FXMLLoader

    @FXML // fx:id="HellMoneyButton"
    private Button HellMoneyButton; // Value injected by FXMLLoader

    @FXML // fx:id="QueryLife"
    private Button QueryLife; // Value injected by FXMLLoader


    @FXML
    void pressReincarnation(ActionEvent event) {
    	AlertBox.display("抱歉", "该功能暂未开放", "确定");
    }


    @FXML
    void pressQueryLife(ActionEvent event) {
    	AlertBox.queryLifeBox();
    }


    @FXML
    void pressHellMoney(ActionEvent event) {
    	AlertBox.display("抱歉", "该功能暂未开放", "确定");
    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Main.controllers.put(this.getClass().getSimpleName(), this);
		LoginController controller = (LoginController) Main.controllers.get(LoginController.class.getSimpleName());
		System.out.println(controller.currentUser);
	}



}
