package frc.robot.Auto.Missions;

//import these so that the mission is an option when testing
import frc.robot.Auto.AutoMissionChooser;
import frc.robot.Auto.AutoMissionEndedException;

// import the actions from the auto.actions folder
import frc.robot.Auto.Actions.DriveForTimeAction;
import frc.robot.Auto.Actions.ParallelAction;
import frc.robot.Auto.Actions.TurnDegreesAction;
import frc.robot.Auto.Actions.WaitAction;
import frc.robot.Auto.Actions.MoveElevatorAction;
import frc.robot.Auto.Actions.EffectorAction;

public class Testing extends MissionBase{
    
    @Override
    protected void routine() throws AutoMissionEndedException {
       
    // Testing actions
    
    // Working actions
        runAction(new MoveElevatorAction(2, "Score L2"));
        runAction(new EffectorAction(0.2, 2));
        //runAction(new TurnDegreesAction(-110, 3));
        //runAction(new DriveForTimeAction (0.2, 2));;
        //runAction(new WaitAction(1));
        //runAction(new TurnDegreesAction(180, 3));
    }
}
