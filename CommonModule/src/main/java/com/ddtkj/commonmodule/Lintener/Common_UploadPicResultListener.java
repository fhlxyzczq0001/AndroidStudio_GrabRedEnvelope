package com.ddtkj.commonmodule.Lintener;



/**
 * 上传图片监听接口
 * @author Administrator
 */
public interface Common_UploadPicResultListener {
	/**
	 * 请求服务器的回调接口方法
	 * @param isFinish 请求是否完成
	 * @param isSucc 请求的结果
	 * @param currentIndex 当前完成提交的图片，在所有图片中的位置
	 * @param msg   返回状态类型  ResultCodeEnum
	 * @param url      返回图片网络连接
	 * @return
	 */
	public void onResult(boolean isFinish, boolean isSucc, int currentIndex, String msg, String url);
}
