package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 保存用户
     * @param user
     */
    void sava(User user);

    /**
     * 根剧code查询用户
     * @param code
     * @return
     */
    User findByCode(String code);

    /**
     * 修改用户状态
     * @param u
     */
    void update(User u);

    /**
     * 根据用户名和密码查询用户
     * @param username
     * @param password
     */
    User findByUsernameAndPassword(String username, String password);
}
