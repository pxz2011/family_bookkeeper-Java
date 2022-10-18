//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pxh.hab.service;

import cn.hutool.db.PageResult;
import com.pxh.hab.entity.AccountBook;
import com.pxh.hab.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;

public interface IAccountBookService {
    Integer addTrade(AccountBook var1);
}
