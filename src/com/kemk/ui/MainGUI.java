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
import java.awt.Dimension;

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
import java.awt.Toolkit;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollBar;
import javax.swing.JList;
import javax.script.ScriptException;
import javax.swing.AbstractListModel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Window.Type;
import javax.swing.ButtonGroup;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;


public class MainGUI {

	private JFrame frmKaramrselEitimMerkezi;
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
					
					window.frmKaramrselEitimMerkezi.setVisible(true);
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
	private final ButtonGroup trackingModeGroup = new ButtonGroup();
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
		frmKaramrselEitimMerkezi = new JFrame();
		frmKaramrselEitimMerkezi.setTitle("Karam\u00FCrsel E\u011Fitim Merkezi Komutanl\u0131\u011F\u0131 Ara\u00E7 Plaka Tan\u0131ma Sistemi");
		frmKaramrselEitimMerkezi.setResizable(false);
		frmKaramrselEitimMerkezi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmKaramrselEitimMerkezi.setUndecorated(true);

		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frmKaramrselEitimMerkezi.setBounds(0, 0, (int)dim.getWidth(),(int) dim.getHeight());
		SpringLayout springLayout = new SpringLayout();
		frmKaramrselEitimMerkezi.getContentPane().setLayout(springLayout);
		
		JLabel lblPlaka = new JLabel("Plaka :");
		frmKaramrselEitimMerkezi.getContentPane().add(lblPlaka);
		
		
		JLabel txtPlaka = new JLabel("");
		springLayout.putConstraint(SpringLayout.NORTH, txtPlaka, 70, SpringLayout.NORTH, frmKaramrselEitimMerkezi.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, txtPlaka, 419, SpringLayout.WEST, frmKaramrselEitimMerkezi.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, txtPlaka, 98, SpringLayout.NORTH, frmKaramrselEitimMerkezi.getContentPane());
		frmKaramrselEitimMerkezi.getContentPane().add(txtPlaka);
		
		ImagePanel panel = new ImagePanel();
		springLayout.putConstraint(SpringLayout.SOUTH, lblPlaka, -6, SpringLayout.NORTH, panel);
		springLayout.putConstraint(SpringLayout.NORTH, panel, 120, SpringLayout.NORTH, frmKaramrselEitimMerkezi.getContentPane());
		frmKaramrselEitimMerkezi.getContentPane().add(panel);
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		springLayout.putConstraint(SpringLayout.NORTH, tabbedPane, 363, SpringLayout.NORTH, frmKaramrselEitimMerkezi.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, tabbedPane, 12, SpringLayout.WEST, frmKaramrselEitimMerkezi.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, tabbedPane, -1089, SpringLayout.EAST, frmKaramrselEitimMerkezi.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, tabbedPane, -169, SpringLayout.SOUTH, frmKaramrselEitimMerkezi.getContentPane());
		frmKaramrselEitimMerkezi.getContentPane().add(tabbedPane);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Camera", null, panel_2, null);
		
		JLabel lblFPS = new JLabel("FPS :");
		JPanel panel_1 = new JPanel();
		
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
		springLayout.putConstraint(SpringLayout.WEST, lblPlaka, 13, SpringLayout.EAST, CameraView);
		springLayout.putConstraint(SpringLayout.WEST, panel, 11, SpringLayout.EAST, CameraView);
		springLayout.putConstraint(SpringLayout.EAST, CameraView, -6, SpringLayout.WEST, txtPlaka);
		
		
		CameraView.setBackground(Color.BLACK);
		CameraView.setBorder(new LineBorder(new Color(0, 0, 0)));
		

		springLayout.putConstraint(SpringLayout.WEST, CameraView, 12, SpringLayout.WEST, frmKaramrselEitimMerkezi.getContentPane());
		frmKaramrselEitimMerkezi.getContentPane().add(CameraView);
		CameraView.setOpaque(false);
		
