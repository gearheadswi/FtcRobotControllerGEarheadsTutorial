package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Hopper {
    private final Servo lifter_servo;
    private static final double lift_servo_up_position = 0.5;
    private static final double lift_servo_down_position = 0.5;

    public Hopper(HardwareMap hardwareMap) {
        lifter_servo = hardwareMap.get(Servo.class, "servo");
    }

    public void raise () {
        lifter_servo.setPosition(lift_servo_up_position);
    }

    public void drop () {
        lifter_servo.setPosition((lift_servo_down_position));
    }
}
