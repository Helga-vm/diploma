<<<<<<< HEAD
package diploma.Analizers;
=======
package Analizers;
>>>>>>> master

import net.n3.nanoxml.IXMLElement;
import net.n3.nanoxml.XMLElement;

public abstract class AnalizerBase { // base class for analizators
	protected XMLElement xml;
<<<<<<< HEAD
	protected abstract void Analize(); // main analization method
=======
	public abstract void Analize(); // main analization method
>>>>>>> master
	public AnalizerBase(XMLElement _xml) {
		this.xml = _xml;
	}
	public XMLElement getResult() {
		return this.xml;
	}
	public void setXmlElement(XMLElement _xml) {
		this.xml = _xml;
	}
	
}
