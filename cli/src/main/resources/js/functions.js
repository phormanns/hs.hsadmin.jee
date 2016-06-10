importClass(java.util.ArrayList);
importClass(java.util.HashMap);

function hsaParseParam(val) {
	if (val instanceof java.util.List) {
		return val;
	}
	if (val instanceof java.util.Map) {
		return val;
	}
	if (typeof val === 'object' && val.constructor === Array) { 
		return hsaParseParamArray(val); 
	} 
	if (typeof val === 'object') { 
		return hsaParseParamObject(val); 
	}
}

function hsaParseParamArray(o) {
	var lst = new ArrayList();
	var val = '';
	for (var idx=0; idx < o.length; idx++) {
		val = o[idx];
		if (typeof val === 'object' && val.constructor === Array) { 
			val = hsaParseParamArray(val); 
		}
		else if (typeof val === 'object') { 
			val = hsaParseParamObject(val); 
		};
		lst.add(val);
	};
	return lst;
}

function hsaParseParamObject(o) {
	var hsh = new HashMap();
	for (var key in o) {
		var val = o[key];
		if (typeof val === 'object' && val.constructor === Array) { 
			val = hsaParseParamArray(val); 
		}
		else if (typeof val === 'object') { 
			val = hsaParseParamObject(val); 
		}; 
		hsh.put(key, val);
	};
	return hsh;
}

function hsaToNativeJSObject(val) {
	if (val instanceof java.util.List) {
		var res = [];
		for (var i = 0; i < val.size(); i++) {
			res[i] = hsaToNativeJSObject(val.get(i));
		}
		return res;
	}
	if (val instanceof java.util.Map) {
		var res = {};
		var iter = val.keySet().iterator();
		while (iter.hasNext()) {
			var key = iter.next();
			res[key] = hsaToNativeJSObject(val.get(key));
		}
		return res;
	}
	return val;
}

function hsaModuleCall(mod, fct, json) {
	var params = new ArrayList();
    params.add(casgrantingticket.getRunAs());
    params.add(casgrantingticket.getTicket());
    if (typeof json === "undefined") {
        json = {where:{}, set:{}};
    }
    if (fct == "update" || fct == "add") {
        params.add(hsaParseParamObject(json["set"]));
    }
    if (fct == "update" || fct == "delete" || fct == "search") {
        params.add(hsaParseParamObject(json["where"]));
    }
    xmlrpcLastResult = xmlrpcclient.execute(mod + "." + fct, params);
    return hsaToNativeJSObject(xmlrpcLastResult);
}
