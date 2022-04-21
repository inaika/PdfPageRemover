# PdfPageRemover
A very simple program for removing pages in PDF files made for my own convenience.

Utilizes Apache PDFBox for manipulating PDF files.

In the current (very simplistic) version any page in a .pdf file that contains a annotation (comment) "DELETEPAGE" will be removed when the program is ran.
Currently only has a minimalistic CLI, which supports passing the input file path as an argument. Perhaps a simple GUI will follow.

Run with "pdfPageRemover.jar <inputFilePath>", or "java -jar pdfPageRemover.jar <inputFilePath>" if you wish to see sout prints.
