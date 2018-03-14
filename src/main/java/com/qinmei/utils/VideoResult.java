package com.qinmei.utils;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 自定义响应
 * 
 * <p>Title: FaceResult</p>
 * <p>Description: </p>
 * <p>Company: www.qinmei.com</p> 
 * @author 史恒飞 ，tel 18516417728
 * @version 1.0 ，@2018年1月10日上午9:38:55
 */
public class VideoResult {

	/*{200
	 * "52c2678b18"
	 * 
	 * 400错误请求
		  "ErrorType": "URL_UNREACHABLE",
		  "Message": "Url 'http://mcf44p.natappfree.cc/upload/fuqin.mp4' is unreachable."
		}*/
	
	
    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    public static VideoResult build(Integer status, String msg, Object data) {
        return new VideoResult(status, msg, data);
    }

    public static VideoResult ok(Object data) {
        return new VideoResult(data);
    }

    public static VideoResult ok() {
        return new VideoResult(null);
    }

    public VideoResult() {

    }

    public static VideoResult build(Integer status, String msg) {
        return new VideoResult(status, msg, null);
    }

    public VideoResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public VideoResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

//    public Boolean isOK() {
//        return this.status == 200;
//    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 将json结果集转化为VideoResult对象
     * 
     * @param jsonData json数据
     * @param clazz FaceResult中的object类型
     * @return
     */
    public static VideoResult formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, VideoResult.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 没有object对象的转化
     * 
     * @param json
     * @return
     */
    public static VideoResult format(String json) {
        try {
            return MAPPER.readValue(json, VideoResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Object是集合转化
     * 
     * @param jsonData json数据
     * @param clazz 集合中的类型
     * @return
     */
    public static VideoResult formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

}
