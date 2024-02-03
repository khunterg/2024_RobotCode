package frc.robot.periods;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.molib.buttons.ButtonManager;
import frc.robot.Robot;
import frc.robot.subsystem.Chassis;
import frc.robot.subsystem.Runway;

@SuppressWarnings("unused")
public class Autonomous {
    /**
     * options for whether or not Celia is in the coding room
     */
    private enum IsCeliaHere{
        YES("ye"),
        NO("nah");
        
        private final String label;
        private IsCeliaHere(String label){
            this.label = label;
        }
        @Override public String toString(){
            return label;
        }
    }
    /**
     * Starting Position options for autonomous
     */
    private enum StartingPosition{
        CSPEAKER("Speaker Center"),
        LSPEAKER("Speaker Left"),
        RSPEAKER("Speaker Right");

        private final String label;
        private StartingPosition(String label){
            this.label = label;
        }
        @Override public String toString(){
            return label;
        }
    }
    /**
     * auto options
     */
    private enum Sequence{
        NOTHING("don't."){
            @Override public void run(){
                switch(mStage){
                    case 0:
                        Robot.disableSubsystems();
                        mStage++;
                        break;
                    default:
                        Robot.disableSubsystems();
                }
            }
        },
        WORLDS_WORST_FUNCTION(":P"){
        @Override public void run(){
            switch(mStage){
                case 0:
                    if(mSelectedIsCeliaHere == IsCeliaHere.YES)
                        System.out.println("Nothing works boo, we know why.");
                    else
                        System.out.println("Nothing works boo, we don't know why.");

                    mStage++; break;
                default:
                    //Nothing
                    Robot.disableSubsystems();
            }
        }
        },
        CELIA("cEliA"){
        @Override public void run(){
            switch(mStage){
                case 0:
                    Runway.enableReel();
                    tmrStageTimeOut.reset();
                    mStage++;
                    break;
                case 1:
                    if(tmrStageTimeOut.get()>1.0) mStage++;
                    break;
                case 2:
                    Runway.enableDirector();
                    tmrStageTimeOut.reset();
                    mStage++;
                    break;
                case 3:
                    if(tmrStageTimeOut.get()>1.0) mStage++;
                    break;
                case 4:
                    Runway.disable();
                    Chassis.setDrivePower(-1.0, -1.0);
                    tmrStageTimeOut.reset();
                    mStage++;
                    break;
                case 5:
                    if(tmrStageTimeOut.get()>0.5) mStage++;
                    break;
                case 6:
                    Chassis.disable();
                    mStage++;
                default:
                    Robot.disableSubsystems();
            }
        }
        },
        JOE("joE"){
        @Override public void run(){
            switch(mStage){
                case 0:
                    Chassis.setDrivePower(-1.0, -1.0);
                    tmrStageTimeOut.reset();
                    mStage++;
                    break;
                case 1:
                    if(tmrStageTimeOut.get()>1.0) mStage++;
                    break;
                case 2:
                    Chassis.disable();
                    break;
                default:
                    Robot.disableSubsystems();
            }
        }
        };
        public static final Timer tmrStageTimeOut = new Timer();
        private final String label;
        private static int mStage = 0;
        private Sequence(String label){
            this.label = label;
        }
        @Override public String toString(){
            return label;
        }

        public void init() {
            tmrStageTimeOut.start();
            tmrStageTimeOut.reset();
            mStage = 0;
        }
        public abstract void run();
    }

    private static SendableChooser<Sequence> chsSequence = new SendableChooser<Sequence>();
    private static Sequence mSelectedSequence;
    private static SendableChooser<StartingPosition> chsStartingPosition = new SendableChooser<StartingPosition>();
    private static StartingPosition mSelectedStartingPosition;
    private static SendableChooser<IsCeliaHere> chsIsCeliaHere = new SendableChooser<>();
    private static IsCeliaHere mSelectedIsCeliaHere;
    private static IsCeliaHere mIsCeliaHere;
    private static StartingPosition mStartingPosition;
    private static Sequence mSequence;
    private static Alliance mAlliance;

    private Autonomous(){}

    public static void init(){

        
        ButtonManager.clearFlags();

        mSelectedSequence = chsSequence.getSelected();
    mStartingPosition = chsStartingPosition.getSelected();
    mSelectedIsCeliaHere = chsIsCeliaHere.getSelected();

    mSelectedSequence.init();

}

    public static void initDashboard(){
        chsSequence.addOption(Sequence.NOTHING.label, Sequence.NOTHING);
        chsSequence.addOption(Sequence.CELIA.label, Sequence.CELIA);
        chsSequence.addOption(Sequence.WORLDS_WORST_FUNCTION.label, Sequence.WORLDS_WORST_FUNCTION);
        chsSequence.setDefaultOption(Sequence.NOTHING.label, Sequence.NOTHING);
        SmartDashboard.putData("Sequence", chsSequence);

        chsStartingPosition.addOption(StartingPosition.CSPEAKER.label, StartingPosition.CSPEAKER);
        chsStartingPosition.addOption(StartingPosition.LSPEAKER.label, StartingPosition.LSPEAKER);
        chsStartingPosition.addOption(StartingPosition.RSPEAKER.label, StartingPosition.RSPEAKER);
        chsStartingPosition.setDefaultOption(StartingPosition.CSPEAKER.label, StartingPosition.CSPEAKER);
        SmartDashboard.putData("Starting Position", chsStartingPosition);

        chsIsCeliaHere.addOption(IsCeliaHere.YES.label, IsCeliaHere.YES);
        chsIsCeliaHere.addOption(IsCeliaHere.NO.label, IsCeliaHere.NO);
        chsIsCeliaHere.setDefaultOption(IsCeliaHere.NO.label, IsCeliaHere.NO);
        SmartDashboard.putData("Is Celia Here?", chsIsCeliaHere);
    }

    public static void periodic(){
        mSelectedSequence.run();
        Chassis.periodic();
        Runway.periodic();
    }
}
