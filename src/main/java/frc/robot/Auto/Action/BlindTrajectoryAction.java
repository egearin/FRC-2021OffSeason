// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Auto.Action;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.Subsystems.Drive;

/** Add your docs here. */
public class BlindTrajectoryAction implements Action {

    Drive mDrive;
    Timer timer;
    double timeToGo;

    public BlindTrajectoryAction(){
        mDrive = Drive.getInstance();
        timer = new Timer();
    }
    @Override
    public void done() {

    }

    @Override
    public boolean isFinished() {
        return timer.get() > timeToGo;
    }

    @Override
    public void start() {
        timer.reset();
        timer.start();
        timeToGo = Drive.distanceToGoal / Constants.blindmps;
    }

    @Override
    public void update() {
        mDrive.robotDrive(Constants.blindtrajspeed, 0);
    }
    
}
