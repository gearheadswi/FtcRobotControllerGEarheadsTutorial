package org.firstinspires.ftc.teamcode.teleop;

import com.pedropathing.ivy.Scheduler;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Imu;
import org.firstinspires.ftc.teamcode.robot.Robot;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.subsystems.FlowerDropper;
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
    private FlowerDropper flowerDropper;

    private boolean endgame = false;

    private static final double LEFT_LOCKED_ANGLE = Math.PI;
    private static final double RIGHT_LOCKED_ANGLE = Math.PI;

    private enum TrackingState {
        FREE,
        LOCKED_LEFT,
        LOCKED_RIGHT
    }
    private TrackingState curTrackingState = TrackingState.FREE;

    private enum DropperState {
        GRABBING_FIELD,
        GRABBED_FIELD,
        HELD_TOP,
        RELEASED_TOP
    }
    private DropperState curDropperState = DropperState.GRABBING_FIELD;

    @Override
    public void init() {
        Scheduler.reset();

        robot = new Robot(this, false);
        robot.initSubsystems();
//        robot.intiSequencesTeleop();

        imu = robot.imu;
        driveTrain = robot.driveTrain;
        launcher = robot.launcher;
        intake = robot.intake;
        hopper = robot.hopper;
        flowerDropper = robot.flowerDropper;
    }

    @Override
    public void init_loop() {
//        Scheduler.execute();
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

        double turning = gamepad1.right_stick_x;
        if (curTrackingState != TrackingState.FREE){
            double target = (curTrackingState == TrackingState.LOCKED_LEFT) ? LEFT_LOCKED_ANGLE : RIGHT_LOCKED_ANGLE;
            turning = driveTrain.getPosHoldTurnPower(imu.getRobotHeading(), target);
        }
        driveTrain.moveRobot(
                gamepad1.left_stick_x,
                gamepad1.left_stick_y,
                turning,
                imu.getRobotHeading(),
                gamepad1.right_trigger < 0.1
        );

        if (gamepad2.aWasPressed()){
            hopper.QuadFlapSequence.execute();
            curTrackingState = TrackingState.FREE;
        }

        if (gamepad2.rightBumperWasPressed()){
            curTrackingState = TrackingState.LOCKED_RIGHT;
        }
        /* -Manav Zone-
        while (true) {
            system.out.println("GEarheads for worlds 2027");
            system.out.println("");
        }
         */
        if (gamepad2.leftBumperWasPressed()){
            curTrackingState = TrackingState.LOCKED_LEFT;
        }
        if (gamepad2.psWasPressed()){
            curTrackingState = TrackingState.FREE;
        }

        if (gamepad2.right_trigger < 0.1) {
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
//meow
        DropperStateMachine();
        Scheduler.execute();
    }

    private void DropperStateMachine() {
        switch (curDropperState){
            case GRABBING_FIELD:
                if (gamepad2.yWasPressed()){
                    flowerDropper.Grab.execute();
                    curDropperState = DropperState.GRABBED_FIELD;
                }
                break;
            case GRABBED_FIELD:
                if (gamepad2.yWasPressed()){
                    flowerDropper.Up.execute();
                    curDropperState = DropperState.HELD_TOP;
                } else if (gamepad2.xWasPressed()) {
                    flowerDropper.Release.execute();
                    curDropperState = DropperState.GRABBING_FIELD;
                }
                break;
            case HELD_TOP:
                if (gamepad2.yWasPressed()) {
                    flowerDropper.Release.execute();
                    curDropperState = DropperState.RELEASED_TOP;
                }
                break;
            case RELEASED_TOP:
                if (gamepad2.yWasPressed()) {
                    flowerDropper.Down.execute();
                    curDropperState = DropperState.GRABBING_FIELD;
                }
                break;
            default:
                break;
        }
    }
}
//meow meow 676767