package bc.blockchain.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import bc.blockchain.common.request.Request;

public class RequestUtil {

	public static Request create(String string) {
		Request request=JSON.parseObject(string, new TypeReference<Request>() {});;
		return request;
	}

}
