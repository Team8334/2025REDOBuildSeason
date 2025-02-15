package frc.robot.Auto.Missions;

//import these so that the mission is an option when testing
import frc.robot.Auto.AutoMissionChooser;
import frc.robot.Auto.AutoMissionEndedException;

// import the actions from the auto.actions folder
import frc.robot.Auto.Actions.DriveForTimeAction;
import frc.robot.Auto.Actions.ParallelAction;
import frc.robot.Auto.Actions.TurnDegreesAction;
import frc.robot.Auto.Actions.WaitAction;

public class Testing extends MissionBase{
    
    @Override
    protected void routine() throws AutoMissionEndedException {
       
        //runAction(new DriveForTimeAction (0.2, 5));;
        runAction(new TurnDegreesAction(90, 5));
    }
}
