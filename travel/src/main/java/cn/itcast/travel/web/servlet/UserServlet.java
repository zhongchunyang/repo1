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

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService us = new UserServiceImpl();

    /**
     * 注册用户
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ResultInfo info = new ResultInfo();
        //验证码的校验
        String check = req.getParameter("check");
        String check1 = (String) req.getSession().getAttribute("CHECKCODE_SERVER");
        req.getSession().removeAttribute("CHECKCODE_SERVER");//验证码只能使用一次
        if (check1 == null || !check.equalsIgnoreCase(check1)) {
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

    /**
     * 登录
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> map = req.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        User u = us.login(user);

        //判断用户不存在
        ResultInfo info = new ResultInfo();
        if (u == null) {
            info.setFlag(false);
            info.setErrorMsg("用户不存在");
        } else {
            //用户存在未激活
            if (u.getStatus().equalsIgnoreCase("n")) {
                info.setFlag(false);
                info.setErrorMsg("用户尚未激活");
            } else {
                //用户存在已激活
                info.setFlag(true);
                req.getSession().setAttribute("user", u);
            }
        }
        //响应数据
        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json;charset=utf-8");
        mapper.writeValue(resp.getOutputStream(), info);

    }

    /**
     * 查找一个
     * @param req
     * @param resp
     */
    public void findOne(HttpServletRequest req, HttpServletResponse resp) {
        //从session中获取用户
        Object user = req.getSession().getAttribute("user");

        //将user写会客户端
        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json;charset=utf-8");
        try {
            mapper.writeValue(resp.getOutputStream(), user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 退出
     * @param req
     * @param resp
     */
    public void exit(HttpServletRequest req, HttpServletResponse resp) {
        //注销session
        req.getSession().invalidate();
        //重定向
        try {
            resp.sendRedirect(req.getContextPath() + "/login.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 激活
     * @param req
     * @param resp
     */
    public void active(HttpServletRequest req, HttpServletResponse resp) {
        String code = req.getParameter("code");
        System.out.println(code);
        if (code != null) {
            boolean flag = us.active(code);
            String msg = "";
            if (flag) {
                //激活成功
                msg = "激活成功请<a href='login.html'>登录</a>";
            } else {
                //激活失败
                msg = "激活失败,请联系管理员";
            }
            resp.setContentType("text/html;charset=utf-8");
            try {
                resp.getWriter().write(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


