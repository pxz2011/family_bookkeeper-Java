//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pxh.hab.service.impl;

import cn.hutool.db.PageResult;
import com.pxh.hab.entity.AccountBook;
import com.pxh.hab.entity.User;
import com.pxh.hab.mapper.AccountBookMapper;
import com.pxh.hab.mapper.UserMapper;
import com.pxh.hab.service.IAccountBookService;
import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AccountBookImpl implements IAccountBookService {
    @Resource
    private AccountBookMapper accountBookMapper;

    public AccountBookImpl() {
    }

    public Integer addTrade(AccountBook accountBook) {
        return this.accountBookMapper.insert(accountBook);
    }

}
