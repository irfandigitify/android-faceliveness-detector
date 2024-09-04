package com.google.mediapipe.examples.facelandmarker
/*
 * Copyright 2023 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.util.Log
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.google.mediapipe.tasks.vision.facelandmarker.FaceLandmarkerResult

/**
 *  This ViewModel is used to store face landmarker helper settings
 */
class MainViewModel : ViewModel() {
    var leftProgressValue =0
    var rightProgressValue =0
    var upProgressValue =0
    var downProgressValue =0

    private var _delegate: Int = FaceLandmarkerHelper.DELEGATE_CPU
    private var _minFaceDetectionConfidence: Float =
        FaceLandmarkerHelper.DEFAULT_FACE_DETECTION_CONFIDENCE
    private var _minFaceTrackingConfidence: Float = FaceLandmarkerHelper
        .DEFAULT_FACE_TRACKING_CONFIDENCE
    private var _minFacePresenceConfidence: Float = FaceLandmarkerHelper
        .DEFAULT_FACE_PRESENCE_CONFIDENCE
    private var _maxFaces: Int = FaceLandmarkerHelper.DEFAULT_NUM_FACES

    val currentDelegate: Int get() = _delegate
    val currentMinFaceDetectionConfidence: Float
        get() =
            _minFaceDetectionConfidence
    val currentMinFaceTrackingConfidence: Float
        get() =
            _minFaceTrackingConfidence
    val currentMinFacePresenceConfidence: Float
        get() =
            _minFacePresenceConfidence
    val currentMaxFaces: Int get() = _maxFaces

    fun setDelegate(delegate: Int) {
        _delegate = delegate
    }

    fun setMinFaceDetectionConfidence(confidence: Float) {
        _minFaceDetectionConfidence = confidence
    }
    fun setMinFaceTrackingConfidence(confidence: Float) {
        _minFaceTrackingConfidence = confidence
    }
    fun setMinFacePresenceConfidence(confidence: Float) {
        _minFacePresenceConfidence = confidence
    }

    fun setMaxFaces(maxResults: Int) {
        _maxFaces = maxResults
    }
    fun checkHeadRotation(faceLandmarkerResult: FaceLandmarkerResult?, progressBar: MultiLevelCircularProgressBar) {
        if (faceLandmarkerResult != null && faceLandmarkerResult.faceLandmarks().isNotEmpty()) {
            val landmarks = faceLandmarkerResult.faceLandmarks().get(0)

            if (landmarks.isNotEmpty()) {
                val noseTip = landmarks[4]
                val leftEyeOuter = landmarks[33]
                val rightEyeOuter = landmarks[263]
                val chin = landmarks[152]
                val forehead = landmarks[10]

                val horizontalRotation = when {
                    noseTip.x() > rightEyeOuter.x() -> "left"
                    leftEyeOuter.x() > noseTip.x() -> "right"
                    else -> "center"
                }


                // Check vertical rotation
                val averageY = (forehead.y() + chin.y()) / 2
                val verticalRotation = when {
                    noseTip.y() < averageY -> "up"
                    noseTip.y() > averageY -> "down"
                    else -> "center"
                }
                when (horizontalRotation) {
                    "left" -> leftProgressValue += 5
                    "right" ->  downProgressValue+= 5
                }
                when (verticalRotation) {
                    "up" -> rightProgressValue += 5
                    "down" -> upProgressValue += 5
                }

                progressBar.progressLeft=leftProgressValue.toFloat()
                progressBar.progressRight=rightProgressValue.toFloat()
                progressBar.progressUp=upProgressValue.toFloat()
                progressBar.progressDown=downProgressValue.toFloat()
                Log.e("Rotation Check", "Head rotation: Horizontal - $horizontalRotation, Vertical - $verticalRotation")
            }
        }
    }

}