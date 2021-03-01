/**
 * QRCoreResponseEntity
 * <p>
 * Wrapper of QRText Text for JSON APO Response
 * <p>
 * Author: Fernando Karnagi
 */
package sg.com.cyder.qrcodereader.entity;

import java.util.Objects;

public class QRTextResponseEntity {

    private String qrText;

    public String getQrText() {
        return qrText;
    }

    public void setQrText(String qrText) {
        this.qrText = qrText;
    }

    public QRTextResponseEntity() {
    }

    public QRTextResponseEntity(String qrText) {
        this.qrText = qrText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QRTextResponseEntity that = (QRTextResponseEntity) o;
        return Objects.equals(qrText, that.qrText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(qrText);
    }

    @Override
    public String toString() {
        return "QRCoreResponseEntity{" +
                "qrText='" + qrText + '\'' +
                '}';
    }
}
