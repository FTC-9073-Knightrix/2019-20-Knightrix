package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.ArrayList;
import java.util.List;

public abstract class AutoHardwareMap extends LinearOpMode {
    //Create the four motors
    public DcMotor leftFrontDrive;
    public DcMotor rightFrontDrive;
    public DcMotor rightBackDrive;
    public DcMotor leftBackDrive;

    public DcMotor centerEncoder;

    public Servo blockServo;

    //Create the gyroscope
    public BNO055IMU gyro;
    //Create the orientation variable for the robot position
    public Orientation orientation;
    //Create the angle tracker
    public double angle = 0;

    public ModernRoboticsI2cRangeSensor RRB;
    public ModernRoboticsI2cRangeSensor RLB;

    //amount of clicks per unit
    //15727.5 clicks
    //457.5 cm
    //180.13 in
    public final double ENCCM = 15727.5/457.5;
    public final double ENCIN = 15727.5/180.13;

    static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    static final String LABEL_FIRST_ELEMENT = "Stone";
    static final String LABEL_SECOND_ELEMENT = "Skystone";
    static final String VUFORIA_KEY = "AYl7ALf/////AAABmYRUZlrZD0yrpIpwu/lCqI404R8xljtOfAC2CqbtCpS8cdQOxNeRkjlt2adr+rxsSRHbQ8BerXEveMhl/jmXPM8GaYKri+E1j+LZplYraYHpWu6YXtceQQ24UIRIDKtRXkzUY9Hp5QY+Zrie7bELZTAm21wWSNLtF1XPd2XyEUa0Lw96c5BpczME5VhEQdVCfHA1TLnOEsQutrw173uOiZvvKdeEV1bGeRAlfkcf99ffKvTlVMZORlMCksWeKcatZRoC/zBXphIJMETpgYZCkmy59usG/JxL6rQrAjXHaLM60fzvLm2E9Fl5guhDrydctoHye8+Zla8Uh0tFAqF14YaElg2mmhAci59Nq2c1hher";
    VuforiaLocalizer vuforia = null;
    TFObjectDetector tfod;
    //public float stoneleft;
    //public float stonetop;
    //public float stoneright;
    //public float stonebottom;
    public List<Float> stoneleft = new ArrayList<Float>();
    public List<Float> stonetop = new ArrayList<Float>();
    public List<Float> stoneright = new ArrayList<Float>();
    public List<Float> stonebottom = new ArrayList<Float>();
    public int found = -1;

    public VuforiaTrackables targetsSkyStone = null;
    public VuforiaTrackable stoneTarget = null;
    public double VuX = 0;
}
