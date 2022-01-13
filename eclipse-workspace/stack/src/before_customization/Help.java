package before_customization;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public abstract class Help {

	public static String EXCLE_FILE_PATH = "D:/customization_azurestackfiles.xlsx";
	
	public static final String GLOBAL_REPO_PATH = "D:/Repository/previous/azure-stack-docs/";

	public static final String GLOBAL_BACKUP_FOLDER = "D:/Repository/latest/azure-stack-docs/";
	
	public static final String DELETED = "deleted";
	
	public static final String NEW = "New";
	
	public static String EQUAL = "equal";
	
	public static String CHANGED = "Changed";
	
	public static String METEDATE_CHANGED = "Metedata Changed";
	
	public static String Diff_COUNT = "Diff Count: ";
	
	public static int UPDATE_TYPE_UPDATE = 0;
	
	public static int UPDATE_TYPE_DELETE = 1;
	
	public static int UPDATE_TYPE_ADDED = 2;
	
	protected abstract List<String> getUselessFiles();
	
	protected List<FileStatus> filterChangedList(List<FileStatus> changedFileList) {
		if(!getUselessFiles().isEmpty()) {
			List<FileStatus> newChangedList = new ArrayList<FileStatus>();
			
			for(FileStatus fileInfo: changedFileList) {
				boolean isUseless = false;
				
				for(String uselessFile: getUselessFiles()) {
					if(!uselessFile.trim().equals("") && fileInfo.getFilePath().endsWith(uselessFile)) {
						isUseless = true;
						break;
					}
				}
				
				if(isUseless == false) {
					newChangedList.add(fileInfo);
				}
				
			}
			
			return newChangedList;
		}  
		
		return changedFileList;
	}
	
	public synchronized List<FileStatus> getChangedFilesList(String service) {
		// get changed files: changed/deleted/added
		List<FileStatus> changedFileList = new ArrayList<FileStatus>(500);
		
		try {
			System.out.println("========================================================");
			System.out.println("Start to create customizaton plan -> " + service + ".");  
			
			// get local backup files
			List<String> backupFileList = getAllBackupFiles(service);
			
			System.out.println("Get all backup files done."); 
			
			// get global files
			List<String> globalFileList = getAllGlobalFiles(service);
			
			System.out.println("Get all global files done."); 
			
			for(String backupFile: backupFileList) {
				String globalFilePath = Help.GLOBAL_REPO_PATH + backupFile;
				String backupFilsPath = Help.GLOBAL_BACKUP_FOLDER + backupFile;
				
				File globalFile = new File(globalFilePath);
				if(globalFile.exists()) {
					// changed
					 String compareResult = Help.compare(backupFilsPath, globalFilePath);
					 
					 if(compareResult.equals(Help.METEDATE_CHANGED)) {
						 
						 FileStatus fileStatus = new FileStatus();
						 fileStatus.setFilePath(backupFile); 
						 fileStatus.setFileStatus(Help.CHANGED);
						 fileStatus.setFileComment(Help.METEDATE_CHANGED); 
						 
						 changedFileList.add(fileStatus);
					 } else if(compareResult.contains(Help.Diff_COUNT)) {
						 FileStatus fileStatus = new FileStatus();
						 fileStatus.setFilePath(backupFile); 
						 fileStatus.setFileStatus(Help.CHANGED);
						 fileStatus.setDiffCount(Integer.parseInt(compareResult.split(":")[1].trim())); 
						 
						 changedFileList.add(fileStatus);
					 }
				} else {
					 FileStatus fileStatus = new FileStatus();
					 fileStatus.setFilePath(backupFile); 
					 fileStatus.setFileStatus(Help.DELETED);
					 
					 changedFileList.add(fileStatus);
				}
			}
			
			// added
			globalFileList.removeAll(backupFileList);
			
			for(String globalFile: globalFileList) {
				FileStatus fileStatus = new FileStatus();
				fileStatus.setFilePath(globalFile); 
				fileStatus.setFileStatus(Help.NEW);
				 
				changedFileList.add(fileStatus);
			}
			
			System.out.println("Get changed list done."); 
			
			// remove useless files from changed list
			changedFileList = filterChangedList(changedFileList);
			
			Collections.sort(changedFileList);
			
			System.out.println("Filter changed list done."); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return changedFileList;
	}

	public static int compare_details(List<String> list_1, List<String> list_2) {
		int size_1=list_1.size();
		int size_2=list_2.size();
		
		int diffCount = 0;
		
		int index_1 = 0;
		int index_2 = 0;
		int update_type = -1;
		
		for(; index_1<size_1 && index_2<size_2; ) {
			// same line, same content
			if(list_1.get(index_1).equals(list_2.get(index_2))) {
				index_1++;
				index_2++;
				update_type = -1;
				continue;
			} else {
				int current_index_2 = index_2;
				
				for(; index_2<size_2; index_2++) {
					if(list_1.get(index_1).equals(list_2.get(index_2))) {
						update_type = UPDATE_TYPE_ADDED;
						break;
					}
				}  
				
				if(update_type == UPDATE_TYPE_ADDED) {
					diffCount++;
					
					index_1++;
					index_2++;
					update_type = -1;
				} else {
					index_2 = current_index_2;
					
					int current_index_1 = index_1;
					
					for(; index_1<size_1; index_1++) {
						if(list_1.get(index_1).equals(list_2.get(index_2))) {
							update_type = UPDATE_TYPE_DELETE;
 							break;
						}
					}  
					
					if(update_type == UPDATE_TYPE_DELETE) {
						diffCount++;
						
						index_1++;
						index_2++;
						update_type = -1;
					} else {
						diffCount++;
						
						index_1 = current_index_1;
						// is update_type_change
						index_1++;
						index_2++;
						update_type = -1;
					}
				}
			}
		}
		
		return diffCount;
	}
	
	public static String compare(String file_1, String file_2) {
		BufferedReader reader_1 = null;
		BufferedReader reader_2 = null;

        try {
        	reader_1 = new BufferedReader(new InputStreamReader(new FileInputStream(file_1), "utf-8"));
        	reader_2 = new BufferedReader(new InputStreamReader(new FileInputStream(file_2), "utf-8"));

        	//---------------------------------------------
            List<String> list_1 = new ArrayList<String>();
            List<String> list_1_metedata = new ArrayList<String>();
            List<String> list_1_body = new ArrayList<String>();
            
            String line = null;
            while ((line = reader_1.readLine()) != null) {
            	list_1.add(line);
            }
            
            // metedata exist
            if(list_1.size()>0 && list_1.get(0).trim().equals("---")) {
            	// set metedata list
            	list_1_metedata.add(list_1.get(0));
            	
            	// start from 1
            	for(int i=1, size=list_1.size(); i<size; i++) {
            		list_1_metedata.add(list_1.get(i));
            		
            		if(list_1.get(i).trim().equals("---")) {
            			break;
            		} 
            	}
            	
            	// set body list
            	// start from 1
            	int second3MinusPlace = 0;
            	for(int i=1, size=list_1.size(); i<size; i++) {
            		if(list_1.get(i).trim().equals("---")) {
            			second3MinusPlace = i;
            			break;
            		}
            	}
            	
            	for(int i=second3MinusPlace+1, size=list_1.size(); i<size; i++) {
            		list_1_body.add(list_1.get(i)); 
            	}
            	
            } else {
            	// metedata not exist, only set body list
            	list_1_body.addAll(list_1);
            }
            
            //---------------------------------------------
            List<String> list_2 = new ArrayList<String>();
            List<String> list_2_metedata = new ArrayList<String>();
            List<String> list_2_body = new ArrayList<String>();
            while ((line = reader_2.readLine()) != null) {
            	list_2.add(line);
            }
            
            // metedata exist
            if(list_2.size()>0 && list_2.get(0).trim().equals("---")) {
            	// set metedata list
            	list_2_metedata.add(list_2.get(0));
            	
            	// start from 1
            	for(int i=1, size=list_2.size(); i<size; i++) {
            		list_2_metedata.add(list_2.get(i));
            		
            		if(list_2.get(i).trim().equals("---")) {
            			break;
            		} 
            	}
            	
            	// set body list
            	// start from 1
            	int second3MinusPlace = 0;
            	for(int i=1, size=list_2.size(); i<size; i++) {
            		if(list_2.get(i).trim().equals("---")) {
            			second3MinusPlace = i;
            			break;
            		}
            	}
            	
            	for(int i=second3MinusPlace+1, size=list_2.size(); i<size; i++) {
            		list_2_body.add(list_2.get(i)); 
            	}
            } else {
            	// metedata not exist
            	list_2_body.addAll(list_2);
            }
            
            // compare
            if(list_1.equals(list_2)) {
            	return EQUAL;
            } else {
            	if(!list_1_metedata.equals(list_2_metedata) && list_1_body.equals(list_2_body)) { 
            		return METEDATE_CHANGED;
            	} else {
           			
           			return Diff_COUNT + compare_details(list_1_body, list_2_body);
            	}
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader_1 != null) {
                	reader_1.close();
                	reader_1 = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            try {
                if (reader_2 != null) {
                	reader_2.close();
                	reader_2 = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		
        return CHANGED;
	}
	
	public static void writeToExcel(List<FileStatus> changedFileList, XSSFWorkbook workbook, String sheetName) {
		XSSFSheet sheet = workbook.createSheet(sheetName); 
		
		CellStyle deletedstyle = workbook.createCellStyle();
		deletedstyle.setFillForegroundColor(IndexedColors.RED.getIndex());
		deletedstyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		deletedstyle.setBorderBottom(BorderStyle.THIN);
		deletedstyle.setBorderTop(BorderStyle.THIN);
		deletedstyle.setBorderLeft(BorderStyle.THIN);
		deletedstyle.setBorderRight(BorderStyle.THIN);
	    
	    CellStyle new_style = workbook.createCellStyle();
	    new_style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
	    new_style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    new_style.setBorderBottom(BorderStyle.THIN);
	    new_style.setBorderTop(BorderStyle.THIN);
	    new_style.setBorderLeft(BorderStyle.THIN);
	    new_style.setBorderRight(BorderStyle.THIN);
	    
	    CellStyle metedatastyle = workbook.createCellStyle();
	    metedatastyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
	    metedatastyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    metedatastyle.setBorderBottom(BorderStyle.THIN);
	    metedatastyle.setBorderTop(BorderStyle.THIN);
	    metedatastyle.setBorderLeft(BorderStyle.THIN);
	    metedatastyle.setBorderRight(BorderStyle.THIN);
		
	    // -----------------------------------------------------
	    CellStyle headerstyle = workbook.createCellStyle();
	    headerstyle.setFillForegroundColor(IndexedColors.YELLOW1.getIndex());
	    headerstyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    headerstyle.setBorderBottom(BorderStyle.THIN);
	    headerstyle.setBorderTop(BorderStyle.THIN);
	    headerstyle.setBorderLeft(BorderStyle.THIN);
	    headerstyle.setBorderRight(BorderStyle.THIN);
	    
	    CellStyle bodystyle = workbook.createCellStyle();
	    bodystyle.setBorderBottom(BorderStyle.THIN);
	    bodystyle.setBorderTop(BorderStyle.THIN);
	    bodystyle.setBorderLeft(BorderStyle.THIN);
	    bodystyle.setBorderRight(BorderStyle.THIN);
		
		Row row = sheet.createRow(0);
		Cell cell_one = row.createCell(0);
		cell_one.setCellValue("File"); 
		cell_one.setCellStyle(headerstyle); 
		
		Cell cell_two = row.createCell(1);
		cell_two.setCellValue("Status");
		cell_two.setCellStyle(headerstyle); 
		
		Cell cell_three = row.createCell(2);
		cell_three.setCellValue("Details");
		cell_three.setCellStyle(headerstyle); 
		
		row.setHeight((short)500);
	    //------------------------------------------------------
	    
		for(int i=0, size=changedFileList.size(); i<size; i++) {
			FileStatus fileStatus = changedFileList.get(i);
			
			// row 0 has been created. so use i + 1 here
			row = sheet.createRow(i + 1);
			Cell cell_0 = row.createCell(0);
			cell_0.setCellValue(fileStatus.getFilePath()); 
			cell_0.setCellStyle(bodystyle); 
			
			Cell cell_1 = row.createCell(1);
			cell_1.setCellValue(fileStatus.getFileStatus()); 
			cell_1.setCellStyle(bodystyle); 
			
			if(fileStatus.getFileStatus().equals(NEW)) {
				cell_1.setCellStyle(new_style); 
			} else if(fileStatus.getFileStatus().equals(DELETED)) {
				cell_1.setCellStyle(deletedstyle); 
			}
			
			Cell cell_2 = row.createCell(2);
			if(fileStatus.getFileComment() != null) {
				cell_2.setCellValue(fileStatus.getFileComment());
			} else {
				if(fileStatus.getDiffCount() > 0) {
					cell_2.setCellValue(Diff_COUNT + fileStatus.getDiffCount());
				}
			}
			
			cell_2.setCellStyle(bodystyle); 
			
			if(fileStatus.getFileComment() != null && fileStatus.getFileComment().equals(METEDATE_CHANGED)) {
				cell_2.setCellStyle(metedatastyle);
			}
		}
		
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
 
	}
	
	public static void writeToExcel(String excelPath, Map<String, List<FileStatus>> changedListMap) throws IOException { 
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		// show Statistics info 
		createStatisticsSheet(workbook, changedListMap);
		
		Set<String> sheetNameSet = changedListMap.keySet();
		for(String sheetName: sheetNameSet) {
			
			writeToExcel(changedListMap.get(sheetName), workbook, sheetName); 
		}
		
		try {
			FileOutputStream outputStream = new FileOutputStream(new File(excelPath));
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("========================================================");
		System.out.println("Write to file done.");
	}
	
	
	private static void createStatisticsSheet(XSSFWorkbook workbook, Map<String, List<FileStatus>> changedListMap) throws IOException { 
		XSSFSheet sheet = workbook.createSheet("Customization Info"); 
		
	    CellStyle headerstyle = workbook.createCellStyle();
	    headerstyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
	    headerstyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    headerstyle.setBorderBottom(BorderStyle.THIN);
	    headerstyle.setBorderTop(BorderStyle.THIN);
	    headerstyle.setBorderLeft(BorderStyle.THIN);
	    headerstyle.setBorderRight(BorderStyle.THIN);
	    
	    CellStyle bodystyle = workbook.createCellStyle();
	    bodystyle.setBorderBottom(BorderStyle.THIN);
	    bodystyle.setBorderTop(BorderStyle.THIN);
	    bodystyle.setBorderLeft(BorderStyle.THIN);
	    bodystyle.setBorderRight(BorderStyle.THIN);
		
		Set<String> sheetNameSet = changedListMap.keySet();
		
		Row row = sheet.createRow(0);
		Cell cell_one = row.createCell(0);
		cell_one.setCellValue("Service Name"); 
		cell_one.setCellStyle(headerstyle); 
		
		Cell cell_two = row.createCell(1);
		cell_two.setCellValue("Updated Files");
		cell_two.setCellStyle(headerstyle); 
		
		row.setHeight((short)800);
		
		int i=1;
		for(String serviceName: sheetNameSet) {
			row = sheet.createRow(i);
			Cell cell_0 = row.createCell(0);
			cell_0.setCellValue(serviceName); 
			cell_0.setCellStyle(bodystyle); 
			
			Cell cell_1 = row.createCell(1);
			cell_1.setCellValue(changedListMap.get(serviceName).size());
			cell_1.setCellStyle(bodystyle); 
			
			row.setHeight((short)700);
			
			i++;
		}
	    
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
	}
	
	// eg: /articles/active-directory/authentication/howto-mfa-mfasettings.md
	public static void getAllFilesIncludingSubFolder(List<String> fileList, String folderName) {
        File folder = new File(folderName); 
        
        for (File file : folder.listFiles()) {
            if (file.isFile()) {
                if(file.getName().endsWith(".md") || file.getName().endsWith(".yml")) {
                	fileList.add(file.getAbsolutePath().replace("\\", "/").replace(GLOBAL_REPO_PATH, "").replace(GLOBAL_BACKUP_FOLDER, ""));
                }
            } else {
                getAllFilesIncludingSubFolder(fileList, file.getAbsolutePath());
            }
        }
    }
	
	public static void getAllFiles(List<String> fileList, String folderName) {
        File folder = new File(folderName); 
        
        for (File file : folder.listFiles()) {
            if (file.isFile()) {
                if(file.getName().endsWith(".md") || file.getName().endsWith(".yml")) {
                	fileList.add(file.getAbsolutePath().replace("\\", "/").replace(GLOBAL_REPO_PATH, "").replace(GLOBAL_BACKUP_FOLDER, ""));
                }
            } 
        }
    }
	
	protected List<String> getAllBackupFiles(String service) {
		List<String> backupFileList = new ArrayList<String>(1000);
		
		Help.getAllFilesIncludingSubFolder(backupFileList, Help.GLOBAL_BACKUP_FOLDER + service);
		
		return backupFileList;
	}
	
	protected List<String> getAllGlobalFiles(String service) {
		List<String> globalFileList = new ArrayList<String>(1000);
		
		Help.getAllFilesIncludingSubFolder(globalFileList, Help.GLOBAL_REPO_PATH + service);
		
		return globalFileList;
	}
	
	
	
	
}
