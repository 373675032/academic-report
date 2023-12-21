package world.xuewei.service;

import org.springframework.stereotype.Service;
import world.xuewei.dao.AdminMapper;
import world.xuewei.entity.Admin;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Admin)表业务接口实现类
 *
 * <p>
 * ==========================================================================
 * 郑重说明：本项目免费开源！原创作者为：薛伟同学，严禁私自出售。
 * ==========================================================================
 * B站账号：薛伟同学
 * 微信公众号：薛伟同学
 * 作者博客：http://xuewei.world
 * ==========================================================================
 * 陆陆续续总会收到粉丝的提醒，总会有些人为了赚取利益倒卖我的开源项目。
 * 不乏有粉丝朋友出现钱付过去，那边只把代码发给他就跑路的，最后还是根据线索找到我。。
 * 希望各位朋友擦亮慧眼，谨防上当受骗！
 * ==========================================================================
 *
 * @author <a href="http://xuewei.world/about">XUEW</a>
 */
@Service
public class AdminService {

    @Resource
    private AdminMapper adminMapper;

    public boolean insert(Admin admin) {
        return adminMapper.insert(admin) == 1;
    }

    public boolean deleteById(Integer id) {
        return adminMapper.deleteById(id) == 1;
    }

    public Admin getById(Integer id) {
        return adminMapper.getById(id);
    }

    public Admin getByNo(String no) {
        return adminMapper.getByNo(no);
    }

    public List<Admin> listAdmins() {
        return adminMapper.listAdmins();
    }

    public List<Admin> listAdmins(Admin admin) {
        return adminMapper.listAdmins(admin);
    }

    public int countAdmins(Admin admin) {
        return adminMapper.countAdmins(admin);
    }

    public boolean update(Admin admin) {
        return adminMapper.update(admin) == 1;
    }

}