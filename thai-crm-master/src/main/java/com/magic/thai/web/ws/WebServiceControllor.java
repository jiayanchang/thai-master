package com.magic.thai.web.ws;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.magic.thai.db.domain.ChannelOrder;
import com.magic.thai.db.domain.Goods;
import com.magic.thai.db.domain.MerchantOrder;
import com.magic.thai.db.domain.MerchantOrderGoods;
import com.magic.thai.db.service.ChannelService;
import com.magic.thai.db.service.InterfaceOrderService;
import com.magic.thai.exception.ThaiException;
import com.magic.thai.exception.webservice.FormatException;
import com.magic.thai.exception.webservice.NativeException;
import com.magic.thai.exception.webservice.ParameterException;
import com.magic.thai.util.Asserts;
import com.magic.thai.web.ws.vo.CheckGoodsVo;
import com.magic.thai.web.ws.vo.CreateOrderVo;
import com.magic.thai.web.ws.vo.QueryGoodsesVo;
import com.magic.thai.web.ws.vo.QueryOrderVo;
import com.magic.thai.web.ws.vo.RefundOrderVo;
import com.magic.thai.web.ws.vo.TravelerVo;
import com.magic.thai.web.ws.vo.response.GoodsListVo;
import com.magic.thai.web.ws.vo.response.QueryOrderResult;
import com.magic.thai.web.ws.vo.response.WebServiceResult;

@Controller
@RequestMapping(value = "/ws")
public class WebServiceControllor {

	static Logger logger = LoggerFactory.getLogger(WebServiceControllor.class);

	@Autowired
	private InterfaceOrderService interfaceOrderService;
	@Autowired
	private ChannelService channelService;

	@RequestMapping(value = "/queryGoodses", headers = "Accept=application/xml")
	public void queryGoodses(@RequestBody String requestBody, HttpServletResponse response, ModelMap model) throws Exception {
		logger.info("threadId={}, queryGoodses={}", Thread.currentThread().getId(), requestBody);
		QueryGoodsesVo vo = (QueryGoodsesVo) unmarshall(requestBody, QueryGoodsesVo.class);
		// WebServiceResult result = new WebServiceResult();
		try {
			Asserts.isTrue(StringUtils.isNotBlank(vo.getToken()), new ParameterException("TOKEN不能为空"));
			List<Goods> goodses = interfaceOrderService.queryGoodses(vo);
			channelService.refreshSoldGoodsCount(channelService.load(vo.getToken()), goodses.size());
			GoodsListVo goodsListVo = new GoodsListVo();
			goodsListVo.setGoodses(goodses);
			responseResult(response, new WebServiceResult().success(goodsListVo));
		} catch (ThaiException e) {
			responseResult(response, new WebServiceResult().fail(e));
		} catch (JAXBException e) {
			e.printStackTrace();
			responseResult(response, new WebServiceResult().fail(new FormatException("请求参数格式异常")));
		} catch (Exception e) {
			e.printStackTrace();
			responseResult(response, new WebServiceResult().fail(new NativeException("系统内部错误")));
		}
	}

	@RequestMapping(value = "/createOrder", headers = "Accept=application/xml")
	public void createOrder(@RequestBody String requestBody, HttpServletResponse response, ModelMap model) throws Exception {
		logger.info("threadId={}, createOrder={}", Thread.currentThread().getId(), requestBody);
		CreateOrderVo vo = (CreateOrderVo) unmarshall(requestBody, CreateOrderVo.class);
		try {
			Asserts.isTrue(StringUtils.isNotBlank(vo.getToken()), new ParameterException("TOKEN不能为空"));
			Asserts.isTrue(StringUtils.isNotBlank(vo.getOrderContactor()), new ParameterException("订单联系人不能为空"));
			Asserts.isTrue(StringUtils.isNotBlank(vo.getOrderContactorEmail()), new ParameterException("订单联系邮箱不能为空"));
			Asserts.isTrue(StringUtils.isNotBlank(vo.getOrderContactorMobile()), new ParameterException("订单联系电话不能为空"));

			Asserts.notNull(vo.getTravelers(), new ParameterException("游客不能为空"));
			Asserts.isTrue(vo.getTravelers().size() > 0, new ParameterException("游客不能为空"));
			Asserts.isTrue(vo.getGoodses().size() > 0, new ParameterException("商品不能为空"));

			for (TravelerVo travelerVo : vo.getTravelers()) {
				Asserts.isTrue(StringUtils.isNotBlank(travelerVo.getIdNo()), new ParameterException("游客证件号不能为空"));
				Asserts.notNull(travelerVo.getIdType(), new ParameterException("游客证件类型不能为空"));
				Asserts.isTrue(StringUtils.isNotBlank(travelerVo.getFirstName()), new ParameterException("游客姓不能为空"));
				Asserts.isTrue(StringUtils.isNotBlank(travelerVo.getLastName()), new ParameterException("游客名不能为空"));
				Asserts.isTrue(StringUtils.isNotBlank(travelerVo.getNationality()), new ParameterException("游客国籍不能为空"));
			}
			ChannelOrder order = interfaceOrderService.create(vo);
			responseResult(response, new WebServiceResult().success(order.getChannelOrderNo()));
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
			ChannelOrder order = interfaceOrderService.query(vo);

			QueryOrderResult result = new QueryOrderResult();
			result.setCompleted(true);
			for (MerchantOrder merchantOrder : order.getMerchantOrders()) {
				for (MerchantOrderGoods goods : merchantOrder.getGoodses()) {
					result.getGoodsStatuses().put(goods.getId(), merchantOrder.isCompleted() ? "已完成" : "处理中");
					if (!merchantOrder.isCompleted()) {
						result.setCompleted(false);
					}
				}
			}
			responseResult(response, new WebServiceResult().success(result));
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
			RefundOrderVo vo = (RefundOrderVo) unmarshall(requestBody, RefundOrderVo.class);
			Asserts.notNull(vo.getToken(), new ParameterException("TOKEN不能为空"));
			Asserts.notNull(vo.getOrderNo(), new ParameterException("单号不能为空"));
			Asserts.isFalse(vo.getGoodsVo().size() == 0, new ParameterException("商品ID为空"));
			interfaceOrderService.refund(vo);
			responseResult(response, new WebServiceResult().success("退单申请成功"));
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
