/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved. 
                       */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Auto.Action;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Subsystems.Drive;

/**
 * Add your docs here.
 */
public class DriveAction implements Action {
    public Drive mDrive;
    static Timer timer;
    double __time;
    double _speed;

    public DriveAction(double speed, double time) {
        mDrive = Drive.getInstance();
        timer = new Timer();
        __time = time;
        _speed = speed;
    }

    @Override
    public void done() {
        mDrive.stopDrive();

    }

    @Override
    public boolean isFinished() {
        return timer.get() >= __time;
    }

    @Override
    public void start() {
        timer.reset();
        timer.start();
    }

    @Override
    public void update() {
        mDrive.robotDrive(_speed, 0);
    }
}
