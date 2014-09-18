/*
 * FCKeditor - The text editor for internet
 * Copyright (C) 2003-2005 Frederico Caldeira Knabben
 * 
 * Licensed under the terms of the GNU Lesser General Public License:
 * 		http://www.opensource.org/licenses/lgpl-license.php
 * 
 * For further information visit:
 * 		http://www.fckeditor.net/
 * 
 * File Name: ConnectorServlet.java
 * 	Java Connector for Resource Manager class.
 * 
 * Version:  2.3
 * Modified: 2005-08-11 16:29:00
 * 
 * File Authors:
 * 		Simone Chiaretta (simo@users.sourceforge.net)
 */

package com.magic.thai.plugin.fckeditor;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Servlet to upload and browse files.<br>
 * 
 * This servlet accepts 4 commands used to retrieve and create files and folders
 * from a server directory. The allowed commands are:
 * <ul>
 * <li>GetFolders: Retrive the list of directory under the current folder
 * <li>GetFoldersAndFiles: Retrive the list of files and directory under the
 * current folder
 * <li>CreateFolder: Create a new directory under the current folder
 * <li>FileUpload: Send a new file to the server (must be sent with a POST)
 * </ul>
 * 
 * @author Simone Chiaretta (simo@users.sourceforge.net)
 */

public class FckEditorServlet extends HttpServlet {
	/**
     * 
     */
	private static final long serialVersionUID = -7925057652032174063L;

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(FckEditorServlet.class);

	private static String baseDir;

	private static boolean debug = false;

	/**
	 * Initialize the servlet.<br>
	 * Retrieve from the servlet configuration the "baseDir" which is the root
	 * of the file repository:<br>
	 * If not specified the value of "/UserFiles/" will be used.
	 * 
	 */
	@Override
	public void init() throws ServletException {
		baseDir = getInitParameter("baseDir");
		if (logger.isInfoEnabled()) {
			logger.info("init() - " + baseDir); //$NON-NLS-1$
		}
		debug = (new Boolean(getInitParameter("debug"))).booleanValue();
		if (baseDir == null) {
			baseDir = "/MchtFiles/";
		}
		String realBaseDir = getServletContext().getRealPath(baseDir);
		File baseFile = new File(realBaseDir);
		if (!baseFile.exists()) {
			baseFile.mkdir();
		}
	}

