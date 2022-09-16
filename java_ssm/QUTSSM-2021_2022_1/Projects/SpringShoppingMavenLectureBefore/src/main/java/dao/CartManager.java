package dao;

import java.util.ArrayList;
import java.util.Iterator;
import bean.vo.GoodsVo;
import bean.vo.Item;

public class CartManager {
	ArrayList<Item> cart = new ArrayList<Item>();
	
	public void addToCart(String goodsId, int quantity){
		GoodsDAO goodsDAO = new GoodsDAO();
		
		GoodsVo g = goodsDAO.getGoodsById(goodsId);
		
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
