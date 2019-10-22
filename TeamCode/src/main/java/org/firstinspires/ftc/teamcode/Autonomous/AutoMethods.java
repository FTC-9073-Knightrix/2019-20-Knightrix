package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchImpl;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

import static java.lang.Math.round;

public abstract class AutoMethods extends AutoHardwareMap {
    //Create the initialization method to run at the start of the programs
    public void initRobot() {

        //Add the motors to the configuration on the phones
        //leftFrontDrive = hardwareMap.dcMotor.get("LF");
        //rightFrontDrive = hardwareMap.dcMotor.get("RF");
        rightBackDrive = hardwareMap.dcMotor.get("RB");
        leftBackDrive = hardwareMap.dcMotor.get("LB");

        //Set the direction of the motors
        //rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
        //leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);

        //Set the mode the motors are going to be running in
        //leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Add the gyroscope to the configuration on the phones
        gyro = hardwareMap.get(BNO055IMU.class, "gyro");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        gyro.initialize(parameters);

        //Add range sensors
        RRB = hardwareMap.get(ModernRoboticsI2cRangeSensor.class,"RRB");
        RLB = hardwareMap.get(ModernRoboticsI2cRangeSensor.class,"RLB");
        RLB.setI2cAddress(I2cAddr.create8bit(0x16));
    }

    public void initVision() {
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        /**
         * Activate TensorFlow Object Detection before we wait for the start command.
         * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
         **/
        if (tfod != null) {
            tfod.activate();
        }

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start op mode");
        telemetry.update();
        waitForStart();
    }

    public void runVision() {
        if (tfod != null) {
            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null) {
                if (updatedRecognitions.size() >= 3) {
                    telemetry.addData("# Object Detected", updatedRecognitions.size());
                    // step through the list of recognitions and display boundary info.
                    int i = 0;
                    int x = 0;
                    for (Recognition recognition : updatedRecognitions) {
                        if (recognition.getLabel().equals("Skystone")) {
                            found = x;
                        }
                        telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                        telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                recognition.getLeft(), recognition.getTop());
                        stoneleft.add(x, recognition.getLeft());
                        stonetop.add(x, recognition.getTop());
                        telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                recognition.getRight(), recognition.getBottom());
                        stoneright.add(x, recognition.getRight());
                        stonebottom.add(x, recognition.getBottom());
                        x++;
                    }
                    telemetry.update();
                }
            }
        }
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

                    //telemetry.addData("X", VuX);
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
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");
        //parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    public void initVuStone() {
        initVuforia();

        targetsSkyStone = this.vuforia.loadTrackablesFromAsset("Skystone");
        stoneTarget = targetsSkyStone.get(0);

        stoneTarget.setName("Stone Target");
    }

    /**
     * Initialize the TensorFlow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.8;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }

    // Move for a number of clicks based on the Gyro, Power/Speed, and desired direction of the robot
    public void gyroMove(int direction, double power, int distance, int wait){
        orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
        int StartingOrientation = (int) orientation.firstAngle;

        resetEncoders();
        distance *= ENCDISTANCE; //converts cm to encoder rotations
        while(opModeIsActive() &&  (distance > (Math.abs(leftFrontDrive.getCurrentPosition()) + Math.abs(rightFrontDrive.getCurrentPosition()) + Math.abs(leftBackDrive.getCurrentPosition()) + Math.abs(rightBackDrive.getCurrentPosition())) / 4)) {
            orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
            int gyroDegrees = (int) orientation.firstAngle;

            if (gyroDegrees < -359) {
                gyroDegrees += 360;
            }

            int CorrectionDegrees = (StartingOrientation - gyroDegrees);
            float myrot = (float)(CorrectionDegrees / 180.0) * -1;

            move(direction, (float) power, myrot);

            // DEBUG with Telemetry
            telemetry.addData("Directin Goal: ", direction);
            telemetry.addData("Gyro Position: ", gyroDegrees);
            telemetry.addData("Correction :   ",  CorrectionDegrees );
            telemetry.addData("MyRot -1/+1 :  ",  myrot);

            telemetry.addData("Position", (
                    Math.abs(leftFrontDrive.getCurrentPosition()) +
                            Math.abs(rightFrontDrive.getCurrentPosition()) +
                            Math.abs(leftBackDrive.getCurrentPosition()) +
                            Math.abs(rightBackDrive.getCurrentPosition()) )/ 4);
            telemetry.update();
        }

        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);

        sleep(wait);
    }

    //Create the move method to move the robot based off the angle, power, and rotation of the robot applied
    public void move (double myangle, float mypower, float myrot) {

        //If none of the motors are null, run each motor to an individual value based off the values inputted from the joystick
        if (leftFrontDrive != null && leftBackDrive != null && rightFrontDrive != null && rightBackDrive != null) {
            leftFrontDrive.setPower(Range.clip((myrot + (mypower * ((Math.sin((myangle + 135) / 180 * Math.PI))))), -1, 1));
            leftBackDrive.setPower(Range.clip((myrot + (mypower * ((Math.sin((myangle + 45) / 180 * Math.PI))))), -1, 1));
            rightFrontDrive.setPower(Range.clip((-myrot + (mypower * ((Math.sin((myangle + 45) / 180 * Math.PI))))), -1, 1));
            rightBackDrive.setPower(Range.clip((-myrot + (mypower * ((Math.sin((myangle + 135) / 180 * Math.PI))))), -1, 1));

            telemetry.addData("LeftFront", leftFrontDrive.getCurrentPosition());
            telemetry.addData("LeftBack", leftBackDrive.getCurrentPosition());
            telemetry.addData("RightFront", rightFrontDrive.getCurrentPosition());
            telemetry.addData("RightBack", rightBackDrive.getCurrentPosition());
        }
    }

    public void movePosition(double inches, double power) {
        resetEncoders();
        int distance = (int)round(inches * ENCDISTANCE);
        while(leftBackDrive.getCurrentPosition() < distance || rightBackDrive.getCurrentPosition() < distance) {
            if (leftBackDrive.getCurrentPosition() < distance) {
                leftBackDrive.setPower(power);
            }
            else {
                leftBackDrive.setPower(0);
            }
            if (rightBackDrive.getCurrentPosition() < distance) {
                rightBackDrive.setPower(power);
            }
            else {
                rightBackDrive.setPower(0);
            }
            telemetry.addData("Left", leftBackDrive.getCurrentPosition());
            telemetry.addData("Right", rightBackDrive.getCurrentPosition());
            telemetry.update();
        }
    }

    public void resetEncoders() {
        //leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
