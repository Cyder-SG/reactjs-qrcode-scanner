const QrCode = require('qrcode-reader');
const Jimp = require("jimp");
const fs = require("fs");

const qr = new QrCode();

// const imageName = "qrcode-only.png";
const imageName = "another-qr-code.jpeg";

var buffer = fs.readFileSync(__dirname + '/' + imageName);
Jimp.read(buffer, function (err, image) {
    if (err) {
        console.error(err);
        // TODO handle error
    }
    qr.callback = function (err, value) {
        if (err) {
            console.log("Error happened")
            console.error(err);
        }
        console.log(value.result);
        console.log(value);
    };
    qr.decode(image.bitmap);
});