package org.firstinspires.ftc.teamcode.autonomous.tuning;

import com.pedropathing.tuning.autotune.Procedure;
import com.pedropathing.tuning.autotune.Tuner;

import org.firstinspires.ftc.teamcode.autonomous.tuning.procedures.MecanumTuner;
import org.firstinspires.ftc.teamcode.autonomous.tuning.procedures.PinpointTuner;

public class Tuning {
    @Tuner
    public static Procedure mecanumTuner(){
        return new MecanumTuner();
    }

    @Tuner
    public static Procedure pinpoint(){
        return new PinpointTuner();
    }
}
