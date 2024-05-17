package Lab3;

import Lab3.Handlers.JSONHandler;
import Lab3.Handlers.XMLHandler;
import Lab3.Handlers.YAMLHandler;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Manager {
    private final Map<Integer, List<Reactor>> mapOfFiles;
    private final XMLHandler xmlHandler;
    private final JSONHandler jsonHandler;
    private final YAMLHandler yamlHandler;
    int lastNumber;

    public Manager() {
        mapOfFiles=new HashMap<>();
        xmlHandler=new XMLHandler();
        jsonHandler=new JSONHandler();
        yamlHandler=new YAMLHandler();
        xmlHandler.setNext(jsonHandler);
        jsonHandler.setNext(yamlHandler);

    }

    public void getReactorsFromFile(String filePath) throws JAXBException, IOException {
        List<Reactor> reactors=xmlHandler.handle(filePath);
        if(reactors==null) throw new IOException("Extension is not correct");
        mapOfFiles.put(mapOfFiles.size(),xmlHandler.handle(filePath));
        lastNumber= mapOfFiles.size()-1;
    }
    public void getNodeForLastReactor(JTree tree){
        DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) treeModel.getRoot();
        DefaultMutableTreeNode fileNode=new DefaultMutableTreeNode(mapOfFiles.get(lastNumber).get(0).source);
        treeModel.insertNodeInto(fileNode, rootNode, rootNode.getChildCount());
        List<Reactor> lastReactors = mapOfFiles.get(lastNumber);
        for (Reactor reactor : lastReactors) {
            ReactorTreeNode reactorNode = ReactorTreeNode.createReactorNode(reactor);
            treeModel.insertNodeInto(reactorNode, fileNode, fileNode.getChildCount());
        }
    }
}
