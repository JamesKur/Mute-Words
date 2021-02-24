import java.nio.file.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;

//Subititle subunite: a single block of text that appears on screen during a movie.
//This code is still in the works, hence the numerous commented-out code that is used for testing.
public class MuteWords {
    public static void main(String[] args) throws IOException {
        Path wordList = Paths.get("Bad_Words.txt");
        ArrayList<String> words = TextToArray.indexArray(wordList);
        Collections.sort(words, Comparator.comparing(String::length));
        Collections.reverse(words);
        //System.out.println(words);
        Path subtitle = Paths.get("Avatar.srt");
        //Path subtitle = Paths.get(args[0]);
        Path edl = Paths.get(subtitle.toString().substring(0,subtitle.toString().lastIndexOf(".")) + ".edl");
        try{
            Files.createFile(edl);
        }catch(Exception e){
            Files.delete(edl);
            Files.createFile(edl);
        }
        BufferedReader subtitleReader = Files.newBufferedReader(subtitle);
        //System.out.println(subtitleReader.readLine());
        ArrayList<String> subUnit = new ArrayList<String>();
        String subUnitSpeech = "";
        String subtitleLine;
        ArrayList<Double> timestamps;
        ArrayList<EDLCommand> edls = new ArrayList<EDLCommand>();
        Scanner tester = new Scanner(System.in);

        while((subtitleLine=subtitleReader.readLine()) != null){
            if(subtitleLine.length() > 0){
                subUnit.add(subtitleLine.toLowerCase());
            }
            else{
                for(int i=subUnit.size();i>2;i--){
                   subUnitSpeech = subUnit.get(i-1) + " " + subUnitSpeech;
                }
                //System.out.println(subUnit);
                //System.out.println(subUnitSpeech);
                //System.out.println(subUnitSpeech.contains("damn"));
                //tester.nextLine();
                for(String word : words){
                    if(subUnitSpeech.contains(word)){
                        //System.out.println(contains(subUnitSpeech.indexOf(word) == 0,subUnitSpeech,word));
                        if(contains(subUnitSpeech,word)>-1){
                                //System.out.println("Wooophie");
                                timestamps = TimeCalculations.subtitleToIntervel(subUnit.get(1), subUnitSpeech, word);
                                EDLManager.command(edl, timestamps,1,subUnitSpeech,word);
                                for(int i=0;i<timestamps.size();i+=2){
                                    edls.add(new EDLCommand(timestamps.get(i), timestamps.get(i+1), EDLCommand.MUTE, subUnitSpeech, word));
                                }
                                //tester.nextLine();
                        }
                        break;
                    }
                }
                subUnitSpeech = "";
                subUnit.clear();
            }   
        }

        Collections.sort(edls);

    }
    //Attemps to find all practical uses of word in a string. 
    /*For example, if you are looking for the word "is", this method will ignore that char
    sequence in the in the word "fist"*/
    public static int contains( String subUnitSpeech, String word){
        if(subUnitSpeech.indexOf(word)==0){
            if(subUnitSpeech.contains(word+" ")){
                return subUnitSpeech.indexOf(word+" ");
            }
            else if(subUnitSpeech.contains(word+".")){
                return subUnitSpeech.indexOf(word+".");
            }
            else if(subUnitSpeech.contains(word+",")){
                return subUnitSpeech.indexOf(word+",");
            }
            else if(subUnitSpeech.contains(word+"-")){
                return subUnitSpeech.indexOf(word+"-");
            }
            else if(subUnitSpeech.contains(word+"?")){
                return subUnitSpeech.indexOf(word+"?");
            }
            else if(subUnitSpeech.contains(word+"!")){
                return subUnitSpeech.indexOf(word+"!");
            }
            else if(subUnitSpeech.contains(word+"er")){
                return subUnitSpeech.indexOf(word+"er");
            }
            else if(subUnitSpeech.contains(word+"es")){
                return subUnitSpeech.indexOf(word+"es");
            }
            else if(subUnitSpeech.contains(word+"y")){
                return subUnitSpeech.indexOf(word+"y");
            }
            else if(subUnitSpeech.contains(word+"ies")){
                return subUnitSpeech.indexOf(word+"ies");
            }
            else if(subUnitSpeech.contains(" "+word+"s")){
                try {
                    if(Character.isLetter(subUnitSpeech.charAt(subUnitSpeech.indexOf(" "+word+"s")+(" "+word+"s").length())))
                        return -1;
                    else
                        return subUnitSpeech.indexOf(" "+word+"y");
                    
                } catch (Exception e) {
                    return subUnitSpeech.indexOf(" "+word+"y");
                }
            }
            else if(subUnitSpeech.contains(word+"ing")){
                return subUnitSpeech.indexOf(word+"ing");
            }
            else if(subUnitSpeech.contains(word+"ed")){
                return subUnitSpeech.indexOf(word+"ed");
            }
            else
                return -1;
        }
        else{
            if(subUnitSpeech.contains(" "+word+" ")){
                return subUnitSpeech.indexOf(word+" ");
            }
            else if(subUnitSpeech.contains(" "+word+".")){
                return subUnitSpeech.indexOf(" "+word+".");
            }
            else if(subUnitSpeech.contains(" "+word+",")){
                return subUnitSpeech.indexOf(" "+word+",");
            }
            else if(subUnitSpeech.contains(" "+word+"-")){
                return subUnitSpeech.indexOf(" "+word+"-");
            }
            else if(subUnitSpeech.contains(" "+word+"?")){
                return subUnitSpeech.indexOf(" "+word+"?");
            }
            else if(subUnitSpeech.contains(" "+word+"!")){
                return subUnitSpeech.indexOf(" "+word+"!");
            }
            else if(subUnitSpeech.contains(" "+word+"er")){
                return subUnitSpeech.indexOf(" "+word+"er");
            }
            else if(subUnitSpeech.contains(" "+word+"es")){
                return subUnitSpeech.indexOf(" "+word+"es");
            }
            else if(subUnitSpeech.contains(" "+word+"y")){
                return subUnitSpeech.indexOf(" "+word+"y");
            }
            else if(subUnitSpeech.contains(" "+word+"ies")){
                return subUnitSpeech.indexOf(" "+word+"ies");
            }
            else if(subUnitSpeech.contains(" "+word+"s")){
                try {
                    if(Character.isLetter(subUnitSpeech.charAt(subUnitSpeech.indexOf(" "+word+"s")+(" "+word+"s").length())))
                        return -1;
                    else
                        return subUnitSpeech.indexOf(" "+word+"y");
                    
                } catch (Exception e) {
                    return subUnitSpeech.indexOf(" "+word+"y");
                }
            }
            else if(subUnitSpeech.contains(" "+word+"ing")){
                return subUnitSpeech.indexOf(" "+word+"ing");
            }
            else if(subUnitSpeech.contains(" "+word+"ed")){
                return subUnitSpeech.indexOf(" "+word+"ed");
            }
            else if(subUnitSpeech.contains(">"+word+"<")){
                return subUnitSpeech.indexOf(">"+word+"<");
            }
            else if(subUnitSpeech.contains(" "+word+"<")){
                return subUnitSpeech.indexOf(" "+word+"<");
            }
            else if(subUnitSpeech.contains(">"+word+" ")){
                return subUnitSpeech.indexOf(">"+word+" ");
            }
            else if(subUnitSpeech.contains("-"+word+",")){
                return subUnitSpeech.indexOf("-"+word+",");
            }
            else if(subUnitSpeech.contains("-"+word+"-")){
                return subUnitSpeech.indexOf("-"+word+"-");
            }
            else if(subUnitSpeech.contains("-"+word+"?")){
                return subUnitSpeech.indexOf("-"+word+"?");
            }
            else if(subUnitSpeech.contains("-"+word+"!")){
                return subUnitSpeech.indexOf("-"+word+"!");
            }
            else if(subUnitSpeech.contains("-"+word+"er")){
                return subUnitSpeech.indexOf("-"+word+"er");
            }
            else if(subUnitSpeech.contains("-"+word+"es")){
                return subUnitSpeech.indexOf("-"+word+"es");
            }
            else if(subUnitSpeech.contains("-"+word+"y")){
                return subUnitSpeech.indexOf("-"+word+"y");
            }
            else if(subUnitSpeech.contains("-"+word+"ies")){
                return subUnitSpeech.indexOf("-"+word+"ies");
            }
            else if(subUnitSpeech.contains("-"+word+"s")){
                return subUnitSpeech.indexOf("-"+word+"s");
            }
            else if(subUnitSpeech.contains("-"+word+"ing")){
                return subUnitSpeech.indexOf("-"+word+"ing");
            }
            else if(subUnitSpeech.contains("-"+word+"ed")){
                return subUnitSpeech.indexOf("-"+word+"ed");
            }
            else if(subUnitSpeech.contains("-"+word+"<")){
                return subUnitSpeech.indexOf(" "+word+"<");
            }
            else if(subUnitSpeech.contains(">"+word+"-")){
                return subUnitSpeech.indexOf(">"+word+"-");
            }
            else
                return -1;
        }
    }

    //Find all instances of word in a string.
    public static ArrayList<Integer> findAllInstances(String text, String word){
        int index;
        int count=0;
        ArrayList<Integer> indeces = new ArrayList<Integer>();
        text = text.toLowerCase();
        do{
            index = MuteWords.contains(text, word);
            if(index>-1){ 
                text = text.substring(index+word.length());
                indeces.add(index+count);
                count+=index+word.length();
            }
        }while(index>-1);
        //System.out.println(indeces);
        //Collections.sort(indeces);
        return indeces;
    }
}