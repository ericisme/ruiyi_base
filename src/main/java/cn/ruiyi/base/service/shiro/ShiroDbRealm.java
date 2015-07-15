/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package cn.ruiyi.base.service.shiro;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.ruiyi.base.entity.Role;
import cn.ruiyi.base.entity.User;
import cn.ruiyi.base.service.account.AccountService;
import cn.ruiyi.base.util.StringUtil;

public class ShiroDbRealm extends AuthorizingRealm
{
	private static Logger log = LoggerFactory.getLogger(ShiroDbRealm.class);


	protected AccountService accountService;

	

//	public ShiroDbRealm()
//	{
//		setAuthenticationTokenClass(CasToken.class);
//	}
//	/**
//	 * 认证回调函数,登录时调用.
//	 */
//	@Override
//	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException
//	{
//		CasToken casToken = (CasToken) authcToken;
//		if (authcToken == null)
//		{
//			return null;
//		}
//
//		String ticket = (String) casToken.getCredentials();
//		if (!StringUtils.hasText(ticket))
//		{
//			return null;
//		}
//
//		String userId =(String) casToken.getPrincipal();
//		User user = accountService.findUserByLoginName(userId);
//		if (user != null)
//		{
//			return new SimpleAuthenticationInfo(new ShiroUser(user.getId(), user.getLoginName()),
//					casToken.getCredentials(), getName());
//		}else{
//			return null;
//		}
//	}
	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException
	{
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		//System.out.println("验证");
//		System.out.println("token.getUsername():"+token.getUsername());
//		System.out.println("token.getPassword():"+token.getPassword());		
//		System.out.println("token.getPrincipal().toString():"+(token.getPrincipal().toString()));
//		System.out.println("token.getCredentials().toString():"+(token.getCredentials().toString()));
//		//token.getPassword();		
		String username = token.getUsername();
		if (token.getUsername() == null) {
			throw new AccountException(
					"Null usernames are not allowed by this realm.");
		}
		User user = null;
		//try{
			user = accountService.findUserByLoginName(token.getUsername());
			if(user==null){
					throw new UnknownAccountException("No account found for user [" + username + "]");
			}
		//}catch(Exception e){
		//	throw new UnknownAccountException("No account found for user ["
		//			+ username + "]");
		//}		
		//System.out.println("user email:"+user.getEmail());		
		SimpleAuthenticationInfo saInfo = new SimpleAuthenticationInfo(
				user.getLoginName(), user.getPassword(), getName());
		// 用用户名填盐
		//saInfo.setCredentialsSalt(ByteSource.Util.bytes(username));
		return saInfo;		
		//return null;
	}
	


	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
	{
		String loginName = (String) principals.getPrimaryPrincipal();
		//System.out.println("doGetAuthorizationInfo loginName:"+loginName);
		User user = accountService.findUserByLoginName(loginName);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		for (Role role : user.getRoleList())
		{
			// 基于Role的权限信息
			info.addRole(role.getName());
			//System.out.println("role name is " + role.getName());
			// 基于Permission的权限信息
			info.addStringPermissions(role.getPermissions());
			//System.out.println("role permissions is " + role.getPermissionNames());
		}
		return info;
	}
	
	/**
	 * 更新用户授权信息缓存.
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		principals.getRealmNames();
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清除所有用户授权信息缓存.
	 */
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}

	@Autowired
	public void setAccountService(AccountService accountService)
	{
		this.accountService = accountService;
	}

	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public static class ShiroUser implements Serializable
	{
		private static final long serialVersionUID = -1373760761780840081L;

		public Long id;

		public String loginName;

		public ShiroUser(Long id, String loginNamem)
		{
			this.id = id;
			this.loginName = loginNamem;
		}

		public Long getId()
		{
			return id;
		}

		public void setId(Long id)
		{
			this.id = id;
		}

		public String getLoginName()
		{
			return loginName;
		}

		public void setLoginName(String loginName)
		{
			this.loginName = loginName;
		}

		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString()
		{
			return id.toString();
		}

		/**
		 * 重载equals,只计算loginName;
		 */
		@Override
		public int hashCode()
		{
			return HashCodeBuilder.reflectionHashCode(this, "loginName");
		}

		/**
		 * 重载equals,只比较loginName
		 */
		@Override
		public boolean equals(Object obj)
		{
			return EqualsBuilder.reflectionEquals(this, obj, "loginName");
		}
	}
}
