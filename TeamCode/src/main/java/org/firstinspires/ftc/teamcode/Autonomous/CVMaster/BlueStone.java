package org.firstinspires.ftc.teamcode.Autonomous.CVMaster;

import org.firstinspires.ftc.teamcode.Autonomous.AutoMethods;
import org.firstinspires.ftc.teamcode.Autonomous.WebcamCV;

public class BlueStone extends WebcamCV {
    public void runOpMode(){
        String skystoneLocation = stageSwitchingPipeline.skystone();
        if (skystoneLocation.equals("Right")){
            telemetry.addData("skystone location: ", "right");
        }else if(skystoneLocation.equals("Left")){
            telemetry.addData("skystone location: ", "left");
        }else{
            telemetry.addData("skystone location: ", "center");
        }
    }
}
