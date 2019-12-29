package Controller;

import Domain.Book;
import Service.BookService;
import Util.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

@WebServlet("/book.ctl")
public class BookController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //根据request对象，获得代表参数的JSON字串
        String book_json = JSONUtil.getJSON(request);
        //将JSON字串解析为degree对象
        Book bookToAdd = JSON.parseObject(book_json, Book.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //在数据库表中增加degree对象
        try {
            boolean isAdded = BookService.getInstance().add(bookToAdd);
            if(isAdded) {
                message.put("message", "增加成功");
            }else{
                message.put("message", "增加失败");
            }
        } catch (SQLException e) {
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        //响应message到前端
        response.getWriter().println(message);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //读取参数id
        String id_str = request.getParameter("id");
        int id = Integer.parseInt(id_str);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表中删除对应的学位
        try {
            boolean deleted = BookService.getInstance().delete(id);
            if (deleted) {
                message.put("message", "删除成功");
            }else{
                message.put("message", "已经被删除成功");
            }
        } catch (SQLException e) {
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        //响应message到前端
        response.getWriter().println(message);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String book_json = JSONUtil.getJSON(request);
        //将JSON字串解析为degree对象
        Book bookToAdd = JSON.parseObject(book_json, Book.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表修改degree对象对应的记录
        try {
            BookService.getInstance().update(bookToAdd);
            message.put("message", "修改成功");
        } catch (SQLException e) {
            message.put("message", "数据库操作异常");
            e.printStackTrace();
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
        }
        //响应message到前端
        response.getWriter().println(message);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //读取参数id
        String id_str = request.getParameter("id");
        //读取参数id
        String bookName = request.getParameter("bookName");
        //读取参数id
        String address = request.getParameter("address");
        String writer = request.getParameter("writer");
        String press = request.getParameter("press");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            //如果id = null, 表示响应所有学位对象，否则响应id指定的学位对象
            if (id_str != null) {
                int id = Integer.parseInt(id_str);
                responseBook(id, response);
            } else if (bookName !=null){
                responseBooksByBookName(bookName,response);
            }else if (address != null) {
                responseBooksByAddress(address, response);
            }else if (writer != null) {
                responseBooksByWriter(writer, response);
            }else if (press != null){
                responseBooksByPress(press,response);
            }else{
                responseBooks(response);
            }
        } catch (SQLException e) {
            message.put("message", "数据库操作异常");
            e.printStackTrace();
            //响应message到前端
            response.getWriter().println(message);
        } catch (Exception e) {
            message.put("message", "网络异常");
            e.printStackTrace();
            //响应message到前端
            response.getWriter().println(message);
        }
    }

    //响应一个学位对象
    private void responseBook(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //根据id查找学位
        Book book = BookService.getInstance().find(id);
        String book_json = JSON.toJSONString(book);
        //响应degree_json到前端
        response.getWriter().println(book_json);
    }

    //响应所有学位对象
    private void responseBooks(HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //获得所有学位
        Collection<Book> books = BookService.getInstance().findAll();
        String books_json = JSON.toJSONString(books, SerializerFeature.DisableCircularReferenceDetect);
        //响应degrees_json到前端
        response.getWriter().println(books_json);
    }
    //响应所有学位对象
    private void responseBooksByBookName(String bookName,HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //获得所有学位
        Collection<Book> books = BookService.getInstance().findAllByBookName(bookName);
        String books_json = JSON.toJSONString(books, SerializerFeature.DisableCircularReferenceDetect);
        //响应degrees_json到前端
        response.getWriter().println(books_json);
    }
    //响应所有学位对象
    private void responseBooksByWriter(String writer,HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //获得所有学位
        Collection<Book> books = BookService.getInstance().findAllByWriter(writer);
        String books_json = JSON.toJSONString(books, SerializerFeature.DisableCircularReferenceDetect);
        //响应degrees_json到前端
        response.getWriter().println(books_json);
    }
    //响应所有学位对象
    private void responseBooksByPress(String press,HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //获得所有学位
        Collection<Book> books = BookService.getInstance().findAllByPress(press);
        String books_json = JSON.toJSONString(books, SerializerFeature.DisableCircularReferenceDetect);
        //响应degrees_json到前端
        response.getWriter().println(books_json);
    }
    //响应所有学位对象
    private void responseBooksByAddress(String address,HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //获得所有学位
        Collection<Book> books = BookService.getInstance().findAllByAddress(address);
        String books_json = JSON.toJSONString(books, SerializerFeature.DisableCircularReferenceDetect);
        //响应degrees_json到前端
        response.getWriter().println(books_json);
    }
}
