package e1common.temp;
///**   
// * Copyright © 2016 北京恒泰实达科技发展有限公司. All rights reserved.
// * 项目名称：tech14Ehcache
// * 描述信息: 
// * 创建日期：2016年1月27日 下午5:39:36 
// * @author malitao
// * @version 
// */
//package e1common;
//
///** 
// *  
// * 创建日期：2016年1月27日 下午5:39:36 
// * @author malitao
// */
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import com.dao.UserDao;
//import com.entity.User;
//import com.service.UserService;
//import com.util.EhCacheManager;
//
///**
// * 用户管理service实现类
// */
//@Service(value="userService")
//public class UserServiceImpl implements UserService {
//	@Autowired
//	private UserDao userDao;
//	
//	private final static String GET_USER_KEY = "GET_USER_KEY_";  
//	
//	/**
//	 * 增加或修改用户
//	 */
//	public int userOperate(User user){
//		if(user.getId() == null){
//			return userDao.addUser(user);
//		}else{
//			return userDao.updateUser(user);
//		}
//	}
//	
//	/**
//	 * 删除用户
//	 */
//	public int deleteUser(String id) {
//		Map<String,Object> params = new HashMap<String, Object>();
//		params.put("id",id);
//		
//		//根据id查询用户
//		List<User> listUser = userDao.getUser(params);
//		
//		//删除之前登录保存的缓存
//		if(listUser.size() > 0){
//			User user = listUser.get(0);
//			EhCacheManager.remove(GET_USER_KEY+user.getAccount()+"_"+user.getPassword());
//		}
//		
//		return userDao.deleteUser(id);
//	}
//	
//	/**
//	 * 用户登录，从缓存中取数据，如果没有就查数据库
//	 */
//	public List<User> getUserDenglu(Map<String,Object> params) {
//		//到缓存中取数据
//		List<User> listUser = EhCacheManager.get(GET_USER_KEY+params.get("account")+"_"+params.get("password"));  
//		
//		//如果没有则去数据库查询
//        if(listUser == null){  
//            listUser =  userDao.getUserDenglu(params);  
//            //将取到的数据保存到缓存中
//            EhCacheManager.put(GET_USER_KEY+params.get("account")+"_"+params.get("password"), listUser);  
//        }  
//        
//        return listUser;
//	}
//	
//	/**
//	 * 查询用户
//	 */
//	public List<User> getUser(Map<String,Object> params){
//		return userDao.getUser(params);
//	}
//	
//	/**
//	 * 查询用户总数
//	 */
//	public int getUserCount(Map<String,Object> params){
//		return userDao.getUserCount(params);
//	}
//}
