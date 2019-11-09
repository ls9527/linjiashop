package cn.enilu.flash.api.controller.shop;

import cn.enilu.flash.bean.constant.factory.PageFactory;
import cn.enilu.flash.bean.core.BussinessLog;
import cn.enilu.flash.bean.dictmap.CommonDict;
import cn.enilu.flash.bean.entity.shop.Order;
import cn.enilu.flash.bean.enumeration.BizExceptionEnum;
import cn.enilu.flash.bean.exception.ApplicationException;
import cn.enilu.flash.bean.vo.front.Rets;
import cn.enilu.flash.service.shop.OrderService;
import cn.enilu.flash.utils.factory.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shop/order")
public class OrderController {
	private  Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public Object list() {
	Page<Order> page = new PageFactory<Order>().defaultPage();
		page = orderService.queryPage(page);
		return Rets.success(page);
	}
	@RequestMapping(method = RequestMethod.POST)
	@BussinessLog(value = "编辑订单", key = "name",dict= CommonDict.class)
	public Object save(@ModelAttribute Order tShopOrder){
		if(tShopOrder.getId()==null){
			orderService.insert(tShopOrder);
		}else {
			orderService.update(tShopOrder);
		}
		return Rets.success();
	}
	@RequestMapping(method = RequestMethod.DELETE)
	@BussinessLog(value = "删除订单", key = "id",dict= CommonDict.class)
	public Object remove(Long id){
		if (id == null) {
			throw new ApplicationException(BizExceptionEnum.REQUEST_NULL);
		}
		orderService.deleteById(id);
		return Rets.success();
	}
}
