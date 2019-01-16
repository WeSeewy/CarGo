import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class MySQLUtil {
    private static final String TAG="MySQLUtil : ";

    public static Connection connect() {
        Connection con;
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/cargo?serverTimezone=UTC";
        String user = "root";
        String password = "jane017017";
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
            if (!con.isClosed()) {
                System.out.println(TAG+"数据库连接成功");
                return con;
            }
        } catch (ClassNotFoundException e) {
            System.out.println(TAG+"数据库驱动没有安装");
            return null;
        } catch (SQLException e) {
            System.out.println(TAG+"数据库连接失败");
            return null;
        }
        return null;
    }

    public static String queryUser(String tel){
        Connection con=connect();
        String sql=TAG;
        if(con!=null){
            try {
                System.out.println(TAG+"query user");
                Statement statement = con.createStatement();
                sql="SELECT * FROM user WHERE tel='"+tel+"'";
//                System.out.println(sql);
                ResultSet resultSet = statement.executeQuery(sql);
                JsonData jsonData=new JsonData();
                while (resultSet.next()) {
                    jsonData.name = resultSet.getString("name");
                    jsonData.tel = resultSet.getString("tel");
                    jsonData.sex = resultSet.getString("sex");
                    jsonData.money = resultSet.getString("money");
                    jsonData.age = resultSet.getString("age");
                    jsonData.pwd = resultSet.getString("pwd");
                    jsonData.verifycode = resultSet.getString("verifycode");
//                    System.out.println("ans="+ans);
                }
                resultSet.close();
                con.close();
                return new Gson().toJson(jsonData,JsonData.class);
            }catch (SQLException e) {
                System.out.println(TAG+"queryPwd Exception+\n"+sql);
                e.printStackTrace();
                return null;
            }

        }
        return null;
    }

    // add user
    public static String addUser(String name,String tel,String sex,String pwd,String age){
        Connection con=connect();
        String sql=TAG;
        if(con!=null){
            try {
                Statement statement = con.createStatement();
                sql="SELECT COUNT(*) FROM user WHERE tel='"+tel+ "'";
                ResultSet resultSet = statement.executeQuery(sql);
                int cnt=0;
                while (resultSet.next()) {
                    cnt = resultSet.getInt(1);
                }
                if(cnt>=1)  {con.close();return "registered";}
                sql="INSERT INTO user VALUES('"+name+"','"+tel+"','"+sex+"','"+pwd+"','0','','"+age+"')";
                System.out.println(sql);
                statement.executeUpdate(sql);
                con.close();
                System.out.println("——added"+sql);
                return "ok";
            }catch (SQLException e) {
                e.printStackTrace();
                return "SQLException";
            }

        }
        System.out.println(TAG+sql);
        return null;
    }

//    public static void del(String table,String user,String str){
//        System.out.println(TAG+"del"+user);
//        ArrayList<String> ans=new ArrayList<String>();
//        Connection con=connect();
//        if(con!=null){
//            try {
//                Statement statement = con.createStatement();
//                String sql="DELETE from "+table+" where userid = '"+user+"' and str='"+str+"'";
//                statement.executeUpdate(sql);
//                con.close();
//            }catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//

//
//    public static void update(String table,String user,String str){
//        Connection con=connect();
//        System.out.println(TAG+"updateImg"+user);
//        if(con!=null){
//            try {
//                Statement statement = con.createStatement();
//                String sql="UPDATE "+table+" SET avatar = '"+str+"' where phone_number='"+user+"'";
//                System.out.println(TAG+sql);
//                statement.executeUpdate(sql);
//                con.close();
//            }catch (SQLException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
//
//    public static String query_head(String phone){
//        Connection con=connect();
//        if(con!=null){
//            try {
//                // System.out.println("query con");
//                Statement statement = con.createStatement();
//                String sql="SELECT avatar from person where phone_number='"+phone+"'";
//                System.out.println(sql);
//                ResultSet resultSet = statement.executeQuery(sql);
//                String item=null;
//                while (resultSet.next()) {
//                    //  System.out.println("query got edu");
//                    item = resultSet.getString("avatar");
//                    System.out.println(phone + item);
//                }
//
//                resultSet.close();
//                con.close();
//                return item;
//            }catch (SQLException e) {
//                e.printStackTrace();
//                return null;
//            }
//
//        }
//        return null;
//    }
    public static void main(String[] args) {

    }

    private static class JsonData{
        String pwd;
        String name;
        String tel;
        String sex;
        String money;
        String age;
        String verifycode;
        JsonData(){
            pwd=null;name=null;tel=null;sex=null;money=null;verifycode=null;
        }
    }
}
