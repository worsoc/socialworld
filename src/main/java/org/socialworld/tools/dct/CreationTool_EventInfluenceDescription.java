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

	private int treeRangeStart = 0;
	private int treeRangeEnd = 10; // Standardmäßig erstmal nur 10

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
        // --- WEST: NAVIGATION PANEL ---
        JPanel westPanel = new JPanel(new BorderLayout());
        westPanel.setPreferredSize(new Dimension(250, 0));
        westPanel.setBorder(BorderFactory.createTitledBorder("Explorer"));

        // Oberer Teil des West-Panels: Suche und Range
        JPanel westNorthPanel = new JPanel(new GridLayout(0, 1));
        
        // 1. Suchfeld
        searchField = new JTextField();
        searchField.setBorder(BorderFactory.createTitledBorder("Quick Search (Event)"));
        addChangeListener(searchField, e -> filterTree(searchField.getText()));
        westNorthPanel.add(searchField);

        // 2. Range-Eingabe (ID von - bis)
        JPanel rangePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rangePanel.setBorder(BorderFactory.createTitledBorder("ID Range"));
        JTextField startF = new JTextField(String.valueOf(treeRangeStart), 3);
        JTextField endF = new JTextField(String.valueOf(treeRangeEnd), 3);
        JButton setRangeBtn = new JButton("Set");
        
        rangePanel.add(new JLabel("ID:"));
        rangePanel.add(startF);
        rangePanel.add(new JLabel("-"));
        rangePanel.add(endF);
        rangePanel.add(setRangeBtn);
        westNorthPanel.add(rangePanel);

        westPanel.add(westNorthPanel, BorderLayout.NORTH);

        // 3. JTree Navigator
        eventNavigator = new JTree(buildEventTree());
        eventNavigator.setRootVisible(false);
        eventNavigator.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) eventNavigator.getLastSelectedPathComponent();
            if (node != null && node.isLeaf()) {
                try {
                    String eventName = node.getParent().toString();
                    currentEventType = EventType.valueOf(eventName);
                    currentInfluenceTypeNr = Integer.parseInt(node.toString().replaceAll("\\D+", ""));
                    currentContext = eventName + " / ID " + currentInfluenceTypeNr;
                    
                    // Lade Daten aus der Datenbank
                    loadRuleFromDB(currentEventType.getIndex(), currentInfluenceTypeNr);
                } catch (Exception ex) {
                    System.err.println("Error selecting node: " + ex.getMessage());
                }
            }
        });
        westPanel.add(new JScrollPane(eventNavigator), BorderLayout.CENTER);
        add(westPanel, BorderLayout.WEST);

        // --- CENTER: LOGIC CANVAS ---
        logicCanvas = new JPanel(new BorderLayout());
        logicCanvas.setBackground(new Color(60, 63, 65));
        add(new JScrollPane(logicCanvas), BorderLayout.CENTER);

        // --- EAST: PROPERTY INSPECTOR ---
        propertyInspector = new JPanel();
        propertyInspector.setPreferredSize(new Dimension(300, 0));
        add(propertyInspector, BorderLayout.EAST);

        // --- ACTION: Range Button Listener ---
        setRangeBtn.addActionListener(e -> {
            try {
                int start = Integer.parseInt(startF.getText());
                int end = Integer.parseInt(endF.getText());
                
                // Prüfung: Bis-Wert muss größer als Von-Wert sein
                if (end <= start) {
                    
                    // Automatische Korrektur (Ende auf Start + 1 setzen)
                    end = start + 1;
                    endF.setText(String.valueOf(end));
                    System.out.println("Range korrigiert: End-Wert wurde auf " + end + " gesetzt.");
                }
  
                // Prüfung: Bis-Wert muss kleiner als 100 sein
                if (end > 99) {
                     
                    // Automatische Korrektur (Ende auf 99, start auf 98 setzen)
                    end = 99;
                    start = 98;
                    startF.setText(String.valueOf(start));
                   endF.setText(String.valueOf(end));
                   System.out.println("Range korrigiert: End-Wert wurde auf 99 gesetzt.");
                }
              
                // Werte in die globalen Variablen übernehmen
                treeRangeStart = start;
                treeRangeEnd = end;
               
                // Baum mit neuen ID-Grenzen neu aufbauen
                eventNavigator.setModel(new DefaultTreeModel(buildEventTree()));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Bitte nur Zahlen für die Range eingeben.");
            }
        });
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
            String.format("<MX+N>%s;%s;%s</MX+N>", currentBasisAttr, currentM, currentN); 
        
        return String.format("WENN %s %s %s DANN <%s>%s</%s>", 
                currentConditionAttr, currentOperator, currentThreshold, targetTag, actionPart, targetTag);
    }    
 
    private void btnSaveToDatabasePressed() {
        if (currentEventType == null) return;
        
        JsonEventInfluenceDescription root = new JsonEventInfluenceDescription();
        root.eventType = currentEventType.name();
        root.influenceType = currentInfluenceTypeNr;
        root.attributeChanges = new ArrayList<>();

        JsonEventInfluencesAttributeDescription attrDesc = new JsonEventInfluencesAttributeDescription();
        attrDesc.orderNr = 0;
        attrDesc.attribute = currentTargetAttr; // Name des Ziel-Attributs
        attrDesc.term = new ArrayList<>();

        // --- TERM 1: Condition ---
        JsonTerm t1 = new JsonTerm();
        t1.termNr = 1;
        t1.function = "condition";
        t1.functionArgs = new ArrayList<>();
        // Attribut als NAME speichern (Typ "string")
        t1.functionArgs.add(createArg(1, FunctionArgType.Attribute, org.socialworld.calculation.Type.string, currentConditionAttr));
        t1.functionArgs.add(createArg(2, FunctionArgType.Const, org.socialworld.calculation.Type.string, currentOperator));
        t1.functionArgs.add(createArg(3, FunctionArgType.Const, org.socialworld.calculation.Type.integer, currentThreshold));
        attrDesc.term.add(t1);

        // --- TERM 2: Action ---
        JsonTerm t2 = new JsonTerm();
        t2.termNr = 2;
        t2.functionArgs = new ArrayList<>();
        if (isConstantMode) {
            t2.function = Expression_Function.value.name();
            t2.functionArgs.add(createArg(1, FunctionArgType.Const, org.socialworld.calculation.Type.integer, currentConstValue));
        } else {
            // 1. Wir nutzen den Enum-Standard 'function'
        	t2.function = Expression_Function.function.name(); // "function"
        	t2.functionArgs.add(createArg(1, FunctionArgType.Const, org.socialworld.calculation.Type.string, "MX+N"));

        	// JETZT: Den Namen statt des Index verwenden
        	String formulaData = String.format("%s;%s;%s", currentBasisAttr, currentM, currentN);
        	t2.functionArgs.add(createArg(2, FunctionArgType.Const, org.socialworld.calculation.Type.string, formulaData));
        }
        attrDesc.term.add(t2);

        root.attributeChanges.add(attrDesc);

        EventInfluenceDescription eid = new EventInfluenceDescription(root);

        // Test-Ausgabe in die Konsole
        System.out.println("JEID: " + root.toString());
       System.out.println("Engine-Objekt erfolgreich erzeugt: " + eid.toString());

        // DB Insert/Update
        new TablePoolEID().insert(currentEventType.ordinal(), currentInfluenceTypeNr, root.toString());
    }

    
    

    private JsonFunctionArg createArg(int nr, FunctionArgType argType, org.socialworld.calculation.Type swType, String val) {
        JsonFunctionArg arg = new JsonFunctionArg();
        arg.faNr = nr;
        arg.type = argType.toString();
        arg.value = new JsonValue();
        
        // Mapping der Typen auf die Bezeichner in deiner Datenbank
        String dbTypeLabel = "string"; // Standard für Attribute, Operatoren, etc.
        
        if (swType == org.socialworld.calculation.Type.integer) {
            dbTypeLabel = "integer";
        } else if (swType == org.socialworld.calculation.Type.floatingpoint) {
            dbTypeLabel = "floatingpoint";
        }
        
        arg.value.type = dbTypeLabel; 
        arg.value.value = val;
        arg.value.name = ""; // Name ist in deinem DB-Beispiel ein leerer String
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
        
        
        //  Ein kleiner Abstandshalter vor dem Mass-Generate Button
        propertyInspector.add(Box.createVerticalStrut(20));
        propertyInspector.add(new JSeparator()); // Eine Trennlinie
        propertyInspector.add(Box.createVerticalStrut(10));

        //  Den Button erstellen (wie im letzten Schritt besprochen)
        JButton batchBtn = new JButton("MASS GENERATE 19.300 RULES");
        batchBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); // Volle Breite
        batchBtn.setBackground(new Color(139, 0, 0)); // Dunkelrot
        batchBtn.setForeground(Color.WHITE);
        batchBtn.setFont(new Font("SansSerif", Font.BOLD, 11));

        batchBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Sollen wirklich 19.300 initiale Regeln in der DB erzeugt werden?", 
                "Datenbank-Initialisierung", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                // Starte in einem Thread, damit die GUI nicht einfriert
                new Thread(() -> runMassGeneration()).start();
            }
        });

        // 3. WICHTIG: Den Button zum Panel hinzufügen!
        propertyInspector.add(batchBtn);

        // 4. GUI-Refresh auslösen
        propertyInspector.revalidate();
        propertyInspector.repaint();
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
            
            // Nutzt die dynamische Range
            for (int i = treeRangeStart; i < treeRangeEnd; i++) { 
                if (i >= 0 && i < 100) { // Sicherheitshalber auf 0-99 begrenzen
                    n.add(new DefaultMutableTreeNode("ID " + i));
                }
            }
            r.add(n);
        }
        return r;
    }
 /*  
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
 */   
    private void loadRuleFromDB(int eventTypeNr, int influenceTypeNr) {
        TablePoolEID table = new TablePoolEID();
         String jsonFromDB = table.getJsonEID(eventTypeNr, influenceTypeNr); 

         if (jsonFromDB != null && !jsonFromDB.isEmpty()) {
             try {
                 EventInfluenceDescription eid = new EventInfluenceDescription(jsonFromDB);
                 JsonEventInfluenceDescription jeid = Json.getGsonInstance().fromJson(jsonFromDB, JsonEventInfluenceDescription.class);
                 mapEngineObjectToGUI(eid, jeid);
             } catch (Exception e) {
                 System.err.println("Fehler beim Parsen der DB-Daten: " + e.getMessage());
                 resetToDefaultVariables();
                 refreshUI();
             }
         } else {
             // FALLBACK: Wenn die Datenbank leer ist, zeige Standardwerte an
             System.out.println("Kein DB-Eintrag gefunden. Nutze Standardwerte.");
             resetToDefaultVariables();
             refreshUI();
         }
    }

    
    
    private void mapEngineObjectToGUI(EventInfluenceDescription eid, JsonEventInfluenceDescription jeid) {
        if (eid == null || jeid == null) return;

        try {
            // 1. Kontext setzen
            this.currentEventType = org.socialworld.core.EventType.getEventType(eid.getNrEventType());
            this.currentInfluenceTypeNr = eid.getNrInfluenceType();

            if (jeid.attributeChanges != null && !jeid.attributeChanges.isEmpty()) {
                JsonEventInfluencesAttributeDescription attr = jeid.attributeChanges.get(0);
                this.currentTargetAttr = attr.attribute;

                if (attr.term != null && attr.term.size() >= 2) {
                    
                    // --- TERM 1: Condition (WENN) ---
                    JsonTerm t1 = attr.term.get(0);
                    if (t1.functionArgs != null && t1.functionArgs.size() >= 3) {
                        String rawVal = t1.functionArgs.get(0).value.value;
                        if (rawVal.matches("\\d+")) {
                            int idx = Integer.parseInt(rawVal);
                            this.currentConditionAttr = org.socialworld.attributes.Attribute.getAttributeName(idx).toString();
                        } else {
                            this.currentConditionAttr = rawVal;
                        }
                        this.currentOperator = t1.functionArgs.get(1).value.value;
                        this.currentThreshold = t1.functionArgs.get(2).value.value;
                    }

                    // --- TERM 2: Action (DANN) ---
                    JsonTerm t2 = attr.term.get(1);
                    
                    // Check auf Constant ("value")
                    if (t2.function.equalsIgnoreCase("value") || t2.function.equalsIgnoreCase("replacement")) {
                        this.isConstantMode = true;
                        if (!t2.functionArgs.isEmpty()) {
                            this.currentConstValue = t2.functionArgs.get(0).value.value;
                        }
                    } 
                    // Check auf die neue "function" Struktur (Arg 1: Kennung, Arg 2: Daten)
                    else if (t2.function.equalsIgnoreCase("function") && t2.functionArgs.size() >= 2) {
                        String identifier = t2.functionArgs.get(0).value.value; // "MX+N"
                        String formulaData = t2.functionArgs.get(1).value.value; // "Attribute;M;N"
                        
                        if ("MX+N".equalsIgnoreCase(identifier) && formulaData.contains(";")) {
                            this.isConstantMode = false;
                            String[] parts = formulaData.split(";");
                            if (parts.length >= 3) {
                                this.currentBasisAttr = parts[0];
                                this.currentM = parts[1];
                                this.currentN = parts[2];
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Fehler beim Mapping (Identifier + Formula): " + e.getMessage());
        }

        refreshUI(); 
    }
   
 
    private void resetToDefaultVariables() {
        this.currentConditionAttr = "mood";
        this.currentOperator = ">=";
        this.currentThreshold = "50";
        this.currentTargetAttr = "health";
        this.currentBasisAttr = "power";
        this.currentM = "1.0";
        this.currentN = "0.0";
        this.isConstantMode = false;
        this.currentConstValue = "100";
        // currentContext, currentEventType und currentInfluenceTypeNr 
        // werden im Tree-Listener gesetzt, daher hier kein Reset nötig.
    }
    
    
 
    // 2. Die Generierungs-Logik
    private void runMassGeneration() {
        TablePoolEID table = new TablePoolEID();
        int totalGenerated = 0;
        
        // Wir nehmen "mood" als Standard-Bedingung für alle generierten Regeln
        String condAttr = "mood";
        String op = ">=";
        String threshold = "50";

        table.clear();
        
        for (EventType et : EventType.values()) {
            if (et == EventType.nothing) continue;
 
            for (int inflType = 0; inflType < 100; inflType++) {

                // Ziel-Attribut (Y)
                int targetIdx = (et.getIndex() + inflType) % Attribute.NUMBER_OF_ATTRIBUTES;
                String targetAttr = Attribute.getAttributeName(targetIdx).toString();
                
                // Basis-Attribut (X)
                int basisIdx = (targetIdx + 5) % Attribute.NUMBER_OF_ATTRIBUTES;
                String basisAttr = Attribute.getAttributeName(basisIdx).toString();
                
                // Mathematische Werte
                float m = 0.5f + (float)((et.getIndex() * inflType) % 150) / 100.0f;
                float n = (float)((et.getIndex() + inflType) % 200) / 10.0f;

                // --- JSON Struktur aufbauen ---
                JsonEventInfluenceDescription root = new JsonEventInfluenceDescription();
                root.eventType = et.name();
                root.influenceType = inflType;
                root.attributeChanges = new ArrayList<>();

                JsonEventInfluencesAttributeDescription attrDesc = new JsonEventInfluencesAttributeDescription();
                attrDesc.attribute = targetAttr;
                attrDesc.orderNr = 0;
                attrDesc.term = new ArrayList<>();

                // Term 1: Condition
                JsonTerm t1 = new JsonTerm();
                t1.termNr = 1; t1.function = "condition"; t1.functionArgs = new ArrayList<>();
                t1.functionArgs.add(createArg(1, FunctionArgType.Attribute, org.socialworld.calculation.Type.string, condAttr));
                t1.functionArgs.add(createArg(2, FunctionArgType.Const, org.socialworld.calculation.Type.string, op));
                t1.functionArgs.add(createArg(3, FunctionArgType.Const, org.socialworld.calculation.Type.integer, threshold));
                attrDesc.term.add(t1);

                // Term 2: Action (MX+N)
                JsonTerm t2 = new JsonTerm();
                t2.termNr = 2; t2.function = "function"; t2.functionArgs = new ArrayList<>();
                t2.functionArgs.add(createArg(1, FunctionArgType.Const, org.socialworld.calculation.Type.string, "MX+N"));
                String formulaData = String.format("%s;%.2f;%.2f", basisAttr, m, n);
                t2.functionArgs.add(createArg(2, FunctionArgType.Const, org.socialworld.calculation.Type.string, formulaData));

                attrDesc.term.add(t2);
                root.attributeChanges.add(attrDesc);

                // In DB schreiben
                table.insert(et.getIndex(), inflType, root.toString());
                totalGenerated++;
            }
            System.out.println("Generated rules for " + et.name());
        }
        JOptionPane.showMessageDialog(this, "Finished! " + totalGenerated + " rules written to MariaDB.");
    }
   
    
    
    
    
    
    
    
    
    
    
    public static void main(String[] args) { SwingUtilities.invokeLater(CreationTool_EventInfluenceDescription::new); }
}



/*
 In deiner Klasse, die den String "courage;1.0;2.3" verarbeitet, musst du den ersten Teil nun über deine Enum auflösen:Alt: int idx = Integer.parseInt(parts[0]);Neu: Attribute attr = Attribute.valueOf(parts[0]); (oder deine entsprechende Methode Attribute.fromName()).
 * 
 String[] formulaParts = jsonArg2.split(";");
Attribute basisAttr = Attribute.valueOf(formulaParts[0]);
float m = Float.parseFloat(formulaParts[1]);
float n = Float.parseFloat(formulaParts[2]);
// Erzeuge MX+N Expression...
 
 */
