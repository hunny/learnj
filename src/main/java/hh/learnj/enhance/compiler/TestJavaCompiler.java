package hh.learnj.enhance.compiler;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Properties;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class TestJavaCompiler {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) throws Exception {
		String separator = System.getProperty("file.separator");
		String dir = System.getProperty("user.dir") + separator + "src/main/java/com/abc";
		FileReader fileReader = new FileReader(new File(dir + separator + "TestCompiler.tmp"));
		String fileName = dir + separator + "TestCompiler.java";
		File file = new File(fileName);
		FileWriter fileWriter = new FileWriter(file);
		int read = -1;
		while ((read = fileReader.read()) != -1) {
			fileWriter.write(read);
		}
		fileWriter.flush();
		fileWriter.close();
		fileReader.close();
		System.out.println(fileName);
		
		//compiler
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		System.out.println(compiler.getClass().getName());
		StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
		Iterable units = manager.getJavaFileObjects(new File(fileName));
		CompilationTask task = compiler.getTask(null, manager, null, null, null, units);
		task.call();
		manager.close();
		
		//after compiler file then delete file
		file.delete();//delete source file
		
		//load class
		File mFile = new File(System.getProperty("user.dir") + separator + "src/main/java");
		URL clazzUrl = mFile.toURI().toURL();
		System.out.println(clazzUrl);
		URL[] urls = new URL[] {clazzUrl};
		URLClassLoader loader = new URLClassLoader(urls);
		Class clazz = loader.loadClass("com.abc.TestCompiler");
		System.out.println(clazz);
		loader.close();
		
		Method method = clazz.getMethod("getAllProperties", null);
		method.invoke(null, null);
		Object object = clazz.newInstance();
		Method hello = clazz.getMethod("helloWorld", String.class);
		hello.invoke(object, ", HAHA! This is a test.");
	}
	
	public static void getAllProperties() {
		System.out.println("[-][-][-][-][-][-][-][-][-][-][-][-][-][-][-][-][-][-][-][-]");
		Properties props = System.getProperties();
		Enumeration<Object> keys = props.keys();
		while(keys.hasMoreElements()) {
			String key = (String)keys.nextElement();
			System.out.println(key + "[+]=[+]" + System.getProperty(key));
		}
	}
	
	public void allProperties() {
		System.out.println("[+][+][+][+][+][+][+][+][+][+][+][+][+][+][+][+][+][+][+][+]");
		getAllProperties();
	}

}
