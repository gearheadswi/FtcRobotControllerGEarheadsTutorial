package org.firstinspires.ftc.teamcode.hardware;

import com.pedropathing.math.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;

/**
 * Wrapper around LimeLight3A class that allows for easy use of limelight
 * Call init after constructing to enable limelight
 */
public class LimeLightCamera {
    private Limelight3A limelight;

    /** Forced delay between position updates returned from updateGlobalPosition */
    private static final double position_update_delay = 1000;
    private double last_global_position_update = 0;

    public LimeLightCamera(Limelight3A limelight) {
        this.limelight = limelight;
    }

    /**
     * Enables the limelight, limelight doesn't work if not called
     * Can call during init
     */
    public void init(){
        limelight.pipelineSwitch(0);
        limelight.setPollRateHz(100);
        limelight.start();
    }

    /**
     * Prints position data utilizing current visual data to supplied telemetry
     * Includes MT1 and MT2 position algorithms
     * @param telemetry
     */
    public void printApriltagPositionInfo(Telemetry telemetry){
        LLResult result = limelight.getLatestResult();
        if (result == null){
            telemetry.addLine("Result is null");
            return;
        }
        if (!result.isValid()){
            telemetry.addLine("Result is not valid");
            return;
        }
        Pose3D pose = result.getBotpose();
        telemetry.addLine("Botpose");
        telemetry.addData("Pose", pose.toString());

        pose = result.getBotpose_MT2();
        telemetry.addLine("Botpose MT2");
        telemetry.addData("Pose", pose.toString());
    }

    public void printIdList(Telemetry telemetry){
        LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid()){
            List<LLResultTypes.FiducialResult> fiducials = result.getFiducialResults();
            for (LLResultTypes.FiducialResult fiducial : fiducials) {
                int id = fiducial.getFiducialId(); // The ID number of the fiducial
                Pose3D pose = fiducial.getRobotPoseTargetSpace();
                telemetry.addData("ID", id);
                telemetry.addData("Position", pose.getPosition().toString());
            }
        }
    }

    /**
     * Periodically returns position updates according to position_update_delay
     * Useful for updating odometry position
     * Returns null if there is no tags detected or it hasn't been long enough since last update
     * Aligned with pedro coordinate system
     * @return
     */
    public Pose updateGlobalPosition(){
        LLResult result = limelight.getLatestResult();
        if (result != null && result.getStaleness() < 100 && result.isValid() && result.getBotposeTagCount() >= 1) {
            Pose recived_pose = getFieldPosition();
            if ((recived_pose.x() == 72 && recived_pose.y() == 72) || (recived_pose.x() == 0 && recived_pose.y() == 0)){
                return null;
            }
            if (System.currentTimeMillis() - last_global_position_update < position_update_delay){
                return null;
            }
            last_global_position_update = System.currentTimeMillis();
            return recived_pose;
        }
        return null;
    }

    /**
     * Returns current field position using the pedropathing coordinate system
     * May return (0, 0) or (72, 72) when unable to see any tags
     * @return Position
     */
    public Pose getFieldPosition(){
        LLResult result = limelight.getLatestResult();

        Pose pose = new Pose(0, 0, 0);

        if (result != null && result.isValid()) {
            Pose3D pos_3d = limelight.getLatestResult().getBotpose_MT2();
            if (pos_3d != null) {
                pose = new Pose(
                        pos_3d.getPosition().y * 39.3701 + 72,
                        -(pos_3d.getPosition().x * 39.3701 - 72),
                        pos_3d.getOrientation().getYaw(AngleUnit.RADIANS)
                );
            }
        }

        return pose;
    }

    /**
     * Update robot orientation, required if using MT2 algorithm for positioning
     * Zero degree should be aligned with Pedro coordinate system
     * @param field_yaw (degrees)
     */
    public void updateRobotOrientation(double field_yaw){
        double degrees = field_yaw + 90;
        limelight.updateRobotOrientation(degrees);
    }
}
