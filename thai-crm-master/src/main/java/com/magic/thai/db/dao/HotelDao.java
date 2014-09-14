package com.magic.thai.db.dao;

import java.util.List;

import com.magic.thai.db.domain.Hotel;
import com.magic.thai.db.vo.HotelVo;
import com.magic.thai.util.PaginationSupport;

public interface HotelDao {

	public Hotel loadById(int id);

	public Integer create(Hotel entity);

	public void update(Hotel entity);

	public void delete(Hotel Hotel);

	public void delete(int HotelId);

	public PaginationSupport getHotelesPage(String title, String dept, String arr, int status, int currPage, Integer merchantId);

	public List<Hotel> list(HotelVo vo);

}
