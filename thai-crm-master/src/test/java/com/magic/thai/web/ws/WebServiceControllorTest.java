package com.magic.thai.web.ws;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import com.magic.thai.web.ws.vo.CreateOrderVo;
import com.magic.thai.web.ws.vo.QueryGoodsesVo;
import com.magic.thai.web.ws.vo.TravelerVo;

public class WebServiceControllorTest {

	private static final String token = "123";

	@Test
	public void testCreatOrder() throws Exception {
		String url = "http://localhost:8080/crm/ws/createOrder";

		CreateOrderVo createOrderVo = new CreateOrderVo();
		createOrderVo.setDeptDate("2014-01-02");
		createOrderVo.setGoodsId(1);
		createOrderVo.setOrderContactorMobile("13900987766");
		createOrderVo.setOrderContactor("贾彦昌");
		createOrderVo.setToken("61ab279c07a758c9e64f40fec837e4");

		TravelerVo vo = new TravelerVo();
		// vo.setBirth(birth);
		// vo.setEffectiveDate(effectiveDate);
		// vo.setGender(gender);
		vo.setIdNo("2929384747473939922");
		// vo.setIdType(idType);
		vo.setMobile("13900987766");
		vo.setName("贾彦昌");
		vo.setNationality("中国");
		// vo.setType(type);

		createOrderVo.getTravelers().add(vo);

		String xmlData = marshall(createOrderVo);
		String html = xmlrequest(url, xmlData);

		System.out.println(html);
	}

	@Test
	public void testQueryGoodses() throws Exception {
		String url = "http://localhost:8080/crm/ws/queryGoodses";

		QueryGoodsesVo vo = new QueryGoodsesVo();
		vo.setToken(token);
		String xmlData = marshall(vo);
		String html = xmlrequest(url, xmlData);
		System.out.println(html);
	}

	private String marshall(Object vo) throws JAXBException, PropertyException {
		JAXBContext jaxbContext = JAXBContext.newInstance(vo.getClass());
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		final StringWriter stringWriter = new StringWriter();
		jaxbMarshaller.marshal(vo, stringWriter);
		return stringWriter.toString();
	}

	private String xmlrequest(String url, String xmlData) throws UnsupportedEncodingException, IOException, ClientProtocolException {
		StringEntity xmlEntity = new StringEntity(xmlData, "GBK");
		List<String[]> headers = new ArrayList<String[]>();
		headers.add(new String[] { "Cache-Control", "no-cache" });
		headers.add(new String[] { "Accept", "*/*" });
		headers.add(new String[] { "Content-Type", "text/xml" });

		Map<String, String> params = new HashMap<String, String>();
		List<NameValuePair> strParams = new ArrayList<NameValuePair>();
		for (Entry<String, String> entry : params.entrySet()) {
			strParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(strParams, "UTF-8");

		HttpPost post = new HttpPost(url);
		// setHead(post, headers);
		post.setEntity(xmlEntity);

		HttpResponse response = new DefaultHttpClient().execute(post);
		// HttpResponse response = excuteRequest(new DefaultHttpClient(), post,
		// null);
		HttpEntity responseEntity = response.getEntity();
		// 如果是块压缩，那需要使用gzip解码
		Header contentEncoding = response.getFirstHeader("Content-Encoding");
		if (contentEncoding != null && StringUtils.trimToEmpty(contentEncoding.getValue()).contains("gzip")) {
			responseEntity = new GzipDecompressingEntity(responseEntity);
		}
		String html = EntityUtils.toString(responseEntity, "UTF-8");
		return html;
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
