package org.socialworld.tools.dct;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.function.Consumer;

import org.socialworld.core.EventType;
import org.socialworld.attributes.Attribute;
import org.socialworld.calculation.FunctionArgType;
import org.socialworld.calculation.descriptions.EventInfluenceDescription;
import org.socialworld.calculation.Expression_Function;
import org.socialworld.calculation.Expression_ConditionOperator;
import org.socialworld.datasource.parsing.*;
import org.socialworld.datasource.tablesPool.TablePoolEID;

/**
 * SocialWorld Logic Designer - Final Production Build
 * Visual Node Editor with integrated JSON/DB Export and Live-Synchronized UI.
 */
public class CreationTool_EventInfluenceDescription extends JFrame {

    // --- State Variables ---
    private String currentConditionAttr = "mood";
    private String currentOperator = ">=";
    private String currentThreshold = "50";
    private String currentTargetAttr = "health";
    private String currentBasisAttr = "power";
    private String currentM = "1.0";
    private String currentN = "0.0";
    private boolean isConstantMode = false;
    private String currentConstValue = "100";
    private String currentContext = "Select a Rule";
    private EventType currentEventType;
    private int currentInfluenceTypeNr = 0;

    // --- UI Components ---
    private JTree eventNavigator;
    private JPanel logicCanvas;
    private JPanel propertyInspector;
    private JTextField searchField;

    public CreationTool_EventInfluenceDescription() {
        setupMainFrame();
        initComponents();
        refreshUI();
        setVisible(true);
    }

