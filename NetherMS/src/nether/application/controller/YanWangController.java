/**
 * Sample Skeleton for 'super.fxml' Controller Class
 */

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
import nether.dao.User;

public class YanWangController implements Initializable {

	public User currentUser = null;

	@FXML // fx:id="Resign"
	private Button Resign; // Value injected by FXMLLoader

	@FXML // fx:id="DeathNote"
	private Button DeathNote; // Value injected by FXMLLoader

	@FXML // fx:id="DevilsNight"
	private Button DevilsNight; // Value injected by FXMLLoader

	@FXML // fx:id="AssignRight"
	private Button AssignRight; // Value injected by FXMLLoader

	@FXML // fx:id="SelectGhost"
	private Button SelectGhost; // Value injected by FXMLLoader

	@FXML // fx:id="ManageHellMoney"
	private Button ManageHellMoney; // Value injected by FXMLLoader

	@FXML // fx:id="ActionLog"
	private Button ActionLog; // Value injected by FXMLLoader

	@FXML // fx:id="QueryLife"
	private Button QueryLife; // Value injected by FXMLLoader

	@FXML
	void pressDeathNote(ActionEvent event) {
		ControllerUtils ctrlUtils = new ControllerUtils();
		ctrlUtils.jumpFxml("deathNote.fxml", DeathNote, false);
	}

	@FXML
	void pressQueryLife(ActionEvent event) {
		AlertBox.queryLifeBox();
	}

	@FXML
	void AssignRight(ActionEvent event) {
		AlertBox.AssignRight();
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
	void pressSelectGhost(ActionEvent event) {
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

	/**
	 * @Title: initialize
	 * @Description:获取其他文件的数据,初始化页面
	 * @param arg0
	 * @param arg1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Main.controllers.put(this.getClass().getSimpleName(), this);
		LoginController controller = (LoginController) Main.controllers.get(LoginController.class.getSimpleName());
		System.out.println(controller.currentUser);
	}
}