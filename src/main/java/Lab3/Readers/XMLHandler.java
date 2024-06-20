package Lab3.Readers;


import Lab3.Reactor.Reactor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class XMLHandler extends BaseHandler {

    public XMLHandler() {
        this.extension = ".xml";
    }

    @Override
    public List<Reactor> handle(String filePath) throws JAXBException, IOException {
        File file=new File(filePath);
        if (canHandle(file)) {
            if(next!=null)return next.handle(filePath);
            return null;
        } else {
            JAXBContext jaxbContext = JAXBContext.newInstance(ReactorType.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            ReactorType reactorType = (ReactorType) unmarshaller.unmarshal(file);
            List<Reactor> reactors=reactorType.getReactors();
            reactors.forEach(reactor -> reactor.source=file.getName());
            return reactors;
        }

    }

    @XmlRootElement(name = "ReactorType")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ReactorType {
        @XmlElement(name = "Reactor")
        private List<Reactor> reactors;

        public List<Reactor> getReactors() {
            return reactors;
        }
    }



}
