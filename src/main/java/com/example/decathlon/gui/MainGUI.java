package com.example.decathlon.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;

import com.example.decathlon.deca.*;
import com.example.decathlon.heptathlon.*;

public class MainGUI {

    private JTextField nameField;
    private JTextField resultField;
    private JComboBox<String> modeBox;
    private JComboBox<String> disciplineBox;
    private JTextArea outputArea;
    private final Map<String,Integer> totals = new LinkedHashMap<>();

    public static void main(String[] args) {
        new MainGUI().createAndShowGUI();
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Track and Field Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,8,6,8);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;

        c.gridx = 0; c.gridy = 0;
        form.add(new JLabel("Competition:"), c);
        modeBox = new JComboBox<>(new String[] { "Decathlon", "Heptathlon" });
        modeBox.addActionListener(e -> rebuildDisciplineBox());
        c.gridx = 1; c.gridy = 0; c.weightx = 1.0;
        form.add(modeBox, c);

        c.weightx = 0;
        c.gridx = 0; c.gridy = 1;
        form.add(new JLabel("Enter Competitor's Name:"), c);
        nameField = new JTextField(20);
        c.gridx = 1; c.gridy = 1; c.weightx = 1.0;
        form.add(nameField, c);

        c.weightx = 0;
        c.gridx = 0; c.gridy = 2;
        form.add(new JLabel("Select Discipline:"), c);
        disciplineBox = new JComboBox<>();
        rebuildDisciplineBox();
        c.gridx = 1; c.gridy = 2; c.weightx = 1.0;
        form.add(disciplineBox, c);

        c.weightx = 0;
        c.gridx = 0; c.gridy = 3;
        form.add(new JLabel("Enter Result:"), c);
        resultField = new JTextField(10);
        c.gridx = 1; c.gridy = 3; c.weightx = 1.0;
        form.add(resultField, c);
        JButton calculateButton = new JButton("Calculate Score");
        calculateButton.addActionListener(new CalculateButtonListener());
        c.gridx = 2; c.gridy = 3; c.weightx = 0;
        form.add(calculateButton, c);

        outputArea = new JTextArea(10, 50);
        outputArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(outputArea);

        frame.add(form, BorderLayout.NORTH);
        frame.add(scroll, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void rebuildDisciplineBox() {
        String sel = (String) modeBox.getSelectedItem();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        if ("Decathlon".equals(sel)) {
            model.addElement("100m");
            model.addElement("Long Jump");
            model.addElement("Shot Put");
            model.addElement("High Jump");
            model.addElement("400m");
            model.addElement("110m Hurdles");
            model.addElement("Discus Throw");
            model.addElement("Pole Vault");
            model.addElement("Javelin Throw");
            model.addElement("1500m");
        } else {
            model.addElement("100m Hurdles");
            model.addElement("High Jump");
            model.addElement("Shot Put");
            model.addElement("200m");
            model.addElement("Long Jump");
            model.addElement("Javelin Throw");
            model.addElement("800m");
        }
        disciplineBox.setModel(model);
    }

    private class CalculateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText().trim();
            if (name.isEmpty() || !name.matches("[\\p{L} ]+")) {
                JOptionPane.showMessageDialog(null, "Name must contain letters only.", "Invalid Name", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String discipline = (String) disciplineBox.getSelectedItem();
            String resultText = resultField.getText().trim();

            try {
                double result = Double.parseDouble(resultText);
                int score = 0;

                if ("Decathlon".equals(modeBox.getSelectedItem())) {
                    switch (discipline) {
                        case "100m":            score = new Deca100M().calculateResult(result); break;
                        case "400m":            score = new Deca400M().calculateResult(result); break;
                        case "1500m":           score = new Deca1500M().calculateResult(result); break;
                        case "110m Hurdles":    score = new Deca110MHurdles().calculateResult(result); break;
                        case "Long Jump":       score = new DecaLongJump().calculateResult(result); break;
                        case "High Jump":       score = new DecaHighJump().calculateResult(result); break;
                        case "Pole Vault":      score = new DecaPoleVault().calculateResult(result); break;
                        case "Discus Throw":    score = new DecaDiscusThrow().calculateResult(result); break;
                        case "Javelin Throw":   score = new DecaJavelinThrow().calculateResult(result); break;
                        case "Shot Put":        score = new DecaShotPut().calculateResult(result); break;
                    }
                } else {
                    switch (discipline) {
                        case "100m Hurdles":    score = new Hep100MHurdles().calculateResult(result); break;
                        case "200m":            score = new Hep200M().calculateResult(result); break;
                        case "800m":            score = new Hep800M().calculateResult(result); break;
                        case "Long Jump":       score = new HeptLongJump().calculateResult(result); break;
                        case "High Jump":       score = new HeptHightJump().calculateResult(result); break;
                        case "Javelin Throw":   score = new HeptJavelinThrow().calculateResult(result); break;
                        case "Shot Put":        score = new HeptShotPut().calculateResult(result); break;
                    }
                }

                totals.merge(name, score, Integer::sum);

                outputArea.append("Competitor: " + name + "\n");
                outputArea.append("Discipline: " + discipline + "\n");
                outputArea.append("Result: " + result + "\n");
                outputArea.append("Score: " + score + "\n");
                outputArea.append("Total: " + totals.get(name) + "\n\n");

                resultField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number for the result.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            } catch (com.example.decathlon.deca.InvalidResultException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Invalid Result", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
