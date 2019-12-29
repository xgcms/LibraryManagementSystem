package Controller;

import Domain.Reader;
import Service.ReaderService;
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

@WebServlet("/reader.ctl")
public class ReaderController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //根据request对象，获得代表参数的JSON字串
        String reader_json = JSONUtil.getJSON(request);
        //将JSON字串解析为degree对象
        Reader readerToAdd = JSON.parseObject(reader_json, Reader.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //在数据库表中增加degree对象
        boolean isAdded = ReaderService.getInstance().add(readerToAdd);
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
        boolean deleted = ReaderService.getInstance().delete(id);
        if (deleted) {
            message.put("message", "删除成功");
        }else{
            message.put("message", "删除失败");
        }
        //响应message到前端
        response.getWriter().println(message);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String reader_json = JSONUtil.getJSON(request);
        //将JSON字串解析为degree对象
        Reader readerToAdd = JSON.parseObject(reader_json, Reader.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表修改degree对象对应的记录
        try {
            ReaderService.getInstance().update(readerToAdd);
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
        String readerName = request.getParameter("readerName");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            //如果id = null, 表示响应所有学位对象，否则响应id指定的学位对象
            if (id_str != null) {
                int id = Integer.parseInt(id_str);
                responseReader(id, response);
            }else if (readerName != null){
                responseReaderByReaderName(readerName,response);
            } else {
                responseReaders(response);
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
    private void responseReader(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //根据id查找学位
        Reader reader = ReaderService.getInstance().find(id);
        String reader_json = JSON.toJSONString(reader);
        //响应degree_json到前端
        response.getWriter().println(reader_json);
    }

    //响应一个学位对象
    private void responseReaderByReaderName(String readerName, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //根据id查找学位
        Reader reader = ReaderService.getInstance().findByReaderName(readerName);
        String reader_json = JSON.toJSONString(reader);
        //响应degree_json到前端
        response.getWriter().println(reader_json);
    }

    //响应所有学位对象
    private void responseReaders(HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //获得所有学位
        Collection<Reader> readers = ReaderService.getInstance().findAll();
        String readers_json = JSON.toJSONString(readers, SerializerFeature.DisableCircularReferenceDetect);
        //响应degrees_json到前端
        response.getWriter().println(readers_json);
    }
}
