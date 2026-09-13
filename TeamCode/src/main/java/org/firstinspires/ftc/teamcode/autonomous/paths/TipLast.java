package org.firstinspires.ftc.teamcode.autonomous.paths;

import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.autonomous.tuning.Constants;
import org.firstinspires.ftc.teamcode.robot.Robot;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.subsystems.FlowerDropper;
import org.firstinspires.ftc.teamcode.subsystems.Hopper;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Launcher;

@Autonomous(name = "TipLast", group = "Autonomous")
public class TipLast extends OpMode {
    public Follower follower;

    private Command autoSequence;

    private Robot robot;
    private DriveTrain driveTrain;
    private Launcher launcher;
    private Intake intake;
    private Hopper hopper;
    private FlowerDropper flowerDropper;

    private PoseFactory factory = PoseFactory.degrees();

    private Pose ShootFacing = factory.of(63, 9, 90);
    private Pose ShootFacingControl = factory.of(12, 13, 90);

    private Pose AltShooting = factory.of(63, 135, 270);
    private Pose AltShootingControl = factory.of(18, 130, 90);

    private Pose AltParkingPose = factory.of(9, 116, 0);
    private Pose ParkingPose = factory.of(9, 95, 0);

    private Path toPark = Paths.curve(AltShooting, AltShootingControl, AltParkingPose);

    @Override
    public void init() {
        Scheduler.reset();
        robot = new Robot(this, false);
        robot.initSubsystems();
        robot.intiSequencesAuto();

        driveTrain = robot.driveTrain;
        launcher = robot.launcher;
        intake = robot.intake;
        hopper = robot.hopper;
        flowerDropper = robot.flowerDropper;

        follower = Constants.create(hardwareMap);
        follower.setPose(AltShooting);
        follower.update();

        buildSequence();
    }

    @Override
    public void init_loop() {
        Scheduler.execute();
    }

    @Override
    public void start(){
        Scheduler.schedule(autoSequence);
        launcher.launchOn();
        intake.foward();
    }

    @Override
    public void loop() {
        Scheduler.execute();
        follower.update();
    }

    public void buildSequence() {
        autoSequence = sequential(
                waitMs(10 * 1000),
                hopper.QuadFlapSequence,
                follow(follower, toPark)
        );
    }
}