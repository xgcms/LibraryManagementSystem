package Controller;

import Domain.Librarian;
import Service.LibrarianService;
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

@WebServlet("/librarian.ctl")
public class LibrarianController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //根据request对象，获得代表参数的JSON字串
        String librarian_json = JSONUtil.getJSON(request);
        //将JSON字串解析为degree对象
        Librarian librarianToAdd = JSON.parseObject(librarian_json, Librarian.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //在数据库表中增加degree对象
        boolean isAdded = LibrarianService.getInstance().add(librarianToAdd);
        if (isAdded) {
            message.put("message", "增加成功");
        }else{
            message.put("message", "增加失败");
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
        boolean deleted = LibrarianService.getInstance().delete(id);
        if (deleted) {
            message.put("message", "删除成功");
        }else{
            message.put("message", "已经被删除成功");
        }
        //响应message到前端
        response.getWriter().println(message);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String librarian_json = JSONUtil.getJSON(request);
        //将JSON字串解析为degree对象
        Librarian librarianToAdd = JSON.parseObject(librarian_json, Librarian.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表修改degree对象对应的记录
        try {
            LibrarianService.getInstance().update(librarianToAdd);
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
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            //如果id = null, 表示响应所有学位对象，否则响应id指定的学位对象
            if (id_str == null) {
                responseLibrarians(response);
            } else {
                int id = Integer.parseInt(id_str);
                responseLibrarian(id, response);
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
    private void responseLibrarian(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //根据id查找学位
        Librarian librarian = LibrarianService.getInstance().find(id);
        String librarian_json = JSON.toJSONString(librarian);
        //响应degree_json到前端
        response.getWriter().println(librarian_json);
    }

    //响应所有学位对象
    private void responseLibrarians(HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //获得所有学位
        Collection<Librarian> librarians = LibrarianService.getInstance().findAll();
        String librarians_json = JSON.toJSONString(librarians, SerializerFeature.DisableCircularReferenceDetect);
        //响应degrees_json到前端
        response.getWriter().println(librarians_json);
    }
}
