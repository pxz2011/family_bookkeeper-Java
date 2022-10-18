//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pxh.hab.mapper;

import com.pxh.hab.entity.AccountBook;
import java.util.ArrayList;
import java.util.List;

import com.pxh.hab.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;

@Mapper
public interface AccountBookMapper  {
    List<AccountBook> findAll();

    Integer insert(AccountBook var1);

    ArrayList<AccountBook> findNameLike(@Param("name") String name, @Param("pageNum") Integer pageNum);

    Integer delWithId(String var1);

}
