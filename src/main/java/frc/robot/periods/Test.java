package frc.robot.periods;

import frc.molib.hid.XboxController;
import frc.molib.buttons.Button;
import frc.molib.buttons.ButtonManager;
import frc.robot.subsystem.Chassis;
@SuppressWarnings("unused")
public class Test {
    private static XboxController ctlTest = new XboxController(2);
    private static Button btnResetDistance = new Button(){
        @Override public boolean get(){ return ctlTest.getStartButton(); }
    };
    private static Button btnDriveEightFeet = new Button(){
        @Override public boolean get(){return ctlTest.getAButton();}
    };
    private static Button btnDriveFourFeet = new Button(){
        @Override public boolean get(){return ctlTest.getXButton();}
    };
    private static Button btnDriveTwoFeet = new Button(){
        @Override public boolean get(){return ctlTest.getYButton();}
    };
    private static Button btnDriveOneFeet = new Button(){
        @Override public boolean get(){return ctlTest.getBButton();}
    };
    private static Button btnTurn90 = new Button(){
        @Override public boolean get(){return ctlTest.getRightTrigger();}
    };
    private static Button btnDisablePIDs = new Button(){
        @Override public boolean get(){return ctlTest.getBackButton();}
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
            Chassis.resetAngle();
        }
        if(btnDriveOneFeet.getPressed()){
            Chassis.resetDistance();
            Chassis.goToDistance(12.00);
        }
        if(btnDriveTwoFeet.getPressed()){
            Chassis.resetDistance();
            Chassis.goToDistance(24.00);
        }
        if(btnDriveFourFeet.getPressed()){
            Chassis.resetDistance();
            Chassis.goToDistance(48.00);
        }
        if(btnDriveEightFeet.getPressed()){
            Chassis.resetDistance();
            Chassis.goToDistance(96.00);
        }
        if(btnDisablePIDs.get()){
            Chassis.disablePIDs();
        }
        if (btnTurn90.getPressed()){
            Chassis.resetAngle();
            Chassis.goToAngle(90.0);
        }
        Chassis.periodic();
    }
    
}
