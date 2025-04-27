package editeurgraphe.vue;

import editeurgraphe.model.GrapheManager;
import editeurgraphe.model.Tache;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
// ceci est un test 
public class InterfaceGraphique extends JFrame {

    private GrapheManager grapheManager;
    private JPanel panneauGraphe;
    private JButton boutonAjouterTache;
    private JButton boutonAjouterLien;
    private JButton boutonModifierTache;
    private JButton boutonSupprimerTache;
    private JButton boutonSupprimerLien;
    private JButton boutonAjouterDebutFin;
    private Tache tacheSelectionnee = null;

    public InterfaceGraphique() {
        super("Gestion de projet - Graphe des Tâches");
        this.grapheManager = new GrapheManager();
        initialiserInterface();
    }

    private void initialiserInterface() {
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panneauBoutons = new JPanel();
        boutonAjouterTache = new JButton("Ajouter Tâche");
        boutonAjouterLien = new JButton("Ajouter Lien");
        boutonModifierTache = new JButton("Modifier Tâche");
        boutonSupprimerTache = new JButton("Supprimer Tâche");
        boutonSupprimerLien = new JButton("Supprimer Lien");
        boutonAjouterDebutFin = new JButton("Ajouter Début et Fin");

        panneauBoutons.add(boutonAjouterTache);
        panneauBoutons.add(boutonAjouterLien);
        panneauBoutons.add(boutonModifierTache);
        panneauBoutons.add(boutonSupprimerTache);
        panneauBoutons.add(boutonSupprimerLien);
        panneauBoutons.add(boutonAjouterDebutFin);

        add(panneauBoutons, BorderLayout.NORTH);

        panneauGraphe = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                dessinerGraphe(g);
            }
        };
        panneauGraphe.setBackground(Color.WHITE);
        panneauGraphe.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for (Tache t : grapheManager.getToutesLesTaches()) {
                    int dx = e.getX() - t.getX();
                    int dy = e.getY() - t.getY();
                    if (Math.sqrt(dx * dx + dy * dy) <= 20) {
                        tacheSelectionnee = t;
                        break;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                tacheSelectionnee = null;
            }
        });

        panneauGraphe.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (tacheSelectionnee != null) {
                    tacheSelectionnee.setX(e.getX());
                    tacheSelectionnee.setY(e.getY());
                    panneauGraphe.repaint();
                }
            }
        });

        add(panneauGraphe, BorderLayout.CENTER);

        boutonAjouterTache.addActionListener(e -> ajouterTache());
        boutonAjouterLien.addActionListener(e -> ajouterLien());
        boutonModifierTache.addActionListener(e -> modifierTache());
        boutonSupprimerTache.addActionListener(e -> supprimerTache());
        boutonSupprimerLien.addActionListener(e -> supprimerLien());
        boutonAjouterDebutFin.addActionListener(e -> ajouterDebutEtFin());
    }

    private void dessinerGraphe(Graphics g) {
        int rayon = 30;
        for (Tache t : grapheManager.getToutesLesTaches()) {
            for (Tache succ : t.getSuccesseurs()) {
                int x1 = t.getX();
                int y1 = t.getY();
                int x2 = succ.getX();
                int y2 = succ.getY();
                g.setColor(Color.GRAY);
                g.drawLine(x1, y1, x2, y2);
                g.setColor(Color.BLUE);
                g.drawString(String.valueOf(t.getDuree()), (x1 + x2) / 2, (y1 + y2) / 2);
            }
        }
        for (Tache t : grapheManager.getToutesLesTaches()) {
            int x = t.getX();
            int y = t.getY();
            g.setColor(Color.ORANGE);
            g.fillOval(x - rayon / 2, y - rayon / 2, rayon, rayon);
            g.setColor(Color.BLACK);
            g.drawOval(x - rayon / 2, y - rayon / 2, rayon, rayon);
            g.drawString(t.getNom(), x - rayon / 2, y - rayon);
        }
    }

    private void ajouterTache() {
        String nom = JOptionPane.showInputDialog(this, "Nom de la tâche :");
        String dureeStr = JOptionPane.showInputDialog(this, "Durée :");
        int duree = Integer.parseInt(dureeStr);
        grapheManager.ajouterTache(nom, duree);
        panneauGraphe.repaint();
    }

    private void ajouterLien() {
        String origine = JOptionPane.showInputDialog(this, "Tâche d'origine :");
        String destination = JOptionPane.showInputDialog(this, "Tâche destination :");
        if (!grapheManager.existeTache(origine) || !grapheManager.existeTache(destination)) {
            JOptionPane.showMessageDialog(this, "Une des tâches n'existe pas.");
            return;
        }
        grapheManager.ajouterLien(origine, destination);
        panneauGraphe.repaint();
    }

    private void modifierTache() {
        String nom = JOptionPane.showInputDialog(this, "Nom de la tâche à modifier :");
        String nouveauNom = JOptionPane.showInputDialog(this, "Nouveau nom :");
        String nouvelleDureeStr = JOptionPane.showInputDialog(this, "Nouvelle durée :");
        int nouvelleDuree = Integer.parseInt(nouvelleDureeStr);
        grapheManager.modifierTache(nom, nouveauNom, nouvelleDuree);
        panneauGraphe.repaint();
    }

    private void supprimerTache() {
        String nom = JOptionPane.showInputDialog(this, "Nom de la tâche à supprimer :");
        Tache t = grapheManager.getTache(nom);
        if (t == null) return;

        if (!t.getSuccesseurs().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Impossible de supprimer cette tâche car elle est un prérequis pour d'autres.",
                "Suppression interdite",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        grapheManager.supprimerTache(nom);
        panneauGraphe.repaint();
    }

    private void supprimerLien() {
        String origine = JOptionPane.showInputDialog(this, "Tâche d'origine du lien à supprimer :");
        String destination = JOptionPane.showInputDialog(this, "Tâche destination du lien à supprimer :");

        Tache t1 = grapheManager.getTache(origine);
        Tache t2 = grapheManager.getTache(destination);

        if (t1 == null || t2 == null || !t1.getSuccesseurs().contains(t2)) {
            JOptionPane.showMessageDialog(this, "Lien introuvable entre les tâches spécifiées.");
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(this,
            "Supprimer ce lien supprimera la dépendance entre les tâches. Continuer ?",
            "Confirmation de suppression de lien",
            JOptionPane.YES_NO_OPTION);

        if (confirmation == JOptionPane.YES_OPTION) {
            grapheManager.supprimerLien(origine, destination);
            panneauGraphe.repaint();
        }
    }

    private void ajouterDebutEtFin() {
        grapheManager.ajouterTachesDebutEtFin();
        panneauGraphe.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InterfaceGraphique ig = new InterfaceGraphique();
            ig.setVisible(true);
        });
    }
}
