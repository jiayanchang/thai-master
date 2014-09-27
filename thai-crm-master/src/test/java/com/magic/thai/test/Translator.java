package com.magic.thai.test;

import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class Translator {

	public static void toEn(String query) throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet("http://translate.google.cn/translate_a/single?client=t&sl=auto&tl=en&hl=zh-CN"
				+ "&dt=bd&dt=ex&dt=ld&dt=md&dt=qc&dt=rw&dt=rm&dt=ss&dt=t&dt=at&dt=sw&ie=UTF-8&oe=UTF-8&swap=1&rom=1&ssel=6&tsel=3&q="
				+ URLEncoder.encode(query, "utf-8"));

		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			System.out.println(EntityUtils.toString(entity));
			httpget.abort();
		}
	}

	public static void toZh(String query) throws Exception {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet("http://translate.google.cn/translate_a/single?client=t&sl=th&tl=zh-CN&hl=zh-CN"
				+ "&dt=bd&dt=ex&dt=ld&dt=md&dt=qc&dt=rw&dt=rm&dt=ss&dt=t&dt=at&dt=sw&ie=UTF-8&oe=UTF-8&ssel=3&tsel=3" + "&q="
				+ URLEncoder.encode(query, "utf-8"));

		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			System.out.println(EntityUtils.toString(entity));
			httpget.abort();
		}
	}

	public static void main(String[] args) throws Exception {
		String query = "วัน ดำน้ำดูปะการัง ภูเก็ต การเดินทาง ออกไปในทะเล";
		toEn(query);
		toZh(query);
	}

}
