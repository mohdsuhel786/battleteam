package com.battle.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.battle.exception.BattleException;
import com.battle.payloads.FileResponse;
import com.battle.payloads.GameDto;
import com.battle.payloads.TournamentDto;
import com.battle.services.FileService;
import com.battle.services.GameService;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/games")
@Validated
public class GameController {
	
	@Autowired
    private GameService gameService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.imageGame}")
	private String path;
	
	 @PostConstruct
	    public void init() {
	        try {
	            Files.createDirectories(Paths.get(path));
	        } catch (IOException e) {
	            throw new RuntimeException("Could not create upload folder!");
	        }
	    }

    @PostMapping("/addGame")
    public ResponseEntity<?> registerGame(@Valid @RequestBody GameDto game) throws BattleException {
        GameDto savedGame = gameService.registerGame(game);
        return ResponseEntity.created(URI.create("/games/" + savedGame.getGameId())).build();
    }



    @GetMapping("/allGames")
    public ResponseEntity<List<GameDto>> getGame() throws BattleException {
        List<GameDto> games = gameService.getGames();
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<GameDto> getGamer(@Valid @PathVariable Long gameId) throws BattleException {
        GameDto game = gameService.getGameById(gameId);
        return new ResponseEntity<>(game, HttpStatus.OK);
    }
    @PostMapping("/game/image/upload/{gameId}")
	public ResponseEntity<FileResponse> fileUpLoad(
			@RequestParam("image") MultipartFile image,
			@PathVariable("gameId") Long gameId
			) throws BattleException{
		String fileName = null;
		GameDto game = this.gameService.getGameById(gameId);
		
		try {
			fileName = this.fileService.uploadImage(image,path);
			game.setGameImageName(fileName);
			GameDto updateGame = this.gameService.updateGame(gameId,game);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(new FileResponse(null, "image is not uploaded due to error on server!"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(new FileResponse(fileName, "image is successfully uploaded!"),HttpStatus.OK);
	}
	
	
	//method to serve file
	@GetMapping(value = "/game/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE )
	public void serveImage(
			@PathVariable("imageName") String imageName,
			HttpServletResponse response) throws IOException
	{
		InputStream resourceImage = this.fileService.getImage(imageName,path);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resourceImage, response.getOutputStream());
	}
}
