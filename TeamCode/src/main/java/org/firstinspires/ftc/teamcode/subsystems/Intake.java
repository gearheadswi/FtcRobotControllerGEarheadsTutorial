package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    private final DcMotorEx intake_motor;
    public int speed;

    public Intake(HardwareMap hardwareMap) {
        intake_motor = hardwareMap.get(DcMotorEx.class, "intake");
        intake_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void start_forward() {
        speed = 1;
        intake_motor.setPower(speed);

    }

    public void toggle_movement() {
        intake_motor.setPower(speed *= -1);
    }

    public void stop() {
        intake_motor.setPower(0);
        speed = 0;
    }
}
