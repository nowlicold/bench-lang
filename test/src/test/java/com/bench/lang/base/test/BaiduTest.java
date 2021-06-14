package com.bench.lang.base.test;

import com.baidu.aip.imageprocess.AipImageProcess;
import com.baidu.aip.util.Base64Util;
import com.baidu.aip.util.ImageUtil;
import com.bench.lang.base.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

/**
 * @className BaiduTest
 * @autor cold
 * @DATE 2021/6/12 23:42
 **/
public class BaiduTest {
    //设置APPID/AK/SK
    public static final String APP_ID = "24363644";
    public static final String API_KEY = "3xHK0DydX8MatvAi5KPaRgel";
    public static final String SECRET_KEY = "ZzYbdyjMOYeGi0cI292sE2fBZkmDigod";

    public static void main(String[] args) throws IOException {
        // 初始化一个AipImageProcess
        AipImageProcess client = new AipImageProcess(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);


        // 调用接口
        String path = "D:\\temp\\2.jpg";
        String write2 = "D:\\temp\\3.jpg";

        JSONObject res = client.imageQualityEnhance(path, new HashMap<String, String>());
        byte[] imageByte = Base64Util.decode(res.getString("image"));
        IOUtils.write(imageByte,write2,true);

    }
}
