package cn.itcast.travel.dao;

import java.util.List;

public interface RouteDao {
    /**
     * 根据cid查询总记录数
     */
    int findToTotalCount(int cid);

    /**
     * 根据cid,star,pagesize查询点前页的集合
     */
    List findByPage(int cid, int start, int pageSize);
}
