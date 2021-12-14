// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import frc.robot.Constants;
import frc.robot.Utils;

/**
 * Shooter Subsystem.
 */
public class Shooter {
    private static Shooter mInstance = new Shooter();

    public static Shooter getInstance() {
        return mInstance;
    }

    public Conveyor mConveyor;
    public VictorSP shooterWheel;
    public VictorSP acceleratorWheel;
    public VictorSP feederWheel;
    public Encoder acceleratorEncoder;
    public Encoder shooterEncoder;

    public double shooterRPM;
    public double accRPM;
    public double shooterPrevRate;
    public double accPrevRate;

    public Timer timer;
    public double prev_time = -1;

    public boolean firstTime = true;
    public boolean isReadyForSpeedUp = false;
    public boolean isReadyForShoot = false;

    public PIDController shooterPid;
    public PIDController acceleratorPid;
    public SimpleMotorFeedforward shooterFeedforward;
    public SimpleMotorFeedforward accFeedforward;


    private Shooter() {
        // double ball_radius = Constants.ballRadius;
        mConveyor = Conveyor.getInstance();
        shooterWheel = new VictorSP(Constants.shooterWheelMotorPort);
        acceleratorWheel = new VictorSP(Constants.acceleratorWheelMotorPort);
        feederWheel = new VictorSP(Constants.feederMotorPort);
        acceleratorEncoder= new Encoder(Constants.accEncPort1, Constants.accEncPort2, Constants.accEncDirection, EncodingType.k1X);
        shooterEncoder = new Encoder(Constants.shooterEncPort1, Constants.shooterEncPort2, Constants.shooterEncDirection, EncodingType.k1X);
        shooterEncoder.setSamplesToAverage(10);
        acceleratorEncoder.setSamplesToAverage(10);
        shooterEncoder.setDistancePerPulse(Constants.shooterEncDistancePerPulse);
        acceleratorEncoder.setDistancePerPulse(Constants.accEncDistancePerPulse);
        acceleratorWheel.setInverted(true);
        feederWheel.setInverted(false);
        shooterPid = new PIDController(Constants.kShooterP, Constants.kShooterI, Constants.kShooterD);
        acceleratorPid = new PIDController(Constants.kAccP, Constants.kAccI, Constants.kAccD);
        shooterFeedforward = new SimpleMotorFeedforward(Constants.kShooterS, Constants.kShooterV, Constants.kShooterA);
        accFeedforward = new SimpleMotorFeedforward(Constants.kAccS, Constants.kAccV, Constants.kAccA);
        timer = new Timer();
        timer.reset();
        timer.start();
        /*
         * shooterFeedforward.maxAchievableAcceleration(12, velocity)
         * accFeedforward.maxAchievableVelocity(12, acceleration)
         */
    }

    
        

    
    /*
     * //resets only sensors public void resetSensors(){ shooterEnc.reset();
     * accEnc.reset(); }
     * 
     * 
     * //resets PID values public void resetPID(){ shooterPid.reset();
     * acceleratorPid.reset(); }
     * 
     * /** Resets everything resettable
     */
    public void reset() {
        // resetPID();
        // resetSensors();
        System.out.println("Reset");
    }

    public double getShooterRPM() {
        shooterRPM = shooterEncoder.getRate() * 60;
        return shooterRPM;
    }

    public double getAccRPM() {
        accRPM = acceleratorEncoder.getRate() * 60;
        return accRPM;
    }

    // Feeder Wheel Codes
    public void feederOn() {
        feederWheel.set(1);
    }

    public void feederOff() {
        feederWheel.set(0);
    }

    public void feederReverse() {
        feederWheel.set(-1);
    }

    // public void calculateRoationalSpeed(){}

    /**
     * Check if is ready for shoot
     * 
     * @param desiredRate
     * @return
     */
    public boolean isReadyForShoot(double desiredRate) {
        if (Utils.tolerance(shooterEncoder.getRate(), desiredRate + 4, 3)
                && Utils.tolerance(acceleratorEncoder.getRate(), desiredRate, 7)) {
            System.out.println("Ready with acc rpm of " + getAccRPM());
            return true;
        } else {
            return false;
        }
    }

    public void shooterSpeedUp(double wantedRPM) {
        double curAccSpeed = acceleratorEncoder.getRate();
        double curShooterSpeed = shooterEncoder.getRate();
        double wantedRate = wantedRPM / 60;
        System.out.println("Shooter: " + curShooterSpeed);
        double shooterPower = shooterPid.calculate(curShooterSpeed, wantedRate);
        double acceleratorPower = acceleratorPid.calculate(curAccSpeed, wantedRate);
        shooterPower += shooterFeedforward.calculate(wantedRate);
        acceleratorPower += accFeedforward.calculate(wantedRate);
        shooterWheel.setVoltage(shooterPower);
        acceleratorWheel.setVoltage(acceleratorPower);
    }

    public void setShooterMotorSpeed(double speed) {
        shooterWheel.set(speed);
    }

    public void setAccMotorSpeed(double speed) {
        acceleratorWheel.set(speed);
    }

    public void shooterStop() {
        shooterWheel.set(0);
        acceleratorWheel.set(0);
        // resetPID();
    }

    public void feederStop() {
        feederWheel.set(0);
        mConveyor.conveyorStop();
    }

    public void shootWoConveyor(double wantedRPM){
        if (isReadyForShoot(wantedRPM / 60)) {
                feederWheel.set(1);
        } 
        else {
                feederStop();
        }
    }
    

    // don't forget to reset feederWheel motor value
    public void shoot(double wantedRPM) {
        if (isReadyForShoot(wantedRPM / 60)) {
            feederWheel.set(1);
            mConveyor.conveyorShoot();
        } 
        else {
            feederStop();
        }
    }

    public void blindSpeedUp(double speed) {
        setShooterMotorSpeed(speed);
        setAccMotorSpeed(speed);
    }

    public void blindSpeedOff() {
        setShooterMotorSpeed(0);
        setAccMotorSpeed(0);
    }

    public void blindShoot() {
        feederWheel.set(1);

        mConveyor.conveyorShoot();
    }

    public void blindShootOff() {
        feederOff();
        // mConveyor.conveyorStop();
    }
}
