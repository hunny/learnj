package hh.learnj.enhance.classloader;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MyClassLoader extends ClassLoader {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public void copyStream(InputStream in, OutputStream out) throws IOException {
		int b = -1; 
		while ((b = in.read()) != -1) {
			out.write(b);
		}
	}
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		try {
			InputStream stream = new FileInputStream(name);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			copyStream(stream, bos);
			byte[] arr = bos.toByteArray();
			return defineClass(null, arr, 0, arr.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.findClass(name);
	}

}
