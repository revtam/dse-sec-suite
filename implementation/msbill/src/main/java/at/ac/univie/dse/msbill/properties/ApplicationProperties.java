package at.ac.univie.dse.msbill.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationProperties {

	private Properties props;
	private static ApplicationProperties instance = null;

	private ApplicationProperties() {
		try {
			InputStream propFileReader = this.getClass().getClassLoader().getResourceAsStream("application.properties");
			props = new Properties();
			props.load(propFileReader);
			propFileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ApplicationProperties getInstance() {
		if (instance == null) {
			instance = new ApplicationProperties();
		}
		return instance;
	}

	public String getProperty(String key) {
		return this.props.getProperty(key);
	}

	public Double getDoubleProperty(String key) {
		return Double.parseDouble(this.props.getProperty(key));
	}

	public Long getLongProperty(String key) {
		return Long.parseLong(this.props.getProperty(key));
	}

}
