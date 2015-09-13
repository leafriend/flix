package com.leafriend.flix;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Flix {

    private Options options;

    private int directoryCount;

    private int fileCount;

    private List<File> files;

    public static void main(String[] args) {

        Options options = new Options();

        new Flix(options).run();

    }

    public Flix(Options options) {
        this.options = options;
        this.directoryCount = 0;
        this.fileCount = 0;
        this.files = new ArrayList<>();
    }

    public void run() {
        try {

            if (options.isVerbose()) {
                System.out.print(options.getScanDirectory().getCanonicalPath());
            }
            traverse(options.getScanDirectory());

            dump();

            System.out.print(options.getOutputFile().getCanonicalPath()
                    + " is generated for " + fileCount + " file"
                    + (fileCount > 1 ? "s" : "") + " in " + directoryCount
                    + " director" + (directoryCount > 1 ? "ies" : "y"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void traverse(File dir) throws IOException {
        for (File file : dir.listFiles()) {
            String path = file.getCanonicalPath();
            if (file.isFile()) {
                fileCount++;
                files.add(file);
                if (options.isVerbose()) {
                    String name = file.getName();
                    System.out.print(name);
                    for (int i = 0; i < name.length(); i++)
                        System.out.print("\b");
                    for (int i = 0; i < name.length(); i++)
                        System.out.print(" ");
                    for (int i = 0; i < name.length(); i++)
                        System.out.print("\b");
                }
            } else if (file.isDirectory()) {
                directoryCount++;
                if (options.isVerbose()) {
                    System.out.print("\n");
                    System.out.print(path + File.separator);
                }
                traverse(file);
            } else {
                error("Path '" + path + "' is not a file or directory");
            }
        }
    }

    private void error(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void dump() throws IOException {

        String sheetName = "Sheet1";// name of sheet

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(sheetName);

        CreationHelper createHelper = wb.getCreationHelper();

        CellStyle linkStyle = wb.createCellStyle();
        XSSFFont linkFont = wb.createFont();
        linkFont.setUnderline(XSSFFont.U_SINGLE);
        linkFont.setColor(IndexedColors.BLUE.getIndex());
        linkStyle.setFont(linkFont);

        {

            XSSFRow row = sheet.createRow(0);

            XSSFCell fileCell = row.createCell(0);
            fileCell.setCellValue("File");

            XSSFCell pathCell = row.createCell(1);
            pathCell.setCellValue("Path");

            XSSFCell dirCell = row.createCell(2);
            dirCell.setCellValue("Directory");

        }

        int r = 1;
        for (File file : files) {

            XSSFRow row = sheet.createRow(r++);

            XSSFCell fileCell = row.createCell(0);
            fileCell.setCellValue(file.getName());

            String path = file.getCanonicalPath();

            XSSFCell pathCell = row.createCell(1);
            pathCell.setCellValue(path);

            org.apache.poi.ss.usermodel.Hyperlink link = createHelper
                    .createHyperlink(Hyperlink.LINK_URL);
            link.setAddress(file.toURI().toString());
            pathCell.setHyperlink(link);
            pathCell.setCellStyle(linkStyle);

            int i = 2;
            int start = 0;
            while (start >= 0 && start <= path.length()) {
                int end = path.indexOf(File.separator, start);
                if (end < 0)
                    break;

                String dir = path.substring(start, end);

                XSSFCell dirCell = row.createCell(i++);
                dirCell.setCellValue(dir);
                start = end + 1;
            }

        }

        FileOutputStream fileOut = new FileOutputStream(options.getOutputFile());

        wb.write(fileOut);
        fileOut.flush();
        fileOut.close();

        wb.close();

    }

}
