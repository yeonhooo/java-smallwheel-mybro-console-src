package smallwheel.mybro.common;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * 프로퍼티 파일 관리 
 * 
 * @author yeonhooo
 *
 */
public final class ContextMaster {
	
	private final static Logger LOGGER = Logger.getLogger(ContextMaster.class);
	private static ResourceBundle resources;

	/**
	 * Don't let anyone instantiate this class.
	 */
	private ContextMaster() { };
	
	public static String getString(String name) {
		try {
			// resources = ResourceBundle.getBundle("conf.environment" + filename, Locale.getDefault());
			FileInputStream fis = new FileInputStream("."+ File.separator+"environment.properties");
			resources = new PropertyResourceBundle(fis);
		} catch (MissingResourceException | IOException mre) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(" ContextMaster getString() ", mre);
			}
		}
		return resources.getString(name);
	}

	public static void reload() {
		try {
			resources = ResourceBundle.getBundle(".\\environment.properties", Locale.getDefault());
		} catch (MissingResourceException mre) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(" ContextMaster reload() ", mre);
			}
		}
	}
}
