package org.firstinspires.ftc.teamcode.Autonomous.CVMaster;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.teamcode.Autonomous.WebcamCV;

import java.io.File;

@Autonomous(name = "BlueStoneCV", group = "CV")

public class BlueStone extends WebcamCV {
    public void runOpMode(){
        initRobot();

        waitForStart();

        while (opModeIsActive())
        {
            blockGrabServo.setPosition(0.54);
            String skystone = stageSwitchingPipeline.skystone();
            while(skystone.equals("")) {
                sleep(100);
                skystone = stageSwitchingPipeline.skystone();
            }

            // Enable to debug Range values
            //File LeftRangeFile = AppUtil.getInstance().getSettingsFile("LeftRangeFile.txt");


            // Move robot to a certain distance of blocks
            boolean TaskPending = true;
            double LeftRangeValue = 0;

            while (opModeIsActive() && TaskPending) {
                // Read leftRange Sensor
                // that is located on the back of the robot
                LeftRangeValue = leftRange.getDistance(DistanceUnit.CM);
                //ReadWriteFile.writeFile(LeftRangeFile, String.valueOf(LeftRangeValue)); //Store value into a file
                float MyPower = 0;
                if (LeftRangeValue > 20) { MyPower = (float) -0.2; } //go slower
                if (LeftRangeValue > 28) { MyPower = (float) -0.4; } //go fast
                if (LeftRangeValue < 17) { MyPower = (float) 0.2; }  // you are too close move away
                if (LeftRangeValue >= 17 && LeftRangeValue <= 20) {  // you are right where I wanted
                    MyPower = 0;
                    TaskPending = false;
                }

                telemetry.addData("Distance (CM): ", LeftRangeValue);
                telemetry.addData("Power        : ", MyPower);
                telemetry.update();
                move(90, MyPower, 0);
            }
            straighten(0, 0.5);
            // Move End



            // Marker to stop the program here and keep testing
            /*
            TaskPending = true;
            while (opModeIsActive() && TaskPending) {
                telemetry.addLine("-- Stopped -- ");
                telemetry.update();
                move(90, 0, 0);
            }
            */

            if (skystone.equals("Left")) {
                newGyroMove(45,-0.5,15,0,0);
            }
            else if (skystone.equals("Right")) {
                newGyroMove(135,-0.5,15,0,0);
            }
            else {
                newGyroMove(0, 0.3, 3, 60, 0);
            }
            //Set the arm down
            blockServo.setPosition(1);
            sleep(600);
            blockGrabServo.setPosition(0);
            //Wait for the arm to fully go down
            sleep(600);
            blockServo.setPosition(0.23);
            sleep(600);
            //Pull the stone out
            newGyroMove(90, 0.4, 9,60, 0);
            if (skystone.equals("Left")) {
                newGyroMove(45,0.5,15,0,0);
            }
            else if (skystone.equals("Right")) {
                newGyroMove(135,0.5,15,0,0);
            }
            straighten(0, 0.5);
            newGyroMove(0,1,140,60,0);
            straighten(0, 0.5);
            newGyroMove(90,-0.3,15,60,0);
            blockServo.setPosition(1);
            //Wait for the arm to fully go up
            sleep(600);
            blockGrabServo.setPosition(0.54);
            //If it is the left or middle stone
            sleep(600);
            blockServo.setPosition(0.23);
            sleep(600);
            newGyroMove(90,0.3,15,60,0);
            straighten(0, 0.5);
            newGyroMove(0,-1,182,75,0);
            straighten(0, 0.5);
            while (rightRange.getDistance(DistanceUnit.CM) < 15 && opModeIsActive()) {
                move(90, (float)-0.6, 0);
            }
            while (rightRange.getDistance(DistanceUnit.CM) > 25 && opModeIsActive()) {
                move(90, (float)-0.6, 0);
            }
            while (rightRange.getDistance(DistanceUnit.CM) > 15 && opModeIsActive()) {
                move(90, (float)-0.2, 0);
            }
            move(0,0,0);
            //Set the arm down
            blockServo.setPosition(1);
            sleep(600);
            blockGrabServo.setPosition(0);
            //Wait for the arm to fully go down
            sleep(600);
            blockServo.setPosition(0.23);
            sleep(600);
            //Pull the stone out
            newGyroMove(90, 0.4, 9,60, 0);
            straighten(0, 0.5);
            newGyroMove(0,1,190,75,0);
            straighten(0, 0.5);
            newGyroMove(90,-0.3,15,60,0);
            blockServo.setPosition(1);
            //Wait for the arm to fully go up
            sleep(600);
            blockGrabServo.setPosition(0.54);
            //If it is the left or middle stone
            sleep(600);
            blockServo.setPosition(0.23);
            sleep(600);
            newGyroMove(90,0.3,15,60,0);
            stop();
        }
    }
}
