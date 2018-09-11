package cn.dlj1.cms.controller.supports;

import org.apache.commons.io.IOUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 附件View
 *
 *
 */
@Component
@Order(10)
public class AttachmentView implements View {

    public static final String APPLICATION_VIEW_NAME = "application/attachment";
    public static final String VIEW_NAME = AttachmentView.class.getSimpleName().substring(0, 1).toLowerCase() + AttachmentView.class.getSimpleName().substring(1);

    public static final String FILE_NAME = "attachment_file_name";
    public static final String FILE_CONTENT_NAME = "attachment_file_content";

    @Override
    public String getContentType() {
        return APPLICATION_VIEW_NAME;
    }

    @Override
    public void render(Map<String, ?> map, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        String fileName = URLEncoder.encode(httpServletRequest.getAttribute(FILE_NAME).toString(), "UTF-8");
        byte[] fileContent = (byte[]) httpServletRequest.getAttribute(FILE_CONTENT_NAME);

        httpServletResponse
                .setHeader("content-disposition", "attachment;filename=" + fileName);
        IOUtils.write(fileContent, httpServletResponse.getOutputStream());
    }

    public static String view(HttpServletRequest request, String fileName, byte[] data) {
        request.setAttribute(FILE_NAME, fileName);
        request.setAttribute(FILE_CONTENT_NAME, data);
        return VIEW_NAME;
    }

}