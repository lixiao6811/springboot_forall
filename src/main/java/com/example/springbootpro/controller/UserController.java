package com.example.springbootpro.controller;

import com.example.springbootpro.entity.User;
import com.example.springbootpro.service.UserService;
import com.example.springbootpro.tools.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by lx on 2017/8/16.
 */

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/all/{pageNum}/{pageSize}", produces = {"application/json;charset=UTF-8"})
    public R findAllUser(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){
        return userService.findAllUser(pageNum,pageSize);
    }

    @PostMapping("/addUser")
    public Object addUser(@Valid @RequestBody User param, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return R.error(-1, bindingResult.getFieldError().getDefaultMessage());
        return userService.addUser(param);
    }




}