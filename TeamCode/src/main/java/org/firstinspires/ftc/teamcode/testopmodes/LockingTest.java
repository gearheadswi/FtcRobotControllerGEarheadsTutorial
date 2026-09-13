package org.firstinspires.ftc.teamcode.testopmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.hardware.Imu;
import org.firstinspires.ftc.teamcode.robot.Robot;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;

@TeleOp
public class LockingTest extends OpMode {
    private Robot robot;

    private DriveTrain driveTrain;
    private Imu imu;

    private double p = 1;
    private double d = 0;

    @Override
    public void init() {
        robot = new Robot(this, false);
        robot.initSubsystems();

        driveTrain = robot.driveTrain;
        imu = robot.imu;
    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {
        double turning = driveTrain.getPosHoldTurnPower(imu.getRobotHeading(), 0);
        driveTrain.moveRobot(
                0,
                0,
                turning,
                0,
                false
        );

        if (gamepad2.dpadUpWasPressed()){
            p += 1;
        }
        if (gamepad2.dpadDownWasPressed()){
            p -= 1;
        }

        if (gamepad2.dpadRightWasPressed()){
            d += 1;
        }
        if (gamepad2.dpadLeftWasPressed()){
            d -= 1;
        }

        driveTrain.pid.set_values(p, 0, d, 0);

        telemetry.addData("P", p);
        telemetry.addData("D", d);
        telemetry.update();
    }
}
