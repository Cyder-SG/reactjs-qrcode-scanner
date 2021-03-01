/**
 * ServletInitializer
 * <p>
 * Servlet initialiser
 * <p>
 * Author: Fernando Karnagi
 */
package sg.com.cyder.qrcodereader;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(QrcodereaderApplication.class);
	}

}
