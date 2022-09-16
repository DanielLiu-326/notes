package autowired.service.impl;

import autowired.dao.BaseDao;
import autowired.service.BaseService;
import org.springframework.beans.factory.annotation.*;

/**
 * Description:
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br/>Copyright (C), 2001-2016, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class BaseServiceImpl<T> implements BaseService<T>
{
	@Autowired
	private BaseDao<T> dao;
	
	public void addEntity(T entity)
	{
		System.out.println("调用" + dao
			+ "保存实体：" + entity);
		dao.save(entity);
	}
}