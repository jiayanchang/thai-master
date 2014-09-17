package com.magic.thai.web.ws;

import java.io.InputStream;
import java.io.StringWriter;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import com.magic.thai.web.ws.vo.CreateOrderVo;
import com.magic.thai.web.ws.vo.ErrorMessage;
import com.magic.thai.web.ws.vo.WebServiceResult;

public class WebServiceControllorTest {

	@Test
	public void xml() throws Exception {
		String url = "http://localhost:8080/crm/ws/createOrder";

		CreateOrderVo createOrderVo = new CreateOrderVo();
		createOrderVo.setOrderContactor("贾彦昌");
		// String xmlData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		// xmlData += "<user><username>zhang</username><password>123</password></user>";
		JAXBContext jaxbContext = JAXBContext.newInstance(CreateOrderVo.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		final StringWriter stringWriter = new StringWriter();
		jaxbMarshaller.marshal(createOrderVo, stringWriter);
		String xmlData = stringWriter.toString();

		StringEntity xmlEntity = new StringEntity(xmlData, "GBK");
		List<String[]> headers = new ArrayList<String[]>();
		headers.add(new String[] { "Cache-Control", "no-cache" });
		headers.add(new String[] { "Accept", "*/*" });
		headers.add(new String[] { "Content-Type", "text/xml" });

		HttpPost post = new HttpPost(url);
		// setHead(post, headers);
		post.setEntity(xmlEntity);

		HttpResponse response = new DefaultHttpClient().execute(post);
		// HttpResponse response = excuteRequest(new DefaultHttpClient(), post, null);
		HttpEntity responseEntity = response.getEntity();
		// 如果是块压缩，那需要使用gzip解码
		Header contentEncoding = response.getFirstHeader("Content-Encoding");
		if (contentEncoding != null && StringUtils.trimToEmpty(contentEncoding.getValue()).contains("gzip")) {
			responseEntity = new GzipDecompressingEntity(responseEntity);
		}
		String html = EntityUtils.toString(responseEntity, "UTF-8");
		System.out.println(html);
	}

	@Test
	public void testTemp() throws Exception {
		WebServiceResult r = new WebServiceResult();
		ErrorMessage e = new ErrorMessage();
		e.setMessage("errorrrrrr");
		r.setData(e);
		JAXBContext jaxbContext = JAXBContext.newInstance(WebServiceResult.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		final StringWriter stringWriter = new StringWriter();
		jaxbMarshaller.marshal(r, stringWriter);
		String xmlData = stringWriter.toString();
		System.out.println(xmlData);
	}

	public void test() throws Exception {
		// 请求的地址
		String url = "http://localhost:8080/crm/ws/createOrder";
		// ①创建Http Request(内部使用HttpURLConnection)
		ClientHttpRequest request = new SimpleClientHttpRequestFactory().createRequest(new URI(url), HttpMethod.POST);
		// ②设置客户端可接受的媒体类型（即需要什么类型的响应体数据）
		// request.getHeaders().set("Accept", "application/xml");
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