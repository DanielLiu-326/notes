package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.vo.DBBean;
import bean.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class GoodsDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List getGoodsByPage(int pageNo){
		//select * from goods limit  M,N
		//N=2
		//pageNo=1  M=0,pageNo=2, M=2
		//select * from goods limit (pageNo-1)*2,2
		int numPerPage = 2;
		int pageIndexBegin = (pageNo-1)*numPerPage;
		
		String sql = "select * from goods limit "+pageIndexBegin+","+numPerPage;

		RowMapper<GoodsVo> rowMapper = new BeanPropertyRowMapper<GoodsVo>(GoodsVo.class);
		return jdbcTemplate.query(sql, rowMapper);
	}
	
	
	public GoodsVo getGoodsById(String goodsId){
		RowMapper<GoodsVo> rowMapper = new BeanPropertyRowMapper<GoodsVo>(GoodsVo.class);
		return jdbcTemplate.queryForObject(
				"select * from person where id=?",
				new Object[]{goodsId}, new int[]{java.sql.Types.INTEGER},
				rowMapper);
	}

	public int getPageCount(){
		return jdbcTemplate.queryForObject(
				"select count(*) from goods",
				Integer.class);
	}


}
