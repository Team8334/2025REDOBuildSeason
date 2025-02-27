package frc.robot.Data;

    public final class PortMap {
        public static final int DRIVER_CONTROLLER = 0;
        public static final int OPERATOR_CONTROLLER = 1;
        
        public static final int MECANUM_FRONT_RIGHT = 3;
        public static final int MECANUM_FRONT_LEFT = 1;
        public static final int MECANUM_BACK_RIGHT = 4;
        public static final int MECANUM_BACK_LEFT = 2;
        public static final int LIMITSWITCH = 0;


        public static final float MOVING_THRESHOLD = (float)(0.0);
        public static final float MAX_HEIGHT_FOR_DRIVING = (float)(10000000000000000000.0);

        public static final int ELEVATOR_ENCODER = 4;
        public static final int ELEVATOR_MOTOR_ONE = 5; // this is for test robot 5 and 6 for competition robot?
        public static final int ELEVATOR_MOTOR_TWO = 6; // check this
    

        //values for following motors and sensors are currently unknown. delete this comment once you get the real values.
        public static final int EFFECTOR_MOTOR_LOWER = 9; //9 on competition bot, 27 on test bot
        public static final int EFFECTOR_MOTOR_UPPER = 10; //10 on compeition bot, 27 on test bot
        public static final int LASER_CAN = 12;

        // Drive base encoders
        public static final double LEFTENCODER_A = 0; // do not have definite value
        public static final double LEFTENCODER_B = 0; // do not have definite value
            
        }
