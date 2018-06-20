package com.kemk.ui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;

import java.sql.Connection;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.annotation.Repeatable;

import javax.swing.JPanel;

import com.github.sarxos.webcam.Webcam;
import com.kemk.importing.Camera;
import com.kemk.sql.PlakaVeritabani;
import com.kemk.sql.RegisteredUserInfo;
import com.openalpr.jni.Alpr;

import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.JSeparator;
import javax.swing.JDesktopPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.JToolBar;
import javax.swing.JTabbedPane;
import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import net.miginfocom.swing.MigLayout;
import javax.swing.SpringLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollBar;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;


public class MainGUI {

	private JFrame frame;
	public static Connection con ;

	/**
	 * Launch the application.
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					MainGUI window = new MainGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	Camera cam ;
	private JRadioButton rdbtnLiveTracking;
	private JRadioButton rdbtnMotionTracking;
	
	PlakaVeritabani pv ;
	/**
	 * Create the application.
	 */
	public MainGUI() {
		
		try {
			pv = new PlakaVeritabani() {
				@Override
				public void CantConnectToDBCallback(SQLException e) {
					super.CantConnectToDBCallback(e);
					CantConnet(e);
				}};
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		initialize();
		
	}
	public void CantConnet(SQLException e){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ErrorMessageDialog window = new ErrorMessageDialog();
					window.setBounds(0, 0, 440, 330);
					window.SetMessage(e.getMessage());
					window.setVisible(true);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 683, 566);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton OpenCamera = new JButton("OpenCamera");
		frame.getContentPane().add(OpenCamera);

		
		OpenCamera.setBounds(37, 75, 111, 23);
		
		JLabel lblFPS = new JLabel("FPS :");
		lblFPS.setBounds(235, 79, 99, 14);
		frame.getContentPane().add(lblFPS);
		
		JLabel lblPlaka = new JLabel("Plaka :");
		lblPlaka.setBounds(442, 75, 46, 14);
		frame.getContentPane().add(lblPlaka);
		
		
		JLabel txtPlaka = new JLabel("");
		txtPlaka.setBounds(480, 70, 128, 23);
		frame.getContentPane().add(txtPlaka);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(32, 365, 122, 48);
		frame.getContentPane().add(panel_1);
		
		rdbtnLiveTracking = new JRadioButton("Live Tracking");
		rdbtnLiveTracking.setToolTipText("");
		
		
		rdbtnMotionTracking = new JRadioButton("Motion Tracking");
		
			
			rdbtnMotionTracking.setSelected(true);
			rdbtnMotionTracking.setToolTipText("");
		
			ImagePanel panel = new ImagePanel();
			panel.setBounds(471, 100, 172, 40);
			frame.getContentPane().add(panel);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(rdbtnLiveTracking)
						.addComponent(rdbtnMotionTracking))
					.addContainerGap(21, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(rdbtnLiveTracking)
					.addPreferredGap(ComponentPlacement.RELATED, 2, Short.MAX_VALUE)
					.addComponent(rdbtnMotionTracking))
		);
		panel_1.setLayout(gl_panel_1);
		
		DefaultListModel<String> model = new DefaultListModel<String>();
		JList<String> list = new JList<String>(model);
		list.setBorder(UIManager.getBorder("ScrollPane.border"));
		list.setValueIsAdjusting(true);
		list.setVisibleRowCount(10);
		
	
		CameraPanel CameraView = new CameraPanel(lblFPS,txtPlaka,panel) {
			@Override
			public void DetectingCallback(String platename) {
				super.DetectingCallback(platename);
				//Plaka Sorgulama Bölümü ------------------------------------------
				try {
					
					RegisteredUserInfo u = pv.PlakaSorgula(platename);
					if(u != null)
					{
						model.addElement(u.plateString + " plakalý " + u.name + " Giriþ Yaptý ...");
					}
					/*
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDateTime now = LocalDateTime.now();
					pv.PlakaEkle("34asdasd34",dtf.format(now).toString());
					*/
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		};
		CameraView.setBounds(26, 112, 308, 242);
		frame.getContentPane().add(CameraView);
		CameraView.setOpaque(false);
		
		JSpinner spinner = new JSpinner();
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if((int)spinner.getValue() < 0)
					spinner.setValue(0);
				CameraView.SetMotionValue((int) spinner.getValue());
				CameraView.repaint();
			}
		});
		
				
				frame.getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{OpenCamera, lblFPS, lblPlaka, txtPlaka, panel, panel_1, rdbtnMotionTracking, CameraView, rdbtnLiveTracking}));
		spinner.setBounds(112, 420, 36, 23);
		frame.getContentPane().add(spinner);
		
		JLabel lblMotionValue = new JLabel("Motion Value :");
		lblMotionValue.setBounds(32, 424, 99, 14);
		frame.getContentPane().add(lblMotionValue);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if((int)spinner_1.getValue() < 0)
					spinner_1.setValue(0);
				CameraView.SetMotionOffsetValue((int) spinner_1.getValue());
				CameraView.repaint();
			}
		});
		
		JLabel label = new JLabel("Motion Offset :");
		label.setBounds(32, 449, 92, 14);
		frame.getContentPane().add(label);
		spinner_1.setBounds(112, 448, 36, 20);
		frame.getContentPane().add(spinner_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(419, 178, 218, 176);
		frame.getContentPane().add(scrollPane);
		scrollPane.setViewportView(list);
	
		
		JLabel lblLogs = new JLabel("Logs");
		lblLogs.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogs.setBounds(419, 163, 208, 14);
		frame.getContentPane().add(lblLogs);
		
		
		//CameraView.GetPlateToImagePanel(panel);
		
		OpenCamera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CameraView.StartCamera();
			}
			
		});
	}
}
