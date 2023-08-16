package zad2;

import java.nio.file.*;

public class Futil {

    public static void processDir(String dirName, String resultFileName){
        try{
            Visitor visitor = new Visitor(Paths.get(resultFileName));
            Files.walkFileTree(Paths.get(dirName), visitor);
        }catch(Exception e){
            System.out.println(e);
        }
    }
}