package com.magic.thai.web.ws;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/ws")
public class WebServiceControllor {

	@RequestMapping(value = "/response/ContentType", headers = "Accept=application/xml")
	public void response3(HttpServletResponse response) throws IOException {
		// ①表示响应的内容区数据的媒体类型为xml格式，且编码为utf-8(客户端应该以utf-8解码)
		response.setContentType("application/xml;charset=utf-8");
		// ②写出响应体内容
		String xmlData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		xmlData += "<user><username>zhang</username><password>123</password></user>";
		response.getWriter().write(xmlData);
	}

	private static void xmlRequest() throws IOException, URISyntaxException {
		// 请求的地址
		String url = "http://localhost:9080/springmvc-chapter6/response/ContentType";
		// ①创建Http Request(内部使用HttpURLConnection)
		ClientHttpRequest request = new SimpleClientHttpRequestFactory().createRequest(new URI(url), HttpMethod.POST);
		// ②设置客户端可接受的媒体类型（即需要什么类型的响应体数据）
		request.getHeaders().set("Accept", "application/xml");
		// ③发送请求并得到响应
		ClientHttpResponse response = request.execute();
		// ④得到响应体的编码方式
		Charset charset = response.getHeaders().getContentType().getCharSet();
		// ⑤得到响应体的内容
		InputStream is = response.getBody();
		byte bytes[] = new byte[(int) response.getHeaders().getContentLength()];
		is.read(bytes);
		String xmlData = new String(bytes, charset);
		System.out.println("charset : " + charset + ", xml data : " + xmlData);
	}

}
