package com.ch5.JdbcTemplate;
import com.ch5.MyUser;

import java.util.List;
public interface TestDao {
	public int update(String sql, Object[] param);
	public List<MyUser> query(String sql, Object[] param);
}
