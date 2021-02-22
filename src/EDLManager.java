import java.nio.file.*;
import java.io.IOException;
import java.util.ArrayList;
import java.text.DecimalFormat;

//Adds an Edit Decission List(EDL) command into an EDL file
public final class EDLManager{

    private EDLManager() {
        //not called
    }

    public static void command(Path edl, ArrayList<Double> timestamps,int command,String speech,String word) throws IOException{
        String suffix = " "+speech+word+"\n";
        for(int i=0;i<timestamps.size();i += 2){
            if(command == EDLCommand.MUTE){
                Files.write(edl,(mute(formatTime(timestamps.get(i)),formatTime(timestamps.get(i+1)))+suffix).getBytes(),StandardOpenOption.APPEND);
            }
        }
    }

    public static void command(Path edl, ArrayList<Double> timestamps,int command) throws IOException{
        String suffix = "\n";
        for(int i=0;i<timestamps.size();i += 2){
            if(command == EDLCommand.MUTE){
                Files.write(edl,(mute(formatTime(timestamps.get(i)),formatTime(timestamps.get(i+1)))+suffix).getBytes(),StandardOpenOption.APPEND);
            }
        }
    }

    public static void command(Path edl, ArrayList<EDLCommand> edlCommands) throws IOException{
        String suffix;
        for(EDLCommand edlCommand : edlCommands){
            if(edlCommand.getCommand() == EDLCommand.MUTE){
                suffix = " "+edlCommand.getSpeech()+edlCommand.getMutedWord()+"\n";
                Files.write(edl,(mute(formatTime(edlCommand.getStart()),formatTime(edlCommand.getEnd()))+suffix).getBytes(),StandardOpenOption.APPEND);
            }
        }
    }

    private static String formatTime(double timeValue){
        DecimalFormat df = new DecimalFormat("#####.###");
        timeValue = Double.parseDouble(df.format(timeValue));
        String time = String.valueOf(timeValue);
        while(time.substring(0,time.indexOf(".")).length() < 5){
            time = "0" + time;
        }
        while(time.substring(time.indexOf(".")+1).length()<3){
            time = time + "0";
        }
        return time;
    }
    
    private static String mute(String start, String stop){
        return start+" "+stop+" "+EDLCommand.MUTE+" "+ "#Muted";
    }
}

//Example of EDL commands
//00000.000	00416.339	3	#SkipToNext
//00421.339	00422.024	1	#Muted: i'm good! 7m1s