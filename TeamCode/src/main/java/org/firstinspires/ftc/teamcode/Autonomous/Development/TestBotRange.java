package org.firstinspires.ftc.teamcode.Autonomous.Development;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Autonomous.AutoMethods;

@Autonomous(name="TestBotRange", group="Development")

public class TestBotRange extends AutoMethods {
    public void runOpMode() {
        initRobot();
        waitForStart();

        while (getRuntime() != -1) {
//            rightBackDrive.setPower(Range.clip(1 * (1 - (5 / RRB.getDistance(DistanceUnit.CM))) / 15,-1,1));
//            leftBackDrive.setPower(Range.clip(-1 * (1 - (5 / RLB.getDistance(DistanceUnit.CM))) / 15,-1,1));
            //telemetry.addData("Right", RRB.getDistance(DistanceUnit.CM));
            //telemetry.addData("Left", RLB.getDistance(DistanceUnit.CM));
            telemetry.update();
        }
    }
}
