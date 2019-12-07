package org.firstinspires.ftc.teamcode.Autonomous.Development;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Autonomous.AutoMethods;

@Autonomous(name="Back Encoder Test", group = "Development")

public class Back_Encoder extends AutoMethods {
    public void runOpMode() {
        initRobot();
        waitForStart();

        final double encoderCM = 30000/252.0;

        /*while (Math.abs(intakeLeft.getCurrentPosition()) < 30000 || Math.abs(intakeRight.getCurrentPosition()) < 30000) {
            if (Math.abs(intakeLeft.getCurrentPosition()) < 30000) {
                leftFrontDrive.setPower(0.1);
                leftBackDrive.setPower(0.1);
            }
            else {
                leftFrontDrive.setPower(0);
                leftBackDrive.setPower(0);
            }
            if (Math.abs(intakeRight.getCurrentPosition()) < 30000) {
                rightFrontDrive.setPower(0.1);
                rightBackDrive.setPower(0.1);
            }
            else {
                rightFrontDrive.setPower(0);
                rightBackDrive.setPower(0);

            }*/

//        gyroMoveSide(0, 1, 304.8,0);


        turn(90,0.3);
        telemetry.addData("Real position", BackEncoder.getCurrentPosition() );
        telemetry.update();
        sleep(5000);
        turn(179,0.3);
        telemetry.addData("Real position", BackEncoder.getCurrentPosition() );
        telemetry.update();
        sleep(5000);
        turn(-90,.3);
        telemetry.addData("Real position", BackEncoder.getCurrentPosition() );
        telemetry.update();

        /// Turning right, the Back_Encoder values are:
        //  90 =>  -2,718
        // 180 =>  -5,550
        // 270 =>  -8,325
        // 360 => -10,100


        while(opModeIsActive()) {
//            telemetry.addData("Should be", 304.8*encoderCM);
            telemetry.addData("Real position", BackEncoder.getCurrentPosition() );
            telemetry.update();
        }
    }
}
