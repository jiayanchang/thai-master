package com.magic.thai.web.ws.vo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.magic.thai.db.domain.Goods;

@XmlRootElement(name = "results")
@XmlAccessorType(value = XmlAccessType.PROPERTY)
public class GoodsListVo {

	private List<Goods> goodses;

	@XmlElementWrapper(name = "goodses")
	@XmlElement(name = "goods")
	public List<Goods> getGoodses() {
		return goodses;
	}

	public void setGoodses(List<Goods> goodses) {
		this.goodses = goodses;
	}

}
