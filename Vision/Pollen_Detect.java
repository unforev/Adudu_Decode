package org.firstinspires.ftc.teamcode.Vision;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.List;

@TeleOp(name = "Pollen Center")
public class Pollen_Detect extends LinearOpMode {
    // Khai báo Limelight
    private Limelight3A limelight;

    @Override
    public void runOpMode() {
        // Lấy Limelight
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        // Chọn pipeline Neural Detector
        limelight.pipelineSwitch(0);
        // Bật camera
        limelight.start();
        telemetry.addLine("Limelight Ready!");
        telemetry.update();
        // chờ hoạt động
        waitForStart();
        while (opModeIsActive()) {
            LLResult result = limelight.getLatestResult();

            if (result != null && result.isValid()) {
                List<LLResultTypes.DetectorResult> detectorList = result.getDetectorResults();
                boolean found = false;
                // Tìm đối tượng là bóng pollen
                for (LLResultTypes.DetectorResult object : detectorList) {
                    if (object.getClassName().equals("pollen")) {
                        double centerX = object.getTargetXPixels();
                        double centerY = object.getTargetYPixels();
                        telemetry.addLine("Pollen Found!");
                        telemetry.addData("Center X", centerX);
                        telemetry.addData("Center Y", centerY);

                        found = true;
                        break;
                    }
                }

                if (!found) {
                    telemetry.addLine("khong thay pollen");
                }

                telemetry.update();
            }
            else {
                telemetry.addLine("ko co du lieu cua limelight");
                telemetry.update();
            }
        }
        limelight.stop();
    }
}
