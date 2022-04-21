# PdfPageRemover
A very simple program for removing pages in PDF files made for my own convenience.

In the current version any page in a .pdf file that contains an annotation (comment) "DELETEPAGE" will be removed when the program is ran.
Currently only has a minimalistic CLI, which supports passing the input file path as an argument. Perhaps a simple GUI will follow.

## Dependencies
Apache PDFBox (3.0.0-alpha2) and Apache Commons Logging (1.2).

## Usage
Grab the jar from \release\ and run from command line with "pdfPageRemover.jar inputFilePath", or "java -jar pdfPageRemover.jar inputFilePath" if you wish to see status prints.
