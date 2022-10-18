

package com.pxh.hab.controller;

import com.pxh.hab.entity.AccountBook;
import com.pxh.hab.mapper.AccountBookMapper;
import com.pxh.hab.result.JsonResult;
import com.pxh.hab.service.IAccountBookService;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.jdbc.Null;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping({"/hab"})
@CrossOrigin
public class AccountBookController {
    private static final Logger log = LoggerFactory.getLogger(AccountBookController.class);
    @Resource
    private AccountBookMapper accountBookMapper;
    @Resource
    private IAccountBookService accountBookService;
    @PutMapping({"/add"})
    public JsonResult<Void> add(AccountBook accountBook) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        log.info("VisitDate:{}", formatter.format(date));
        JsonResult<Void> jsonResult = new JsonResult();
        if (this.accountBookService.addTrade(accountBook) != null) {
            jsonResult.setMessage("添加成功");
            jsonResult.setState(200);
        } else {
            jsonResult.setMessage("添加交易失败");
            jsonResult.setState(500);
        }

        return jsonResult;
    }

    @DeleteMapping({"/{id}"})
    public JsonResult<Void> del(@PathVariable String id) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        log.info("VisitDate:{}", formatter.format(date));
        JsonResult<Void> jsonResult = new JsonResult();
        Integer integer = this.accountBookMapper.delWithId(id);
        System.out.println(integer);
        if (integer == 0) {
            jsonResult.setMessage("删除交易不成功,此id不存在!!");
            jsonResult.setState(500);
        } else {
            jsonResult.setMessage("删除交易记录成功");
            jsonResult.setState(200);
        }

        return jsonResult;
    }

    @GetMapping({"/findAll"})
    public JsonResult<List<AccountBook>> findAll() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        log.info("VisitDate:{}", formatter.format(date));
        JsonResult<List<AccountBook>> jsonResult = new JsonResult<List<AccountBook>>();
        jsonResult.setData(this.accountBookMapper.findAll());
        List<AccountBook> accountBookList = this.accountBookMapper.findAll();
        PrintStream var10001 = System.out;
        accountBookList.forEach(var10001::println);
        return jsonResult;
    }

    @PutMapping({"/findNameLike"})
    public JsonResult<ArrayList<AccountBook>> findNameLike(String name, Integer pageNum) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        log.info("VisitDate:{}", formatter.format(date));
        JsonResult<ArrayList<AccountBook>> jsonResult = new JsonResult<>();
        ArrayList<AccountBook> accountBookList = this.accountBookMapper.findNameLike(name, (pageNum - 1) * 10);
        PrintStream var10001 = System.out;
        accountBookList.forEach(var10001::println);
        jsonResult.setData(accountBookList);
        return jsonResult;
    }

    @GetMapping("/file/getFile/{username}")
    public void getFile(HttpServletRequest request, HttpServletResponse response, @PathVariable String username) throws IOException {

        String fileName = "image" + username + ".jpg";
        try {
            String path = "E:\\pengxh_Project\\homeaccountbook\\java\\";
            File file = new File(path + fileName);
            FileInputStream fis;
            fis = new FileInputStream(file);
            long size = file.length();
            byte[] data = new byte[(int) size];
            fis.read(data, 0, (int) size);
            fis.close();
            response.setContentType("image/png");
            OutputStream out = response.getOutputStream();
            out.write(data);

            out.flush();
            out.close();
        } catch (IOException e) {
            response.setStatus(500);
        }

    }

    @PostMapping("/file/upload")
    public JsonResult<Void> upload(@RequestParam("file") MultipartFile file, HttpServletResponse response, String username) throws IOException {
        JsonResult<Void> jsonResult = new JsonResult<>();
        if (file.isEmpty()) {
            jsonResult.setState(500);
            jsonResult.setMessage("图片不能为空!");
        } else {
            System.out.println(file.getOriginalFilename());//文件名
            System.out.println(file.getContentType());//文件类型
            System.out.println(file.getSize());//文件大小
            System.out.println(file.getInputStream());//文件的输入流

            //获得文件上传的路径
            String fileName = "image" + username + file.getOriginalFilename();
            String filePath = "E:\\pengxh_Project\\homeaccountbook\\java\\";
            File dest = new File(filePath + fileName);
            try {
                file.transferTo(dest);
                log.info("上传成功");
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                jsonResult.setState(500);
                jsonResult.setMessage(e.getMessage());
            }
        }
        return jsonResult;
    }

}
