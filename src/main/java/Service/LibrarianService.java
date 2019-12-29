package Service;

import Dao.LibrarianDao;
import Domain.Librarian;

import java.sql.SQLException;
import java.util.Collection;
//定义类DegreeService
public final class LibrarianService {
    //获得degreeDao私有静态对象
    private static LibrarianDao librarianDao
            = LibrarianDao.getInstance();
    //创建静态对象degreeService，并赋值给变量degreeService
    private static LibrarianService librarianService
            = new LibrarianService();

    //定义私有无参构造器
    private LibrarianService() {
    }

    //获得公共静态degreeService对象
    public static LibrarianService getInstance() {
        return librarianService;
    }

    //定义findAll方法
    public Collection<Librarian> findAll() throws SQLException {
        return librarianDao.findAll();
    }

    //定义find方法
    public Librarian find(Integer id) throws SQLException {
        return librarianDao.find(id);
    }

    //定义update方法
    public boolean update(Librarian librarian) throws SQLException, ClassNotFoundException {
        return librarianDao.update(librarian);
    }

    //定义add方法
    public boolean add(Librarian librarian) {
        return librarianDao.add(librarian);
    }

    //定义delete方法
    public boolean delete(Integer id) {
        return librarianDao.delete(id);
    }
}