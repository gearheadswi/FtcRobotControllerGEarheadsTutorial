package org.firstinspires.ftc.teamcode.hardware;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import static com.pedropathing.ivy.commands.Commands.instant;
import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.groups.Groups.repeat;
import static com.pedropathing.ivy.groups.Groups.sequential;

public class Claws {
    private final Servo Claws;
    public static final double Open = 0;
    public static final double Close = 0;
    public Claws(HardwareMap hardwareMap) {
        Claws = hardwareMap.get(Servo.class, "claws");
    }

    public void ClawsOpen() {
        Claws.setPosition(Open);
    }

    public void ClawsClose() {
        Claws.setPosition(Close);
    }

    public Command OpenCommand = sequential(
            instant(this::ClawsOpen),
            waitMs(500)
    );

    public Command CloseCommand = sequential(
            instant(this::ClawsClose),
            waitMs(500)
    );
}