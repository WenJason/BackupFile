package before_customization;

public class FileStatus implements Comparable<FileStatus>{

	private String filePath;
	
	private String fileStatus;
	
	private String fileComment;
	
	private int diffCount = -1;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}

	public String getFileComment() {
		return fileComment;
	}

	public void setFileComment(String fileComment) {
		this.fileComment = fileComment;
	}

	public int getDiffCount() {
		return diffCount;
	}

	public void setDiffCount(int diffCount) {
		this.diffCount = diffCount;
	}
	 
	@Override
	public int compareTo(FileStatus o) {
		if (o.getFileStatus().compareTo(this.fileStatus) > 0) {
			
			return 1;
		} else if (o.getFileStatus().compareTo(this.fileStatus) < 0) {
			
			return -1;
		} else {
			return this.getFilePath().compareTo(o.filePath);
		}
	}
}
