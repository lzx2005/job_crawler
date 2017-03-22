package com.lzx2005.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lzx2005.service.WebsiteParser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Lizhengxian on 2017/3/22.
 */
public class NetEaseWebsiteParser implements WebsiteParser {
    public JSONObject parse(JSONObject jsonObject,Document html) {

        Elements jobTitles = html.getElementsByClass("job-title");
        if(jobTitles!=null&&jobTitles.size()>0){
            Element jobTitle = jobTitles.get(0);
            //System.out.println(jobTitle.text());
            jsonObject.put("jobTitle",jobTitle.text());
        }

        Elements postDates = html.getElementsByClass("post-date");
        if(postDates!=null&&postDates.size()>0){
            Element postDate = postDates.get(0);
            //System.out.println(postDate.text().split("：")[1]);
            jsonObject.put("postDate",postDate.text().split("：")[1]);
        }

        Elements tds = html.getElementsByTag("td");
        if(tds!=null&&tds.size()>=6){

            /*
            *   <td>技术&gt;前端及客户端开发</td>
            *   <td>本科</td>
            *   <td>杭州市</td>
            *   <td>不限</td>
            *   <td> 1人 </td>
            *   <td>全职</td>
            * */
            Element typeAll = tds.get(0);
            String[] split = typeAll.text().split(new String(">"));
            String type1 = split[0];
            String type2 = split[1];

            jsonObject.put("type1",type1);
            jsonObject.put("type2",type2);

            Element eduElement = tds.get(1);
            jsonObject.put("edu",eduElement.text());

            Element cityElement = tds.get(2);
            jsonObject.put("city",cityElement.text());

            Element expElement = tds.get(3);
            jsonObject.put("exp",expElement.text());

            Element headCountElement = tds.get(4);
            jsonObject.put("headCount",headCountElement.text());

            Element jobTypeElement = tds.get(5);
            jsonObject.put("jobType",jobTypeElement.text());
        }
        //System.out.println("----------");
        Elements sectionContents = html.getElementsByClass("section-content");
        if(sectionContents!=null&&sectionContents.size()>=2){
            Element jobDesc = sectionContents.get(0);
            Element jobRequ = sectionContents.get(1);
            //System.out.println(jobDesc.text());
            //System.out.println("----------");
            //System.out.println(jobRequ.text());

            jsonObject.put("jobDesc",jobDesc.text());
            jsonObject.put("jobRequ",jobRequ.text());
        }

        if(jsonObject.get("jobTitle")==null){
            return null;
        }
        return jsonObject;
    }
}
