package nl.fontys.s3;

import nu.pattern.OpenCV;
import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.videoio.VideoCapture;


public class Main {
    private static final String SERVER_URL = "http://localhost:8080/cameras"; // Temp Host URL
    private static final String MAC_ADDRESS = MacAddressFetcher.getMacAddress();

    private static final int CAMERA_INDEX = 0; // default camera index

    public static void main(String[] args) {
        OpenCV.loadShared();

        VideoCapture camera = new VideoCapture(CAMERA_INDEX);
        if (!camera.isOpened()) {
            System.out.println("Error: Camera not accessible");
            return;
        }

        Mat frame = new Mat();

        while (true) {
            if (camera.read(frame)) {
                HighGui.imshow("Camera Feed", frame);

                String macAddress = MAC_ADDRESS;

                if (macAddress == null) {
                    System.out.println("MAC Address is null");
                    return;
                }

                String jsonPayload = "{ \"macAddress\": \"" + macAddress + "\" }";
                HttpDataSender.sendData(SERVER_URL, jsonPayload);

                // Exit on 'Esc' key
                if (HighGui.waitKey(30) == 27) {
                    break;
                }
            } else {
                System.out.println("Error: Could not read frame");
                break;
            }
        }
        camera.release();
        HighGui.destroyAllWindows();
    }

}
