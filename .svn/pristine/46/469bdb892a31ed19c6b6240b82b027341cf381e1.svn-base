package com.qinmei.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

@Controller
public class TestFileUpload {

	@RequestMapping("/upload")
	public String upload(@RequestParam(value = "img") MultipartFile img)
			throws ServletException, IOException {
		
		System.out.println(img);
		
		img.transferTo(new File("D:/t/a.jpg"));
		
		System.out.println("..");
		
		// 检测是否为多媒体上传
     /*   if (!ServletFileUpload.isMultipartContent(req)) {
            // 如果不是则停止
            PrintWriter writer = response.getWriter();
            writer.println("Error: 表单必须包含 enctype=multipart/form-data");
            writer.flush();
        }*/
		
		//演示使用fileUpload组件完成文件的上传
		//1、创建一个工厂类
		//DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		//2、创建一个用于处理上传数据的核心类
		//ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
		
		/*
		 * 3、解析request对象，它中保存了用户请求的所有数据
		 *   使用ServletFileUpload 解析request对象，得到的是一个List集合。
		 *   在上传文件的时候，可以携带其他的非文件数据，也可以一次上传多个文件数据。
		 *   把用户提交的所有数据分解成不同的FileItem对象。
		 */
	//	try {
			/*
			 * list中保存着当前上传的所有数据，
			 * 其中有的是真正的文件上传信息，有的是一些普通的表单数据
			 * 
			 * 这时需要遍历这个list集合，然后取出每个FileItem对象，
			 * 判断当前这个FileItem对象到底是文件组件（就可以读取数据），
			 * 非文件组件（获取普通表单中的数据）。
			 */
		//	List<FileItem> list = fileUpload.parseRequest(req);
			//遍历集合
		//	for (FileItem fileItem : list) {
				//获取到每一个独立的FileItem对象，需要判断它到底是什么组件
				//使用FileItem中的isFormField()  判断当前是否是普通表单
		//		if( fileItem.isFormField() ){
					//说明当前的FileItem是一个普通的表单
					//获取普通表单中的数据
		//			String name = fileItem.getFieldName();
		//			String value = fileItem.getString();
		//			System.out.println("普通表单中的name="+name+",value="+value);
		//		}else{
					//说明当前的FileItem是一个文件上传表单
					//获取文件名：
		//			String fileName = fileItem.getName();
					//获取输入流读取数据
		//			InputStream in = fileItem.getInputStream();
					//创建一个输出流把文件保存在服务器的本地硬盘上 
					//使用ServletContext 对象获取项目中的目录
		//			String path = "";
					//创建一个File对象
		//			File file = new File(path,fileName);
					//创建输出流
		//			FileOutputStream fos = new FileOutputStream(file);
					
		//			int ch = 0;
		//			while( (ch = in.read())!=-1 ){
		//				fos.write(ch);
		//			}
							
		//			in.close();
		//			fos.close();
		//			return "success";
		//		}
		//	}
		//	
		//} catch (FileUploadException e) {
		//	e.printStackTrace();
		//}
		return "";
	}
	
	@RequestMapping("/uploadtest")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        
        InputStream is = request.getInputStream();  
        StringBuffer sb = new StringBuffer();  
        int count = 0;  
        while(true){  
            int a = is.read();  
            sb.append((char)a);  
            if(a == '\r')  
                count++;  
            if(count==4){  
                is.read();  
                break;  
            }  
                  
        }  
        String title = sb.toString();  
        System.out.println(title);  
        String[] titles = title.split("\r\n");  
        String fileName = titles[1].split(";")[2].split("=")[1].replace("\"","");  
        System.out.println(fileName);  
        FileOutputStream os = new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\"+fileName));  
        byte[] ob = new byte[1024];  
        int length = 0;  
        while((length = is.read(ob, 0, ob.length))>0){  
            os.write(ob,0,length);  
            os.flush();  
        }  
        os.close();  
        is.close();  
    }  
}
