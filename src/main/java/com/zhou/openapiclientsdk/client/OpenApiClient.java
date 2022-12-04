package com.zhou.openapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.zhou.openapiclientsdk.model.User;
import com.zhou.openapiclientsdk.utils.SignUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: code muxiaoming
 * @DateCreatedIn: 2022/11/28 23:15
 * @Description: 调用第三方接口的客户端
 */
public class OpenApiClient {

    /**
     * accessKey
     */
    private String accessKey;

    /**
     * secretKey
     */
    private String secretKey;

    public OpenApiClient() {
    }

    public OpenApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGet(String name) {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);

        String result = HttpUtil.get("http://localhost:8123/api/name/", paramMap);
        System.out.println("result = " + result);
        return result;
    }

    public String getNameByPost(String name) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);

        String result = HttpUtil.post("http://localhost:8123/api/name/", paramMap);
        System.out.println("result = " + result);
        return result;
    }


    private Map<String, String> getHeaderMap(String body){
        Map<String, String> hashMap = new HashMap<>();
        //参数1: accessKey
        hashMap.put("accessKey", accessKey);
        //一定还能直接发送secretKey
        //hashMap.put("secretKey", secretKey);
        //body: 前端需要传递给后端的参数
        //参数2: 用户参数
        hashMap.put("body", body);
        //参数3: sign: 签名,JWT本质就是一种签名认证算法
        //签名生成算法(MD5,HMac,Sha1)输出的就是签名
        //个人理解签名认证算法就是认证签名的
        hashMap.put("sign", SignUtils.genSign(body, secretKey));
        //参数4: nonce: 随机数(防请求重放,只能用一次),但就需要服务器端保存上次用的随机数
        //当并发量大时
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        //参数5: timestamp: 时间戳(随机数有效时间,过期删除,
        // 注意nonce还是只能用一次,并非是有效时间内能用多次)
        //比如随机数有效期即时间戳五分钟,那就可以每五分钟清除一次随机数释放空间
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis()/1000));
        return hashMap;
    }

    public String getUsernameByPost(User user) {
        String json = JSONUtil.toJsonStr(user);
        HttpResponse execute = HttpRequest.post("http://localhost:8123/api/name/user")
                .body(json)
                .addHeaders(getHeaderMap(json))
                .execute();
        System.out.println("status = " + execute.getStatus());
        String result = execute.body();
        System.out.println("result = " + result);
        return result;

    }
}
