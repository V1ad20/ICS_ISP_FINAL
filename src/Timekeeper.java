public class Timekeeper {

    long checkTime;
    long endTime;

    public Timekeeper() {
    }

    public void start() {
        checkTime = System.currentTimeMillis();
    }

    /**
     * @return long
     */
    public long calculate() {
        endTime = System.currentTimeMillis();
        return endTime - checkTime;
    }

}
