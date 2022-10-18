//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.pxh.hab.controller;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.pxh.hab.entity.User;
import com.pxh.hab.mapper.AccountBookMapper;
import com.pxh.hab.mapper.UserMapper;
import com.pxh.hab.result.JsonResult;
import com.pxh.hab.service.IUserService;
import com.pxh.hab.service.exception.InsertException;
import com.pxh.hab.service.exception.UserNotFoundException;
import com.pxh.hab.service.exception.UsernameDuplicatedException;
import com.pxh.hab.utils.JWTUtil;
import com.pxh.hab.utils.RedisUtil;
import com.pxh.hab.utils.VerificationCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

@RestController
@CrossOrigin
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Resource
    JsonResult jsonResult;
    @Resource
    RedisUtil redisUtil;
    @Resource
    private UserMapper userMapper;
    @Resource
    private IUserService userService;
    @Autowired
    JavaMailSender javaMailSender;
    @Resource
    JWTUtil jwtUtil;
    @Resource
    AccountBookMapper accountBookMapper;

    public UserController() {
    }

    @GetMapping("/sendMail")
    public JsonResult<Void> sendEmail(String To) {
        JsonResult<Void> jsonResult = new JsonResult<>();
        if (userMapper.findByEmail(To) != null) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom("1521955177@qq.com");
                message.setTo(To);
                message.setSubject("PengxhProject");
                String random = VerificationCodeUtil.getFourRandom();
                message.setText("验证码为:" + random);
                javaMailSender.send(message);
                redisUtil.set(To, random, 1);
                jsonResult.setState(200);
                jsonResult.setMessage("ok");
            } catch (MailSendException e) {
                jsonResult.setState(500);
                jsonResult.setMessage("邮箱输入有误!请检查后再输入");
            }

        } else {
            jsonResult.setState(500);
            jsonResult.setMessage("请先注册!");
        }
        return jsonResult;

    }

    @PostMapping("/verifyMail")
    public JsonResult<User> verify(String pass, String To) {
        JsonResult<User> jsonResult = new JsonResult<>();
        if (pass.equals(redisUtil.get(To))) {
            jsonResult.setMessage("登录成功!");
            jsonResult.setState(200);
            User user = userMapper.findByEmail(To);
            String token = JWTUtil.getToken(user.getUserName(), user.getPassword());
            jsonResult.setToken(token);
            jsonResult.setResultUsername(user.getUserName());
            jsonResult.setData(user);
            log.info(String.valueOf(user));
            redisUtil.set("token_" + user.getUserName(), token, 1);
            redisUtil.del(To);
        } else {
            jsonResult.setMessage("验证码错误!");
            jsonResult.setState(500);

        }
        return jsonResult;
    }

    @PutMapping({"/user/modifyPassword"})
    public JsonResult<User> modify(HttpServletRequest request) throws Exception {
        JsonResult<User> jsonResult = new JsonResult<User>();
        String password = request.getHeader("newPassword");
        String defaultPassword = request.getHeader("defaultPassword");
        String username = request.getHeader("userName");
        String token = request.getHeader("token");
        try {
            User user = JWTUtil.parse(token);
            if (user.getUserName().equals(username)) {
                Integer res = userService.modify(username, password, defaultPassword);
                System.out.println(res);
                if (res != null) {
                    String newToken = JWTUtil.getToken(username, password);
                    redisUtil.del("token" + "_" + username);
                    redisUtil.set("token_" + username, newToken, 1);
                    jsonResult.setState(200);
                } else {
                    jsonResult.setState(500);
                    jsonResult.setMessage("修改密码失败！");
                }
            } else {
                jsonResult.setState(500);
                jsonResult.setMessage("偷梁换柱？");
            }


        } catch (Exception e) {
            jsonResult.setState(500);
            jsonResult.setMessage(e.getMessage());
        }
        return jsonResult;

    }

    @DeleteMapping("/user/{userName}")
    public JsonResult<User> delUser(@PathVariable String userName, HttpServletRequest request) throws Exception {
        JsonResult<User> jsonResult = new JsonResult<User>();
        String token = request.getHeader("token");
        if (!Objects.equals(JWTUtil.parse(token).getUserName(), userName)) {
            jsonResult.setState(500);
            jsonResult.setMessage("偷梁换柱?");
        } else {
            Integer res = userMapper.deleteByName(userName);
            if (res != null) {
                if (redisUtil.isExists("token_" + userName)) {
                    redisUtil.del("token_" + userName);
                    jsonResult.setState(200);
                }
                jsonResult.setState(200);
            } else {
                jsonResult.setState(500);
            }
        }
        return jsonResult;
    }

    @PostMapping({"/user/reg"})
    public JsonResult reg(HttpServletRequest request, Model model) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        log.info("VisitDate:{}", formatter.format(date));
        User user = new User();
        String userName = request.getHeader("userName");
        String password = request.getHeader("password");
        String email = request.getHeader("email");
        user.setUserName(userName);
        user.setPassword(password);
        user.setUserStatus("user");
        user.setEmail(email);
        System.out.println(user.toString());
        JsonResult<User> jsonResult = new JsonResult<User>();
        System.out.println("数据库是否有键值" + redisUtil.isExists("token_" + user.getUserName()));
        if(userMapper.findByEmail(email) == null){
            try {
                if (!StrUtil.isBlank(user.getUserName()) && !StrUtil.isBlank(user.getPassword()) && !StrUtil.isBlank(user.getEmail())) {
                    this.userService.reg(user);
                    if (redisUtil.isExists("token_" + user.getUserName())) {
                        jsonResult.setState(200);
                        jsonResult.setMessage("注册成功");
                        jsonResult.setToken(redisUtil.get("token_" + user.getUserName()));
                        jsonResult.setResultUsername(user.getUserName());
                    } else {
                        String token = JWTUtil.getToken(user.getUserName(), user.getPassword());
                        redisUtil.set("token_" + user.getUserName(), token, 1L);
                        System.out.println("redis数据库token=" + redisUtil.get("token_" + user.getUserName()));
                        jsonResult.setState(200);
                        jsonResult.setMessage("注册成功");
                        jsonResult.setToken(token);
                        jsonResult.setResultUsername(user.getUserName());
                    }
                } else {
                    jsonResult.setState(500);
                    jsonResult.setMessage("请输入用户名或密码或邮箱");
                }
            } catch (UserNotFoundException | UsernameDuplicatedException | InsertException var11) {
                jsonResult.setState(500);
                jsonResult.setMessage(var11.getMessage());
            }

            System.out.println("okk");

            System.out.println(jsonResult.toString());
        }else{
            jsonResult.setState(500);
            jsonResult.setMessage("email已被注册");
        }

        return jsonResult;
    }

    @PostMapping({"/user/login"})
    public JsonResult<User> login(Model model, @RequestBody HashMap userInfo, HttpServletResponse response) throws IOException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        log.info("VisitDate:{}", formatter.format(date));
        String userName = (String) userInfo.get("userName");
        String password = (String) userInfo.get("password");
        System.out.println(userName + password);
        JsonResult<User> jsonResult = new JsonResult();
        System.out.println("数据库是否有键值" + redisUtil.isExists("token_" + userName));

        try {
            if (this.userService.login(userName, password) != null) {
                User user = new User();
                user.setUserName(userName);
                user.setPassword(password);
                user.setUserStatus(this.userMapper.findStatusByUsername(userName));
                jsonResult.setData(user);
                if (redisUtil.isExists("token_" + userName)) {
                    System.out.println("redis数据库token=" + redisUtil.get("token_" + userName));
                    jsonResult.setState(200);
                    jsonResult.setMessage("登录成功");
                    jsonResult.setToken(redisUtil.get("token_" + userName));
                    jsonResult.setResultUsername(userName);
                } else {
                    String token = JWTUtil.getToken(userName, password);
                    System.out.println("后端生成token=" + token);
                    redisUtil.set("token_" + userName, token, 1L);
                    System.out.println("redis数据库token=" + redisUtil.get("token_" + userName));
                    jsonResult.setState(200);
                    jsonResult.setMessage("登录成功");
                    jsonResult.setToken(token);
                }
            } else {
                jsonResult.setState(500);
                jsonResult.setMessage("用户名或密码错误");
            }
        } catch (SignatureVerificationException | UserNotFoundException var11) {
            jsonResult.setState(500);
            jsonResult.setMessage(var11.getMessage());
        }

        return jsonResult;
    }

    @GetMapping({"/token"})
    public JsonResult<Void> jwtLogin(HttpServletRequest request) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        log.info("访问时间:{}", formatter.format(date));

        try {
            String token = request.getHeader("token");
            User user = JWTUtil.parse(token);
            System.out.println(token);
            String userName = user.getUserName();
            String password = user.getPassword();

            try {
                if (this.userService.login(userName, password) != null) {
                    this.jsonResult.setState(200);
                    this.jsonResult.setMessage("登录成功");
                } else {
                    this.jsonResult.setState(500);
                    this.jsonResult.setMessage("用户名或密码错误");
                }
            } catch (UserNotFoundException var9) {
                this.jsonResult.setState(4000);
                this.jsonResult.setMessage(var9.getMessage());
            }
        } catch (TokenExpiredException var10) {
            this.jsonResult.setMessage("token已失效!!,请重新登录!!");
        } catch (JWTDecodeException | SignatureVerificationException var11) {
            this.jsonResult.setState(500);
            this.jsonResult.setMessage("token错误!!");
        }

        return this.jsonResult;
    }

    @GetMapping("checkToken")
    public JsonResult<Void> checkToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        System.out.println("前端token" + token);
        try {
            if (jwtUtil.verify(token)) {
                jsonResult.setState(200);
            } else {
                jsonResult.setState(500);
            }


        } catch (Exception e) {
            jsonResult.setState(500);
        }
        return jsonResult;

    }


}
