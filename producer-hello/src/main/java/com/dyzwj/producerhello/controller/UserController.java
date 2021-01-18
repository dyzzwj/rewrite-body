package com.dyzwj.producerhello.controller;

import com.dyzwj.producerhello.domain.User;
import org.springframework.web.bind.annotation.*;

/**
 * @author zwj
 * @version 1.0.0
 * @ClassName UserController.java
 * @Description TODO
 * @createTime 2020年01月17日 14:59:00
 */
@RestController
public class UserController {

    @GetMapping("/get")
    public String getTest(User user){
        return user.toString();

    }

    /**
     * 参数是bean时

     *  -
     * @param user
     * @return
     */

    @PostMapping("/post")
    public String postTest(@RequestBody User user){
        return user.toString();
    }

}
