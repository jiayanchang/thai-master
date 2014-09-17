package com.magic.thai.web.ws;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.magic.thai.db.domain.Order;
import com.magic.thai.db.service.InterfaceOrderService;
import com.magic.thai.exception.ThaiException;
import com.magic.thai.exception.webservice.FormatException;
import com.magic.thai.exception.webservice.NativeException;
import com.magic.thai.exception.webservice.ParameterException;
import com.magic.thai.util.Asserts;
import com.magic.thai.web.ws.vo.CheckGoodsVo;
import com.magic.thai.web.ws.vo.CreateOrderVo;
import com.magic.thai.web.ws.vo.QueryOrderVo;
import com.magic.thai.web.ws.vo.TravelerVo;
import com.magic.thai.web.ws.vo.WebServiceResult;

@Controller
@RequestMapping(value = "/ws")
public class WebServiceControllor {

	static Logger logger = LoggerFactory.getLogger(WebServiceControllor.class);

	@Autowired
	private InterfaceOrderService interfaceOrderService;

	@RequestMapping(value = "/createOrder", headers = "Accept=application/xml")
	public void createOrder(@RequestBody String requestBody, HttpServletResponse response, ModelMap model) throws Exception {
		logger.info("threadId={}, createOrder={}", Thread.currentThread().getId(), requestBody);
		CreateOrderVo vo = (CreateOrderVo) unmarshall(requestBody, CreateOrderVo.class);
		// WebServiceResult result = new WebServiceResult();
		try {
			Asserts.notNull(vo.getToken(), new ParameterException("TOKEN不能为空"));
			Asserts.notNull(vo.getDeptDate(), new ParameterException("出发日期不能为空"));
			Asserts.notNull(vo.getGoodsId(), new ParameterException("商品ID不能为空"));
			Asserts.notNull(vo.getOrderContactor(), new ParameterException("订单联系人不能为空"));
			Asserts.notNull(vo.getTravelers(), new ParameterException("游客不能为空"));
			Asserts.isTrue(vo.getTravelers().size() > 0, new ParameterException("游客不能为空"));

			try {
				vo.deptDateObj = DateUtils.parseDate(vo.getDeptDate(), new String[] { "yyyy-MM-dd", "yyyy/MM/dd" });
			} catch (Exception e) {
				throw new ParameterException("出发日期格式异常");
			}
			for (TravelerVo travelerVo : vo.getTravelers()) {
				Asserts.notNull(travelerVo.getIdNo(), new ParameterException("游客证件号不能为空"));
				Asserts.notNull(travelerVo.getIdType(), new ParameterException("游客证件类型不能为空"));
				Asserts.notNull(travelerVo.getName(), new ParameterException("游客姓名不能为空"));
				Asserts.notNull(travelerVo.getNationality(), new ParameterException("游客国籍不能为空"));
			}
			Order order = interfaceOrderService.create(vo);
			responseResult(response, new WebServiceResult().success(order));
		} catch (ThaiException e) {
			responseResult(response, new WebServiceResult().fail(e));
		} catch (JAXBException e) {
			responseResult(response, new WebServiceResult().fail(new FormatException("请求参数格式异常")));
		} catch (Exception e) {
			e.printStackTrace();
			responseResult(response, new WebServiceResult().fail(new NativeException("系统内部错误")));
		}
	}

	@RequestMapping(value = "/queryOrder", headers = "Accept=application/xml")
	public void queryOrder(@RequestBody String requestBody, HttpServletResponse response, ModelMap model) throws Exception {
		logger.info("threadId={}, queryOrder={}", Thread.currentThread().getId(), requestBody);
		try {
			QueryOrderVo vo = (QueryOrderVo) unmarshall(requestBody, QueryOrderVo.class);
			Asserts.notNull(vo.getToken(), new ParameterException("TOKEN不能为空"));
			Asserts.notNull(vo.getOrderNo(), new ParameterException("单号不能为空"));
			Order order = interfaceOrderService.query(vo);
			responseResult(response, new WebServiceResult().success(order));
		} catch (ThaiException e) {
			responseResult(response, new WebServiceResult().fail(e));
		} catch (JAXBException e) {
			responseResult(response, new WebServiceResult().fail(new FormatException("请求参数格式异常")));
		} catch (Exception e) {
			e.printStackTrace();
			responseResult(response, new WebServiceResult().fail(new NativeException("系统内部错误")));
		}
	}

