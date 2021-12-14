// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Auto.Modes;

import java.util.Arrays;

import frc.robot.Auto.AutoModeEndedException;
import frc.robot.Auto.Action.*;

/** Add your docs here. */
public class FarRightAuto extends AutoModeBase {

    @Override
    protected void routine() throws AutoModeEndedException {
        runAction(new SeriesAction(Arrays.asList(
                    new ParallelAction(Arrays.asList(
                        new ShooterSpeedUpAction(3500),
                        new SeriesAction(Arrays.asList(new PivotDownAction(2), new NoopAction())),
                        new SeriesAction(Arrays.asList(
                            new SeekTargetAndAimAction(0.7), 
                            new ShooterShootAction(3500, 5)
                        ))
                    )),
                    new ParallelAction(Arrays.asList(new IntakeAction(3), new DriveAction(0.5,3))),
                    new StopAllAction()
                ))
        );
    }
    
}
