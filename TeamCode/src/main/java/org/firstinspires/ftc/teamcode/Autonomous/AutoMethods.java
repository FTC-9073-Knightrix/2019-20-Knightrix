package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

public abstract class AutoMethods extends AutoHardwareMap {
    //Create the initialization method to run at the start of the programs
    public void initRobot() {
        //Add the motors to the configuration on the phones
        leftFrontDrive = hardwareMap.dcMotor.get("LF");
        rightFrontDrive = hardwareMap.dcMotor.get("RF");
        rightBackDrive = hardwareMap.dcMotor.get("RB");
        leftBackDrive = hardwareMap.dcMotor.get("LB");
        intakeLeft = hardwareMap.dcMotor.get("IL");
        intakeRight = hardwareMap.dcMotor.get("IR");
        liftMotor = hardwareMap.dcMotor.get("LM");

        //camera initialization

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        //phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        phoneCam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        phoneCam.openCameraDevice();
        stageSwitchingPipeline = new WebcamCV.StageSwitchingPipeline();
        phoneCam.setPipeline(stageSwitchingPipeline);
        phoneCam.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);

        //Servos
        blockServo = hardwareMap.servo.get("BS");
        sideServo = hardwareMap.servo.get("SS");
        blockGrabServo = hardwareMap.servo.get("BGS");

        rightRange = hardwareMap.get(DistanceSensor.class, "RR");
        leftRange = hardwareMap.get(DistanceSensor.class, "LR");

