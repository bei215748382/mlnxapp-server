package com.mlnx.mlnxapp.test.rest;

import com.alibaba.fastjson.JSONObject;
import com.mlnx.mlnxapp.test.util.HttpUtil;
/**
* report test类
* Tue Oct 13 09:56:44 CST 2015 GenEntityMysql工具类生成
*/ 
public class ReportTest {

	private static void regist() {

		JSONObject obj = new JSONObject();
		obj.put("key", "value");
		String sr = HttpUtil.sendPost(
				"http://localhost:8080/mlnxapp-server/rest/reports",
				obj.toJSONString());
		System.out.println(sr);
	}

	private static void findAll() {

		String sr = HttpUtil
				.sendGet("http://localhost:8080/mlnxapp-server/rest/reports/all");
		System.out.println(sr);
	}

	private static void delete() {

		String sr = HttpUtil.sendPost(
				"http://localhost:8080/mlnxapp-server/rest/reports/delete",
				"{id}");
		System.out.println(sr);
	}

	private static void findById() {

		String sr = HttpUtil
				.sendGet("http://localhost:8080/mlnxapp-server/rest/reports/{id}");
		System.out.println(sr);
	}

	public static void main(String[] args) {

		regist();
		findAll();
		delete();
		findById();
	}
}
