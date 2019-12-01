package org.firstinspires.ftc.teamcode.Autonomous.Development;

import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.teamcode.Autonomous.AutoMethods;

import java.io.File;

public class InitGyro extends AutoMethods {
    //Begin the program with the runOpMode() method of LinearOpMode
    public void runOpMode() {

        //Run our defined method initRobot() to initialize devices
        initRobot();

        //Wait for the driver to press start on the phone
        waitForStart();

        //Records the current position into a Text File
        orientation = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
        int StartingOrientation = (int) orientation.firstAngle;
        String filename = "Gyro_pos.txt";
        File file = AppUtil.getInstance().getSettingsFile(filename);
        ReadWriteFile.writeFile(file, String.valueOf(StartingOrientation));


        //Move forwards at half speed
        //gyroMove(0, 0.4, 10, 0);
    }

}
