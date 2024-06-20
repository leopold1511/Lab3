package Lab3;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class GUI extends JFrame {
    private JPanel rootPane;
    private JButton uploadFileButton;
    private JTree reactorTree;
    private JScrollPane scroll;
    private JButton regionButton;
    private JButton importDBButton;
    private JButton clearButton;
    private JButton operatorButton;
    private JButton countryButton;
    private final Manager manager = new Manager();
    String filePath;

    private final JTable jTable1;
    private final JDialog aggregationWindow;

    public GUI() {
        super("Reactors");
        setContentPane(rootPane);
        clearTree(reactorTree);
        this.setSize(500, 300);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jTable1 = new JTable();
        JScrollPane scrollPane = new JScrollPane(jTable1);
        aggregationWindow = new JDialog(this, "Агрегация");
        aggregationWindow.add(scrollPane);

        uploadFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            int returnValue = fileChooser.showOpenDialog(GUI.this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                filePath = selectedFile.getAbsolutePath();
            }
            try {
                manager.getReactorsFromFile(filePath);
                manager.addReactorsToTree(reactorTree);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(GUI.this, "The file does not fit the format " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

            }
        });
        this.setVisible(true);
        importDBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    manager.readDatabase();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearTree(reactorTree);
                manager.reactorDBS.clear();
                manager.reactors.clear();
            }
        });
        regionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTable("Регион", Calculator.aggregateByRegion(manager.reactorDBS));
            }
        });
        countryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTable("Страна", Calculator.aggregateByCountry(manager.reactorDBS));
            }
        });
        operatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTable("Оператор", Calculator.aggregateByOperator(manager.reactorDBS));
            }
        });
    }

    public void clearTree(JTree tree) {
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Reactors");
        model.setRoot(root);
        root.removeAllChildren();
        model.reload();
    }

    private void createTable(String title, Map<String, Map<Integer, Double>> map) {
        if (map != null) {
            // Define an ordered list of years
            List<Integer> years = IntStream.range(2014, 2025).boxed().toList();

            DefaultTableModel model = new DefaultTableModel(
                    new Object[][]{},
                    new String[]{
                            title, "Объем ежегодной загрузки, т", "Год"
                    }
            ) {
                final Class[] types = new Class[]{
                        String.class, Integer.class, Integer.class
                };
                final boolean[] canEdit = new boolean[]{
                        false, false, false
                };

                public Class getColumnClass(int columnIndex) {
                    return types[columnIndex];
                }

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
                }
            };

            for (Map.Entry<String, Map<Integer, Double>> entry : map.entrySet()) {
                Map<Integer, Double> fuelLoad = entry.getValue();

                // Iterate through the ordered list of years
                for (Integer year : years) {
                    Double load = fuelLoad.get(year);
                    if (load != null) {
                        model.addRow(new Object[]{entry.getKey(), Math.round(load), year});
                    } else {
                        // Add a row with zero load for missing years
                        model.addRow(new Object[]{entry.getKey(), 0, year});
                    }
                }
            }

            jTable1.setModel(model);
            jTable1.getColumnModel().getColumn(2).setMaxWidth(50);
            aggregationWindow.setSize(480, 500);
            aggregationWindow.setVisible(rootPaneCheckingEnabled);
        }
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        rootPane = new JPanel();
        rootPane.setLayout(new GridLayoutManager(4, 5, new Insets(0, 0, 0, 0), -1, -1));
        uploadFileButton = new JButton();
        uploadFileButton.setText("Upload file");
        rootPane.add(uploadFileButton, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scroll = new JScrollPane();
        rootPane.add(scroll, new GridConstraints(3, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        reactorTree = new JTree();
        scroll.setViewportView(reactorTree);
        regionButton = new JButton();
        regionButton.setText("Region");
        rootPane.add(regionButton, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        importDBButton = new JButton();
        importDBButton.setText("Import DB");
        rootPane.add(importDBButton, new GridConstraints(1, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        operatorButton = new JButton();
        operatorButton.setText("Operator");
        rootPane.add(operatorButton, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        countryButton = new JButton();
        countryButton.setText("Country");
        rootPane.add(countryButton, new GridConstraints(2, 2, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        clearButton = new JButton();
        clearButton.setText("Clear");
        rootPane.add(clearButton, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPane;
    }

}
