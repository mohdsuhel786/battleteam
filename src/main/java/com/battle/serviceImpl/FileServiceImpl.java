package com.battle.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.battle.services.FileService;

@Service
public class FileServiceImpl implements FileService {

//	@Value("${project.image}")
//	private String path;
//	
//	 @PostConstruct
//	    public void init() {
//	        try {
//	            Files.createDirectories(Paths.get(path));
//	        } catch (IOException e) {
//	            throw new RuntimeException("Could not create upload folder!");
//	        }
//	    }
	
	
	@Override
	public String uploadImage(MultipartFile file,String path) throws IOException {
		
		
		//File Name
		String name = file.getOriginalFilename();
		
		String randomID = UUID.randomUUID().toString();
		String fileName1 = randomID.concat(name.substring(name.lastIndexOf(".")));
		//FullPath
		String filePath = path + File.separator + fileName1;
		

		//create folder if not created
		File f = new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		//file copy
		Files.copy(file.getInputStream(), Paths.get(filePath));
		return fileName1;
	}


	@Override
	public InputStream getImage(String fileName,String path) throws FileNotFoundException {
		String fullPath = path+File.separator+fileName;
		FileInputStream fileInputStream = new FileInputStream(fullPath);
		//db logic to return inputstream
		return fileInputStream;
	}

}
