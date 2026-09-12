package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

public class Launcher {
    private final DcMotorEx launchMotor1;
    private boolean launching = false;
    private double launchSpeed = 1000;

    public Launcher(HardwareMap hardwareMap){
        launchMotor1 = hardwareMap.get(DcMotorEx.class, "launch1");

        launchMotor1.setDirection(DcMotorSimple.Direction.FORWARD);
        launchMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        launchMotor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        setPID(500, 20);
    }

    private void setMotorSpeed(double speed){
        launchMotor1.setVelocity(launchSpeed);
    }

    public void setPID(double p, double f){
        launchMotor1.setPIDFCoefficients(DcMotor.RunMode.RUN_WITHOUT_ENCODER, new PIDFCoefficients(
                p,0,0,f
        ));
    }

    public void launchOn(){
        if (!launching){
            setMotorSpeed(launchSpeed);
            launching = true;
        }
    }

    public void launchOff(){
        if (launching){
            setMotorSpeed(0);
            launching = false;
        }
    }

    public void setTargetSpeed(double speed){
        launchSpeed = speed;
    }
}
