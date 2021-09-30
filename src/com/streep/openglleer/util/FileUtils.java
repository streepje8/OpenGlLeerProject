package com.streep.openglleer.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {

	public static String loadFile(String path) {
		try {
			return new String(Files.readAllBytes(Paths.get(path)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
