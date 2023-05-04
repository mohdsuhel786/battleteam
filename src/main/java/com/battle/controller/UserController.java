package com.battle.controller;

import com.battle.auth.AuthenticationResponse;
import com.battle.entities.User;
import com.battle.exception.BattleException;
import com.battle.payloads.FileResponse;
import com.battle.payloads.UserDto;
import com.battle.payloads.UserUpdateDto;
import com.battle.services.FileService;
import com.battle.services.UserService;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private FileService fileService;

	@Value("${project.imageUser}")
	private String path;

	@PostConstruct
	public void init() {
		try {
			Files.createDirectories(Paths.get(path));
		} catch (IOException e) {
			throw new RuntimeException("Could not create upload folder!");
		}
	}


	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto user) throws BattleException {
		AuthenticationResponse registerUser = userService.registerUser(user);
		//  return ResponseEntity.created(URI.create("/users/" + registerUser.getToken())).build();
		return ResponseEntity.ok(registerUser);
	}


	@GetMapping("/{email}")
	public ResponseEntity<UserUpdateDto> getUserByMail(@PathVariable String email) throws BattleException {
		UserUpdateDto user = userService.findUserByEmail(email);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping("")
	public ResponseEntity<List<UserUpdateDto>> getUsers() throws BattleException {
		List<UserUpdateDto> users = userService.getUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('USER') or hasRole('MODERATER') or hasRole('ADMIN')")
	@GetMapping("/user/{userId}")
	public ResponseEntity<UserUpdateDto> getUser(@PathVariable Long userId) throws BattleException {
		UserUpdateDto user = userService.getUserById(userId);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<String> deleteUserr(@PathVariable Long userId) throws BattleException {
		userService.deleteUserById(userId);
		return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
	}

	@PutMapping("/update/{userId}")
	public ResponseEntity<UserUpdateDto> getUser(@PathVariable Long userId, @Valid @RequestBody UserUpdateDto userDto) throws BattleException {
		UserUpdateDto user = userService.updateUser(userDto, userId);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PostMapping("/user/image/upload/{userId}")
	public ResponseEntity<FileResponse> fileUpLoad(
			@RequestParam("image") MultipartFile image,
			@PathVariable("userId") Long userId
	) throws BattleException {
		String fileName = null;
		UserUpdateDto user = this.userService.getUserById(userId);

		try {
			fileName = this.fileService.uploadImage(image, path);
			user.setUserImageName(fileName);
			UserUpdateDto updateUser = this.userService.updateUser(user, userId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(new FileResponse(null, "image is not uploaded due to error on server!"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(new FileResponse(fileName, "image is successfully uploaded!"), HttpStatus.OK);
	}


	//method to serve file
	@GetMapping(value = "/user/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void serveImage(
			@PathVariable("imageName") String imageName,
			HttpServletResponse response) throws IOException {
		InputStream resourceImage = this.fileService.getImage(imageName, path);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resourceImage, response.getOutputStream());
	}
}
