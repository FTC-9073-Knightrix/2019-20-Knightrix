package org.firstinspires.ftc.teamcode.Autonomous.CVMaster;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Autonomous.WebcamCV;

@Autonomous(name = "BlueStoneCV", group = "CV")

public class BlueStone extends WebcamCV {
    public void runOpMode(){
        initRobot();

        waitForStart();

        while (opModeIsActive())
        {
            blockGrabServo.setPosition(0.54);
            newGyroMove(70,-0.5,5,60,0);
            String skystone = stageSwitchingPipeline.skystone();
            while(skystone.equals("")) {
                sleep(100);
                skystone = stageSwitchingPipeline.skystone();
            }
            if (skystone.equals("Left")) {
                newGyroMove(45,-0.5,15,0,0);
                newGyroMove(90,-0.5,20,0,0);
            }
            else if (skystone.equals("Center")) {
                newGyroMove(90,-0.5,35,0,0);
            }
            else {
                newGyroMove(135,-0.5,15,0,0);
                newGyroMove(90,-0.5,20,0,0);
            }

            while (leftRange.getDistance(DistanceUnit.CM) > 15 && opModeIsActive()) {
                move(90, (float)-0.3, 0);
            }
            move(0,0,0);

            //Set the arm down
            blockServo.setPosition(0.81);
            sleep(1000);
            blockGrabServo.setPosition(0);
            //Wait for the arm to fully go down
            sleep(1000);
            blockServo.setPosition(0.23);
            sleep(1000);
            //Pull the stone out
            newGyroMove(90, 0.8, 9,60, 0);
            if (skystone.equals("Left")) {
                newGyroMove(45,0.5,15,0,0);
            }
            else if (skystone.equals("Right")) {
                newGyroMove(135,0.5,15,0,0);
            }
            straighten(0, 0.5);
            newGyroMove(0,1,107,60,0);
            blockServo.setPosition(0.81);
            //Wait for the arm to fully go up
            sleep(1000);
            blockGrabServo.setPosition(0.54);
            //If it is the left or middle stone
            sleep(1000);
            blockServo.setPosition(0.23);
            sleep(1000);
            newGyroMove(0,1,142,60,0);
            stop();
        }
    }
}
