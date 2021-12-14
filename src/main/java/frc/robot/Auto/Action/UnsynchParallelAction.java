/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Auto.Action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Composite action, running all sub-actions at the same time All actions are
 * started then updated until all actions report being done.
 */
public class UnsynchParallelAction implements Action {
    private final ArrayList<Action> mActions;

    public UnsynchParallelAction(List<Action> actions) {
        mActions = new ArrayList<>(actions);
    }

    public UnsynchParallelAction(Action... actions) {
        this(Arrays.asList(actions));
    }

    @Override
    public void start() {
        mActions.forEach(Action::start);
    }

    @Override
    public void update() {
        for (Action currentAction : mActions) {
            if (!currentAction.isFinished()) {
                currentAction.update();
            } else {
                currentAction.done();
            }
        }
    }

    @Override
    public boolean isFinished() {
        for (Action action : mActions) {
            if (!action.isFinished()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void done() {
        mActions.forEach(Action::done);
    }
}