// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Auto.AutoModeExecutor;
import frc.robot.Auto.Modes.AdaptiveAuto;
import frc.robot.Auto.Modes.AutoModeBase;
import frc.robot.Auto.Modes.FarRightAuto;
import frc.robot.Auto.Modes.SimpleAuto;
import frc.robot.Subsystems.Climbing;
import frc.robot.Subsystems.Drive;
import frc.robot.Subsystems.Drivepanel;
import frc.robot.Subsystems.Vision;
import frc.robot.Subsystems.Gamepad;
import frc.robot.Subsystems.Intake;
import frc.robot.Subsystems.Shooter;
import frc.robot.Subsystems.Conveyor;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kAdaptiveAuto = "Adaptive Auto";
  private static final String kFarRight = "FarRight Auto";
  private static final String kSimpleAuto = "Simple Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  
  private AutoModeExecutor ame;
  private Drive mDrive;
  private Drivepanel mDrivepanel;
  private Gamepad mGamepad;
  private Intake mIntake;
  private static Shooter mShooter;
  private Climbing mClimbing;
  private Vision mVision;
  private double wantedRPM = 5000 ;
  private double turnPID = 0.07;   
  private Timer timer;
  private boolean shooterPressed;
  private Conveyor mConveyor; 
  private boolean willShootBlind = false;
  private static final int kProcessingMode = 1;
  private static final int kDrivingMode = 2;
  private int mSelectedMode;
  private final SendableChooser<Integer> m_cameraChooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Simple Auto", kSimpleAuto);
    m_chooser.addOption("Far Right", kFarRight);
    m_chooser.addOption("Adaptive Auto",kAdaptiveAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    mDrive = Drive.getInstance();
    mDrivepanel = Drivepanel.getInstance();
    mGamepad = Gamepad.getInstance();
    mIntake = Intake.getInstance();
    mShooter = Shooter.getInstance();
    mClimbing = Climbing.getInstance();
    mConveyor = Conveyor.getInstance();
    mVision = Vision.getInstance();
    mVision.setLedMode(0);
    SmartDashboard.putNumber("Wanted RPM for Speed Up ", 5000 );
    SmartDashboard.putNumber("Turn PID", turnPID);
    timer = new Timer();
    timer.reset();
    timer.start();
    ame = new AutoModeExecutor();
    m_cameraChooser.setDefaultOption("Processing Mode", kProcessingMode);
    m_cameraChooser.addOption("Driving Mode", kDrivingMode);
    
    SmartDashboard.putData("Limelight Mode", m_cameraChooser);
    mShooter.resetSensors();
    mShooter.resetPID();
    mDrive.resetSensors();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    //System.out.println("Shooter:" +mShooter.shooterEncoder.getRate());

    //System.out.println("Acc:"+mShooter.acceleratorEncoder.getRate());
    mSelectedMode = m_cameraChooser.getSelected();
    switch (mSelectedMode){
      case kProcessingMode:
        mVision.setCameraMode(false);
        break;
      case kDrivingMode:
        mVision.setCameraMode(true);
        break;
    }
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString line to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional comparisons to the
   * switch structure below with additional strings. If using the SendableChooser
   * make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    System.out.println("Auto selected: " + m_autoSelected);
    switch (m_autoSelected) {
      case kAdaptiveAuto:
        ame.setAutoMode(new AdaptiveAuto());
        break;
      case kSimpleAuto:
        ame.setAutoMode(new SimpleAuto());
        break; 
      case kFarRight:
        ame.setAutoMode(new FarRightAuto());
        break; 
      default:
        ame.setAutoMode(new SimpleAuto());
        break;
    }
    mShooter.resetSensors();
    mShooter.resetPID();
    mDrive.resetSensors();

    ame.start();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    if (ame != null){
      ame.stop();
      ame.reset();
    }
   
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    // Teleop: Robot drive
    double speed = mGamepad.getForward() - mGamepad.getReverse();
    double rotation;
    mDrive.robotDrive(speed, mGamepad.getSteering());

    /*if(mDrivePanel.driveLMain()){
      mDrive.driveLMaster.set(1);
    }else{mDrive.driveLMaster.set(0);}
    if(mDrivePanel.driveLTwo()){

    }*/
    /*if (Math.abs(mGamepad.getSensetiveSteering()) > 0.2){
      rotation = mGamepad.getSensetiveSteering() * 0.5;
    }
    else{
      rotation = mGamepad.getSteering() * 0.75;
    }*/
    //speed = Utils.map(speed, 0, 1, Constants.speedDeadZone, 1);
    /*rotation = Utils.map(rotation, 0, 1, Constants.rotationDeadZone, 1);*/
    rotation = mGamepad.getSteering() * 0.75;
    mDrive.robotDrive(speed, rotation, 1);
    /*mDrive.updateOdometry();
    NetworkTableEntry m_xEntry = NetworkTableInstance.getDefault().getTable("troubleshooting").getEntry("X");
    NetworkTableEntry m_yEntry = NetworkTableInstance.getDefault().getTable("troubleshooting").getEntry("Y");
    var translation = mDrive.odometry.getPoseMeters().getTranslation();
    m_xEntry.setNumber(translation.getX());
    m_yEntry.setNumber(translation.getY());*/
    mGamepad.forceFeedback(speed, rotation);
    // Teleop: Pivot
    if(mDrivepanel.pivotDown()){
      mIntake.pivotDown();
    }
    else if(mDrivepanel.pivotUp()){
      mIntake.pivotUp();
    }
    else{
      mIntake.pivotStall();
    }
    // Teleop: Gamepad Intake
    if (mGamepad.getIntakeGamepad()){
      mIntake.intakeOn();
    }
    else if (mGamepad.fifthBall()){
      mIntake.fifthBall();
    }
    else if(mGamepad.getReverseIntakeGamepad()){
      mIntake.intakeReverse();
    }
    else{
      // If manual controls is not commanding to intake
      boolean isNotIntakeUsed = false;
      //Teleop: Manual Left Roller
      if(mDrivepanel.leftRoller()){
        mIntake.leftRoller();
      }
      else if(mDrivepanel.leftRollerReverse()){
          mIntake.leftRollerReverse();
      }
      else{
          isNotIntakeUsed = true;
          mIntake.stopLeftRoller();
      }
      //Teleop: Manual Center Roller
      if(mDrivepanel.centerRoller()){
        mIntake.centerRoller();
      }
      else if(mDrivepanel.centerRollerReverse()){
          mIntake.centerRollerReverse();
      }
      else{
          isNotIntakeUsed = true;
          mIntake.stopCenterRoller();
      }
      //Teleop: Manual Right Roller
      if(mDrivepanel.rightRoller()){
          mIntake.rightRollerReverse();
      }
      else if(mDrivepanel.rightRollerReverse()){
          mIntake.rightRoller();
      }
      else{
          isNotIntakeUsed = true;
          mIntake.stopRightRoller();
      }
      // If intake is not used
      if(isNotIntakeUsed){
        mIntake.intakeStop();
      }
    }

    /*if(mDrivePanel.shooterSpeedUp()){
        mShooter.blindSpeedUp(1);
    }
    else{
        mShooter.shooterStop();
    }*/
    //Teleop: Shoot | Uses feeder and accelator
    /*if(mGamepad.getStartShooting()){
        mShooter.blindShoot();;
    }
    else{
        mShooter.feederOff();
    }*/
    // Teleop: Shooter Speed Up | Uses Shooter and Accelerator Wheel
    if (mDrivepanel.isShooterSpeedUpPressed()){
      shooterPressed = !shooterPressed;
    }

    if(!willShootBlind){
      if (shooterPressed){
        mShooter.shooterSpeedUp(wantedRPM);
      }

      else{
      mShooter.shooterStop();
      }
    
      if(mGamepad.getStartShooting()){
      mShooter.shoot(wantedRPM);
      
      }
     
      else if(mGamepad.shootWoConveyor()){
      mShooter.shootWoConveyor(wantedRPM);
      
      }

      else if (mDrivepanel.speedUpLevel1()){
        mShooter.shooterStop();
        wantedRPM = 3500;
    }
      else if (mDrivepanel.speedUpLevel2()){
        mShooter.shooterStop();
        wantedRPM = 4000;
    }
      else if (mDrivepanel.speedUpLevel3()){
        mShooter.shooterStop();
        wantedRPM = 5000;
    }
      else if (mGamepad.feederReverse()){
        mShooter.feederReverse();
      }
    
  }
    
    /*if(mDrivePanel.shooterSpeedUp()){
        mShooter.shooterSpeedUp(wantedRPM);
    }
    else{
        mShooter.shooterStop();
    }*/
    //Teleop: Shoot | Uses feeder and accelator
    

    if (mDrivepanel.climberUp()){
      mClimbing.releaseClimber();
    }
    else if (mDrivepanel.climberDown()){
      mClimbing.hang();
    }

    else if (mDrivepanel.releaseSlowly()){
      mClimbing.releaseSlowly();
    }

    else{
      mClimbing.stopClimbMotor();
    }

    if (mDrivepanel.resetGyro()){
      mShooter.resetSensors();
      mShooter.resetPID();
      mDrive.resetSensors();
      
    }

    if (mDrivepanel.autoAim()){
      if (mDrivepanel.autoAim()){
        double[] visionInfo = mVision.getInfo();
        if (visionInfo[0] > 0){
          
          if (Utils.tolerance(visionInfo[1], 0, 0.5)){
            double distance = mVision.estimateDistanceFromAngle(visionInfo[2]);
            System.out.println("Distance is " + distance + "m");
          }
          else{
            double arcadeRotation = mDrive.turnPID(visionInfo[1]);
            System.out.println("Arcade is " + arcadeRotation);

            
          }
        }
      }
    }
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {
  
  // mGamepad.forceFeedback(0,0);
  if (ame != null){
    ame.stop();
    ame.reset();
  }
}
  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {
  }

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {
    timer.reset();
    timer.start();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
    // if(mGamepad.shooterTest())
    mShooter.shooterSpeedUp(100);
    
  }
}
