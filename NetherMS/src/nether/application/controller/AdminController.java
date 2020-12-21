package nether.application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import nether.application.AlertBox;
import nether.application.ControllerUtils;
import nether.application.Main;

public class AdminController implements Initializable{

	@FXML
	private Button Resign;

	@FXML
	private Button DevilsNight;

	@FXML
	private Button ExpiateGhost;

	@FXML
	private Button ManageHellMoney;

	@FXML
	private Button SearchNote;

	@FXML
	private Button ActionLog;

	@FXML
	private Button QueryLife;

	@FXML
	void pressSearchNote(ActionEvent event) {
		ControllerUtils ctrlUtils = new ControllerUtils();
		ctrlUtils.jumpFxml("deathNote.fxml", SearchNote, false);
	}

	@FXML
	void pressQueryLife(ActionEvent event) {
		AlertBox.queryLifeBox();
	}

	@FXML
	void pressActionLog(ActionEvent event) {
		AlertBox.display("抱歉", "该功能暂未开放", "确定");
	}

	@FXML
	void pressResign(ActionEvent event) {
		AlertBox.display("抱歉", "该功能暂未开放", "确定");
	}

	@FXML
	void pressExpiateGhost(ActionEvent event) {
		AlertBox.display("抱歉", "该功能暂未开放", "确定");
	}

	@FXML
	void pressDevilsNight(ActionEvent event) {
		AlertBox.display("抱歉", "该功能暂未开放", "确定");
	}

	@FXML
	void pressManageHellMoney(ActionEvent event) {
		AlertBox.display("抱歉", "该功能暂未开放", "确定");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Main.controllers.put(this.getClass().getSimpleName(), this);
		LoginController controller = (LoginController) Main.controllers.get(LoginController.class.getSimpleName());
		System.out.println(controller.currentUser);
	}

}
