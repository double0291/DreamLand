package com.dreamland.util;

public class Constants {
	/**
	 * 私有构造函数，防止被初始化
	 */
	private Constants() {
	}

	// 应用名
	public static final String APP_NAME = "DreamLand";

	// 主页
	public static enum HOME_CARD {
		VIDEO, GAME, MINE
	}

	/*
	 * 网络请求命令
	 */
	public static enum HttpCmd {
		NULL, TEST;
	}

}
