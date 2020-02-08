package org.firstinspires.ftc.teamcode.Autonomous.Development;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Autonomous.AutoMethods;

@Autonomous(name="Lift Test", group="Development")
public class LiftMotor extends AutoMethods {
    public void runOpMode() {
        initRobot();

        waitForStart();
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor.setTargetPosition(100);
        liftMotor.setPower(1);
        sleep(2000);
        liftMotor.setTargetPosition(0);
    }
}
