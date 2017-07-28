package com.taotao.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.service.RegisterService;

@Service
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private TbUserMapper userMapper;

	@Override
	public TaotaoResult checkData(String param, int type) {

		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();

		if (1 == type) {
			criteria.andUsernameEqualTo(param);
		} else if (2 == type) {
			criteria.andPhoneEqualTo(param);
		} else if (3 == type) {
			criteria.andEmailEqualTo(param);
		}

		List<TbUser> list = userMapper.selectByExample(example);
		if (list == null || list.isEmpty()) {
			return TaotaoResult.ok(true);
		}

		return TaotaoResult.ok(false);
	}

	@Override
	public TaotaoResult register(TbUser user) {

		if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
			return TaotaoResult.build(400, "username or password is empty...");
		}

		TaotaoResult result = checkData(user.getUsername(), 1);
		
		if (!(boolean) result.getData()) {
			return TaotaoResult.build(400, "username exist...");
		}

		if(user.getPhone() != null) {
			result = checkData(user.getPhone(), 1);
			if (!(boolean) result.getData()) {
				return TaotaoResult.build(400, "phone exist...");
			}
		}
		
		if(user.getEmail() != null) {
			result = checkData(user.getEmail(), 1);
			if (!(boolean) result.getData()) {
				return TaotaoResult.build(400, "email exist...");
			}
		}
		
		user.setCreated(new Date());
		user.setUpdated(new Date());
		
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		
		userMapper.insert(user);
		
		return TaotaoResult.ok();
	}

}
