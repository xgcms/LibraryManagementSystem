package Dao;

import Domain.Book;
import Domain.Borrowing;
import Domain.Reader;
import Util.Helper;
import Util.JdbcHelper;

import java.sql.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class BorrowingDao {
    private static BorrowingDao borrowingDao=
            new BorrowingDao();
    private BorrowingDao(){}
    public static BorrowingDao getInstance(){
        return borrowingDao;
    }
    public Set<Borrowing> findAll() throws SQLException {
        Set<Borrowing> borrowings = new HashSet<Borrowing>();
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM borrowing");
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()){
            Book book = BookDao.getInstance().find(resultSet.getInt("book_id"));
            Reader reader = ReaderDao.getInstance().find(resultSet.getInt("reader_id"));
            //以当前记录中的id,description,no,remarks值为参数，创建Book对象
            Borrowing borrowing = new Borrowing(resultSet.getInt("id"),resultSet.getDate("date"),book,reader);
            //向Books集合中添加Book对象
            borrowings.add(borrowing);
        }
        //关闭资源
        JdbcHelper.close(resultSet,statement,connection);
        return borrowings;
    }

    public Borrowing find(Integer id) throws SQLException{
        Borrowing borrowing = null;
        Connection connection = JdbcHelper.getConn();
        String deleteBorrowing_sql = "SELECT * FROM borrowing WHERE id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(deleteBorrowing_sql);
        //为预编译参数赋值
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        //由于id不能取重复值，故结果集中最多有一条记录
        //若结果集有一条记录，则以当前记录中的id,description,no,remarks值为参数，创建Book对象
        //若结果集中没有记录，则本方法返回null
        if (resultSet.next()){
            Book book = BookDao.getInstance().find(resultSet.getInt("book_id"));
            Reader reader = ReaderDao.getInstance().find(resultSet.getInt("reader_id"));
            borrowing = new Borrowing(resultSet.getInt("id"),resultSet.getDate("date"),book,reader);
        }
        //关闭资源
        JdbcHelper.close(resultSet,preparedStatement,connection);
        return borrowing;
    }

    public Set<Borrowing> findAllByReader(Integer readerId) throws SQLException {
        Set<Borrowing> borrowings = new HashSet<Borrowing>();
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        String selectBorrowing_sql = "SELECT * FROM borrowing WHERE reader_id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(selectBorrowing_sql);
        //为预编译参数赋值
        preparedStatement.setInt(1,readerId);
        ResultSet resultSet = preparedStatement.executeQuery();
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()){
            Book book = BookDao.getInstance().find(resultSet.getInt("book_id"));
            Reader reader = ReaderDao.getInstance().find(resultSet.getInt("reader_id"));
            //以当前记录中的id,description,no,remarks值为参数，创建Book对象
            Borrowing borrowing = new Borrowing(resultSet.getInt("id"),resultSet.getDate("date"),book,reader);
            //向Books集合中添加Book对象
            borrowings.add(borrowing);
        }
        //关闭资源
        JdbcHelper.close(resultSet,preparedStatement,connection);
        return borrowings;
    }

    public Set<Borrowing> findAllByReaderName(String readerName) throws SQLException {
        Set<Borrowing> borrowings = new HashSet<Borrowing>();
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        String selectBorrowing_sql = "SELECT * FROM borrowing WHERE reader_id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(selectBorrowing_sql);
        //为预编译参数赋值
        preparedStatement.setInt(1,ReaderDao.getInstance().findByName(readerName).getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()){
            Book book = BookDao.getInstance().find(resultSet.getInt("book_id"));
            //以当前记录中的id,description,no,remarks值为参数，创建Book对象
            Borrowing borrowing = new Borrowing(resultSet.getInt("id"),resultSet.getDate("date"),book,ReaderDao.getInstance().find(resultSet.getInt("reader_id")));
            //向Books集合中添加Book对象
            borrowings.add(borrowing);
        }
        //关闭资源
        JdbcHelper.close(resultSet,preparedStatement,connection);
        return borrowings;
    }

    public boolean borrowBook(Borrowing borrowing){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int affectedRowNum = 0;
        try {
            connection = JdbcHelper.getConn();
            //关闭自动提交(事件开始）
            connection.setAutoCommit(false);
            if (Integer.parseInt(borrowing.getBook().getTotal())>0) {
                String addBorrowing_sql = "INSERT INTO borrowing (date,book_id,reader_id) VALUES" + " (?,?,?)";
                //在该连接上创建预编译语句对象
                preparedStatement = connection.prepareStatement(addBorrowing_sql);
                //为预编译参数赋值
                preparedStatement.setString(1, Helper.getTime());
                preparedStatement.setInt(2, borrowing.getBook().getId());
                preparedStatement.setInt(3, borrowing.getReader().getId());
                //执行预编译语句，获取添加记录行数并赋值给affectedRowNum
                preparedStatement.executeUpdate();
                String updateBook_sql = " UPDATE Book SET total=?, borrowed_number=? WHERE id=?";
                //在该连接上创建预编译语句对象
                preparedStatement = connection.prepareStatement(updateBook_sql);
                //为预编译参数赋值
                preparedStatement.setInt(1, Integer.parseInt(borrowing.getBook().getTotal()) - 1);
                preparedStatement.setInt(2,Integer.parseInt(borrowing.getBook().getBorrowedNumber()) + 1);
                preparedStatement.setInt(3, borrowing.getBook().getId());
                //执行预编译语句，获取改变记录行数并赋值给affectedRowNum
                affectedRowNum = preparedStatement.executeUpdate();
                //提交当前连接所做的操作（事件以提交结束）
                connection.commit();
            }
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


    //delete方法，根据Book的id值，删除数据库中对应的Book对象
    public boolean returnBook(Borrowing returning){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int affectedRowNum = 0;
        try {
            connection = JdbcHelper.getConn();
            //关闭自动提交(事件开始）
            connection.setAutoCommit(false);
            System.out.println(Helper.differentDays(returning.getDate(),new Date()));
            if (Helper.differentDays(returning.getDate(),new Date()) <= 30) {
                //写sql语句
                String deleteBorrowing_sql = "DELETE FROM borrowing WHERE book_id=? AND reader_id=?";
                //在该连接上创建预编译语句对象
                preparedStatement = connection.prepareStatement(deleteBorrowing_sql);
                //为预编译参数赋值
                preparedStatement.setInt(1, returning.getBook().getId());
                preparedStatement.setInt(2, returning.getReader().getId());
                //执行预编译语句，获取删除记录行数并赋值给affectedRowNum
                affectedRowNum = preparedStatement.executeUpdate();
                String updateBook_sql = " UPDATE Book SET total=? WHERE id=?";
                //在该连接上创建预编译语句对象
                preparedStatement = connection.prepareStatement(updateBook_sql);
                //为预编译参数赋值
                preparedStatement.setInt(1, Integer.parseInt(returning.getBook().getTotal()) + 1);
                preparedStatement.setInt(2, returning.getBook().getId());
                //执行预编译语句，获取改变记录行数并赋值给affectedRowNum
                preparedStatement.executeUpdate();
                //提交当前连接所做的操作（事件以提交结束）
                connection.commit();
            }
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
}
