package Lab3.Handlers;

import Lab3.Reactor;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class BaseHandler  {
    public BaseHandler next;
    protected String extension;


    public void setNext(BaseHandler handler){
        this.next=handler;
    }

    abstract List<Reactor> handle(String filePath) throws JAXBException, IOException;
    public boolean canHandle(File file) {
        return !file.getName().endsWith(extension);
    }
}
