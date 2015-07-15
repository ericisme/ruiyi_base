package cn.unis.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.ruiyi.base.constants.Constants;

@Entity
@Table(name = "test2", schema = Constants.BASE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Test2 extends IdEntity{

	private String name2;

	private Test1 test1;

	@ManyToOne
	public Test1 getTest1() {
		return test1;
	}

	public void setTest1(Test1 test1) {
		this.test1 = test1;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

}
