package com.zsga.cf.ssm.test.cache;

import org.junit.Test;

import redis.clients.jedis.Jedis;

//@RunWith(SpringJUnit4ClassRunner.class)
public class JedisTest {
	
	@Test
	public void testJedis() {
		Jedis jedis = new Jedis("10.211.55.8");
		jedis.set("foo", "bar");
		String value = jedis.get("foo");
		System.out.println(value);
	}
}
