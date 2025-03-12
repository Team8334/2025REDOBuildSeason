package frc.robot.Data;

public class EncoderValues {
    //this is a list of encoder values.
    //comments on how high the levels are, the numbers are for how many rotations
    //use advantage scope. connect to bot, then select "output" under elevator. that number is the 
    //numbers you see right now. during calibration, double check these values work, and if not, follow
    //those steps to change them.

    public static final double ELEVATOR_RAMP = -0.42;
    public static final double ELEVATOR_L1 = 0.04; // 46cm
    public static final double ELEVATOR_L2 = 0.42; //81cm
    public static final double ELEVATOR_L3 = 1.83; //121 cm
    public static final double ELEVATOR_L4 = 4.15; // 183cm
    public static final double ELEVATOR_BARGE = 4.28;
    public static final double ELEVATOR_LOWER_ALGAE = -0.42;
    public static final double ELEVATOR_UPPER_ALGAE = 1.11;
}
