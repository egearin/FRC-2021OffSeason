// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Auto.Action;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Subsystems.Shooter;

/** Add your docs here. */
public class BlindShootAction implements Action {
    
    Shooter mShooter;
    static Timer timer;
    double prevTime;
    double _feedtime;
    int shootedBalls;
    double _timeOffset;
    static boolean canShoot;

    public BlindShootAction(double feedTime, double timeOffset){
        mShooter = Shooter.getInstance();
        timer = new Timer();
        _feedtime = feedTime;
        _timeOffset = timeOffset;
    }
    @Override
    public void start() {
        timer.reset();
        timer.start();
        shootedBalls = 0;
        prevTime = -1;
        canShoot = false;
    }

    @Override
    public void update() {
        if (timer.get() > shootedBalls*_timeOffset){
            if (prevTime == -1){
                prevTime = timer.get();
            }
            else if ((timer.get() - prevTime) < _feedtime){
                mShooter.blindShoot();
            }
            else{
                mShooter.blindShootOff();
                prevTime = -1;
                shootedBalls++;
            }
        }
    }

    @Override
    public boolean isFinished() {
        return shootedBalls >= 3;
    }

    @Override
    public void done() {
        mShooter.blindShootOff();
        BlindSpeedUpAction.declareFinished();
    }
}