package cn.dlj1.cms.controller;

import cn.dlj1.cms.entity.Entity;
import cn.dlj1.cms.response.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 上传文件接口
 * <br>
 * 使用Ajax形式的上传
 */
public interface UploadController<T extends Entity> extends Controller<T> {

    @PostMapping("/upload")
    @ResponseBody
    default Result upload(HttpServletRequest request, @RequestParam(value = "ele") MultipartFile ele) {

        return getActionService().upload(request, ele);
    }

}