		JScrollPane scrollPane = new JScrollPane();
		springLayout.putConstraint(SpringLayout.EAST, panel, -83, SpringLayout.EAST, scrollPane);
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 181, SpringLayout.NORTH, frmKaramrselEitimMerkezi.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 419, SpringLayout.WEST, frmKaramrselEitimMerkezi.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, 357, SpringLayout.NORTH, frmKaramrselEitimMerkezi.getContentPane());
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		frmKaramrselEitimMerkezi.getContentPane().add(scrollPane);
		scrollPane.setViewportView(list);
	
		
		JLabel lblLogs = new JLabel("Logs");
		springLayout.putConstraint(SpringLayout.SOUTH, panel, -6, SpringLayout.NORTH, lblLogs);
		springLayout.putConstraint(SpringLayout.NORTH, lblLogs, 164, SpringLayout.NORTH, frmKaramrselEitimMerkezi.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblLogs, 419, SpringLayout.WEST, frmKaramrselEitimMerkezi.getContentPane());
		lblLogs.setHorizontalAlignment(SwingConstants.CENTER);
		frmKaramrselEitimMerkezi.getContentPane().add(lblLogs);
		
		JPanel pnlHeader = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, CameraView, 65, SpringLayout.SOUTH, pnlHeader);
		springLayout.putConstraint(SpringLayout.SOUTH, CameraView, 309, SpringLayout.SOUTH, pnlHeader);
		pnlHeader.setBackground(Color.DARK_GRAY);
		springLayout.putConstraint(SpringLayout.NORTH, pnlHeader, 0, SpringLayout.NORTH, frmKaramrselEitimMerkezi.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, pnlHeader, 0, SpringLayout.WEST, frmKaramrselEitimMerkezi.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, pnlHeader, 32, SpringLayout.NORTH, frmKaramrselEitimMerkezi.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, pnlHeader, 1366, SpringLayout.WEST, frmKaramrselEitimMerkezi.getContentPane());
		frmKaramrselEitimMerkezi.getContentPane().add(pnlHeader);
		SpringLayout sl_pnlHeader = new SpringLayout();
		pnlHeader.setLayout(sl_pnlHeader);
		
		JButton btnApplicationExit = new JButton("X");
		
		btnApplicationExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			
		});
		
		sl_pnlHeader.putConstraint(SpringLayout.NORTH, btnApplicationExit, 0, SpringLayout.NORTH, pnlHeader);
		sl_pnlHeader.putConstraint(SpringLayout.WEST, btnApplicationExit, -70, SpringLayout.EAST, pnlHeader);
		sl_pnlHeader.putConstraint(SpringLayout.SOUTH, btnApplicationExit, 32, SpringLayout.NORTH, pnlHeader);
		sl_pnlHeader.putConstraint(SpringLayout.EAST, btnApplicationExit, 0, SpringLayout.EAST, pnlHeader);
		btnApplicationExit.setSelected(false);
		btnApplicationExit.setToolTipText("");
		btnApplicationExit.setForeground(Color.WHITE);
		btnApplicationExit.setBackground(Color.RED);
		btnApplicationExit.setFocusable(false);
		pnlHeader.add(btnApplicationExit);
		
		JLabel lblK = new JLabel("Karam\u00FCrsel E\u011Fitim Merkezi Komutanl\u0131\u011F\u0131 Plaka Tan\u0131ma Sistemi");
		sl_pnlHeader.putConstraint(SpringLayout.NORTH, lblK, 6, SpringLayout.NORTH, pnlHeader);
		sl_pnlHeader.putConstraint(SpringLayout.WEST, lblK, 10, SpringLayout.WEST, pnlHeader);
		sl_pnlHeader.putConstraint(SpringLayout.EAST, lblK, -6, SpringLayout.WEST, btnApplicationExit);
		lblK.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblK.setForeground(Color.WHITE);
		pnlHeader.add(lblK);
		
		
		
		
		//frame.setUndecorated(true);
		//frame.setVisible(true);
		
		JButton OpenCamera = new JButton("OpenCamera");
		springLayout.putConstraint(SpringLayout.NORTH, OpenCamera, 6, SpringLayout.SOUTH, CameraView);
				
				//CameraView.GetPlateToImagePanel(panel);
				
				OpenCamera.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						CameraView.StartCamera();
					}
					
				});
		
	

		springLayout.putConstraint(SpringLayout.WEST, OpenCamera, 0, SpringLayout.WEST, lblFPS);
		
		frmKaramrselEitimMerkezi.getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{OpenCamera, lblFPS, lblPlaka, txtPlaka, panel, panel_1, rdbtnMotionTracking, CameraView, rdbtnLiveTracking}));
		
		
	
		springLayout.putConstraint(SpringLayout.WEST, lblFPS, 10, SpringLayout.WEST, frmKaramrselEitimMerkezi.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, lblFPS, 0, SpringLayout.NORTH, lblPlaka);
		springLayout.putConstraint(SpringLayout.EAST, lblFPS, -310, SpringLayout.WEST, lblPlaka);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(20)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(lblFPS, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
						.addComponent(OpenCamera, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(29, Short.MAX_VALUE))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(OpenCamera, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 138, Short.MAX_VALUE)
					.addComponent(lblFPS)
					.addContainerGap())
		);
		panel_2.setLayout(gl_panel_2);
		
		tabbedPane.addTab("Camera Options", null, panel_1, null);
		springLayout.putConstraint(SpringLayout.NORTH, panel_1, 368, SpringLayout.NORTH, frmKaramrselEitimMerkezi.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel_1, 32, SpringLayout.WEST, frmKaramrselEitimMerkezi.getContentPane());
		
		rdbtnLiveTracking = new JRadioButton("Live Tracking");
		trackingModeGroup.add(rdbtnLiveTracking);
		rdbtnLiveTracking.setToolTipText("");
		
		
		rdbtnMotionTracking = new JRadioButton("Motion Tracking");
		trackingModeGroup.add(rdbtnMotionTracking);
		
			
			rdbtnMotionTracking.setSelected(true);
			rdbtnMotionTracking.setToolTipText("");
			
			JLabel lblMotionValue = new JLabel("Motion Value :");
			springLayout.putConstraint(SpringLayout.NORTH, lblMotionValue, 426, SpringLayout.NORTH, frmKaramrselEitimMerkezi.getContentPane());
			springLayout.putConstraint(SpringLayout.WEST, lblMotionValue, 43, SpringLayout.WEST, frmKaramrselEitimMerkezi.getContentPane());
			
			JLabel label = new JLabel("Motion Offset :");
			springLayout.putConstraint(SpringLayout.NORTH, label, 450, SpringLayout.NORTH, frmKaramrselEitimMerkezi.getContentPane());
			springLayout.putConstraint(SpringLayout.WEST, label, 40, SpringLayout.WEST, frmKaramrselEitimMerkezi.getContentPane());
			
			JSpinner spinner = new JSpinner();
			springLayout.putConstraint(SpringLayout.NORTH, spinner, 422, SpringLayout.NORTH, frmKaramrselEitimMerkezi.getContentPane());
			springLayout.putConstraint(SpringLayout.WEST, spinner, 125, SpringLayout.WEST, frmKaramrselEitimMerkezi.getContentPane());
			springLayout.putConstraint(SpringLayout.SOUTH, spinner, 445, SpringLayout.NORTH, frmKaramrselEitimMerkezi.getContentPane());
			spinner.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					if((int)spinner.getValue() < 0)
						spinner.setValue(0);
					CameraView.SetMotionValue((int) spinner.getValue());
					CameraView.repaint();
				}
			});
			
			JSpinner spinner_1 = new JSpinner();
			springLayout.putConstraint(SpringLayout.NORTH, spinner_1, 450, SpringLayout.NORTH, frmKaramrselEitimMerkezi.getContentPane());
			springLayout.putConstraint(SpringLayout.WEST, spinner_1, 125, SpringLayout.WEST, frmKaramrselEitimMerkezi.getContentPane());
			spinner_1.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					if((int)spinner_1.getValue() < 0)
						spinner_1.setValue(0);
					CameraView.SetMotionOffsetValue((int) spinner_1.getValue());
					CameraView.repaint();
				}
			});
			GroupLayout gl_panel_1 = new GroupLayout(panel_1);
			gl_panel_1.setHorizontalGroup(
				gl_panel_1.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel_1.createSequentialGroup()
						.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
							.addComponent(rdbtnLiveTracking)
							.addComponent(rdbtnMotionTracking)
							.addGroup(gl_panel_1.createSequentialGroup()
								.addContainerGap()
								.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
									.addComponent(label)
									.addComponent(lblMotionValue))
								.addGap(28)
								.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
									.addComponent(spinner)
									.addComponent(spinner_1, GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE))))
						.addContainerGap(100, Short.MAX_VALUE))
			);
			gl_panel_1.setVerticalGroup(
				gl_panel_1.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel_1.createSequentialGroup()
						.addComponent(rdbtnLiveTracking)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(rdbtnMotionTracking)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
							.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblMotionValue))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
							.addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(label))
						.addContainerGap(87, Short.MAX_VALUE))
			);
			panel_1.setLayout(gl_panel_1);
	}
}
