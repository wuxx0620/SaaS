package com.master.system.shiro.realm;

import com.master.common.Constants.Constant;
import com.master.common.shiro.session.realm.MasterRealm;
import com.master.domain.system.Permission;
import com.master.domain.system.User;
import com.master.domain.system.response.ProfileResult;
import com.master.system.service.PermissionService;
import com.master.system.service.UserService;
import org.apache.shiro.authc.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuxx
 * @version 1.0
 * @date 2020/11/19 14:32
 * @className UserRealm
 * @description TODO
 */
public class UserRealm extends MasterRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String mobile = usernamePasswordToken.getUsername();
        String password = new String(usernamePasswordToken.getPassword());
        User user = userService.findByMobile(mobile);
        if (user != null && user.getPassword().equals(password)) {
            ProfileResult result = null;
            if (Constant.UserLevel.USER.equals(user.getLevel())) {
                result = new ProfileResult(user);
            } else {
                Map map = new HashMap();
                //如果是企业管理员,就查询企业管理员可见的
                if (Constant.UserLevel.COADMIN.equals(user.getLevel())) {
                    map.put("enVisible", "1");
                } else if (Constant.UserLevel.SAASADMIN.equals(user.getLevel())) {
                    //如果是SaaS管理员，只显示企业不显示的
                    /**
                     * 即只显示企业管理和模块管理
                     */
                    map.put("enVisible", "0");
                }
                List<Permission> list = permissionService.findAll(map);
                result = new ProfileResult(user, list);
            }
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(result, user.getPassword(), this.getName());
            return info;
        }
        return null;
    }
}
