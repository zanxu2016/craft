package info.luckydog.craft.controller;

import com.alibaba.fastjson.JSON;
import info.luckydog.craft.annotation.Permission;
import info.luckydog.craft.constant.PermissionEnum;
import info.luckydog.craft.model.dto.UserDTO;
import info.luckydog.craft.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * UserController
 *
 * @author eric
 * @since 2019/6/6
 */
@Api(tags = {"UserController"}, description = "用户相关接口")
@RestController
@RequestMapping("user")
@Permission(funcNecessary = {PermissionEnum.ADMIN})
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取用户信息", notes = "通过id获取用户信息")
    @ApiImplicitParam(name = "id", value = "用户Id", required = true, dataType = "Long", paramType = "path")
    @GetMapping("/get/{id}")
    public UserDTO getUser(@PathVariable("id") Long id) {
        return userService.getUser(id);
    }

    @PostMapping(value = "/add")
    public String addUser(@ModelAttribute UserDTO user) {
        userService.addUser(user);
        return "success";
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_PROBLEM_JSON_UTF8_VALUE)
    public String updateUser(@RequestBody UserDTO user) {
        log.info(JSON.toJSONString(user));
//        userService.updateUser(user);
        return "success";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "success";
    }
}
