package Dao;
import Domain.Book;
import Util.JdbcHelper;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public final class BookDao {
    private static BookDao bookDao=
            new BookDao();
    private BookDao(){}
    public static BookDao getInstance(){
        return bookDao;
    }

    public Set<Book> findAll() throws SQLException{
        Set<Book> books = new HashSet<Book>();
        //获得连接对象
        Connection connection = JdbcHelper.getConn();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM book");
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()){
            //以当前记录中的id,description,no,remarks值为参数，创建Book对象
            Book book = new Book(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getString("no"),resultSet.getString("writer"),resultSet.getString("press"),resultSet.getString("address"),resultSet.getString("total"),resultSet.getString("borrowed_number"));
            //向Books集合中添加Book对象
            books.add(book);
        }
        //关闭资源
        JdbcHelper.close(resultSet,statement,connection);
        return books;
    }

    public Book find(Integer id) throws SQLException{
        Book book = null;
        Connection connection = JdbcHelper.getConn();
        String findBook_sql = "SELECT * FROM Book WHERE id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(findBook_sql);
        //为预编译参数赋值
        preparedStatement.setInt(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        //由于id不能取重复值，故结果集中最多有一条记录
        //若结果集有一条记录，则以当前记录中的id,description,no,remarks值为参数，创建Book对象
        //若结果集中没有记录，则本方法返回null
        if (resultSet.next()){
            book = new Book(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getString("no"),resultSet.getString("writer"),resultSet.getString("press"),resultSet.getString("address"),resultSet.getString("total"),resultSet.getString("borrowed_number"));
        }
        //关闭资源
        JdbcHelper.close(resultSet,preparedStatement,connection);
        return book;
    }

    public Set<Book> findByBookName(String bookName) throws SQLException{
        Set<Book> books = new HashSet<Book>();
        Connection connection = JdbcHelper.getConn();
        String findBook_sql = "SELECT * FROM Book WHERE name=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(findBook_sql);
        //为预编译参数赋值
        preparedStatement.setString(1,bookName);
        ResultSet resultSet = preparedStatement.executeQuery();
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()){
            //以当前记录中的id,description,no,remarks值为参数，创建Book对象
            Book book = new Book(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getString("no"),resultSet.getString("writer"),resultSet.getString("press"),resultSet.getString("address"),resultSet.getString("total"),resultSet.getString("borrowed_number"));
            //向Books集合中添加Book对象
            books.add(book);
        }
        //关闭资源
        JdbcHelper.close(resultSet,preparedStatement,connection);
        return books;
    }

    public Set<Book> findByWriter(String writer) throws SQLException{
        Set<Book> books = new HashSet<Book>();
        Connection connection = JdbcHelper.getConn();
        String findBook_sql = "SELECT * FROM Book WHERE writer=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(findBook_sql);
        //为预编译参数赋值
        preparedStatement.setString(1,writer);
        ResultSet resultSet = preparedStatement.executeQuery();
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()){
            //以当前记录中的id,description,no,remarks值为参数，创建Book对象
            Book book = new Book(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getString("no"),resultSet.getString("writer"),resultSet.getString("press"),resultSet.getString("address"),resultSet.getString("total"),resultSet.getString("borrowed_number"));
            //向Books集合中添加Book对象
            books.add(book);
        }
        //关闭资源
        JdbcHelper.close(resultSet,preparedStatement,connection);
        return books;
    }

    public Set<Book> findByPress(String press) throws SQLException{
        Set<Book> books = new HashSet<Book>();
        Connection connection = JdbcHelper.getConn();
        String findBook_sql = "SELECT * FROM Book WHERE press=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(findBook_sql);
        //为预编译参数赋值
        preparedStatement.setString(1,press);
        ResultSet resultSet = preparedStatement.executeQuery();
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()){
            //以当前记录中的id,description,no,remarks值为参数，创建Book对象
            Book book = new Book(resultSet.getInt("id"),resultSet.getString("name"),resultSet.getString("no"),resultSet.getString("writer"),resultSet.getString("press"),resultSet.getString("address"),resultSet.getString("total"),resultSet.getString("borrowed_number"));
            //向Books集合中添加Book对象
            books.add(book);
        }
        //关闭资源
        JdbcHelper.close(resultSet,preparedStatement,connection);
        return books;
    }

    public Set<Book> findBookByAddress(String address) throws SQLException{
        Set<Book> books = new HashSet<Book>();
        Connection connection = JdbcHelper.getConn();
        String findBook_sql = "SELECT * FROM Book WHERE address like ?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(findBook_sql);
        //为预编译参数赋值
        preparedStatement.setString(1,address);
        ResultSet resultSet = preparedStatement.executeQuery();
        //若结果集仍然有下一条记录，则执行循环体
        while (resultSet.next()){
            //以当前记录中的id,description,no,remarks值为参数，创建Book对象
            Book book = new Book(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("no"),
                    resultSet.getString("writer"),
                    resultSet.getString("press"),
                    resultSet.getString("address"),
                    resultSet.getString("total"));
            //向Books集合中添加Book对象
            books.add(book);
        }
        //关闭资源
        JdbcHelper.close(resultSet,preparedStatement,connection);
        return books;
    }

    public boolean add(Book book) throws SQLException,ClassNotFoundException{
        Connection connection = JdbcHelper.getConn();
        String addBook_sql = "INSERT INTO Book (address, name, no, press, total, writer,borrowed_number) VALUES"+" (?,?,?,?,?,?,?)";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(addBook_sql);
        //为预编译参数赋值
        preparedStatement.setString(1,book.getAddress());
        preparedStatement.setString(2,book.getName());
        preparedStatement.setString(3,book.getNo());
        preparedStatement.setString(4,book.getPress());
        preparedStatement.setString(5,book.getTotal());
        preparedStatement.setString(6,book.getWriter());
        System.out.println(book.getBorrowedNumber());
        preparedStatement.setString(7,book.getBorrowedNumber());
        //执行预编译语句，获取添加记录行数并赋值给affectedRowNum
        int affectedRowNum=preparedStatement.executeUpdate();
        //关闭资源
        JdbcHelper.close(preparedStatement,connection);
        return affectedRowNum>0;
    }


    //delete方法，根据Book的id值，删除数据库中对应的Book对象
    public boolean delete(Book book) throws ClassNotFoundException,SQLException{
        Connection connection = JdbcHelper.getConn();
        //写sql语句
        String deleteBook_sql = "DELETE FROM Book WHERE id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(deleteBook_sql);
        //为预编译参数赋值
        preparedStatement.setInt(1,book.getId());
        //执行预编译语句，获取删除记录行数并赋值给affectedRowNum
        int affectedRows = preparedStatement.executeUpdate();
        //关闭资源
        JdbcHelper.close(preparedStatement,connection);
        return affectedRows>0;
    }

    public boolean update(Book book) throws ClassNotFoundException,SQLException{
        Connection connection = JdbcHelper.getConn();
        //写sql语句
        String updateBook_sql = " UPDATE Book SET address=?, name=?, no=?, press=?, total=?, writer=? WHERE id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(updateBook_sql);
        //为预编译参数赋值
        preparedStatement.setString(1,book.getAddress());
        preparedStatement.setString(2,book.getName());
        preparedStatement.setString(3,book.getNo());
        preparedStatement.setString(4,book.getPress());
        preparedStatement.setString(5,book.getTotal());
        preparedStatement.setString(6,book.getWriter());
        preparedStatement.setInt(7,book.getId());
        //执行预编译语句，获取改变记录行数并赋值给affectedRowNum
        int affectedRows = preparedStatement.executeUpdate();
        //关闭资源
        JdbcHelper.close(preparedStatement,connection);
        return affectedRows>0;
    }
}
