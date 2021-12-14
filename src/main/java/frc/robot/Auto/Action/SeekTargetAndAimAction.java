// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Auto.Action;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Utils;
import frc.robot.Subsystems.Drive;
import frc.robot.Subsystems.Vision;

/** Add your docs here. */
public class SeekTargetAndAimAction implements Action {

    Vision mVision;
    Drive mDrive;
    boolean aimedToTarget = false;
    Timer timer;
    double _rotation_speed;

    public SeekTargetAndAimAction(double rotation_speed) {
        mVision = Vision.getInstance();
        mDrive = Drive.getInstance();
        timer = new Timer();
        _rotation_speed = rotation_speed;
    }

    @Override
    public void done() {
        mDrive.stopDrive();
    }

    @Override
    public boolean isFinished() {
        return aimedToTarget && (timer.get() > 3);
    }

    @Override
    public void start() {
        timer.reset();
        timer.start();
    }

    @Override
    public void update() {
        double[] visionInfo = mVision.getInfo();
        if (visionInfo[0] == 0.0) {
            mDrive.robotDrive(0, _rotation_speed);
        } else {
            if (Utils.tolerance(visionInfo[1], 0, 0.5)) {
                aimedToTarget = true;
            } else {
                mDrive.turnPID(visionInfo[1]);
            }
        }
    }
}
