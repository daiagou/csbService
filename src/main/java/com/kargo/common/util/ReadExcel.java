package com.kargo.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.Permission;
import java.security.PermissionCollection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ReadExcel {
    private static final Logger logger = LoggerFactory.getLogger(ReadExcel.class);


    public static List<String> readCards(File file) throws Exception {
        List<String> list = new ArrayList<String>();
        Workbook workbook = null;
        InputStream fis = null;
        try {
            fis = new FileInputStream(file);
            if(file.getName().endsWith("xls")){
                //2003
                workbook = new HSSFWorkbook(fis);
            }else if(file.getName().endsWith("xlsx")){
                //2007
                workbook = new XSSFWorkbook(fis);
            }
            Sheet sheet = workbook.getSheetAt(0);
            int startRowNum = sheet.getFirstRowNum();
            int endRowNum = sheet.getLastRowNum();
            String temp = null;
            for (int rowNum = startRowNum; rowNum <= endRowNum; rowNum++) {
                Row row = sheet.getRow(rowNum);
                Cell cell = row.getCell(0);
                temp=cell.getStringCellValue().trim();
                if(StringUtils.isNotBlank(temp)){
                    list.add(temp);
                }
            }
        }finally {
            try {
                fis.close();
                workbook.close();
            }catch (Exception e){
                logger.error(e.getMessage(),e);
            }
        }
        return list;
    }





    public void readExcelFromPath(String filePath) throws Exception {
        System.out.println("begin");

        File file = new File(filePath);

        InputStream fis = new FileInputStream(file);

        POIFSFileSystem pfs = new POIFSFileSystem(fis);
        EncryptionInfo encInfo = new EncryptionInfo(pfs);
        Decryptor decryptor = Decryptor.getInstance(encInfo);
        decryptor.verifyPassword("KGOKGO");
        XSSFWorkbook workbook = new XSSFWorkbook(decryptor.getDataStream(pfs));

        Sheet sheet = workbook.getSheetAt(0);
        int startRowNum = sheet.getFirstRowNum();
        int endRowNum = sheet.getLastRowNum();
        for (int rowNum = startRowNum; rowNum <= endRowNum; rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row == null)
                continue;
            int startCellNum = row.getFirstCellNum();
            int endCellNum = row.getLastCellNum();
            for (int cellNum = startCellNum; cellNum < endCellNum; cellNum++) {
                Cell cell = row.getCell(cellNum);
                if (cell == null)
                    continue;
                int type = cell.getCellType();
                switch (type) {
                    case Cell.CELL_TYPE_NUMERIC:// 数值、日期类型
                        double d = cell.getNumericCellValue();
                        if (HSSFDateUtil.isCellDateFormatted(cell)) {// 日期类型
                            Date date = cell.getDateCellValue();
                            Date date1 = HSSFDateUtil.getJavaDate(d);
                            System.out.print(" "
                                    + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                    .format(date) + " ");
                        } else {// 数值类型
                            System.out.print(" " + d + " ");
                        }
                        break;
                    case Cell.CELL_TYPE_BLANK:// 空白单元格
                        System.out.print(" null ");
                        break;
                    case Cell.CELL_TYPE_STRING:// 字符类型
                        System.out.print(" " + cell.getStringCellValue() + " ");
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:// 布尔类型
                        System.out.println(cell.getBooleanCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_ERROR: // 故障
                        System.err.println("非法字符");// 非法字符;
                        break;
                    default:
                        System.err.println("error");// 未知类型
                        break;
                }
            }
            System.out.println();
        }
        System.out.println("end");
    }

    public static void main(String[] args) throws Exception {
        String excelPath = "/Users/dannygu/Documents/workspace_2017/PlatformSalesFromExcel/file/ExcelAdapter.xlsx";

        removeCryptographyRestrictions();
        ReadExcel readExcel = new ReadExcel();
        readExcel.readExcelFromPath(excelPath);
    }

    public static void removeCryptographyRestrictions() {
        if (!isRestrictedCryptography()) {
            logger.info("Cryptography restrictions removal not needed");
            return;
        }
        try {
        /*
         * Do the following, but with reflection to bypass access checks:
         *
         * JceSecurity.isRestricted = false;
         * JceSecurity.defaultPolicy.perms.clear();
         * JceSecurity.defaultPolicy.add(CryptoAllPermission.INSTANCE);
         */
            final Class<?> jceSecurity = Class.forName("javax.crypto.JceSecurity");
            final Class<?> cryptoPermissions = Class.forName("javax.crypto.CryptoPermissions");
            final Class<?> cryptoAllPermission = Class.forName("javax.crypto.CryptoAllPermission");

            final Field isRestrictedField = jceSecurity.getDeclaredField("isRestricted");
            isRestrictedField.setAccessible(true);
            final Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(isRestrictedField, isRestrictedField.getModifiers() & ~Modifier.FINAL);
            isRestrictedField.set(null, false);

            final Field defaultPolicyField = jceSecurity.getDeclaredField("defaultPolicy");
            defaultPolicyField.setAccessible(true);
            final PermissionCollection defaultPolicy = (PermissionCollection) defaultPolicyField.get(null);

            final Field perms = cryptoPermissions.getDeclaredField("perms");
            perms.setAccessible(true);
            ((Map<?, ?>) perms.get(defaultPolicy)).clear();

            final Field instance = cryptoAllPermission.getDeclaredField("INSTANCE");
            instance.setAccessible(true);
            defaultPolicy.add((Permission) instance.get(null));

            logger.info("Successfully removed cryptography restrictions");
        } catch (final Exception e) {
            logger.error("Failed to remove cryptography restrictions" + e);
        }
    }

    private static boolean isRestrictedCryptography() {
        // This simply matches the Oracle JRE, but not OpenJDK.
        return "Java(TM) SE Runtime Environment".equals(System.getProperty("java.runtime.name"));
    }
}