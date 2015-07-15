package cn.ruiyi.base.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;
import org.springside.modules.utils.Collections3;

import cn.ruiyi.base.constants.Constants;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;

/**
 * 角色. * 
 * @author eric
 */
@Entity
@Table(name = "base_role",schema=Constants.BASE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//@SequenceGenerator(name = "YPT_ROLE", sequenceName = Constants.YPT_BASE+"."+"YPT_ROLE_SEQ", allocationSize = 1)
public class Role implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1359405125239265918L;

	private Long id;
	@Id
	//@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="YPT_ROLE")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="role_id")
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * 角色名称
	 */
	@NotEmpty
	private String name;
	
	/**
	 * 角色描述
	 */
	private String description;
	
	/**
	 * 角色状态(-1,停用，1、启用
	 */
	private int status;

	/**
	 * 权限集合
	 */
	private List<Permission> permissionList = Lists.newArrayList(); // 有序的关联对象集合
	

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Role() {
	}

	public Role(Long id) {
		this.id = id;
	}
	
	@Column(nullable=false,length=200)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	// 多对多定义
	@ManyToMany(targetEntity=Permission.class,cascade = { CascadeType.PERSIST,CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "base_permission_role" ,schema=Constants.BASE, joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = { @JoinColumn(name = "perm_id") })
	// Fecth策略定义
	@Fetch(FetchMode.SUBSELECT)
	// 集合按id排序
	@OrderBy("id ASC")
	// 缓存策略
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<Permission> getPermissionList()
	{
		return permissionList;
	}

	public void setPermissionList(List<Permission> permissionList)
	{
		this.permissionList = permissionList;
	}

	/**
	 * <pre>
	 * 取得角色的权限名
	 * </pre>
	 * @return
	 */
	@Transient
	@JsonIgnore
	public String getPermissionNames() {
		return Collections3.extractToString(permissionList, "permName", ", ");
	}
	
	/**
	 * <pre>
	 * 取得角色的权限值
	 * </pre>
	 * @return
	 */
	@Transient
	@JsonIgnore
	public String getPermissionValues(){
		return Collections3.extractToString(permissionList, "permValue", ", ");
	}
	
	/**
	 * <pre>
	 * 取得角色权限值集合
	 * </pre>
	 * @return List<String>
	 */
	@SuppressWarnings("unchecked")
	@Transient
	@JsonIgnore
	public List<String> getPermissions(){
		return Collections3.extractToList(permissionList, "permValue");
	}
	@Column(columnDefinition = "INTEGER default 1")
	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
