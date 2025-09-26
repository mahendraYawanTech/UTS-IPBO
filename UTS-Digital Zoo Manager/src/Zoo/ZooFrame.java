package Zoo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ZooFrame extends JFrame {
    private JTextField tfName;
    private JTextField tfAge;
    private JComboBox<String> cbType;
    private JTextField tfFurColor;
    private JCheckBox chkCanFly;
    private JButton btnAdd;
    private JTextArea taLog;

    // storage
    private final ArrayList<Animal> animals = new ArrayList<>();

    public ZooFrame() {
        super("Digital Zoo Manager");
        initComponents();
        layoutComponents();
        attachListeners();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        tfName = new JTextField(20);
        tfAge = new JTextField(5);

        cbType = new JComboBox<>(new String[] { "Generic Mammal", "Generic Bird" });

        tfFurColor = new JTextField(15);
        chkCanFly = new JCheckBox("Can Fly?");

        btnAdd = new JButton("Add Animal");

        taLog = new JTextArea();
        taLog.setEditable(false);
        taLog.setLineWrap(true);
        taLog.setWrapStyleWord(true);
    }

    private void layoutComponents() {
        JPanel panelForm = new JPanel();
        panelForm.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6,6,6,6);
        gc.anchor = GridBagConstraints.WEST;

        // Row 1: Name
        gc.gridx = 0; gc.gridy = 0;
        panelForm.add(new JLabel("Name:"), gc);
        gc.gridx = 1; gc.gridy = 0;
        panelForm.add(tfName, gc);

        // Row 2: Age
        gc.gridx = 0; gc.gridy = 1;
        panelForm.add(new JLabel("Age:"), gc);
        gc.gridx = 1; gc.gridy = 1;
        panelForm.add(tfAge, gc);

        // Row 3: Type
        gc.gridx = 0; gc.gridy = 2;
        panelForm.add(new JLabel("Type:"), gc);
        gc.gridx = 1; gc.gridy = 2;
        panelForm.add(cbType, gc);

        // Row 4: Specific attribute area (we'll add both; toggle visibility)
        gc.gridx = 0; gc.gridy = 3;
        panelForm.add(new JLabel("Fur Color / Can Fly:"), gc);
        JPanel specialPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        specialPanel.add(tfFurColor);
        specialPanel.add(Box.createHorizontalStrut(8));
        specialPanel.add(chkCanFly);
        gc.gridx = 1; gc.gridy = 3;
        panelForm.add(specialPanel, gc);

        // Row 5: Button
        gc.gridx = 1; gc.gridy = 4;
        gc.anchor = GridBagConstraints.EAST;
        panelForm.add(btnAdd, gc);

        JScrollPane scroll = new JScrollPane(taLog, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Main layout
        setLayout(new BorderLayout(10,10));
        add(panelForm, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(new JLabel("Log:"), BorderLayout.WEST);

        // initial visibility
        updateSpecialFieldsVisibility();
    }

    private void attachListeners() {
        cbType.addActionListener(e -> updateSpecialFieldsVisibility());

        btnAdd.addActionListener(e -> onAddAnimal());

        // Allow pressing Enter in tfAge to trigger add
        tfAge.addActionListener(e -> onAddAnimal());
    }

    private void updateSpecialFieldsVisibility() {
        String sel = (String) cbType.getSelectedItem();
        boolean isMammal = sel != null && sel.contains("Mammal");
        tfFurColor.setVisible(isMammal);
        chkCanFly.setVisible(!isMammal);
        // For layout update
        tfFurColor.revalidate();
        tfFurColor.repaint();
        chkCanFly.revalidate();
        chkCanFly.repaint();
    }

    private void onAddAnimal() {
        String name = tfName.getText().trim();
        String ageText = tfAge.getText().trim();
        String selType = (String) cbType.getSelectedItem();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a name.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageText);
            if (age < 0) throw new NumberFormatException("Negative age");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid non-negative integer for age.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (selType == null) {
            JOptionPane.showMessageDialog(this, "Please select an animal type.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Animal newAnimal;
        if (selType.contains("Mammal")) {
            String fur = tfFurColor.getText().trim();
            newAnimal = new Mammal(name, age, fur);
            animals.add(newAnimal);
            appendToLog(String.format("Added a new Mammal! Info: %s. Sound: %s",
                    newAnimal.getInfo(), newAnimal.makeSound()));
        } else { // Bird
            boolean canFly = chkCanFly.isSelected();
            newAnimal = new Bird(name, age, canFly);
            animals.add(newAnimal);
            appendToLog(String.format("Added a new Bird! Info: %s. Sound: %s",
                    newAnimal.getInfo(), newAnimal.makeSound()));
        }

        // clear basic inputs (not special fields, to let user enter many)
        tfName.setText("");
        tfAge.setText("");
        tfName.requestFocus();
    }

    private void appendToLog(String text) {
        taLog.append(text + "\n\n");
        // auto-scroll
        taLog.setCaretPosition(taLog.getDocument().getLength());
    }

    // Optional: method to list all animals in the log
    public void dumpAllAnimalsToLog() {
        appendToLog("---- All animals in memory (" + animals.size() + ") ----");
        for (Animal a : animals) {
            appendToLog(a.getClass().getSimpleName() + ": " + a.getInfo());
        }
    }
}
