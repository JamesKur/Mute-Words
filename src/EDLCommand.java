public class EDLCommand implements Comparable<EDLCommand>{
    public static int CUT = 0;
    public static int MUTE = 1;
    public static int SCENE_MARKER = 2;
    public static int COMMERCIAL = 3;
    private double beginning;
    private double end;
    private int com;
    private String speech = "SPEECH NOT SET";
    private String mutedWord = "MUTED WORD NOT SET";

    public EDLCommand(double start, double stop, int command, String text, String word){
        beginning = start;
        end = stop;
        com = command;
        speech = text;
        mutedWord = word;
    }
    public EDLCommand(double start, double stop, int command, String text){
        beginning = start;
        end = stop;
        com = command;
        speech = text;
    }

    public EDLCommand(double start, double stop, int command){
        beginning = start;
        end = stop;
        com = command;
    }
    public double getStart(){
        return beginning;
    }

    public double getEnd(){
        return end;
    }

    public double getCommand(){
        return com;
    }

    public String getSpeech(){
        return speech;
    }

    public String getMutedWord(){
        return mutedWord;
    }

    @Override
    public int compareTo(EDLCommand edlCommand) {
        if(this.beginning - edlCommand.beginning > 0)
            return 1;
        else if(this.beginning - edlCommand.beginning > 0)
            return -1;
        else
            return 0;
    }
}
