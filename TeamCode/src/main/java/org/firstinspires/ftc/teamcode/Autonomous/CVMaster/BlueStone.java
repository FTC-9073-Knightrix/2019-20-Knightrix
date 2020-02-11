package org.firstinspires.ftc.teamcode.Autonomous.CVMaster;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.teamcode.Autonomous.WebcamCV;

import java.io.File;

@Autonomous(name = "BlueStoneCV", group = "CV")

public class BlueStone extends WebcamCV {
    public void runOpMode(){
        initRobot();

        waitForStart();

        //while (opModeIsActive())
        //{
            //Detects stone position (Left, Center, Right) and closes camera
            String skystone = stageSwitchingPipeline.skystone();
            while(opModeIsActive() && skystone.equals("")) {
                sleep(100);
                skystone = stageSwitchingPipeline.skystone();
            }
            phoneCam.closeCameraDevice();
            //sleep(2000);


            // Set Grabber Fully Open
            blockGrabServo.setPosition(0.54);
            // Set Servo UP
            blockServo.setPosition(0.7);
            //sleep(2000);


            // Get Closer to skystone wall
            boolean TaskPending = true;
            double LeftRangeValue = 0;

            while (opModeIsActive() && TaskPending) {
                // Read leftRange Sensor
                // that is located on the back of the robot
                LeftRangeValue = leftRange.getDistance(DistanceUnit.CM);
                //ReadWriteFile.writeFile(LeftRangeFile, String.valueOf(LeftRangeValue)); //Store value into a file
                float MyPower = 0;
                if (LeftRangeValue > 20) { //go slower
                    MyPower = (float) -0.2;
                }
                if (LeftRangeValue > 28) { //go fast
                    MyPower = (float) -0.4;
                }
                if (LeftRangeValue < 17) { // you are too close move away
                    MyPower = (float) 0.2;
                }
                if (LeftRangeValue >= 17 && LeftRangeValue <= 20) {  // you are right where I wanted
                    MyPower = 0;
                    TaskPending = false;
                }
                move(90, MyPower, 0);
            }
            straighten(0, 0.5);
            // Move End
            //sleep(2000);


            // Move robot forwards/backwards based on skystone position
            float MyPower = (float) 0.3;
            float MyDistance = 5;
            if (skystone.equals("Left")) { // Go Forwards
                MyPower = (float) 0.3;
                MyDistance = 19;
            }
            else if (skystone.equals("Right")) { // Go Backwards
                MyPower = (float)-0.3;
                MyDistance = 8;
            }
            else { // Center, move forwards a little
                MyPower = (float) 0.3;
                MyDistance =  4;
            }
        straighten(0, 0.5);
            newGyroMove(0, MyPower, MyDistance, 60, 0);
            //sleep(2000);


            // ########## Grabs the stone ###########
            //Set the arm down

            // Move closer to the stone
        blockServo.setPosition(0.75);
            newGyroMove(90,-0.2,8,60,0);
        blockServo.setPosition(1);
        sleep(300);
            // Closes the hand
            blockGrabServo.setPosition(0);
            sleep(600);
            // Raises the skystone
            blockServo.setPosition(0.23);
            // sleep(600);
            //Pull the stone out
            newGyroMove(90, 0.4, 9,60, 0);
            straighten(0, 0.5);
            // ####  END Grabbing Stone #####
            //sleep(2000);



            if (skystone.equals("Left")) {
                newGyroMove(0,1,95,60,0);
            }
            else if (skystone.equals("Right")) {
                newGyroMove(0,1,125,60,0);
            }
            else {
                newGyroMove(0,1,110,60,0);
            }
            //sleep(2000);


            // Drop the skystone
            //Set the arm down
            blockServo.setPosition(1);
            //Wait for the arm to fully Down
            sleep(600);
            // Opens the hand
            blockGrabServo.setPosition(0.54);
            sleep(300);
            // Raises the hand
            blockServo.setPosition(0.23);
            sleep(300);
        newGyroMove(90,0.2,2,60,0);
            //sleep(2000);

            // Turn 180 degrees
            if (skystone.equals("Right")) {
                turn(180, 0.5);
            }
            //sleep(2000);


            // Goes back into stone zone based on three different distances
            if (skystone.equals("Left")) {
                newGyroMove(0,-1,133,75,0);
            }
            else if (skystone.equals("Right")) {
                newGyroMove(0,1,140,75,0);
            }
            else {
                newGyroMove(0,-1,150,75,0);
            }
            //sleep(2000);

            if (skystone.equals("Right")) {
                // Goes at an angle to skystone
                turn(-135, 0.5);
                //sleep(2000);

                // Turn on the intake
                intakeLeft.setPower(1);
                intakeRight.setPower(1);
                //sleep(2000);

                // Capture the skystone
                newGyroMove(0, 0.3, 12, 60, 0);
                sleep(600);
                newGyroMove(0, -0.3, 12, 60, 0);
                //sleep(2000);

                // Aligh back to be straight to the walls
                turn(-180, -0.5);
                //sleep(2000);

                // Turn OFF the intake
                intakeLeft.setPower(0);
                intakeRight.setPower(0);
                //sleep(2000);

                // Move to the Construction Area
                newGyroMove(0, -1, 85, 100, 0);
                //sleep(2000);

                // Drop the skystone
                intakeLeft.setPower(-0.6);
                intakeRight.setPower(-0.6);
                //sleep(2000);

                // Continues moving backward
                newGyroMove(0, -1, 40, 75, 0);
                //sleep(2000);

                // Turn OFF the intake
                intakeLeft.setPower(0);
                intakeRight.setPower(0);
            }
            else {
                // Move closer to the stone
                blockServo.setPosition(.7);
                sleep(300);
                // Get Closer to skystone wall
                TaskPending = true;
                double RightRangeValue = 0;

                while (opModeIsActive() && TaskPending) {
                    // Read leftRange Sensor
                    // that is located on the back of the robot
                    RightRangeValue = rightRange.getDistance(DistanceUnit.CM);
                    //ReadWriteFile.writeFile(LeftRangeFile, String.valueOf(LeftRangeValue)); //Store value into a file
                    MyPower = 0;
                    if (RightRangeValue > 20) { //go slower
                        MyPower = (float) -0.2;
                    }
                    if (RightRangeValue > 28) { //go fast
                        MyPower = (float) -0.4;
                    }
                    if (RightRangeValue < 17) { // you are too close move away
                        MyPower = (float) 0.2;
                    }
                    if (RightRangeValue >= 17 && RightRangeValue <= 20) {  // you are right where I wanted
                        MyPower = 0;
                        TaskPending = false;
                    }
                    move(90, MyPower, 0);
                }
                straighten(0, 0.5);
                blockServo.setPosition(0.75);
                newGyroMove(90,-0.2,10,60,0);
                blockServo.setPosition(1);
                sleep(300);
                // Closes the hand
                blockGrabServo.setPosition(0);
                sleep(600);
                // Raises the skystone
                blockServo.setPosition(0.23);
                // sleep(600);
                //Pull the stone out
                newGyroMove(90, 0.4, 9,60, 0);
                straighten(0, 0.5);
                if (skystone.equals("Left")) {
                    newGyroMove(0,1,130,75,0);
                }
                else if (skystone.equals("Center")) {
                    newGyroMove(0,1,145,75,0);
                }
                // Drop the skystone
                //Set the arm down
                blockServo.setPosition(1);
                //Wait for the arm to fully Down
                sleep(600);
                // Opens the hand
                blockGrabServo.setPosition(0.54);
                //sleep(300);
                // Raises the hand
                //blockServo.setPosition(0.23);
                //sleep(300);
            }
            //sleep(2000);

            liftMotor.setTargetPosition(-2600);
            liftMotor.setPower(0);
            blockServo.setPosition(0.81);
            //Wait for the arm to fully go up
            sleep(1000);
            blockGrabServo.setPosition(0.54);
            //Turn to get ready for the drivers
            liftMotor.setPower(1);
            while (liftMotor.getCurrentPosition() > -2300 && opModeIsActive()) {
                telemetry.addData("Position", liftMotor.getCurrentPosition());
                telemetry.update();
            }
            blockServo.setPosition(0);
            //Wait for the arm to fully go up
            sleep(500);
            blockGrabServo.setPosition(0.2);
            liftMotor.setTargetPosition(0);
            while (liftMotor.getCurrentPosition() < -300 && opModeIsActive()) {
                telemetry.addData("Position", liftMotor.getCurrentPosition());
                telemetry.update();
            }
        File gyroPosition = AppUtil.getInstance().getSettingsFile("gyroPosition.txt");
        orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
        ReadWriteFile.writeFile(gyroPosition, String.valueOf(orientation.firstAngle + 90));
            stop();
        //}
    }
}
