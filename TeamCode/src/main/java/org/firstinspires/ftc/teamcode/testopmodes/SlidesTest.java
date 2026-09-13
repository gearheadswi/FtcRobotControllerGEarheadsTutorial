package org.firstinspires.ftc.teamcode.testopmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.hardware.Slides;

@TeleOp(name = "ServoTest", group = "Tele Op")
public class SlidesTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Slides slide = new Slides(hardwareMap, "name", 0.3);

        while (opModeIsActive()) {
            if (gamepad1.aWasPressed()){
                slide.MoveSlidesUp();
            }

            if (gamepad1.bWasPressed()){
                slide.MoveSlidesDown();
            }

            telemetry.addData("Speed", slide.Slide.getVelocity());
            telemetry.addData("Set Power", slide.Slide.getPower());
            telemetry.update();
        }
    }

}
