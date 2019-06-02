package com.pmt.videoscripting.utils;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileUploadUtil {
	
	public static  List<Path> listSourceFiles(Path dir,String glob) throws IOException {
		List<Path> result = new ArrayList<>();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, glob)) {
			for (Path entry : stream) {
				result.add(entry);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

}
