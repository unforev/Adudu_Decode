package org.firstinspires.ftc.teamcode.Mechanism;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {

    private DcMotor Intake_Motor;
    private DcMotor Transfer_Motor;
    final private HardwareMap hardwareMap;
    private double revsPerBall = 0;
    private int ballCount = 0;
    private int targetPosition = 0;
    private boolean intakeRunning = false;
    private double ticksPerRev;

    public Intake(LinearOpMode Adudu_OpMode) {
        this.hardwareMap = Adudu_OpMode.hardwareMap;
    }


    //    public Intake(OpMode Adudu_OpMode_Auto) {
//        this.hardwareMap = Adudu_OpMode_Auto.hardwareMap;
//    }

    public void Initiate(){
        Intake_Motor = hardwareMap.get(DcMotor.class, "intake");
        Transfer_Motor = hardwareMap.get(DcMotor.class, "transfer");

        Intake_Motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Intake_Motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Intake_Motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Transfer_Motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Transfer_Motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Transfer_Motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    public void Operate(){
            intakeBall();
    }
    public void intakeBall(){
//        if(intakeRunning) return;
//        if(ballCount >= 3) return;

        targetPosition =
                Intake_Motor.getCurrentPosition() +
                        (int)(ticksPerRev * revsPerBall);

        Intake_Motor.setTargetPosition(targetPosition);
        Intake_Motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Intake_Motor.setPower(0.8);

        Transfer_Motor.setTargetPosition(targetPosition);
        Transfer_Motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Transfer_Motor.setPower(0.8);

//        intakeRunning = true;
    }

    public void update(){

        if(!intakeRunning) return;
        if(!Intake_Motor.isBusy()){
            Intake_Motor.setPower(0);
            Intake_Motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            intakeRunning = false;
            ballCount++;
        }
    }
    public void resetBallCounter(){
        ballCount = 0;
    }

    public void reverseIntake(){
        Intake_Motor.setPower(-0.8);
    }

    public void stop(){
        Intake_Motor.setPower(0);
    }

    public int getBallCount(){
        return ballCount;
    }

    public double getMotorRevs(){
        return Intake_Motor.getCurrentPosition()/ticksPerRev;
    }

    public boolean isRunning(){
        return intakeRunning;
    }

}

