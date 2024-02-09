package com.battle.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

//	void init();
	String uploadImage(MultipartFile file,String path) throws IOException;
	InputStream getImage(String fileName,String path) throws FileNotFoundException; 
}
