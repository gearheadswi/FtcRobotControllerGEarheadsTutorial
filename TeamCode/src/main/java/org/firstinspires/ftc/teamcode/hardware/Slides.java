package org.firstinspires.ftc.teamcode.hardware;

import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.CommandBuilder;
import com.pedropathing.ivy.behaviors.BlockedBehavior;
import com.pedropathing.ivy.behaviors.EndCondition;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Slides {
    public final DcMotorEx Slide;

    private final double speed;
    private static final double minVelocity = 100;
    private static final long waitTime = 500;

    private long moveTime;

    public Slides(HardwareMap hardwareMap, String motorName, double speed) {
        this.speed = speed;
        Slide = hardwareMap.get(DcMotorEx.class, motorName);
        Slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void MoveSlidesUp() {
        Slide.setPower(speed);
        moveTime = System.currentTimeMillis();
    }

    public void MoveSlidesDown() {
        Slide.setPower(-speed);
        moveTime = System.currentTimeMillis();
    }

    public void UpdateSlides() {
        if (atPosition()) {
            Slide.setPower(0);
        }
    }

    private void Stop(){
        Slide.setPower(0);
    }

    private boolean atPosition(){
        return Math.abs(Slide.getVelocity()) < minVelocity && System.currentTimeMillis() > moveTime+waitTime;
    }

    public Command DownCommand = new CommandBuilder()
            .setStart(this::MoveSlidesDown)
            .setDone(this::atPosition)
            .setEnd(endCondition -> {
                Stop();
            }).requiring(this).setBlockedBehavior(BlockedBehavior.CANCEL);

    public Command UpCommand = new CommandBuilder()
            .setStart(this::MoveSlidesUp)
            .setDone(this::atPosition)
            .setEnd(endCondition -> {
                Stop();
            }).requiring(this).setBlockedBehavior(BlockedBehavior.CANCEL);
}