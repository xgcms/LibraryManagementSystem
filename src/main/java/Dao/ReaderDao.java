package Dao;
import Domain.Reader;
import Util.JdbcHelper;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public final class ReaderDao {
    private static ReaderDao readerDao=
            new ReaderDao();
    private ReaderDao(){}
    public static ReaderDao getInstance(){
        return readerDao;
    }

    public Set<Reader> findAll() throws SQLException{
        Set<Reader> readers = new HashSet<Reader>();
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Reader");
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()){
            //以当前记录中的id,description,no,remarks值为参数，创建Reader对象
            Reader reader = new Reader(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getString("no"),resultSet.getString("age"),resultSet.getString("sex"),resultSet.getString("department"),resultSet.getString("telNumber"));
            //向Readers集合中添加Reader对象
            readers.add(reader);
        }
        //关闭资源
        JdbcHelper.close(resultSet,statement,connection);
        return readers;
    }

    public Reader find(Integer id) throws SQLException{
        Reader reader = null;
        Connection connection = JdbcHelper.getConn();
        String deleteReader_sql = "SELECT * FROM Reader WHERE id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(deleteReader_sql);
        //为预编译参数赋值
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        //由于id不能取重复值，故结果集中最多有一条记录
        //若结果集有一条记录，则以当前记录中的id,description,no,remarks值为参数，创建Reader对象
        //若结果集中没有记录，则本方法返回null
        if (resultSet.next()){
            reader = new Reader(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getString("no"),resultSet.getString("age"),resultSet.getString("sex"),resultSet.getString("department"),resultSet.getString("telNumber"));
        }
        //关闭资源
        JdbcHelper.close(resultSet,preparedStatement,connection);
        return reader;
    }
    public Reader findByName(String name) throws SQLException{
        Reader reader = null;
        Connection connection = JdbcHelper.getConn();
        String findReader_sql = "SELECT * FROM Reader WHERE name=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(findReader_sql);
        //为预编译参数赋值
        preparedStatement.setString(1,name);
        ResultSet resultSet = preparedStatement.executeQuery();
        //由于id不能取重复值，故结果集中最多有一条记录
        //若结果集有一条记录，则以当前记录中的id,description,no,remarks值为参数，创建Reader对象
        //若结果集中没有记录，则本方法返回null
        if (resultSet.next()){
            reader = new Reader(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getString("no"),resultSet.getString("age"),resultSet.getString("sex"),resultSet.getString("department"),resultSet.getString("telNumber"));
        }
        //关闭资源
        JdbcHelper.close(resultSet,preparedStatement,connection);
        return reader;
    }

    public boolean add(Reader reader){
        Connection connection = null;
        int reader_id = 0;
        PreparedStatement preparedStatement = null;
        int affectedRowNum = 0;
        try {
            connection = JdbcHelper.getConn();
            //关闭自动提交(事件开始）
            connection.setAutoCommit(false);
            String addReader_sql = "INSERT INTO Reader (no, age, department, name, sex, telnumber) VALUES"+" (?,?,?,?,?,?)";
            //在该连接上创建预编译语句对象
            preparedStatement = connection.prepareStatement(addReader_sql);
            //为预编译参数赋值
            preparedStatement.setString(1,reader.getNo());
            preparedStatement.setString(2,reader.getAge());
            preparedStatement.setString(3,reader.getDepartment());
            preparedStatement.setString(4,reader.getName());
            preparedStatement.setString(5,reader.getSex());
            preparedStatement.setString(6,reader.getTelNumber());
            //执行预编译语句，获取添加记录行数并赋值给affectedRowNum
            preparedStatement.executeUpdate();
            //获取最后一个插入的id
            ResultSet resultSet = preparedStatement.executeQuery("select last_insert_id()");
            if (resultSet.next()) {
                reader_id = resultSet.getInt("last_insert_id()");
            }
            String addUser_sql = "INSERT INTO user_reader (username,password,reader_id) VALUES" + " (?,?,?)";
            //在该连接上创建预编译语句对象
            preparedStatement = connection.prepareStatement(addUser_sql);
            //为预编译参数赋值
            preparedStatement.setString(1, reader.getNo());
            preparedStatement.setString(2, reader.getNo());
            preparedStatement.setInt(3, reader_id);
            affectedRowNum = preparedStatement.executeUpdate();
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
            pstmt = connection.prepareStatement("DELETE FROM user_reader WHERE reader_id = ?");
            //为预编译的语句参数赋值
            pstmt.setInt(1, id);
            //执行预编译对象的executeUpdate()方法，获取删除记录的行数
            pstmt.executeUpdate();
            //创建PreparedStatement接口对象，包装编译后的目标代码（可以设置参数，安全性高）
            pstmt = connection.prepareStatement("DELETE FROM reader WHERE ID = ?");
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


    public boolean update(Reader reader) throws ClassNotFoundException,SQLException{
        Connection connection = JdbcHelper.getConn();
        //写sql语句
        String updateReader_sql = " UPDATE Reader SET no=?, age=?, department=?, name=?, sex=?, telnumber=? WHERE id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(updateReader_sql);
        //为预编译参数赋值
        preparedStatement.setString(1,reader.getNo());
        preparedStatement.setString(2,reader.getAge());
        preparedStatement.setString(3,reader.getDepartment());
        preparedStatement.setString(4,reader.getName());
        preparedStatement.setString(5,reader.getSex());
        preparedStatement.setString(6,reader.getTelNumber());
        preparedStatement.setInt(7,reader.getId());
        //执行预编译语句，获取改变记录行数并赋值给affectedRowNum
        int affectedRows = preparedStatement.executeUpdate();
        //关闭资源
        JdbcHelper.close(preparedStatement,connection);
        return affectedRows>0;
    }
}

