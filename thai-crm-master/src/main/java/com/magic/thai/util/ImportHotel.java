package com.magic.thai.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.magic.thai.db.dao.HotelDao;
import com.magic.thai.db.domain.Hotel;

public class ImportHotel extends BaseTest {

	@Autowired
	HotelDao dao;

	@Test
	@Transactional
	public void testHo() throws Exception {
		InputStream is = new FileInputStream("d:/1.xlsx");
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);

		Hotel hotel = null;
		List<Hotel> list = new ArrayList<Hotel>();
		// 循环工作表Sheet
		XSSFSheet hssfSheet = xssfWorkbook.getSheetAt(0);
		// 循环行Row
		for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
			// for (int rowNum = 1; rowNum <= 11; rowNum++) {
			XSSFRow hssfRow = hssfSheet.getRow(rowNum);
			if (hssfRow == null) {
				continue;
			}
			hotel = new Hotel();
			// 循环列Cell
			// 0学号 1姓名 2学院 3课程名 4 成绩
			// hotel_id chain_id chain_name brand_id brand_name hotel_name
			// hotel_formerly_name hotel_translated_name addressline1
			// addressline2 zipcode city state country countryisocode
			// star_rating longitude latitude url checkin checkout
			// numberrooms numberfloors yearopened yearrenovated photo1
			// photo2 photo3 photo4 photo5 overview rates_from continent_id
			// continent_name city_id country_id number_of_reviews
			// rating_average rates_currency

			// hotel.setChainName(chain_name 3);
			// hotel.setName(hotel_name 6);
			// hotel.setNameEn(hotel_formerly_name 7);
			// hotel.setAddress(addressline1 9);;
			// hotel.setCity(city 12);
			// hotel.setContinent(34);
			// hotel.setCountry(14);
			// hotel.setState(state 13);

			XSSFCell xh = hssfRow.getCell(2);
			if (xh != null) {
				hotel.setChainName(getValue(xh));
			}
			xh = hssfRow.getCell(5);
			if (xh != null) {
				hotel.setName(getValue(xh));
			}
			xh = hssfRow.getCell(6);
			if (xh != null) {
				hotel.setFormerlyName(getValue(xh));
			}
			xh = hssfRow.getCell(7);
			if (xh != null) {
				hotel.setTranslatedName(getValue(xh));
			}

			xh = hssfRow.getCell(8);
			if (xh != null) {
				hotel.setAddress(getValue(xh));
			}
			xh = hssfRow.getCell(11);
			if (xh != null) {
				hotel.setCity(getValue(xh));
			}
			xh = hssfRow.getCell(12);
			if (xh != null) {
				hotel.setState(getValue(xh));
			}
			xh = hssfRow.getCell(13);
			if (xh != null) {
				hotel.setCountry(getValue(xh));
			}
			xh = hssfRow.getCell(33);
			if (xh != null) {
				hotel.setContinent(getValue(xh));
			}
			list.add(hotel);
			System.out.println(rowNum + " " + hotel);
			dao.create(hotel);
		}

		for (Hotel hotel2 : list) {
			System.out.println(hotel2);
		}
	}

	/**
	 * 得到Excel表中的值
	 *
	 * @param hssfCell Excel中的每一个格子
	 * @return Excel中每一个格子中的值
	 */
	@SuppressWarnings("static-access")
	private String getValue(XSSFCell hssfCell) {
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			// 返回布尔类型的值
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			// 返回数值类型的值
			return String.valueOf(hssfCell.getNumericCellValue());
		} else {
			// 返回字符串类型的值
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}
}