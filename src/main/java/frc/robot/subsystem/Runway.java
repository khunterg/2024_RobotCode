package frc.robot.subsystem;

import com.ctre.phoenix6.hardware.TalonFX;

public class Runway {
    private static TalonFX mtrDirector = new TalonFX(5);
    private static TalonFX mtrReel_T = new TalonFX(6);
    private static TalonFX mtrReel_B = new TalonFX(7);

    private static double mTopReelPower = 0.0;
    private static double mBottomReelPower = 0.0;
    private static double mDirectorPower = 0.0;
    /**
     * constructor for the runway
     */
    private Runway(){

    }
    /**
     * disables entire Runway, no more shashaying
     */
    public static void disable(){
        disableDirector();
        disableReel();
    }
    /**
     * motor configuration
     */
    public static void init(){
        mtrReel_T.setInverted(true);
        mtrReel_B.setInverted(false);
        mtrDirector.setInverted(false);
    }
    public static void initDashboard(){

    }
    /**
     * sets the top and botom reel power to 0
     */
    public static void disableReel(){
        setReelPower(0.0, 0.0);
    }
    /**
     * enables the reel
     */
    public static void enableReel(){
        setReelPower(0.75, 0.85);
    }
    /**
     * reverses the reel
     */
    public static void reverseReel(){
        setReelPower(-0.25, -0.25);
    }
    /**
     * sets the reel power
     * @param topPower [-1.0 to 1.0]
     * @param bottomPower [-1.0 to 1.0]
     */
    public static void setReelPower(double topPower, double bottomPower){
        mTopReelPower = topPower;
        mBottomReelPower = bottomPower;
    }
    /**
     * disables the director
     */
    public static void disableDirector(){
        setDirectorPower(0.0);
    }
    public static void enableDirector(){
        setDirectorPower(0.25);
    }
    public static void reverseDirector(){
        setDirectorPower(-0.25);
    }
    public static void setDirectorPower(double directorPower){
        mDirectorPower = directorPower;
    }
    public static void periodic(){
        mtrReel_T.set(mTopReelPower);
        mtrReel_B.set(mBottomReelPower);
        mtrDirector.set(mDirectorPower);
    }
}
