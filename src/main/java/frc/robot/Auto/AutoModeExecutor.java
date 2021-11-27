/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.Auto;

import frc.robot.Auto.Modes.AutoModeBase;

/**
 * This class selects, runs, and (if necessary) stops a specified autonomous mode.
 */
public class AutoModeExecutor {
    private static AutoModeExecutor mInstance = null;

    private AutoModeBase mAutoMode = null;
    private Thread mThread = null;

    public AutoModeExecutor() {}

    public static AutoModeExecutor getInstance() {
        if (mInstance == null) {
            mInstance = new AutoModeExecutor();
        }

        return mInstance;
    }

    /**
     * Method for Setting Auto Mode
     * @param new_auto_mode
     */
    public void setAutoMode(AutoModeBase new_auto_mode) {
        mAutoMode = new_auto_mode;
        mThread = new Thread(new Runnable(){
        
            @Override
            public void run() {
                if (mAutoMode != null) {
                    mAutoMode.run();
                }
            }
        });
    }

    /**
     * Starting Thread
     */
    public void start() {
        if (mThread != null) {
            mThread.start();
        }
    }

    /**
     * Check if Thread is running
     * @return
     */
    public boolean isStarted() {
        return mAutoMode != null && mAutoMode.isActive() && mThread != null && mThread.isAlive();
    }

    /**
     * Stopping Thread And Resetting Auto Mode
     */
    public void reset() {
        if (isStarted()) {
            stop();
        }

        mAutoMode = null;
    }

    /**
     * Stopping Auto Mode
     */
    public void stop() {
        if (mAutoMode != null) {
            mAutoMode.stop();
        }
        mThread = null;
    }

    /**
     * Returns current auto mode
     * @return
     */
    public AutoModeBase getAutoMode() {
        return mAutoMode;
    }

    /**
     * Check if current auto mode is interrupted
     * @return
     */
    public boolean isInterrupted() {
        if (mAutoMode == null) {
            return false;
        }
        return mAutoMode.getIsInterrupted();
    }

    /**
     * Interrupt current auto mode
     */
    public void interrupt() {
        if (mAutoMode == null) {
            return;
        }
        mAutoMode.interrupt();
    }

    /**
     * Resume Auto Mode
     */
    public void resume() {
        if (mAutoMode == null) {
            return;
        }
        mAutoMode.resume();
    }
}