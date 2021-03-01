/**
 * QrcodereaderApplication
 * <p>
 * Main application
 * <p>
 * Author: Fernando Karnagi
 */
package sg.com.cyder.qrcodereader;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class QrcodereaderApplication {

    private int maxUploadSizeInMb = 20 * 1024 * 1024; // 20 MB

    public static void main(String[] args) {
        SpringApplication.run(QrcodereaderApplication.class, args);
    }

    // declare file size limit
    @Bean
    public TomcatServletWebServerFactory containerFactory() {
        return new TomcatServletWebServerFactory() {
            protected void customizeConnector(Connector connector) {
                int maxSize = maxUploadSizeInMb;
                super.customizeConnector(connector);
                connector.setMaxPostSize(maxSize);
                connector.setMaxSavePostSize(maxSize);
                if (connector.getProtocolHandler() instanceof AbstractHttp11Protocol) {

                    ((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(maxSize);
                    logger.info("Set MaxSwallowSize " + maxSize);
                }
            }
        };
    }
}