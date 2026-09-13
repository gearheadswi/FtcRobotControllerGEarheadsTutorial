package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.ivy.Command;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import static com.pedropathing.ivy.commands.Commands.instant;
import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.groups.Groups.repeat;
import static com.pedropathing.ivy.groups.Groups.sequential;

import org.firstinspires.ftc.teamcode.hardware.Claws;
import org.firstinspires.ftc.teamcode.hardware.Slides;

public class FlowerDropper {

    private Slides elevator;
    private Claws claw;
    private Servo loweringServo;

    private static final double downServoPos = 0;

    public FlowerDropper(HardwareMap hardwareMap){
        elevator = new Slides(hardwareMap, "name", 0.3);
        loweringServo = hardwareMap.get(Servo.class, "name");
        claw = new Claws(hardwareMap);
    }

    public Command Grab = claw.CloseCommand;
    public Command Up = elevator.UpCommand;
    public Command Release = claw.OpenCommand;
    public Command Down = elevator.DownCommand;

    public Command InitSequenceCommand = sequential(
            instant(() -> {
                loweringServo.setPosition(downServoPos);
            }),
            Release
    );
}
