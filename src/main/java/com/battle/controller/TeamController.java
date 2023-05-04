package com.battle.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import com.battle.entities.Team;
import com.battle.entities.User;
import com.battle.exception.BattleException;
import com.battle.payloads.FileResponse;
import com.battle.payloads.TeamDto;
import com.battle.payloads.UserDto;
import com.battle.payloads.UserUpdateDto;
import com.battle.services.FileService;
import com.battle.services.TeamServices;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/teams")
@Validated
public class TeamController {

    @Autowired
    private TeamServices teamService;

    @Autowired
    private FileService fileService;

    @Value("${project.imageTeam}")
    private String path;

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload folder!");
        }
    }


    @GetMapping("")
    public List<TeamDto> getAllTeams() throws BattleException {
        List<TeamDto> allTeams = teamService.getAllTeams();
        return allTeams;

    }

    // @PreAuthorize("hasRole('USER')")
    @PostMapping("/createTeam/user/{userId}")
    public TeamDto createTeam(@Valid @PathVariable("userId") Long userId, @RequestParam String teamName, @RequestParam String inGameName) throws BattleException {

        return teamService.createTeam(userId, teamName, inGameName);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{teamId}/user/{userId}")
    public void deleteTeam(@PathVariable("teamId") Long teamId, @PathVariable("userId") Long userId) throws BattleException {
        teamService.deleteTeam(teamId, userId);
    }

    @GetMapping("/{teamId}")
    public TeamDto findTeam(@Valid @PathVariable("teamId") Long teamId) throws BattleException {
        TeamDto team = teamService.getTeamById(teamId);
        return team;
    }

    //   @PreAuthorize("hasRole('USER')")
    @PostMapping("/{teamId}/invite")
    public String inviteMember(@Valid @PathVariable("teamId") Long teamId, @RequestParam String email, HttpServletRequest request) throws BattleException {
        String invitationalCode = teamService.inviteMember(teamId, email, request);
        return invitationalCode;
    }

    //(@RequestBody inGame name)
    @PostMapping("/{teamId}/{userId}")

    public ResponseEntity<?> acceptInvitation(@PathVariable("userId") Long userId, @Valid @RequestParam("invitationToken") String invitationToken, @PathVariable("teamId") Long teamId, @Valid @RequestParam String inGameName, HttpServletRequest request, HttpServletResponse response) throws BattleException {
        teamService.acceptInvitation(userId, teamId, invitationToken, inGameName);
        return new ResponseEntity<String>("Team Join Successfully", HttpStatus.ACCEPTED);
    }

    @GetMapping("/{teamId}/invite")
    public String getInvitation(@PathVariable("teamId") Long teamId, @Valid @RequestParam(value = "invitationToken", required = false) String invitationToken, Model map) throws BattleException {
        //teamService.acceptInvitation(userId, teamId, invitationToken,inGameName);
        map.addAttribute("msg", "teams" + "employee requested by invitationToken: " + invitationToken);
//    	List<TeamMember> teamMember = teamMemberList.getTeamMember();
//    	for(User x:user) {
//    		if(x.getTeamName().equals(teamId.trim())&& x.getInvitationToken().equals(invittionToken.trim()))
//    		return "success";
//    	}

        return invitationToken;
    }

    @PutMapping("/{teamId}/user/{oldCaptainUserId}/member/{teamMemberId}")
    public TeamDto changeNewCaptain(@PathVariable Long teamId, @PathVariable Long oldCaptainUserId, @PathVariable Long teamMemberId) throws BattleException {
        TeamDto changeCaptain = teamService.changeCaptain(teamId, oldCaptainUserId, teamMemberId);
        return changeCaptain;

    }

    @PutMapping("/updateTeam/{teamId}/user/{userId}/member/{teamMemberId}")
    public ResponseEntity<TeamDto> updateTeamName(@PathVariable Long teamId, @PathVariable Long userId, @PathVariable Long teamMemberId, @RequestParam String teamName) throws BattleException {
        TeamDto updatedTeam = teamService.updateTeam(teamId, userId, teamMemberId, teamName);
        return new ResponseEntity<>(updatedTeam, HttpStatus.CREATED);
    }

    @PostMapping("/team/image/upload/{teamId}/{userId}")
    public ResponseEntity<FileResponse> fileUpLoad(
            @RequestParam("image") MultipartFile image,
            @PathVariable("teamId") Long teamId,
            @PathVariable("userId") Long userId
    ) throws BattleException {
        String fileName = null;
        TeamDto team = this.teamService.getTeamById(teamId);

        try {
            fileName = this.fileService.uploadImage(image, path);
            team.setTeamImageName(fileName);
            TeamDto updateUser = this.teamService.updateTeamPic(team, teamId, userId);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return new ResponseEntity<>(new FileResponse(null, "image is not uploaded due to error on server!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new FileResponse(fileName, "image is successfully uploaded!"), HttpStatus.OK);
    }


    //method to serve file
    @GetMapping(value = "/team/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void serveImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response) throws IOException {
        InputStream resourceImage = this.fileService.getImage(imageName, path);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resourceImage, response.getOutputStream());
    }
}


