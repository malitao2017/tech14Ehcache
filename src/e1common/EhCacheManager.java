/**   
 * Copyright © 2016 北京恒泰实达科技发展有限公司. All rights reserved.
 * 项目名称：tech14Ehcache
 * 描述信息: 
 * 创建日期：2016年1月27日 下午5:40:37 
 * @author malitao
 * @version 
 */
package e1common;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;  
import java.net.URL;

import net.sf.ehcache.CacheManager;  
import net.sf.ehcache.Ehcache;  
import net.sf.ehcache.Element;  

import org.apache.commons.logging.Log;  
import org.apache.commons.logging.LogFactory;  
  
/** 
 * 缓存管理类 
 * EhCache的使用 
 * 参考：http://x125858805.iteye.com/blog/2161734
 */  
public class EhCacheManager {  
    private static Log log = LogFactory.getLog(EhCacheManager.class);
    //一个manager的名称
    private static final String CACHE_KEY = "serviceCache";
    //可配置生存期
    public static final int CACHE_LIVE_SECONDS = 180;
    //本方法的获取方式：单例
    private static EhCacheManager instance = new EhCacheManager();
    //缓存管理类
    private static CacheManager cacheManager;
    //一个缓存对象
    private static Ehcache fileCache;  
  
    private EhCacheManager() {
        log.info("Init file cache ----------------------------------------");  
        
        /**
         * CacheManager对象的创建方式
         * 有四种方式获取
         */
        //1、无参 说明：Ehcache在启动的时候会扫描classes目录下的ehcache.xml配置文件，创建CacheManager对象，如果将ehcache.xml文件放到classes目录下，可以通过无参形式加载配置文件；
//        cacheManager = new CacheManager();  
        //2、通过配置文件 说明：如果没有将配置文件放在classes目录下，则在ehcache启动的时候找不到配置文件，没有创建CacheManager对象，所以在加载配置文件的时候需要通过路径来加载配置文件；
        cacheManager = new CacheManager("D:\\work\\eclipse\\workspace-jee\\tech14Ehcache\\src\\e1common\\\\ehcache.xml");
        //3、通过资源
        URL url = getClass().getResource("/ehcache.xml");
        cacheManager = new CacheManager(url);
        //4、通过输入流
        try{
	        InputStream fis = new FileInputStream(new File("src\\e1common\\ehcache.xml").getAbsolutePath());
	        cacheManager = new CacheManager(fis);
			fis.close();
        }catch(Exception e){}
        
		//创建一个Cache
        fileCache = cacheManager.getCache(CACHE_KEY);
        
//        if(fileCache == null ){
//        	cacheManager.add(CACHE_KEY);
//        	fileCache = cacheManager.getCache(CACHE_KEY);
//        }
        log.info("Init file cache success....");  
    }  
  
    public static synchronized EhCacheManager getInstance() {  
        if (instance == null) {  
            instance = new EhCacheManager();  
        }  
        return instance;  
    }  
  
    public static byte[] loadFile(String key) {  
        Element e = fileCache.get(key);  
        if (e != null) {  
            Serializable s = e.getValue();  
            if (s != null) {  
                return (byte[]) s;  
            }  
        }  
        return null;  
    }  
  
    public static void cacheFile(String key, byte[] data) {  
        fileCache.put(new Element(key, data));  
    }  
      
    /** 
     * 将数据存入缓存，缓存无时间限制 
     * @param key 
     * @param value 
     */  
    public static  <T> void put(String key,T value){  
        fileCache.put(new Element(key, value));  
    }  
      
    /** 
     * 带过期时间的缓存，存入 
     * @param key  键 
     * @param value 值 
     * @param timeToLiveSeconds 缓存过期时间 
     */  
    public static  <T> void put(String key,T value,int timeToLiveSeconds){  
//        fileCache.put(new Element(key, value,0,timeToLiveSeconds));
        fileCache.put(new Element(key, value,null, 0,timeToLiveSeconds)); 
    }  
      
      
    /** 
     * 通过key值获取存入缓存中的数据 
     * @param key   数据存入缓存时的key 
     */  
    @SuppressWarnings("unchecked")  
    public static <T> T get(String key) {  
        Element el = fileCache.get(key);  
        if (el == null) {  
            if (log.isDebugEnabled())  
                log.debug("not found key: " + key);  
            return null;  
        }  
        
        T t = (T) el.getObjectValue();  
        return t;  
    }  
  
      
    /** 
     * 根据key删除缓存 
     */  
    public static boolean  remove(String key) {  
        log.info("remove key:"+key);  
        return fileCache.remove(key);  
    }  
    
    /**
     * 关闭cacheManager 对象
     */
    public static void shutdown() {  
        cacheManager.shutdown();  
    }  
    
    public static void main(String[] args) {
		
	}
}
