package com.yd.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author Administrator
 * 获取资源文件工具类
 */
public class PropertiesUtils {
	private final Logger logger =  Logger.getLogger(PropertiesUtils.class);
	//private final String CONFIG_PATH = "constants_path";
	private Properties property;
	
	/**
	 * 
	 */
	public PropertiesUtils(){
		InputStream inputStream = null;
		try {
			inputStream = this.getClass().getClassLoader().getResourceAsStream("constants.properties");
			this.property = new Properties();
			property.load(inputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}finally{
			if(inputStream!=null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("关闭输入流失败!", new Exception("PropertiesUtil关闭输入流失败!"));
				}
			}
		}
	}
	
	/*
	public void initPropertiesUtils(ServletContext servletContext){
		
		String location = servletContext.getInitParameter(CONFIG_PATH);
		if(location == null){
			return;
		}
		
		InputStream inputStream = servletContext.getResourceAsStream(location);
		this.property = new Properties();
		try {
			property.load(inputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}finally{
			if(inputStream!=null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("关闭输入流失败!", new Exception("PropertiesUtil关闭输入流失败!"));
				}
			}
		}
	}
	*/
	
	/**
	 * 获取配置文件中的值
	 * @param propertyName 
	 * @return String
	 */
	public String getConfigString(String propertyName) {
		logger.info(propertyName+":"+ property.getProperty(propertyName));
		return property.getProperty(propertyName);
	}
	
	/**
	 * 获取配置文件中的值
	 * @param propertyName 
	 * @return int 
	 */
	public int getConfigInt(String propertyName){
		logger.info(propertyName+":"+ property.getProperty(propertyName));
		return Integer.valueOf(this.property.getProperty(propertyName));
	}
	
}
