package e1common;
/**   
 * Copyright © 2016 北京恒泰实达科技发展有限公司. All rights reserved.
 * 项目名称：tech14Ehcache
 * 描述信息: 
 * 创建日期：2016年1月27日 下午6:16:39 
 * @author malitao
 * @version 
 */

import java.io.Serializable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/** 
 * EhCache的使用2
 * 参考：http://chenlinbo.iteye.com/blog/972636
 * 创建日期：2016年1月27日 下午6:16:39 
 * @author malitao
 */
public class EhCacheManager2 {

	public static void main(String[] args) {
		use1();
	}
	
	@SuppressWarnings("unused")
	public static void use1() {
	//	1.EhCache 的简单使用
		CacheManager manager = new CacheManager("src/config/other.xml");     
	
	//	缓存的创建，采用自动的方式
		CacheManager singletonManager = CacheManager.create();
		singletonManager.addCache("testCache");
		Cache test = singletonManager.getCache("testCache");     
	
	//	或者直接创建Cache
		singletonManager = CacheManager.create();
		Cache memoryOnlyCache = new Cache("testCache", 5000, false, false, 5, 2);
		manager.addCache(memoryOnlyCache);
		test = singletonManager.getCache("testCache");     
	
	//	删除cache
		singletonManager = CacheManager.create();
		singletonManager.removeCache("sampleCache1");     
	
	//	在使用ehcache后，需要关闭
		CacheManager.getInstance().shutdown(); 
	
	//	caches 的使用
		Cache cache = manager.getCache("sampleCache1");     
	
	//	执行crud操作
		cache = manager.getCache("sampleCache1");
		Element element = new Element("key1", "value1");
		cache.put(element);     
	
	//	update
		cache = manager.getCache("sampleCache1");
		cache.put(new Element("key1", "value1"));
		//This updates the entry for "key1"
		cache.put(new Element("key1", "value2"));     
	
	//	get Serializable
		cache = manager.getCache("sampleCache1");
		element = cache.get("key1");
		Serializable valueSer = element.getValue();     
	
	//	get non serializable
		cache = manager.getCache("sampleCache1");
		element = cache.get("key1");
		Object valueObj = element.getObjectValue();   
		System.out.println();
	
	//	remove
		cache = manager.getCache("sampleCache1");
		element = new Element("key1", "value1");
		cache.remove("key1");     
	}
}
