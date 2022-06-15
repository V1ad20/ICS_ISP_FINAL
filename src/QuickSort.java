import java.util.*;

public class QuickSort {

    String[] names;
    Long[] recordedTimes;

    public QuickSort(ArrayList<String> arr1, ArrayList<Long> arr2) {
        this.names = (String[]) arr1.toArray();
        this.recordedTimes = (Long[]) arr2.toArray();
    }

    public void controlFunc(String[] named, Long[] arr, int start, int end) {
        if (start < end) {
            int pivot = partition(named, arr, start, end);
            controlFunc(named, arr, start, pivot - 1);
            controlFunc(named, arr, pivot + 1, end);
        }
    }

    public void swap(String[] named, Long[] arr, int start, int end) {

        Long temp = arr[start];
        String tempString = named[start];
        arr[start] = arr[end];
        named[start] = named[end];
        arr[end] = temp;
        named[end] = tempString;
    }

    public int partition(String[] named, Long[] arr, int start, int end) {
        int pivChoose = (int) ((Math.random() * (end - start)) + start);
        Long pivot = arr[pivChoose];
        swap(named, arr, pivChoose, end);

        int i = start - 1;
        for (int j = start; j < end; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(named, arr, i, j);
            }
        }
        swap(named, arr, i + 1, end);
        return (i + 1);
    }

    public void sort() {
        controlFunc(names, recordedTimes, 0, recordedTimes.length - 1);
    }

    public String[] getNames() {
        return names;
    }

    public Long[] getTimes() {
        return recordedTimes;
    }
}
