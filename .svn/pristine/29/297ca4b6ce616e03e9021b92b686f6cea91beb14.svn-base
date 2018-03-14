package com.qinmei.test;

import java.io.File;
import java.io.IOException;

public class SRCCheck {

	public static void main(String[] args){
		 /*二、获取当前文件夹
		 *   这是获取相对路径的方法（常用），便于程序在不同的计算机OS中迁移
		 *   获得当前工程所在文件夹，如本例中是D:\workspaceOfJavaEclipse\javaTest
		 */
	         File root2=new File("..");//获得当前文件夹（即工程目录），结果D:\workspaceOfJavaEclipse\javaTest
	         //File root2=new File("..");//获得当前文件夹的父文件夹，结果D:\workspaceOfJavaEclipse
	         try {
	             String rootDir2=root2.getCanonicalPath();
	             System.out.println("当前工程所在文件夹："+rootDir2);
	         } catch (IOException e) {
	             // TODO Auto-generated catch block
	             e.printStackTrace();
	         }
		         
		     }//end main
}
