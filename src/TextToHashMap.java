import java.util.HashMap;
import java.nio.file.*;
import java.io.BufferedReader;
import java.io.IOException;

public final class TextToHashMap {
    private TextToHashMap(){
        //not called
    }

	public static HashMap<String,String> indexArray(Path path) throws IOException{
        HashMap wordMap = new HashMap<String,String>();
        BufferedReader reader = Files.newBufferedReader(path);
        String line;
        String firstWord;
        String rest;

        while((line=reader.readLine()) != null){
            line.replaceAll("-"," ");
            if(line.contains("<") && line.contains(">")){
                line = line.substring(0, line.indexOf("<")) + " " +
                    line.substring(line.indexOf(">")+1);
            }
            try{
                firstWord = line.substring(0,line.indexOf(line.indexOf(" ")));
                rest = line.substring(line.indexOf(line.indexOf(" ")+1);
            }catch(StringIndexOutOfBoundsException e){
                firstWord= line;
                rest = "";
            }
            wordMap.put(firstWord,rest);
        }
        return wordMap;
    } 
}
