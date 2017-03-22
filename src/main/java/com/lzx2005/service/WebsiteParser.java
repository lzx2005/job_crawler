package com.lzx2005.service;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.nodes.Document;

/**
 * Created by Lizhengxian on 2017/3/22.
 */
public interface WebsiteParser {

    JSONObject parse(JSONObject jsonObject,Document html);
}
