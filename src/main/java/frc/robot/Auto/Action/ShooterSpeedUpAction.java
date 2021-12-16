// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Auto.Action;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Subsystems.Shooter;

/** Add your docs here. */
public class ShooterSpeedUpAction implements Action {

    Shooter mShooter;
    double _speed;
    static boolean isFinished;

    public ShooterSpeedUpAction(double speed) {
        mShooter = Shooter.getInstance();
        _speed = speed;
    }

    @Override
    public void start() {
        isFinished = false;
    }

    @Override
    public void update() {
        mShooter.shooterSpeedUp(_speed);
        System.out.println("SpeedingUp...");
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public void done() {
        mShooter.shooterStop();
        System.out.println("FINISHED");
    }

    public static void declareFinished() {
        isFinished = true;
    }

}
