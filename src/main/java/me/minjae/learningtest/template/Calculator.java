package me.minjae.learningtest.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
	
	public Integer fileReadTemplate(String filepath, BufferedReaderCallback callback) throws IOException {
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(filepath));
			int ret = callback.doSometingWithReader(br);
			return ret;
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			throw e;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO: handle exception
					System.out.println(e.getMessage());
				}
			}
		}
	}
	
	public <T> T lineReadTemplate (String filepath, LineCallback<T> callback, T initVal) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filepath));
			T res = initVal;
			String line = null;
			while((line = br.readLine()) != null) {
				res = callback.doSomethingWithLine(line, res);
			}
			return res;
		} catch (IOException e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
					// TODO: handle exception
				}
			}
		}
	}
	
	public String concatenate(String filepath) throws IOException {
		LineCallback<String> concatenateCallback = 
				new LineCallback<String>() {
					public String doSomethingWithLine(String line, String value) {
						// TODO Auto-generated method stub
						return value + line;
					}
				};
				return lineReadTemplate(filepath, concatenateCallback, "");
	}
	
	public Integer calcSum(String filepath) throws IOException {
		LineCallback<Integer> sumCallback = new LineCallback<Integer>() {
			
			public Integer doSomethingWithLine(String line, Integer value) {
				// TODO Auto-generated method stub
				return value += Integer.valueOf(line);
			}
		};
		return lineReadTemplate(filepath, sumCallback, 0);
	}
	
	public Integer calcMul(String filepath) throws IOException {
		LineCallback<Integer> mulCallback = new LineCallback<Integer>() {
			public Integer doSomethingWithLine(String line, Integer value) {
				// TODO Auto-generated method stub
				return value *= Integer.valueOf(line);
			}
		};
		return lineReadTemplate(filepath, mulCallback, 1);
	}
}
