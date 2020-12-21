package nether.application.controller;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import nether.application.AlertBox;
import nether.application.ControllerUtils;
import nether.application.Main;
import nether.dao.User;
import nether.dao.UserDBUtil;

public class RegisterController implements Initializable {
	public User currentUser;

	@FXML
	private TextField trueName;

	@FXML
	private Button GhostButton;

	@FXML
	private Button returnLogin;

	@FXML
	private PasswordField password;

	@FXML
	private PasswordField passwordAgain;

	@FXML
	private Button MortalButton;



	@FXML
	void selectMortalButton(ActionEvent event) {
		String username = this.trueName.getText();// 用户名
		String userpassword = this.password.getText();// 密码
		String passwordAgain = this.passwordAgain.getText();
		// 对账户密码进行检查
		String pattern1 = "[A-Za-z0-9_\\-\\u4e00-\\u9fa5]{3,16}";
		String pattern2 = "[A-Za-z0-9]{6,16}";
		Pattern r1 = Pattern.compile(pattern1);
		Pattern r2 = Pattern.compile(pattern2);

		
		Matcher m = r1.matcher(username);
		Matcher n = r2.matcher(userpassword);
		
		System.out.println(m.matches());
		System.out.println(n.matches());
		System.out.println(m);
		System.out.println(n);

		if (!m.matches() && n.matches()) {
			AlertBox.display("错误", "你的账号格式有误,请重新输入", "确定");
		} else if (!n.matches() && m.matches()) {
			AlertBox.display("错误", "你的密码格式有误,请重新输入", "确定");
		} else if (!n.matches() && !m.matches()) {
			AlertBox.display("错误", "你的账号和密码格式都有误,请重新输入", "确定");
		} else {

//			UserDBUtil dbUtil = new UserDBUtil();	// User ID 生成要用到这个组件，不能改变顺序在new User后面
			if (userpassword.equals(passwordAgain)) {
				currentUser = new User(username, userpassword, "mortal", new Date());

				UserDBUtil.storeUser(currentUser);
				AlertBox.display("恭喜", "注册成功,你的令牌为："+ String.valueOf(currentUser.getId()) +" 前往登陆页面登陆", "确定");
				// 跳转并且关闭页面
				ControllerUtils ctrlUtils = new ControllerUtils();
				ctrlUtils.jumpFxml("login.fxml", MortalButton, true);
			} else {
				AlertBox.display("错误", "两次密码不相同,请重新输入", "确定");
			}
		}
	}

	@FXML
	void selectGhostButton(ActionEvent event) {
		String username = this.trueName.getText();// 用户名
		String userpassword = this.password.getText();// 密码
		String passwordAgain = this.passwordAgain.getText();
		
		String pattern1 = "[A-Za-z0-9_\\-\\u4e00-\\u9fa5]{3,16}";
		String pattern2 = "[A-Za-z0-9]{6,16}";
		Pattern r1 = Pattern.compile(pattern1);
		Pattern r2 = Pattern.compile(pattern2);

		Matcher m = r1.matcher(username);
		Matcher n = r2.matcher(userpassword);

		System.out.println(m.matches());
		System.out.println(n.matches());
		System.out.println(m);
		System.out.println(n);
		
		
		if (!m.matches() && n.matches()) {
			AlertBox.display("错误", "你的账号格式有误,请重新输入", "确定");
		} else if (!n.matches() && m.matches()) {
			AlertBox.display("错误", "你的密码格式有误,请重新输入", "确定");
		} else if (!n.matches() && !m.matches()) {
			AlertBox.display("错误", "你的账号和密码格式都有误,请重新输入", "确定");
		} else {
//			UserDBUtil dbUtil = new UserDBUtil();
			if (userpassword.equals(passwordAgain)) {
				currentUser = new User(username, userpassword, "ghost", new Date());

				UserDBUtil.storeUser(currentUser);
				AlertBox.display("恭喜", "注册成功,你的令牌为："+ String.valueOf(currentUser.getId()) +" 前往登陆页面登陆", "确定");
				// 跳转并且关闭页面
				ControllerUtils ctrlUtils = new ControllerUtils();
				ctrlUtils.jumpFxml("login.fxml", GhostButton, true);
			} else {
				AlertBox.display("错误", "两次密码不相同,请重新输入", "确定");
			}
		}
	}

	@FXML
	void pressReturnLogin(ActionEvent event) {
		ControllerUtils ctrlUtils = new ControllerUtils();
		ctrlUtils.jumpFxml("login.fxml", returnLogin, true);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Main.controllers.put(this.getClass().getSimpleName(), this);
		LoginController controller = (LoginController) Main.controllers.get(LoginController.class.getSimpleName());
		System.out.println(controller.currentUser);
	}


}
