package nether.application;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import nether.dao.User;
import nether.dao.UserDBUtil;

public class AlertBox {

	public static void display(String title, String message, String buttonStirng) {
		Stage window = new Stage();
		window.setTitle(title);
		// modality要使用Modality.APPLICATION_MODEL
		window.initModality(Modality.APPLICATION_MODAL);
		window.setMinWidth(300);
		window.setMinHeight(150);

		Button button = new Button(buttonStirng);
		button.setOnAction(e -> window.close());

		Label label = new Label(message);

		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, button);
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout);
		window.setScene(scene);
		// 使用showAndWait()先处理这个窗口，而如果不处理，main中的那个窗口不能响应
		window.showAndWait();
	}

	public static void queryLifeBox() {
		Stage window = new Stage();
		window.setTitle("阳寿查询");
		// modality要使用Modality.APPLICATION_MODEL
		window.initModality(Modality.APPLICATION_MODAL);
		window.setMinWidth(300);
		window.setMinHeight(150);

		Label label = new Label("请输入查询目标的ID");

		TextField inputID = new TextField();
		inputID.setPromptText("在这里输入ID");
		inputID.setMinWidth(180);

		Button button = new Button("查询");
		button.setOnAction((ActionEvent e) -> {
			UserDBUtil dbUtil = new UserDBUtil();
			if (inputID.getText() == null) {
				AlertBox.display("错误", "ID输入不能为空", "确定");
			}
			User currentUser = dbUtil.getUserByID(Integer.parseInt(inputID.getText()));
			if (currentUser == null) {// 检测用户名是否存在
				AlertBox.display("错误", "用户不存在,请重新输入", "确定");
				return;
			}
			// TODO 好像user并没有阳寿这个属性?
			display("成功", currentUser.getUsername()+"能活到" + currentUser.getBirthdate(), "确定");
		});

		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, inputID, button);
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout);
		window.setScene(scene);
		// 使用showAndWait()先处理这个窗口，而如果不处理，main中的那个窗口不能响应
		window.showAndWait();
	}

	public static void AssignRight() {

		Stage window = new Stage();
		window.setTitle("权限分配");
		// modality要使用Modality.APPLICATION_MODEL
		window.initModality(Modality.APPLICATION_MODAL);
		window.setMinWidth(300);
		window.setMinHeight(150);

		Label label = new Label("请输入需要重新分配权限的目标的ID");
		TextField inputID = new TextField();
		inputID.setPromptText("在这里输入ID");
		inputID.setMinWidth(180);

		Label label2 = new Label("你希望该用户拥有什么权限");
		ToggleGroup group = new ToggleGroup();
		RadioButton rb1 = new RadioButton("mortal");
		rb1.setToggleGroup(group);
		rb1.setSelected(true);
		RadioButton rb2 = new RadioButton("ghost");
		rb2.setToggleGroup(group);
		RadioButton rb3 = new RadioButton("admin");
		rb3.setToggleGroup(group);

		
		
		Button button = new Button("更改");
		button.setOnAction((ActionEvent e) -> {
			
			//判断用户名格式
			UserDBUtil dbUtil = new UserDBUtil();
			if (inputID.getText() == null) {
				AlertBox.display("错误", "令牌输入不能为空", "确定");
				return;
			}
			if (inputID.getText().length() >= 9) {
				AlertBox.display("错误", "超出令牌范围", "确定");
				return;
			}
			
			

			User currentUser = dbUtil.getUserByID(Integer.parseInt(inputID.getText()));
			if (currentUser != null) {// 检测用户名是否存在
				//判断选择的权限
				String scopeString;
				if (rb1.isSelected()) {
					scopeString = new String("mortal");
				}
				else if (rb2.isSelected()) {
					scopeString = new String("ghost");
					currentUser.getScope().delScope("admin");//确保能降
					currentUser.getScope().addScope("ghost");//确保能到ghost
				}
				else {
					scopeString = new String("admin");
					currentUser.getScope().addScope("admin");
				}
				
				//好像只能从高往下,不能从低往上
//				currentUser.setScope(new Scope(scopeString));
				
				UserDBUtil.updateUser(currentUser);
				display("成功", "成功将它的权限设置为" + scopeString, "确定");
				
				inputID.setText("");//清空输入
				
				return;
			}
			AlertBox.display("错误", "用户不存在,请重新输入", "确定");

		});

		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, inputID, label2, rb1, rb2, rb3, button);
		layout.setAlignment(Pos.CENTER);

		Scene scene = new Scene(layout);
		window.setScene(scene);
		// 使用showAndWait()先处理这个窗口，而如果不处理，main中的那个窗口不能响应
		window.showAndWait();
	}
}