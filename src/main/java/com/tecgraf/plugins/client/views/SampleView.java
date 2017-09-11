package com.tecgraf.plugins.client.views;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class SampleView extends JFrame{

    public SampleView() throws HeadlessException {
        MigLayout layout = new MigLayout("fillx", "[right]rel[grow,fill]", "[]10[]");
        JPanel panel = new JPanel(layout);

        panel.add(new JLabel("Enter size:"), "");
        panel.add(new TextField(""), "wrap");
        panel.add(new JLabel("Enter weight"), "");
        panel.add(new TextField(""), "");

        add(panel);
        setSize(350, 350);

    }

    public static void main(String[] args) {
        new SampleView().setVisible(true);
    }
}
