package cn.dlj1.cms.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 请求工具类
 *
 * @author fivewords(443672581@qq.com)
 * @date 2017年6月7日
 */
public class HttpUtils {

    public static final String UTF8 = "UTF-8";

    /**
     * 默认utf8的get请求
     *
     * @param url
     * @return
     */
    public static String get(String url) {
        return get(url, UTF8);
    }

    /**
     * 发送get请求
     *
     * @param url
     * @param encoding
     * @return
     */
    public static String get(String url, String encoding) {
        // 创建默认的httpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String body = null;
        try {
            // 用get方法发送http请求
            HttpGet get = new HttpGet(url);
            CloseableHttpResponse httpResponse = null;
            // 发送get请求
            httpResponse = httpClient.execute(get);
            try {
                // response实体
                HttpEntity entity = httpResponse.getEntity();
                if (entity != null) {
                    // 按指定编码转换结果实体为String类型
                    body = EntityUtils.toString(entity, encoding);
                }
                EntityUtils.consume(entity);
            } finally {
                httpResponse.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return body;
    }

    /**
     * 自定义参数的get请求
     *
     * @param url
     * @param params
     * @param encoding
     * @return
     */
    public static String get(String url, Map<String, String> params, String encoding) {
        if (params != null) {
            StringBuffer sb = new StringBuffer();

            for (Entry<String, String> entry : params.entrySet()) {
                sb.append(entry.getKey());
                sb.append("=");
                try {
                    sb.append(URLEncoder.encode(entry.getValue(), encoding));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                sb.append("&");
            }
            if (StringUtils.isNotEmpty(url)) {
                if (url.indexOf("?") == -1) {
                    url = url + "?" + sb.toString();
                } else {
                    url = url + "&" + sb.toString();
                }
            }
        }
        return get(url, encoding);
    }

    public static String get(String url, Map<String, String> params) {
        return get(url, params, UTF8);
    }

    /**
     * 默认utf8的Post请求
     *
     * @param url
     * @param params
     * @return
     * @throws IOException
     */
    public static String post(String url, Map<String, String> params) throws IOException {
        return post(url, params, UTF8);
    }

    /**
     * 发送post请求
     *
     * @param url
     * @param params
     * @param encoding
     * @return
     * @throws IOException
     */
    public static String post(String url, Map<String, String> params, String encoding) throws IOException {
        String body = "";

        // 创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        // 创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);

        // 装填参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (params != null) {
            for (Entry<String, String> entry : params.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        // 设置参数到请求对象中
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));

        // 设置header信息
        // 指定报文头【Content-type】、【User-Agent】
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        //httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        // 执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost);
        // 获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            // 按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, encoding);
        }
        EntityUtils.consume(entity);
        // 释放链接
        response.close();
        return body;
    }

    /**
     * postJSON
     *
     * @param url
     * @param json
     * @param encoding
     * @return
     */
    public static String postJSON(String url, String json, String encoding) {
        URL httpUrl;
        String response = null;
        try {
            httpUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            connection.setReadTimeout(30000);
            connection.connect();
            OutputStream os = connection.getOutputStream();
            IOUtils.write(json, os, encoding);
            os.close();
            InputStream is = connection.getInputStream();
            response = IOUtils.toString(is);
            is.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * postJSON<br>
     * 默认UTF-8字符集
     *
     * @param url
     * @param json
     * @return
     */
    public static String postJSON(String url, String json) {
        return postJSON(url, json, UTF8);
    }


}
