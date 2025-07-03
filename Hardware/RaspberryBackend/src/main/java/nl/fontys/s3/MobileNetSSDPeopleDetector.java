package nl.fontys.s3;

import org.opencv.core.*;
import org.opencv.dnn.*;

import java.util.ArrayList;
import java.util.List;

public class MobileNetSSDPeopleDetector {

    private final Net net;
    private static final int PERSON_CLASS_ID = 15;  // 15 is 'person' in COCO classes
    private static final float CONFIDENCE_THRESHOLD = 0.5f;

    public MobileNetSSDPeopleDetector(String prototxtPath, String modelPath) {
        net = Dnn.readNetFromCaffe(prototxtPath, modelPath);
    }

    public int detectPeople(Mat frame) {
        List<Rect> detections = new ArrayList<>();

        Mat blob = Dnn.blobFromImage(frame, 0.007843, new Size(300, 300), new Scalar(127.5, 127.5, 127.5), false, false);
        net.setInput(blob);
        Mat detectionsMat = net.forward();

        detectionsMat = detectionsMat.reshape(1, (int) detectionsMat.total() / 7);
        int peopleCounter = 0;

        for (int i = 0; i < detectionsMat.rows(); i++) {
            float confidence = (float) detectionsMat.get(i, 2)[0];
            int classId = (int) detectionsMat.get(i, 1)[0];

            // Only count Person if Confidence is above threshold
            if (confidence > CONFIDENCE_THRESHOLD && classId == PERSON_CLASS_ID) {
                int left = (int) (detectionsMat.get(i, 3)[0] * frame.cols());
                int top = (int) (detectionsMat.get(i, 4)[0] * frame.rows());
                int right = (int) (detectionsMat.get(i, 5)[0] * frame.cols());
                int bottom = (int) (detectionsMat.get(i, 6)[0] * frame.rows());
                detections.add(new Rect(left, top, right - left, bottom - top));
                peopleCounter ++;
            }
        }
        return peopleCounter;
    }
}

