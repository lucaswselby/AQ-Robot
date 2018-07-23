package AQOCR;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import nicksapps.Matrix;
import nicksapps.NumberList;

public class PxlImg
{
    /**
     * A boolean representation of a black and white image such that white
     * pixels are false and black pixels are true.
     * @param aBoolMat
     */
    public PxlImg(Matrix<Boolean> aBoolMat) {
        boolMat = aBoolMat;
    }
    
    /**
     * Constructor for the sole purpose of using the initialize function and its
     * products.
     */
    public PxlImg() { }
    
    /**
     * If the PxlImg represents a row of individual digits, this function will
     * output a list of PxlImg's for the individual digits.
     * @return
     */
    public List<PxlImg> separateDigits() {
        List<PxlImg> pxlDigits = new ArrayList<PxlImg>(AQOCR.CHARS);
        for (int c = 0; c < boolMat.getColumns(); c++) {
            Matrix<Boolean> pxlDigitMat = new Matrix<Boolean>(boolMat.getRows(),1);
            while (c < boolMat.getColumns() && boolMat.getColumn(c).contains(Boolean.TRUE)) {
                pxlDigitMat = pxlDigitMat.cat(Matrix.HORIZONTAL, boolMat.getColumn(c));
                c++;
            }
            pxlDigitMat.removeColumn(0);
            pxlDigits.add(new PxlImg(AQOCR.trim(pxlDigitMat)));
            while (c < boolMat.getColumns() && !boolMat.getColumn(c).contains(Boolean.TRUE))
                c++;
            c--;
        }
        int size = pxlDigits.size();
        for (int extra = 0; extra < AQOCR.CHARS - size; extra++)
            pxlDigits.add(null);
        return pxlDigits;
    }
    
    /**
     * Returns the percentage of pixels that are have the same value and the
     * same location as another PxlImg
     * @param img
     * @return
     */
    public double percentMatch(PxlImg img) {
        double count = 0;
        double sum = 0;
        for (int row = 0; row < Math.max(img.getRows(), this.getRows()); row++) {
            for (int col = 0; col < Math.max(img.getColumns(), this.getColumns()); col++) {
                count++;
                try {
                    if (img.get(row, col) == this.get(row, col))
                        sum++;
                } catch (IndexOutOfBoundsException e) { }
            }
        }
        return sum / count;
    }
    
    /**
     * Returns the boolean value of the pixel at a specified location
     * @param r
     * @param c
     * @return
     */
    public boolean get(int r, int c) {
        return boolMat.get(r, c);
    }
    
    /**
     * Returns the number of rows
     * @return
     */
    public int getRows() {
        return boolMat.getRows();
    }
    
    /**
     * Returns the number of columns
     * @return
     */
    public int getColumns() {
        return boolMat.getColumns();
    }
    
    @Override
    public String toString() {
        String str = "";
        for (int r = 0; r < boolMat.getRows(); r++) {
            for (int c = 0; c < boolMat.getColumns(); c++) {
                if (boolMat.get(r, c))
                    str += TRUE_CHAR;
                else
                    str += FALSE_CHAR;
            }
            str += "\n";
        }
        return str;
    }
    
    /**
     * Prepares the 11 standard digits for recognizing.
     */
    public void initialize() {
        Scanner digits = null;
        try {
            digits = new Scanner(new File("digits.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("digits.txt file is missing");
        }
        
        String line;
        
        List<PxlImg> pxlList = new ArrayList<PxlImg>(11);
        for (int d = 0; d < 11; d++) {
            String str = "";
            line = digits.nextLine();
            while (!line.equals("")) {
                str += line + "\n";
                line = digits.nextLine();
            }
            Scanner digit = new Scanner(str);
            line = digit.nextLine();
            int cols = line.length();
            int rows = 1;
            while (digit.hasNextLine()) {
                digit.nextLine();
                rows++;
            }
            Matrix<Boolean> aBoolMat = new Matrix<Boolean>(rows, cols);
            digit = new Scanner(str);
            for (int r = 0; r < rows; r++) {
                line = digit.nextLine();
                for (int c = 0; c < cols; c++) {
                    if (line.charAt(c) == TRUE_CHAR)
                        aBoolMat.set(r, c, Boolean.TRUE);
                    else
                        aBoolMat.set(r, c, Boolean.FALSE);
                }
            }
            pxlList.add(new PxlImg(aBoolMat));
        }
        
        ZERO = pxlList.get(0);
        ONE = pxlList.get(1);
        TWO = pxlList.get(2);
        THREE = pxlList.get(3);
        FOUR = pxlList.get(4);
        FIVE = pxlList.get(5);
        SIX = pxlList.get(6);
        SEVEN = pxlList.get(7);
        EIGHT = pxlList.get(8);
        NINE = pxlList.get(9);
        MINUS = pxlList.get(10);
    }
    
    /**
     * Returns the digit the PxlImage looks most like
     * @return
     */
    public int intMostLike() {
        initialize();
        NumberList numList = new NumberList(11);
        numList.add(percentMatch(ZERO));
        numList.add(percentMatch(ONE));
        numList.add(percentMatch(TWO));
        numList.add(percentMatch(THREE));
        numList.add(percentMatch(FOUR));
        numList.add(percentMatch(FIVE));
        numList.add(percentMatch(SIX));
        numList.add(percentMatch(SEVEN));
        numList.add(percentMatch(EIGHT));
        numList.add(percentMatch(NINE));
        numList.add(percentMatch(MINUS));
        int ind = numList.indexOf(numList.max());
        if (ind == 10)
            return -1;
        return ind;
    }
    
    public static final char TRUE_CHAR = 'X';
    public static final char FALSE_CHAR = ' ';
    
    public Matrix<Boolean> boolMat;
    public PxlImg ZERO;
    public PxlImg ONE;
    public PxlImg TWO;
    public PxlImg THREE;
    public PxlImg FOUR;
    public PxlImg FIVE;
    public PxlImg SIX;
    public PxlImg SEVEN;
    public PxlImg EIGHT;
    public PxlImg NINE;
    public PxlImg MINUS;
}