package br.com.util.unificararquivo;

import java.io.File;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by c1278778 on 15/06/18.
 */
public class UnifImgtoPdf {

    /**
     * Unificar os arquivos no formato PDF
     *
     * @param pdfs
     * @return
     * @throws Exception
     */
    public File pdfs(List<File> pdfs) throws Exception{

        File outfile = File.createTempFile("output", ".pdf");
        concatPDFs(pdfs, outfile, true);

        if(isCalcularMB(outfile)){
            return outfile;
        } else {
            return null;
        }

    }

    /**
     * Unificar os arquivos no formato IMG
     *
     * @param imgs
     * @return
     * @throws Exception
     */
    public File imgs(List<File> imgs) throws Exception {

        File outfile = File.createTempFile("output", ".pdf");
        concatIMGtoPDFs(imgs, outfile);

        if(isCalcularMB(outfile)){
            return outfile;
        } else {
            return null;
        }
    }

    /**
     * Compactação de arquivos
     *
     * @param streamOfPDFFiles
     * @param outfile
     * @param paginate
     */
    private void concatPDFs(List<File> streamOfPDFFiles, File outfile, boolean paginate) {

        Document document = new Document();

        try {
            OutputStream outputStream = new FileOutputStream(outfile);

            try {
                List<File> pdfs = streamOfPDFFiles;
                List<PdfReader> readers = new ArrayList<PdfReader>();
                int totalPages = 0;
                Iterator<File> iteratorPDFs = pdfs.iterator();

                // Create Readers for the pdfs.
                while (iteratorPDFs.hasNext()) {
                    File pdf = iteratorPDFs.next();
                    PdfReader pdfReader = new PdfReader(new FileInputStream(pdf));
                    readers.add(pdfReader);
                    totalPages += pdfReader.getNumberOfPages();
                }

                // Create a writer for the outputstream
                PdfWriter writer = PdfWriter.getInstance(document, outputStream);
                document.open();
                BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
                PdfContentByte cb = writer.getDirectContent(); // Holds the PDF

                // data
                PdfImportedPage page;
                int currentPageNumber = 0;
                int pageOfCurrentReaderPDF = 0;
                Iterator<PdfReader> iteratorPDFReader = readers.iterator();

                // Loop through the PDF files and add to the output.
                while (iteratorPDFReader.hasNext()) {

                    PdfReader pdfReader = iteratorPDFReader.next();

                    // Create a new page in the target for each source page.
                    while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {

                        pageOfCurrentReaderPDF++;
                        currentPageNumber++;
                        page = writer.getImportedPage(pdfReader, pageOfCurrentReaderPDF);

                        if(page.getWidth() > page.getHeight()){
                            //Vertical 210x297
                            document.setPageSize(new Rectangle(page.getWidth(), page.getHeight()));
                        } else {
                            //Horizontal 297x210
                            document.setPageSize(new Rectangle(page.getHeight(), page.getHeight()));
                        }

                        document.newPage();

                        cb.addTemplate(page, 0, 0);

                        // Code for pagination.
                        if (paginate) {
                            cb.beginText();
                            cb.setFontAndSize(bf, 9);
                            cb.showTextAligned(PdfContentByte.ALIGN_CENTER,
                                    "" + currentPageNumber + " of " + totalPages,
                                    520, 5, 0);
                            cb.endText();
                        }
                    }
                    pageOfCurrentReaderPDF = 0;
                }

                outputStream.flush();
                document.close();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (document.isOpen())
                    document.close();
                try {
                    if (outputStream != null)
                        outputStream.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Compactação de arquivos
     *
     * @param files
     * @param outfile
     * @throws Exception
     */
    private void concatIMGtoPDFs(List<File> files, File outfile) throws Exception {

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(outfile));
        document.open();

        for (File f : files) {
            Image image = Image.getInstance(f.getAbsolutePath());
            image.setAbsolutePosition(0, 0);
            image.setBorderWidth(0);

            if(image.getWidth() > image.getHeight()){
                //Vertical 210x297
                document.setPageSize(new Rectangle(image.getWidth(), image.getHeight()));
            } else {
                //Horizontal 297x210
                document.setPageSize(new Rectangle(image.getHeight(), image.getHeight()));
            }

            document.newPage();
            document.add(image);
        }
        document.close();
    }

    /**
     * Ler InputStream e retorna File
     *
     * @param inputStream
     * @return
     * @throws NullPointerException
     */
    public File inputStreamToFile(InputStream inputStream) throws NullPointerException {

        OutputStream outputStream = null;

        try {

            File file = File.createTempFile("output", ".pdf");
            outputStream = new FileOutputStream(file);

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

            return file;

        } catch (IOException e) {
            e.printStackTrace();
            throw new NullPointerException("Problema no arquivo enviado.");

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    // outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * Quando a saida for:
     * true o valor e inferior a 4MB
     * false o valor e
     *
     * @param file
     * @return
     */
    private static boolean isCalcularMB(File file) {
        long MB = 1000000l;
        System.out.println((file.length() / MB));
        return ((file.length() / MB) <= 4);
    }

}