    private void setupMainFrame() {
        setTitle("SocialWorld Logic Designer - JSON Factory");
        setSize(1400, 950);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(2, 2));
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
    }

    private void initComponents() {
        // --- WEST: NAVIGATION (250px) ---
        JPanel westPanel = new JPanel(new BorderLayout());
        westPanel.setPreferredSize(new Dimension(250, 0));
        searchField = new JTextField();
        addChangeListener(searchField, e -> filterTree(searchField.getText()));
        westPanel.add(searchField, BorderLayout.NORTH);

        eventNavigator = new JTree(buildEventTree());
        eventNavigator.setRootVisible(false);
        eventNavigator.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) eventNavigator.getLastSelectedPathComponent();
            if (node != null && node.isLeaf()) {
                String eventName = node.getParent().toString();
                currentEventType = EventType.valueOf(eventName);
                currentInfluenceTypeNr = Integer.parseInt(node.toString().replaceAll("\\D+", ""));
                currentContext = eventName + " / ID " + currentInfluenceTypeNr;
                refreshUI();
            }
        });
        westPanel.add(new JScrollPane(eventNavigator), BorderLayout.CENTER);
        add(westPanel, BorderLayout.WEST);

        // --- CENTER: CANVAS ---
        logicCanvas = new JPanel(new BorderLayout());
        logicCanvas.setBackground(new Color(60, 63, 65));
        add(new JScrollPane(logicCanvas), BorderLayout.CENTER);

        // --- EAST: INSPECTOR (300px) ---
        propertyInspector = new JPanel();
        propertyInspector.setPreferredSize(new Dimension(300, 0));
        add(propertyInspector, BorderLayout.EAST);
    }

    private void refreshUI() {
        setupInspectorFields();
        updateVisualCanvas();
    }

    private void updateVisualCanvas() {
        logicCanvas.removeAll();
        logicCanvas.setLayout(new BorderLayout());
        
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(45, 47, 49));
        header.setPreferredSize(new Dimension(0, 45));
        JLabel lbl = new JLabel("  " + currentContext);
        lbl.setForeground(Color.ORANGE);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        header.add(lbl, BorderLayout.WEST);
        logicCanvas.add(header, BorderLayout.NORTH);

        JPanel nodes = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        nodes.setBackground(new Color(60, 63, 65));
        
        addNode(nodes, "Term 1: Condition", String.format("IF: %s\nOP: %s\nVAL: %s", currentConditionAttr, currentOperator, currentThreshold), false);
        nodes.add(new JLabel(" \u2794 ") {{ setForeground(Color.WHITE); setFont(new Font("SansSerif", Font.BOLD, 25)); }});
        
        String actionInfo = isConstantMode ? 
            String.format("Type: Constant\nTarget: %s\nValue: %s", currentTargetAttr, currentConstValue) :
            String.format("Type: MX+N\nTarget: %s\nBasis: %s\n%s*%s + %s", currentTargetAttr, currentBasisAttr, currentM, currentBasisAttr, currentN);
        addNode(nodes, "Term 2: Action", actionInfo, isConstantMode);
        
        logicCanvas.add(nodes, BorderLayout.CENTER);

        JTextField lineField = new JTextField(generateLineString());
        lineField.setEditable(false);
        lineField.setBackground(Color.BLACK);
        lineField.setForeground(new Color(150, 255, 150));
        lineField.setFont(new Font("Monospaced", Font.BOLD, 12));
        logicCanvas.add(lineField, BorderLayout.SOUTH);

        logicCanvas.revalidate(); logicCanvas.repaint();
    }

    private String generateLineString() {
        String targetTag = currentTargetAttr.toUpperCase();
        String actionPart = isConstantMode ? 
            String.format("<Const>%s</Const>", currentConstValue) :
            String.format("<MX+N>%d;%s;%s</MX+N>", Attribute.valueOf(currentBasisAttr).getIndex(), currentM, currentN);
        return String.format("WENN %s %s %s DANN <%s>%s</%s>", currentConditionAttr, currentOperator, currentThreshold, targetTag, actionPart, targetTag);
    }

    private void btnSaveToDatabasePressed() {
        if (currentEventType == null) return;
        JsonEventInfluenceDescription root = new JsonEventInfluenceDescription();
        root.eventType = currentEventType.name();
        root.influenceType = currentInfluenceTypeNr;
        root.attributeChanges = new ArrayList<>();

        JsonEventInfluencesAttributeDescription attrDesc = new JsonEventInfluencesAttributeDescription();
        attrDesc.orderNr = 0;
        attrDesc.attribute = currentTargetAttr;
        attrDesc.term = new ArrayList<>();

        JsonTerm t1 = new JsonTerm();
        t1.termNr = 1;
        t1.function = Expression_Function.condition.toString();
        t1.functionArgs = new ArrayList<>();
        t1.functionArgs.add(createArg(1, FunctionArgType.Attribute, org.socialworld.calculation.Type.integer, String.valueOf(Attribute.valueOf(currentConditionAttr).getIndex())));
        t1.functionArgs.add(createArg(2, FunctionArgType.Const, org.socialworld.calculation.Type.string, currentOperator));
        t1.functionArgs.add(createArg(3, FunctionArgType.Const, org.socialworld.calculation.Type.integer, currentThreshold));
        attrDesc.term.add(t1);

        JsonTerm t2 = new JsonTerm();
        t2.termNr = 2;
        t2.functionArgs = new ArrayList<>();
        if (isConstantMode) {
            t2.function = Expression_Function.value.toString();
            t2.functionArgs.add(createArg(1, FunctionArgType.Const, org.socialworld.calculation.Type.integer, currentConstValue));
        } else {
            t2.function = "MX+N";
            t2.functionArgs.add(createArg(1, FunctionArgType.Attribute, org.socialworld.calculation.Type.integer, String.valueOf(Attribute.valueOf(currentBasisAttr).getIndex())));
            t2.functionArgs.add(createArg(2, FunctionArgType.Const, org.socialworld.calculation.Type.floatingpoint, currentM));
            t2.functionArgs.add(createArg(3, FunctionArgType.Const, org.socialworld.calculation.Type.floatingpoint, currentN));
        }
        attrDesc.term.add(t2);

        attrDesc.term.forEach(t -> t.functionArgs.forEach(a -> a.value.name = "arg_" + a.faNr));
        root.attributeChanges.add(attrDesc);

        //new TablePoolEID().insert(currentEventType.ordinal(), currentInfluenceTypeNr, root.toString());
 
        EventInfluenceDescription eid = new EventInfluenceDescription(root);

        // Test-Ausgabe in die Konsole
        System.out.println("Engine-Objekt erfolgreich erzeugt: " + eid.toString());

    }

    private JsonFunctionArg createArg(int nr, FunctionArgType argType, org.socialworld.calculation.Type swType, String val) {
        JsonFunctionArg arg = new JsonFunctionArg();
        arg.faNr = nr;
        arg.type = argType.toString();
        arg.value = new JsonValue();
        arg.value.type = swType.getIndexWithSWTPraefix(); 
        arg.value.value = val;
        return arg;
    }

    private void setupInspectorFields() {
        propertyInspector.removeAll();
        propertyInspector.setLayout(new BoxLayout(propertyInspector, BoxLayout.Y_AXIS));
        
        String[] attrs = Attribute.getNameList().toArray(String[]::new);
        String[] ops = new ArrayList<String>() {{
            for(Expression_ConditionOperator op : Expression_ConditionOperator.values()) add(op.toString());
        }}.toArray(String[]::new);

        // --- 1. CONDITION ---
        addHeader("1. Condition (WENN)");
        JComboBox<String> cAttr = new JComboBox<>(attrs);
        cAttr.setSelectedItem(currentConditionAttr);
        cAttr.addActionListener(e -> { currentConditionAttr = (String)cAttr.getSelectedItem(); updateVisualCanvas(); });
        propertyInspector.add(cAttr);

        JComboBox<String> opBox = new JComboBox<>(ops);
        opBox.setSelectedItem(currentOperator);
        opBox.addActionListener(e -> { currentOperator = (String)opBox.getSelectedItem(); updateVisualCanvas(); });
        propertyInspector.add(opBox);

        JTextField thresh = new JTextField(currentThreshold);
        addChangeListener(thresh, e -> { currentThreshold = thresh.getText(); updateVisualCanvas(); });
        propertyInspector.add(thresh);

        propertyInspector.add(Box.createVerticalStrut(20));

        // --- 2. ACTION ---
        addHeader("2. Action (DANN)");
        JComboBox<String> target = new JComboBox<>(attrs);
        target.setSelectedItem(currentTargetAttr);
        target.addActionListener(e -> { currentTargetAttr = (String)target.getSelectedItem(); updateVisualCanvas(); });
        propertyInspector.add(new JLabel("Target Attribute:")); propertyInspector.add(target);

        // STRATEGY (Jetzt oben platziert)
        JComboBox<String> strat = new JComboBox<>(new String[]{"Linear Function (MX+N)", "Fixed Constant"});
        strat.setSelectedIndex(isConstantMode ? 1 : 0);
        propertyInspector.add(new JLabel("Strategy:")); propertyInspector.add(strat);
        propertyInspector.add(Box.createVerticalStrut(10));

        CardLayout cl = new CardLayout();
        JPanel cardPanel = new JPanel(cl);
        
        // MXN Panel
        JPanel mxn = new JPanel();
        mxn.setLayout(new BoxLayout(mxn, BoxLayout.Y_AXIS));
        JComboBox<String> basis = new JComboBox<>(attrs);
        basis.setSelectedItem(currentBasisAttr);
        basis.addActionListener(e -> { currentBasisAttr = (String)basis.getSelectedItem(); updateVisualCanvas(); });
        JTextField mF = new JTextField(currentM); addChangeListener(mF, e -> { currentM = mF.getText(); updateVisualCanvas(); });
        JTextField nF = new JTextField(currentN); addChangeListener(nF, e -> { currentN = nF.getText(); updateVisualCanvas(); });
        mxn.add(new JLabel("Basis (X):")); mxn.add(basis);
        mxn.add(new JLabel("Slope m:")); mxn.add(mF);
        mxn.add(new JLabel("Offset n:")); mxn.add(nF);

        // CST Panel
        JPanel cst = new JPanel();
        cst.setLayout(new BoxLayout(cst, BoxLayout.Y_AXIS));
        JTextField cF = new JTextField(currentConstValue); addChangeListener(cF, e -> { currentConstValue = cF.getText(); updateVisualCanvas(); });
        cst.add(new JLabel("Value:")); cst.add(cF);

        cardPanel.add(mxn, "MXN"); cardPanel.add(cst, "CST");
        propertyInspector.add(cardPanel);

        strat.addActionListener(e -> { 
            isConstantMode = strat.getSelectedIndex() == 1; 
            cl.show(cardPanel, isConstantMode ? "CST" : "MXN"); 
            updateVisualCanvas(); 
        });
        cl.show(cardPanel, isConstantMode ? "CST" : "MXN");

        propertyInspector.add(Box.createVerticalGlue());
        JButton saveBtn = new JButton("SAVE TO DB");
        saveBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        saveBtn.setBackground(new Color(0, 80, 0)); saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
        saveBtn.addActionListener(e -> btnSaveToDatabasePressed());
        propertyInspector.add(saveBtn);
        propertyInspector.revalidate(); propertyInspector.repaint();
    }

    private void addHeader(String t) { JLabel l = new JLabel("<html><b>" + t + "</b></html>"); propertyInspector.add(l); }
    private void addNode(JPanel c, String t, String co, boolean gold) {
        JPanel n = new JPanel(new BorderLayout());
        n.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        n.setPreferredSize(new Dimension(180, 110));
        JLabel h = new JLabel(t, SwingConstants.CENTER); h.setOpaque(true);
        h.setBackground(gold ? new Color(218, 165, 32) : new Color(70, 140, 140));
        h.setForeground(Color.WHITE);
        JTextArea b = new JTextArea(co); b.setEditable(false); b.setBackground(new Color(245, 245, 245));
        b.setFont(new Font("Monospaced", Font.PLAIN, 10));
        n.add(h, BorderLayout.NORTH); n.add(b, BorderLayout.CENTER);
        c.add(n);
    }
    private void filterTree(String text) {
        if (text.length() < 2) return;
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) eventNavigator.getModel().getRoot();
        Enumeration<TreeNode> e = root.breadthFirstEnumeration();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
            if (node.toString().toLowerCase().contains(text.toLowerCase())) {
                TreePath path = new TreePath(node.getPath());
                eventNavigator.setSelectionPath(path);
                eventNavigator.scrollPathToVisible(path);
                break;
            }
        }
    }
    private void addChangeListener(JTextField tf, Consumer<DocumentEvent> c) {
        tf.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { c.accept(e); }
            public void removeUpdate(DocumentEvent e) { c.accept(e); }
            public void changedUpdate(DocumentEvent e) { c.accept(e); }
        });
    }
    private DefaultMutableTreeNode buildEventTree() {
        DefaultMutableTreeNode r = new DefaultMutableTreeNode("Rules");
        for (EventType et : EventType.values()) {
            if (et == EventType.nothing) continue;
            DefaultMutableTreeNode n = new DefaultMutableTreeNode(et.name());
            for (int i = 0; i < 5; i++) n.add(new DefaultMutableTreeNode("ID " + i));
            r.add(n);
        }
        return r;
    }
    
    private void loadRuleFromDB(int eventTypeNr, int influenceTypeNr) {
        TablePoolEID table = new TablePoolEID();
         String jsonFromDB = table.getJsonEID(eventTypeNr, influenceTypeNr); 

        if (jsonFromDB != null && !jsonFromDB.isEmpty()) {
              EventInfluenceDescription eid = new EventInfluenceDescription(jsonFromDB);
            
            // Jetzt müssen wir die GUI-Variablen mit den Werten aus dem EID-Objekt füllen
            // Hierzu brauchen wir eine Mapper-Logik (siehe unten)
         //   mapEngineObjectToGUI(eid);
        }
    }

    
    public static void main(String[] args) { SwingUtilities.invokeLater(CreationTool_EventInfluenceDescription::new); }
}
