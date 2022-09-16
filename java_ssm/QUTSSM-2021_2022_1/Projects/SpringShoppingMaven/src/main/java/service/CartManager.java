package service;

import java.util.ArrayList;
import java.util.Iterator;

import bean.vo.GoodsVo;
import bean.vo.Item;
import dao.GoodsDao;
import dao.impl.GoodsDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartManager {
//cartmanager相当于服务层，必须被容器中的controller，即AddToCart调用，AddToCart必须通过Init方法放到容器中
	@Autowired
	GoodsDao goodsDao;

	ArrayList<Item> cart = new ArrayList<Item>();
	
	public void addToCart(String goodsId, int quantity){
//		GoodsDaoImpl goodsDAO = new GoodsDaoImpl();
		
		GoodsVo g = goodsDao.getGoodsById(goodsId);
		
		Iterator<Item> it = cart.iterator();
		boolean find = false;
		while(it.hasNext()){
			Item oneItem = it.next();
			if(oneItem.getGoods().getGoodsId().equalsIgnoreCase(goodsId)){
				oneItem.setQuantity(oneItem.getQuantity() + quantity);
				find = true;
			}
		}
		if(!find){
				Item newItem = new Item(g,quantity);
				cart.add(newItem);
		}
	}

	public void update(String goodsId, int quantity){
//		System.out.println(goodsId);
		Iterator<Item> it = cart.iterator();
		while(it.hasNext()){
			Item oneItem = it.next();
			if(oneItem.getGoods().getGoodsId().equalsIgnoreCase(goodsId)){
				oneItem.setQuantity(quantity);
				break;
			}
		}
		
	}
	
	public void delete(String goodsId){
		if(cart != null){
			Iterator it = cart.iterator();
			while(it.hasNext()){
				Item temp = (Item)it.next();
				String tGoodsId = temp.getGoods().getGoodsId();
				
				if(tGoodsId.equals(goodsId)){
					cart.remove(temp);
					break;
				}
				
			}//while
			
		}//if
	}
	
	
	public ArrayList<Item> getCart() {
		return cart;
	}

	public void setCart(ArrayList<Item> cart) {
		this.cart = cart;
	}
}
