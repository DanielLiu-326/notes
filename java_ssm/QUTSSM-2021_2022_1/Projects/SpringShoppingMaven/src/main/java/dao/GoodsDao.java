package dao;

import bean.vo.GoodsVo;

import java.util.List;

public interface GoodsDao {
    List getGoodsByPage(int pageNo);
    GoodsVo getGoodsById(String goodsId);
    int getPageCount();
}
