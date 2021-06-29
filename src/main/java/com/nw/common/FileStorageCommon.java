package com.nw.common;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

import com.nw.model.Movie;

public class FileStorageCommon {
	
	public static void fileStorage(MultipartFile file, int id, Object o) throws IOException {
		String uploadDir = "";
		String fileName = file.getOriginalFilename();
		if (o instanceof Movie) {
			uploadDir = "src/main/resources/static/images/movie/" + id;
		} else {
			uploadDir = "src/main/resources/static/images/actor/" + id;
		}
		Path uploadPath = Paths.get(uploadDir);
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		try(InputStream inputStream = file.getInputStream()) {
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO: handle exception
			throw new IOException("Could not save image file: " + fileName, e);
		}
	}

}
