package com.taotao.rest;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taotao.rest.dao.JedisClient;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

public class AppTest {

	@Test
	public void testJedisClientSpring() throws Exception {
		// 创建一个spring容器
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-*.xml");
		// 从容器中获得JedisClient对象
		JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
		System.out.println(jedisClient);
		// jedisClient操作redis
		jedisClient.set("ab", "1000");
		String string = jedisClient.get("ab");
		System.out.println(string);

		applicationContext.close();
	}
	
	@Test
	public void testJedisCluster() throws Exception {
		//创建一个JedisCluster对象
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("119.29.195.17", 7001));
		nodes.add(new HostAndPort("119.29.195.17", 7002));
		nodes.add(new HostAndPort("119.29.195.17", 7003));
		nodes.add(new HostAndPort("119.29.195.17", 7004));
		nodes.add(new HostAndPort("119.29.195.17", 7005));
		nodes.add(new HostAndPort("119.29.195.17", 7006));
		//在nodes中指定每个节点的地址
		//jedisCluster在系统中是单例的。
		JedisCluster jedisCluster = new JedisCluster(nodes);
		jedisCluster.set("name", "zhangsan");
		jedisCluster.set("value", "100");
		String name = jedisCluster.get("name");
		String value = jedisCluster.get("value");
		System.out.println(name);
		System.out.println(value);
		
		
		//系统关闭时关闭jedisCluster
		jedisCluster.close();
	}

	@Test
	public void testJedisSingle() throws Exception {
		//创建一个Jedis对象
		Jedis jedis = new Jedis("119.29.195.17", 6379);
		jedis.set("test", "hello jedis");
		String string = jedis.get("test");
		System.out.println(string);
		jedis.close();
	}


}
