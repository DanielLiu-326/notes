package service;

import java.util.List;

public interface GoodsService {
    List getGoodsByPage(int page);

    int getPageCount();
}
