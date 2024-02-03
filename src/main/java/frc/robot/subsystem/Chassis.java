package frc.robot.subsystem;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.networktables.NetworkTable;
import frc.molib.PIDController;
import frc.molib.buttons.ButtonManager;
import frc.molib.dashboard.Entry;
import frc.robot.Robot;

@SuppressWarnings("all")
public class Chassis {//make the Chassis class

    private static NetworkTable tblChassis = Robot.tblSubsystem.getSubTable("Chassis");
    private static Entry<Double> entDrive_Distance_L1 = new Entry<Double>(tblChassis, "Drive Distance - L1");
    private static Entry<Double> entDrive_Distance_L2 = new Entry<Double>(tblChassis, "Drive Distance - L2");
    private static Entry<Double> entDrive_Distance_R1 = new Entry<Double>(tblChassis, "Drive Distance - R1");
    private static Entry<Double> entDrive_Distance_R2 = new Entry<Double>(tblChassis, "Drive Distance - R2");

    private static Entry<Boolean> entDrive_AtDistance = new Entry<Boolean>(tblChassis, "Is At Distance");
    

    private static TalonFX mtrDrive_L1 = new TalonFX(1);//make the motors
    private static TalonFX mtrDrive_L2 = new TalonFX(2);
    private static TalonFX mtrDrive_R1 = new TalonFX(3);
    private static TalonFX mtrDrive_R2 = new TalonFX(4);

    private static PIDController pidDrive_Distance = new PIDController(0.03, 0.0, 0.0);

    private static double mLeftDrivePower = 0.0;//private variables within the class for left and right power
    private static double mRightDrivePower = 0.0;

    private static double mDriveGearRatio = 1.0/6.28;

    private Chassis(){}//constructor

    public static void disable(){
        setDrivePower(0.0, 0.0);
    }

    public static void init() {//happens once, in this case it happens once the robot turns on
        mtrDrive_L1.setInverted(true);//inverts the left two motors
        mtrDrive_L2.setInverted(true);
        mtrDrive_R1.setInverted(false);
        mtrDrive_R2.setInverted(false);
        mtrDrive_L2.setControl(new Follower(mtrDrive_L1.getDeviceID(), false));//makes left 2 follow left 1
        mtrDrive_R2.setControl(new Follower(mtrDrive_R1.getDeviceID(), false));//ditto but right

        pidDrive_Distance.configOutputRange(-0.25, 0.25);
        pidDrive_Distance.configAtSetpointTime(0.25);
    }

    public static void initDashboard(){

    }

    public static void setDrivePower(double leftPower, double rightPower){//function to set drive power via the function's inputs
        mLeftDrivePower = leftPower;
        mRightDrivePower= rightPower;
    }

    public static double getDriveDistance(){
        return ((mtrDrive_L1.getPosition().getValue()+mtrDrive_L2.getPosition().getValue()+mtrDrive_R1.getPosition().getValue()+mtrDrive_R2.getPosition().getValue())/4.0)*(mDriveGearRatio)*(4.0*Math.PI);
    }

    public static void disablePIDs(){
        disableDistancePID();
    }
    public static void disableDistancePID(){
        pidDrive_Distance.disable();
    }
    public static void goToDistance(double distance){
        pidDrive_Distance.setSetpoint(distance);
        pidDrive_Distance.enable();
    }
    public static boolean isAtDistance(){
        return pidDrive_Distance.atSetpoint();
    }
    public static void resetDistance(){
        mtrDrive_L1.setPosition(0.0);
        mtrDrive_L2.setPosition(0.0);
        mtrDrive_R1.setPosition(0.0);
        mtrDrive_R2.setPosition(0.0);
    }

    public static void periodic(){//runs on loop
        if(pidDrive_Distance.isEnabled()){
            setDrivePower(pidDrive_Distance.calculate(getDriveDistance()), pidDrive_Distance.calculate(getDriveDistance()));
        }

        entDrive_Distance_L1.set(mtrDrive_L1.getPosition().getValue() * mDriveGearRatio * (4.0 * Math.PI));
        entDrive_Distance_L2.set(mtrDrive_L2.getPosition().getValue() * mDriveGearRatio * (4.0 * Math.PI));
        entDrive_Distance_R1.set(mtrDrive_R1.getPosition().getValue() * mDriveGearRatio * (4.0 * Math.PI));
        entDrive_Distance_R2.set(mtrDrive_R2.getPosition().getValue() * mDriveGearRatio * (4.0 * Math.PI));

        entDrive_AtDistance.set(pidDrive_Distance.atSetpoint());

        mtrDrive_L1.set(mLeftDrivePower);//actually sets the motor's power
        mtrDrive_R1.set(mRightDrivePower);
    }
}
