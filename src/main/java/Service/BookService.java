package Service;

import Dao.BookDao;
import Domain.Book;

import java.sql.SQLException;
import java.util.Collection;
//定义类DegreeService
public final class BookService {
    //获得degreeDao私有静态对象
    private static BookDao bookDao
            = BookDao.getInstance();
    //创建静态对象degreeService，并赋值给变量degreeService
    private static BookService bookService
            =new BookService();
    //定义私有无参构造器
    private BookService(){}
    //获得公共静态degreeService对象
    public static BookService getInstance(){
        return bookService;
    }
    //定义findAll方法
    public Collection<Book> findAll() throws SQLException {
        return bookDao.findAll();
    }
    //定义findAll方法
    public Collection<Book> findAllByBookName(String bookName) throws SQLException {
        return bookDao.findByBookName(bookName);
    }
    //定义findAll方法
    public Collection<Book> findAllByWriter(String writer) throws SQLException {
        return bookDao.findByWriter(writer);
    }
    //定义findAll方法
    public Collection<Book> findAllByPress(String press) throws SQLException {
        return bookDao.findByPress(press);
    }
    //定义findAll方法
    public Collection<Book> findAllByAddress(String address) throws SQLException {
        return bookDao.findBookByAddress(address);
    }
    //定义find方法
    public Book find(Integer id) throws SQLException {
        return bookDao.find(id);
    }
    //定义update方法
    public boolean update(Book book) throws SQLException, ClassNotFoundException {
        return bookDao.update(book);
    }
    //定义add方法
    public boolean add(Book book) throws SQLException, ClassNotFoundException {
        return bookDao.add(book);
    }
    //定义delete方法
    public boolean delete(Integer id) throws SQLException, ClassNotFoundException {
        Book book = this.find(id);
        return bookDao.delete(book);
    }
}
