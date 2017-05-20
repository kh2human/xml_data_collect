package sql;

public class SQLManager {

	public static String getMergeSQL() {
		String sql = "MERGE INTO REF_RDFKEPCO_TB Z ";
		sql += "USING (SELECT ? ICUS, ? LOG_TIME, ? DATA_TYPE, ? VAL FROM DUAL) Y ";
		sql += "ON (Z.LOG_TIME = Y.LOG_TIME AND Z.ICUS = Y.ICUS AND Z.DATA_TYPE = Y.DATA_TYPE) ";
		sql += "WHEN MATCHED THEN ";
		sql += "UPDATE SET Z.VAL = Y.VAL ";
		sql += "WHEN NOT MATCHED THEN ";
		sql += "INSERT (Z.ICUS, Z.LOG_TIME, Z.DATA_TYPE, Z.VAL) ";
		sql += "VALUES (Y.ICUS, Y.LOG_TIME, Y.DATA_TYPE, Y.VAL) ";
		return sql;
	}
	
	public static String getMergeSQL1() {
		String sql = "MERGE INTO REF_RDFKEPCO_TB Z ";
		sql += "USING (SELECT ? ICUS, ? LOG_TIME, ? DATA_TYPE, FN_KEPCOTAG(?, ?) TAGSN, ? VAL FROM DUAL) Y ";
		sql += "ON (Z.LOG_TIME = Y.LOG_TIME AND Z.ICUS = Y.ICUS AND Z.DATA_TYPE = Y.DATA_TYPE) ";
		sql += "WHEN MATCHED THEN ";
		sql += "UPDATE SET Z.VAL = Y.VAL ";
		sql += "WHEN NOT MATCHED THEN ";
		sql += "INSERT (Z.ICUS, Z.LOG_TIME, Z.DATA_TYPE, Z.TAGSN, Z.VAL) ";
		sql += "VALUES (Y.ICUS, Y.LOG_TIME, Y.DATA_TYPE, Y.TAGSN, Y.VAL) ";
		return sql;
	}
	
	public static void main(String[] args) {
		System.out.println(getMergeSQL());
	}

}
