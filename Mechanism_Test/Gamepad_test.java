package org.firstinspires.ftc.teamcode.Mechanism_Test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Mechanism.Drivetrain;

public class Gamepad_test extends OpMode {

    Drivetrain drivetrain;

    @Override
    public void init() {
        drivetrain.Initiate();
    }

    @Override
    public void loop() {
        double x = gamepad1.left_stick_x;
        double y = -gamepad1.left_stick_y;
        double z = gamepad1.right_stick_x;

        drivetrain.Operate(x, y, z);
        drivetrain.Initiate();
    }


}
