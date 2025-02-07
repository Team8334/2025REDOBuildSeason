package frc.robot.Subsystem;

public class EndEffector {
    private static EndEffector instance = null;

    public static EndEffector getInstance() {
        if (instance == null) {
            instance = new EndEffector();
        }
        return instance;
    }


}
