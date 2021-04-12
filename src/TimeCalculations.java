import java.util.ArrayList;

//Performs timecalculations using word and sutitle unit location and size.
public final class TimeCalculations {

    private TimeCalculations(){
        //not called
    }

    public static ArrayList<Double> subtitleToIntervel(String times, String speech, String word){
        
        ArrayList<Double> timestamps = new ArrayList<Double>();
        ArrayList<Integer> indeces = MuteWords.findAllInstances(speech, word);

        Double beginning = toSeconds(Integer.valueOf(times.substring(0,2)), Integer.valueOf(times.substring(3,5)),
            Double.valueOf(times.substring(6,8)+"."+times.substring(9,12)));

        Double end = toSeconds(Integer.valueOf(times.substring(17,19)),Integer.valueOf(times.substring(20,22)),
            Double.valueOf(times.substring(23,25)+"."+times.substring(26)));
        
        double interval = end - beginning;
        double letterTime = interval/(speech.length()-1);
        double wordTime = letterTime*word.length();
        double start;
        for(int i=0;i<indeces.size();i++){
            start = beginning+(indeces.get(i)*letterTime);
            timestamps.add(start-2);
            if(speech.substring(indeces.get(i)).contains(" ") == false){
                timestamps.add(end);
            }else{
            timestamps.add(start+wordTime-.2);
            }
        }
        //System.out.println(timestamps);

        //Removes overlap if there are multiple instances of the same word in a subtitle unit
        timestamps = removeOverlap(timestamps);

        /*We don't want any created timestamps to be outside the original time frame of the subtitle unit because
        all words should occur within that timeframe. This will get ride of extraneous timestamps or set them as the maximum
        bounds as necessary.*/
        timestamps = removeOutOfBounds(timestamps, beginning, end);
        //double[] timeValues = new double[]{beginning,end};
        //System.out.println(timestamps);
        return timestamps;
    }

    //Converse time to seconds.
    private static double toSeconds(int hours, int minutes, double seconds){
            return hours*3600 + minutes*60 + seconds;
        }


    //Removes overlaps of timestamps.
    private static ArrayList<Double> removeOverlap(ArrayList<Double> timestamps){
        for(int i=0;i<timestamps.size();i++){
            //System.out.println(timestamps);
            if(timestamps.size()>i+1 && timestamps.get(i)>timestamps.get(i+1)){
                    timestamps.remove(i);
                    timestamps.remove(i);
                    i--;
            }
        }
        return timestamps;
    }

    //Finds timestamps that are out of the subtitle unit time range and removes them or sets them as the bounds.
    private static ArrayList<Double> removeOutOfBounds(ArrayList<Double> timestamps, double beginning, double end){
        for(int i=0;i<timestamps.size();i++){
            if(timestamps.get(i)<beginning){
                if(i==0){
                    timestamps.set(0,beginning);
                }
                else{
                    timestamps.set(i,beginning);
                    timestamps.subList(0, i-1).clear();
                    i=0;
                }
            }
            else if(timestamps.get(i)>end){
                    timestamps.set(i,end);
                    timestamps.subList(i,timestamps.size()-1).clear();
                break;            
            }
        }
        return timestamps;
    }
    
    
}
