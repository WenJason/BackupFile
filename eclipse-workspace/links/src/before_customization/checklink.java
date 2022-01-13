package before_customization;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.SSLHandshakeException;
import org.apache.commons.lang3.StringUtils;

public class checklink {

	public static synchronized void main(String[] args) {
		startCheck("D:\\Repository\\en-us.live\\mc-docs-pr.en-us\\articles\\vpn-gateway");

		// startCheckOthers("E:\\my_files\\mooncake\\mc-docs-pr.zh-cn\\articles");

		// startCheck(new String[] {
		// "E:\\my_files\\mooncake\\mc-docs-pr.zh-cn\\articles\\active-directory",
		// "E:\\my_files\\mooncake\\mc-docs-pr.zh-cn\\articles\\application-gateway",
		// "E:\\my_files\\mooncake\\mc-docs-pr.zh-cn\\articles\\backup",
		// "E:\\my_files\\mooncake\\mc-docs-pr.zh-cn\\articles\\batch"
		// "E:\\my_files\\mooncake\\mc-docs-pr.zh-cn\\articles\\key-vault",
		// "E:\\my_files\\mooncake\\mc-docs-pr.zh-cn\\articles\\cognitive-services",
		// "E:\\my_files\\mooncake\\mc-docs-pr.zh-cn\\articles\\multi-factor-authentication",
		// "E:\\my_files\\mooncake\\mc-docs-pr.zh-cn\\articles\\notification-hubs",
		// "E:\\my_files\\mooncake\\mc-docs-pr.zh-cn\\articles\\power-bi-workspace-collections",
		// "E:\\my_files\\mooncake\\mc-docs-pr.zh-cn\\articles\\redis-cache"
		// "E:\\my_files\\mooncake\\mc-docs-pr.zh-cn\\articles\\virtual-machine-scale-sets",
		// "E:\\my_files\\mooncake\\mc-docs-pr.zh-cn\\articles\\vpn-gateway",
		// "E:\\my_files\\mooncake\\mc-docs-pr.en-us\\includes"
		// });

	}

	private static synchronized void startCheck(String folderName) {
		List<String> fileList = new ArrayList<String>(200);

		readFileNameFromSourceFolder(folderName, fileList);

		System.out.println("Start checking...\n");

		int count = 1;
		for (String file : fileList) {
			System.out.println(count++ + ". " + file.substring(file.lastIndexOf("\\") + 1));

			checkAllLinks(file);
		}

		System.out.println("\nChecking links done....");
	}

	private static synchronized void startCheck(String[] folderNameArr) throws Exception {
		List<String> fileList = new ArrayList<String>(1000);

		for (String folderName : folderNameArr) {
			readFileNameFromSourceFolder(folderName, fileList);
		}

		System.out.println("Start checking...\n");

		int count = 1;
		for (String file : fileList) {
			System.out.println(count++ + "." + file.substring(file.lastIndexOf("\\") + 1));

			checkAllLinks(file);
		}

		System.out.println("\nChecking links done....");
	}

	private static synchronized void startCheckOthers(String folderName) throws Exception {
		System.out.println("Start checking...\n");

		List<String> fileList = new ArrayList<String>(200);

		readFileNameFromSourceFolder_others(folderName, fileList);

		int count = 1;
		for (String file : fileList) {
			System.out.println(count++ + ". " + file.substring(file.lastIndexOf("\\") + 1));

			checkAllLinks(file);
		}

		System.out.println("\nChecking links done....");
	}

	public static synchronized void readFileNameFromSourceFolder_others(String folderName, List<String> fileList) {
		File sourceFolder = new File(folderName);

		for (File file : sourceFolder.listFiles()) {
			if (file.isFile() && (file.getName().endsWith(".md") || file.getName().endsWith(".yml"))) {
				fileList.add(file.getAbsolutePath());
			}
		}
	}

	public static synchronized void readFileNameFromSourceFolder(String folderName, List<String> fileList) {
		File sourceFolder = new File(folderName);

		for (File file : sourceFolder.listFiles()) {
			if (file.isFile() && (file.getName().endsWith(".md") || file.getName().endsWith(".yml"))) {
				fileList.add(file.getAbsolutePath());
			} else if (file.isDirectory()) {
				readFileNameFromSourceFolder(file.getAbsolutePath(), fileList);
			}
		}
	}

