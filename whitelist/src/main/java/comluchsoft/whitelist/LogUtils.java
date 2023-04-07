/*
 * File Name: LogUtils.java 
 * History:
 * Created by mwqi on 2014-4-4
 */
package comluchsoft.whitelist;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 日志输出控制类 (Description)
 * @author mwqi
 */
public class LogUtils {
	/** 日志输出级别NONE */
	public static final int LEVEL_NONE = 0;
	/** 日志输出级别V */
	public static final int LEVEL_VERBOSE = 1;
	/** 日志输出级别D */
	public static final int LEVEL_DEBUG = 2;
	/** 日志输出级别I */
	public static final int LEVEL_INFO = 3;
	/** 日志输出级别W */
	public static final int LEVEL_WARN = 4;
	/** 日志输出级别E */
	public static final int LEVEL_ERROR = 5;

	/** 日志输出时的TAG */
	private static String mTag = "com.app.nake";
	/** 是否允许输出log */
	private static int mDebuggable = LEVEL_ERROR;

	/** 用于记时的变量 */
	private static long mTimestamp = 0;
	/** 写文件的锁对象 */
	private static final Object mLogLock = new Object();

	public static boolean isDebug = false;// 是否需要打印bug，可以在application的onCreate函数里面初始化
	private static final String TAG = "lyy";
	public static final boolean netDebug = false;

	/**
	 * Drawing toolbox
	 */
	private static final char TOP_LEFT_CORNER = '╔';
	private static final char BOTTOM_LEFT_CORNER = '╚';
	private static final char MIDDLE_CORNER = '╟';
	private static final char HORIZONTAL_DOUBLE_LINE = '║';
	private static final String DOUBLE_DIVIDER = "════════════════════════════════════════════";
	private static final String SINGLE_DIVIDER = "────────────────────────────────────────────";
	private static final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
	private static final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
	private static final String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER;
	private static final char I = 'I', W = 'W', D = 'D', E = 'E', V = 'V', A = 'A', M = 'M';

	static String LINE_SEPARATOR = System.getProperty("line.separator"); //等价于"\n\r"，唯一的作用是能装逼
	static int JSON_INDENT = 4;

	static String className;//类名
	static String methodName;//方法名
	static int lineNumber;//行数

	private LogUtils(){
        /* Protect from instantiations */
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	public interface LogCallback{
		void log(String text);
	}
	private static LogCallback logCallback;
	public static void setLogCallback(LogCallback callback){
		logCallback = callback;
	}

	public static void dXlog(String message){
		if(netDebug) {
			sendMsg(className + " : " + message);
		}
		getMethodNames(new Throwable().getStackTrace());
		String msglog  = createLog(message);
		if (logCallback!= null){
			logCallback.log(msglog);
		}else {
			Log.d(className, msglog);
		}
	}

	public static void setDebugState(boolean isDebug) {
		LogUtils.isDebug = isDebug;
	}

	/**
	 * 打印MAp
	 */
	public static void m(Map map) {
		Set set = map.entrySet();
		if (set.size() < 1) {
			//printLog(D, "[]");
			return;
		}

		int i = 0;
		String[] s = new String[set.size()];
		for (Object aSet : set) {
			Map.Entry entry = (Map.Entry) aSet;
			s[i] = entry.getKey() + " = " + entry.getValue() + ",\n";
			i++;
		}
		//printLog(V, s);
	}

	/**
	 * 打印JSON
	 *
	 * @param jsonStr
	 */
	public static void j(String jsonStr) {
		if (isDebug) {
			String message;
			try {
				if (jsonStr.startsWith("{")) {
					JSONObject jsonObject = new JSONObject(jsonStr);
					message = jsonObject.toString(JSON_INDENT); //这个是核心方法
				} else if (jsonStr.startsWith("[")) {
					JSONArray jsonArray = new JSONArray(jsonStr);
					message = jsonArray.toString(JSON_INDENT);
				} else {
					message = jsonStr;
				}
			} catch (JSONException e) {
				message = jsonStr;
			}

			message = LINE_SEPARATOR + message;
			String[] lines = message.split(LINE_SEPARATOR);
			StringBuilder sb = new StringBuilder();
			//printLog(D, lines);
		}
	}


	public static final boolean isDebuggable() {
		if(isDebug) {
			return true;
		} else {
			return BuildConfig.DEBUG;
		}
	}

	private static String createLog( String log ) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(methodName);
		buffer.append("(").append(className).append(":").append(lineNumber).append(")");
		buffer.append(log);
		return buffer.toString();
	}

