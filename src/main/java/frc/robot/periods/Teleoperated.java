package frc.robot.periods;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.molib.buttons.Button;
import frc.molib.buttons.ButtonManager;
import frc.molib.hid.Joystick;
import frc.molib.hid.XboxController;
import frc.robot.subsystem.Chassis;
import frc.robot.subsystem.Hanger;
import frc.robot.subsystem.Runway;

@SuppressWarnings("unused")
/**
 * make telephone class
 */
public class Teleoperated {

    public static enum ZoomyPercentage{
        TORTOISE("Tortoise", .05),
        NORMAL("Normal", .70),
        FAST("Fast", .95);

        public final String label;
        public final double percentage;

        private ZoomyPercentage(String label, double percentage){
            this.label = label;
            this.percentage = percentage;
        }
    }
    private static SendableChooser<ZoomyPercentage> chsZoomyPercentage = new SendableChooser<ZoomyPercentage>();
    private static ZoomyPercentage mSelectedZoomyPercentage;
    /**
     * make controller, id 0
     */
   // private static XboxController ctlDrive = new XboxController(3);
    private static Joystick ctlDrive_L = new Joystick(0);
    private static Joystick ctlDrive_R = new Joystick(1);
    private static XboxController ctlOperator = new XboxController(2);

    private static final Button btnEnableReel = new Button(){
        @Override public boolean get() {return ctlOperator.getRightTrigger();}
    };
    private static final Button btnReveseReel = new Button(){
        @Override public boolean get() {return ctlOperator.getLeftTrigger();}
    };
    private static final Button btnEnableDirector = new Button(){
        @Override public boolean get() {return ctlDrive_R.getTrigger();}
    };
    private static final Button btnRaiseHanger = new Button(){
        @Override public boolean get() {return ctlOperator.getLeftStickButton();}
    };
    private static final Button btnLowerHanger = new Button(){
        @Override public boolean get() {return ctlOperator.getRightStickButton();}
    };
    /**
     * constructor
     */
    private Teleoperated(){}
    /**
     * init
     */
    
    public static void init(){
       mSelectedZoomyPercentage  = chsZoomyPercentage.getSelected();
       ButtonManager.clearFlags();
    }
    /**
     * very important variable, trust me.
     */
    private static void mIdkJoeEatsElbows(){}
    /**
     * dashboard
     */
    public static void initDashboard(){
        chsZoomyPercentage.addOption(ZoomyPercentage.TORTOISE.label, ZoomyPercentage.TORTOISE);
        chsZoomyPercentage.addOption(ZoomyPercentage.NORMAL.label, ZoomyPercentage.NORMAL);
        chsZoomyPercentage.addOption(ZoomyPercentage.FAST.label, ZoomyPercentage.FAST);

        chsZoomyPercentage.setDefaultOption(ZoomyPercentage.NORMAL.label, ZoomyPercentage.NORMAL);

        SmartDashboard.putData("Zoomy Percentage", chsZoomyPercentage);
    }
    /**
     * takes the function's variables and sets them to the motor powers
     *
     * @param leftPower [-1.0 to 1.0]
     * @param rightPower [-1.0 to 1.0]
     */
    private static void setTankDrive(double leftPower, double rightPower){
        Chassis.setDrivePower(leftPower, rightPower);
    }

    /**
     * math for arcade
     * @param throttle [-1.0 to 1.0] forward backward
     * @param steering [-1.0 to 1.0] left right
     */
    private static void setArcadeDrive(double throttle, double steering){
        setTankDrive(throttle + steering, throttle - steering);
    }
    /**
     * loops and sets the tank drive with left being the left joystick and right being the right joystick
     */
    public static void periodic(){
        setTankDrive(ctlDrive_L.getY()*mSelectedZoomyPercentage.percentage, ctlDrive_R.getY()*mSelectedZoomyPercentage.percentage);
        if(btnEnableReel.get()){
            Runway.enableReel();
        }else if(btnReveseReel.get()){
            Runway.reverseReel();
            Runway.reverseDirector();
        }else{
            Runway.disable();
        }
        if(btnEnableDirector.get()){
            Runway.enableDirector();
        }else{
            Runway.disableDirector();
        }
        if(btnLowerHanger.get()){
            Hanger.lowerHanger();
        }else if(btnRaiseHanger.get()){
            Hanger.raiseHanger();
        }else{
            Hanger.disable();
        }
        Chassis.periodic();
        Runway.periodic();
        Hanger.periodic();
    }
}
