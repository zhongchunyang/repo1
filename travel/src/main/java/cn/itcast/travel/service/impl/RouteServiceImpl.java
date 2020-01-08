package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.service.RouteService;

public class RouteServiceImpl implements RouteService {
    RouteDao dao=new RouteDaoImpl();
    @Override
    public PageBean pageQuery(int cid, int currentPage, int pageSize) {
        return null;
    }
}
