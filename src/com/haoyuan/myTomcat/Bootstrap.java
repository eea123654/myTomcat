package com.haoyuan.myTomcat;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NetUtil;
import cn.hutool.core.util.StrUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.haoyuan.myTomcat.http.Request;
import com.haoyuan.myTomcat.http.Response;
import com.haoyuan.myTomcat.util.Constant;

public class Bootstrap {
	//接收传入的参数并返回
	 public static void main(String[] args) {
		 
	        try {
	            int port = 18080;
	 
	            if(!NetUtil.isUsableLocalPort(port)) {
	                System.out.println(port +"端口已经被占用");
	                return;
	            }
	            ServerSocket ss = new ServerSocket(port);

	            while(true) {
	            	 while(true) {
	                     Socket s =  ss.accept();
	                     Request request = new Request(s);
	                     System.out.println("浏览器的输入信息： \r\n" + request.getRequestString());
	                     System.out.println("uri:" + request.getUri());
	      
	                     Response response = new Response();
	                     String html = "Hello DIY Tomcat from how2j.cn";
	                     response.getWriter().println(html);
	      
	                     handle200(s, response);
	                 }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 
	    }
	 
	 private static void handle200(Socket s, Response response) throws IOException {
	        String contentType = response.getContentType();
	        String headText = Constant.response_head_202;
	        headText = StrUtil.format(headText, contentType);
	        byte[] head = headText.getBytes();
	 
	        byte[] body = response.getBody();
	 
	        byte[] responseBytes = new byte[head.length + body.length];
	        ArrayUtil.copy(head, 0, responseBytes, 0, head.length);
	        ArrayUtil.copy(body, 0, responseBytes, head.length, body.length);
	 
	        OutputStream os = s.getOutputStream();
	        os.write(responseBytes);
	        s.close();
	    }
	 
}
