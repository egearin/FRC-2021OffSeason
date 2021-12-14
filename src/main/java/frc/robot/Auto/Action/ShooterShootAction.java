// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Auto.Action;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Subsystems.Shooter;

/** Add your docs here. */
public class ShooterShootAction implements Action {

    Shooter mShooter;
    static Timer timer;
    double _time;
    double _speed;

    public ShooterShootAction(double speed, double time) {
        mShooter = Shooter.getInstance();
        timer = new Timer();
        _speed = speed;
        _time = time;
    }

    @Override
    public void start() {
        timer.reset();
        timer.start();
    }

    @Override
    public void update() {
        mShooter.shoot(_speed);
    }

    @Override
    public boolean isFinished() {
        return timer.get() > _time;
    }

    @Override
    public void done() {
        mShooter.feederStop();
        ShooterSpeedUpAction.declareFinished();
    }
}