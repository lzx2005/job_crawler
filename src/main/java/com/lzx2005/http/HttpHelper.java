package com.lzx2005.http;



import com.alibaba.fastjson.JSONObject;
import com.lzx2005.service.impl.NetEaseWebsiteParser;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * Created by Lizhengxian on 2017/3/22.
 */
public class HttpHelper {

    public Document readWebsite(String urlString){
        try {
            URL url = new URL(urlString);
            return readWebsite(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Document readWebsite(URL url){
        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod(url.toString());
        try {
            int httpCode = httpClient.executeMethod(getMethod);
            if(httpCode==200){
                byte[] responseBody = getMethod.getResponseBody();
                String html = new String(responseBody, "UTF-8");
                Document document = Jsoup.parse(html);
                return document;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Document s = new HttpHelper().readWebsite("http://hr.163.com/position/list.do?postType=01");
        //JSONObject parse = new NetEaseWebsiteParser().parse(s);
    }
}
