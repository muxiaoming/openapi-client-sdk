package com.zhou.openapiclientsdk.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * @Author: code muxiaoming
 * @DateCreatedIn: 2022/12/3 23:28
 * @Description: 签名工具类
 */
public class SignUtils {
    /**
     *
     * @param body 参数
     * @param secretKey secretKey
     * @return sign
     */
    public static String genSign(String body, String secretKey) {
        Digester sha = new Digester(DigestAlgorithm.SHA256);
        String content = body + "." + secretKey;
        //String md5Hex1 = DigestUtil.md5Hex(testStr);
        return sha.digestHex(content, CharsetUtil.UTF_8);
    }
}
