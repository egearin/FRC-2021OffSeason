/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Auto.Modes;

import java.util.Arrays;

import frc.robot.Auto.AutoModeEndedException;
import frc.robot.Auto.Action.*;

/*
    * Shooter chracaterization
    * Drivepanel ShooterSpeedUp with Level
    * Subsystem Test:
        - Pigeon
        - Shooter
        - Climb
    * FarRight Auto:
        - Paralel Seek Target (turn to left) and ShooterSpeedUp
        - Paralel Shoot | Lower Pivot
        - Turn to 180 degree
        - Paralel Go Forward for x seconds | Start intake
        - Stop everything
    * Simple Auto Ready
    

*/
/**
 * Add your docs here.
 */
public class NoLimelightAuto extends AutoModeBase {

    @Override
    protected void routine() throws AutoModeEndedException {
        runAction(new SeriesAction(Arrays.asList(
                    new ParallelAction(Arrays.asList(
                        new ShooterSpeedUpAction(3500),
                        new ShooterShootAction(3500, 5)
                )),
                new SeriesAction(new DriveAction(-0.5,2), new StopAction()))));
            
    }
}