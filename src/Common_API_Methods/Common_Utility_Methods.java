package Common_API_Methods;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Common_Utility_Methods {

	public static void EvidenceCreator(String FileName, String RequestBody, int StatusCode, String ResponseBody)
			throws IOException {

		File TextFile = new File("D:\\Evidence\\" + FileName + ".txt");
		//System.out.println("New blank text file of name: " + TextFile.getName());
		FileWriter dataWrite = new FileWriter(TextFile);

		dataWrite.write("Request Body is : " + "\n\n" + RequestBody + "\n\n");
		dataWrite.write("Status Code is :" + StatusCode + "\n\n");
		dataWrite.write("Response Body  is :" + "\n\n" + ResponseBody);
		dataWrite.close();

		System.out.println("Requestbody and Responsebody is written in text file" + TextFile.getName());

	}

	public static ArrayList<String> ReadDataExcel(String sheetname, String TestCaseName) throws IOException {

		ArrayList<String> ArrayData = new ArrayList<String>();
		// Create the object of file input stream to locate the excel file
		FileInputStream Fis = new FileInputStream(
				"D:\\Evidence2\\Evidence_Number_RestAssured.xlsx");
		// Step 2: Open the excel file by creating the object XSSFWorkbook
		XSSFWorkbook Workbook = new XSSFWorkbook(Fis);
		// Step no : Open the desired Sheet
		int countofsheet = Workbook.getNumberOfSheets();
		for (int i = 0; i < countofsheet; i++) {
			String SheetName = Workbook.getSheetName(i);
			// step no 4: Access the desired sheet
			
			
			if (SheetName.equalsIgnoreCase(sheetname)) {
				// Use XSSFSheet to save the sheet into the variable
				XSSFSheet Sheet = Workbook.getSheetAt(i);

				// create the iterator to iterator through rows and find out in which
				Iterator<Row> Rows = Sheet.iterator();
				Row FirstRow = Rows.next();
				// Create the Iterator through the cells of first row to find out which cell
				// contains test case
				Iterator<Cell> CellsOfFirstRow = FirstRow.cellIterator();
				int k = 0;
				int TC_column = 0;
				while (CellsOfFirstRow.hasNext()) {
					Cell cellValue = CellsOfFirstRow.next();
					if (cellValue.getStringCellValue().equalsIgnoreCase("TestCaseName")) {
						TC_column = k;
						// System.out.println("expected column for test case name:" +k);
						break;
					}
					k++;
				}
				// Verify the row where the desired test case is found and fetch entire row.
				while (Rows.hasNext()) {
					Row Datarow = Rows.next();
					String TCName = Datarow.getCell(TC_column).getStringCellValue();
					if (TCName.equalsIgnoreCase(TestCaseName)) 
						
					{
						Iterator<Cell> CellValues = Datarow.cellIterator();
						while (CellValues.hasNext()) {
							String Data = CellValues.next().getStringCellValue();
							ArrayData.add(Data);
						}
						break;
					}
				}

			}

		}
		return ArrayData;
	}
}
