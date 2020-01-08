package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;

/**
 * 线路service
 */
public interface RouteService {
    /**
     * 分页查询
     * @param cid
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageBean pageQuery(int cid, int currentPage, int pageSize);
}
