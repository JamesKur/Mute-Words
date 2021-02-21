import java.nio.file.*;
import java.io.IOException;
import java.util.ArrayList;
import java.text.DecimalFormat;

public class EDLManager{
    public static void command(Path edl, ArrayList<Double> timestamps,int command,String speech,String word) throws IOException{
        String suffix = " "+speech+word+"\n";
        for(int i=0;i<timestamps.size();i += 2){
            //System.out.println(mute(formatTime(timestamps.get(i)),formatTime(timestamps.get(i+1)))+suffix);
            Files.write(edl,(mute(formatTime(timestamps.get(i)),formatTime(timestamps.get(i+1)))+suffix).getBytes(),StandardOpenOption.APPEND);
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

//00000.000	00416.339	3	#SkipToNext
//00421.339	00422.024	1	#Muted: i'm good! 7m1s