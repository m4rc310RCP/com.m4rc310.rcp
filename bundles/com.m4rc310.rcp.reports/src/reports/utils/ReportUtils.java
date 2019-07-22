/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reports.utils;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;

//import com.m4rc310.component.log.Log;
//import com.m4rc310.component.utils.MD5Utils;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PDFRenderer;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.query.JRXPathQueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Marcelo
 */
public class ReportUtils {

    protected static final Map<String, Object> params = new HashMap<String, Object>();

    static {
    	
//        params.put(JRXPathQueryExecuterFactory., "dd/MM/yyyy");
        params.put(JRXPathQueryExecuterFactory.XML_DATE_PATTERN, "dd/MM/yyyy");
        params.put(JRXPathQueryExecuterFactory.XML_NUMBER_PATTERN, "#,##0.##");
        params.put(JRXPathQueryExecuterFactory.XML_LOCALE, Locale.getDefault());
        params.put(JRParameter.REPORT_LOCALE, Locale.getDefault());
    }

    public static void compileAllReports(String path) {
        ArrayList<File> files = new ArrayList<>();
        File f = new File(path);
        if (!f.exists()) {
            return;
        }

        listf(path, files);

        files.stream().filter((file) -> (file.getName().endsWith(".jrxml"))).forEachOrdered((file) -> {
            compile(file.getAbsolutePath());
        });
    }

    public static JasperReport getReport(String name) {
        try {
            name = name.replace(".jasper", "");
            name = String.format("reportsJasper/%s.jasper", name);

            InputStream is = ReportUtils.class.getClassLoader().getResourceAsStream(name);

            return (JasperReport) JRLoader.loadObject(is);
        } catch (JRException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static void viewerReportList(String report, List<Object> values) throws JRException {
        viewerReport(report, params, values);
    }

    public static void viewerReport(String report, Object values) throws JRException {
        List<Object> list = new ArrayList<Object>();
        list.add(values);
        viewerReportList(report, list);
    }

    public static void printReport(String report, List<?> values) throws JRException {
        printReport(report, params, values);
    }

    public static void printReport(JasperReport report, Map<String, Object> params, List<?> values) throws JRException {
        JasperPrint jPrint = JasperFillManager.fillReport(report, params, new JRBeanCollectionDataSource(values));
        printReportToPrinter(jPrint);
    }

    public static void printReport(String report, Map<String, Object> params, List<?> values) throws JRException {
        JasperReport jr = ReportUtils.getReport(report);
        printReport(jr, params, values);
    }

//    private class ImagePrintable implements Printable {
//
//        private final BufferedImage image;
//
//        public ImagePrintable(BufferedImage image) {
//            this.image = image;
//        }
//
//        @Override
//        public int print(Graphics graphics, PageFormat page, int pageIndex) throws PrinterException {
//            if (pageIndex != 0) {
//                return NO_SUCH_PAGE;
//            }
//            graphics.drawImage(image, 20, 20, (int) page.getWidth(), (int) page.getHeight(), null);
//            return PAGE_EXISTS;
//        }
//    }

//    private void printReportToPrinter2(JasperPrint jp) throws JRException {
//        JasperReportsContext jrc = DefaultJasperReportsContext.getInstance();
//        JasperPrintManager manager = JasperPrintManager.getInstance(jrc);
//        BufferedImage image = (BufferedImage) manager.printToImage(jp, 0, 3f);
//
//        PrinterJob printJob = PrinterJob.getPrinterJob();
//        Paper paper = printJob.defaultPage().getPaper();
//        paper.setImageableArea(0, 0, jp.getPageWidth(), jp.getPageHeight());
//
//        ImagePrintable ip = new ImagePrintable(image);
//        printJob.setPrintable(ip);
//
//        try {
//            ImageIO.write(image, "png", new File("img.png"));
//            printJob.print();
//
//        } catch (PrinterException | IOException e) {
//        }
//    }
    
    public static List<PrintService> getImpressoras(){
        return Arrays.asList(PrinterJob.lookupPrintServices());
    }

    public static void printReportToPrinter(JasperPrint jp) throws JRException {

//        PrinterJob printJob = PrinterJob.getPrinterJob();
//        Paper paper = printJob.defaultPage().getPaper();
//        paper.setImageableArea(20, 20, jp.getPageWidth(), jp.getPageHeight());

        JasperPrintManager.printReport(jp, false);

//        JasperExportManager.exportReportToPdfFile(jp, "out.pdf");
//        PrinterJob job = PrinterJob.getPrinterJob();
//        /* Create an array of PrintServices */
//        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
//        int selectedService = 0;
//        try {
//            job.setPrintService(services[selectedService]);
//
//            PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
//            printRequestAttributeSet.add(MediaSizeName.ISO_A4);
//
//            PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
//            MediaSizeName mediaSizeName = MediaSize.findMedia(4, 4, MediaPrintableArea.INCH);
////            
////             PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
//
//            printRequestAttributeSet.add(mediaSizeName);
//            printRequestAttributeSet.add(new Copies(1));
//
//            JRPrintServiceExporter exporter = new JRPrintServiceExporter();
//            exporter.setExporterInput(new SimpleExporterInput(jp));
//            SimplePrintServiceExporterConfiguration configuration = new SimplePrintServiceExporterConfiguration();
//
//            configuration.setPrintService(job.getPrintService());
//
//            configuration.setPrintRequestAttributeSet(printRequestAttributeSet);
//            configuration.setPrintServiceAttributeSet(printServiceAttributeSet);
//
//            configuration.setDisplayPageDialog(false);
//            configuration.setDisplayPrintDialog(true);
//            exporter.setConfiguration(configuration);
//            exporter.exportReport();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        PrinterJob printJob = PrinterJob.getPrinterJob();
//        PageFormat format = printJob.defaultPage();
//
//        Paper paper = new Paper();
//        paper.setImageableArea(0, 0, PaperSizeEnum.A4.getWidth(), PaperSizeEnum.A4.getHeight());
//        format.setPaper(paper);
//        try {
//            byte[] pdf = JasperExportManager.exportReportToPdf(jp);
//            doPrint(pdf, false);
//        } catch (JRException ex) {
//            Logger.getLogger(ReportUtils.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public static JasperPrint createJasperPrint(String report, Map<String, Object> params, List<?> values) throws JRException {
        JasperReport jr = ReportUtils.getReport(report);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jr, params, new JRBeanCollectionDataSource(values));
        return jasperPrint;
    }

//    public static void toPDF(String report, Map params, List values) throws JRException {
//        try {
//            JasperReport jr = ReportUtils.getReport(report);
//            JasperPrint jasperPrint = JasperFillManager.fillReport(jr, params, new JRBeanCollectionDataSource(values));
//
//            JasperViewer jrviewer = new JasperViewer(jasperPrint, false);
//            jrviewer.setVisible(true);
//        } catch (JRException e) {
//            Log.err(e);
//        }
//    }
    public static void viewerReport(JasperReport report, Map<String, Object> params, List<Object> values) throws JRException {
        try {
//            JDialog viewer = new JDialog();
//            viewer.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//            viewer.setModal(true);

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, new JRBeanCollectionDataSource(values));
            JasperViewer jrViewer = new JasperViewer(jasperPrint, false);
            
            jrViewer.setVisible(true);
//            viewer.getContentPane().add(jrViewer.getContentPane());
//            viewer.setSize(jrViewer.getWidth(), jrViewer.getHeight());
//            viewer.pack();
//            viewer.setLocationRelativeTo(null);
////            
//            viewer.setVisible(true);

//            JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, new JRBeanCollectionDataSource(values));
//            JasperViewer jrviewer = new JasperViewer(jasperPrint, false);
//            jrviewer.setVisible(true);
        } catch (JRException e) {
        	e.printStackTrace();
        }
    }

