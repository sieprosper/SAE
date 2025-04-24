package editeurgraphe.controller;

import editeurgraphe.model.Graph;
import editeurgraphe.model.exception.NodeCollisionException;
import editeurgraphe.view.GraphPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

public class GraphController {
    private Graph model;
    private GraphPanel view;

    public GraphController(Graph model, GraphPanel view) {
        this.model = model;
        this.view = view;

        view.repaintGraph(model);

        view.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) { // Vérifie si c'est un clic droit
                    int x = e.getX();
                    int y = e.getY();

                    try {
                        model.addNode(x, y); // Utilisation de Graph.addNode
                        view.repaint();
                    } catch (NodeCollisionException ex) {
                        JOptionPane.showMessageDialog(view, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
}
