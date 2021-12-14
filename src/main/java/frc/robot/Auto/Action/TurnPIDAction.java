// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Auto.Action;

import frc.robot.Utils;
import frc.robot.Subsystems.Drive;

/** Add your docs here. */
public class TurnPIDAction implements Action {

    Drive mDrive;
    double _desiredAngle;
    double _tolerance;

    public TurnPIDAction(double desiredAngle, double tolerance) {
        mDrive = Drive.getInstance();
        _desiredAngle = desiredAngle;
        _tolerance = tolerance;
    }

    @Override
    public void done() {
        mDrive.stopDrive();
    }

    @Override
    public boolean isFinished() {
        return Utils.tolerance(mDrive.getGyroAngle(), _desiredAngle, 1);
    }

    @Override
    public void start() {
    }

    @Override
    public void update() {
        mDrive.turnPID(_desiredAngle);
    }

}
