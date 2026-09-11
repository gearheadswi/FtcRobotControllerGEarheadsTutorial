package org.firstinspires.ftc.teamcode;

import com.pedropathing.math.Pose;

/**
 * Static bridge for passing the robot's final autonomous pose into teleop.
 * FTC OpModes are separate instances, so statics are the only way to carry
 * state across the auto → teleop boundary within a single power cycle.
 */
public class ValueStorage {

    /** Set to {@code true} at the end of auto so teleop knows to use {@link #auto_pose}. */
    public static boolean fromAuto = false;

    /** Final pose (inches / radians) written by auto and read by teleop to seed the localizer. */
    public static Pose auto_pose = new Pose(0, 0, 0);
}
