package frc.robot.Auto.Missions.BlueMissions;

import frc.robot.Auto.AutoMissionChooser;
import frc.robot.Auto.AutoMissionEndedException;
import frc.robot.Auto.Missions.MissionBase;

import frc.robot.Auto.Actions.DriveForTimeAction;
import frc.robot.Auto.Actions.WaitAction;
import frc.robot.Auto.Actions.TurnDegreesAction;
import frc.robot.Auto.Actions.MoveElevatorAction;
import frc.robot.Auto.Actions.EffectorAction;
import frc.robot.Auto.Actions.FrontAlignAction;
import frc.robot.Auto.Actions.FrontLockOnAction;
import frc.robot.Data.States;

/*
 * This mission has the robot move out of the robot starting
 * zone to score a coral on L4
 */

public class BlueScoreL4 extends MissionBase{
    States state;
    @Override
    protected void routine() throws AutoMissionEndedException {
       
        runAction (new FrontLockOnAction("Reef",true,10));//pls change number
        runAction (new FrontAlignAction("Reef","left",10)); //pls change number
        runAction (new MoveElevatorAction(1.5, States.SCOREL4));
        //runAction (new EffectorAction(0.2, 0.2));
        runAction (new DriveForTimeAction(0.1, 0.2));
        runAction (new MoveElevatorAction(1.5, States.RAMP));
    }
}
