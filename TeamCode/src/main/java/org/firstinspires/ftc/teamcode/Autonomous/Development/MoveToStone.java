package org.firstinspires.ftc.teamcode.Autonomous.Development;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Autonomous.AutoMethods;

@Autonomous(name="MoveToStone", group="Development")

public class MoveToStone extends AutoMethods {
    public void runOpMode() {
        initRobot();

        leftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        initVision();

        while (opModeIsActive()) {
            runVision();
            //top 0
            //left 0
            //right 800
            //bottom 450
            if (stoneleft < 200) {
                //turn right
                leftBackDrive.setPower(-0.2);
                rightBackDrive.setPower(0.2);
            }
            else if (stoneright > 600)
            {
                //turn left
                leftBackDrive.setPower(0.2);
                rightBackDrive.setPower(-0.2);
            }
            else if (stonebottom < 300) {
                //forwards
                leftBackDrive.setPower(-0.2);
                rightBackDrive.setPower(-0.2);
            }
            else {
                stop();
            }
        }
    }
}
