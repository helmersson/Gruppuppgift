package com.example.decathlon.gui;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



import java.awt.*;

import com.example.decathlon.deca.*;


public class MainGUI {

    private JTextField nameField;
    private JTextField resultField;
    private JComboBox<String> disciplineBox;
    private JTextArea outputArea;

    public static void main(String[] args) {
        new MainGUI().createAndShowGUI();
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Track and Field Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        JPanel panel = new JPanel(new GridLayout(6, 1));

        // Input for competitor's name
        nameField = new JTextField(20);
        panel.add(new JLabel("Enter Competitor's Name:"));
        panel.add(nameField);

        // Dropdown for selecting discipline
        String[] disciplines = {
                "100m", "400m", "1500m", "110m Hurdles",
                "Long Jump", "High Jump", "Pole Vault",
                "Discus Throw", "Javelin Throw", "Shot Put"
        };
        disciplineBox = new JComboBox<>(disciplines);
        panel.add(new JLabel("Select Discipline:"));
        panel.add(disciplineBox);

        // Input for result
        resultField = new JTextField(10);
        panel.add(new JLabel("Enter Result:"));
        panel.add(resultField);

        // Button to calculate and display result
        JButton calculateButton = new JButton("Calculate Score");
        calculateButton.addActionListener(new CalculateButtonListener());
        panel.add(calculateButton);

        // Output area
        outputArea = new JTextArea(5, 40);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        panel.add(scrollPane);

        frame.add(panel);
        frame.setVisible(true);
    }

    private class CalculateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String discipline = (String) disciplineBox.getSelectedItem();
            String resultText = resultField.getText();

            try {
                double result = Double.parseDouble(resultText);

                int score = 0;
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

                outputArea.append("Competitor: " + name + "\n");
                outputArea.append("Discipline: " + discipline + "\n");
                outputArea.append("Result: " + result + "\n");
                outputArea.append("Score: " + score + "\n\n");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number for the result.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            } catch (InvalidResultException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Invalid Result", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }
}
