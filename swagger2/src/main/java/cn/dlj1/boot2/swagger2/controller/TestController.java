package cn.dlj1.boot2.swagger2.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api("测试demo")
@RestController
public class TestController {

    @ApiOperation(value = "首页", notes = "默认返回")
    @ApiImplicitParam(name = "id", value = "学生ID", paramType = "path", required = true, dataType = "Integer")
    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @ApiOperation(value = "测试接口", notes = "无")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "name", value = "姓名", paramType = "body", required = true, dataType = "String"),
        @ApiImplicitParam(name = "work", value = "工作", paramType = "body", required = true, dataType = "String")
    })
    @GetMapping(value = "/test")
    public String test(
            @RequestParam String name,
            @RequestParam String work
    ){

        return name + " 的工作是 " + work;
    }


}
