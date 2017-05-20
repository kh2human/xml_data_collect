package core;

//import gui.status.StatusList;
import time.TimeChecker;
//import java.util.List;
import org.w3c.dom.Document;
import java.util.Calendar;
import java.net.URI;
import java.text.SimpleDateFormat;
import javax.swing.SwingWorker;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.xml.parsers.DocumentBuilder;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
//import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class XMLCollector extends SwingWorker<Void, Void> {
	private int diff;
	private Document doc = null;
	private xml.DocumentBus docBus = null;
//	private table.DataTable dataTable = null;
//	private xml.DocumentBus docBus1 = null;
	private JLabel status = null; 
	private Boolean isLoop = true;
	private String fileName = null;
	
	public XMLCollector(int diff) {
		this.diff = diff;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
//	public void setDataTable(table.DataTable dataTable) {
//		this.dataTable = dataTable;
//	}
	
//	public void setDocBus1(xml.DocumentBus docBus1) {
//		this.docBus1 = docBus1;
//	}
	
	public void setDocBus(xml.DocumentBus docBus) {
		this.docBus = docBus;
	}
	
	public void setStatus(JLabel status) {
		this.status = status;
	}
	
	public Document getDoc() {
		return doc;
	}
	
	public void setLoop(Boolean isLoop) {
		this.isLoop = isLoop;
	}
	
	@Override
	protected Void doInBackground() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMdd");
		final String url = "http://pccs.kepco.co.kr/iSmart/pccs/group.do?method=readGroupUsage&groupName=KWater&date=";
		int errorWaiting = 1000;
//		boolean isLoop = true;
//		Calendar start = Calendar.getInstance();
//		Calendar now = Calendar.getInstance();
		
		while (isLoop) {
//			Calendar now = Calendar.getInstance();
			
//			if (now.getTimeInMillis() - start.getTimeInMillis() > 20 * 60 * 1000) {
//				System.out.println(TimeChecker.getTime() + " > TIMEOUT(20min)");
//				start = Calendar.getInstance();
//				diff = 0;
//				continue;
//			}
			
			try {
//				System.out.println("-------------------------------------------------");
				System.out.println(TimeChecker.getTime() + " > XML LOAD START.");
				file.Logger.writeLog(TimeChecker.getTime() + " > XML LOAD START.");
				Calendar date = Calendar.getInstance();
				date.add(Calendar.DATE, diff);
				DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
				URI uri = URI.create(url + dateFormat.format(date.getTime()));
//				Calendar loadStart = Calendar.getInstance();
//				Calendar loading = Calendar.getInstance();
				URLConnection urlConnection = uri.toURL().openConnection();
				urlConnection.setConnectTimeout(5 * 60 * 1000);
				InputStream inputStream = urlConnection.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				Path xml = Paths.get(fileName);
//				if (Files.notExists(xml.getParent())) {
//					Files.createDirectories(xml.getParent());
//				}
				if (Files.notExists(xml)) {
					Files.createFile(xml);
				}
				BufferedWriter bufferedWriter = Files.newBufferedWriter(xml, Charset.forName("UTF-8"));
				String line = "";
				while ((line = bufferedReader.readLine()) != null) {
					bufferedWriter.write(line);
				}
				bufferedWriter.close();
				bufferedReader.close();
				inputStream.close();
				while (doc == null) {
//					loading = Calendar.getInstance();
					System.out.println(TimeChecker.getTime() + " > PARSING XML");
					file.Logger.writeLog(TimeChecker.getTime() + " > PARSING XML");
//					bufferedReader = Files.newBufferedReader(xml);
					doc = documentBuilder.parse(xml.toFile());
					System.out.println(TimeChecker.getTime() + " > NORMALIZING XML");
					file.Logger.writeLog(TimeChecker.getTime() + " > NORMALIZING XML");
//					doc = documentBuilder.parse(uri.toString());
					doc.getDocumentElement().normalize();
				}
				System.out.println(TimeChecker.getTime() + " > XML LOAD COMPLETE.");
				file.Logger.writeLog(TimeChecker.getTime() + " > XML LOAD COMPLETE.");
				/*
				 * doc -> docBus
				 * doc -> copy(doc) -> docBus1
				*/
//				if (dataTable != null) {
//					List<Document> listDocument = dataTable.getDocBus().getList();
//					while (listDocument.size() >= 2) {
//						listDocument.remove(0);
//					}
//					listDocument.add(doc);
//					dataTable.refresh();
//				}
				if (docBus != null) {
					docBus.getList().add(doc);
				}
				doc = null;
				if (Files.exists(xml)) {
					Files.delete(xml);
				}
				System.out.println(TimeChecker.getTime() + " > XML DOC ADDED.");
				file.Logger.writeLog(TimeChecker.getTime() + " > XML DOC ADDED.");
				if (status != null) {
					status.setText(docBus.getList().size() + "");
				}
//				diff--;
//				Thread.sleep(1000);
//				Thread.sleep(5 * 60 * 1000);
				Thread.sleep(15/*mi*/ * 60/*s/mi*/ * 1000/*ms/s*/); //DO NOT DELETE
				errorWaiting = 1000;
			} catch (Exception e) {
				System.out.println(TimeChecker.getTime() + " > XML LOAD ERROR: " + e.getMessage());
				file.Logger.writeLog(TimeChecker.getTime() + " > XML LOAD ERROR: " + e.getMessage());
				try {
					Thread.sleep(errorWaiting);
					errorWaiting += 500;
				} catch (Exception e1) {
					
				}
//				continue;
			}
		}
		System.out.println(TimeChecker.getTime() + " > END XML LOAD JOB");
		file.Logger.writeLog(TimeChecker.getTime() + " > END XML LOAD JOB");
		
		return null;
	}
	
	public static void main(String args[]) {
		JFrame jFrame = new JFrame("XML Collecting Test");
		JLabel jLabel = new JLabel("0");
		JButton toggle = new JButton("Start");
		xml.DocumentBus docBus = new xml.DocumentBus();
		XMLCollector xmlCollector = new XMLCollector(0);
		xmlCollector.setStatus(jLabel);
		xmlCollector.setDocBus(docBus);
		
		toggle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ("Start".equals(toggle.getText())) {
					xmlCollector.setLoop(true);
					xmlCollector.execute();
					toggle.setText("Stop");
				} else {
					xmlCollector.setLoop(false);
					xmlCollector.cancel(true);
					toggle.setText("Start");
				}
				
			}
		});
		
		jFrame.setAlwaysOnTop(true);
		jFrame.setLayout(new FlowLayout());
		jFrame.add(jLabel);
		jFrame.add(toggle);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.pack();
		jFrame.setResizable(false);
		jFrame.setVisible(true);
	}
}
