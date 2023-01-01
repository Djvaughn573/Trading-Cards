package com.vaughn.tradingcards.ui.preference;

import java.io.File;
import java.io.IOException;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.osgi.service.datalocation.Location;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlPreferenceStore extends XML implements IPreferenceStoreExt {

	public Element eRoot, eUser, eDefault;
	public NodeList rootList, userList, defaultList;
	
	public XmlPreferenceStore(Location filePath, String fileFolder, String fileName) {
		super(filePath, fileFolder, fileName);
		defaultConstructor();
//		setupDefault();
		this.filePath = locationToString(filePath);
		this.fileFolder = fileFolder;
		this.fileName = fileName;
		this.file = new File(this.filePath + File.separator + this.fileFolder 
				+ File.separator + this.fileName + ".xml");
		
		this.createDefaultElements();
//		this.createNodeLists();
	}

	@Override
	public boolean loadFile(File file) {
		if (document == null) {
			document = setDocument();
		}
		try {
			document = documentBuilder.parse(file);
			document.normalize();
			
			//	The reason to override. Elements need to be set after parse.
			eRoot = document.getDocumentElement();
			eUser = getChildElement(eRoot, "userStore");
			eDefault = getChildElement(eRoot, "defaultStore");
			
			return true;
		} catch (SAXException | IOException event) {
			return false;
		}
	}
	
	public void createDefaultElements() {
		eRoot = document.createElement(fileName);
		eUser = document.createElement("userStore");
		eDefault = document.createElement("defaultStore");
		
		document.appendChild(eRoot);
		eRoot.appendChild(eUser);
		eRoot.appendChild(eDefault);
	}
	
	public void createNodeLists() {
		rootList = eRoot.getChildNodes();
		
		for(int loop = 0; loop < rootList.getLength(); loop++) {
			String name = rootList.item(loop).getNodeName();
			NodeList list = rootList.item(loop).getChildNodes();
			
			if (name == "userStore") {
				userList = list;
			}
			if (name == "defaultStore") {
				defaultList = list;
			}
		}
	}

	public Element newChildElement(Element parent, String childName) {
		Element eChild = document.createElement(childName);
		parent.appendChild(eChild);
		return eChild;
	}
	
	public Element getChildElement(Element parent, String childName) {
		//	Get the list of child nodes from the parent.
		NodeList list = parent.getChildNodes();
		
		//	Loop through the list to find the node with childName.
		for (int loop = 0; loop < list.getLength(); loop++) {
			String name = list.item(loop).getNodeName();
			if (name == childName) {
				return (Element) list.item(loop);
			}
		}
		
		//	If the childName was not found.
		return null;
	}

	@Override
	@Deprecated
	public void addPropertyChangeListener(IPropertyChangeListener listener) {
	}

	@Override
	@Deprecated
	public boolean contains(String name) {
		return false;
	}

	@Override
	@Deprecated
	public void firePropertyChangeEvent(String name, Object oldValue, Object newValue) {
	}

	@Override
	public boolean getBoolean(String name) {
		Element eBoolean = getChildElement(eUser, "boolean");
		Element eName = getChildElement(eBoolean, name);
		
		String value = eName.getTextContent();
		return Boolean.valueOf(value);
	}

	@Override
	public boolean getDefaultBoolean(String name) {
		Element eBoolean = getChildElement(eDefault, "boolean");
		Element eName = getChildElement(eBoolean, name);
		
		String value = eName.getTextContent();
		return Boolean.valueOf(value);
	}

	@Override
	public double getDefaultDouble(String name) {
		Element eDouble = getChildElement(eDefault, "double");
		Element eName = getChildElement(eDouble, name);
		
		String value = eName.getTextContent();
		return Double.valueOf(value);
	}

	@Override
	public float getDefaultFloat(String name) {
		Element eFloat = getChildElement(eDefault, "float");
		Element eName = getChildElement(eFloat, name);
		
		String value = eName.getTextContent();
		return Float.valueOf(value);
	}

	@Override
	public int getDefaultInt(String name) {
		Element eInt = getChildElement(eDefault, "int");
		Element eName = getChildElement(eInt, name);
		
		String value = eName.getTextContent();
		return Integer.valueOf(value);
	}

	@Override
	public long getDefaultLong(String name) {
		Element eLong = getChildElement(eDefault, "long");
		Element eName = getChildElement(eLong, name);
		
		String value = eName.getTextContent();
		return Long.valueOf(value);
	}

	@Override
	public String getDefaultString(String name) {
		Element eString = getChildElement(eDefault, "string");
		Element eName = getChildElement(eString, name);
		
		return eName.getTextContent();
	}

	@Override
	public double getDouble(String name) {
		Element eDouble = getChildElement(eUser, "double");
		Element eName = getChildElement(eDouble, name);
		
		String value = eName.getTextContent();
		return Double.valueOf(value);
	}

	@Override
	public float getFloat(String name) {
		Element eFloat = getChildElement(eUser, "float");
		Element eName = getChildElement(eFloat, name);
		
		String value = eName.getTextContent();
		return Float.valueOf(value);
	}

	@Override
	public int getInt(String name) {
		Element eInt = getChildElement(eUser, "int");
		Element eName = getChildElement(eInt, name);
		
		String value = eName.getTextContent();
		return Integer.valueOf(value);
	}

	@Override
	public long getLong(String name) {
		Element eLong = getChildElement(eUser, "long");
		Element eName = getChildElement(eLong, name);
		
		String value = eName.getTextContent();
		return Long.valueOf(value);
	}

	@Override
	public String getString(String name) {
		Element eString = getChildElement(eUser, "string");
		Element eName = getChildElement(eString, name);
		
		return eName.getTextContent();
	}

	@Override
	@Deprecated
	public boolean isDefault(String name) {
		return false;
	}

	@Override
	@Deprecated
	public boolean needsSaving() {
		return false;
	}

	@Override
	@Deprecated
	public void putValue(String name, String value) {
	}

	@Override
	@Deprecated
	public void removePropertyChangeListener(IPropertyChangeListener listener) {
	}

	@Override
	public void setDefault(String name, double value) {
		Element eDouble = getChildElement(eDefault, "double");
		if (eDouble == null) {
			eDouble = newChildElement(eDefault, "double");
		}
		
		Element eName = getChildElement(eDouble, name);
		if (eName == null) {
			eName = newChildElement(eDouble, name);
		}
		
		eName.setTextContent(String.valueOf(value));
	}

	@Override
	public void setDefault(String name, float value) {
		Element eFloat = getChildElement(eDefault, "float");
		if (eFloat == null) {
			eFloat = newChildElement(eDefault, "float");
		}
		
		Element eName = getChildElement(eFloat, name);
		if (eName == null) {
			eName = newChildElement(eFloat, name);
		}
		
		eName.setTextContent(String.valueOf(value));
	}

	@Override
	public void setDefault(String name, int value) {
		Element eInt = getChildElement(eDefault, "int");
		if (eInt == null) {
			eInt = newChildElement(eDefault, "int");
		}
		
		Element eName = getChildElement(eInt, name);
		if (eName == null) {
			eName = newChildElement(eInt, name);
		}
		
		eName.setTextContent(String.valueOf(value));
	}

	@Override
	public void setDefault(String name, long value) {
		Element eLong = getChildElement(eDefault, "long");
		if (eLong == null) {
			eLong = newChildElement(eDefault, "long");
		}
		
		Element eName = getChildElement(eLong, name);
		if (eName == null) {
			eName = newChildElement(eLong, name);
		}
		
		eName.setTextContent(String.valueOf(value));
	}

	@Override
	public void setDefault(String name, String defaultObject) {
		Element eString = getChildElement(eDefault, "string");
		if (eString == null) {
			eString = newChildElement(eDefault, "string");
		}
		Element eName = getChildElement(eString, name);
		if (eName == null) {
			eName = newChildElement(eString, name);
		}
		
		eName.setTextContent(defaultObject);
	}

	@Override
	public void setDefault(String name, boolean value) {
		Element eBoolean = getChildElement(eDefault, "boolean");
		if (eBoolean == null) {
			eBoolean = newChildElement(eDefault, "boolean");
		}
		
		Element eName = getChildElement(eBoolean, name);
		if (eName == null) {
			eName = newChildElement(eBoolean, name);
		}
		
		eName.setTextContent(String.valueOf(value));
	}

	@Override
	@Deprecated
	public void setToDefault(String name) {
	}

	@Override
	public void setValue(String name, double value) {
		Element eDouble = getChildElement(eUser, "double");
		if (eDouble == null) {
			eDouble = newChildElement(eUser, "double");
		}
		Element eName = getChildElement(eDouble, name);
		if (eName == null) {
			eName = newChildElement(eDouble, name);
		}
		
		eName.setTextContent(String.valueOf(value));
	}

	@Override
	public void setValue(String name, float value) {
		Element eFloat = getChildElement(eUser, "float");
		if (eFloat == null) {
			eFloat = newChildElement(eUser, "float");
		}
		Element eName = getChildElement(eFloat, name);
		if (eName == null) {
			eName = newChildElement(eFloat, name);
		}
		
		eName.setTextContent(String.valueOf(value));
	}

	@Override
	public void setValue(String name, int value) {
		Element eInt = getChildElement(eUser, "int");
		if (eInt == null) {
			eInt = newChildElement(eUser, "int");
		}
		Element eName = getChildElement(eInt, name);
		if (eName == null) {
			eName = newChildElement(eInt, name);
		}
		
		eName.setTextContent(String.valueOf(value));
	}

	@Override
	public void setValue(String name, long value) {
		Element eLong = getChildElement(eUser, "long");
		if (eLong == null) {
			eLong = newChildElement(eUser, "long");
		}
		Element eName = getChildElement(eLong, name);
		if (eName == null) {
			eName = newChildElement(eLong, name);
		}
		
		eName.setTextContent(String.valueOf(value));
	}

	@Override
	public void setValue(String name, String value) {
		Element eString = getChildElement(eUser, "string");
		if (eString == null) {
			eString = newChildElement(eUser, "string");
		}
		Element eName = getChildElement(eString, name);
		if (eName == null) {
			eName = newChildElement(eString, name);
		}
		
		eName.setTextContent(value);
	}

	@Override
	public void setValue(String name, boolean value) {
		Element eBoolean = getChildElement(eUser, "boolean");
		if (eBoolean == null) {
			eBoolean = newChildElement(eUser, "boolean");
		}
		
		Element eName = getChildElement(eBoolean, name);
		if (eName == null) {
			eName = newChildElement(eBoolean, name);
		}
		
		eName.setTextContent(String.valueOf(value));
	}

	@Override
	public short getShort(String name) {
		Element eShort = getChildElement(eUser, "short");
		Element eName = getChildElement(eShort, name);
		
		String value = eName.getTextContent();
		return Short.valueOf(value);
	}

	@Override
	public short getDefaultShort(String name) {
		Element eShort = getChildElement(eDefault, "short");
		Element eName = getChildElement(eShort, name);
		
		String value = eName.getTextContent();
		return Short.valueOf(value);
	}

	@Override
	public void setDefault(String name, short value) {
		Element eShort = getChildElement(eDefault, "short");
		if (eShort == null) {
			eShort = newChildElement(eDefault, "short");
		}
		
		Element eName = getChildElement(eShort, name);
		if (eName == null) {
			eName = newChildElement(eShort, name);
		}
		
		eName.setTextContent(String.valueOf(value));
	}

	@Override
	public void setValue(String name, short value) {
		Element eShort = getChildElement(eUser, "short");
		if (eShort == null) {
			eShort = newChildElement(eUser, "short");
		}
		
		Element eName = getChildElement(eShort, name);
		if (eName == null) {
			eName = newChildElement(eShort, name);
		}
		
		eName.setTextContent(String.valueOf(value));
	}
	
}
