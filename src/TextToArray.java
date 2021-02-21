
//import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.nio.charset.StandardCharsets;

public class TextToArray {

	public static ArrayList<String> indexArray(Path path) throws IOException{
        ArrayList<String> list = new ArrayList<String>();
        BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        String line;
        while((line=reader.readLine()) != null){
            list.add(line.toLowerCase());
        }
        return list;
    }
}
