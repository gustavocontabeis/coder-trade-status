package br.com.codersistemas.codertradestatus.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileUtils {

	public static List<String[]> readFile(File file, String splitBy) {
		List<String[]> list = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(file.getAbsoluteFile()), "ISO-8859-1"));) {
			String line;
			int nrLine = 0;
			Map<String, Integer> map = null;
			while ((line = br.readLine()) != null) {
				String[] split = line.split(splitBy);
				list.add(split);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

}
