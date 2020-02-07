package org.firstinspires.ftc.teamcode.Autonomous.CVMaster;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Autonomous.WebcamCV;

@Autonomous(name = "BlueStoneIntake", group = "CV")

public class BlueStoneIntake extends WebcamCV {
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

            while (leftRange.getDistance(DistanceUnit.CM) < 20 && opModeIsActive()) {
                move(90, (float)-0.6, 0);
            }
            while (leftRange.getDistance(DistanceUnit.CM) > 30 && opModeIsActive()) {
                move(90, (float)-0.6, 0);
            }
            while (leftRange.getDistance(DistanceUnit.CM) > 20 && opModeIsActive()) {
                move(90, (float)-0.2, 0);
            }
            move(0,0,0);
            if (skystone.equals("Left")) {
                newGyroMove(45,-0.5,15,0,0);
            }
            else if (skystone.equals("Right")) {
                newGyroMove(135,-0.5,15,0,0);
            }
            else {
                newGyroMove(0, -0.3, 18, 60, 0);
                turn(-45,-0.5);
                intakeLeft.setPower(1);
                intakeRight.setPower(1);
                newGyroMove(0, 0.3, 12, 60, 0);
                sleep(1000);
                newGyroMove(0, -0.3, 12, 60, 0);
                turn(0,0.5);
                intakeLeft.setPower(0);
                intakeRight.setPower(0);
            }
            /*//Set the arm down
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
            straighten(0, 0.5);*/
            newGyroMove(0,1,110,75,0);
            /*straighten(0, 0.5);
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
            straighten(0, 0.5);*/
            intakeLeft.setPower(-0.2);
            intakeRight.setPower(-0.2);
            newGyroMove(0,-1,10,75,0);
            intakeLeft.setPower(0);
            intakeRight.setPower(0);
            turn(180,0.5);
            newGyroMove(0,1,90,75,0);
            turn(-135,0.5);
            intakeLeft.setPower(1);
            intakeRight.setPower(1);
            newGyroMove(0, 0.3, 12, 60, 0);
            sleep(1000);
            newGyroMove(0, -0.3, 12, 60, 0);
            turn(180,-0.5);
            intakeLeft.setPower(0);
            intakeRight.setPower(0);
            newGyroMove(0,-1,110,75,0);
            intakeLeft.setPower(-0.2);
            intakeRight.setPower(-0.2);
            newGyroMove(0,-1,20,75,0);
            turn(90,-0.5);
            newGyroMove(0,-1,20,75,0);
            stop();
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
