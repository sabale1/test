package in.ecgc.smile.erp.accounts.util;

import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.ecgc.smile.erp.accounts.model.FTR;

public class FtrReportedToBankExcel {

	private static final Logger LOGGER = LoggerFactory.getLogger(FtrReportedToBankExcel.class);

	public FtrReportedToBankExcel() {
	}

	public static Integer generateSheet(List<FTR> ftrs , String bank) throws IOException {
		LOGGER.info(ftrs.toString());
		  XSSFWorkbook ftrExcel = new XSSFWorkbook();
	      XSSFSheet rptbnk = ftrExcel.createSheet("Bank Report");
	      XSSFRow row;
		  LocalDateTime now = LocalDateTime.now();
	      String st = now.toString();
	      st = st.split("T")[0];
	      UUID st2 = UUID.randomUUID();
	      String home = System.getProperty("user.home");
	      String fileLocation = new File("src\\main\\resources\\").getAbsolutePath()+"\\"+bank+"_"+st+"_"+st2.toString().split("-")[1]+".xlsx";
	      FileOutputStream file = new FileOutputStream(fileLocation);
	      row = rptbnk.createRow(1);
	      Cell cell = row.createCell(1);
          cell.setCellValue("Fund Transfer");
          ftrExcel.write(file);
          file.close();
          ftrExcel.close();
		return 1;
	}


}
