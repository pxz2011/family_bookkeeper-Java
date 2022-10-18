//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pxh.hab.service;

import com.pxh.hab.entity.User;

public interface IUserService {
    void reg(User var1);

    User login(String var1, String var2);

    Integer modify(String username,String password,String defaultPassword);
}
