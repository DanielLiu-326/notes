package service.impl;

import dao.GoodsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.GoodsService;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    GoodsDao goodsDao;


    @Override
    public List getGoodsByPage(int pageNo) {
        return goodsDao.getGoodsByPage(pageNo);
    }

    @Override
    public int getPageCount() {
        return goodsDao.getPageCount();
    }
}
