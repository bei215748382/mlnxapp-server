package com.mlnx.mlnxapp.test.rest;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.mlnx.mlnxapp.test.util.HttpUtil;
/**
* article test类
* Tue Oct 13 09:56:43 CST 2015 GenEntityMysql工具类生成
*/ 
public class ArticleTest {

	private static void regist() {

		JSONObject obj = new JSONObject();
		obj.put("title", "文章一");
		obj.put("body", "文章的内容巴拉巴拉巴拉");
		obj.put("author", "abm");// 准时度评分
		obj.put("time", new Date());
		obj.put("type", "社区");
		obj.put("praise", 0);
		obj.put("collect", 0);
		obj.put("comment", 0);
		System.out.println(obj.toJSONString());
		String sr = HttpUtil.sendPost(
				"http://localhost:8080/mlnxapp-server/rest/articles",
				obj.toJSONString());
		System.out.println(sr);
	}

	private static void findAll() {

		String sr = HttpUtil
				.sendGet("http://localhost:8080/mlnxapp-server/rest/articles/all");
		System.out.println(sr);
	}

	private static void delete() {

		String sr = HttpUtil.sendPost(
				"http://localhost:8080/mlnxapp-server/rest/articles/delete",
				"{id}");
		System.out.println(sr);
	}

	private static void findById() {

		String sr = HttpUtil
				.sendGet("http://localhost:8080/mlnxapp-server/rest/articles/{id}");
		System.out.println(sr);
	}

	public static void main(String[] args) {

		regist();
		findAll();
		delete();
		findById();
	}
}
