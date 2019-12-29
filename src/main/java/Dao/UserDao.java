package Dao;

import Domain.Librarian;
import Domain.Reader;
import Domain.User;
import Util.JdbcHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class UserDao {
    private static UserDao userDao=new UserDao();
    private UserDao(){}
    public static UserDao getInstance(){
        return userDao;
    }
    //根据id值查找
    public User findByReaderId(Integer readerId) throws SQLException{
        User user = null;
        Connection connection = JdbcHelper.getConn();
        String selectUser_sql = "SELECT * FROM user_reader WHERE reader_id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(selectUser_sql);
        //为预编译参数赋值
        preparedStatement.setInt(1,readerId);
        ResultSet resultSet = preparedStatement.executeQuery();
        //由于id不能取重复值，故结果集中最多有一条记录
        //若结果集有一条记录，则以当前记录中的id,username,password,teacher,logintime值为参数，创建User对象
        //若结果集中没有记录，则本方法返回null
        if (resultSet.next()){
            Reader reader = ReaderDao.getInstance().find(resultSet.getInt("reader_id"));
            user = new User(resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    reader);
        }
        //关闭资源
        JdbcHelper.close(resultSet,preparedStatement,connection);
        return user;
    }

    //根据id值查找
    public User findByLibrarianId(Integer librarianId) throws SQLException{
        User user = null;
        Connection connection = JdbcHelper.getConn();
        String selectUser_sql = "SELECT * FROM user_librarian WHERE librarian_id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(selectUser_sql);
        //为预编译参数赋值
        preparedStatement.setInt(1,librarianId);
        ResultSet resultSet = preparedStatement.executeQuery();
        //由于id不能取重复值，故结果集中最多有一条记录
        //若结果集有一条记录，则以当前记录中的id,username,password,teacher,logintime值为参数，创建User对象
        //若结果集中没有记录，则本方法返回null
        if (resultSet.next()){
            Librarian librarian = LibrarianDao.getInstance().find(resultSet.getInt("librarian_id"));
            user = new User(resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    librarian);
        }
        //关闭资源
        JdbcHelper.close(resultSet,preparedStatement,connection);
        return user;
    }

    //登录
    public User login(String role,String username,String password) throws SQLException {
        //声明变量user
        User user = null;
        //连接对象
        Connection connection = JdbcHelper.getConn();
        PreparedStatement pstmt=null;
        ResultSet resultSet = null;
        if (role.equals("读者")) {
            //sql语句
            String login_sql = "SELECT * FROM user_reader where username = ? AND password = ?";
            //预编译语句
            pstmt = connection.prepareStatement(login_sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            //执行返回结果集
            resultSet = pstmt.executeQuery();
            //判断结果集是否有东西
            if (resultSet.next()) {
                user = UserDao.getInstance().findByReaderId(resultSet.getInt("reader_id"));
            }
        }else if (role.equals("管理员")){
            //sql语句
            String login_sql = "SELECT * FROM user_librarian where username = ? AND password = ?";
            //预编译语句
            pstmt = connection.prepareStatement(login_sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            //执行返回结果集
            resultSet = pstmt.executeQuery();
            //判断结果集是否有东西
            if (resultSet.next()) {
                user = UserDao.getInstance().findByLibrarianId(resultSet.getInt("librarian_id"));
            }
        }
        //关闭资源
        JdbcHelper.close(pstmt,connection);
        return user;
    }

    //改密码
    public boolean changePassword(User user) throws SQLException {
        Connection connection = JdbcHelper.getConn();
        PreparedStatement preparedStatement = null;
        int affectedRows = 0;
        System.out.println(user.getRole());
        if (user.getRole().equals("管理员")) {
            //写sql语句
            String changeUserPassword_sql = "UPDATE user_librarian SET password=? WHERE librarian_id=?";
            //在该连接上创建预编译语句对象
            preparedStatement = connection.prepareStatement(changeUserPassword_sql);
            //为预编译参数赋值
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setInt(2, user.getId());
            //执行预编译语句，获取改变记录行数并赋值给affectedRowNum
            affectedRows = preparedStatement.executeUpdate();
        }else if (user.getRole().equals("读者")){
            //写sql语句
            String changeUserPassword_sql = "UPDATE user_reader SET password=? WHERE reader_id=?";
            //在该连接上创建预编译语句对象
            preparedStatement = connection.prepareStatement(changeUserPassword_sql);
            //为预编译参数赋值
            preparedStatement.setString(1,user.getPassword());
            preparedStatement.setInt(2,user.getId());
            //执行预编译语句，获取改变记录行数并赋值给affectedRowNum
            affectedRows = preparedStatement.executeUpdate();
        }
        System.out.println();
        //关闭资源
        JdbcHelper.close(preparedStatement,connection);
        return affectedRows>0;
    }
}

