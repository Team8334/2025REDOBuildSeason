package frc.robot.Auto.Missions.BlueMissions;

import frc.robot.Auto.AutoMissionChooser;
import frc.robot.Auto.AutoMissionEndedException;
import frc.robot.Auto.Missions.MissionBase;

import frc.robot.Auto.Actions.DriveForTimeAction;
import frc.robot.Auto.Actions.WaitAction;
import frc.robot.Auto.Actions.TurnDegreesAction;
import frc.robot.Auto.Actions.MoveElevatorAction;
import frc.robot.Auto.Actions.EffectorAction;
import frc.robot.Data.States;

/*
 * This mission has the robot move out of the robot starting
 * zone to score a coral on L4
 * 
 * Info: the reef is 88 inches from the robot starting line.
 * 
 * HOW TO LINE UP:
 * The back right wheel is on the line. Right most green thing of the effecator shou be in line with the 
 * reef peg of choice. When these are aligned, the robot should be turned to the right just a little bit.
 * 
 */

public class BlueScoreL4 extends MissionBase{
    States state;
    @Override
    protected void routine() throws AutoMissionEndedException {
       
        runAction (new DriveForTimeAction(-0.3, 2.1));
        runAction (new MoveElevatorAction(1.5, States.SCOREL4));
        runAction (new EffectorAction(0.2, States.SCORING));
        runAction (new DriveForTimeAction(0.1, 0.5));
        runAction (new MoveElevatorAction(1.5, States.RAMP));
        runAction (new EffectorAction(0.2, States.NOTHING));
    }
}
