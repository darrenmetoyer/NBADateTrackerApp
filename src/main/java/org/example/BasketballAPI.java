package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;


public class BasketballAPI {

    public static HashMap<String, HashMap<String, HashMap<String, Object>>> matchesStringToMap(ArrayList<String> matches) {
        HashMap<String, HashMap<String, HashMap<String, Object>>> gamesMap = new HashMap<>();
        int count = 1;
        for (String game : matches) {
            HashMap<String, HashMap<String, Object>> teamsMap = new HashMap<>();
            String[] individualTeams = game.split("\"homeScore\"");
            for (int i = 0; i < individualTeams.length; i++) {
                String[] teamStats = individualTeams[i].split(",");
                HashMap<String, Object> statsMap = new HashMap<>();
                for (int j = 0; j < teamStats.length; j++) {
                    if (teamStats[j].contains("display")) {
                        String[] statSplit = teamStats[j].split(":");
//                        statsMap.put("Score", );
                        System.out.println(statsMap);
                    } else if (teamStats[j].contains("id") && teamStats[j].length() == 9) {
                        String[] statSplit = teamStats[j].split(":");
                        statsMap.put("Team id", Integer.parseInt(statSplit[1]));
                    } else if (teamStats[j].contains("\"name\":") && teamStats[j + 1].contains("\"nameCode\":")) {
                        String[] statSplit = teamStats[j].split(":");
                        String teamName = statSplit[1].substring(1, statSplit[1].length() - 1);
                        statsMap.put("Name", teamName);
                    } else if (teamStats[j].contains("shortName")) {
                        String[] statSplit = teamStats[j].split(":");
                        String teamName = statSplit[1].substring(1, statSplit[1].length() - 1);
                        statsMap.put("Short Name", teamName);
                    }
                }
                if (i == 0) {
                    teamsMap.put("Away Team", statsMap);
                } else {
                    teamsMap.put("Home Team", statsMap);
                }
                gamesMap.put("Game " + count, teamsMap);
            }
            count++;
        }
//        System.out.println(gamesMap.get("Away Team"));
        return gamesMap;
    }

//    public static void JSONStringToMap () {
//        ObjectMapper mapper = new ObjectMapper();
//        String json = "{\"name\":\"mkyong\", \"age\":\"37\"}";
//
//        try {
//
//            // convert JSON string to Map
//            Map<String, String> map = mapper.readValue(json, Map.class);
//
//            // it works
//            //Map<String, String> map = mapper.readValue(json, new TypeReference<Map<String, String>>() {});
//
//            System.out.println(map);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    public static String getData(String url)  throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("X-RapidAPI-Key", // API Key --> "")
                .header("X-RapidAPI-Host", "basketapi1.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }


    public static ArrayList<String> nbaMatchesFilter(String matches) {
        ArrayList<String> result = new ArrayList<>();
        String connection = "},\\{";
        String[] matchesSplit = matches.split(connection);
        for (String s : matchesSplit) {
            if (s.contains("\"id\":132")) {
                result.add(s);
            }
        }
        return result;
    }


    public static String matchesOnGivenDate(String gameDate)  throws IOException, InterruptedException {
        String[] dateParts = gameDate.split("/");
        String url = "https://basketapi1.p.rapidapi.com/api/basketball/matches/" + dateParts[1] + "/" + dateParts[0] + "/" + dateParts[2];
        return BasketballAPI.getData(url);
    }


//    public static String teamFilter(ArrayList<String> listOfGames, String teamName) {
//        for (int i = 0; i < listOfGames.size(); i++) {
//            if (listOfGames.get(i).contains(teamName)) {
//                return listOfGames.get(i);
//            }
//        }
//        return "";
//    }


//    public static String getGameCode(String game) {
//        String result = "";
//        int codeIndex = game.indexOf("userCount\":0},\"id\":") + 19;
//        while (Character.isDigit(game.charAt(codeIndex))) {
//            result += game.charAt(codeIndex);
//            codeIndex++;
//        }
//        return result;
//    }


//    public static String getMatchStats(String gameCode)  throws IOException, InterruptedException {
//        String url = "https://basketapi1.p.rapidapi.com/api/basketball/match/" + gameCode + "/lineups";
//        return BasketballAPI.getData(url);
//    }


//    public static void playerPerformance(String playerStats) {
//        String[] playersSeparated = playerStats.split("},\\{");
//        for (int i = 0; i < playersSeparated.length; i++) {
//            System.out.println(playersSeparated[i] + "\n");
//        }
//    }


}
