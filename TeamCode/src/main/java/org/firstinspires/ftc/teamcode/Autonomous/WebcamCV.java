//THIS IS WITH INIT3, NAIVE, WEBCAM


package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.ArrayList;
import java.util.List;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;

@TeleOp (name="WebcamCV")

public abstract class WebcamCV extends AutoMethods
{

    @Override
    public void runOpMode()
    {
        /**
         * NOTE: Many comments have been omitted from this sample for the
         * sake of conciseness. If you're just starting out with EasyOpenCv,
         * you should take a look at {@link InternalCameraExample} or its
         * webcam counterpart, {@link WebcamCV} first.
         */


        waitForStart();

        while (opModeIsActive())
        {
            telemetry.addData("Num contours found", stageSwitchingPipeline.getNumContoursFound());
            telemetry.addData("Skystone", stageSwitchingPipeline.skystone());
            telemetry.update();
            sleep(100);
        }
    }

    /*
     * With this pipeline, we demonstrate how to change which stage of
     * is rendered to the viewport when the viewport is tapped. This is
     * particularly useful during pipeline development. We also show how
     * to get data from the pipeline to your OpMode.
     */
    public static class StageSwitchingPipeline extends OpenCvPipeline
    {
        Mat yCbCrChan2Mat = new Mat();
        Mat thresholdMat = new Mat();
        Mat contoursOnFrameMat = new Mat();
        Mat croppedMat = new Mat();
        Rect cropArea = new Rect(70,30,490,110);
        List<MatOfPoint> contoursList = new ArrayList<>();
        int numContoursFound;

        enum Stage
        {
            YCbCr_CHAN2,
            THRESHOLD,
            CONTOURS_OVERLAYED_ON_FRAME,
            RAW_IMAGE,
        }

        private Stage stageToRenderToViewport = Stage.YCbCr_CHAN2;
        private Stage[] stages = Stage.values();

        @Override
        public void onViewportTapped()
        {
            /*
             * Note that this method is invoked from the UI thread
             * so whatever we do here, we must do quickly.
             */

            int currentStageNum = stageToRenderToViewport.ordinal();

            int nextStageNum = currentStageNum + 1;

            if(nextStageNum >= stages.length)
            {
                nextStageNum = 0;
            }

            stageToRenderToViewport = stages[nextStageNum];
        }

        @Override
        public Mat processFrame(Mat input)
        {
            contoursList.clear();

            /*
             * This pipeline finds the contours of yellow blobs such as the Gold Mineral
             * from the Rover Ruckus game.
             */
            Imgproc.cvtColor(input, yCbCrChan2Mat, Imgproc.COLOR_RGB2YCrCb);
            Core.extractChannel(yCbCrChan2Mat, yCbCrChan2Mat, 2);
            Imgproc.threshold(yCbCrChan2Mat, thresholdMat, 102, 255, Imgproc.THRESH_BINARY_INV);
            Imgproc.findContours(thresholdMat, contoursList, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
            numContoursFound = contoursList.size();
            input.copyTo(contoursOnFrameMat);
            Imgproc.drawContours(contoursOnFrameMat, contoursList, -1, new Scalar(0, 0, 255), 3, 8);
            croppedMat = yCbCrChan2Mat.submat(cropArea);

            switch (stageToRenderToViewport)
            {
                case YCbCr_CHAN2:
                {
                    return yCbCrChan2Mat;
                }

                /*case THRESHOLD:
                {
                    return thresholdMat;
                }

                case CONTOURS_OVERLAYED_ON_FRAME:
                {
                    return contoursOnFrameMat;
                }

                case RAW_IMAGE:
                {
                    return input;
                }*/

                default:
                {
                    //return input;
                    return croppedMat;
                }
            }
        }

        public int getNumContoursFound()
        {
            return numContoursFound;
        }

        public String skystone()
        {
            if (croppedMat != null) {
                //Left
                double[] left = croppedMat.get(55, 110);
                //Center
                double[] center = croppedMat.get(55, 270);
                //Right
                double[] right = croppedMat.get(55, 380);
                //Calculate: black = 0
                if (left[0] < center[0]) {
                    if (center[0] < right[0]) {
                        return "Right";
                    }
                    else {
                        return "Center";
                    }
                }
                else {
                    if (left[0] < right[0]) {
                        return "Right";
                    }
                    else {
                        return "Left";
                    }
                }
            }
            return "";
        }
    }
}