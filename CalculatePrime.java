package prime;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;

public class CalculatePrime {
    public static void main(String[] args) {
        //1. get starting number and set ending number
        //2. load whole primeFile into primeArray..... 
        //      prolly need to change to read from file directly in the future...
        //2. calculate prime and save into array
        //3. append primearray into file

        
        long startTime = System.currentTimeMillis();



        /* 1. getLastNumber from primeFile then set startNumber ----------- */
        String primeFileLocation = "/home/pokish86/Data1/Documents/primelist.txt";
        File primeFile = new File(primeFileLocation);
        long lastNumber = getLastNumber(primeFile);
        long calcRange = 100l;
        long startNumber = (lastNumber / calcRange + 1) * calcRange + 1;
        long endNumber = startNumber + calcRange - 1;

        /* 2. Load whole primeFile into primeArray ------------------------ */
        ArrayList<Long> primeArray = new ArrayList<Long>();
        try (Scanner s = new Scanner(primeFile)) {
            while (s.hasNext()) {primeArray.add(Long.parseLong(s.next()));}
        } catch (FileNotFoundException e) {e.printStackTrace();}

        /* X. Write logs to primeLog */
        // write start time to primeLog
        // write primeArray.size() to primeLog
        // write startNumber and endNumber to primeLog
        // String primeFileLog = "/home/pokish86/Data1/Documents/primeRunLog.txt";
        // File primeLog = new File(primeFileLog);

        /* 3. Calculateprime and add into array --------------------------- */
        ArrayList<Long> tempArray = new ArrayList<Long>();
        for (long i = startNumber; i <= endNumber; i++) {
            if (checkIfPrime(i, primeArray)) {
                System.out.print(".");
                primeArray.add(i);
                tempArray.add(i);
            }
        }
        // write tempArray.size() to primeLog

        /* 4. Append tempArray into primeFile ----------------------------- */
        for (int i = 0; i < tempArray.size(); i++) {
            try {
                appendToFile((String.valueOf(tempArray.get(i)) + "\n"),primeFileLocation);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /* 0. write to log */
        long endTime = System.currentTimeMillis();
        double totalRuntime = (double)(endTime - startTime) / 1000;
        System.out.println(totalRuntime);
        // write totalRuntime to primeLog

    }

    static Long getLastNumber(File file) {
        RandomAccessFile fileHandler = null;
        StringBuilder sb = new StringBuilder();
        try {
            fileHandler = new RandomAccessFile(file, "r");
            long fileLength = fileHandler.length() - 1;
            for (long filePointer = fileLength; filePointer != -1; filePointer--) {
                fileHandler.seek(filePointer);
                int readByte = fileHandler.readByte();
                if (readByte == 0xA) {
                    if (filePointer == fileLength) continue;
                    break;
                } else if (readByte == 0xD) {
                    if (filePointer == fileLength - 1) continue;
                    break;
                }
                sb.append((char)readByte);
            }
        } catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (fileHandler != null) {
                try {
                    String lastLine = sb.reverse().toString();
                    fileHandler.close();
                    return Long.parseLong(lastLine);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return null;
    }

    static boolean checkIfPrime (long num, ArrayList<Long> arr) {
        long calRange = num / 2;
        for (int i = 0; arr.get(i) <= calRange; i++) {
            if (num % arr.get(i) == 0) return false;
        }
        return true;
    }

    static void appendToFile(String numToAppend, String filePath) throws IOException {
        Path path = Paths.get(filePath);
        Files.write(path, numToAppend.getBytes(), StandardOpenOption.APPEND);
    }
}