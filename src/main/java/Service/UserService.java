package Service;

import Dao.UserDao;
import Domain.User;

import java.sql.SQLException;

public final class UserService {
	private UserDao userDao = UserDao.getInstance();
	//本类的一个对象引用，保存自身对象
	private static UserService userService = new UserService();
	//私有的构造方法，防止其它类创建它的对象
	private UserService() {}
	//静态方法，返回本类的惟一对象
	public static UserService getInstance(){
		return UserService.userService;
	}
	//定义findByUserName方法
	public User findByReaderId(int readerId) throws SQLException {
		return userDao.findByReaderId(readerId);
	}
	public User findByLibrarianId(int librarianId) throws SQLException {
		return userDao.findByLibrarianId(librarianId);
	}
	//定义login方法
	public User login(String role,String username, String password) throws SQLException {
		return userDao.login(role,username,password);
	}
	//定义changePassword方法
	public boolean changePassword(User user) throws SQLException {
		return userDao.changePassword(user);
	}
}
