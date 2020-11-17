package com.master.system.service;

import com.master.common.utils.IdWorker;
import com.master.domain.system.Role;
import com.master.domain.system.User;
import com.master.system.dao.RoleDao;
import com.master.system.dao.UserDao;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * @author wuxx
 * @version 1.0
 * @date 2020/11/16 10:49
 * @className UserService
 * @description TODO
 */

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private IdWorker idWorker;


    /**
     * 根据mobile查询用户
     */
    public User findByMobile(String mobile) {
        return userDao.findByMobile(mobile);
    }


    /**
     * 保存用户
     */
    public void save(User user) {
        //设置主键
        String id = idWorker.nextId() + "";
        //md5加密密码
        String password = new Md5Hash(user.getPassword(), user.getMobile(), 3).toString();
        user.setLevel("user");
        user.setPassword(password);//设置初始密码
        user.setEnableState(1);
        user.setId(id);
        //调用dao保存用户
        userDao.save(user);
    }

    /**
     * 更新用户
     */
    public void update(String id, User user) {
        User tempUser = userDao.findById(id).get();
        if (!ObjectUtils.isEmpty(tempUser) && !ObjectUtils.isEmpty(user)) {
            tempUser.setUsername(user.getUsername());
            tempUser.setPassword(user.getPassword());
            tempUser.setWorkNumber(user.getWorkNumber());
            tempUser.setTimeOfEntry(user.getTimeOfEntry());
        }
        //更新用户
        userDao.save(tempUser);
    }


    /**
     * 根据id查询用户
     */
    public User findById(String id) {
        return userDao.findById(id).get();
    }

    /**
     * 根据id删除用户
     */
    public void deleteById(String id) {
        userDao.deleteById(id);
    }

    /**
     * 分配角色
     *
     * @param userId  用户id
     * @param roleIds 要分配的角色id
     */
    public void assignRoles(String userId, List<String> roleIds) {
        User user = userDao.findById(userId).get();
        Set<Role> roles = new HashSet<>();
        for (String roleId : roleIds) {
            Role role = roleDao.findById(roleId).get();
            roles.add(role);
        }
        user.setRoles(roles);
    }

    /**
     * 查询全部用户列表
     */
    public Page<User> findAll(Map<String, Object> map, int page, int size) {
        /**
         * 自定义查询条件
         *      1.实现Specification接口（提供泛型：查询的对象类型）
         *      2.实现toPredicate方法（构造查询条件）
         *      3.需要借助方法参数中的两个参数（
         *          root：获取需要查询的对象属性
         *          CriteriaBuilder：构造查询条件的，内部封装了很多的查询条件（模糊匹配，精准匹配）
         *       ）
         *  案例：根据客户名称查询，查询客户名为传智播客的客户
         *          查询条件
         *              1.查询方式
         *                  cb对象
         *              2.比较的属性名称
         *                  root对象
         *
         */
        Specification<User> sp = new Specification<User>() {
            //构造查询条件

            /**
             *   root    ：Root接口，代表查询的根对象，可以通过root获取实体中的属性
             *   query   ：代表一个顶层查询对象，用来自定义查询
             *   cb      ：用来构建查询，此对象里有很多条件方法
             **/
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if (!StringUtils.isEmpty(map.get("companyId"))) {
                    list.add(cb.equal(root.get("companyId").as(String.class), (String) map.get("companyId")));
                }
                //根据请求的部门id构造查询条件
                if (!StringUtils.isEmpty(map.get("departmentId"))) {
                    list.add(cb.equal(root.get("departmentId").as(String.class), (String) map.get("departmentId")));
                }
                if (!StringUtils.isEmpty(map.get("hasDept"))) {
                    if ("0".equals((String) map.get("hasDept"))) {
                        list.add(cb.isNull(root.get("departmentId")));
                    } else {
                        list.add(cb.isNotNull(root.get("departmentId")));
                    }
                }
                return cb.and(list.toArray(new Predicate[list.size()]));
            }
        };
        Page<User> pageUser = userDao.findAll(sp, new PageRequest(page - 1, size));
        return pageUser;
    }
}
