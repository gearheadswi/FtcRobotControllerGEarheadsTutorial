package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Thin wrapper around the Control Hub IMU that supports re-zeroing the heading mid-match.
 * Hardware config name: {@code "gyro"}. Hub orientation: logo LEFT, USB UP.
 */
public class Imu {

    /** Underlying FTC SDK IMU, exposed for direct access if needed. */
    public IMU imu;

    private double initialHeading;

    /**
     * @param hardwareMap used to look up {@code "gyro"} and initialize it with the hub's
     *                    physical mounting orientation
     */
    public Imu(HardwareMap hardwareMap) {
        imu = hardwareMap.get(IMU.class, "gyro");
        RevHubOrientationOnRobot orientationOnRobot =
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                        RevHubOrientationOnRobot.UsbFacingDirection.UP
                );

        com.qualcomm.robotcore.hardware.IMU.Parameters parameters = new com.qualcomm.robotcore.hardware.IMU.Parameters(orientationOnRobot);
        imu.initialize(parameters);
    }

    /**
     * @return yaw in radians, offset by the value captured at the last {@link #setInitialHeading()} call
     */
    public double getRobotHeading() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS) + initialHeading;
    }

    /**
     * Re-zeros the heading to the robot's current orientation.
     * Call this whenever the driver wants to reset field-relative "forward" (PS button in teleop).
     */
    public void setInitialHeading() {
        initialHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
    }
}
