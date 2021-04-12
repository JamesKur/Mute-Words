
import java.nio.file.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

//Adds each line of text into an ArrayList
public final class TextToArray{

    private TextToArray(){
        //not called
    }

	public static ArrayList<String> indexArray(Path path) throws IOException{
        ArrayList<String> list = new ArrayList<String>();
        BufferedReader reader = Files.newBufferedReader(path);
        String line;
        while((line=reader.readLine()) != null){
            line.replreplaceAll("-","");
            if(line.contains("<") && line.contains(">")){
                line = line.substring(0, line.indexOf("<")) + " " +
                    line.substring(line.indexOf(">")+1);
            }
            list.add(line.toLowerCase());
        }
        
        return list;
    }
}
