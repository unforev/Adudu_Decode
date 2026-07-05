package org.firstinspires.ftc.teamcode.Mechanism;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class ArcadeDrive {
    private DcMotor frontleftMotor, backleftMotor, frontrightMotor, backrightMotor;

    private IMU imu;

    public void init(HardwareMap hardwareMap) {
        frontleftMotor = hardwareMap.get(DcMotor.class, "front_left_motor");
        backleftMotor = hardwareMap.get(DcMotor.class, "back_left_motor");
        frontrightMotor = hardwareMap.get(DcMotor.class, "front_right_motor");
        backrightMotor = hardwareMap.get(DcMotor.class, "back_right_motor");

        frontleftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backleftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontrightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backrightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontleftMotor.setDirection(DcMotor.Direction.REVERSE);
        backleftMotor.setDirection(DcMotor.Direction.REVERSE);

        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
                )
        );
        imu.initialize(parameters);
    }

    public void driveFieldCentric(double throttle, double strafe, double spin) {

        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        double rotX = strafe * Math.cos(-botHeading) - throttle * Math.sin(-botHeading);
        double rotY = strafe * Math.sin(-botHeading) + throttle * Math.sin(-botHeading);

        double frontleftPower = rotY + rotX + spin;
        double backleftPower = rotY - rotX + spin;
        double frontrightPower = rotY - rotX - spin;
        double backrightPower = rotY + rotX - spin;

        normalizeAndSetPower(frontleftPower, backleftPower, frontrightPower, backrightPower);
    }

    private void normalizeAndSetPower(double fl, double bl, double fr, double br) {
        double largest = Math.max(Math.abs(fl), Math.abs(bl));
        largest = Math.max(largest, Math.abs(fr));
        largest = Math.max(largest, Math.abs(br));

        if (largest > 1.0) {
            fl /= largest;
            bl /= largest;
            fr /= largest;
            br /= largest;
        }

        frontleftMotor.setPower(fl);
        frontrightMotor.setPower(bl);
        backleftMotor.setPower(fr);
        backrightMotor.setPower(br);
    }
    public void resetHeading() {
        imu.resetYaw();
    }

    public double getHeadingDegrees() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }
}
