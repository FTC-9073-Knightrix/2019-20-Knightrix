package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public abstract class TeleOpHardwareMap extends OpMode {
    //Create the four drive motors
    public DcMotor leftFrontDrive;
    public DcMotor rightFrontDrive;
    public DcMotor rightBackDrive;
    public DcMotor leftBackDrive;
    //Create other motors
    public DcMotor intakeLeft;
    public DcMotor intakeRight;
    public DcMotor liftMotor;

    //Create the servo motors
    public Servo bodyTwistServo;
    public Servo blockTwistServo;
    public Servo clampServo;
    public Servo blockServo;
    public Servo siteServo;

    //Create the variable that will keep track of the left joystick's x value
    public float leftstick_x = 0;
    public float g1_leftstick_x = 0;
    public float g2_leftstick_x = 0;
    //Create the variable that will keep track of the left joystick's y value
    public float leftstick_y = 0;
    public float g1_leftstick_y = 0;
    public float g2_leftstick_y = 0;

    public float g1_rightstick_x = 0;
    public float g2_rightstick_x = 0;

    //Create the variable that tracks the GamePad buttons
    public boolean g2_dpad_down  = false;
    public boolean g2_dpad_up    = false;
    public boolean g2_dpad_right = false;
    public boolean g2_dpad_left  = false;

    // Game pad Bumpers
    public boolean g1_left_bumper = false;
    public boolean g1_right_bumper = false;
    public boolean g2_right_bumper = false;
    public boolean g2_left_bumper  = false;

    //Gamepad triggers
    public float g2_right_trigger = 0;
    public float g2_left_trigger = 0;

    //Gamepad buttons
    public boolean g1_a = false;
    public boolean g1_b = false;
    public boolean g2_a = false;
    public boolean g2_b = false;
    public boolean g2_y = false;
    public boolean g1_dpad_up = false;
    public boolean g1_dpad_down = false;

    //Create the gyroscope
    public BNO055IMU gyro;
    //Create the orientation variable for the robot position
    public Orientation orientation;
    //Create the variable for the degrees of the robot
    public int gyroDegrees = 0;
    //Create the variable that will calculate the angle of the joystick
    public int myangle = 0;
    //Create the variable that will calculate the power of the robot
    public float mypower = 0;
    //Create the variable that will keep the previous angle of the robot
    public int gyroResetValue = 0;
    //Create the variable that will calculate the rotation of the robot
    public float myrot = 0;
    //Create slowmode variable
    public boolean slowmode = false;

    public boolean ready = false;
    public boolean initRun = false;
    public int stage = 0;

    public boolean sideDown = false;

    //Initialize the defined objects
    public void init() {
        //Add the motors to the configuration on the phones
        leftFrontDrive = hardwareMap.dcMotor.get("LF");
        rightFrontDrive = hardwareMap.dcMotor.get("RF");
        rightBackDrive = hardwareMap.dcMotor.get("RB");
        leftBackDrive = hardwareMap.dcMotor.get("LB");
        intakeLeft = hardwareMap.dcMotor.get("IL");
        intakeRight = hardwareMap.dcMotor.get("IR");
        liftMotor = hardwareMap.dcMotor.get("LM");

        //Add servos to the configuration on the phones
        bodyTwistServo = hardwareMap.servo.get("BodyS");   // Turns Skystone from front to back of robot
        blockTwistServo = hardwareMap.servo.get("BlockS"); // Turns Skystone Left and right 90 degrees
        clampServo = hardwareMap.servo.get("CS"); // Grabs the Skystone from the top. Located in the arm
        blockServo = hardwareMap.servo.get("BS"); // Placed on the side, for autonomous SKYSTONE selection
        siteServo = hardwareMap.servo.get("SS");  // Placed at the back of the robot, to grab the platform

        //Set the direction of the motors
        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);// F
        rightBackDrive.setDirection(DcMotor.Direction.REVERSE); // F
        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD); // R
        leftBackDrive.setDirection(DcMotor.Direction.FORWARD);  // R
        intakeLeft.setDirection(DcMotor.Direction.REVERSE);

        //Set the mode the motors are going to be running in
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Add the gyroscope to the configuration on the phones
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        gyro = hardwareMap.get(BNO055IMU.class, "gyro");
        gyro.initialize(parameters);
    }
}