	@RequestMapping(value = "/refundOrder", headers = "Accept=application/xml")
	public void refundOrder(@RequestBody String requestBody, HttpServletResponse response, ModelMap model) throws Exception {
		logger.info("threadId={}, refundOrder={}", Thread.currentThread().getId(), requestBody);
		try {
			QueryOrderVo vo = (QueryOrderVo) unmarshall(requestBody, QueryOrderVo.class);
			Asserts.notNull(vo.getToken(), new ParameterException("TOKEN不能为空"));
			Asserts.notNull(vo.getOrderNo(), new ParameterException("单号不能为空"));
			Order order = interfaceOrderService.query(vo);
			responseResult(response, new WebServiceResult().success(order));
		} catch (ThaiException e) {
			responseResult(response, new WebServiceResult().fail(e));
		} catch (JAXBException e) {
			responseResult(response, new WebServiceResult().fail(new FormatException("请求参数格式异常")));
		} catch (Exception e) {
			e.printStackTrace();
			responseResult(response, new WebServiceResult().fail(new NativeException("系统内部错误")));
		}
	}

	@RequestMapping(value = "/checkGoods", headers = "Accept=application/xml")
	public void checkGoods(@RequestBody String requestBody, HttpServletResponse response, ModelMap model) throws Exception {
		logger.info("threadId={}, checkGoods={}", Thread.currentThread().getId(), requestBody);
		try {
			CheckGoodsVo vo = (CheckGoodsVo) unmarshall(requestBody, CheckGoodsVo.class);
			Asserts.notNull(vo.getToken(), new ParameterException("TOKEN不能为空"));
			Asserts.notNull(vo.getGoodsId(), new ParameterException("商品ID不能为空"));
			Asserts.notNull(vo.getTravelerNum(), new ParameterException("人数不能为空"));
			Asserts.isTrue(vo.getTravelerNum().intValue() > 0, new ParameterException("人数不能小于0"));

			try {
				vo.deptDateObj = DateUtils.parseDate(vo.getDeptDate(), new String[] { "yyyy-MM-dd", "yyyy/MM/dd" });
			} catch (Exception e) {
				throw new ParameterException("出发日期格式异常");
			}

			boolean pass = interfaceOrderService.checkGoods(vo);
			responseResult(response, new WebServiceResult().success(pass));
		} catch (ThaiException e) {
			responseResult(response, new WebServiceResult().fail(e));
		} catch (JAXBException e) {
			responseResult(response, new WebServiceResult().fail(new FormatException("请求参数格式异常")));
		} catch (Exception e) {
			e.printStackTrace();
			responseResult(response, new WebServiceResult().fail(new NativeException("系统内部错误")));
		}
	}

	private Object unmarshall(String requestBody, Class<?> clz) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(clz);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		return unmarshaller.unmarshal(new StringReader(requestBody));
	}

	private void responseResult(HttpServletResponse response, WebServiceResult result) throws JAXBException, PropertyException, IOException {
		JAXBContext jaxbContext = JAXBContext.newInstance(result.getClass());
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		final StringWriter stringWriter = new StringWriter();
		jaxbMarshaller.marshal(result, stringWriter);
		String xmlData = stringWriter.toString();
		response.getWriter().write(xmlData);
		logger.info("threadId={}, response={}", Thread.currentThread().getId(), xmlData);
	}
}
