package org.zjh.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IoRWUtil {

	public IoRWUtil(BufferedReader br){
		this.br = br;
	}
	public IoRWUtil(BufferedWriter bw){
		this.bw = bw;
	}
	
	public BufferedReader br = null;
	public BufferedWriter bw = null;
	public String name = "";
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static IoRWUtil createRead(String path){
		try {
			File f = new File(path);
			BufferedReader br = new BufferedReader(new FileReader(f));
			IoRWUtil ir = new IoRWUtil(br);
			ir.name = f.getName();
			return ir;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static IoRWUtil createRead(InputStream io){
		try { 
			BufferedReader br = new BufferedReader(new InputStreamReader(io));
			IoRWUtil ir = new IoRWUtil(br);
//			ir.name = f.getName();
			return ir;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static IoRWUtil createWriter(String path){
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path)));
			IoRWUtil ir = new IoRWUtil(bw);
			return ir;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String [] getListFile(String path){
		File f = new File(path);
		return f.list();
	}
	
	public static File [] getFiles(String path){
		File f = new File(path);
		return f.listFiles();
	}
	
	public String readLine(){
		try {
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void write(String string){
		try {
			bw.write(string);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void newLine(){
		try {
			bw.newLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close(){
		try {
			if(br != null){
					br.close();
			}
			if(bw != null){
				bw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
