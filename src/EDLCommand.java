public class EDLCommand implements Comparable<EDLCommand>{
    public static int CUT = 0;
    public static int MUTE = 1;
    public static int SCENE_MARKER = 2;
    public static int COMMERCIAL = 3;
    private double beginning;
    private double end;
    private int com;

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