	private static void getMethodNames(StackTraceElement[] sElements){
		className = sElements[1].getFileName();
		methodName = sElements[1].getMethodName();
		lineNumber = sElements[1].getLineNumber();
	}

	public static void sendMsg(String msg) {
		if(msg != null) {
			msg += "\n"; //消息结尾符
			//socketClientManage.send(sendDataString.getBytes(), sendDataString.getBytes().length);
			//发送消息主语句
			//new Thread(new CanChatUdpSend(msg, "192.168.0.93", 5920)).start();
			//WebsocketClient.getInstance().sendMessage(msg);
			//AsyncLogger.Logging("file", msg);
		}
	}
	/***********************************************************************************/
	public static void e(String message){
		if(netDebug) {
			sendMsg(className + " : " + message);
		}

		if (!isDebuggable()) {
			//return;
		}

		// Throwable instance must be created before any methods
		getMethodNames(new Throwable().getStackTrace());
		String msglog  = createLog(message);
		Log.e(className, msglog);
	}

	public static void i(String message){
		if(netDebug) {
			sendMsg(className + " : " + message);
		}

		if (!isDebuggable()) {
			return;
		}

		getMethodNames(new Throwable().getStackTrace());
		String msglog  = createLog(message);
		Log.i(className, msglog);
	}

	public static void d(String message){
		if (!isDebuggable()) {
			return;
		}

		getMethodNames(new Throwable().getStackTrace());
		String msglog  = createLog(message);

		if(netDebug) {
			sendMsg(className + " : " + msglog);
		}
		Log.d(className, msglog);
	}

	public static void v(String message){
		if(netDebug) {
			sendMsg(className + " : " + message);
		}

		if (!isDebuggable()) {
			return;
		}

		getMethodNames(new Throwable().getStackTrace());
		String msglog  = createLog(message);
		Log.v(className, msglog);
	}

	public static void w(String message){
		if(netDebug) {
			sendMsg(className + " : " + message);
		}

		if (!isDebuggable()) {
			return;
		}

		getMethodNames(new Throwable().getStackTrace());
		String msglog  = createLog(message);
		Log.w(className, msglog);
	}

	/**** 一个特殊的日志类型 直接写文件，没有条件判断****/
	public static void f(String message) {
		if(netDebug) {
			sendMsg(className + " : " + message);
		}

		getMethodNames(new Throwable().getStackTrace());
		String msglog  = createLog(message);
		Log.d(className, msglog);
	}

	/*********************************************************************************************/
	public static void e(String TAG, String message){
		if(netDebug) {
			sendMsg(className + " : " + message);
		}

		if (!isDebuggable()) {
			//return;
		}

		// Throwable instance must be created before any methods
		getMethodNames(new Throwable().getStackTrace());
		String msglog  = createLog(message);
		Log.e(className, msglog);
	}


	public static void i(String TAG, String message){
		if(netDebug) {
			sendMsg(className + " : " + message);
		}

		if (!isDebuggable()) {
			return;
		}

		getMethodNames(new Throwable().getStackTrace());
		String msglog  = createLog(message);
		Log.i(className, msglog);
	}

	public static void d(String TAG, String message){
		if(netDebug) {
			sendMsg(className + " : " + message);
		}

		if (!isDebuggable()) {
			return;
		}

		getMethodNames(new Throwable().getStackTrace());
		String msglog  = createLog(message);
		Log.d(className, msglog);
	}

	public static void v(String TAG, String message){
		if(netDebug) {
			sendMsg(className + " : " + message);
		}

		if (!isDebuggable()) {
			return;
		}

		getMethodNames(new Throwable().getStackTrace());
		String msglog  = createLog(message);
		Log.v(className, msglog);
	}

	public static void w(String TAG, String message){
		if(netDebug) {
			sendMsg(className + " : " + message);
		}

		if (!isDebuggable()) {
			return;
		}

		getMethodNames(new Throwable().getStackTrace());
		String msglog  = createLog(message);
		Log.w(className, msglog);
	}

	/**** 一个特殊的日志类型 直接写文件，没有条件判断****/
	public static void f(String TAG, String message) {
		if(netDebug) {
			sendMsg(className + " : " + message);
		}

		getMethodNames(new Throwable().getStackTrace());
		String msglog  = createLog(message);
		Log.d(TAG, msglog);
	}

