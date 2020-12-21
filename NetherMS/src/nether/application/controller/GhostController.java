package nether.application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import nether.application.AlertBox;
import nether.application.Main;

public class GhostController implements Initializable {

	@FXML
	private Button Reincarnation;

	@FXML
	private Button Maintenance;

	@FXML
	private Button HellMoneyButton;

	@FXML
	private Button QueryLife;


	@FXML
	void pressQueryLife(ActionEvent event) {
		AlertBox.queryLifeBox();
	}

	@FXML
	void pressReincarnation(ActionEvent event) {
		AlertBox.display("抱歉", "该功能暂未开放", "确定");
	}

	@FXML
	void pressMaintenance(ActionEvent event) {
		AlertBox.display("抱歉", "该功能暂未开放", "确定");
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