	private static synchronized void checkAllLinks(String fileName) {
		List<String> allLinkList = getAllLinks(fileName);

		BufferedReader reader = null;
		for (String link : allLinkList) {

			String errorContent = null;

			link = link.trim();

			if (link.startsWith("www")) {
				link = "http://" + link;
			}

			int respCode = -1;

			try {
				URL url = new URL(link);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setDoInput(true);
				conn.setDoOutput(true);

				respCode = conn.getResponseCode();

				if (respCode == 404) {
					errorContent = "404 error: --> " + link;
				} else if (respCode == 502) {
					errorContent = "502 error: --> " + link;
				} else {
					StringBuilder sb = new StringBuilder();

					reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

					int lineCount = 0;
					String str = null;
					while ((str = reader.readLine()) != null) {
						lineCount++;

						sb.append(str);

						if (lineCount >= 30) {
							break;
						}
					}

					String content = sb.toString();

					if (content.toLowerCase().contains("errors_404")) {
						errorContent = "404 error: --> " + link;
					} else if (content.contains("æœªèƒ½æ‰¾åˆ°æ‚¨è¯·æ±‚çš„é¡µé�¢")) {
						errorContent = "æœªèƒ½æ‰¾åˆ°æ‚¨è¯·æ±‚çš„é¡µé�¢ï¼Œè¯·æ£€æŸ¥: --> " + link;
					} else if (content.contains("This topic has been moved")) {
						errorContent = "è¯¥ä¸»é¢˜å·²è¿�ç§»æˆ–ä¸�å�¯ç”¨ï¼Œè¯·æ£€æŸ¥: --> " + link;
					}
				}

				if (errorContent != null) {
					System.out.println("\n    " + errorContent + "\n");
				}
			} catch (SSLHandshakeException se) {

			} catch (java.net.ConnectException se) {
				System.out.println("\n    æ‰‹åŠ¨æ£€æŸ¥: --> " + link + "\n");
			} catch (FileNotFoundException fe) {
				System.out.println("\n    æ— ç”¨é“¾æŽ¥: --> " + link + "\n");
			} catch (UnknownHostException ue) {
			} catch (Exception e) {
				if (respCode != 403) {
					System.out.println("\n    æ‰‹åŠ¨æ£€æŸ¥: --> " + link + "\n");
				}
			} finally {
				try {
					if (reader != null) {
						reader.close();
						reader = null;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static synchronized List<String> getAllLinks(String fileName) {
		List<String> allLinks = new ArrayList<String>(50);

		if (fileName == null || fileName.equals("")) {
			return allLinks;
		}

		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "utf-8"));

			String line = null;
			while ((line = reader.readLine()) != null) {
				line = line.trim();

				// ignore commented links
				if (line.startsWith("<!--") && line.endsWith("-->")) {
					continue;
				}

				// ignore included files
				if (line.startsWith("[!INCLUDE [")) {
					continue;
				}

				// ignore some issues like -> [System.Timespan]::FromDays(1)) -Permission
				// Read,List
				line = line.replace("::", "");

				List<String> link1 = getLineLinks(fileName, line);

				List<String> link2 = getLineLinks_2(fileName, line);

				// check "index.md"
				List<String> link3 = getLineLinks_3(fileName, line);

				// check ".yml"
				List<String> link4 = getLineLinks_4(fileName, line);

				allLinks.addAll(link1);

				allLinks.addAll(link2);

				allLinks.addAll(link3);

				allLinks.addAll(link4);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
					reader = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return allLinks;
	}

	private static synchronized List<String> getLineLinks(String fileName, String line) {
		List<String> urlList = new ArrayList<String>(5);

		String patternStr = "(\\[[^\\[]+\\]\\([^\\(\\[]+\\))+";
		Pattern pattern = Pattern.compile(patternStr);

		Matcher matcher = pattern.matcher(line);
		while (matcher.find()) {
			String matchedString = matcher.group();

			String subStr = matchedString.substring(matchedString.lastIndexOf("]"));

			String url = subStr.substring(subStr.indexOf("(") + 1, subStr.indexOf(")")).trim();

			try {
				handleUrl(url, fileName, urlList);
			} catch (Exception e) {
				System.err.println(url);
			}

		}

		return urlList;
	}

	private static synchronized List<String> getLineLinks_2(String fileName, String line) {
		List<String> urlList = new ArrayList<String>(5);

		String patternStr = "(\\[[^\\[]+\\]):.+";
		Pattern pattern = Pattern.compile(patternStr);

		Matcher matcher = pattern.matcher(line);
		if (matcher.find()) {
			String matchedString = matcher.group();

			String subStr = matchedString.substring(matchedString.indexOf("]"));

			String url = subStr.substring(subStr.indexOf(":") + 1).trim();

			try {
				handleUrl(url, fileName, urlList);
			} catch (Exception e) {
				System.err.println(url);
			}
		}

		return urlList;
	}

	private static synchronized List<String> getLineLinks_3(String fileName, String line) {
		List<String> urlList = new ArrayList<String>(5);

		if (fileName.endsWith("index.md") && line.indexOf("<a href=\"") > -1) {
			String url = line.substring(line.indexOf("<a href=\"") + 9);

			url = url.substring(0, url.indexOf("\""));

			try {
				handleUrl(url, fileName, urlList);
			} catch (Exception e) {
				System.err.println(url);
			}
		}

		return urlList;
	}

	private static synchronized List<String> getLineLinks_4(String fileName, String line) {
		List<String> urlList = new ArrayList<String>(5);

		if (fileName.endsWith(".yml")) {
			if (line.indexOf("href:") > -1) {
				String url = line.substring(line.indexOf("href:") + "href:".length()).replace("\"", "")
						.replace("\'", "").trim();

				if (!url.endsWith(".yml")) { // ignore --> href:index.yml
					try {
						handleUrl(url, fileName, urlList);
					} catch (Exception e) {
						System.err.println(url);
					}
				}
			} else if (line.indexOf("a href=") > -1) {
				String[] links = line.split("a href=");

				for (int i = 0; i < links.length; i++) {
					if (links[i].startsWith("\"")) {
						links[i] = links[i].substring(1);
						links[i] = links[i].substring(0, links[i].indexOf("\""));

						if (!links[i].endsWith(".yml")) { // ignore --> href:index.yml
							try {
								handleUrl(links[i], fileName, urlList);
							} catch (Exception e) {
								System.err.println(links[i]);
							}
						}
					}
				}
			} else if (line.indexOf("tocHref:") > -1) {
				String url = line.substring(line.indexOf("tocHref:") + "tocHref:".length()).replace("\"", "")
						.replace("\'", "").trim();

				if (!url.endsWith(".yml")) { // ignore --> href:index.yml
					try {
						handleUrl(url, fileName, urlList);
					} catch (Exception e) {
						System.err.println(url);
					}
				}
			} else if (line.indexOf("topicHref:") > -1) {
				String url = line.substring(line.indexOf("topicHref:") + "topicHref:".length()).replace("\"", "")
						.replace("\'", "").trim();

				if (!url.endsWith(".yml")) { // ignore --> href:index.yml
					try {
						handleUrl(url, fileName, urlList);
					} catch (Exception e) {
						System.err.println(url);
					}
				}
			}
		}

		return urlList;
	}

	private static synchronized void handleUrl(String url, String fileName, List<String> urlList) throws Exception {
		if (url.startsWith("/")) {
			urlList.add("https://docs.azure.cn" + url);
		} else if (url.contains("http:") || url.contains("www.") || url.contains("https:")) {
			urlList.add(url);
		} else if (url.startsWith("../") && !url.startsWith("../articles") && url.contains(".md")
				&& !url.contains("../includes/")) { // links under articles/
			String relativePath = getRelativeFilePath(fileName);

			int numOfPointPointSlash = StringUtils.countMatches(url, "../");
			int numOfSlash = StringUtils.countMatches(relativePath, "/");

			if (numOfPointPointSlash > numOfSlash) {
				System.out.println("\n    Wrong url: --> " + url + "\n");
				return;
			}

			String newRelativePath = relativePath;
			for (int i = 0; i < numOfPointPointSlash; i++) {
				newRelativePath = newRelativePath.substring(0, newRelativePath.lastIndexOf("/"));
			}

			if (!newRelativePath.equals("") && !newRelativePath.startsWith("/")) {
				newRelativePath = "/" + newRelativePath;
			}

			urlList.add("https://docs.azure.cn/zh-cn" + newRelativePath + "/"
					+ url.replace("../", "").replace(".md", "").replace("articles/", ""));
		} else if (url.startsWith("../articles") && url.contains(".md") && !url.contains("../includes/")) { // links
																											// under
																											// includes/

			urlList.add("https://docs.azure.cn/zh-cn" + getRelativeFilePath(fileName) + "/"
					+ url.replace("../", "").replace(".md", "").replace("articles/", ""));
		} else if (url.startsWith("./") && url.contains(".md") && !url.contains("../includes/")) {

			urlList.add("https://docs.azure.cn/zh-cn" + getRelativeFilePath(fileName) + "/"
					+ url.replace("./", "").replace(".md", "").replace("articles/", ""));
		} else if (url.contains(".md") && !url.contains("../includes/")) {

			urlList.add("https://docs.azure.cn/zh-cn" + getRelativeFilePath(fileName) + "/"
					+ url.replace(".md", "").replace("articles/", ""));
		} else if (url.startsWith("#") || url.startsWith("./media") || url.startsWith("media")
				|| url.contains("../includes/") || url.startsWith("mailto:") || url.startsWith("../media")
				|| url.endsWith(".png")) {
			// ignore
		} else {
			System.out.println("\n    Wrong url: --> " + url + "\n");
		}
	}

	private static synchronized String getRelativeFilePath(String fileName) throws Exception {
		String path;

		if (fileName.indexOf("articles") > -1) {
			path = fileName.substring(fileName.indexOf("articles")).replace("\\", "/");
		} else if (fileName.indexOf("docs-ref-conceptual\\includes") > -1) {
			path = fileName.substring(fileName.indexOf("docs-ref-conceptual\\includes")).replace("\\", "/");
		} else if (fileName.indexOf("includes") > -1) {
			path = fileName.substring(fileName.indexOf("includes")).replace("\\", "/");
		} else if (fileName.indexOf("docs-ref-conceptual") > -1) {
			path = fileName.substring(fileName.indexOf("docs-ref-conceptual")).replace("\\", "/");
		} else {
			throw new Exception("wrong file root directroy");
		}

		path = path.substring(path.indexOf("/"), path.lastIndexOf("/"));

		if (fileName.indexOf("docs-ref-conceptual") > -1) {
			path = "/java" + path;
		}

		return path;
	}
}
