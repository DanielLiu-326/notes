package autowired.service.impl;

import autowired.domain.Item;
import autowired.service.ItemService;
import org.springframework.stereotype.*;


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
@Service("itemService")
public class ItemServiceImpl extends BaseServiceImpl<Item>
	implements ItemService
{
	//@Autowired
	//	private BaseDao<Item> dao; //实际注入的是ItemDaoImpl

	//public void addEntity(Item entity)
	//	{
	//		System.out.println("调用" + dao
	//			+ "保存实体：" + entity);
	//		dao.save(entity);
	//	}

}
