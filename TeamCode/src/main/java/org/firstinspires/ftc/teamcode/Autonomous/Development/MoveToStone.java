package org.firstinspires.ftc.teamcode.Autonomous.Development;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.Autonomous.AutoMethods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Autonomous(name="MoveToStone", group="Development")

public class MoveToStone extends AutoMethods {
    public void runOpMode() {
        initRobot();

        leftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        initVision();

        while (found == -1) {
            runVision();
        }

        List<Float> sortright = stoneright;
        Collections.sort(sortright);

        if (stoneright.get(found) == sortright.get(0)) {
            telemetry.addLine("Stone - Stone - Skystone");
            telemetry.addLine("" + sortright.get(2) + " < " + sortright.get(1) + " < " + sortright.get(0));
            telemetry.addLine("Skystone = " + stoneright.get(found));
            telemetry.addLine("As Is = " + stoneright);
            telemetry.addLine("Sorted = " + sortright);
        }
        else if (stoneright.get(found) == sortright.get(1)) {
            telemetry.addLine("Stone - Skystone - Stone");
            telemetry.addLine("" + sortright.get(2) + " < " + sortright.get(1) + " < " + sortright.get(0));
            telemetry.addLine("Skystone = " + stoneright.get(found));
            telemetry.addLine("As Is = " + stoneright);
            telemetry.addLine("Sorted = " + sortright);
        }
        else {
            telemetry.addLine("Skystone - Stone - Stone");
            telemetry.addLine("" + sortright.get(2) + " < " + sortright.get(1) + " < " + sortright.get(0));
            telemetry.addLine("Skystone = " + stoneright.get(found));
            telemetry.addLine("As Is = " + stoneright);
            telemetry.addLine("Sorted = " + sortright);
        }
        while (opModeIsActive()) {
            telemetry.update();
        }

            //top 0
            //left 0
            //right 800
            //bottom 450
            /*if (stoneleft < 200) {
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
            }*/
        //}
    }
}
