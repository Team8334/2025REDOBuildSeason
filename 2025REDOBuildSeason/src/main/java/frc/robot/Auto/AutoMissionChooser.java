package frc.robot.Auto;

import frc.robot.Auto.Missions.*;
import frc.robot.Auto.Missions.BlueMissions.BlueHGStartPosition1;
import frc.robot.Auto.Missions.BlueMissions.BlueScoreStartPosition3;
import frc.robot.Auto.Missions.BlueMissions.BlueScoreStartPosition2;
import frc.robot.Auto.Missions.RedMissions.RedScoreStartPosition1;
import frc.robot.Auto.Missions.RedMissions.RedScoreStartPosition3;
import frc.robot.Auto.Missions.RedMissions.RedScoreStartPosition2;

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
        StartingPosition1,
        StartingPosition2,
        StartingPosition3,
        Testing,
        // actual missions
        MoveAcrossLineMission,
        doNothing,
        RedScoreStartPosition1,
        RedScoreStartPosition2,
        RedScoreStartPosition3,
        BlueHGStartPosition1,
        BlueStartPosition2,
        BlueStartPosition3,

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
        missionChooser.addOption("Starting Position 1", DesiredMission.StartingPosition1);
        missionChooser.addOption("Starting Position 2", DesiredMission.StartingPosition2);
        missionChooser.addOption("Starting Position 3", DesiredMission.StartingPosition3);
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
            // if scoring in L2, and does mission according to alliance
            case StartingPosition1:
                if (alliance == "Red") {
                    return Optional.of(new RedScoreStartPosition1());
                }
                else if (alliance == "Blue") {
                    return Optional.of(new BlueHGStartPosition1());
                }
                else {
                    return Optional.of(new DoNothingMission());
                }
            // if scoring in L3, and does mission according to alliance
            case StartingPosition2:
                if (alliance == "Red"){
                    return Optional.of(new RedScoreStartPosition2());
                }
                else if (alliance == "Blue"){
                    return Optional.of(new BlueScoreStartPosition2());
                }
                else {
                    return Optional.of(new DoNothingMission());
                }
            // if scoring in L4, and does mission according alliance
            case StartingPosition3:
                if (alliance == "Red") {
                    return Optional.of(new RedScoreStartPosition3());
                }
                else if (alliance == "Blue") {
                    return Optional.of(new BlueScoreStartPosition3());
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

