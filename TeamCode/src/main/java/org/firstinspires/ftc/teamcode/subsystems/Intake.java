package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    private final DcMotorEx intake_motor;

    public Intake(HardwareMap hardwareMap) {
        intake_motor = hardwareMap.get(DcMotorEx.class, "intake");
        intake_motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    public void foward(){
        intake_motor.setPower(1);
    }

    public void reverse(){
        intake_motor.setPower(-1);
    }

    public void stop() {
        intake_motor.setPower(0);
    }
}
