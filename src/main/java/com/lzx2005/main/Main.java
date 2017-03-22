package com.lzx2005.main;

import com.alibaba.fastjson.JSONObject;
import com.lzx2005.dao.MongoDao;
import com.lzx2005.http.HttpHelper;
import com.lzx2005.service.impl.NetEaseWebsiteParser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.UnknownHostException;

/**
 * Created by Lizhengxian on 2017/3/22.
 */
public class Main {
    public static void main(String[] args) throws UnknownHostException {
        HttpHelper httpHelper = new HttpHelper();
        NetEaseWebsiteParser netEaseWebsiteParser = new NetEaseWebsiteParser();
        MongoDao mongoDao = new MongoDao();
        boolean hasNext =true;
        int page=1;
        while (hasNext){
            Document document = httpHelper.readWebsite("http://hr.163.com/position/list.do?currentPage="+page);
            Elements tbodys = document.getElementsByTag("tbody");
            //System.out.println(tbodys.size());
            if(tbodys!=null&&tbodys.size()>0){
                for(Element tbody : tbodys){
                    Elements trs = tbody.getElementsByTag("tr");
                    //System.out.println(trs.size());
                    for(Element tr : trs){
                        if(!tr.hasAttr("id")){
                            Elements tds = tr.getElementsByTag("td");
                            //System.out.println(tds.size());
                            for(Element td : tds){
                                Elements as = td.getElementsByTag("a");
                                if(as!=null&&as.size()>0){
                                    Element a = as.get(0);
                                    String href = a.attr("href");
                                    String jdUrl = "http://hr.163.com"+href;
                                    System.out.println("开始读取地址："+href);
                                    Document jdDocument = httpHelper.readWebsite(jdUrl);
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.put("href",href);
                                    jsonObject.put("url",jdUrl);
                                    jsonObject.put("jobId",href.split("=")[1]);
                                    JSONObject jobDetail = netEaseWebsiteParser.parse(jsonObject,jdDocument);
                                    System.out.println(jobDetail);
                                    if(jobDetail!=null){
                                        mongoDao.saveToMongoDB(jobDetail);
                                    }
                                    System.out.println("------------------------");

                                }
                                //System.out.print(td.text()+" ");
                            }
                            //System.out.println();
                        }
                    }
                }
                page++;
            }else{
                hasNext = false;
            }
        }
    }
}
