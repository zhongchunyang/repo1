package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    private UserDao dao = new UserDaoImpl();

    /**
     * 注册的方法
     *
     * @param user
     * @return
     */
    @Override
    public boolean regist(User user) {
        //1.根据用户名查询用户是否存在
        User u = dao.findByUsername(user.getUsername());
        if (u != null) {
//            1.1存在失败
            return false;
        }
//        1.2不存在就注册
        user.setStatus("N");
        user.setCode(UuidUtil.getUuid());
        dao.sava(user);
//当注册客户成功时,就进行邮件的发送

        String content = "<a href='http://localhost/travel/user/active?code=" + user.getCode() + "'>点击激活</a>";
        MailUtils.sendMail(user.getEmail(), content, "激活邮件");
        return true;
    }

    /**
     * 激活用户
     *
     * @param code
     * @return
     */
    @Override
    public boolean active(String code) {
        User u = null;
        //根据激活码查询user对象
        u = dao.findByCode(code);
        if (u == null) {
            return false;
        }
        //调用dao修改用户状态
        dao.update(u);
        return true;
    }

    @Override
    public User login(User user) {

        return  dao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }


}