    public static void viewerReport(String report, Map<String, Object> params, List<Object> values) throws JRException {
        try {
            JasperReport jr = ReportUtils.getReport(report);
            viewerReport(jr, params, values);
        } catch (JRException e) {
        	e.printStackTrace();
        }
    }
    
    
    public static void compileReports(String pluginId, String dirName) {
    	try {
    		URL bundle = Platform.getBundle(pluginId).getResource(dirName);
    		compileReports(FileLocator.toFileURL(bundle).getFile());
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
    }
    

    public static void compileReports(String path) {

        ArrayList<File> files = new ArrayList<>();
        File f = new File(path);

        if (!f.exists()) {
            return;
        }

        listf(f.getAbsolutePath(), files);

        Preferences prefs = Preferences.userRoot().node("Reports");
        
        files.stream().forEach((file) -> {
            try {
                String sPath = file.getName();
                String md5 = ReportMD5Utils.getHashMD5(file);

                if (!prefs.get(sPath, "").equals(md5)) {
                    compile(file.getAbsolutePath());
                    prefs.put(sPath, md5);
                    prefs.flush();
                }
            } catch (BackingStoreException e) {
            	e.printStackTrace();
            }
        });

    }

    public static void compile(String path) {
        try {
            File file = new File(path);
            
            URL bundle = Platform.getBundle("com.m4rc310.rcp.reports").getResource("reportsJasper");
            String dest = String.format("%s/%s", FileLocator.toFileURL(bundle).getFile(), file.getName().replace(".jrxml", ".jasper"));
            
            System.out.println("compile " + dest);
            
            
            JasperDesign design = JRXmlLoader.load(path);
            
            
            
            
            JasperCompileManager.compileReportToFile(design, dest);
//            file = new File(dest);
//            String newPath = ReportUtils.class.getResource("/reports").getPath();
//            newPath = String.format("%s/%s", newPath, file.getName());
//            Files.copy(file.toPath(), new FileOutputStream(newPath));
        } catch (IOException | JRException e) {
        	e.printStackTrace();
        }
    }

    public static void listf(String directoryName, ArrayList<File> files) {
        File directory = new File(directoryName);
        File[] fList = directory.listFiles();
        for (File file : fList) {
        	
        	System.out.println(file);
        	
            if (file.isDirectory()) {
                listf(file.getAbsolutePath(), files);
            } else if (file.getAbsolutePath().endsWith(".jasper")) {
            } else if (file.getAbsolutePath().endsWith(".jrxml")) {
                files.add(file);
            }
        }
    }

    //Método para imprimir o pdf  
    public static void doPrint(final byte[] pdfData, final boolean showDialog) {
        new Thread(() -> {
            try {
                ByteBuffer buffer = ByteBuffer.wrap(pdfData);
                final PDFFile pdfFile = new PDFFile(buffer);
                MyPDFPrintPage pages = new MyPDFPrintPage(pdfFile);
//
                PrinterJob printJob = PrinterJob.getPrinterJob();
//
                HashPrintRequestAttributeSet attr = new HashPrintRequestAttributeSet();
//                attr.add(new MediaPrintableArea(0f, 0f, PaperSizeEnum.A4.getWidth(), PaperSizeEnum.A4.getHeight(), MediaPrintableArea.MM));
//
//                PageFormat pageFormat = PrinterJob.getPrinterJob().defaultPage();
                PageFormat pageFormat = printJob.getPageFormat(attr);
                Paper paper = pageFormat.getPaper();
//
                Book book = new Book();
                book.append(pages, pageFormat, pdfFile.getNumPages());
                printJob.setPageable(book);
//
////                System.out.println(pdfFile.getPage(0).getWidth());
//
//                if (pdfFile.getNumPages() > 0) {
////                    paper.setSize(pdfFile.getPage(0).getWidth(), pdfFile.getPage(0).getHeight());
////                        paper.setSize(PaperSizeEnum.A4.getWidth(), PaperSizeEnum.A4.getHeight());
////                        paper.setImageableArea(0, 0, PaperSizeEnum.A4.getWidth(), PaperSizeEnum.A4.getHeight());
////                        int margin = 10;
////                        paper.setImageableArea(margin, margin, paper.getWidth() - (margin * 2), paper.getHeight() - (margin * 2));
//                }
                pageFormat.setPaper(paper);
//
                PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
                printJob.setPrintService(printService);
//
                if (showDialog) {
                    if (printJob.printDialog()) {
                        printJob.print();
                    }
                } else {
                    printJob.print();
                }
            } catch (HeadlessException | PrinterException | IOException | NullPointerException e) {
                e.printStackTrace(System.err);
            }
        }).start();
    }

    //Classe auxiliar para renderizar as páginas do pdf  
    static class MyPDFPrintPage implements Printable {

        private final PDFFile file;

        MyPDFPrintPage(PDFFile file) {
            this.file = file;
        }

        @Override
        public int print(Graphics g, PageFormat format, int index) throws PrinterException {
            int pagenum = index + 1;
            // don't bother if the page number is out of range.  
            if ((pagenum >= 1) && (pagenum <= file.getNumPages())) {
                // fit the PDFPage into the printing area  
                Graphics2D g2 = (Graphics2D) g;

                try {
                    PDFPage page = file.getPage(pagenum);
//                    BufferedImage bImage = new BufferedImage((int) page.getWidth(), (int) page.getHeight(),
//                            BufferedImage.TYPE_INT_RGB);

                    double pwidth = format.getImageableWidth();
                    double pheight = format.getImageableHeight();

                    double aspect = page.getAspectRatio();
                    double paperaspect = pwidth / pheight;
//                    
                    Rectangle imgbounds;
                    if (aspect > paperaspect) {
                        // paper is too tall / pdfpage is too wide  
                        int height = (int) (pwidth / aspect);

                        imgbounds = new Rectangle((int) format.getImageableX(), (int) (format.getImageableY() + ((pheight - height) / 2)), (int) pwidth, height);
                    } else {
                        // paper is too wide / pdfpage is too tall  
                        int width = (int) (pheight * aspect);
                        System.out.println(width);
                        imgbounds = new Rectangle((int) (format.getImageableX() + ((pwidth - width) / 2)), (int) format.getImageableY(), width, (int) pheight);
                    }

                    PDFRenderer pgs = new PDFRenderer(page, g2, imgbounds, null, null);
                    page.waitForFinish();
                    pgs.run();

//                    g2.drawImage(bImage,
//                            null, 0, 0);
//                    
//                    ImageIO.write(bImage,
//                            "JPEG", new File("img.jpg"));
                } catch (InterruptedException e) {
                }

//                try {
//                    PDFPage page = file.getPage(pagenum);
//                    double pwidth = PaperSizeEnum.A4.getWidth();
//                    double pheight = PaperSizeEnum.A4.getHeight();
//
//                    double aspect = page.getAspectRatio();
//                    double paperaspect = pwidth / pheight;
//
//
//
//                    // render the page  
//                } catch (Exception ie) {
//                    ie.printStackTrace();
//                }
                return PAGE_EXISTS;
            } else {
                return NO_SUCH_PAGE;
            }
        }
    }

}
