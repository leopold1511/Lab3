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
    final ArrayList<Reactor> reactors;
    private final XMLHandler xmlHandler;
    ArrayList<ReactorDB> reactorDBS;


    public Manager() {
        reactors=new ArrayList<>();
        xmlHandler=new XMLHandler();
        xmlHandler.setNext(new JSONHandler());
        xmlHandler.next.setNext(new YAMLHandler());

    }

    public void getReactorsFromFile(String filePath) throws JAXBException, IOException {
        if(reactors==null) throw new IOException("Extension is not correct");
        reactors.addAll(xmlHandler.handle(filePath));
    }
    public void getNodeForLastReactors(JTree tree){
        DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) treeModel.getRoot();
        DefaultMutableTreeNode fileNode=new DefaultMutableTreeNode(reactors.get(reactors.size()-1).source);
        treeModel.insertNodeInto(fileNode, rootNode, rootNode.getChildCount());
        for (Reactor reactor : reactors) {
            ReactorTreeNode reactorNode = ReactorTreeNode.createReactorNode(reactor);
            treeModel.insertNodeInto(reactorNode, fileNode, fileNode.getChildCount());
        }
    }
    public void readDatabase() {
        if (reactors == null){
            System.out.println("Сначала прочитайте типы реакторов!");
            return;
        }
        DBReader reader = new DBReader();
        reactorDBS=reader.readDB(reactors);
        Calculator.calculateFuelLoad(reactorDBS);
    }

}
