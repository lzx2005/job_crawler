package com.lzx2005.dao;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClientURI;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.net.UnknownHostException;

/**
 * Created by Lizhengxian on 2017/3/22.
 */
public class MongoDao {

    private MongoTemplate mongoTemplate;

    public MongoDao() throws UnknownHostException {
        Mongo mongo = Mongo.Holder.singleton().connect(new MongoClientURI("mongodb://127.0.0.1:27017"));
        this.mongoTemplate = new MongoTemplate(mongo,"job");
    }

    public void saveToMongoDB(JSONObject jsonObject){
        mongoTemplate.insert(jsonObject,"job_detail2");
    }

    public static void main(String[] args) throws UnknownHostException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("a",1);
        jsonObject.put("b","b");
        jsonObject.put("c",true);
        new MongoDao().saveToMongoDB(jsonObject);
    }
}
