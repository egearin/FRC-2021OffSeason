/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Auto.Action;

import frc.robot.Subsystems.Drive;

/**
 * Add your docs here.
 */
public class StopAction implements Action {

    public Drive mDrive;

    @Override
    public void done() {
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void start() {
        mDrive = Drive.getInstance();
        mDrive.stopDrive();
    }

    @Override
    public void update() {
        mDrive.stopDrive();
    }
}
