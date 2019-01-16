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

@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final String TAG="LoginServlet : ";

    private class JsonData{
        String status;
        String name;
        String tel;
        String sex;
        String money;
        String age;
        JsonData(){status="param error";}
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(TAG+"doGet()");
        //    192.168.8.13:8081/LoginServlet?type=pwd&tel=18956232079&pwd=123
        response.setContentType("text/json;charset=UTF-8");
//        response.setHeader("Access-Control-Allow-Origin", "*");

        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        JsonData jsonOut = new JsonData();

        String type = request.getParameter("type");
        if (type != null) type = new String(type.getBytes("iso-8859-1"), "utf-8");
        String tel = request.getParameter("tel");
        if (tel != null) tel = new String(tel.getBytes("iso-8859-1"), "utf-8");
        String pwd = request.getParameter("pwd");
        if (pwd != null) pwd = new String(pwd.getBytes("iso-8859-1"), "utf-8");

        try {
            if (type!=null && tel != null && tel.length() > 0 && pwd != null && pwd.length() > 0) {
                JSONObject jsonObject=new JSONObject(MySQLUtil.queryUser(tel));
                String ans=jsonObject.getString(type);
                if (ans!=null&&ans.equals(pwd)) {
                    jsonOut.status = "right";
                    jsonOut.name=jsonObject.getString("name");
                    jsonOut.age=jsonObject.getString("age");
                    jsonOut.tel=jsonObject.getString("tel");
                    jsonOut.sex=jsonObject.getString("sex");
                    jsonOut.money=jsonObject.getString("money");
                } else {
                    jsonOut.status = "wrong";
                    System.out.println(TAG+"wrong--"+pwd+"/"+ans);
                }
            } else {
                jsonOut.status = "param error";
                System.out.println(TAG + "参数错误");
                System.out.println("    "+ tel + " " + pwd);
            }
        }catch (NullPointerException e){
            jsonOut.status = e.getCause().toString();
            e.printStackTrace();
        }finally {
            String jsonStr=new Gson().toJson(jsonOut,JsonData.class);
            out.println(jsonStr);
            out.close();
        }
    }

}
