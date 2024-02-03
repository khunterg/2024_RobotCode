package frc.robot.subsystem;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.motorcontrol.Talon;
@SuppressWarnings("unused")
public class Hanger {

    private static TalonFX hanger_L= new TalonFX(8);
    private static TalonFX hanger_R = new TalonFX(9);
    
    private static double mLeftHangerPower = 0.0;
    private static double mRightHangerPower = 0.0;

    private Hanger(){}

    public static void setHangerPower(double power){
        mLeftHangerPower = power;
        mRightHangerPower = power;
    }    
    public static void raiseHanger(){
        setHangerPower(0.7);
    }
    public static void lowerHanger(){
        setHangerPower(-0.7);
    }
    public static void disable(){
        setHangerPower(0.0);
    }

    public static void init(){
        hanger_L.setInverted(true);
    }

    public static void initDashboard(){}

    public static void periodic(){
        hanger_L.set(mLeftHangerPower);
        hanger_R.set(mRightHangerPower);
    }
}
