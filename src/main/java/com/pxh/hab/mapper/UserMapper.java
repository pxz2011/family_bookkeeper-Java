//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pxh.hab.mapper;

import com.pxh.hab.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Mapper
public interface UserMapper{
    Integer insert(User var1);

    User findByUsername(String var1);

    Integer deleteByName(String username);

    String findStatusByUsername(String var1);

    Integer update(User user);

    String findSalt(@Param("userName") String var1);

    String findByPassword(@Param("username") String var1);

    User findByEmail(@Param("email") String var1);

}
