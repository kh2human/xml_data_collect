package core;

import config.DBConfig;
import config.XMLConfig;
import gui.status.ControlPanel;
import gui.status.StatusList;
import time.TimeChecker;
import java.text.DecimalFormat;
import java.math.RoundingMode;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.SwingWorker;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import db.DBDriver;

public class LoopTransfer extends SwingWorker<Void, Void> {
	
	private final String dbType;
	private final String dbDriver;
	private java.sql.Connection con;
	private final StatusList list;
	private final ControlPanel panel;
	private xml.DocumentBus docBus = null;
	private boolean isLoop = true;

	public LoopTransfer(DBConfig dbConfig, XMLConfig xmlConfig, StatusList list, ControlPanel panel) {
		this.panel = panel;
		this.list = list;
		dbType = dbConfig.getDbType();
		dbDriver = DBDriver.getDriver(dbType);
		try {
			Class.forName(dbDriver);
			con = java.sql.DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getId(), dbConfig.getPw());
		} catch (Exception e) {
			System.out.println(e.getMessage());
			panel.getStart().setEnabled(false);
			panel.getStop().setEnabled(true);
//			systemDown(e);
		} 
	}
	
	public void setDocBus(xml.DocumentBus docBus) {
		this.docBus = docBus;
	}
	
	public void setLoop(boolean isLoop) {
		this.isLoop = isLoop;
	}
	
//	private void systemDown(Exception e) {
//		
//	}
	
	@Override
	public Void doInBackground() {
		List<Document> docs = docBus.getList();
		Document doc = null;
		int errorWaiting = 1000;
		
		while (isLoop) {
			try {
				if (docs != null && docs.size() > 0) {
					System.out.println(TimeChecker.getTime() + " > REMOVING DOC...");
					file.Logger.writeLog(TimeChecker.getTime() + " > REMOVING DOC...");
					doc = docs.remove(0);
					list.getXmlConnection().setText(docs.size() + "");
				}
				if (doc == null || !doc.hasChildNodes()) {
					Thread.sleep(1000);
					continue;
				}
				PreparedStatement st = null;
				
				st = con.prepareStatement(sql.SQLManager.getMergeSQL());
				NodeList dataList = doc.getElementsByTagName("DATA");
				
				int datas = 0;
				String log_date = "";

				for (int i = 0; i < dataList.getLength(); i++) {
					org.w3c.dom.Node nData = dataList.item(i);
					
					if (nData.getNodeType() == Node.ELEMENT_NODE) {
						Element eData = (Element)nData;
						NodeList usageList = eData.getElementsByTagName("USAGE");

						for (int j = 0; j < usageList.getLength(); j++) {
							Node nUsage = usageList.item(j);
							
							if (nUsage.getNodeType() == Node.ELEMENT_NODE) {
								Element eUsage = (Element)nUsage;
								
								for (int k = 0; k < eUsage.getChildNodes().getLength(); k++) {
									int sqlOrder = 1;
									String dataType = eUsage.getChildNodes().item(k).getNodeName();
									Double val = Double.parseDouble(xml.ElementManager.getElementContent(eUsage, dataType));
									
									if ("TIME_ID".equals(dataType)) {
										continue;
									}
									
									DecimalFormat df = new DecimalFormat("#.####");
									df.setRoundingMode(RoundingMode.DOWN);
									val = Double.parseDouble(df.format(val));
									String icus = xml.ElementManager.getElementContent(eData, "ICUS");
									String log_time = xml.ElementManager.getElementContent(eUsage, "TIME_ID").substring(0, 10);
									
									st.setString(sqlOrder++, icus);
									st.setString(sqlOrder++, log_time);
									st.setString(sqlOrder++, dataType);
//									st.setString(sqlOrder++, icus);
//									st.setString(sqlOrder++, dataType);
									st.setDouble(sqlOrder++, val);
									st.executeUpdate();
									list.getRunningStatus().setText(TimeChecker.getTime() + " > " + log_time.substring(0, 8) + ", " + (++datas) + " datas entered.");
									log_date = log_time.substring(0, 8);
								}
							}
						}
					}
				}
				if (!st.isClosed()) {
					st.close();
				}
				
				System.out.println(TimeChecker.getTime() + " > " + log_date + ", " + datas + " datas enterd.");
				file.Logger.writeLog(TimeChecker.getTime() + " > " + log_date + ", " + datas + " datas enterd.");
				doc = null;
				System.out.println(TimeChecker.getTime() + " > MERGE END, Size: " + docs.size() + "");
				file.Logger.writeLog(TimeChecker.getTime() + " > MERGE END, Size: " + docs.size() + "");
				Thread.sleep(1000);
				errorWaiting = 1000;
			} catch (Exception e) {
				System.out.println(TimeChecker.getTime() + " > DB MERGE ERROR: " + e.getMessage());
				file.Logger.writeLog(TimeChecker.getTime() + " > DB MERGE ERROR: " + e.getMessage());
				try {
					Thread.sleep(errorWaiting);
					errorWaiting += 500;
				} catch (Exception e1) {
					
				}
				
			}
		}
		try {
			if (!con.isClosed())
				con.close();
			panel.getStart().setEnabled(true);
			panel.getStop().setEnabled(false);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		System.out.println(TimeChecker.getTime() + " > END DB MERGE JOB.");
		file.Logger.writeLog(TimeChecker.getTime() + " > END DB MERGE JOB.");
		
		return null;
	}
}
