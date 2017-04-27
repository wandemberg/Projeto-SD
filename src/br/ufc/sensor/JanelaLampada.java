package br.ufc.sensor;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.ufc.Lampada;
import br.ufc.cliente.ClienteLampada;

import javax.swing.JRadioButton;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JanelaLampada extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaLampada frame = new JanelaLampada();
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
	public JanelaLampada() {
		
		ClienteLampada cl = new ClienteLampada("127.0.0.1", 12345);
		cl.start();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JRadioButton rdbtnLigar = new JRadioButton("Ligar");
		rdbtnLigar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Lampada lamp = cl.getLampada();
				lamp.setLigar(true);
				cl.setLampada(lamp);
			}
		});
		contentPane.add(rdbtnLigar, BorderLayout.CENTER);

		JRadioButton rdbtnDesligar = new JRadioButton("Desligar");
		rdbtnDesligar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Lampada lamp = cl.getLampada();
				lamp.setLigar(false);
				cl.setLampada(lamp);
			}
		});
		rdbtnDesligar.setSelected(true);
		contentPane.add(rdbtnDesligar, BorderLayout.WEST);

		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnDesligar);
		bg.add(rdbtnLigar);

		JLabel lblLmpada = new JLabel("L\u00E2mpada");
		contentPane.add(lblLmpada, BorderLayout.NORTH);
	}

}
