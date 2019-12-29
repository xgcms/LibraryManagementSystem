package Service;

import Dao.BookDao;
import Dao.BorrowingDao;
import Domain.Book;
import Domain.Borrowing;
import Domain.Reader;

import java.sql.SQLException;
import java.util.Collection;
public class BorrowingService {
    //获得degreeDao私有静态对象
    private static BorrowingDao borrowingDao
            = BorrowingDao.getInstance();
    //创建静态对象degreeService，并赋值给变量degreeService
    private static BorrowingService borrowingService
            =new BorrowingService();
    //定义私有无参构造器
    private BorrowingService(){}
    //获得公共静态degreeService对象
    public static BorrowingService getInstance(){
        return borrowingService;
    }
    //定义findAll方法
    public Collection<Borrowing> findAll() throws SQLException {
        return borrowingDao.findAll();
    }
    //定义findAll方法
    public Collection<Borrowing> findAllByReader(Integer readerId) throws SQLException {
        return borrowingDao.findAllByReader(readerId);
    }
    //定义findAll方法
    public Collection<Borrowing> findAllByReaderName(String readerName) throws SQLException {
        return borrowingDao.findAllByReaderName(readerName);
    }
    //定义find方法
    public Borrowing find(Integer id) throws SQLException {
        return borrowingDao.find(id);
    }

    //定义add方法
    public boolean borrowBook(Borrowing borrowing){
        return borrowingDao.borrowBook(borrowing);
    }
    //定义delete方法
    public boolean returnBook(Borrowing returning){
        return borrowingDao.returnBook(returning);
    }
}
