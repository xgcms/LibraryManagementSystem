package Controller;

import Domain.User;
import Service.UserService;
import Util.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/user.ctl")
public class UserController extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user_json = JSONUtil.getJSON(request);
        //将JSON字串解析为degree对象
        User passwordToUpdate = JSON.parseObject(user_json, User.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表修改degree对象对应的记录
        try {
            boolean isUpdated = UserService.getInstance().changePassword(passwordToUpdate);
            if (isUpdated) {
                message.put("message", "修改密码成功");
            }else{
                message.put("message", "修改密码失败");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //读取参数id
        String id_str = request.getParameter("id");
        String role = request.getParameter("role");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            //如果id = null, 表示响应所有学位对象，否则响应id指定的学位对象
            if (id_str != null && role.equals("reader")) {
                int id = Integer.parseInt(id_str);
                responseUserByReaderId(id,response);
            } else if (id_str != null && role.equals("librarian")){
                int id = Integer.parseInt(id_str);
                responseUserByLibrarianId(id,response);
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
    private void responseUserByReaderId(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //根据id查找学位
        User user = UserService.getInstance().findByReaderId(id);
        String user_json = JSON.toJSONString(user);
        //响应degree_json到前端
        response.getWriter().println(user_json);
    }
    //响应一个学位对象
    private void responseUserByLibrarianId(int id, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //根据id查找学位
        User user = UserService.getInstance().findByLibrarianId(id);
        String user_json = JSON.toJSONString(user);
        //响应degree_json到前端
        response.getWriter().println(user_json);
    }
}
