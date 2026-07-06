package org.firstinspires.ftc.teamcode.Vision;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.List;

@TeleOp(name = "Tube Center")
public class Tube_Detect extends LinearOpMode {

    // Khai báo camera Limelight 3A
    private Limelight3A limelight;

    @Override
    public void runOpMode() {
        // Lấy camera ở trong ổ
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        // chọn đầu luôn :))
        limelight.pipelineSwitch(0);
        // mở cam
        limelight.start();
        telemetry.addLine("Limelight Ready!");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {

            LLResult result = limelight.getLatestResult();

            if (result != null && result.isValid()) {

                List<LLResultTypes.DetectorResult> tubeList = result.getDetectorResults();

                if (!tubeList.isEmpty()) {

                    LLResultTypes.DetectorResult tube = tubeList.get(0);

                    telemetry.addData("Center X", tube.getTargetXPixels());
                    telemetry.addData("Center Y", tube.getTargetYPixels());
                }
            }
        }
    }
}