package frc.robot.Auto.Missions;

import frc.robot.Auto.AutoMissionChooser;
import frc.robot.Auto.AutoMissionEndedException;

import frc.robot.Auto.Actions.DriveForTimeAction;
import frc.robot.Auto.Actions.WaitAction;
import frc.robot.Auto.Actions.TurnDegreesAction;
import frc.robot.Auto.Actions.MoveElevatorAction;
import frc.robot.Auto.Actions.EffectorAction;

/*
 * This mission has the robot move out of the robot starting
 * zone to score a coral on L4
 */

public class BlueScoreL4 extends MissionBase{
    @Override
    protected void routine() throws AutoMissionEndedException {
       
        runAction(new WaitAction(AutoMissionChooser.delay)); // MAY NOT BE NEEDED
        runAction(new DriveForTimeAction(-0.5, 5));
        runAction(new TurnDegreesAction(30, 4));// add 5 degrees because the robot has consistently com up 5 degrees short of the needed turn
        runAction(new MoveElevatorAction(2, 0.3)); // May be needed depending on how robot starts
        runAction(new EffectorAction(1));
    }
}
