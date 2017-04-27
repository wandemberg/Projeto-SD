package br.ufc.sensor;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import br.ufc.Cortina;
import br.ufc.Lampada;
import br.ufc.cliente.ClienteCortina;

public class JanelaCortina extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaCortina frame = new JanelaCortina();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JanelaCortina() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		ClienteCortina cl = new ClienteCortina("127.0.0.1", 12345);
		cl.start();

		JRadioButton rdbtnLigar = new JRadioButton("Ligar");
		rdbtnLigar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Cortina cort = cl.getCortina();
				cort.setLevantar(true);
				cl.setCortina(cort);
			}
		});
		contentPane.add(rdbtnLigar, BorderLayout.CENTER);

		JRadioButton rdbtnDesligar = new JRadioButton("Desligar");
		rdbtnDesligar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Cortina cort = cl.getCortina();
				cort.setLevantar(false);
				cl.setCortina(cort);
			}
		});
		rdbtnDesligar.setSelected(true);
		contentPane.add(rdbtnDesligar, BorderLayout.WEST);

		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnDesligar);
		bg.add(rdbtnLigar);

		JLabel lblLmpada = new JLabel("Cortina");
		contentPane.add(lblLmpada, BorderLayout.NORTH);
	}

}
