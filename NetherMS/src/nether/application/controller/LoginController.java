/**
 * Sample Skeleton for 'login.fxml' Controller Class
 */

package nether.application.controller;

import java.net.URL;
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

public class LoginController implements Initializable{

	// 输入账号密码后获得的用户
	public User currentUser;//写出来是为了方便其他程序读取
	// 登陆的权限,高权限用户可以选择小于等于他权限的用户类型去登陆,界面的权限只登陆权限挂钩.
	private String LoginScope = null;

	@FXML
	private Button GhostButton;

	@FXML
	private PasswordField password;

	@FXML
	private Button RegisterButton;

	@FXML
	private Button AdminButton;

	@FXML
	private Button MortalButton;

	@FXML
	private Button LoginButton;

	@FXML
	private TextField username;

	@FXML
	private Button YanWangBotton;

	@FXML
	private void pressLoginButton(ActionEvent event) {
//		System.out.println(username.getText());
//		System.out.println(password.getText());
		UserDBUtil utool = new UserDBUtil();
		for(User user : utool.getAllUsers()) {
			System.out.println(user.toString());
		}
		String userid = this.username.getText();
		String userpw = this.password.getText();
		
		// 对账户密码进行检查
		String pattern1 = "[A-Za-z0-9_\\-\\u4e00-\\u9fa5]{3,16}";
		String pattern2 = "[A-Za-z0-9]{6,16}";
		Pattern r1 = Pattern.compile(pattern1);
		Pattern r2 = Pattern.compile(pattern2);
		
		Matcher m = r1.matcher(userid);
		Matcher n = r2.matcher(userpw);
		

		if (!m.matches() && n.matches()) {
			AlertBox.display("错误", "你的账号格式有误,请重新输入", "确定");
		} else if (!n.matches() && m.matches()) {
			AlertBox.display("错误", "你的密码格式有误,请重新输入", "确定");
		} else if (!n.matches() && !m.matches()) {
			AlertBox.display("错误", "你的账号和密码格式都有误,请重新输入", "确定");
		} else {
			// 确保限权非空
			if (LoginScope == null) {
				LoginScope = "mortal";// 没有选择按钮就默认最低权限
			}
			System.out.println(LoginScope);

			//初始化用户
//			currentUser =new User(userid, userpw, "super");//测试用
			
			UserDBUtil dbUtil = new UserDBUtil();
			currentUser= dbUtil.getUserByID(Integer.parseInt(userid));//实际使用
			
			if(currentUser==null) {//检测用户名是否存在
				AlertBox.display("错误", "用户不存在,请重新输入", "确定");
				return;
			}
			
			if(!currentUser.checkPassword(userpw)) {//检测密码是否正确
				AlertBox.display("错误", "你的密码错误,请重新输入", "确定");
				return;
			}
			
			// 检测用户权限是否足够
			if (currentUser.hasScope(LoginScope)) {
				// 通过用户权限合成文件路径
				String fileName = LoginScope + ".fxml";
				System.out.println(fileName);
				
				ControllerUtils ctrlUtils = new ControllerUtils();
				ctrlUtils.jumpFxml(fileName, LoginButton, true);
			} else {
				AlertBox.display("错误", "你的用户权限低于当前登陆等级,请选择更低的权限", "确定");
			}

		}
		return;

	}

	@FXML
	void pressMortalButton(ActionEvent event) {
		AlertBox.display("提示", "你已选择mortal权限", "确定");
		LoginScope = "mortal";
	}

	@FXML
	void pressGhostButton(ActionEvent event) {
		AlertBox.display("提示", "你已选择ghost权限", "确定");
		LoginScope = "ghost";
	}

	@FXML
	void pressAdminButton(ActionEvent event) {
		AlertBox.display("提示", "你已选择admin权限", "确定");
		LoginScope = "admin";
	}

	@FXML
	void pressYanWangBotton(ActionEvent event) {
		AlertBox.display("提示", "你已选择super权限", "确定");
		LoginScope = "super";
	}

	@FXML
	void registerButton(ActionEvent event) throws Exception {
		ControllerUtils ctrlUtils = new ControllerUtils();
		ctrlUtils.jumpFxml("register.fxml", LoginButton, true);

	}

	@FXML
	void esc(ActionEvent event) {
		// TODO 结束程序
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Main.controllers.put(this.getClass().getSimpleName(), this);
		
	}

}