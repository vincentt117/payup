package src.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.cloud.vision.spi.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.protobuf.ByteString;


/**
 * Servlet implementation class OcrProcess
 */
@WebServlet("/OcrProcess/")
public class OcrProcess extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArrayList<Room> rooms;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OcrProcess() {
		super();
		// TODO Auto-generated constructor stub
	}


	public void init(){
		rooms = new ArrayList<Room>();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request1, HttpServletResponse response1) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response1.getWriter().append("Served at: ").append("");

	}
	
	private ArrayList<String> doStuff(FileItem fileItem) throws IOException{
		// Instantiates a client
		ImageAnnotatorClient vision = ImageAnnotatorClient.create();
		// The path to the image file to annotate
		//String fileName = servletContext.getResourceAsStream("WEB-INF/receipt.jpg");

		// Reads the image file into memory
		byte[] data = org.apache.commons.io.IOUtils.toByteArray(fileItem.getInputStream());
		ByteString imgBytes = ByteString.copyFrom(data);

		// Builds the image annotation request
		List<AnnotateImageRequest> requests = new ArrayList<>();
		Image img = Image.newBuilder().setContent(imgBytes).build();
		Feature feat = Feature.newBuilder().setType(Type.TEXT_DETECTION).build();
		AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
				.addFeatures(feat)
				.setImage(img)
				.build();
		requests.add(request);

		// Performs label detection on the image file
		BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
		List<AnnotateImageResponse> responses = response.getResponsesList();

		
		ArrayList<String> products = new ArrayList<String>();
		for (AnnotateImageResponse res : responses) {
			if (res.hasError()) {
				System.out.printf("Error: %s\n", res.getError().getMessage());
				return null;
			}

			for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
				annotation.getAllFields().forEach((k, v)-> {
					if(k.toString().contains("google.cloud.vision.v1.EntityAnnotation.description")){
						String[] text = v.toString().split("\n");
						for(int i = 0; i < text.length - 1;i++){
							if(text[i].startsWith("1") && text[i+1].contains(".")){
								products.add(text[i].substring(text[i].indexOf(" ") + 1, text[i].length()));
								products.add(text[i+1]);
							} else if(text[i].contains("Total")){
								products.add("Total");
								products.add(text[i+1]);
								break;
							}
						}
					}
				});
			}
		}
		return products;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		String name = request.getHeader("name");
		
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Configure a repository (to ensure a secure temp location is used)
		ServletContext servletContext = this.getServletConfig().getServletContext();
		java.io.File repository = (java.io.File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		ArrayList<String> products = new ArrayList<String>();
		try {
			List<FileItem> items = upload.parseRequest(request);
			// Process the uploaded items
			Iterator<FileItem> iter = items.iterator();
			while (iter.hasNext()) {
			    FileItem item = iter.next();

			    if (item.isFormField()) {
			    } else {
			        products.addAll(doStuff(item));
			        //RoomManager.getRoom(name);
			        UserManager.addFileItem(item, products, name);
			    }
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		
		System.out.println(products.toString());
		response.getWriter().append(products.toString());
	}

}
