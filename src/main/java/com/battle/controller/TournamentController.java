package com.battle.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.battle.entities.Team;
import com.battle.entities.TeamMember;
import com.battle.entities.Tournament;
import com.battle.entities.TournamentGroup;
import com.battle.exception.BattleException;
import com.battle.payloads.FileResponse;
import com.battle.payloads.TeamDto;
import com.battle.payloads.TeamMemberDto;
import com.battle.payloads.TournamentDto;
import com.battle.payloads.TournamentGroupDto;
import com.battle.payloads.UserDto;
import com.battle.payloads.UserUpdateDto;
import com.battle.repositories.TournamentRepository;
import com.battle.services.FileService;
import com.battle.services.TournamentGroupService;
import com.battle.services.TournamentService;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tournaments")
@Validated
public class TournamentController {
    
    @Autowired
    private  TournamentService tournamentService;
    
    @Autowired
    private  TournamentGroupService tournamentGroupService;
    
	@Autowired
	private FileService fileService;
	
	@Value("${project.imageTournament}")
	private String path;
	
	 @PostConstruct
	    public void init() {
	        try {
	            Files.createDirectories(Paths.get(path));
	        } catch (IOException e) {
	            throw new RuntimeException("Could not create upload folder!");
	        }
	    }
	
    

    @PostMapping("/createTournament/game/{gameId}")
    @ResponseBody
    public ResponseEntity<TournamentDto> createTournament(@Valid @RequestBody TournamentDto tournamentDto,@PathVariable("gameId") Long gameId) throws BattleException {
   
    	TournamentDto savedTournament = tournamentService.createTournament(tournamentDto, gameId);
        return ResponseEntity.created(URI.create("/tournaments/" + savedTournament.getTournamentId())).body(savedTournament);
    }
    
    @GetMapping("")
    public List<TournamentDto> getAllTournaments() throws BattleException {
        List<TournamentDto> allTeams = tournamentService.getAllTeams();
        return allTeams;
        		
    }
    @GetMapping("/{tournamentId}")
    public TournamentDto getTournament(@PathVariable("tournamentId") Long tournamentId) throws BattleException{
    	TournamentDto tournament = tournamentService.getTournament(tournamentId);
        return tournament;
    }
    
    @PutMapping("/{tournamentId}/update")
    public TournamentDto updateTournament(@PathVariable("tournamentId") Long tournamentId,@Valid @RequestBody TournamentDto tournamentDto) throws BattleException{
    	TournamentDto tournament = tournamentService.updateTournament(tournamentId, tournamentDto);
        return tournament;
    }
    
    @PostMapping("/{tournamentId}/teams/{teamId}")
    public ResponseEntity<?> selectTeamMembersAndRegisterTournament(@PathVariable Long tournamentId, @PathVariable Long teamId,@Valid @RequestBody List<TeamMemberDto> teamMembers) throws BattleException {
     TeamDto teamRegister = tournamentService.selectTeamMembersAndRegisterTournament(tournamentId, teamId, teamMembers);
    
     return ResponseEntity.created(URI.create("/tournaments/" + teamRegister.getTeamId())).body(teamRegister);
    }
    @GetMapping("/upcoming")
    public List<TournamentDto> getUpcomingTournaments() throws BattleException {
        // Retrieve upcoming tournaments from database
        List<TournamentDto> upcomingTournaments = tournamentService.getUpcomingTournaments();
        // Convert Tournament objects to TournamentDTO objects
        return upcomingTournaments;
      }
    @GetMapping("/game/{gameId}/upcoming")
    public List<TournamentDto> getUpcomingGameTournaments(@PathVariable Long gameId) throws BattleException {
        // Retrieve upcoming tournaments from database
        List<TournamentDto> upcomingTournaments = tournamentService.getUpcomingGameTournaments(gameId);
        // Convert Tournament objects to TournamentDTO objects
        return upcomingTournaments;
      }
//      
    @GetMapping("/live")
      public List<TournamentDto> getLiveTournaments() throws BattleException {
        // Retrieve live tournaments from database
        List<TournamentDto> liveTournaments = tournamentService.getLiveTournaments();
        // Convert Tournament objects to TournamentDTO objects
       // return liveTournaments.stream().map(this::mapTournamentToDTO).collect(Collectors.toList());
        return liveTournaments;
      }

