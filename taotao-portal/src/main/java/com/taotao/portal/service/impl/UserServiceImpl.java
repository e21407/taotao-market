package com.taotao.portal.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Value("${SSO_BASE_URL}")
	public String SSO_BASE_URL;
	
	@Value("${SSO_TOKEN_USER_URL}")
	private String SSO_TOKEN_USER_URL;
//	@Value("${SSO_LOGIN_PAGE_URL}")
//	public String SSO_LOGIN_PAGE_URL;
//	@Value("${SSO_REDIRICT_URL}")
//	public String SSO_REDIRICT_URL;
	
	@Override
	public TbUser getUserByToken(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
			
			if(StringUtils.isBlank(token)) {
				return null;
			}
			
			String json = HttpClientUtil.doGet(SSO_BASE_URL + SSO_TOKEN_USER_URL + token);
			TaotaoResult result = TaotaoResult.format(json);
			
			if(result.getStatus() != 200) {
				return null;
			}
			
			result = TaotaoResult.formatToPojo(json, TbUser.class);
			TbUser user = (TbUser) result.getData();
			
			return user;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

}
