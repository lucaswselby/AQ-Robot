package AQOCR;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import nicksapps.Matrix;
import nicksapps.NumberList;

public class AQOCR
{
    /**
     * Converts the numbers in an image into a NumberList
     * @param image
     * @return
     */
    public static NumberList recognize(BufferedImage image, Color foreground) {
        image = AQOCR.toBlackAndWhite(image, foreground);
        Matrix<Boolean> boolMat = AQOCR.imgToMat(image);
        boolMat = AQOCR.trim(boolMat);
        List<PxlImg> pxlRows = AQOCR.separateRows(boolMat);
        Matrix<PxlImg> pxlMat = new Matrix<PxlImg>(ELEMENTS, CHARS);
        int row, col;
        for (row = 0; row < ELEMENTS; row++)
            pxlMat.setRow(row, new ArrayList<PxlImg>(pxlRows.get(row).separateDigits()));
        Matrix<Integer> intMat = new Matrix<Integer>(ELEMENTS, CHARS);
        for (row = 0; row < ELEMENTS; row++) {
            for (col = 0; col < CHARS; col++) {
                PxlImg aPxl = pxlMat.get(row, col);
                if (aPxl == null)
                    intMat.set(row, col, null);
                else
                    intMat.set(row, col, aPxl.intMostLike());
            }
        }
        NumberList numList = new NumberList(ELEMENTS);
        for (row = 0; row < ELEMENTS; row++) {
            numList.add(listToInt(intMat.getRow(row)));
        }
        return numList;
    }
    
    /**
     * Converts the image to black and white
     * @param image
     * @return
     */
    public static BufferedImage toBlackAndWhite(BufferedImage image, Color foreground) {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color color = new Color(image.getRGB(x, y));
                if (foreground.equals(color))
                    image.setRGB(x, y, Color.BLACK.getRGB());
                else
                    image.setRGB(x, y, Color.WHITE.getRGB());
            }
        }
        return image;
    }
    
    /**
     * Converts the image to a matrix of booleans (white
     * pixels -> false
     * @param image
     * @return
     */
    public static Matrix<Boolean> imgToMat(BufferedImage image) {
        int height = image.getHeight();
        int width = image.getWidth();
        Matrix<Boolean> boolMat = new Matrix<Boolean>(height, width);
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                if (image.getRGB(c, r) == Color.WHITE.getRGB())
                    boolMat.set(r, c, Boolean.FALSE);
                else
                    boolMat.set(r, c, Boolean.TRUE);
            }
        }
        return boolMat;
    }
    
    /**
     * Removes outer layers of completely false rows and columns
     * @param boolMat
     * @return
     */
    public static Matrix<Boolean> trim(Matrix<Boolean> boolMat) {
        while (!boolMat.getRow(0).contains(Boolean.TRUE))
            boolMat.removeRow(0);
        while (!boolMat.getRow(boolMat.getRows()-1).contains(Boolean.TRUE))
            boolMat.removeRow(boolMat.getRows()-1);
        while (!boolMat.getColumn(0).contains(Boolean.TRUE))
            boolMat.removeColumn(0);
        while (!boolMat.getColumn(boolMat.getColumns()-1).contains(Boolean.TRUE))
            boolMat.removeColumn(boolMat.getColumns()-1);
        return boolMat;
    }
    
    /**
     * Converts the boolean matrix into a list of PxlImg's representing each row
     * of numbers
     * @param boolMat
     * @return
     */
    public static List<PxlImg> separateRows(Matrix<Boolean> boolMat) {
        List<PxlImg> pxlRows = new ArrayList<PxlImg>(ELEMENTS);
        for (int r = 0; r < boolMat.getRows(); r++) {
            Matrix<Boolean> pxlRowMat = new Matrix<Boolean>(1, boolMat.getColumns());
            while (r < boolMat.getRows() && boolMat.getRow(r).contains(Boolean.TRUE)) {
                pxlRowMat = pxlRowMat.cat(Matrix.VERTICAL, boolMat.getRow(r));
                r++;
            }
            pxlRowMat.removeRow(0);
            pxlRows.add(new PxlImg(trim(pxlRowMat)));
            while (r < boolMat.getRows() && !boolMat.getRow(r).contains(Boolean.TRUE))
                r++;
            r--;
        }
        return pxlRows;
    }
    
    /**
     * Determines if an array of booleans contains a specific boolean
     * @param boolArr
     * @param bool
     * @return
     */
    public static boolean contains(boolean[] boolArr, boolean bool) {
        boolean cont = false;
        for (int i = 0; i < boolArr.length; i++) {
            if (boolArr[i] == bool)
                return true;
        }
        return cont;
    }
    
    /**
     * Converts a list of digits into a single integer
     * @param list
     * @return
     */
    public static int listToInt(List<Integer> list) {
        double num = 0;
        boolean negative = list.get(0).equals(new Integer(-1));
        if (negative)
            list.remove(0);
        while (list.contains(null))
            list.remove(list.size() - 1);
        for (int i = 0; i < list.size(); i++) {
            num += Math.pow(10, list.size() - i - 1) * list.get(i).doubleValue();
        }
        if (negative)
            num = num * -1;
        return (int)num;
    }
    
    public static final int ELEMENTS = 8;
    public static final int CHARS = 4;
    
    public static final Color BLUEISH = new Color(0, 51, 153);
}