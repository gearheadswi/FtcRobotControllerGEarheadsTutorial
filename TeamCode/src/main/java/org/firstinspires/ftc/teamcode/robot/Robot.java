package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.hardware.Imu;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.subsystems.Hopper;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

/**
 * Owns all hardware subsystems for the robot.
 * Constructor is lightweight (no hardware I/O); call {@link #initSubsystems()} inside
 * {@code OpMode.init()} to actually bring up hardware.
 */
public class Robot {

    /** Alliance color — used to mirror auto paths and field-relative logic. */
    public enum TeamName { Blue, Red }

    public TeamName teamName;
    public HardwareMap hardwareMap;
    public Imu imu;
    public DriveTrain driveTrain;
    public Launcher launcher;
    public Intake intake;
    public Hopper hopper;

    /**
     * @param opMode the active OpMode (provides the hardware map)
     * @param isRed  {@code true} for red alliance, {@code false} for blue
     */
    public Robot(OpMode opMode, boolean isRed) {
        hardwareMap = opMode.hardwareMap;
        teamName = isRed ? TeamName.Red : TeamName.Blue;
    }

    /** Instantiates all subsystems. Call once inside {@code OpMode.init()}. */
    public void initSubsystems() {
        imu = new Imu(hardwareMap);
        driveTrain = new DriveTrain(hardwareMap);
        intake = new Intake(hardwareMap);
        launcher = new Launcher(hardwareMap);
        hopper = new Hopper(hardwareMap);
    }
}
