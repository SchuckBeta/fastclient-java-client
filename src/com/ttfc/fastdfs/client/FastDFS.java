package com.ttfc.fastdfs.client;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.csource.common.*;
import org.csource.fastdfs.*;

public class FastDFS {

	private String fastClientConf = "";
	private StorageClient1 fastClient;
	private TrackerServer trackerServer = null;

	public String getFastClientConf() {
		return fastClientConf;
	}

	public void setFastClientConf(String fastClientConf) {
		this.fastClientConf = fastClientConf;
		this.setTracker();
	}

	private void setTracker() {

		try {
			ClientGlobal.init(this.fastClientConf);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.println("network_timeout=" + ClientGlobal.g_network_timeout + "ms");
		System.out.println("charset=" + ClientGlobal.g_charset);

		TrackerClient tracker = new TrackerClient();
		
		try {
			trackerServer = tracker.getConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StorageServer storageServer = null;
		this.fastClient = new StorageClient1(trackerServer, storageServer);
	}

	public String upload(String file) {
		NameValuePair[] metaList = new NameValuePair[1];
		metaList[0] = new NameValuePair("fileName", file);
		String fileId = null;
		try {
			fileId = this.fastClient.upload_file1(file, null, metaList);
			trackerServer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("upload success. file id is: " + fileId);
		return fileId;
	}

	public int download(String fileId) {
		int i = 0;
		byte[] result = null;
		while (i++ < 10) {

			try {
				result = this.fastClient.download_file1(fileId);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(i + ", download result is: " + result.length);
		}

		return result.length;
	}

}
