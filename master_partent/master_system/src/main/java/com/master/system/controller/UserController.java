package com.master.system.controller;

import com.master.common.controller.BaseController;
import com.master.common.entity.PageResult;
import com.master.common.entity.Result;
import com.master.common.entity.ResultCode;
import com.master.domain.system.User;
import com.master.domain.system.response.ProfileResult;
import com.master.system.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author wuxx
 * @version 1.0
 * @date 2020/11/16 16:07
 * @className UserController
 * @description TODO
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/sys")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 分配角色
     */
    @RequestMapping(value = "/user/assignRoles", method = RequestMethod.PUT)
    public Result save(@RequestBody Map<String, Object> map) {

        //获取被分配的用户id
        String userId = (String) map.get("id");
        //获取到角色的id列表
        List<String> roleIds = (List<String>) map.get("roleIds");
        //调用service完成角色分配
        userService.assignRoles(userId, roleIds);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 保存
     *
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public Result save(@RequestBody User user) {
        //设置保存的用户id
        user.setCompanyId(companyId);
        user.setCompanyName(companyName);
        //调用service完成保存用户
        userService.save(user);
        //构造返回结果
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 查询用户列表
     *
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Result findAll(int page, int size, @RequestParam() Map map) {

        //获取当前的企业id
        map.put("companyId", companyId);
        Page<User> pageUser = userService.findAll(map, page, size);
        //构造返回结果
        PageResult<User> pageResult = new PageResult<>(pageUser.getTotalElements(), pageUser.getContent());
        return new Result(ResultCode.SUCCESS, pageResult);
    }


    /**
     * 根据Id查询
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id) {
        //添加roleIds(用户已经具有的角色id数组)
        User user = userService.findById(id);
        return new Result(ResultCode.SUCCESS, user);
    }

    /**
     * 修改User
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id, @RequestBody User user) {
        //调用Service更新
        userService.update(id, user);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据Id删除
     */
    @RequiresPermissions(value = "API-USER-DELETE")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE, name = "API-USER-DELETE")
    public Result delete(@PathVariable(value = "id") String id) {
        userService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 用户登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody Map<String, Object> loginMap) {
        String mobile = (String) loginMap.get("mobile");
        String password = (String) loginMap.get("password");
        try {
            password = new Md5Hash(password, mobile, 3).toString();
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(mobile, password);
            Subject subject = SecurityUtils.getSubject();
            subject.login(usernamePasswordToken);
            String sessionId = (String) subject.getSession().getId();
            return new Result(ResultCode.SUCCESS, sessionId);
        } catch (Exception e) {
            return new Result(ResultCode.MOBILEORPASSWORDERROR);
        }
    }

    /**
     * 用户登录成功之后,获取用户信息
     */
    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public Result profile(HttpServletRequest request) throws Exception {

        //获取session中的安全数据
        Subject subject = SecurityUtils.getSubject();
        //subject获取所有的安全集合
        PrincipalCollection principals = subject.getPrincipals();
        //获取安全数据
        ProfileResult result = (ProfileResult) principals.getPrimaryPrincipal();
        return new Result(ResultCode.SUCCESS, result);
    }

}
