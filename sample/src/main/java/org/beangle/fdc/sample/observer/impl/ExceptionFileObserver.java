package org.beangle.fdc.sample.observer.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.apache.commons.io.IOUtils;
import org.beangle.fdc.context.FDCContext;
import org.beangle.fdc.observer.ExceptionObserver;


public class ExceptionFileObserver implements ExceptionObserver{
	private static File getFile() throws IOException{
		String path = System.getProperty("java.io.tmpdir").replaceAll("\\\\", "/");
		if(path.endsWith("/")){
			path = path.substring(0,path.length()-1);
		}
		File dir = new File(path+"/log/");
		if(!dir.exists()){
			if(dir.mkdirs()){
				return null;
			}
		}
		
		File logFile = new File(dir.getAbsolutePath()+"/"+System.currentTimeMillis());
		if(!logFile.createNewFile()){
			return null;
		}
		return logFile;
	}
	
	public synchronized void doHandle(FDCContext context) throws Exception{
		Writer writer = new FileWriter(getFile());
		IOUtils.write(context.toString(), writer);
		writer.flush();
		IOUtils.closeQuietly(writer);
	}

	public void handle(FDCContext context) throws Exception {
		doHandle(context);
	}
}
