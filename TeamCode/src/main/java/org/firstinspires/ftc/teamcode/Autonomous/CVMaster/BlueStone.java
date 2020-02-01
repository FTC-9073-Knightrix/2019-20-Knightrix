package org.firstinspires.ftc.teamcode.Autonomous.CVMaster;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Autonomous.AutoMethods;
import org.firstinspires.ftc.teamcode.Autonomous.WebcamCV;

@Autonomous(name = "BlueStoneCV", group = "CV")

public class BlueStone extends WebcamCV {
    public void runOpMode(){
        initRobot();

        waitForStart();

        while (opModeIsActive())
        {
            /*telemetry.addData("Num contours found", stageSwitchingPipeline.getNumContoursFound());
            telemetry.addData("Skystone", stageSwitchingPipeline.skystone());
            telemetry.update();
            sleep(100);*/

            String skystone = stageSwitchingPipeline.skystone();
            while(skystone.equals("")) {
                sleep(100);
                skystone = stageSwitchingPipeline.skystone();
            }

            if (skystone.equals("Left")) {
                gyroMove(70,-0.5,35,0);
            }
            else if (skystone.equals("Center")) {
                gyroMove(90,-0.5,35,0);
            }
            else {
                gyroMove(110,-0.5,35,0);
            }

            while (rightRange.getDistance(DistanceUnit.CM) > 9 && opModeIsActive()) {
                move(90, (float)-0.3, 0);
            }
            move(0,0,0);

            //Set the arm down
            blockServo.setPosition(0.81);
            blockGrabServo.setPosition(0);
            //Wait for the arm to fully go down
            sleep(500);
            blockServo.setPosition(0.23);
            sleep(500);
            //Pull the stone out
            newGyroMove(90, 0.8, 9,60, 0);
            straighten(0, 0.5);
            stop();
        }
    }
}
