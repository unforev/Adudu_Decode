package org.firstinspires.ftc.teamcode.Mechanism;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Outtake {

    private DcMotor Outtake_Motor;
    private DcMotor Transfer_Motor;
    final private HardwareMap hardwareMap;
    private boolean shooting = false;
    private double ticksPerRev;
    private double revsPerBall = 0;
    private int targetPosition = 0;
    private DistanceSensor sensorDistance;

    private enum OuttakeState {
        IDLE,               // chờ
        SPIN_UP,            // xoay lên đỉnh để bắn
        FEEDING,            // Đùn bóng
        ANTI_INERTIA_BRAKE, // Phanh
    }

    private OuttakeState currentState = OuttakeState.IDLE;
    private ElapsedTime stateTimer = new ElapsedTime();
    private final double BALL_THRESHOLD_CM = 6.0;   // có bóng
    private final double SPIN_UP_TIME_MS = 150.0;   // Thời gian bóng trên xoay để lên đinhr
    private final double BRAKE_PULSE_MS = 40.0;     // Thời gian phanh





    public Outtake(LinearOpMode Adudu_OpMode) {
        this.hardwareMap = Adudu_OpMode.hardwareMap;
    }

//    public Outtake(OpMode Adudu_OpMode_Auto) {
//        this.hardwareMap = Adudu_OpMode_Auto.hardwareMap;
//    }

    public void Initiate() {
        Outtake_Motor = hardwareMap.get(DcMotor.class, "outtake");
        Transfer_Motor = hardwareMap.get(DcMotor.class, "transfer");
        Outtake_Motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Outtake_Motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        Outtake_Motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        sensorDistance = hardwareMap.get(DistanceSensor.class, "sensor_distance");

    }

    public void update(){
        if(!shooting) return;
        if(Outtake_Motor.getCurrentPosition() >= targetPosition){
            Outtake_Motor.setPower(0);
            shooting = false;
        }
    }

    public void stop(){
        Outtake_Motor.setPower(0);
        shooting = false;
    }

    public boolean isRunning(){
        return shooting;
    }

    public int getEncoder(){
        return Outtake_Motor.getCurrentPosition();
    }

    public double getMotorRevs(){
        return Outtake_Motor.getCurrentPosition() / ticksPerRev;
    }

    public void Reset_Wait() // Reset Wait
    {
        currentState = OuttakeState.IDLE;
    }

    public void Operate( int mode)
    {
        if(mode == 1) // dung encoder
        {
            if(shooting) return;

            targetPosition = Outtake_Motor.getCurrentPosition()
                    + (int)(ticksPerRev * revsPerBall);

            Outtake_Motor.setPower(0);
        }

        if( mode == 2) // dung distance sensor
        {
            double currentDistance = sensorDistance.getDistance(DistanceUnit.CM);
            switch (currentState) {
                case IDLE:
                    Outtake_Motor.setPower(0.0);
                    Transfer_Motor.setPower(0.0);

                    if (currentDistance < BALL_THRESHOLD_CM) {
                        stateTimer.reset();
                        currentState = OuttakeState.SPIN_UP;
                    }
                    break;

                case SPIN_UP:
                    Outtake_Motor.setPower(1.0);
                    Transfer_Motor.setPower(0.0);

                    if (stateTimer.milliseconds() >= SPIN_UP_TIME_MS) {
                        currentState = OuttakeState.FEEDING;
                    }
                    break;

                case FEEDING:
                    Outtake_Motor.setPower(1.0);
                    Transfer_Motor.setPower(0.8);

                    //->phanh
                    if (currentDistance > BALL_THRESHOLD_CM) {
                        stateTimer.reset();
                        currentState = OuttakeState.ANTI_INERTIA_BRAKE;
                    }
                    break;

                case ANTI_INERTIA_BRAKE:
                    Outtake_Motor.setPower(1.0);
                    Transfer_Motor.setPower(-0.3);

                    if (stateTimer.milliseconds() >= BRAKE_PULSE_MS) {
                        Outtake_Motor.setPower(0.0);
                        Transfer_Motor.setPower(0.0);
                    }
                    break;


            }

        }
//        Bước 1: IDLE (Chờ lệnh)
//        Trạng thái: Tắt toàn bộ motor.
//        Điều kiện chuyển: Người lái Bấm nút A + Cảm biến báo Có bóng (< 6cm)
//
//        Bước 2: SPIN_UP (Lấy đà)
//        Trạng thái: Bật bánh bắn nhỏ (trên cùng) 100% công suất. Bánh đùn dưới đứng im.
//            Điều kiện chuyển: Đợi bánh bắn đạt đỉnh tốc độ trong 0.15 giây
//
//        Bước 3: FEEDING (Đùn bóng)
//        Trạng thái: Giữ bánh bắn trên quay, bật tiếp 2 bánh đùn to dưới (80% công suất) để đẩy quả bóng số 1 lên.
//            Điều kiện chuyển: Ngay khi quả bóng số 1 lọt vào bánh bắn làm cảm biến Hết thấy bóng (> 6cm)
//
//        Bước 4: ANTI_INERTIA_BRAKE (Phanh )
//        Trạng thái: Giữ bánh trên quay để tiễn bóng 1 ra ngoài. Ép bánh đùn dưới quay ngược chiều (-30%).
//            Điều kiện chuyển: Chạy xung điện ngược đúng 0.04 giây để khựng chết quán tính, giữ quả bóng số 2 lại
//
//        Bước 5: RESET_WAIT (Khóa an toàn)
//        Trạng thái: Tắt sạch toàn bộ motor.
//            Điều kiện chuyển: Người lái Buông tay khỏi nút A hoàn toàn -> Quay lại Bước 1 (Sẵn sàng cho quả tiếp theo).

    }




}