	/**-------------------------------------------------------------------------------------------**/
	/*
        /** 以级别为 d 的形式输出LOG */
	public static void vv(String msg) {
		if (mDebuggable >= LEVEL_VERBOSE) {
			Log.v(mTag, msg);
		}
	}

	/** 以级别为 d 的形式输出LOG */
	public static void vd(String msg) {
		if (mDebuggable >= LEVEL_DEBUG) {
			Log.d(mTag, msg);
		}
	}

	/** 以级别为 i 的形式输出LOG */
	public static void vi(String msg) {
		if (mDebuggable >= LEVEL_INFO) {
			Log.i(mTag, msg);
		}
	}

	/** 以级别为 w 的形式输出LOG */
	public static void vw(String msg) {
		if (mDebuggable >= LEVEL_WARN) {
			Log.w(mTag, msg);
		}
	}

	/** 以级别为 w 的形式输出Throwable */
	public static void w(Throwable tr) {
		if (mDebuggable >= LEVEL_WARN) {
			Log.w(mTag, "", tr);
		}
	}

	/** 以级别为 w 的形式输出LOG信息和Throwable */
	public static void w(String msg, Throwable tr) {
		if (mDebuggable >= LEVEL_WARN && null != msg) {
			Log.w(mTag, msg, tr);
		}
	}

	/** 以级别为 e 的形式输出LOG */
	public static void ve(String msg) {
		if (mDebuggable >= LEVEL_ERROR) {
			Log.e(mTag, msg);
		}
	}

	/** 以级别为 e 的形式输出Throwable */
	public static void e(Throwable tr) {
		if (mDebuggable >= LEVEL_ERROR) {
			Log.e(mTag, "", tr);
		}
	}

	/** 以级别为 e 的形式输出LOG信息和Throwable */
	public static void e(String msg, Throwable tr) {
		if (mDebuggable >= LEVEL_ERROR && null != msg) {
			Log.e(mTag, msg, tr);
		}
	}

	/**
	 * 把Log存储到文件中
	 * @param log  需要存储的日志
	 * @param path 存储路径
	 */
	public static void log2File(String log, String path) {
		log2File(log, path, true);
	}

	public static void log2File(String log, String path, boolean append) {
		synchronized (mLogLock) {
			//FileUtils.writeFile(log + "\r\n", path, append);
		}
	}

	/**
	 * 以级别为 e 的形式输出msg信息,附带时间戳，用于输出一个时间段起始点
	 * @param msg 需要输出的msg
	 */
	public static void msgStartTime(String msg) {
		mTimestamp = System.currentTimeMillis();
		if (!TextUtils.isEmpty(msg)) {
			e("[Started：" + mTimestamp + "]" + msg);
		}
	}

	/** 以级别为 e 的形式输出msg信息,附带时间戳，用于输出一个时间段结束点* @param msg 需要输出的msg */
	public static void elapsed(String msg) {
		long currentTime = System.currentTimeMillis();
		long elapsedTime = currentTime - mTimestamp;
		mTimestamp = currentTime;
		e("[Elapsed：" + elapsedTime + "]" + msg);
	}

	public static <T> void printList(List<T> list) {
		if (list == null || list.size() < 1) {
			return;
		}
		int size = list.size();
		i("---begin---");
		for (int i = 0; i < size; i++) {
			i(i + ":" + list.get(i).toString());
		}
		i("---end---");
	}

	public static <T> void printArray(T[] array) {
		if (array == null || array.length < 1) {
			return;
		}
		int length = array.length;
		i("---begin---");
		for (int i = 0; i < length; i++) {
			i(i + ":" + array[i].toString());
		}
		i("---end---");
	}


	/**
	 * 分段打印出较长log文本
	 * @param log        原log文本
	 * @param showCount  规定每段显示的长度（最好不要超过eclipse限制长度）
	 */
	public static void showLogCompletion(String log,int showCount){
		if(log.length() >showCount){
			String show = log.substring(0, showCount);
			Log.i("TAG", show+"");
			if((log.length() - showCount) > showCount){//剩下的文本还是大于规定长度
				String partLog = log.substring(showCount,log.length());
				showLogCompletion(partLog, showCount);
			} else{
				String surplusLog = log.substring(showCount, log.length());
				Log.i("TAG", surplusLog+"");
			}
		} else{
			Log.i("TAG", log+"");
		}
	}
}
