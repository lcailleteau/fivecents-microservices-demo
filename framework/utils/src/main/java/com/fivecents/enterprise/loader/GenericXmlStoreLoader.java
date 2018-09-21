package com.fivecents.enterprise.loader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Named;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * Utility bean to pre-load clients from an xml file.
 * 
 * @author Laurent CAILLETEAU
 */
@Named
@ApplicationScoped
public class GenericXmlStoreLoader {
	/**
	 * Search service.
	 * @param id
	 * @return
	 */
	@SuppressWarnings("restriction")
	public <T> T loadFromResource(String resourceFileName, Class<?>... classesToBeBound) {
		T result = null;
		
		// Loads the resource file.
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourceFileName);
		
		// Extract JAXB objects from the resource InputStream.
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(classesToBeBound);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			result = (T) unmarshaller.unmarshal(inputStream);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return result;
	}
}
