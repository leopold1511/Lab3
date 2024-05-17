package Lab3.Handlers;

import Lab3.Reactor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.databind.type.CollectionType;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class YAMLHandler extends BaseHandler {

    public YAMLHandler() {
        this.extension = ".yaml";
    }

    @Override
    public List<Reactor> handle(String filePath) throws IOException, JAXBException {
        File file = new File(filePath);
        if (canHandle(file)) {
            if(next!=null)return next.handle(filePath);
            return null;
        } else {
            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, Reactor.class);
            List<Reactor> reactors=objectMapper.readValue(file, collectionType);
            reactors.forEach(reactor -> reactor.source=file.getName());
            return reactors;
        }
    }
}