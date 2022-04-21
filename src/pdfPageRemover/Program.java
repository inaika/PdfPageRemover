package pdfPageRemover;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.pdfbox.*;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;

/* TODO: Build a GUI that:
	- shows all pdf pages (or previews/snapshots)
	- allows checking a page for deletion
	- removes all checked pages on demand
	(*) suggests similar pages for deletion
*/

public class Program {
	private static final String DELETION_MARKER_COMMENT = "DELETEPAGE";

	public static void main(String[] args) {
		if (args == null || args.length == 0) {
			System.out.println("No arguments provided! Exiting.");
			return;
		}

		String filePath = parseArgs(args);
		String savePath = filePath.substring(0, filePath.length() - 4) + "_trimmed.pdf";

		try {
			File file = new File(args[0]);
			PDDocument document = Loader.loadPDF(file);

			// deletePages(document, pagesToDelete);
			deleteAnnotationMarkedPages(document);
			document.save(savePath);
		} catch (Exception e) {
			System.out.println("Problem with handling the pdf file :(");
			System.out.println(e);
		}
		System.out.println("Finished! Modified version saved to " + savePath);
	}

	/**
	 * Deletes any pages that contain a comment that is, when trimmed, equal to
	 * DELETION_MARKER_COMMENT
	 * 
	 * @param document
	 * @throws Exception
	 */
	private static void deleteAnnotationMarkedPages(PDDocument document) throws Exception {
		int removedPageCount = 0;
		for (int i = document.getNumberOfPages() - 1; i >= 0; --i) {
			try {
				List<PDAnnotation> annotations = document.getPage(i).getAnnotations();
				for (PDAnnotation annotation : annotations) {
					if (annotation != null && annotation.getContents() != null
							&& annotation.getContents().trim().equals(DELETION_MARKER_COMMENT)) {
						document.removePage(i);
						removedPageCount++;
						System.out.println("Removed page " + (i+1));
					}
				}
			} catch (Exception e) {
				throw e;
			}
		}
		String unitGrammar = (removedPageCount == 1) ? "page" : "pages";
		System.out.println(String.format("...Done! (%d %s removed)", removedPageCount, unitGrammar));
	}

	/**
	 * Parses arguments for input file path
	 */
	private static String parseArgs(String[] args) {
		String filePath;
		String inputPath = args[0];
		if (inputPath.matches("^[a-zA-Z]:[\\\\/].*")) { // full file path starting with ex. "C:[\/]"
			filePath = args[0];
		} else {
			if (inputPath.startsWith(".\\") || inputPath.startsWith("./"))
				inputPath = inputPath.substring(2); // hack for ".[\/]" paths
			else if (inputPath.startsWith("\\") || inputPath.startsWith("/"))
				inputPath = inputPath.substring(1); // hack for "[\/]" paths
			filePath = System.getProperty("user.dir") + inputPath;
		}
		return filePath;
	}

	/**
	 * Deletes pages based on page numbers, this might again become relevant when
	 * GUI is implemented
	 */
	private static void deletePages(PDDocument document) {
		// fill this array with 1-base page numbers if you want to utilize that and
		// uncomment funct call in main
		int[] pagesToRemove = new int[] {

		};
		Arrays.sort(pagesToRemove);

		// remove in reverse order
		System.out.print("Removing pages ");

		for (int i = pagesToRemove.length - 1; i >= 0; --i) {
			int actualPageNumber = pagesToRemove[i] - 1; // 0-based numbering
			document.removePage(actualPageNumber);
			System.out.print(actualPageNumber);

			String endPrint = (i != 0) ? ", " : "\n";
			System.out.print(endPrint);
		}
		System.out.println("...Done!");
	}

}
