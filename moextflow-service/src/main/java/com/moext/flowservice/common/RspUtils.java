package com.moext.flowservice.common;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * 响应对象工具类
 * @author PengPeng
 */
public class RspUtils {
	
	public static <T> BaseResponse<T> success(T data) {
		return new BaseResponse.Builder<T>(ReturnCodeConstant.NORMAL_SUCCESS).setData(data).setMessage("success").build();
	}

	public static <T> BaseResponse<T> success() {
		return new BaseResponse.Builder<T>(ReturnCodeConstant.NORMAL_SUCCESS).setData(null).setMessage("success").build();
	}

	public static <T> BaseResponse<T> error(String msg) {
		return new BaseResponse.Builder<T>(ReturnCodeConstant.NORMAL_ERROR).setData(null).setMessage(msg).build();
	}

	public static String errorStr(String msg) {
		JSONObject json = new JSONObject();
		json.put(WebConstant.RETURN_CODE, ReturnCodeConstant.NORMAL_ERROR);
		json.put(WebConstant.MESSAGE, msg);
		return json.toJSONString();
	}

	public static String successStr(JSONObject data) {
		JSONObject json = new JSONObject();
		json.put("data", data);
		json.put(WebConstant.RETURN_CODE, ReturnCodeConstant.NORMAL_SUCCESS);
		json.put(WebConstant.MESSAGE, "success");
		return json.toJSONString();
	}

	public static String needLoginStr() {
		JSONObject json = new JSONObject();
		json.put(WebConstant.RETURN_CODE, ReturnCodeConstant.NEED_LOGIN);
		json.put(WebConstant.MESSAGE, "need login");
		return json.toJSONString();
	}
	
	public static <T> String validate(T bean, Class<T> clazz) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(bean);
		if(constraintViolations.size() != 0) {
			StringBuffer error = new StringBuffer();
			for (ConstraintViolation<T> constraintViolation : constraintViolations) {
				error.append(constraintViolation.getMessage());
			}
			return error.toString();
		}
		return null;
	}
}
