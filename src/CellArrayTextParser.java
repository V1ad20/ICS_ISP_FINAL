
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class CellArrayTextParser {

    private static Cell[][] cellMap;
    private static int row1;
    private static int col1;
    private static int row2;
    private static int col2;
    private static String cellType;

    /**
     * @param path
     * @param cM
     */
    public static void parseAndApply(String path, Cell[][] cM) {
        cellMap = cM;
        try {
            Scanner sc = new Scanner(new File(path));
            while (sc.hasNext()) {
                String rawData = sc.nextLine();
                System.out.println(rawData);

                // The conditions MUST be in this order (if the string is empty then the
                // substring method will throw an error)
                if (!rawData.equals("") && !rawData.substring(0, 2).equals("//")) {
                    String[] inputArr = rawData.trim().split(":");
                    cellType = inputArr[0];
                    if (cellType.equals("CameraCell"))
                        handleCameraCells(inputArr);
                    else
                        handleNonCameraCells(inputArr);
                }
            }
            sc.close();
        } catch (

        Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @param inputArr
     */
    private static void handleNonCameraCells(String[] inputArr) {
        if (inputArr.length > 2) {
            String fillConfiguration = inputArr[2];
            String[] indices = inputArr[1].split(" ");
            cellType = inputArr[0];

            row1 = Integer.parseInt(indices[0]);
            col1 = Integer.parseInt(indices[1]);
            row2 = Integer.parseInt(indices[2]);
            col2 = Integer.parseInt(indices[3]);

            switch (fillConfiguration) {
                case "rect":
                    rect();
                    break;
                case "rectBorder":
                    rectBorder();
                    break;
                default:
                    break;
            }
        } else {
            String[] indices = inputArr[1].split(" ");
            row1 = Integer.parseInt(indices[0]);
            col1 = Integer.parseInt(indices[1]);
            cellMap[row1][col1].type = inputArr[0];
        }
    }

    /**
     * @param inputArr
     */
    private static void handleCameraCells(String[] inputArr) {
        cellType = inputArr[2];
        String cameraCellType = inputArr[1];

        String[] indices = inputArr[3].split(" ");
        row1 = Integer.parseInt(indices[0]);
        col1 = Integer.parseInt(indices[1]);
        row2 = Integer.parseInt(indices[2]);
        col2 = Integer.parseInt(indices[3]);

        String[] borders = inputArr[4].split(" ");
        int rowBorder = Integer.parseInt(borders[0]) * Cell.sideLength;
        int colBorder = Integer.parseInt(borders[1]) * Cell.sideLength;

        for (int row = row1; row <= row2; row++) {
            for (int col = col1; col <= col2; col++) {
                System.out.println("CameraCell Added");
                cellMap[row][col] = new CameraCell(row, col, cellType, cameraCellType, rowBorder, colBorder);
            }
        }

    }

    private static void rect() {
        for (int row = row1; row <= row2; row++) {
            for (int col = col1; col <= col2; col++) {
                cellMap[row][col].type = cellType;
            }
        }
    }

    private static void rectBorder() {
        for (int row = row1; row <= row2; row++) {
            cellMap[row][col1].type = cellType;
            cellMap[row][col2].type = cellType;
        }
        for (int col = col1; col <= col2; col++) {
            cellMap[row1][col].type = cellType;
            cellMap[row2][col].type = cellType;
        }
    }
}
