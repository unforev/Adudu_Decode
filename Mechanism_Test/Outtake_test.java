package org.firstinspires.ftc.teamcode.Mechanism_Test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Mechanism.Outtake;


@TeleOp
public class Outtake_test extends OpMode {

    private Outtake Adudu_Outtake;
    private Gamepad Adudu_Gamepad1;

    @Override
    public void init(){
        Adudu_Outtake.Initiate();
        Adudu_Gamepad1.b = false;
        Adudu_Gamepad1.y = false;
    }

    @Override
    public void loop(){

        if( Adudu_Gamepad1.b)
        {
            Adudu_Outtake.Operate(1);
            Adudu_Outtake.update();
            Adudu_Gamepad1.b = false;
        }

        if(Adudu_Gamepad1.y)
        {
            Adudu_Outtake.Operate(2);
            Adudu_Outtake.Reset_Wait();
            Adudu_Gamepad1.y = false;
        }


    }

}
