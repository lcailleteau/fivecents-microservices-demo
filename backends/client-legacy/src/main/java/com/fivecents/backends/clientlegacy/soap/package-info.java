/*
 * JAXB annotations for the package. We prefer qualified XML form elements, 
 * but generated JAXB elements set it to unqualified. We change that here.
 *
 * @author Laurent CAILLETEAU
 */
@XmlSchema(
		namespace = "http://clientlegacy.fivecents.com/",
		xmlns = {
			@XmlNs(prefix="leg", namespaceURI="http://clientlegacy.fivecents.com/")
		},
		elementFormDefault = XmlNsForm.QUALIFIED)
package com.fivecents.backends.clientlegacy.soap;

import javax.xml.bind.annotation.XmlSchema;
import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;