package org.firstinspires.ftc.teamcode.Mechanism;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Supplier;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

public class Drivetrain {

    private DcMotor Front_Left_Motor;
    private DcMotor Rear_Left_Motor;
    private  DcMotor Front_Right_Motor;
    private  DcMotor Rear_Right_Motor;

    private Follower follower;

    public static Pose startingPose;

    private TelemetryManager telemetryM;

    private Supplier<PathChain> pathChain;
    final private HardwareMap hardwareMap;

    public Drivetrain(LinearOpMode Adudu_OpMode) {
        this.hardwareMap = Adudu_OpMode.hardwareMap;
        follower = Constants.createFollower(Adudu_OpMode.hardwareMap);
        follower.setStartingPose(startingPose == null ? new Pose() : startingPose);
        follower. update();
    }



    public void Initiate() {
        Front_Left_Motor = hardwareMap.get(DcMotor.class, "lf" );
        Rear_Left_Motor = hardwareMap.get(DcMotor.class, "lr");
        Front_Right_Motor = hardwareMap.get(DcMotor.class, "rf");
        Rear_Right_Motor = hardwareMap.get(DcMotor.class, "rr");

        Front_Left_Motor.setDirection(DcMotorSimple.Direction.REVERSE);
        Rear_Left_Motor.setDirection(DcMotorSimple.Direction.REVERSE);
        Front_Right_Motor.setDirection(DcMotorSimple.Direction.FORWARD);
        Rear_Right_Motor.setDirection(DcMotorSimple.Direction.FORWARD);

        Front_Left_Motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Front_Right_Motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Rear_Right_Motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Rear_Left_Motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


    }

    public void Operate(double x, double y, double z) {
        double theta = Math.atan2(y,x);
        double power = Math.hypot(x,y);
        double turn = z;

        follower.update();
        Pose cur = follower.getPose();
        double heading = cur.getHeading();

        double sin = Math.sin(theta - heading - Math.PI/4);
        double cos = Math.cos(theta - heading - Math.PI/4);
        double max = Math.max(Math.abs(sin),
                Math.abs(cos));

        double Front_Left_Power = power * cos/max + turn;
        double Front_Right_Power = power * sin/max - turn;
        double Rear_Left_Power = power * sin/max + turn;
        double Rear_Right_Power = power * cos/max - turn;

        if( (power + Math.abs(turn)) > 1) {
            Front_Left_Power /= power + Math.abs(turn);
            Front_Right_Power /= power + Math.abs(turn);
            Rear_Left_Power /= power + Math.abs(turn);
            Rear_Right_Power /= power + Math.abs(turn);
        }
        Front_Left_Motor.setPower(Front_Left_Power);
        Front_Right_Motor.setPower(Front_Right_Power);
        Rear_Left_Motor.setPower(Rear_Left_Power);
        Rear_Right_Motor.setPower(Rear_Right_Power);



    }

}
