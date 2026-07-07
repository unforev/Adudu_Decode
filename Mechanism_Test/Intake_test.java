package org.firstinspires.ftc.teamcode.Mechanism_Test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Mechanism.Intake;

@TeleOp
public class Intake_test extends OpMode {

    private Intake Adudu_Intake;
    private Gamepad Adudu_Gamepad1;

    @Override
    public void init() {
        Adudu_Intake.Initiate();
        Adudu_Gamepad1.x = false;
    }

    @Override
    public void loop() {
        if(Adudu_Gamepad1.x)
        {
            Adudu_Intake.Operate();
            Adudu_Intake.stop();
            Adudu_Gamepad1.x = false;
        }
    }
}
