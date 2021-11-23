// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.List;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Subsystems.Climbing;
import frc.robot.Subsystems.Drive;
import frc.robot.Subsystems.Drivepanel;
import frc.robot.Subsystems.Vision;
import frc.robot.Subsystems.Gamepad;
import frc.robot.Subsystems.Intake;
import frc.robot.Subsystems.Shooter;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private Drive mDrive;
  private Drivepanel mDrivepanel;
  private Gamepad mGamepad;
  private Intake mIntake;
  private Shooter mShooter;
  private Climbing mClimbing;
  private Vision mVision;
  private double wantedRPM = 3800;
  private double turnPID = 0.07;
  private double maxSpeed = 0;
  private double maxAcc = 0;
  private double prevTime = 0;
  private double prevLeftSpeed = 0;
  private double prevRightSpeed = 0;
  private double prevLeftDistance = 0;
  private double prevRightDistance = 0;
  private Timer timer;
  //private RSL rsl;
  private boolean shooterPressed;
  private boolean willShootBlind = false;
  private boolean distanceShoot = false;
  
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {

    SmartDashboard.putData("Auto choices", m_chooser);
    mDrive = Drive.getInstance();
    mDrivepanel = Drivepanel.getInstance();
    mGamepad = Gamepad.getInstance();
    mIntake = Intake.getInstance();
    mShooter = Shooter.getInstance();
    mClimbing = Climbing.getInstance();
    mVision = Vision.getInstance();
    mVision.setLedMode(0);
    mShooter.resetSensors();
    mShooter.resetPID();
    //mDrive.resetSensors(); 
    SmartDashboard.putNumber("Wanted RPM", wantedRPM);
    SmartDashboard.putNumber("Turn PID", turnPID);
    timer = new Timer();
    timer.reset();
    timer.start();
    prevTime = timer.get();
    shooterPressed = false;
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit(){

  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

        // Teleop: Robot drive
    double speed = mGamepad.getForward() - mGamepad.getReverse();
    double rotation;
    mDrive.robotDrive(speed, mGamepad.getSteering());
    if (Math.abs(mGamepad.getSensetiveSteering()) > 0.2){
      rotation = mGamepad.getSensetiveSteering() * 0.5;
    }
    else{
      rotation = mGamepad.getSteering() * 0.75;
    }
 
    mDrive.robotDrive(speed, rotation, 1);
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
    if(mDrivepanel.intakeIn() || mGamepad.getIntakeGamepad()){
      mIntake.intakeOn();
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

    // Teleop: Shooter Speed Up | Uses Shooter and Accelerator Wheel
    if (mDrivepanel.isShooterSpeedUpPressed()){
      shooterPressed = !shooterPressed;
    }

    if (willShootBlind){
      if (shooterPressed){
        mShooter.blindSpeedUp(0.8);
      }
      else{
        mShooter.blindSpeedOff();
      }

      if (mGamepad.getStartShooting()){
        mShooter.blindShoot();
      }
      else{
        mShooter.blindShootOff();
      }
    }

      else{
          mShooter.shooterStop();
      }

      if(mGamepad.getStartShooting()){
        mShooter.shoot(wantedRPM);
      }
      else{
          mShooter.feederOff();
      }
      else{
        if (shooterPressed){
          mShooter.shooterSpeedUp(wantedRPM);
        }
        else{
            mShooter.shooterStop();
        }
  
        if(mGamepad.getStartShooting()){
          mShooter.shoot(wantedRPM);
        }
        else{
            mShooter.feederOff();
        }
      }
    
  
    
    //Teleop: Shoot | Uses feeder and accelator
    if (mDrivepanel.autoAim()){
      if (mDrivepanel.autoAim()){
        double[] visionInfo = mVision.getInfo();
        if (visionInfo[0] > 0){
          
          if (Utils.tolerance(visionInfo[1], 0, 1)){
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

    if (mDrivepanel.climberUp()){
      mClimbing.releaseClimber();
    }
    else if (mDrivepanel.climberDown()){
      mClimbing.climb();
    }
    else if (mDrivepanel.climbHalt()){
      mClimbing.hang();
    }
    else{
      mClimbing.stopClimbMotor();
    }

    if (mDrivepanel.resetGyro()){
      mShooter.resetSensors();
      mShooter.resetPID();

    } 
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}
  //mGamepad.forceFeedback(0,0);

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
