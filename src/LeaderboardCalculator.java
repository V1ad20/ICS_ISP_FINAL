import java.util.ArrayList;

public class LeaderboardCalculator {

    ArrayList<String> names;
    ArrayList<Long> recordedTimes;

    ArrayList<String> top10Names;
    ArrayList<Long> top10Times;

    public LeaderboardCalculator(){
        names = new ArrayList<String>();
        recordedTimes = new ArrayList<Long>();
        top10Names = new ArrayList<String>();
        top10Times = new ArrayList<Long>();
    }

    public void addEntry(String name, Long time){
        names.add(name);
        recordedTimes.add(time);
    } 

    public void calculate(){
        QuickSort sorter = new QuickSort(names, recordedTimes);
        sorter.sort();
        getTop10(sorter.names, sorter.recordedTimes);

    }

    public void getTop10(String[] arr1, Long[] arr2){
        for(int i = 0; i < 10; i++){
            top10Names.add(arr1[i]);
        }

        for(int a = 0; a < 10; a++){
            top10Times.add(arr2[a]);
        }
    }

    public ArrayList<String> getTop10Names(){
        return top10Names;
    }

    public ArrayList<Long> getTop10Times(){
        return top10Times;
    }
    
}
