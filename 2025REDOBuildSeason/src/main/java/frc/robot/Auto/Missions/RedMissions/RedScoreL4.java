package frc.robot.Auto.Missions.RedMissions;


import frc.robot.Auto.AutoMissionChooser;
import frc.robot.Auto.AutoMissionEndedException;
import frc.robot.Auto.Missions.MissionBase;

import frc.robot.Auto.Actions.DriveForTimeAction;
import frc.robot.Auto.Actions.EffectorAction;
import frc.robot.Auto.Actions.FrontAlignAction;
import frc.robot.Auto.Actions.FrontLockOnAction;
import frc.robot.Auto.Actions.MoveElevatorAction;
import frc.robot.Auto.Actions.TurnDegreesAction;
import frc.robot.Auto.Actions.WaitAction;
import frc.robot.Data.States;

/*
 * This mission has the robot move out of the robot starting
 * zone to score a coral on L4
 */

public class RedScoreL4 extends MissionBase{
    States state;
    @Override
    protected void routine() throws AutoMissionEndedException {
       
<<<<<<< Updated upstream
        //runAction (new FrontLockOnAction("Reef",true,2));//pls change number
        //runAction (new FrontAlignAction("Reef","left",10)); //pls change number
=======
        runAction (new DriveForTimeAction(-0.2, 2));
        runAction (new FrontLockOnAction("Reef",true,2));//pls change number
        runAction (new FrontAlignAction("Reef","left",10)); //pls change number
>>>>>>> Stashed changes
        runAction (new MoveElevatorAction(0.5, States.SCOREL2));
       // runAction (new EffectorAction(0.2, 0.2));
        runAction (new DriveForTimeAction(0.1, 0.2));
        runAction (new MoveElevatorAction(0.5, States.RAMP));

        //de-algaefying
        runAction (new FrontLockOnAction("Reef", true, 2));
        runAction(new DriveForTimeAction(0.2, 0.2));
        runAction(new MoveElevatorAction(1.25, States.LOWERALGAE));
        //runAction(new EffectorAction(-0.1, 0.75));
    }
}
