package Dao;

import Domain.Librarian;
import Util.JdbcHelper;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class LibrarianDao {
    private static LibrarianDao librarianDao=new LibrarianDao();
    private LibrarianDao(){}
    public static LibrarianDao getInstance(){
        return librarianDao;
    }
    public Set<Librarian> findAll() throws SQLException {
        Set<Librarian> librarians = new HashSet<Librarian>();
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        Statement statement = connection.createStatement();
        //执行SQL查询语句并获得结果集对象（游标指向结果集的开头）
        ResultSet resultSet = statement.executeQuery("SELECT * FROM librarian");
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()){
            //创建Librarian对象，根据遍历结果中的id,description,no,remarks，school值
            Librarian librarian = new Librarian(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getString("no"),resultSet.getString("telNumber"));
            //向Librarians集合中添加Librarian对象
            librarians.add(librarian);
        }
        //关闭资源
        JdbcHelper.close(resultSet,statement,connection);
        return librarians;
    }
    public Librarian find(Integer id) throws SQLException{
        Librarian librarian = null;
        Connection connection = JdbcHelper.getConn();
        String selectLibrarian_sql = "SELECT * FROM librarian WHERE id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(selectLibrarian_sql);
        //为预编译参数赋值
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        //由于id不能取重复值，故结果集中最多有一条记录
        //若结果集有一条记录，则以当前记录中的id,description,no,remarks，school值为参数，创建Librarian对象
        //若结果集中没有记录，则本方法返回null
        if (resultSet.next()){
            librarian = new Librarian(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getString("no"),resultSet.getString("telNumber"));
        }
        //关闭资源
        JdbcHelper.close(resultSet,preparedStatement,connection);
        return librarian;
    }

    public boolean update(Librarian librarian) throws SQLException{
        Connection connection = JdbcHelper.getConn();
        //写sql语句
        String updateLibrarian_sql = "UPDATE Librarian SET name=?,no=?,telNumber=? WHERE id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(updateLibrarian_sql);
        //为预编译参数赋值
        preparedStatement.setString(1,librarian.getName());
        preparedStatement.setString(2,librarian.getNo());
        preparedStatement.setString(3,librarian.getTelNumber());
        preparedStatement.setInt(4,librarian.getId());
        //执行预编译语句，获取改变记录行数并赋值给affectedRowNum
        int affectedRows = preparedStatement.executeUpdate();
        //关闭资源
        JdbcHelper.close(preparedStatement,connection);
        return affectedRows>0;
    }

    public boolean add(Librarian librarian){
        Connection connection = null;
        int librarianId = 0;
        PreparedStatement preparedStatement = null;
        int affectedRowNum = 0;
        try {
            connection = JdbcHelper.getConn();
            //关闭自动提交(事件开始）
            connection.setAutoCommit(false);
            String addLibrarian_sql = "INSERT INTO Librarian (name,no,telNumber) VALUES"+" (?,?,?)";
            //在该连接上创建预编译语句对象
            preparedStatement = connection.prepareStatement(addLibrarian_sql);
            //为预编译参数赋值
            preparedStatement.setString(1,librarian.getName());
            preparedStatement.setString(2,librarian.getNo());
            preparedStatement.setString(3,librarian.getTelNumber());
            //执行预编译语句，获取添加记录行数并赋值给affectedRowNum
            affectedRowNum=preparedStatement.executeUpdate();
            //获取最后一个插入的id
            ResultSet resultSet = preparedStatement.executeQuery("select last_insert_id()");
            if (resultSet.next()) {
                librarianId = resultSet.getInt("last_insert_id()");
            }
            String addUser_sql = "INSERT INTO user_librarian (username,password,librarian_id) VALUES" + " (?,?,?)";
            //在该连接上创建预编译语句对象
            preparedStatement = connection.prepareStatement(addUser_sql);
            //为预编译参数赋值
            preparedStatement.setString(1, librarian.getNo());
            preparedStatement.setString(2, librarian.getNo());
            preparedStatement.setInt(3, librarianId);
            preparedStatement.executeUpdate();
            //提交当前连接所做的操作（事件以提交结束）
            connection.commit();
        }catch (SQLException e){
            e.printStackTrace();
            try{
                //回滚当前连接所作的操作
                if (connection != null){
                    //事件以回滚结束
                    connection.rollback();
                }
            }catch (SQLException e1){
                e1.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
            try{
                //回滚当前连接所作的操作
                if (connection != null){
                    //事件以回滚结束
                    connection.rollback();
                }
            }catch (SQLException e1){
                e1.printStackTrace();
            }
        } finally {
            try{
                //恢复自动提交
                if (connection!=null){
                    connection.setAutoCommit(true);
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
            //关闭资源
            JdbcHelper.close(preparedStatement,connection);
        }
        return affectedRowNum>0;
    }

    public boolean delete(Integer id){
        Connection connection = null;
        PreparedStatement pstmt=null;
        int affectedRowNum = 0;
        try {
            connection = JdbcHelper.getConn();
            //关闭自动提交(事件开始）
            connection.setAutoCommit(false);
            //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
            pstmt = connection.prepareStatement("DELETE FROM user_librarian WHERE librarian_id = ?");
            //为预编译的语句参数赋值
            pstmt.setInt(1, id);
            //执行预编译对象的executeUpdate()方法，获取删除记录的行数
            pstmt.executeUpdate();
            //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
            pstmt = connection.prepareStatement("DELETE FROM librarian WHERE id = ?");
            //为预编译的语句参数赋值
            pstmt.setInt(1, id);
            //执行预编译对象的executeUpdate()方法，获取删除记录的行数
            affectedRowNum = pstmt.executeUpdate();
            //提交当前连接所做的操作（事件以提交结束）
            connection.commit();
        }catch (SQLException e){
            System.out.println(e.getMessage()+"\n errorCode="+e.getErrorCode());
            try{
                //回滚当前连接所作的操作
                if (connection != null){
                    //事件以回滚结束
                    connection.rollback();
                }
            }catch (SQLException e1){
                e1.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
            try{
                //回滚当前连接所作的操作
                if (connection != null){
                    //事件以回滚结束
                    connection.rollback();
                }
            }catch (SQLException e1){
                e1.printStackTrace();
            }
        }finally {
            try{
                //恢复自动提交
                if (connection!=null){
                    connection.setAutoCommit(true);
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
            //关闭资源
            JdbcHelper.close(pstmt,connection);
        }
        return affectedRowNum > 0;
    }
    public Librarian addWithSP(Librarian librarian) throws SQLException,ClassNotFoundException{
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        //根据连接对象准备可调用语句对象，sp_addSchool为存贮过程名称，后面为4个参数
        CallableStatement callableStatement = connection.prepareCall("{CALL sp_addLibrarian (?,?,?,?)}");
        //为参数赋值,将第4个参数设置为输出参数，类型为长整型（数据库的数据类型）
        callableStatement.registerOutParameter(4, Types.BIGINT);
        callableStatement.setString(1,librarian.getName());
        callableStatement.setString(2,librarian.getNo());
        callableStatement.setString(3,librarian.getTelNumber());
        //执行可调用语句callableStatement
        callableStatement.execute();
        //获得第4个参数的值：数据库为该记录自动生成的id
        int id = callableStatement.getInt(4);
        //为参数school的id字段赋值
        librarian.setId(id);
        callableStatement.close();
        connection.close();
        return librarian;
    }
}
