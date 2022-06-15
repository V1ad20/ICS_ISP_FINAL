import java.util.ArrayList;

public class LeaderboardCalculator {

    ArrayList<String> names;
    ArrayList<Long> recordedTimes;

    public LeaderboardCalculator(){
        names = new ArrayList<String>();
        recordedTimes = new ArrayList<Long>();
    }

    public void addEntry(String name, Long time){
        names.add(name);
        recordedTimes.add(time);
    } 

    public void calculate(){
        
    }
    
}
