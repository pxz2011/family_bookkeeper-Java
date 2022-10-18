//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pxh.hab.service.impl;

import com.pxh.hab.entity.User;
import com.pxh.hab.mapper.UserMapper;
import com.pxh.hab.service.IUserService;
import com.pxh.hab.service.exception.InsertException;
import com.pxh.hab.service.exception.UserNotFoundException;
import com.pxh.hab.service.exception.UsernameDuplicatedException;
import java.util.UUID;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserServiceImpl implements IUserService {
    @Resource
    private UserMapper userMapper;

    public UserServiceImpl() {
    }

    public void reg(User user) {
        User result = this.userMapper.findByUsername(user.getUserName());
        if (result != null) {
            throw new UsernameDuplicatedException("用户名被占用");
        } else {
            String oldPassword = user.getPassword();
            String salt = UUID.randomUUID().toString().toUpperCase();
            user.setSalt(salt);
            String md5Password = this.getMd5Password(oldPassword, salt);
            System.out.println(md5Password);
            user.setPassword(md5Password);
            Integer rows = this.userMapper.insert(user);
            if (rows != 1) {
                throw new InsertException("注册失败");
            }
        }
    }

    public User login(String username, String password) {
        User result = this.userMapper.findByUsername(username);
        String salt = this.userMapper.findSalt(username);
        if (result == null) {
            throw new UserNotFoundException("该用户不存在");
        } else {
            String md5Password = this.getMd5Password(password, salt);
            System.out.println(result.getPassword());
            System.out.println(result.getPassword().equals(md5Password));
            return !result.getPassword().equals(md5Password) ? null : new User();
        }
    }

    @Override
    public Integer modify(String username, String password,String defaultPassword) {
        User user = new User();
        user.setUserName(username);
        String salt = userMapper.findSalt(username);
        user.setPassword(getMd5Password(password,salt));
        User sqlUser = userMapper.findByUsername(username);
        if(sqlUser.getPassword().equals(getMd5Password(defaultPassword,salt))){
            return userMapper.update(user);
        }
        else{
            throw new RuntimeException("原密码不正确!");
        }


    }

    private String getMd5Password(String password, String salt) {
        for(int i = 0; i < 3; ++i) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }

        System.out.println(password);
        return password;
    }
}
