package org.firstinspires.ftc.teamcode.testopmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Imu;
import org.firstinspires.ftc.teamcode.robot.Robot;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;

@TeleOp
public class LauncherTest extends OpMode {
    private Robot robot;

    private Launcher launcher;

    private double p = 500;
    private double f = 20;

    private double speed = 0;

    @Override
    public void init() {
        robot = new Robot(this, false);
        robot.initSubsystems();

        launcher = robot.launcher;
    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {
        launcher.setPID(p, f);
        launcher.setTargetSpeed(speed);

        if (gamepad1.aWasPressed()){
            launcher.launchOn();
        }
        if (gamepad1.bWasPressed()){
            launcher.launchOff();
        }

        if (gamepad1.dpadUpWasPressed()){
            speed += 100;
        }
        if (gamepad1.dpadDownWasPressed()){
            speed -= 100;
        }

        if (gamepad2.dpadUpWasPressed()){
            p += 25;
        }
        if (gamepad2.dpadDownWasPressed()){
            p -= 25;
        }

        if (gamepad2.dpadRightWasPressed()){
            f += 5;
        }
        if (gamepad2.dpadLeftWasPressed()){
            f -= 5;
        }

        telemetry.addData("P", p);
        telemetry.addData("F", f);
        telemetry.addData("Speed", speed);
    }
}
