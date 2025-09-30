package Utility;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class dataprovider {
public Properties pro;
public dataprovider() {
	start();
}
public void start() {
	File src=new File("C:\\Users\\PC\\git\\repository2\\Configuration\\config.properties");
	
	try {
		FileInputStream fis=new FileInputStream(src);
		pro=new Properties();
		pro.load(fis);
		
	}
	catch(Exception e) {
		System.out.println(e.getMessage());
	}
	
}
public String geturl() {
	return pro.getProperty("url");
	
}
public String getbrowser() {
	return pro.getProperty("browser");
}
}
