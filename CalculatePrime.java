package prime;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;

public class CalculatePrime {
    public static void main(String[] args) {
        //1. get starting number and set ending number
        //2. load whole primeFile into primeArray..... 
        //      prolly need to change to read from file directly in the future...
        //2. calculate prime and save into array
        //3. append primearray into file

        /* X. Set start time for benchmark */
        long startTime = System.currentTimeMillis();

        /* X. Get dates */
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date now = new Date();
        String currentDateTime = formatter.format(now);
                

        String fileDirectory = "/home/pokish86/Data1/Coding/java/pok/prime/";
        String fileName = fileDirectory + "primelist.txt";
        File primeFile = new File(fileName);

        /* 1. getLastNumber from primeFile then set startNumber ----------- */
        long lastNumber = getLastNumber(primeFile);
        long calcRange = 100l;
        long startNumber = (lastNumber / calcRange + 1) * calcRange + 1;
        long endNumber = startNumber + calcRange - 1;

        /* 2. Load whole primeFile into primeArray ------------------------ */
        ArrayList<Long> primeArray = new ArrayList<Long>();
        try (Scanner s = new Scanner(primeFile)) {
            while (s.hasNext()) {primeArray.add(Long.parseLong(s.next()));}
        } catch (FileNotFoundException e) {e.printStackTrace();}

        // 3. rename and move to folder
        String newFileName = fileDirectory + "pastPrimeList/primelist-" + currentDateTime + ".txt";
        Path result = null;
        try {
            result = Files.move(Paths.get(fileName), Paths.get(newFileName));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(result);
        }

        // 4. create new file
        try {
            File newFile = new File(fileName);
            newFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* 3. Calculateprime and add into array --------------------------- */
        // ArrayList<Long> tempArray = new ArrayList<Long>();
        // for (long i = startNumber; i <= endNumber; i++) {
        //     if (checkIfPrime(i, primeArray)) {
        //         System.out.print(".");
        //         primeArray.add(i);
        //         // tempArray.add(i);
        //     }
        // }
        for (long i = startNumber; i <= endNumber; i++) {
            long calRange = i / 2l;
            boolean notPrime = false;
            for (int j = 0; primeArray.get(j) <= calRange; i++) {
                if (i % primeArray.get(j) == 0) {
                    notPrime = true;
                    break;
                }
            }
            if (notPrime) {
                continue;
            } else {
                primeArray.add(i);
            }
        }
        // write tempArray.size() to primeLog

        /* 4. Append primeArray into primeFile ----------------------------- */
        for (int i = 0; i < primeArray.size(); i++) {
            Path path = Paths.get(fileName);
            String str = String.valueOf(primeArray.get(i)) + "\n";
            try {
                Files.write(path, str.getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /* X. write to log */
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
}