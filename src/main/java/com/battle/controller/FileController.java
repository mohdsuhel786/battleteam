package com.battle.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.battle.payloads.FileResponse;
import com.battle.services.FileService;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/files")
public class FileController {

	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	 @PostConstruct
	    public void init() {
	        try {
	            Files.createDirectories(Paths.get(path));
	        } catch (IOException e) {
	            throw new RuntimeException("Could not create upload folder!");
	        }
	    }
	
	@PostMapping("/upload")
	public ResponseEntity<FileResponse> fileUpLoad(
			@RequestParam("image") MultipartFile image
			){
		String fileName = null;
		try {
			fileName = this.fileService.uploadImage(image,path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(new FileResponse(null, "image is not uploaded due to error on server!"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(new FileResponse(fileName, "image is successfully uploaded!"),HttpStatus.OK);
	}
	
	
	//method to serve file
	@GetMapping(value = "/images/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE )
	public void serveImage(
			@PathVariable("imageName") String imageName,
			HttpServletResponse response) throws IOException
	{
		InputStream resourceImage = this.fileService.getImage(imageName,path);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resourceImage, response.getOutputStream());
	}
}
