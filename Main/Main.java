package org.firstinspires.ftc.teamcode.Main;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Adudu.Adudu;

@TeleOp
public class Main extends LinearOpMode {

    Adudu adudu;

    @Override
    public void runOpMode(){
        adudu = new Adudu(this);
        adudu.Initiate();

        waitForStart();

        if (opModeIsActive()){
            adudu.Operate(this);
        }
    }


}
