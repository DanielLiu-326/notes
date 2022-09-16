package lifecycleInit.impl;

import lifecycleInit.Axe;

/**
 * Description:
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a> 
 * <br/>Copyright (C), 2001-2012, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class SteelAxe
	implements Axe
{
	public SteelAxe()
	{
		System.out.println("Spring实例化依赖bean：SteelAxe实例...");
	}
	public String chop()
	{
		return "钢斧砍柴真快";
	}
}