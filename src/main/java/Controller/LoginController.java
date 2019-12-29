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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 *登录后，同一浏览器发出的请求都被认为是合法请求
 */
@WebServlet("/login.ctl")
public class LoginController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user_json = JSONUtil.getJSON(request);
        //将JSON字串解析为Teacher对象
        User userToCheck = JSON.parseObject(user_json, User.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            User loggedUser = UserService.getInstance().login(userToCheck.getRole(),userToCheck.getUsername(),userToCheck.getPassword());
            if (loggedUser != null){
                //如果当前请求对应着服务器内存中的一个session对象，则返回该对象
                //如果服务器内存中的没有session对象与当前请求对应，则创建一个session对象并返回该对象
                //获取会话对象
                HttpSession session = request.getSession();
                //10分钟没有操作，则使session失效（不活动时间间隔）
                session.setMaxInactiveInterval(10 *60);
                //设置会话属性
                session.setAttribute("currentUser",loggedUser);
                String loggedUser_json = JSON.toJSONString(loggedUser);
                response.getWriter().println(loggedUser_json);
            }else{
                message.put("message","用户名或密码错误");
                response.getWriter().println(message);
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
}
