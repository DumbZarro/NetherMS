package nether.application;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @ClassName: Main
 * @Description:程序入口
 */
public class Main extends Application {

	// 控制器容器
	public static Map<String, Object> controllers = new HashMap<String, Object>();

	/**
	 * @Title: main
	 * @Description: 启动程序,理论上可不写,会自动调用launch
	 * @param args
	 * @return: void
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * @Title: start
	 * @Description:
	 * @param arg0
	 * @throws Exception
	 */
	@Override
	public void start(Stage loginStage) throws Exception {
		// Stage>Scene>pane>Node
		Parent root = FXMLLoader.load(getClass().getResource("./source/login.fxml"));
		Scene scene = new Scene(root);
		loginStage.setTitle("地府管理系统");
		loginStage.setScene(scene);
		loginStage.show();
	}
}
