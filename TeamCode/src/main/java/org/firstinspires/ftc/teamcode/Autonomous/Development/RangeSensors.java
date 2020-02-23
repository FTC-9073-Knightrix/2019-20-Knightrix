package org.firstinspires.ftc.teamcode.Autonomous.Development;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Autonomous.AutoMethods;

@Autonomous(name = "Range")
public class RangeSensors extends AutoMethods {
    public void runOpMode() {
        initRobot();

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Range Left", leftRange.getDistance(DistanceUnit.CM));
            telemetry.addData("Range Right", rightRange.getDistance(DistanceUnit.CM));
            telemetry.update();
        }
    }
}
