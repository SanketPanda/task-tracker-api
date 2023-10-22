package com.task.tracker.controller.user;

import com.task.tracker.dto.refuser.RefUserDTO;
import com.task.tracker.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("user-id/{userId}")
    private RefUserDTO getUserById(@PathVariable("userId") final String userId){
        return userService.getUserDetailsById(userId);
    }

    @GetMapping("user-name/{userName}")
    private List<RefUserDTO> searchUserByName(@PathVariable("userName") final String userName){
        return userService.getUserDetailsByName(userName);
    }
}
