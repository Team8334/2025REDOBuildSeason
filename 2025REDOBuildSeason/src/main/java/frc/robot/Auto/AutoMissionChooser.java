package frc.robot.Auto;

import frc.robot.Auto.Missions.*;
import java.util.Optional;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/*
 * This lets the person choose which mission is executed
 */

public class AutoMissionChooser {
    enum DesiredMission {
        exampleMission,
        // general missions that use alliance to determine the actual missions
        ScoringL1Mission,
        ScoringL4Mission,
        Testing,
        // actual missions
        MoveAcrossLineMission,
        doNothing,
        RedScoreL2,
        RedScoreL4,
        BlueScoreL2,
        BlueScoreL4,

    }

    private DesiredMission cachedDesiredMission = DesiredMission.doNothing;

    private final SendableChooser<DesiredMission> missionChooser;

    private Optional<MissionBase> autoMission = Optional.empty();

    public static double delay;

    String alliance;

    public AutoMissionChooser() {
        missionChooser = new SendableChooser<>();

        // add more here as needed, is what is seen when choosing a mission
        missionChooser.addOption("Do Nothing", DesiredMission.doNothing);
        missionChooser.addOption("Leave Community", DesiredMission.MoveAcrossLineMission);
        missionChooser.addOption("Scoring L2", DesiredMission.ScoringL1Mission);
        missionChooser.addOption("Scoring L4", DesiredMission.ScoringL4Mission);
        missionChooser.addOption("Testing", DesiredMission.Testing);

        SmartDashboard.putNumber("Auto Delay (seconds)", 0);

        SmartDashboard.putData("Auto Mission", missionChooser);
        SmartDashboard.putString("Current Action System", "None");

        try {
            alliance = DriverStation.getAlliance().orElseThrow(() -> new Exception("No alliance")).toString();
        }
        catch (Exception e) {
            // Handle the exception, for example:
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }

    public void updateMissionCreator() {
        try {
            alliance = DriverStation.getAlliance().orElseThrow(() -> new Exception("No alliance")).toString();
        }
        catch (Exception e) {
            // Handle the exception, for example:
            System.out.println("Exception occurred: " + e.getMessage());
        }
        delay = SmartDashboard.getNumber("Auto Delay", 0);
        DesiredMission desiredMission = missionChooser.getSelected();

        if (desiredMission == null) {
            desiredMission = DesiredMission.doNothing;
        }

        if (cachedDesiredMission != desiredMission) {
            System.out.println("Auto selection changed, updating creator: desiredMission->" + desiredMission.name());
            autoMission = getAutoMissionForParams(desiredMission);
        }

        cachedDesiredMission = desiredMission;
    }

    private Optional<MissionBase> getAutoMissionForParams(DesiredMission mission) {
        switch (mission) {
            // do nothing mission
            case doNothing:
                return Optional.of(new DoNothingMission());
            // leave community mission
            case MoveAcrossLineMission:
                return Optional.of(new MoveAcrossLineMission());
            // testing mission
            case Testing:
                return Optional.of(new Testing());
            // if scoring in L1, and does mission according to alliance
            case ScoringL1Mission:
                if (alliance == "Red") {
                    return Optional.of(new RedScoreL2());
                }
                else if (alliance == "Blue") {
                    return Optional.of(new BlueScoreL2());
                }
                else {
                    return Optional.of(new DoNothingMission());
                }
            // if scoring in L4, and does mission according alliance
            case ScoringL4Mission:
                if (alliance == "Red") {
                    return Optional.of(new RedScoreL4());
                }
                else if (alliance == "Blue") {
                    return Optional.of(new BlueScoreL4());
                }
                else {
                    return Optional.of(new DoNothingMission());
                }
            // if no auto mission is found
            default:
                System.err.println("No valid autonomous mission found for" + mission);
                return Optional.empty();
        }
    }

    public void reset() {
        autoMission = Optional.empty();
        cachedDesiredMission = DesiredMission.doNothing;
    }

    public void outputToSmartDashboard() {
        SmartDashboard.putString("AutoMissionSelected", cachedDesiredMission.name());
    }

    public Optional<MissionBase> getAutoMission() {
        return autoMission;
    }
}

