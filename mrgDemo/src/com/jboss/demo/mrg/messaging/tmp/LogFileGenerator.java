package com.jboss.demo.mrg.messaging.tmp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class LogFileGenerator extends Thread {
	
	private FileWriter writer;
	private File file;
	
	public LogFileGenerator() {
		this.file = new File("/tmp/myfile_" + new Random().nextInt(1000) + ".log");
		this.file.deleteOnExit();
		try {
	    	writer = new FileWriter(file);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public File getFile() {
		return file;
	}
	
	public void log() {
		try {
			Random r = new Random();
			int i = r.nextInt(1000);
			while (i < 900) {
				i = r.nextInt(1000);
			}
			
			String log = String.valueOf(i)+"\n";
	    	writer.write(log);
	    	writer.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void run() {
		while (true) {
			log();
			try {
			    Thread.sleep(1000);
			} catch (InterruptedException ie) {}
		}
	}
}
