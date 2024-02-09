package com.battle.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.battle.entities.Team;
import com.battle.payloads.TeamDto;

public class TournamentUtils {
    public static Map<Integer, List<Team>> divideTeamsIntoGroups(List<Team> teams, int groupSize) {
        Map<Integer, List<Team>> groups = new HashMap<>();
        int groupCount = (int) Math.ceil((double) teams.size() / groupSize);
        for (int i = 0; i < groupCount; i++) {
            int startIndex = i * groupSize;
            int endIndex = Math.min(startIndex + groupSize, teams.size());
            List<Team> group = teams.subList(startIndex, endIndex);
            groups.put(i + 1, group);
        }
        return groups;
    }
}
