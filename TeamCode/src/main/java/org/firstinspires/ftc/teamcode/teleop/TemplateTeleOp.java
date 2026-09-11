package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Imu;
import org.firstinspires.ftc.teamcode.robot.Robot;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;

/**
 * Starting point for a teleop OpMode. Copy this class and rename it for each driver-controlled mode.
 *
 * <p>Controls:
 * <ul>
 *   <li>Left stick — strafe / forward</li>
 *   <li>Right stick X — rotate</li>
 *   <li>Right trigger (hold) — slow/precision mode (1/3 speed)</li>
 *   <li>PS button — re-zero field-relative heading</li>
 * </ul>
 */
@TeleOp
public class TemplateTeleOp extends OpMode {
    private Robot robot;

    // Cached references to avoid repeated field lookups in the loop
    private Imu imu;
    private DriveTrain driveTrain;

    @Override
    public void init() {
        robot = new Robot(this, false);
        robot.initSubsystems();

        imu = robot.imu;
        driveTrain = robot.driveTrain;
    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {
        if (gamepad1.psWasPressed()) {
            imu.setInitialHeading();
        }

        driveTrain.moveRobot(
                gamepad1.left_stick_x,
                gamepad1.left_stick_y,
                gamepad1.right_stick_x,
                imu.getRobotHeading(),
                gamepad1.right_trigger < 0.1  // false (slow mode) when trigger is held
        );
    }
}
