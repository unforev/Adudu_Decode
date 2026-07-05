package org.firstinspires.ftc.teamcode.Adudu;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.teamcode.Mechanism.Drivetrain;
import org.firstinspires.ftc.teamcode.Mechanism.Intake;
import org.firstinspires.ftc.teamcode.Mechanism.Outtake;
import org.firstinspires.ftc.teamcode.Mechanism.Transfer;


public class Adudu {

    final private Drivetrain Adudu_Drivetrain;
    final private Intake Adudu_Intake;
    final private Outtake Adudu_Outtake;
    final private Gamepad Adudu_Gamepad1;
    final private Gamepad Adudu_Gamepad2;


    public Adudu(LinearOpMode Adudu_opMode) {
        this.Adudu_Drivetrain = new Drivetrain(Adudu_opMode);
        this.Adudu_Intake = new Intake(Adudu_opMode);
        this.Adudu_Outtake = new Outtake(Adudu_opMode);
        this.Adudu_Gamepad1 = Adudu_opMode.gamepad1;
        this.Adudu_Gamepad2 = Adudu_opMode.gamepad2;
    }


    public void Initiate(){
        Adudu_Drivetrain.Initiate();
        Adudu_Intake.Initiate();
        Adudu_Outtake.Initiate();
    }
    public void Operate(LinearOpMode Adudu_opMode){
        while(Adudu_opMode.opModeIsActive()){
            Adudu_Operate();
        }
    }
    public void Adudu_Operate(){
        double x = Adudu_Gamepad1.left_stick_x;
        double y = -Adudu_Gamepad1.left_stick_y;
        double z = Adudu_Gamepad1.right_stick_x;
        Adudu_Drivetrain.Operate(x, y, z);

        // Intake : button : x
        if(Adudu_Gamepad1.x) Adudu_Intake.Operate();
        else if (!Adudu_Gamepad1.x) Adudu_Intake.stop();

        // Outtake distance_sensor : button : y
        if(Adudu_Gamepad1.y) Adudu_Outtake.Operate(1);
        else if( !Adudu_Gamepad1.y) Adudu_Outtake.Reset_Wait();

        // Outtake encoder : button : b
        if( Adudu_Gamepad1.b) Adudu_Outtake.Operate(2);
        else if( !Adudu_Gamepad1.b) Adudu_Outtake.update();


    }

    }