    @GetMapping("/completed")
    public List<TournamentDto> getCompleteTournaments() throws BattleException {
      // Retrieve live tournaments from database
      List<TournamentDto> liveTournaments = tournamentService.getCompletesTournaments();
      // Convert Tournament objects to TournamentDTO objects
     // return liveTournaments.stream().map(this::mapTournamentToDTO).collect(Collectors.toList());
      return liveTournaments;
    }
    
    
    @GetMapping("/{tournamentId}/teams/{teamId}/team")
    public List<TeamMemberDto> getAllTeamMemberRegister(@PathVariable Long tournamentId,@PathVariable Long teamId) throws BattleException{
    	List<TeamMemberDto> teamMemberRegister = tournamentService.getTeamMemberRegister(tournamentId, teamId);
   
    	return teamMemberRegister;
    }
    @GetMapping("/teamMembers/{teamMemberId}")
    public List<TeamMemberDto> getTeamMemberRegister(@PathVariable Long teamMemberId) throws BattleException{
    	List<TeamMemberDto> teamMemberRegister = tournamentService.getTeamMember(teamMemberId);
   
    	return teamMemberRegister;
    }
    
    @PostMapping("/creategroups/{tournamentId}/{round}")
    public Map<Integer, List<TeamDto>> getGroupsForRound(@PathVariable int round,@PathVariable Long tournamentId,@RequestParam int groupSize) throws BattleException {
      //  int group = groupSize; // You can adjust this value according to your needs
        return tournamentGroupService.createGroupsForRound(tournamentId,round, groupSize);
    }

    @GetMapping("/teamGroups/{tournamentId}/{round}")
    public List<TournamentGroupDto> getGroupsTeam(@PathVariable int round,@PathVariable Long tournamentId) throws BattleException {
      //  int group = groupSize; // You can adjust this value according to your needs
        return tournamentGroupService.getTeams(tournamentId, round);
    }
    
    @GetMapping("/teamGroup/{tournamentId}/{round}")
    public ResponseEntity<List<TeamDto>>  getGroupsTeam(@PathVariable int round,@PathVariable Long tournamentId,@RequestParam int groupNo) throws BattleException {
      //  int group = groupSize; // You can adjust this value according to your needs
    	List<TeamDto> groupTeams = tournamentGroupService.getGroupTeams(tournamentId, round, groupNo);
        return new ResponseEntity<List<TeamDto>>(groupTeams,HttpStatus.OK);
    }
    
    @PostMapping("/tournament/image/upload/{tournamentId}")
	public ResponseEntity<FileResponse> fileUpLoad(
			@RequestParam("image") MultipartFile image,
			@PathVariable("tournamentId") Long tournamentId
			) throws BattleException{
		String fileName = null;
		TournamentDto tournament = this.tournamentService.getTournament(tournamentId);
		
		try {
			fileName = this.fileService.uploadImage(image,path);
			tournament.setTournamentImageName(fileName);
			TournamentDto updateTournament = this.tournamentService.updateTournament(tournamentId,tournament);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(new FileResponse(null, "image is not uploaded due to error on server!"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(new FileResponse(fileName, "image is successfully uploaded!"),HttpStatus.OK);
	}
	
	
	//method to serve file
	@GetMapping(value = "/tournament/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE )
	public void serveImage(
			@PathVariable("imageName") String imageName,
			HttpServletResponse response) throws IOException
	{
		InputStream resourceImage = this.fileService.getImage(imageName,path);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resourceImage, response.getOutputStream());
	}
    
}