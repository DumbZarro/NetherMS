package nether;

import java.util.ArrayList;
import java.util.Date;

import nether.common.DateUtil;
import nether.common.MD5Util;
import nether.common.Scope.Scope;
import nether.dao.User;
import nether.dao.UserDBUtil;

public class Test {

	public static void _main(String[] args) {
		UserDBUtil utool = new UserDBUtil();

		// 添加用户测试
		utool.storeUser(
				new User(0, "YanWang", MD5Util.md5("YanWang666"), (new Scope("super")).toHuffmanCode(), "", "", ""));
		utool.storeUser(
				new User(1, "NiuTou", MD5Util.md5("NiuTou666"), (new Scope("admin")).toHuffmanCode(), "", "", ""));
		User mamian = new User(2, "MaMian", MD5Util.md5("NiuTou666"), (new Scope("admin")).toHuffmanCode(), "", "", "");
		utool.storeUser(mamian);
		utool.storeUser(new User(3, "LSP", MD5Util.md5("LSP2333"), (new Scope("ghost")).toHuffmanCode(),
				DateUtil.Date2String(new Date()), "牡丹花下死", "青青草原"));

//		System.out.print(MD5Util.md5("NiuTou666"));
		// 获取单个测试
		System.out.println("=======get single item===");
		User user = utool.getUserByID(1);
		System.out.println(user.toString());
		System.out.println(user.hasScope("admin"));

		// 全部输出测试
		System.out.println("=========================");
		ArrayList<User> users = utool.getAllUsers();
		for (User u : users) {
			System.out.println(u.toString());
			System.out.println(u.hasScope("admin"));
			System.out.println(u.checkPassword("NiuTou666"));
		}

		// 修改密码测试
		System.out.println("======Update Password Test===========");
		mamian.setPassword("NiuTou250");
		utool.updateUser(mamian);
		User mamianCopy = utool.getUserByID(mamian.getId());
		System.out.println(mamianCopy.checkPassword("NiuTou666"));
		System.out.println(mamianCopy.checkPassword("NiuTou250"));

//		Scope.ScopeHuffmanTable.tripHfmTree(Scope.ScopeHuffmanTable.hfmRoot);

	}

}
