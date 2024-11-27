package nl.fontys.s3;

import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.videoio.VideoCapture;


public class Main {
    private static final String SERVER_URL = "http://localhost:8080/cameras"; // Temp Host URL

    private static final String MAC_ADDRESS = MacAddressFetcher.getMacAddress();
    private static final String prototxtPath = "models/deploy.prototxt";
    private static final String modelPath = "models/mobilenet_iter_73000.caffemodel";

    private static final int CAMERA_INDEX = 0; // default camera index

    public static void main(String[] args) {
        OpenCV.loadLocally();

        if (MAC_ADDRESS == null) {
            System.out.println("MAC Address not available.");
            return;
        }

        MobileNetSSDPeopleDetector detector = new MobileNetSSDPeopleDetector(prototxtPath, modelPath);

        VideoCapture camera = new VideoCapture(CAMERA_INDEX);

        if (!camera.isOpened()) {
            System.out.println("Error: Camera not accessible");
            return;
        }

        Mat frame = new Mat();

        int prevPeopleDetected = 0;

        while (camera.read(frame)) {
            int peopleDetections = detector.detectPeople(frame);

            if (peopleDetections >= 0 && peopleDetections != prevPeopleDetected) {
                HttpDataSender.sendData(SERVER_URL, MAC_ADDRESS, peopleDetections);
                prevPeopleDetected = peopleDetections;

            }

            HighGui.imshow("People Detection", frame);

            if (HighGui.waitKey(1) == 27) break;  // Exit on 'Esc' key

            try {
                Thread.sleep(5000); // Wait for 5 seconds between checks
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        camera.release();
        HighGui.destroyAllWindows();
    }
}
