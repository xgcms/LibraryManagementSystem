package Service;

import Dao.ReaderDao;
import Domain.Reader;

import java.sql.SQLException;
import java.util.Collection;
//定义类DegreeService
public final class ReaderService {
    //获得degreeDao私有静态对象
    private static ReaderDao readerDao
            = ReaderDao.getInstance();
    //创建静态对象degreeService，并赋值给变量degreeService
    private static ReaderService readerService
            =new ReaderService();
    //定义私有无参构造器
    private ReaderService(){}
    //获得公共静态degreeService对象
    public static ReaderService getInstance(){
        return readerService;
    }
    //定义findAll方法
    public Collection<Reader> findAll() throws SQLException {
        return readerDao.findAll();
    }
    //定义find方法
    public Reader find(Integer id) throws SQLException {
        return readerDao.find(id);
    }
    //定义find方法
    public Reader findByReaderName(String readerName) throws SQLException {
        return readerDao.findByName(readerName);
    }
    //定义update方法
    public boolean update(Reader reader) throws SQLException, ClassNotFoundException {
        return readerDao.update(reader);
    }
    //定义add方法
    public boolean add(Reader reader){
        return readerDao.add(reader);
    }
    //定义delete方法
    public boolean delete(Integer id){
        return readerDao.delete(id);
    }
}
