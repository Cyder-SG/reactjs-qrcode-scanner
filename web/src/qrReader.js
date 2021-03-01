/** @format */

import React, { useEffect, useRef, useState } from 'react';
import axios from 'axios';

const QReader = ({ facingMode }) => {

    const [text, setText] = useState(null);
    const [cameraId, setCameraId] = useState(null);
    const [cameraPlaying, setCameraPlaying] = useState(false);

    const gotStream = (stream) => {
        window.stream = stream; // make stream available to console 
        window.imageCapture = new ImageCapture(stream.getTracks()[0]);
        console.log(window.imageCapture)

        const videoElement = document.querySelector('video');
        videoElement.srcObject = stream;

        setInterval(checkQrCode, 5000);

        // refresh button list in case labels have become available
        return navigator.mediaDevices.enumerateDevices();
    }

    const uploadToServer = async (base64Str) => {

        // prepare the base64 image
        // const base64 = 'data:image/png;base64,' + base64Str;

        // convert base64 to post body blob
        const blob = await fetch(base64Str).then(res => res.blob());

        const formData = new FormData();
        formData.append('qr_base64', blob, 'qr_code.png');

        // Post the form, just make sure to set the 'Content-Type' header
        const res = await axios.post('http://localhost:8080/upload', formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });

        // print status
        if (res.data) {
            setText(res.data.qrText);
        }
    }

    const imgToBase64 = (img) => {
        const canvas = document.createElement('canvas');
        const ctx = canvas.getContext('2d');
        canvas.width = img.width;
        canvas.height = img.height;

        // I think this won't work inside the function from the console
        img.crossOrigin = 'anonymous';

        ctx.drawImage(img, 0, 0);

        return canvas.toDataURL();
    }

    const checkQrCode = () => {
        window.imageCapture.grabFrame()
            .then(imageBitmap => {
                const imageBase64 = imgToBase64(imageBitmap);
                console.log("get image bitmap and uploading to server");
                uploadToServer(imageBase64);
            })
            .catch(error => {
                console.log("error during image capture: ", error)
            });

    }

    // handle media initialisation error
    const handleError = (error) => {
        console.log('navigator.MediaDevices.getUserMedia error: ', error.message, error.name);
    }

    useEffect(() => {
        const init = async () => {
            setCameraPlaying(false);
            const tempCamId = await initialiseCamera();
            await start(tempCamId);
        }
        init();

        return () => {
            // clean up
            stopVideo();
        };
    }, []);

    const initialiseCamera = async () => {

        try {
            let cam1 = null; // stores running rear camera ID
            let cam2 = null; // stores running default camera ID

            const devices = await navigator.mediaDevices.enumerateDevices();

            // labels of rear camera, detected by popular mobile devices
            const rearCameraLabels = ['rear', 'back', 'environment'];

            // labels of front camera, detected by popular mobile devices
            const frontCameraLabels = ['front', 'user', 'face'];

            const cameraLabelsToCheck = facingMode === 'environment' ? rearCameraLabels : frontCameraLabels;

            devices.forEach(device => {
                if (device.kind === 'videoinput') {
                    cam2 = device.deviceId;
                    const label = device.label.toLowerCase();

                    // always get the latest camera, based on 'facingMode' prop
                    // this was tested in Samsung Galaxy Note 10
                    if (cameraLabelsToCheck.filter(e => label.indexOf(e) >= 0).length > 0) {
                        cam1 = device.deviceId;
                    }
                }
            })

            if (cam1 != null) {
                setCameraId(cam1);
                return cam1;
            } else {
                setCameraId(cam2);
                return cam2;
            }
        } catch (e) {
            setCameraId(null);
            return null;
        }

    }

    const stopVideo = async () => {
        if (window.stream) {
            window.stream.getTracks().forEach(track => {
                track.stop();
            });
        }
    }

    const start = async (camId) => {
        // stop the currently running video
        stopVideo();

        // use the rear camera, if any, else any camera
        const constraints = {
            video: { deviceId: camId ? { exact: camId } : undefined }
        };

        // get medias and further process
        navigator.mediaDevices.getUserMedia(constraints).then(gotStream).catch(handleError);
    }

    return (
        <div className="qrCode">
            <video
                style={{
                    maxWidth: 175,
                    margin: "auto",
                }}
                onPlaying={e => {
                    setCameraPlaying(true);
                }} id="video" playsInline autoPlay></video>

            <p>
                Last detected QR:<br/><br/>{text}
            </p>
        </div>
    )

}

export default QReader;
