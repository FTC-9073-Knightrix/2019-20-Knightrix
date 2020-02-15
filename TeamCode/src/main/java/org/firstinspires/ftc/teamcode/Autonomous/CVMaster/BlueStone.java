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

@Autonomous(name = "BlueStone", group = "CV")

public class BlueStone extends WebcamCV {
    public void runOpMode(){
        //Initialize robot
        initRobot();
        //Wait until the robot starts
        waitForStart();

        //Detects stone position (Left, Center, Right) until it is found
        String skystone = stageSwitchingPipeline.skystone();
        while(opModeIsActive() && skystone.equals("")) {
            sleep(100);
            skystone = stageSwitchingPipeline.skystone();
        }
        //Closes camera
        phoneCam.closeCameraDevice();

        //Grabber open
        blockGrabServo.setPosition(0.54);
        //Side servo out
        blockServo.setPosition(0.7);

        //Get closer to skystone wall using range sensor
        boolean TaskPending = true;
        double LeftRangeValue = 0;
        while (opModeIsActive() && TaskPending) {
            LeftRangeValue = leftRange.getDistance(DistanceUnit.CM);
            float MyPower = 0;
            //Too far away, get closer slowly
            if (LeftRangeValue > 20) {
                MyPower = (float) -0.2;
            }
            //Too far away, get closer faster
            if (LeftRangeValue > 28) {
                MyPower = (float) -0.4;
            }
            //Too close, move further slowly
            if (LeftRangeValue < 17) {
                MyPower = (float) 0.2;
            }
            //Perfect
            if (LeftRangeValue >= 17 && LeftRangeValue <= 20) {
                MyPower = 0;
                TaskPending = false;
            }
            move(90, MyPower, 0);
        }

        //Adjust to get ready to move
        straighten(0, 0.5);

        //Move robot forwards/backwards based on skystone position
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

        //Set arm down a little
        blockServo.setPosition(0.75);
        //Move closer to the stone
        newGyroMove(90,-0.2,8,60,0);
        //Set arm fully down once next to stone
        blockServo.setPosition(1);
        //Wait for arm to go down
        sleep(300);
        // Closes the hand
        blockGrabServo.setPosition(0);
        //Waits for the hand to fully close
        sleep(600);
        // Raises the skystone
        blockServo.setPosition(0.23);
        //Pull the stone out
        newGyroMove(90, 0.4, 9,60, 0);
        //Align to get ready to move to other side
        straighten(0, 0.5);
        //Move to the other side of the field to drop off the stone
        if (skystone.equals("Left")) {
            newGyroMove(0,1,95,60,0);
        }
        else if (skystone.equals("Right")) {
            newGyroMove(0,1,125,60,0);
        }
        else {
            newGyroMove(0,1,110,60,0);
        }

        //Set the arm down
        blockServo.setPosition(1);
        //Wait for the arm to fully Down
        sleep(600);
        // Opens the hand
        blockGrabServo.setPosition(0.54);
        //Wait for grabber to fully open
        sleep(300);
        // Raises the hand
        blockServo.setPosition(0.23);
        //Wait for side servo to fully go up
        sleep(300);
        //Move away from the stone a little to make sure the robot doesn't run into the center beam
        newGyroMove(90,0.2,2,60,0);

        //If the right skystone, turn 180 degrees to pick up with the front intake wheels
        if (skystone.equals("Right")) {
            turn(180, 0.5);
        }

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

        //Use the intake if the skystone is the right one (closest to wall)
        if (skystone.equals("Right")) {
            //Turn to angle the skystone
            turn(-135, 0.5);
            // Turn on the intake
            intakeLeft.setPower(1);
            intakeRight.setPower(1);
            //Move forwards to the skystone
            newGyroMove(0, 0.3, 12, 60, 0);
            //Wait a little until the skystone goes into the robot
            sleep(600);
            //Take the robot out
            newGyroMove(0, -0.3, 12, 60, 0);
            //Turn so that the robot can cross over to the other side again
            turn(-180, -0.5);
            // Turn OFF the intake
            intakeLeft.setPower(0);
            intakeRight.setPower(0);
            // Move to the Construction Area
            newGyroMove(0, -1, 85, 100, 0);
            // Drop the skystone as the robot moves backwards
            intakeLeft.setPower(-0.6);
            intakeRight.setPower(-0.6);
            // Continues moving backward
            newGyroMove(0, -1, 40, 75, 0);
            // Turn OFF the intake
            intakeLeft.setPower(0);
            intakeRight.setPower(0);
        }
        //If the skystone is the center or left one
        else {
            //Set the arm down a little
            blockServo.setPosition(.7);
            //Wait for the arm to go down a little
            sleep(300);

            // Get Closer to skystone wall using the range sensor
            TaskPending = true;
            double RightRangeValue = 0;
            while (opModeIsActive() && TaskPending) {
                // Read rightRange Sensor
                RightRangeValue = rightRange.getDistance(DistanceUnit.CM);
                MyPower = 0;
                //Too far, move slowly closer
                if (RightRangeValue > 20) {
                    MyPower = (float) -0.2;
                }
                //Too far, move quickly closer
                if (RightRangeValue > 28) {
                    MyPower = (float) -0.4;
                }
                //Too close, move slowly away
                if (RightRangeValue < 17) {
                    MyPower = (float) 0.2;
                }
                //Perfect
                if (RightRangeValue >= 17 && RightRangeValue <= 20) {
                    MyPower = 0;
                    TaskPending = false;
                }
                move(90, MyPower, 0);
            }
            //Align to the skystone
            straighten(0, 0.5);
            //Move the arm down
            blockServo.setPosition(0.75);
            //Move closer to the stone
            newGyroMove(90,-0.2,10,60,0);
            //Move the arm fully down once next to the skystone
            blockServo.setPosition(1);
            //Wait for the arm to fully go down
            sleep(300);
            // Closes the hand
            blockGrabServo.setPosition(0);
            //Wait for the grabber to fully close
            sleep(600);
            // Raises the skystone
            blockServo.setPosition(0.23);
            //Pull the stone out
            newGyroMove(90, 0.4, 9,60, 0);
            //Align to get ready to go to the other side
            straighten(0, 0.5);
            //Depending on the skystone get to the other side
            if (skystone.equals("Left")) {
                newGyroMove(0,1,130,75,0);
            }
            else if (skystone.equals("Center")) {
                newGyroMove(0,1,145,75,0);
            }
            //Set the arm down
            blockServo.setPosition(1);
            //Wait for the arm to fully Down
            sleep(600);
            // Opens the hand
            blockGrabServo.setPosition(0.54);
        }

        //Make the lift go up to swap
        liftMotor.setTargetPosition(-2600);
        //Don't run the motor yet
        liftMotor.setPower(0);
        //Set the arm out
        blockServo.setPosition(0.81);
        //Wait for the arm to fully go up
        sleep(1000);
        //Set the grabber out
        blockGrabServo.setPosition(0.54);
        //Lift motor up to swap
        liftMotor.setPower(1);
        //While it's moving up show the current position
        while (liftMotor.getCurrentPosition() > -2300 && opModeIsActive()) {
            telemetry.addData("Position", liftMotor.getCurrentPosition());
            telemetry.update();
        }
        //Bring the arm down once lift is up
        blockServo.setPosition(0);
        //Wait for the arm to fully go up
        sleep(500);
        //Close the grabber to a certain position to fit it under the lift
        blockGrabServo.setPosition(0.2);
        //Set the lift down
        liftMotor.setTargetPosition(0);
        //While the lift is going down show its position
        while (liftMotor.getCurrentPosition() < -300 && opModeIsActive()) {
            telemetry.addData("Position", liftMotor.getCurrentPosition());
            telemetry.update();
        }
        //Save the orientation of the robot so that it can be used for the teleop
        File gyroPosition = AppUtil.getInstance().getSettingsFile("gyroPosition.txt");
        orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
        ReadWriteFile.writeFile(gyroPosition, String.valueOf(orientation.firstAngle + 90));
        //Stop the program (it's done)
        stop();
    }
}