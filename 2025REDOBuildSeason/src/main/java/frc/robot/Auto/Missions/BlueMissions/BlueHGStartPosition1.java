package frc.robot.Auto.Missions.BlueMissions;

import frc.robot.Auto.AutoMissionChooser;
import frc.robot.Auto.AutoMissionEndedException;

import frc.robot.Auto.Actions.DriveForTimeAction;
import frc.robot.Auto.Actions.WaitAction;
import frc.robot.Auto.Missions.MissionBase;
import frc.robot.Auto.Actions.TurnDegreesAction;
import frc.robot.Auto.Actions.MoveElevatorAction;
import frc.robot.Auto.Actions.EffectorAction;
import frc.robot.Auto.Actions.FrontLockOnAction;

/*
 * This mission has the robot move out of the robot starting
 * zone to score a coral on L2 for face HG
 */

public class BlueHGStartPosition1 extends MissionBase{
    @Override
    protected void routine() throws AutoMissionEndedException {
        
        // runAction(new WaitAction(AutoMissionChooser.delay)); // MAY NOT BE NEEDED

        runAction(new DriveForTimeAction(-0.1,  0.2));
        runAction(new TurnDegreesAction(-30, 1));// add 5 degrees because michalangelo has consistently com up 5 degrees short of the needed turn
        runAction(new FrontLockOnAction(null, mActive, mUpdateRate));
        // String options: passive, ramp, Score L1, Score L2, Score L3, Score L4, ejecting coral
        runAction(new MoveElevatorAction(1, "Score L2"));
        runAction(new EffectorAction(0.5,1));
        runAction(new DriveForTimeAction(0.1, 0.2));
        runAction(new MoveElevatorAction(2, "ramp"));
    }
}
