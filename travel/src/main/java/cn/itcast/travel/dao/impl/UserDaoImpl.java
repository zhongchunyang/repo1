package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());


    @Override
    public User findByUsername(String username) {
        User user=null;
        try{
//            定义sql
            String sql = "select * from tab_user where username=?";
//        执行sql
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);

        }catch (Exception e){

        }
//
        return user;
    }

    @Override
    public void sava(User user) {
//        1.定义sql
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code)values(?,?,?,?,?,?,?,?,?)";
//        2.执行sql

        jdbcTemplate.update(sql, user.getUsername(),
                user.getPassword(), user.getName(),
                user.getBirthday(), user.getSex(),
                user.getTelephone(), user.getEmail(),
                user.getStatus(),user.getCode());

    }
    /**
     * 根剧code查询用户
     * @param code
     * @return
     */
    @Override
    public User findByCode(String code) {
        User user = null;
        try {
//            定义sql
            String sql = "select * from tab_user where code= ?";
//        执行sql
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);

        } catch (Exception e) {
            e.printStackTrace();
        }
//
        return user;
    }
    /**
     * 修改用户状态
     * @param u
     */
    @Override
    public void update(User u) {
        String sql = "update tab_user set status='Y' WHERE  uid= ?";
        int update = jdbcTemplate.update(sql, u.getUid());
    }

    /**
     * 根据用户名和密码查询用户
     * @param username
     * @param password
     */
    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User user=null;
        try{
//            定义sql
            String sql = "select * from tab_user where username=? and password = ?";
//        执行sql
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username,password);

        }catch (Exception e){
        e.printStackTrace();
        }
//
        return user;
    }

}
