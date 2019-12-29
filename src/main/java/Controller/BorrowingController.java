package Controller;

import Domain.Borrowing;
import Service.BorrowingService;
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

@WebServlet("/borrowing.ctl")
public class BorrowingController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //根据request对象，获得代表参数的JSON字串
        String borrowing_json = JSONUtil.getJSON(request);
        //将JSON字串解析为Teacher对象
        Borrowing borrowingToAdd = JSON.parseObject(borrowing_json, Borrowing.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //在数据库表中增加Teacher对象
        boolean isBorrowed = BorrowingService.getInstance().borrowBook(borrowingToAdd);
        if (isBorrowed) {
            message.put("message", "借阅成功");
        }else {
            message.put("message","借阅失败");
        }
        //响应message到前端
        response.getWriter().println(message);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String return_json = JSONUtil.getJSON(request);
        //将JSON字串解析为Teacher对象
        Borrowing returnToUpdate = JSON.parseObject(return_json, Borrowing.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        //到数据库表修改Teacher对象对应的记录
        //到数据库表中删除对应的老师
        boolean isReturned = BorrowingService.getInstance().returnBook(returnToUpdate);
        if (isReturned) {
            message.put("message", "归还成功");
        }else{
            message.put("message", "归还失败");
        }
        //响应message到前端
        response.getWriter().println(message);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //读取参数id
        String readerId_str = request.getParameter("readerId");
        String readerName = request.getParameter("readerName");
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            //如果id = null, 表示响应所有老师对象，否则响应id指定的老师对象
            if (readerId_str != null) {
                int readerId = Integer.parseInt(readerId_str);
                responseBorrowingsByReader(readerId, response);
            }else if (readerName != null){
                responseBorrowingsByReaderName(readerName,response);
            } else {
                responseBorrowings(response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

    //响应一个老师对象
    private void responseBorrowingsByReader(int readerId, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //根据id查找老师
        Collection<Borrowing> borrowingsByReader = BorrowingService.getInstance().findAllByReader(readerId);
        String borrowingsByReader_json = JSON.toJSONString(borrowingsByReader);
        //响应Teacher_json到前端
        response.getWriter().println(borrowingsByReader_json);
    }
    //响应所有老师对象
    private void responseBorrowingsByReaderName(String readerName,HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //获得所有老师
        Collection<Borrowing> borrowings = BorrowingService.getInstance().findAllByReaderName(readerName);
        String borrowings_json = JSON.toJSONString(borrowings, SerializerFeature.DisableCircularReferenceDetect);
        //响应Teachers_json到前端
        response.getWriter().println(borrowings_json);
    }
    //响应所有老师对象
    private void responseBorrowings(HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        //获得所有老师
        Collection<Borrowing> borrowings = BorrowingService.getInstance().findAll();
        String borrowings_json = JSON.toJSONString(borrowings, SerializerFeature.DisableCircularReferenceDetect);
        //响应Teachers_json到前端
        response.getWriter().println(borrowings_json);
    }
}
