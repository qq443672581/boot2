package cn.dlj1.boot2.jwt.service;

import cn.dlj1.boot2.jwt.config.Audience;
import cn.dlj1.boot2.jwt.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class LoginService {

    @Autowired
    private Audience audience;

    public String login(HttpServletRequest request, HttpServletResponse response, String passport, String password) {
        // 数据库查询 略
        String role = "admin";

        // 生成token
        String jwtToken = JwtUtils.createJWT(
                passport,
                password,
                role,
                audience.getClientId(),
                audience.getName(),
                audience.getExpiresSecond() * 1000,
                audience.getBase64Secret()
        );

        String result_str = "bearer;" + jwtToken;
        response.setHeader("jwt_token", result_str);
        return "success";
    }

}
