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
        public static final int ELEVATOR_MOTOR_ONE = 27; // this is for test robot
        public static final int ELEVATOR_MOTOR_TWO = 10; // check this

        //values for following motors and sensors are currently unknown. delete this comment once you get the real values.
        public static final int EFFECTOR_MOTOR_1 = 27; //9, 27 on test robot
        public static final int EFFECTOR_MOTOR_2 = 10; //what motors are these?
        public static final int LASER_CAN = 12;
            
        }
