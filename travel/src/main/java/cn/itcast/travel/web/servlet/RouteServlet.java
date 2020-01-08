package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    RouteService service=new RouteServiceImpl();

    /**
     * 分页查询
     * @param req
     * @param resp
     */
    public void pageQuery(HttpServletRequest req,HttpServletResponse resp) throws Exception {
        String currentPageStr = req.getParameter("currentPage");
        String pageSizeStr = req.getParameter("pageSize");
        String cidStr = req.getParameter("cid");

//        2处理参数
        int cid=0;
        if(cidStr.length()>0&&cidStr!=null){
            cid = Integer.parseInt(cidStr);
        }
        int currentPage=0;
        if(currentPageStr.length()>0&&currentPageStr!=null){
            currentPage = Integer.parseInt(currentPageStr);
        }else{
            //如果是第一次currentPage是空,就设为默认值
            currentPage=1;
        }
        int pageSize=0;//当前页码显示条数,如果不传默认一页五条
        if(pageSizeStr.length()>0&&pageSizeStr!=null){
            pageSize = Integer.parseInt(pageSizeStr);
        }else{
            pageSize=5;
        }

        PageBean p=service.pageQuery(cid,currentPage,pageSize);
        //将pageBean写会客户端
        writeValue(p, req, resp);

    }
}
