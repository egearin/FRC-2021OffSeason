// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Auto.Action;

import frc.robot.Subsystems.Drive;
import frc.robot.Subsystems.Intake;

/** Add your docs here. */
public class StopAllAction implements Action {

    Intake mIntake;
    Drive mDrive;

    public StopAllAction() {
        mIntake = Intake.getInstance();
        mDrive = Drive.getInstance();
    }

    @Override
    public void start() {
        System.out.println("Stopping all");

    }

    @Override
    public void update() {
        mIntake.intakeStop();
        mDrive.stopDrive();

    }

    @Override
    public boolean isFinished() {

        return false;
    }

    @Override
    public void done() {
        System.out.println("DONE");

    }

}
