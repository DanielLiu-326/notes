package servlet;

import bean.vo.Item;
import dao.CartManager;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/processCart")
public class ProcessCart extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ProcessCart() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
	  	
		String type = request.getParameter("action");
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		String goodsId = request.getParameter("goodsId");
		
		HttpSession session = request.getSession(true);
		ArrayList<Item> cart = (ArrayList<Item>)session.getAttribute("cart");
		CartManager cartManager = new CartManager();
		
//		System.out.println(goodsId);
		if(type.equalsIgnoreCase("修改")){
			cartManager.setCart(cart);
			cartManager.update(goodsId, quantity);
		}
		if(type.equalsIgnoreCase("删除")){
			cartManager.setCart(cart);			
			cartManager.delete(goodsId);
		}
		
		session.setAttribute("cart", cartManager.getCart());
		
		response.sendRedirect("cart.jsp");
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request,response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
