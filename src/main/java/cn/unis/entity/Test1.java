package cn.unis.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import cn.ruiyi.base.constants.Constants;


@Entity
@Table(name = "test1", schema = Constants.BASE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Test1 extends IdEntity{
	private String name1;
	private List<Test2> test2List;
	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	@OneToMany(cascade=CascadeType.ALL,mappedBy="test1",orphanRemoval=true)
//	@Cascade({org.hibernate.annotations.CascadeType.ALL})
	public List<Test2> getTest2List() {
		return test2List;
	}

	private void setTest2List(List<Test2> test2List_) {
		this.test2List = test2List_;
	}
	public void addTest2List(List<Test2> test2List_) {
		this.test2List.clear();
		this.test2List.addAll(test2List_);
	}


}
