package com.magic.thai.web.ws;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
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

import com.magic.thai.web.ws.vo.CheckGoodsVo;
import com.magic.thai.web.ws.vo.CreateOrderVo;
import com.magic.thai.web.ws.vo.QueryGoodsesVo;
import com.magic.thai.web.ws.vo.QueryOrderVo;
import com.magic.thai.web.ws.vo.RefundOrderVo;
import com.magic.thai.web.ws.vo.TravelerVo;

public class WebServiceControllorTest {

	private static final String token = "61ab279c07a758c9e64f40fec837e4";

	@Test
	public void testCreatOrder() throws Exception {
		String url = "http://localhost:8080/crm/ws/createOrder";

		CreateOrderVo createOrderVo = new CreateOrderVo();
		createOrderVo.setDeptDate("2014-09-19");
		createOrderVo.setGoodsId(7);
		createOrderVo.setOrderContactorMobile("13900987766");
		createOrderVo.setOrderContactor("贾彦昌");
		createOrderVo.setToken(token);

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

	@Test
	public void testCheckGoods() throws Exception {
		String url = "http://localhost:8080/crm/ws/checkGoods";

		CheckGoodsVo vo = new CheckGoodsVo();
		vo.setToken(token);
		vo.setGoodsId(7);
		vo.setDeptDate("2014-09-19");
		String xmlData = marshall(vo);
		String html = xmlrequest(url, xmlData);
		System.out.println(html);
	}

	@Test
	public void testQueryOrder() throws Exception {
		String url = "http://localhost:8080/crm/ws/queryOrder";
		QueryOrderVo vo = new QueryOrderVo();
		vo.setToken(token);
		vo.setOrderNo("020214091900000004");

		String xmlData = marshall(vo);
		String html = xmlrequest(url, xmlData);
		System.out.println(html);
	}

	@Test
	public void testRefund() throws Exception {
		String url = "http://localhost:8080/crm/ws/refundOrder";
		RefundOrderVo vo = new RefundOrderVo();
		vo.setToken(token + "2");
		vo.setOrderNo("020214091900000004");
		vo.setReason("死了，去不了了");
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

}
