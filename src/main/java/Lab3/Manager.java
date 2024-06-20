package Lab3;

import Lab3.Readers.DBReader;
import Lab3.Readers.JSONHandler;
import Lab3.Readers.XMLHandler;
import Lab3.Readers.YAMLHandler;
import Lab3.Reactor.Reactor;
import Lab3.Reactor.ReactorDB;
import Lab3.Reactor.ReactorTreeNode;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;


public class Manager {
    final ArrayList<Reactor> reactors = new ArrayList<>();
    private final XMLHandler xmlHandler;
    ArrayList<ReactorDB> reactorDBS = new ArrayList<>();

    public Manager() {
        xmlHandler = new XMLHandler();
        xmlHandler.setNext(new JSONHandler());
        xmlHandler.next.setNext(new YAMLHandler());
    }

    public void getReactorsFromFile(String filePath) throws JAXBException, IOException {
        if (filePath == null) throw new IOException("File path is null");
        reactors.addAll(xmlHandler.handle(filePath));
    }

    public void addReactorsToTree(JTree tree) {
        DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) treeModel.getRoot();
        DefaultMutableTreeNode fileNode = new DefaultMutableTreeNode(reactors.get(reactors.size() - 1).source);
        treeModel.insertNodeInto(fileNode, rootNode, rootNode.getChildCount());
        for (Reactor reactor : reactors) {
            ReactorTreeNode reactorNode = ReactorTreeNode.createReactorNode(reactor);
            treeModel.insertNodeInto(reactorNode, fileNode, fileNode.getChildCount());
        }
    }

    public void readDatabase() throws IOException {
        if (reactors.isEmpty()) throw new IOException("Reactors are not initialized");
        DBReader reader = new DBReader();
        reactorDBS = reader.readDB(reactors);
        Calculator.calculateFuelLoadsForReactors(reactorDBS);
    }
}