	/**
	 * Manage the Get requests (GetFolders, GetFoldersAndFiles, CreateFolder).<br>
	 * 
	 * The servlet accepts commands sent in the following format:<br>
	 * connector?Command=CommandName&Type=ResourceType&CurrentFolder=FolderPath<br>
	 * <br>
	 * It execute the command and then return the results to the client in XML
	 * format.
	 * 
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (debug) {
			if (logger.isInfoEnabled()) {
				logger.info("doGet(HttpServletRequest, HttpServletResponse) - --- BEGIN DOGET ---"); //$NON-NLS-1$
			}
		}
		String commandStr = request.getParameter("Command");
		String typeStr = request.getParameter("Type");
		String currentFolderStr = request.getParameter("CurrentFolder");

		String currentPath = baseDir + typeStr + "/"
				+ getAgencyOrSuperAgencyCode(request) + currentFolderStr;
		String currentDirPath = getServletContext().getRealPath(currentPath);

		response.setContentType("text/xml; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();

		File currentDir = new File(currentDirPath);
		// File currentDir = new File(currentDirPath + "/liantuo");
		if (!currentDir.exists()) {
			currentDir.mkdir();
		}

		Document document = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.newDocument();
		} catch (ParserConfigurationException pce) {
			logger.error("doGet(HttpServletRequest, HttpServletResponse)", pce); //$NON-NLS-1$
		}

		Node root = CreateCommonXml(document, commandStr, typeStr,
				currentFolderStr, request.getContextPath() + currentPath);

		if (debug) {
			if (logger.isInfoEnabled()) {
				logger.info("doGet(HttpServletRequest, HttpServletResponse) - Command = " + commandStr); //$NON-NLS-1$
			}
		}

		if (commandStr.equals("GetFolders")) {
			getFolders(currentDir, root, document);
		} else if (commandStr.equals("GetFoldersAndFiles")) {
			getFolders(currentDir, root, document);
			getFiles(currentDir, root, document);
		} else if (commandStr.equals("CreateFolder")) {
			// 解决乱码 同事修改frmcreatefolder.html 中的oConnector.SendCommand ....
			String newFolderStr = request.getParameter("NewFolderName");
			File newFolder = new File(currentDir, newFolderStr);
			String retValue = "110";
			if (newFolder.exists()) {
				retValue = "101";
			} else {
				try {
					boolean dirCreated = newFolder.mkdir();
					if (dirCreated) {
						retValue = "0";
					} else {
						retValue = "102";
					}
				} catch (SecurityException sex) {
					retValue = "103";
				}

			}
			setCreateFolderResponse(retValue, root, document);
		}

		document.getDocumentElement().normalize();
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();

			DOMSource source = new DOMSource(document);

			StreamResult result = new StreamResult(out);
			transformer.transform(source, result);

			if (debug) {
				StreamResult dbgResult = new StreamResult(System.out);
				transformer.transform(source, dbgResult);
				if (logger.isInfoEnabled()) {
					logger.info("doGet(HttpServletRequest, HttpServletResponse) - --- END DOGET ---"); //$NON-NLS-1$
				}
			}

		} catch (Exception ex) {
			logger.error("doGet(HttpServletRequest, HttpServletResponse)", ex); //$NON-NLS-1$
		}

		out.flush();
		out.close();
	}

	/**
	 * Manage the Post requests (FileUpload).<br>
	 * 
	 * The servlet accepts commands sent in the following format:<br>
	 * connector?Command=FileUpload&Type=ResourceType&CurrentFolder=FolderPath<br>
	 * <br>
	 * It store the file (renaming it in case a file with the same name exists)
	 * and then return an HTML file with a javascript command in it.
	 * 
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (debug) {
			if (logger.isInfoEnabled()) {
				logger.info("doPost(HttpServletRequest, HttpServletResponse) - --- BEGIN DOPOST ---"); //$NON-NLS-1$
			}
		}

		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();

		String commandStr = request.getParameter("Command");
		String typeStr = request.getParameter("Type");
		String currentFolderStr = request.getParameter("CurrentFolder");

		// String currentPath = baseDir + typeStr + currentFolderStr;
		String currentPath = baseDir + typeStr + "/"
				+ getAgencyOrSuperAgencyCode(request) + currentFolderStr;
		String currentDirPath = getServletContext().getRealPath(currentPath);

		if (debug) {
			if (logger.isInfoEnabled()) {
				logger.info("doPost(HttpServletRequest, HttpServletResponse) - " + currentDirPath); //$NON-NLS-1$
			}
		}

		String retVal = "0";
		String newName = "";

		if (!commandStr.equals("FileUpload")) {
			retVal = "203";
		} else {
			FileUpload upload = new FileUpload(new DiskFileItemFactory());
			try {
				List<?> items = upload.parseRequest(new ServletRequestContext(
						request));

				Map<String, Object> fields = new HashMap<String, Object>();

				Iterator<?> iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();
					if (item.isFormField()) {
						fields.put(item.getFieldName(), item.getString());
					} else {
						fields.put(item.getFieldName(), item);
					}
				}
				FileItem uplFile = (FileItem) fields.get("NewFile");
				String fileNameLong = uplFile.getName();
				fileNameLong = fileNameLong.replace('\\', '/');
				String[] pathParts = fileNameLong.split("/");
				String fileName = pathParts[pathParts.length - 1];

				boolean isAllowed = false;
				// boolean isValid = false;
				// 把中文转换成 拼音
				// if (fileName.matches(".*?[\u4e00-\u9fa5].*?")) {
				// fileName = CnToSpell.getFullSpell(fileName);
				// }
				String nameWithoutExt = getNameWithoutExtension(fileName);
				// else {
				// isValid = true;
				// }
				String ext = getExtension(fileName);

				File pathToSave = new File(currentDirPath, fileName);
				int counter = 1;
				while (pathToSave.exists()) {
					newName = nameWithoutExt + "(" + counter + ")" + "." + ext;
					retVal = "201";
					pathToSave = new File(currentDirPath, newName);
					counter++;
				}
				uplFile.write(pathToSave);
			} catch (Exception ex) {
				retVal = "203";
				logger.error(
						"doPost(HttpServletRequest, HttpServletResponse) - ex=" + ex, ex); //$NON-NLS-1$
			}

		}

		out.println("<script type=\"text/javascript\">");
		out.println("window.parent.frames['frmUpload'].OnUploadCompleted("
				+ retVal + ",'" + newName + "');");
		out.println("</script>");
		out.flush();
		out.close();

		if (debug) {
			if (logger.isInfoEnabled()) {
				logger.info("doPost(HttpServletRequest, HttpServletResponse) - --- END DOPOST ---"); //$NON-NLS-1$
			}
		}

	}

	private String getAgencyOrSuperAgencyCode(HttpServletRequest request) {
		return DateFormatUtils.format(new Date(), "yyMMddhhmm");
	}

	private void setCreateFolderResponse(String retValue, Node root,
			Document doc) {
		Element myEl = doc.createElement("Error");
		myEl.setAttribute("number", retValue);
		root.appendChild(myEl);
	}

	private void getFolders(File dir, Node root, Document doc) {
		Element folders = doc.createElement("Folders");
		root.appendChild(folders);
		File[] fileList = dir.listFiles();
		for (int i = 0; i < fileList.length; ++i) {
			if (fileList[i].isDirectory()) {
				Element myEl = doc.createElement("Folder");
				myEl.setAttribute("name", fileList[i].getName());
				folders.appendChild(myEl);
			}
		}
	}

	private void getFiles(File dir, Node root, Document doc) {
		Element files = doc.createElement("Files");
		root.appendChild(files);
		File[] fileList = dir.listFiles();
		for (int i = 0; i < fileList.length; ++i) {
			if (fileList[i].isFile()) {
				Element myEl = doc.createElement("File");
				myEl.setAttribute("name", fileList[i].getName());
				myEl.setAttribute("size", "" + fileList[i].length() / 1024);
				files.appendChild(myEl);
			}
		}
	}

	private Node CreateCommonXml(Document doc, String commandStr,
			String typeStr, String currentPath, String currentUrl) {

		Element root = doc.createElement("Connector");
		doc.appendChild(root);
		root.setAttribute("command", commandStr);
		root.setAttribute("resourceType", typeStr);

		Element myEl = doc.createElement("CurrentFolder");
		myEl.setAttribute("path", currentPath);
		myEl.setAttribute("url", currentUrl);
		root.appendChild(myEl);

		return root;

	}

	/*
	 * This method was fixed after Kris Barnhoorn (kurioskronic) submitted SF
	 * bug #991489
	 */
	private static String getNameWithoutExtension(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf("."));
	}

	/*
	 * This method was fixed after Kris Barnhoorn (kurioskronic) submitted SF
	 * bug #991489
	 */
	private String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	// 处理中文字符串的函数
	public String codeToString(String str) {
		String s = str;
		try {
			byte temp[] = s.getBytes("ISO-8859-1");
			s = new String(temp);
			return s;
		} catch (Exception e) {
			return s;
		}
	}

	public static void main(String[] a) {
		if (logger.isInfoEnabled()) {
			logger.info("main(String[]) - 飞".matches(".*?[u4e00-u9fa5].*?")); //$NON-NLS-1$ //$NON-NLS-2$
		}
		try {
			byte[] bytes = "f 鐨?".getBytes("ISO-8859-1");
			for (byte b : bytes) {
				if (logger.isInfoEnabled()) {
					logger.info("main(String[]) - " + b); //$NON-NLS-1$
				}
			}
			String s = new String("f 鐨?".getBytes("ISO-8859-1"), "UTF-8");
			if (logger.isInfoEnabled()) {
				logger.info("main(String[]) - " + s); //$NON-NLS-1$
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
