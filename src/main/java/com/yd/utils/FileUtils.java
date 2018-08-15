/******************************************************************************
 * Copyright (C) 2010-2020 Billions Finance. All Rights Reserved.
 * ============================================================================
 * 版权所有 2010-2020 佰仟金融服务有限公司，并保留所有权利
 * ----------------------------------------------------------------------------
 * 这不是一个自由软件！您不能在任何未经允许的前提下对程序代码进行修改和使用；不允许
 * 对程序代码以任何形式任何目的的再发布
 * ============================================================================
 *
 * @ProjectName collectsm
 * @PackageName com.billionsfinance.collectsm.common.utils 
 * @FileName FileUtils.java
 * @author Yanke Sun
 * @Date 2015-12-3 上午11:25:20
 *****************************************************************************/

package com.yd.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.billionsfinance.collectsm.common.constants.FileConstants;

/** 
 * @author Yanke Sun
 * @version 2015-12-3 上午11:25:20
 * @since JDK 1.6
 */
public class FileUtils {
	/**
	 * 记录日志对象
	 */
	private static final  Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);
	/**
	 * 
	 * @MethodName mkdirs <br/>
	 * @Description 创建目录,如果目录不存在，则创建目录；如果创建失败或存在同名字的文件或目录，则返回 false<br/>
	 * @author Yanke Sun
	 * @version 2015-12-3 下午12:25:23
	 *  
	 * @param dir 目录对象
	 * @return true:创建成功，false:创建失败
	 */
	public static boolean mkdirs(final File dir){
		//参数为空，则返回false
		if(dir == null){
			LOGGER.debug("创建目录失败，传入参数为null。");
			return false;
		}
		//目录不存在，则进行创建
		if(!dir.exists()){
			if(!dir.mkdirs()){
				LOGGER.error("创建目录("+dir.getAbsolutePath()+")失败!"); //创建失败
				return false;
			}else{
				LOGGER.debug("创建目录("+dir.getAbsolutePath()+")成功。"); //创建成功
				return true;
			}
		}else{
			//目录已经以文件或目录的方式存在
			if(!dir.isDirectory()){
				LOGGER.error("创建目录("+dir.getAbsolutePath()+")失败，存在相同名字的文件！");
			}else{
				LOGGER.error("创建目录("+dir.getAbsolutePath()+")失败，存在相同名字的目录！");
			}
			return false;
		}
	}
	/**
	 * 
	 * @MethodName generatePath <br/>
	 * @Description 生成文件路径 <br/>
	 * @author Yanke Sun
	 * @version 2015-12-3 下午1:46:13
	 * @param  fileType 
	 * @return 日期文件路径
	 */
	public static String generatePath(String fileType){
		//路径变量,默认值是根目录
		StringBuffer path = new StringBuffer(FileConstants.pathRoot+FileConstants.PATH_SPLIT_SIGN);
		//组装路径系统代码路径
		path.append(FileConstants.PATH_SYSTEM_CODE+FileConstants.PATH_SPLIT_SIGN);
		//组装文件类型路径
		path.append(fileType+FileConstants.PATH_SPLIT_SIGN);
		//组装日期路径
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
		String year = simpleDateFormat.format(date);
		path.append(year);
		path.append(FileConstants.PATH_SPLIT_SIGN);
		simpleDateFormat = new SimpleDateFormat("MM");
		String month = simpleDateFormat.format(date);
		path.append(month);
		path.append(FileConstants.PATH_SPLIT_SIGN);
		simpleDateFormat = new SimpleDateFormat("dd");
		String day = simpleDateFormat.format(date);
		path.append(day);
		path.append(FileConstants.PATH_SPLIT_SIGN);
		return path.toString();
	}
	/**
	 * 
	 * @MethodName fileType <br/>
	 * @Description 通过文件名，判断此文件的扩展名 <br/>
	 * @author Yanke Sun
	 * @version 2015-12-3 下午1:41:12
	 *  
	 * @param fileName 文件类型字符串
	 * @return String
	 */
	public static String fileType(String fileName){
		//传入参数为空，返回null
		if(fileName == null){
			LOGGER.debug("获取文件扩展名失败，传入参数为null。");
			return null;
		}
		//文件名不包含".",则把此文件归为其他文件类型
		if(!fileName.contains(".")){
			LOGGER.debug("此文件没有扩展名，归为其他文件类型。");
			return FileConstants.PATH_OTHER;
		}
		//返回此文件的扩展名
		String type = fileName.substring(fileName.lastIndexOf(".")+1);
		return type;
	}
	
//	public static String fileTypeDirectory(String fileType){
//		//传入参数为空，返回null
//		if(fileType == null){
//			logger.debug("获取文件扩展名失败，传入参数为null。");
//			return null;
//		}
//		return null;
//	}

}
