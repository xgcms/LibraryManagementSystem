package Controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout.ctl")
public class LogoutController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取会话对象
        HttpSession session = request.getSession(false);
        if (session != null) {
            //废弃会话数据，使对话失效，以防止其他用户利用服务器内存中的session进行资源访问
            session.invalidate();
        }
        //响应客户端
        response.getWriter().println("Session has invalidated");
    }
}
