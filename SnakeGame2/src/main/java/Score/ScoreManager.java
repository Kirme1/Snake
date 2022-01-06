package Score;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ScoreManager {

    // This method reads all the scores from the Json-file to an arraylist
    public ArrayList<Long> readScores(){

        JSONParser jsonParser = new JSONParser();
        ArrayList<Long> scores = new ArrayList<>();

        try (FileReader fileReader = new FileReader("SnakeGame2/src/main/resources/scores.Json")){
            Object object = jsonParser.parse(fileReader);
            JSONArray scoreBoard = (JSONArray) object;
            for (int i = 0; i < scoreBoard.size(); i++){
                JSONObject obj = (JSONObject) scoreBoard.get(i);
                Long score = (long) obj.get("score");
                scores.add(score);
            }
        } catch (IOException | ParseException e){
            e.printStackTrace();
        }
        return scores;
    }

    // This method adds your score to the Json-file
    public void addScore(Integer score){
        JSONParser jsonParser = new JSONParser();
        JSONObject newScore = new JSONObject();
        newScore.put("score", score);

        try (FileReader fileReader = new FileReader("SnakeGame2/src/main/resources/scores.Json")) {
            Object object = jsonParser.parse(fileReader);
            JSONArray scoreBoard = (JSONArray) object;
            JSONArray newScoreBoard = topScores(scoreBoard, newScore);

            FileWriter file = new FileWriter("SnakeGame2/src/main/resources/scores.Json");
            file.write(newScoreBoard.toJSONString());
            file.flush();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    // This method sorts out the five highest scores
    private JSONArray topScores(JSONArray scores, JSONObject newScore){
        for (int i = 0; i < 5; i++){
            JSONObject object = (JSONObject) scores.get(i);
            if((Integer) newScore.get("score") > (Long) object.get("score")){
                scores.add(i, newScore);
                scores.remove(5);
                break;
            }
        }
        return scores;
    }
}
