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

/**
 * Add your docs here.
 */
public class AdaptiveAuto extends AutoModeBase {

    @Override
    protected void routine() throws AutoModeEndedException {
        runAction(new SeriesAction(Arrays.asList(
                    new ParallelAction(Arrays.asList(
                        new ShooterSpeedUpAction(3500),
                        new SeriesAction(Arrays.asList(new PivotDownAction(2), new NoopAction())),
                        new SeriesAction(Arrays.asList(
                            new SeekTargetAndAimAction(0.7), 
                            new ParallelAction(Arrays.asList(
                                new ShooterShootAction(3500, 5),
                                new FindLocationAction()
                                ))
                            ))
                        )),
                    new BlindTrajectoryAction(),
                    new TurnPIDAction(180, 0.5),
                    new StopAllAction()
                    ))
            );
    }
}
