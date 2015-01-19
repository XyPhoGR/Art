package com.ralitski.art.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Settings {
	
	private static final String SEPARATOR = ": ";
	private static final String PATH = "./settings.txt";
	
	private Map<String, String> values;
	
	public Settings() {
		values = new HashMap<>();
	}
	
	public Settings getSubSettings(String path) {
		return new SubSettings(this, path);
	}
	
	public String get(String key) {
		return values.get(key);
	}
	
	public String get(String key, String defaultValue) {
		String s = values.get(key);
		if(s == null) {
			values.put(key, s = defaultValue);
		}
		return s;
	}
	
	public String set(String key, String value) {
		return values.put(key, value);
	}
	
	public void save() {
		try {
			save(PATH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void save(String f) throws IOException {
		save(new File(f));
	}
	
	public void save(File f) throws IOException {
		if(!f.exists()) {
			f.createNewFile();
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter(f));
		for(Entry<String, String> e : values.entrySet()) {
			writer.write(e.getKey() + SEPARATOR + e.getValue());
			writer.newLine();
		}
		writer.close();
	}
	
	public void load() {
		try {
			load(PATH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void load(String f) throws IOException {
		load(new File(f));
	}
	
	public void load(File f) throws IOException {
		if(!f.exists()) {
			f.createNewFile();
			return;
		}
		BufferedReader reader = new BufferedReader(new FileReader(f));
		String line;
		while((line = reader.readLine()) != null) {
			String[] data = line.split(SEPARATOR, 2);
			if(data.length == 2) {
				values.put(data[0], data[1]);
			}
		}
		reader.close();
	}
}
