public class Timekeeper {
    
    long checkTime;
    long endTime;

    public Timekeeper(){
    }   

    public void start(){
        checkTime = System.currentTimeMillis();
    }

    public long calculate(){
        endTime = System.currentTimeMillis();
        return endTime - checkTime;
    }

}
