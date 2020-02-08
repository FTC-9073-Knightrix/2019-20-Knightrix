package org.firstinspires.ftc.teamcode.Autonomous.CVMaster;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Autonomous.WebcamCV;

@Autonomous(name = "RightStoneCV", group = "CV")

public class RedStone extends WebcamCV {
    public void runOpMode(){
        initRobot();

        waitForStart();

        while (opModeIsActive())
        {
            blockGrabServo.setPosition(0.54);
            String skystone = stageSwitchingPipeline.skystone();
            while(opModeIsActive() && skystone.equals("")) {
                sleep(100);
                skystone = stageSwitchingPipeline.skystone();
            }
            phoneCam.closeCameraDevice();
            blockServo.setPosition(0.7);
            while (rightRange.getDistance(DistanceUnit.CM) > 30 && opModeIsActive()) {
                move(90, (float)-0.6, 0);
            }
            while (rightRange.getDistance(DistanceUnit.CM) > 20 && opModeIsActive()) {
                move(90, (float)-0.2, 0);
            }
            move(0,0,0);
            if (skystone.equals("Left")) {
                newGyroMove(0,0.3,20,60,0);
            }
            else if (skystone.equals("Right")) {
                newGyroMove(0,-0.3,10,60,0);
            }
            else {
                newGyroMove(0, -0.3, 5, 60, 0);
            }
            //Set the arm down
            blockServo.setPosition(1);
            sleep(200);
            blockGrabServo.setPosition(0);
            //Wait for the arm to fully go down
            sleep(600);
            blockServo.setPosition(0.23);
            sleep(600);
            //Pull the stone out
            newGyroMove(90, 0.4, 9,60, 0);
            straighten(0, 0.5);
            if (skystone.equals("Left")) {
                newGyroMove(0,-1,125,60,0);
            }
            else if (skystone.equals("Right")) {
                newGyroMove(0,-1,95,60,0);
            }
            else {
                newGyroMove(0,-1,110,60,0);
            }
            /*straighten(0, 0.5);
            newGyroMove(90,-0.3,15,60,0);*/
            blockServo.setPosition(1);
            //Wait for the arm to fully go up
            sleep(600);
            blockGrabServo.setPosition(0.54);
            //If it is the left or middle stone
            sleep(300);
            blockServo.setPosition(0.23);
            sleep(300);
            /*newGyroMove(90,0.3,15,60,0);
            straighten(0, 0.5);*/
            //turn(180,0.5);
            /*newGyroMove(0,1,182,75,0);
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
            stop();*/
            if (skystone.equals("Left")) {
                newGyroMove(0,1,145,75,0);
            }
            else if (skystone.equals("Right")) {
                newGyroMove(0,1,115,75,0);
            }
            else {
                newGyroMove(0,1,130,75,0);
            }
            turn(-45,-0.5);
            intakeLeft.setPower(1);
            intakeRight.setPower(1);
            newGyroMove(0, 0.3, 12, 60, 0);
            sleep(600);
            newGyroMove(0, -0.3, 12, 60, 0);
            turn(0,0.5);
            intakeLeft.setPower(0);
            intakeRight.setPower(0);
            sleep(200);
            if (skystone.equals("Left")) {
                newGyroMove(0,1,85,100,0);
            }
            else if (skystone.equals("Right")) {
                newGyroMove(0,1,55,100,0);
            }
            else {
                newGyroMove(0,-1,70,100,0);
            }
            intakeLeft.setPower(-0.6);
            intakeRight.setPower(-0.6);
            newGyroMove(0,-1,40,75,0);
            intakeLeft.setPower(0);
            intakeRight.setPower(0);
            //turn(-90,-0.5);
            //newGyroMove(0,-1,20,75,0);
            stop();
        }
    }
}