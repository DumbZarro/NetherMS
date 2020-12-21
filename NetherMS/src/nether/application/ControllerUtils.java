package nether.application;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import nether.dao.User;

public class ControllerUtils {
	/**
	 * @Title: jumpFxml
	 * @Description: 跳转模版
	 * @param targetFile
	 * @param pressedButton
	 * @param close
	 * @return: void
	 */
	public void jumpFxml(String targetFile,Button pressedButton,boolean closeParent) {
		//获取目标界面
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("./source/" + targetFile));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 创建新舞台
		Scene scene = new Scene(root);
		Stage mainStage = new Stage();
		mainStage.setTitle("地府管理系统");
		mainStage.setScene(scene);// 设置界面

		
		if(closeParent) {//showType为True时
			// 删掉旧界面
			Stage stage = (Stage) pressedButton.getScene().getWindow();// 获取按钮所在的舞台
			stage.close();
			mainStage.show();
		}else {
			mainStage.showAndWait();
		}
	}
	
	public static ArrayList<DBUser> convertToDBUserList(ArrayList<User> userList) {
		ArrayList<DBUser> DBList= new ArrayList<DBUser>(userList.size());
		for (int i = 0; i < userList.size();i++) {
			DBList.add(new DBUser(userList.get(i)));
		}
		return DBList;
	}
	
}
