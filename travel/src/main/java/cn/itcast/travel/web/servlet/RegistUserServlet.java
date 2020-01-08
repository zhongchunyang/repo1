package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/registUserServlet")
public class RegistUserServlet extends HttpServlet {

    private    UserService us = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ResultInfo info = new ResultInfo();
        //验证码的校验
        String check = req.getParameter("check");
        String check1 = (String) req.getSession().getAttribute("CHECKCODE_SERVER");
        req.getSession().removeAttribute("CHECKCODE_SERVER");//验证码只能使用一次
        if(check1 == null || !check.equalsIgnoreCase(check1)){
            //验证失败
            info.setFlag(false);
            info.setErrorMsg("验证码错误!");
            //将info对象序列化json
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(info);
            //将json数据协会客户端
            //设置content-json
            resp.setContentType("application/json;charset=utf-8");
            resp.getWriter().write(json);
            return;

        }
        User user = new User();
        //1.获取数据
        Map<String, String[]> map = req.getParameterMap();
        //2.封装对象
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //3.调用Service
        boolean flag = us.regist(user);
        //4.响应结果


        if (flag) {
            //注册成功
            info.setFlag(flag);
        } else {
            //注册失败
            info.setFlag(flag);
            info.setErrorMsg("注册失败!");
        }
        //将info对象序列化json
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(info);
        //将json数据协会客户端
        //设置content-json
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().write(json);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
