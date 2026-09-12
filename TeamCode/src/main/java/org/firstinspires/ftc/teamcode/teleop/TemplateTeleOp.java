package org.firstinspires.ftc.teamcode.teleop;

import com.pedropathing.ivy.Scheduler;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Imu;
import org.firstinspires.ftc.teamcode.robot.Robot;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.subsystems.Hopper;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;

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

    private Imu imu;
    private DriveTrain driveTrain;
    private Launcher launcher;
    private Intake intake;
    private Hopper hopper;

    private boolean endgame = false;

    @Override
    public void init() {
        robot = new Robot(this, false);
        robot.initSubsystems();

        imu = robot.imu;
        driveTrain = robot.driveTrain;
        launcher = robot.launcher;
        intake = robot.intake;
        hopper = robot.hopper;
    }

    @Override
    public void start() {
        launcher.launchOn();
        intake.foward();
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
                gamepad1.right_trigger < 0.1
        );


        if (gamepad2.aWasPressed()){
            hopper.QuadFlapSequence.execute();
        }

        if (gamepad2.b) {
            intake.reverse();
        } else {
            intake.foward();
        }

        if (gamepad2.bWasPressed()) {
            endgame = !endgame;
            if (endgame){
                intake.stop();
                launcher.launchOff();
            } else {
                intake.foward();
                launcher.launchOn();
            }
        }
    }
}
