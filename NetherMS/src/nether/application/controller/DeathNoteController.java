package nether.application.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import nether.application.AlertBox;
import nether.application.ControllerUtils;
import nether.application.DBUser;
import nether.dao.User;
import nether.dao.UserDBUtil;

public class DeathNoteController implements Initializable {

	@FXML
	private TextField Input;

	@FXML
	private TableView<DBUser> mortalTable;
	@FXML
	private TableColumn<DBUser, Integer> mortalID;
	@FXML
	private TableColumn<DBUser, String> mortalName;
	@FXML
	private TableColumn<DBUser, String> mortalDeathCause;
	@FXML
	private TableColumn<DBUser, String> mortalReincarnation;
	@FXML
	private TableColumn<DBUser, String> mortalOperation;

	@FXML
	private TableView<DBUser> reliveTable;
	@FXML
	private TableColumn<DBUser, Integer> reliveID;
	@FXML
	private TableColumn<DBUser, String> reliveName;
	@FXML
	private TableColumn<DBUser, String> reliveDeathCause;
	@FXML
	private TableColumn<DBUser, String> reliveReincarnation;
	@FXML
	private TableColumn<DBUser, String> reliveOperation;

	@FXML
	private TableView<DBUser> ghostTable;
	@FXML
	private TableColumn<DBUser, Integer> GhostID;
	@FXML
	private TableColumn<DBUser, String> GhostName;
	@FXML
	private TableColumn<DBUser, String> GhostDeathCause;
	@FXML
	private TableColumn<DBUser, String> GhostReincarnation;
	@FXML
	private TableColumn<DBUser, String> GhostOperation;

	@FXML
	void pressedKey(KeyEvent keyEvent) {
		// TODO 按钮
		if (keyEvent.getCode() == KeyCode.ENTER)  {

            UserDBUtil dbUtil = new UserDBUtil();
			if (Input.getText() == null) {
				AlertBox.display("错误", "ID输入不能为空", "确定");
			}
			User currentUser = dbUtil.getUserByID(Integer.parseInt(Input.getText()));
			if (currentUser == null) {// 检测用户名是否存在
				AlertBox.display("错误", "用户不存在,请重新输入", "确定");
				return;
			}
			// TODO 好像user并没有阳寿这个属性?
			AlertBox.display("成功", currentUser.getUsername()+"能活到" + currentUser.getBirthdate(), "确定");
			
            Input.setText("");
        }

	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// 获取所有用户
		UserDBUtil dbUtil = new UserDBUtil();
		ArrayList<User> usersList = dbUtil.getAllUsers();
		if(usersList.get(0).getId()==100000){//阎王不显示在上面
			usersList.remove(0);
		}
		// TODO 将userList分类表示
		ArrayList<User> mortalList = new ArrayList<User>();
		ArrayList<User> adminList= new ArrayList<User>();
		ArrayList<User> ghostList= new ArrayList<User>();
		
		for (User item:usersList) {//用户分流
			if(item.hasScope("admin")) {//admin
				adminList.add(item);
				System.out.println("adminList+"+item.getScope());
			}
			else if(item.hasScope("ghost")) {//ghost
				ghostList.add(item);
				System.out.println("ghostList+"+item.getScope());
			}else {//mortal
				mortalList.add(item);
				System.out.println("mortalList+"+item.getScope());
			}
		}
		

		// mortal表载入
		ObservableList<DBUser> mortalData = FXCollections
				.observableArrayList(ControllerUtils.convertToDBUserList(mortalList));

		mortalID.setCellValueFactory(new PropertyValueFactory<>("id"));
		mortalName.setCellValueFactory(new PropertyValueFactory<>("name"));
		mortalDeathCause.setCellValueFactory(new PropertyValueFactory<>("deathCause"));
		mortalReincarnation.setCellValueFactory(new PropertyValueFactory<>("reincarnation"));
		mortalOperation.setCellValueFactory(new PropertyValueFactory<>("operation"));

		mortalTable.setItems(mortalData);
		mortalTable.getColumns().addAll(mortalID, mortalName, mortalDeathCause, mortalReincarnation, mortalOperation);

		// relive表载入  后改admin表

		ObservableList<DBUser> reliveData = FXCollections
				.observableArrayList(ControllerUtils.convertToDBUserList(adminList));

		reliveID.setCellValueFactory(new PropertyValueFactory<>("id"));
		reliveName.setCellValueFactory(new PropertyValueFactory<>("name"));
		reliveDeathCause.setCellValueFactory(new PropertyValueFactory<>("deathCause"));
		reliveReincarnation.setCellValueFactory(new PropertyValueFactory<>("reincarnation"));
		reliveOperation.setCellValueFactory(new PropertyValueFactory<>("operation"));

		reliveTable.setItems(reliveData);
		reliveTable.getColumns().addAll(reliveID, reliveName, reliveDeathCause, reliveReincarnation, reliveOperation);

		// ghost表载入
		ObservableList<DBUser> ghostData = FXCollections
				.observableArrayList(ControllerUtils.convertToDBUserList(ghostList));

		GhostID.setCellValueFactory(new PropertyValueFactory<>("id"));
		GhostName.setCellValueFactory(new PropertyValueFactory<>("name"));
		GhostDeathCause.setCellValueFactory(new PropertyValueFactory<>("deathCause"));
		GhostReincarnation.setCellValueFactory(new PropertyValueFactory<>("reincarnation"));
		GhostOperation.setCellValueFactory(new PropertyValueFactory<>("operation"));

		ghostTable.setItems(ghostData);
		ghostTable.getColumns().addAll(GhostID, GhostName, GhostDeathCause, GhostReincarnation, GhostOperation);

	}

}
