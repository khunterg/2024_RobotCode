package frc.robot.periods;

import edu.wpi.first.wpilibj.XboxController;
import frc.molib.buttons.Button;
import frc.molib.buttons.ButtonManager;
import frc.robot.subsystem.Chassis;
@SuppressWarnings("unused")
public class Test {
    private static XboxController ctlTest = new XboxController(2);
    private static Button btnResetDistance = new Button(){
        @Override public boolean get(){ return ctlTest.getBButton(); }
    };
    private static Button btnDriveTwoFeet = new Button(){
        @Override public boolean get(){return ctlTest.getYButton();}
    };
    private static Button btnDisablePIDs = new Button(){
        @Override public boolean get(){return ctlTest.getXButton();}
    };
    private Test(){}

    public static void init(){
        ButtonManager.clearFlags();
        
        Chassis.disablePIDs();
    }

    public static void initDashboard() {

    }

    public static void periodic() {
        Chassis.setDrivePower(0.0, 0.0);

        if(btnResetDistance.get()){
            Chassis.resetDistance();
        }
        
        if(btnDriveTwoFeet.getPressed()){
            Chassis.resetDistance();
            Chassis.goToDistance(24.00);
        }
        if(btnDisablePIDs.get()){
            Chassis.disablePIDs();
        }
        Chassis.periodic();
    }
    
}
