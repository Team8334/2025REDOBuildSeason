package frc.robot.Interfaces;

public interface Devices {

    boolean isOperational();


    default void logData() {

    }

    String getName();

}