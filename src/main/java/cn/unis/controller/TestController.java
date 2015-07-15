package cn.unis.controller;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import cn.unis.dao.Test1Dao;
import cn.unis.entity.Test1;
import cn.unis.entity.Test2;

import com.google.common.collect.Lists;

@Controller
public class TestController {

	@RequestMapping("locale")
	public String getGetLocale(HttpServletRequest req , HttpServletResponse resp){
		System.out.println("locale:" + RequestContextUtils.getLocale(req));
		return "/unis/frontEnd/alipay/test";
	}
	@RequestMapping("test1")
	public String test1(ModelAndView model ){
		model.addObject("name", "fanzz");
		return "forward:test2";
	}
	@RequestMapping("test2")
	public ModelAndView test2(ModelAndView model,@RequestParam("name")String name,HttpServletRequest req ){
		System.out.println("1234");
		System.out.println("456");
		return null;
	}


	@Autowired
	private Test1Dao test1Dao;

	@PersistenceContext
	private EntityManager entityManager;


	@RequestMapping(value = "/wwc/test", method = RequestMethod.GET)
	@Transactional(readOnly=false)
	public void index(HttpServletRequest request,HttpServletResponse response) {
//		Test1 test_1 = new Test1();
//		test_1.setId(1L);
//		test_1.setName1("fanzz");
//		List<Test2> test2List = Lists.newArrayList();
//		test_1.setTest2List(test2List);
//		test1Dao.save(test_1);

		Test1 test = test1Dao.findOne(1L);



		Test2 t2 = new Test2();
		t2.setId(0L);
		t2.setTest1(test);
		t2.setName2(new Date().getTime() + "t2");

		Test2 t3 = new Test2();
		t3.setId(23L);
		t3.setTest1(test);
		t3.setName2(new Date().getTime() + "t3");

		List<Test2> test2List_ = Lists.newArrayList();
		test2List_.add(t2);
		test2List_.add(t3);

		test.addTest2List(test2List_);

		test1Dao.saveAndFlush(test);


//		Session session = ((Session)entityManager.getDelegate());
//		Test1 test = (Test1) session.get(Test1.class, 1L);
//		test.setName1("faa111aa1111");
//		test.getTest2List().clear();
//		List<Test2> test2List = Lists.newArrayList();
//		session.save(test);
//		session.flush();
	}
}