        //Set the direction of the motors
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);
        intakeLeft.setDirection(DcMotor.Direction.REVERSE);

        //Set the mode the motors are going to be running in
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        liftMotor.setTargetPosition(0);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Add the gyroscope to the configuration on the phones
        gyro = hardwareMap.get(BNO055IMU.class, "gyro");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        gyro.initialize(parameters);
    }

    public void runVuforia() {
        targetsSkyStone.activate();

        if (((VuforiaTrackableDefaultListener)stoneTarget.getListener()).isVisible()) {
                telemetry.addLine("Skystone Visible");
                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener)stoneTarget.getListener()).getFtcCameraFromTarget();
                //telemetry.addData("Pose", pose);
                if (pose != null) {
                    VectorF trans = pose.getTranslation();
                    Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
                    // Extract the X, Y, and Z components of the offset of the target relative to the robot
                    double tX = trans.get(0);
                    double tY = trans.get(1);
                    double tZ = trans.get(2);
                    telemetry.addData("X", tX);
                    telemetry.addData("Y", tY);
                    telemetry.addData("Z", tZ);
                    // Extract the rotational components of the target relative to the robot
                    double rX = rot.firstAngle;
                    double rY = rot.secondAngle;
                    double rZ = rot.thirdAngle;
                    telemetry.addData("Rot X", rX);
                    telemetry.addData("Rot Y", rY);
                    telemetry.addData("Rot Z", rZ);
                    VuX = trans.get(0);
                }
            }
            else {
                telemetry.addLine("No Skystone Visible");
            }
        telemetry.update();
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        //Configure Vuforia
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");
        //Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    public void initVuStone() {
        initVuforia();

        targetsSkyStone = this.vuforia.loadTrackablesFromAsset("Skystone");
        stoneTarget = targetsSkyStone.get(0);

        stoneTarget.setName("Stone Target");

        telemetry.addLine("Vuforia init done");
        telemetry.update();
    }

    // Move for a number of clicks based on the Gyro, Power/Speed, and desired direction of the robot
    public void gyroMove(int direction, double power, double distance, int wait){
        orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
        int StartingOrientation = (int) orientation.firstAngle;

        resetEncoders();
        distance *= ENCCM; //converts cm to encoder rotations
        while(opModeIsActive() &&  (distance > (Math.abs(leftFrontDrive.getCurrentPosition()) + Math.abs(rightFrontDrive.getCurrentPosition()) + Math.abs(leftBackDrive.getCurrentPosition()) + Math.abs(rightBackDrive.getCurrentPosition())) / 4.0)) {
            orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
            int gyroDegrees = (int) orientation.firstAngle;

            if (gyroDegrees < -359) {
                gyroDegrees += 360;
            }

            int CorrectionDegrees = (StartingOrientation - gyroDegrees);
            float myrot = (float)(CorrectionDegrees / 180.0) * -1;

            move(direction, (float) power, myrot);
        }

        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);

        sleep(wait);
    }

    public void newGyroMove(int direction, double power, double distance, int stopping, int wait){
        telemetry.addLine("newGyroMove");
        telemetry.update();
        orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
        int StartingOrientation = (int) orientation.firstAngle;

        //get the percent to start stopping at
        stopping = 100 - stopping;

        resetEncoders();
        distance *= ENCCM; //converts cm to encoder rotations
        while(opModeIsActive() &&  (distance > (Math.abs(leftFrontDrive.getCurrentPosition()) + Math.abs(rightFrontDrive.getCurrentPosition()) + Math.abs(leftBackDrive.getCurrentPosition()) + Math.abs(rightBackDrive.getCurrentPosition())) / 4.0)) {
            orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
            int gyroDegrees = (int) orientation.firstAngle;

            if (gyroDegrees < -180) {
                gyroDegrees += 360;
            }
            else if (gyroDegrees > 180) {
                gyroDegrees -= 360;
            }

            int CorrectionDegrees = (StartingOrientation - gyroDegrees);
            float myrot = (float)(CorrectionDegrees / 180.0) * -1;

            double newPower = (power/Math.abs(power)) * Range.clip(Math.abs(power*(((distance-((Math.abs(leftFrontDrive.getCurrentPosition()) + Math.abs(rightFrontDrive.getCurrentPosition()) + Math.abs(leftBackDrive.getCurrentPosition()) + Math.abs(rightBackDrive.getCurrentPosition())) / 4.0)) / (stopping*distance / 100.0)))),0.1,1);

            move(direction, (float) newPower, myrot);
        }

        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);

        sleep(wait);
    }

    //USING SIDE ENCODERS Move for a number of clicks based on the Gyro, Power/Speed, and desired direction of the robot
    public void gyroMoveSide(int direction, double power, double distance, int wait){
        orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
        int StartingOrientation = (int) orientation.firstAngle;

        resetEncoders();
        distance *= 30000/252.0; //converts cm to encoder rotations
        while(opModeIsActive() &&  (distance > ((Math.abs(intakeLeft.getCurrentPosition()) + Math.abs(intakeRight.getCurrentPosition())) / 2.0))) {
            orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
            int gyroDegrees = (int) orientation.firstAngle;

            if (gyroDegrees < -359) {
                gyroDegrees += 360;
            }

            int CorrectionDegrees = (StartingOrientation - gyroDegrees);
            float myrot = (float)(CorrectionDegrees / 180.0) * -1;

            //=ABS(C2*(((C4-F12)/(C4/10))))
            double newPower = (power/power) * Range.clip(Math.abs(power*(((distance-((Math.abs(intakeLeft.getCurrentPosition()) + Math.abs(intakeRight.getCurrentPosition())) / 2.0)) / (4*distance / 10.0)))),0.1,1);

            move(direction, (float) newPower, myrot);

            telemetry.addData("Left", Math.abs(intakeLeft.getCurrentPosition()));
            telemetry.addData("Right", Math.abs(intakeRight.getCurrentPosition()));
            telemetry.addData("Percent", ((Math.abs(intakeLeft.getCurrentPosition()) + Math.abs(intakeRight.getCurrentPosition())) / 2.0) / distance);
            telemetry.update();
        }

        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);

        sleep(wait);
    }

    //USING SIDE ENCODERS Move for a number of clicks based on the Gyro, Power/Speed, and desired direction of the robot
    public void gyroMoveSideNO(int direction, double power, double distance, int wait){
        orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
        int StartingOrientation = (int) orientation.firstAngle;

        resetEncoders();
        distance *= 30000/252.0; //converts cm to encoder rotations
        while(opModeIsActive() &&  (distance > ((Math.abs(intakeLeft.getCurrentPosition()) + Math.abs(intakeRight.getCurrentPosition())) / 2.0))) {
            orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
            int gyroDegrees = (int) orientation.firstAngle;

            if (gyroDegrees < -359) {
                gyroDegrees += 360;
            }

            int CorrectionDegrees = (StartingOrientation - gyroDegrees);
            float myrot = (float)(CorrectionDegrees / 180.0) * -1;

            move(direction, (float) power, myrot);

            telemetry.addData("Left", Math.abs(intakeLeft.getCurrentPosition()));
            telemetry.addData("Right", Math.abs(intakeRight.getCurrentPosition()));
            telemetry.addData("Percent", ((Math.abs(intakeLeft.getCurrentPosition()) + Math.abs(intakeRight.getCurrentPosition())) / 2.0) / distance);
            telemetry.update();
        }

        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);

        sleep(wait);
    }

    public void straighten (int angle, double power) {
        orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
        if (angle > (int) orientation.firstAngle) {
            turn (angle, power);
        }
        if (angle < (int) orientation.firstAngle) {
            turn (angle, -1 * power);
        }
    }

    //Create the move method to move the robot based off the angle, power, and rotation of the robot applied
    public void move (double myangle, float mypower, float myrot) {

        //If none of the motors are null, run each motor to an individual value based off the values inputted from the joystick
        if (leftFrontDrive != null && leftBackDrive != null && rightFrontDrive != null && rightBackDrive != null) {

            // Record variables
            float Orig_power = mypower;

            //To maximize the motor power, first calculate the maximum absolute power from Trig, then increase power to match 100% (Disregarding rotation values)
            double LeftPower  = Math.max( Math.abs( Math.sin((myangle + 135) / 180 * Math.PI)),Math.abs( Math.sin((myangle +  45) / 180 * Math.PI)));
            double RightPower = Math.max( Math.abs( Math.sin((myangle +  45) / 180 * Math.PI)),Math.abs( Math.sin((myangle + 135) / 180 * Math.PI)));
            double RobotPower = Math.max(Math.abs(LeftPower),Math.abs(RightPower));
            mypower = (float) (1/ RobotPower) * Orig_power; // Determine the new power to apply so that wheels are always running at the Power speed

            
            //Check for errors
            if (Double.isNaN(RobotPower )) {RobotPower = 0;}
            if (Float.isNaN(Orig_power )) {Orig_power = 0;}
            if (Float.isNaN(myrot )) {myrot = 0;}
            if (RobotPower==0) {mypower =0;}
            //Since mypower is -1/+1 and myrot can also be -1/+1, need to trim both down to ensure mypower + myrot are between -1/+1
            myrot = myrot * (Math.abs(myrot) / (Math.abs(myrot)+Math.abs(Orig_power)));
            mypower = mypower * (Math.abs(Orig_power) / (Math.abs(myrot)+Math.abs(Orig_power)));

            leftFrontDrive.setPower( Range.clip(( myrot + (mypower * ((Math.sin((myangle + 135) / 180 * Math.PI))))), -1, 1));
            leftBackDrive.setPower(  Range.clip(( myrot + (mypower * ((Math.sin((myangle +  45) / 180 * Math.PI))))), -1, 1));
            rightFrontDrive.setPower(Range.clip((-myrot + (mypower * ((Math.sin((myangle +  45) / 180 * Math.PI))))), -1, 1));
            rightBackDrive.setPower( Range.clip((-myrot + (mypower * ((Math.sin((myangle + 135) / 180 * Math.PI))))), -1, 1));

            telemetry.addData("LeftFront",  leftFrontDrive.getCurrentPosition());
            telemetry.addData("LeftBack",   leftBackDrive.getCurrentPosition());
            telemetry.addData("RightFront", rightFrontDrive.getCurrentPosition());
            telemetry.addData("RightBack",  rightBackDrive.getCurrentPosition());
        }
    }

    public void resetEncoders() {
        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intakeLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intakeRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intakeLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    //Create the method to turn the robot based on the degree value set and the current position of the robot
    public void turn(double degrees, double power) {

        //Create a variable power of the motor that gets slower the closer the robot is to the set degree
        //double power = 0.3;

        //Get the current position of the robot
        orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
        //Get the current degree of the robot
        angle = orientation.firstAngle;

        //While the difference between the target angle and current angle is greater than three degrees
        while (opModeIsActive() && Math.abs(degrees - angle) > 1) {
            //If the target degree is greater than the current angle of the robot, turn right
            if (Math.abs(degrees - angle) < 25) {
                leftFrontDrive.setPower(-power/5);
                rightFrontDrive.setPower(power/5);
                leftBackDrive.setPower(-power/5);
                rightBackDrive.setPower(power/5);
            }
            else {
                leftFrontDrive.setPower(-power);
                rightFrontDrive.setPower(power);
                leftBackDrive.setPower(-power);
                rightBackDrive.setPower(power);
            }

            //Get the current position of the robot
            orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
            //Get the current degree of the robot
            angle = orientation.firstAngle;

            //Display the target degree the robot is going to move to on the screen
            telemetry.addLine("Target degree: " + (int)(degrees));
            //Display the current degree of the robot on the screen
            telemetry.addLine("Current degree: " + (int)(angle));
            //Update telemetry
            telemetry.update();
        }

        telemetry.addLine("Done with turn");
        telemetry.update();
        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);

        sleep(200);
    }
    // Create a method to get values from Hardware
    // public void Get_Hardware_Values(MyGyro){

    //}
}