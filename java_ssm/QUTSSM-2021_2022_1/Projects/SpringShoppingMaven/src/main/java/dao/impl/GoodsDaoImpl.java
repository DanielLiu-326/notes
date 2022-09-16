package dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.vo.GoodsVo;
import dao.GoodsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class GoodsDaoImpl implements GoodsDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List getGoodsByPage(int pageNo){
		int numPerPage = 2;
		int pageBeginIndex = (pageNo-1)*numPerPage;
		
		String sql = "select * from goods limit " + pageBeginIndex +"," +numPerPage;

		RowMapper<GoodsVo> rowMapper = new BeanPropertyRowMapper<GoodsVo>(GoodsVo.class);

		return jdbcTemplate.query(sql,rowMapper);
	}
	
	
	public GoodsVo getGoodsById(String goodsId){
		RowMapper<GoodsVo> rowMapper = new BeanPropertyRowMapper<GoodsVo>(GoodsVo.class);
		return jdbcTemplate.queryForObject(
				"select * from goods where goodsid=?",
				new Object[]{goodsId}, new int[]{java.sql.Types.INTEGER},
				rowMapper);
	}

	public int getPageCount(){
		int numPerPage = 2;

		String sql = "select count(*) from goods";

		int totalRows = jdbcTemplate.queryForObject(sql,Integer.class);
		
		return (totalRows-1)/numPerPage+1;
		
	}


}
