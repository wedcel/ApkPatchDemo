package com.wedcel.apkpatchdemo.util;

/**
 * ��˵����   APK Patch������
 * @author  www
 * @date    2014-10-28
 * @version 1.0
 */
public class PatchUtils {

	/**
	 *native����
	 * ʹ��·��ΪoldApkPath��apk��·��ΪpatchPath�Ĳ��������ϳ��µ�apk�����洢��newApkPath
	 * @param oldApkPath
	 * @param oldApkPath
	 * @param newApkPath
	 * @param patchPath
	 * @return
	 */
	
	public static native int patch(String oldApkPath, String newApkPath,
			String patchPath);
}