import com.google.gson.Gson;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final String TAG="RegisterServlet : ";

    private class JsonData{
        String status;
        JsonData(){status="没有执行添加";}
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(TAG+"doGet()");
        //    192.168.8.13:8081/RegisterServlet?name=张三&sex=男&tel=18956232079&pwd=123
        response.setContentType("text/json;charset=UTF-8");
//        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        JsonData jsonOut = new JsonData();

        String name = request.getParameter("name");
        if (name != null) name = new String(name.getBytes("iso-8859-1"), "utf-8");

        String sex = request.getParameter("sex");
        if (sex != null) sex = new String(sex.getBytes("iso-8859-1"), "utf-8");

        String tel = request.getParameter("tel");
        if (tel != null) tel = new String(tel.getBytes("iso-8859-1"), "utf-8");

        String pwd = request.getParameter("pwd");
        if (pwd != null) pwd = new String(pwd.getBytes("iso-8859-1"), "utf-8");

        String age = request.getParameter("age");
        if (age != null) age = new String(pwd.getBytes("iso-8859-1"), "utf-8");

        if (name != null && sex !=null && tel != null  && pwd != null && age!=null
                &&name.length()>0&&sex.length()>0&&tel.length()>0&&pwd.length()>0&&age.length()>0) {
            jsonOut.status=MySQLUtil.addUser(name,tel,sex,pwd,age);

        }
        String jsonStr=new Gson().toJson(jsonOut, JsonData.class);
        out.println(jsonStr);
        out.close();
    }
}
