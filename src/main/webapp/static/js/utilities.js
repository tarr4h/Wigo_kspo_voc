if (!window["Utilities"]) {

    var _ERROR_NEED_LOGIN = 462;
    var _ERROR_HAS_NO_RIGHT = 463;
    var _ERROR_BAN_LOGIN = 464;
    var _ERROR_KEY_DUPLICATE = 486;
    var _ERROR_USER_ERROR = 487;
    var _ERROR_UNKNOWN_ERROR = 488;

    window["custoError"] = function(element, msg) {
        this.element = element;
        this.msg = msg;
    };

    if (!console)
        console = {
            log : function(msg) {
            }
        };
    window["Utilities"] = {
        _NULL_RETURN : true,
        browserType : null,
        browserMode : null,
        uniCodeByte : 3,
        dialogs : [],
        getReadableFileSizeString : function(fileSizeInBytes) {

            if (!fileSizeInBytes)
                return "0 KB";
            var i = -1;
            var byteUnits = [ ' KB', ' MB', ' GB', ' TB', 'PB', 'EB', 'ZB', 'YB' ];
            do {
                fileSizeInBytes = fileSizeInBytes / 1024;
                i++;
            } while (fileSizeInBytes > 1024);

            return Math.max(fileSizeInBytes, 0.1).toFixed(1) + byteUnits[i];
        },
        guid : function() {
            function s4() {
                return Math.floor((1 + Math.random()) * 0x10000).toString(16).substring(1);
            }
            return s4() + s4() + '-' + s4() + '-' + s4() + '-' + s4() + '-' + s4() + s4() + s4();
        },
        uuid : function() {
            function s4() {
                return Math.floor((1 + Math.random()) * 0x10000).toString(16).substring(1);
            }
            return s4() + s4() + s4() + s4() + s4() + s4() + s4() + s4();
        },
        getClipboardData : function(e) {
            var pastedText = undefined;
            if (window.clipboardData && window.clipboardData.getData) { // IE
                pastedText = window.clipboardData.getData('Text');
            } else {
                if (!e)
                    e = event;
                if (!e)
                    return null;
                if (e.clipboardData && e.clipboardData.getData) {
                    pastedText = e.clipboardData.getData('text/plain');
                } else
                    (e.originalEvent && e.originalEvent.clipboardData)
                pastedText = e.originalEvent.clipboardData.getData('text/plain');
            }
            return pastedText;

        },
        getByteLength : function(s) {
            var b, i, c;
            for (b = i = 0; c = s.charCodeAt(i++); b += c >> 11 ? this.uniCodeByte : c >> 7 ? 2 : 1)
                ;
            return b;
        },
        parseInt : function(str, x) {
            if (!str)
                return 0;
            if (!x)
                x = 10;
            var ret = parseInt(str, x);
            if (!ret)
                ret = 0;
            return ret;
        },
        parseDate : function(value){
            value = Utilities.getNumberOnly(value);
            if(!value)
                return null;
            var year;
            var month=0;
            var day = 1;
            var hour =0;
            var min = 0;
            var sec = 0;
            if(value.length>=4)
                year = parseInt(value.substring(0,4));
            if(value.length>=6)
                month = parseInt(value.substring(4,6))-1;
            if(value.length>=8)
                day = parseInt(value.substring(6,8));
            if(value.length>=10)
                hour = parseInt(value.substring(8,10));
            if(value.length>=12)
                min = parseInt(value.substring(10,12));
            if(value.length>=14)
                sec = parseInt(value.substring(12,14));
            
            return new Date(year,month,day,hour,min,sec);
        },
        getBrowserType : function() {

            if (this.browserType)
                return this.browserType;
            var _ua = navigator.userAgent;
            var rv = -1;

            // IE 11,10,9,8
            var trident = _ua.match(/Trident\/(\d.\d)/i);
            if (trident != null) {
                if (trident[1] == "7.0")
                    return this.browserType = rv = "IE" + 11;
                if (trident[1] == "6.0")
                    return this.browserType = rv = "IE" + 10;
                if (trident[1] == "5.0")
                    return this.browserType = rv = "IE" + 9;
                if (trident[1] == "4.0")
                    return this.browserType = rv = "IE" + 8;
            }

            if (navigator.appName == 'Microsoft Internet Explorer')
                return this.browserMode = rv = "IE" + 7;

            var agt = _ua.toLowerCase();
            if (agt.indexOf("chrome") != -1)
                return this.browserType = 'Chrome';
            if (agt.indexOf("opera") != -1)
                return this.browserType = 'Opera';
            if (agt.indexOf("staroffice") != -1)
                return this.browserType = 'Star Office';
            if (agt.indexOf("webtv") != -1)
                return this.browserType = 'WebTV';
            if (agt.indexOf("beonex") != -1)
                return this.browserType = 'Beonex';
            if (agt.indexOf("chimera") != -1)
                return this.browserType = 'Chimera';
            if (agt.indexOf("netpositive") != -1)
                return this.browserType = 'NetPositive';
            if (agt.indexOf("phoenix") != -1)
                return this.browserType = 'Phoenix';
            if (agt.indexOf("firefox") != -1)
                return this.browserType = 'Firefox';
            if (agt.indexOf("safari") != -1)
                return this.browserType = 'Safari';
            if (agt.indexOf("skipstone") != -1)
                return this.browserType = 'SkipStone';
            if (agt.indexOf("netscape") != -1)
                return this.browserType = 'Netscape';
            if (agt.indexOf("mozilla/5.0") != -1)
                return this.browserType = 'Mozilla';
        },
        getBrowserMode : function() {

            if (this.browserMode)
                return this.browserMode;
            if (this.getBrowserType().substring(0, 2) != "IE")
                return this.browserMode = this.getBrowserType();

            var _ua = navigator.userAgent;
            if (0 > _ua.indexOf("compatible"))
                return this.browserMode = this.getBrowserType();
            // IE 11,10,9,8
            var idx = _ua.indexOf("MSIE ");
            if (idx < 0)
                return this.browserMode = this.getBrowserType();
            idx += 5;
            var ver = _ua.substring(idx, _ua.indexOf(".", idx));
            if (ver)
                return this.browserMode = "IE" + ver;
            if (navigator.appName == 'Microsoft Internet Explorer')
                return this.browserMode = "IE" + 7;
            return this.browserMode = "IE";
        },
        isNumberCode : function(code) {
            return code >= '0' && code <= '9';
        },
        getNumberOnly : function(str) {
            var ret = "";
            for (var i = 0; str && i < str.length; i++) {
                var ch = str.substring(i, i + 1);
                if (this.isNumberCode(ch))
                    ret += ch;
            }
            return ret;
        },
        numberWithCommas : function (x) {
            x= Math.round(x);
            return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        },
        isNumberOnly : function(str) {
            var ret = "";
            for (var i = 0; str && i < str.length; i++) {
                var ch = str.substring(i, i + 1);
                if (!this.isNumberCode(ch))
                    return false
            }
            return true;
        },
        isAlphaCode : function(code) {
            code = code.toUpperCase();
            return code >= 'A' && code <= 'Z';
        },
        getAlphaOnly : function(str) {
            var ret = "";
            for (var i = 0; str && i < str.length; i++) {
                var ch = str.substring(i, i + 1);
                if (this.isAlphaCode(ch))
                    ret += ch;
            }
            return ret;
        },
        isAlphaNumberCode : function(code) {
            return this.isAlphaCode(code) || this.isNumberCode(code);
        },
        getAlphaNumberOnly : function(str) {
            var ret = "";
            for (var i = 0; str && i < str.length; i++) {
                var ch = str.substring(i, i + 1);
                if (this.isAlphaNumberCode(ch))
                    ret += ch;
            }
            return ret;
        },
        trim : function(value) {
            if (!value)
                return value;
            return $.trim(value);
        },
        getFormObject : function(frm) {
            if (!frm)
                return null;
            var selector = null;
            if (typeof frm === "string") {
                if (frm.substring(0, 1) != "#")
                    selector = "#" + frm;
            } else
                selector = frm;

            var jFrm = $(selector);
            if (!jFrm || !jFrm.length)
                return null;
            return jFrm;
        },
        paramToMap : function(param) {
            if (!param)
                return null;
            var map = {};
            if (param.substring(0, 1) == "?" || param.substring(0, 1) == "#")
                param = param.substring(1);
            var arr = param.split("&");
            for (var i = 0; i < arr.length; i++) {
                var str = arr[i];
                var idx = arr[i].indexOf("=");
                var name = "";
                var value = "";
                if (idx < 0)
                    map[str] = null;
                else if (idx == 0)
                    continue;
                else
                    map[str.substring(0, idx)] = decodeURIComponent(str.substring(idx + 1));
            }
            return map;
        },
        mapToForm : function(map, frm) {
            if (!map)
                return;
            var jFrm = Utilities.getFormObject(frm);
            if (!jFrm || !jFrm.length)
                return null;
            var frmObj = jFrm[0];
            frmObj.reset();
            for ( var name in map) {
//				console.log(name + " : " + map[name]);
                var inp = jFrm.find("[name=" + name + "]");
                if(inp.length>1){
                    if($(inp[0]).attr("type")=="radio"){
                        var rArray = inp;
                        for(var i=0;i<rArray.length;i++){
                            var rdo = $(rArray[i]);
                            var name = rdo.attr("name");
                            var value = rdo.val();
                            if(name && value && map[name]){
                                rdo.prop("checked",map[name]==value);
                            }
                        }
                    }
                    else{
                        var rArray = map[name];
                        if(rArray == null)
                        	continue;
                        if(typeof(rArray) == typeof([])){
                            for(var i=0;i<rArray.length&&i<inp.length;i++){
                                $(inp[i]).val(rArray[i]);                           
                            }
                        }
                        else{
                            $(inp[0]).val(map[name]);
                        }
                    }
                }
                else{
                    inp.val(map[name]); 
                }
                
            }
//          var rArray = jFrm.find("input[type=radio]");
//          for(var i=0;i<rArray.length;i++){
//              var rdo = $(rArray[i]);
//              var name = rdo.attr("name");
//              var value = rdo.val();
//              if(name && value && map[name]){
//                  rdo.prop("checked",map[name]==value);
//              }
//          }
            return;
            // var arr = jFrm.serializeArray();
            // $.each(arr, function() {
            // // if (typeof map[this.name] === "undefined" ||
            // // !frmObj[this.name])
            //
            // if (typeof map[this.name] === "undefined" || jFrm.find("[name=" +
            // this.name + "]").length == 0)
            // return;
            // jFrm.find("[name=" + this.name + "]").val(typeof
            // jFrm.find("[name=" + this.name + "]").length == 1 ?
            // map[this.name] : [ map[this.name] ]);
            // });
        },
        mapToParam : function(map) {
            if (!map)
                return "";
            return $.param(map);

        },
        mapToFormData : function(map) {
            var formData = new FormData();
            if (map) {
                for ( var name in map) {
                    var ret = formData.append(name, map[name]);
                }
            }

            return formData;
        },
        formToParam : function(frm) {
            var jFrm = Utilities.getFormObject(frm);
            if (!jFrm || !jFrm.length)
                return null;
            return jFrm.serialize();
        },
        formToMap : function(frm) {
            var jFrm = Utilities.getFormObject(frm);
            if (!jFrm || !jFrm.length)
                return null;
            var map = {};
            var disa = jFrm.find("[disabled]");
            disa.prop("disabled", false);
            var arr = jFrm.serializeArray();
            $.each(arr, function() {
                if (typeof map[this.name] === "undefined") {
                    map[this.name] = this.value || '';
                } else {
                    if (!map[this.name].push) {
                        map[this.name] = [ map[this.name] ];
                    }
                    map[this.name].push(this.value || '');

                }
            });
            disa.prop("disabled", true);
            return map;

        },
        replaceString : function(str, rep, value) {

            if (!rep || !str)
                return str;

            var val = null;
            try {
                var arr = str.split(rep);
                val = arr.join(value);
            } catch (e) {
                val = str.replace(rep, value);
                while (val != str) {
                    str = val;
                    val = str.replace(rep, value);
                }
                return val;
            }
            return val;

        },
        isCheckPass : function(value) {
            return false;  
        },
        assertFalse : function(value) {
            if (this.isCheckPass(value))
                return this._NULL_RETURN;
            return value.toString() == false.toString();
        },
        assertTrue : function(value) {
            if (this.isCheckPass(value))
                return this._NULL_RETURN;
            return value.toString() == true.toString();
        },
        checkDecimalMax : function(value, max) {
            if (this.isCheckPass(value))
                return this._NULL_RETURN;
            return Number(value.toString()) <= Number(max.toString());
        },
        checkDecimalMin : function(value, min) {
            if (this.isCheckPass(value))
                return this._NULL_RETURN;
            return Number(value.toString()) >= Number(min.toString());
        },
        checkMax : function(value, max) {
            return this.checkDecimalMax(value, max);
        },
        checkMin : function(value, min) {
            return this.checkDecimalMin(value, min);
        },
        checkDigits : function(value, integer, fraction) {
            if (this.isCheckPass(value))
                return this._NULL_RETURN;
            var values = value.toString().split(".");
            if (values.length == 0 || values.length > 2)
                return false;

            intValue = values[0];
            fraValue = values.length == 2 ? values[1] : "";

            if (integer > -1) {
                intValue = Utilities.parseInt(intValue);
                if (integer == 0 && (intValue != 0))
                    return false;
                if (intValue.toString().length > integer)
                    return false;
            }
            if (fraction > -1 && fraValue.length > 0) {
                if (values.length != 2)
                    return false;
                var fr = Utilities.parseInt(fraValue);
                if (!typeof fr === "NaN")
                    return false;
                if (fr.toString().length > fraction)
                    return false;

            }

            return true;
        },
        checkFuture : function(value) {
            if (this.isCheckPass(value))
                return this._NULL_RETURN;
            return Number(value) > Number(new Date());
        },
        checkPast : function(value) {
            if (this.isCheckPass(value))
                return this._NULL_RETURN;
            return Number(value) < Number(new Date());
        },
        checkNotNull : function(value) {
            return value != null;
        },
        checkNull : function(value) {
            return value == null;
        },
        checkPattern : function(value, pattern, flags) {
            if (this.isCheckPass(value))
                return this._NULL_RETURN;
            var flag = "";
            for (var i = 0; flags != null && i < flags.length; i++) {
                if (0x02 == flags[i])
                    flag += "i";
                else if (0x08 == flags[i])
                    flag += "m";
            }
            var reg = null;
            if (flag == "")
                reg = new RegExp(pattern);
            else
                reg = new RegExp(pattern, flag);
            return reg.test(value);
        },
        checkSize : function(value, min, max) {
            if (this.isCheckPass(value))
                return this._NULL_RETURN;
            return this.checkMax(value.length, max) && this.checkMin(value.length, min);
        },
        checkSizeB : function(value, min, max) {
            if (this.isCheckPass(value))
                return this._NULL_RETURN;
            var btLen = Utilities.getByteLength(value);
            return this.checkMax(btLen, max) && this.checkMin(btLen, min);
        },
        checkIsInteger : function(value) {
            if (this.isCheckPass(value))
                return this._NULL_RETURN;
            var num = Number(value.toString());
            if (num == NaN)
                return false;
            return num == Utilities.parseInt(value.toString());
        },
        checkEmail : function(value) {

            if (this.isCheckPass(value))
                return this._NULL_RETURN;
            // var pattern =
            // "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-.]+\.[a-zA-Z0-9-.]+$";
            // var pattern
            // ="^[0-9a-zA-Z]([\-.\w]*[0-9a-zA-Z\-_+])*@([0-9a-zA-Z][\-\w]*[0-9a-zA-Z]\.)+[a-zA-Z]{2,9}$";
            var pattern = "^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$";
            var flags = [ 0x02 ];
            return this.checkPattern(value, pattern, flags);
        },
        checkCreditCardNumber : function(value) {
            if (this.isCheckPass(value))
                return this._NULL_RETURN;
            return true;
        },
        checkLength : function(value, min, max) {
            if (this.isCheckPass(value))
                return this._NULL_RETURN;
            if (!typeof value === 'string')
                return false;
            return this.checkSize(value, min, max);
        },
        checkNotBlank : function(value) {
            return this.checkNotEmpty(Utilities.trim(value));
        },
        checkNotEmpty : function(value) {
            if (value == null)
                return false;
            if (value.length == 0)
                return false;
            return true;
        },

        checkRange : function(value, min, max) {
            if (this.isCheckPass(value))
                return this._NULL_RETURN;
            return value <= max && value >= min;
        },
        checkSafeHtml : function(value, type, add) {
            if (this.isCheckPass(value))
                return this._NULL_RETURN;
            return true;
        },
        checkScriptAssert : function(value, lang, script, alias) {
            if (this.isCheckPass(value))
                return this._NULL_RETURN;
            return true;
        },
        checkURL : function(value) {
            if (this.isCheckPass(value))
                return this._NULL_RETURN;
            var reg = /(http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w-.\/?%&;=]*)?)/gi;
            var url = value.match(reg);
            if (url == null || url.length == 0)
                return false;
            if (url[0] == value)
                return true;
            return false;
        },
        checkNumberOnly : function(value) {
            return this.checkPattern(value, "^[0-9]*$", "");
        },
        goUrl : function(url, context) {
            if (!context)
                context = window;
            context.open(url, "_self");
        },
        checkAllBox : function(name, checked) {
            $("input[name=" + name + "]").prop("checked", checked);
        },
        toggleCheckBox : function(name) {
            var checked = false;
            if (Utilities.hasUncheckedBox(name))
                checked = true;
            Utilities.checkAllBox(name, checked);

        },
        hasUncheckedBox : function(name) {
            var chks = $("input[name=" + name + "]");
            for (var i = 0; i < chks.length; i++) {
                if ($(chks[i]).attr("checkAll"))
                    continue;
                if (!chks[i].checked)
                    return true;
            }
            return false;
        },
        objectToPostParam : function(obj) {
            var strParam = "";
            for ( var i in obj) {
                if (strParam.length)
                    strParam += "&";
                strParam += i.toString() + "=" + obj[i];
            }
            return strParam;
        },
        overrideEvent : function(fnName, fn, bReplace) {
            if (!window[fnName] || bReplace)
                window[fnName] = fn;
            else {
                var orgFn = window[fnName];
                window[fnName] = function() {

                    try {
                        if (!orgFn.apply(this, arguments))
                            return fn.apply(this, arguments);
                    } catch (e) {
                        console.error(e);
                        return fn.apply(this, arguments);
                    }
                };
            }

        },
        ajax : function(param) {
            $.ajax(param);
        },
        toJSON : function(param) {
            return JSON.stringify(param);
        },
        parseJSON : function(param) {
            return $.parseJSON(param);
        },
        getObject : function(url, param, jsonBody, option) {
            var nocaching;
            if (url.indexOf("?") > 0)
                nocaching = "&";
            else
                nocaching = "?";
            var url = url + nocaching + "nocachingParam=" + Utilities.guid();
            var retVal = null;
            var opt = {
                url : url,
                dataType : 'json',
                data : jsonBody && param ? Utilities.toJSON(param) : param,
                type : 'POST',
                cache : false,
                contentType : jsonBody ? 'application/json; charset=utf-8' : 'application/x-www-form-urlencoded; charset=utf-8',
                async : false,
                success : function(data, textStatus, jqXHR) {
                    if (data.error) {
                        if (data.message)
                            alert(data.message);
                    } else
                        retVal = data;
                },
                error : function(jqXHR, textStatus, errorThrown) {

                    retVal = null;
                },
                handleError : true

            };

            if (option)
                opt = $.extend(opt, option);
            Utilities.ajax(opt);
            return retVal;
        },
        reLoad : function() {
            for(var i = 0;i<window.parent.length;i++){
                window.parent[i].location.reload();
            }
        },
        getAjax : function(url, param, jsonBody, callback, option) {
            var nocaching;
            if (url.indexOf("?") > 0)
                nocaching = "&";
            else
                nocaching = "?";
            var url = url + nocaching + "nocachingParam=" + Utilities.guid();
            var returnValue = null;
            var mthd = "GET";
            if(param || jsonBody)
            	mthd = "POST";
            var opt = {
                url : url,
                dataType : 'json',
                data : jsonBody && param ? Utilities.toJSON(param) : param,
                type : mthd,
                cache : false,
                contentType : jsonBody ? 'application/json; charset=utf-8' : ( mthd == 'POST' ?  'application/x-www-form-urlencoded; charset=utf-8' : ''),
                async : true,
                success : function(data, textStatus, jqXHR) {
                    result = true;
                    returnValue = data;
                },
                error : function(jqXHR, textStatus, errorThrown) {
                    returnValue = null;
                },
                complete : function(jqXHR, textStatus) {
                    if (callback) {
                        try {
                            callback(returnValue, jqXHR);
                        } catch (e) {
                            console.error(e);
                        }
                    }
                },
                handleError : true
            };
            if (option)
                opt = $.extend(opt, option);
            Utilities.ajax(opt);
        },
        ajaxDownload : function(url, param, jsonBody, progressCallback, completeCallback, option) {
            var callback = function(returnValue, jqXHR) {
                jqXHR.response = returnValue;
                Utilities.saveAjaxFile(jqXHR, null);
                if (completeCallback)
                    completeCallback(returnValue, jqXHR);
            }
            var prgCallbaock = function(loaded, total, percentComplete) {
                if (progressCallback) {
                    progressCallback(loaded, total, percentComplete);
                }

            };
            var opt = {
                dataType : false,
                contentType : "application/json; charset=utf-8",
                processData : false,
                xhrFields : {
                    responseType : 'blob'
                },
                xhr : function() {
                    var xhr = new window.XMLHttpRequest();
                    xhr.onprogress = function(evt) {
                        if (evt.lengthComputable) {
                            var percentComplete = evt.loaded / evt.total;
                            prgCallbaock(evt.loaded, evt.total, percentComplete);
                        } else {
                            prgCallbaock(evt.loaded);
                        }
                    };
                    xhr.addEventListener("loadstart", function(evt) {
                        prgCallbaock(0, 0, 0);
                    }, false);
                    return xhr;
                }
            };

            if (option)
                opt = $.extend(opt, option);

            Utilities.getAjax(url, param, jsonBody, callback, opt);

        },
        saveAjaxFile : function(req, filename) {
            if (!filename) {
                var desc = req.getResponseHeader("content-disposition");
                if (!desc)
                    return false;
                var idx = desc.indexOf("filename=");
                if (idx > 0) {
                    filename = desc.substring(idx + "filename=".length);
                    if (filename.substring(0, 1) == '"' || filename.substring(0, 1) == "'")
                        filename = filename.substring(1);
                    idx = filename.lastIndexOf('"');
                    if (idx >= 0)
                        filename = filename.substring(0, idx);
                    idx = filename.lastIndexOf("'");
                    if (idx >= 0)
                        filename = filename.substring(0, idx);
                }

            }
            filename = decodeURIComponent(filename);
            if (typeof window.chrome !== "undefined") {
                var link = document.createElement("a");
                var res = req.response;
                if (!res) {
                    res = new Blob([ req.responseText ], {
                        type : 'text/plain'
                    });
                }
                link.href = window.URL.createObjectURL(res);
                link.download = filename;
                link.click();
            } else if (typeof window.navigator.msSaveBlob !== "undefined") {
                var blob = new Blob([ req.response || req.resopnseText ], {
                    type : 'application/force-download'
                });
                window.navigator.msSaveBlob(blob, filename);
            } else {
                var file = new File([ req.response || req.responseText ], filename, {
                    type : 'application/force-download'
                });
                window.open(URL.createObjectURL(file));
            }
        },
        downloadText : function(text, filename) {
            if (typeof window.chrome !== "undefined") {
                var link = document.createElement("a");
                var res = new Blob([ text ], {
                    type : 'text/plain'
                });
                link.href = window.URL.createObjectURL(res);
                link.download = filename;
                link.click();
            } else if (typeof window.navigator.msSaveBlob !== "undefined") {
                var blob = new Blob([ text ], {
                    type : 'application/force-download'
                });
                window.navigator.msSaveBlob(blob, filename);
            } else {
                var file = new File([ text ], filename, {
                    type : 'application/force-download'
                });
                window.open(URL.createObjectURL(file));
            }
        },
        downloadFile : function(data, filename) {
            
                
            if (typeof window.chrome !== "undefined") {
                var link = document.createElement("a");
                var res = new Blob([ data ], {
                    type : "application/octet-stream"
                });
                link.href = window.URL.createObjectURL(res);
                link.download = filename;
                link.click();
            } else if (typeof window.navigator.msSaveBlob !== "undefined") {
                var blob = new Blob([ data ], {
                    type : 'application/force-download'
                });
                window.navigator.msSaveBlob(blob, filename);
            } else {
                var file = new File([ data ], filename, {
                    type : 'application/force-download'
                });
                window.open(URL.createObjectURL(file));
            }
        },
        downloadFileUrl : function(fileUrl,fileNm){
            var ifDown = $("#ifDownload");
            if(!ifDown.length)
            {
                ifDown = $('<iframe style="display:none" id="ifDownload" ></iframe>');  
                $("body").append(ifDown);
            }
            var downloadUrl = Utilities.getDownloadUrl({
                fileUrl: fileUrl,
                fileNm : fileNm
                    
            });
//          var downloadUrl = _basePath + "/file/downloadUrl"+_urlSuffix+ "?fileUrl="+fileUrl+"&fileNm="+ fileNm;
            ifDown.prop("src",downloadUrl);
        },
        min : function(x1, x2) {
            return x1 > x2 ? x2 : x1;
        },
        max : function(x1, x2) {
            return x1 < x2 ? x2 : x1;
        },
        getRect : function(left, right, top, bottom) {
            return {
                left : left,
                right : right,
                top : top,
                bottom : bottom,
                height : function() {
                    return this.bottom - this.top;
                },
                width : function() {
                    return this.right - this.left;

                },
                ptInRect : function(x, y) {
                    return x >= this.left && x <= this.right && y >= this.top && y <= this.bottom;
                },
                interSection : function(rect) {
                    var section = Utilities.getRect();
                    section.left = Utilities.max(this.left, rect.left);
                    section.right = Utilities.min(this.right, rect.right);
                    section.top = Utilities.max(this.top, rect.top);
                    section.bottom = Utilities.min(this.bottom, rect.bottom);
                    return section;
                },
                union : function(rect) {
                    var section = Utilities.getRect();
                    section.left = Utilities.min(this.left, rect.left);
                    section.right = Utilities.max(this.right, rect.right);
                    section.top = Utilities.min(this.top, rect.top);
                    section.bottom = Utilities.max(this.bottom, rect.bottom);
                    return section;
                },
                normalize : function() {
                    var left = this.left, right = this.right, top = this.top, bottom = this.bottom;
                    if (!left)
                        left = 0;
                    if (!right)
                        right = 0;
                    if (!top)
                        top = 0;
                    if (!bottom)
                        bottom = 0;
                    this.top = Utilities.min(top, bottom);
                    this.left = Utilities.min(left, right);
                    this.bottom = Utilities.max(top, bottom);
                    this.right = Utilities.max(left, right);
                    return this;
                },
                moveRect : function(x, y) {
                    this.left -= x;
                    this.right -= x;
                    this.top -= y;
                    this.bottom -= y;
                    return this;
                }
            }.normalize();
        },
        getBoundRect : function(element, onlyView) {
            var bounds = Utilities.getRect();
            var rect = element.offset();
            bounds.left = rect.left;
            bounds.top = rect.top;

            bounds.right = rect.left + element.outerWidth();
            bounds.bottom = rect.top + element.outerHeight();
            var rect;
            if (onlyView) {
                var view = Utilities.getViewport();
                return view.interSection(bounds);
            }
            return bounds;
        },
        getViewport : function() {
            var viewport = Utilities.getRect();
            var win = $(window);
            viewport.top = win.scrollTop();
            viewport.left = win.scrollLeft();
            viewport.right = viewport.left + win.width();
            viewport.bottom = viewport.top + win.height();
            return viewport;
        },
        isOnScreen : function(element) {
            var viewport = Utilities.getViewport();
            var bounds = Utilities.getBoundRect(element);
            var ret = viewport.right < bounds.left || viewport.left > bounds.right || viewport.bottom < bounds.top || viewport.top > bounds.bottom;
            return !ret;
        },
        removeCookie : function(name) {
            return $.removeCookie(name);
        },
        setCookie : function(name, value, day) {
            if (day == undefined)
                day = 1;
            Utilities.removeCookie(name);
            $.cookie(name, value, {
                expires : day,
                path : '/'
            });
        },
        getCookie : function(name) {
            return $.cookie(name);
        },
        byteCheck : function(elSlt) {
            var codeByte = 0;
            for (var idx = 0; idx < elSlt.val().length; idx++) {
                var oneChar = escape(elSlt.val().charAt(idx));
                if (oneChar.length == 1) {
                    codeByte++;
                } else if (oneChar.indexOf("%u") != -1) {
                    codeByte += 3;
                } else if (oneChar.indexOf("%") != -1) {
                    codeByte++;
                }
            }
            return codeByte;
        },
        maxLengthCheck : function(elSlt, title, maxLength) {
            var obj = elSlt;
            if (maxLength == null) {
                maxLength = obj.attr("data-max-byte") != null ? obj.attr("data-max-byte") : 1000;
            }
            if (Number(Utilities.byteCheck(obj)) > Number(maxLength)) {
                alert(title + "이(가) 입력가능문자수를 초과하였습니다.\n(영문, 숫자, 일반 특수문자 : " + maxLength + " / 한글, 한자, 기타 특수문자 : " + parseInt(maxLength / 3, 10) + ").");
                elSlt.val(Utilities.cutByteString(elSlt, maxLength));
                obj.focus();
                return false;
            } else {
                return true;
            }
        },
        encodeURI : function(url) {
            if (!url)
                return "";

            var idxUrl = url.indexOf('?');
            var rootUrl = url.substring(0, idxUrl);
            var newUrl = "";
            if (idxUrl > 0) {
                var paramStr = url.substring(idxUrl + 1, url.length);
                var paramArr = paramStr.split('&');
                var newParam = [];
                $.each(paramArr, function(idx, obj) {
                    var objArr = obj.split('=');
                    var key = objArr[0];
                    var val = objArr[1];
                    if (val != '' || val != 'undefined' || val != null) {
                        val = encodeURI(val);
                    }
                    newParam.push(key + '=' + val);
                });
                if (newParam.length > 0) {
                    var newParamStr = newParam.join('&');
                    newUrl = rootUrl + '?' + newParamStr;
                } else {
                    newUrl = url;
                }
            }
            return newUrl;
        },
        windowOpen : function(url, name, width, height) {
            var spec = "width=" + width + ",height=" + height;
            var win = window.open(url, name, spec);
        },
        windowFullOpen : function(url, name) {
            var width = screen.width-50;
            var height = screen.height;
            var spec = "width=" + width + ",height=" + height;
            var win = window.open(url, name, spec);
        },
        processResult : function(data, jqXHR, defaultErrorMsg) {
            if (jqXHR.status == _ERROR_NEED_LOGIN)
                return false;
            if (jqXHR.status != 200) {
                if (!data)
                    data = jqXHR.responseJSON;
                var msg = data ? data.errorMsg || defaultErrorMsg : defaultErrorMsg;
                
                if (jqXHR.status == _ERROR_KEY_DUPLICATE) {
                    msg = "중복된 키가 존재합니다.";
                }

                if (msg) {
                    alert(msg);
                }
                return false;
            }
            if ( typeof(data)!= typeof([]) && ( data == null || data == false)) {
                if (defaultErrorMsg)
                    alert(defaultErrorMsg);
                return false;
            }
            if (data) {
                if (data.errorCode || data.errorMsg) {
                    var msg = data.errorMsg || defaultErrorMsg;
                    if (data.errorMsg) {
                        alert(msg);
                    }
                    return false;
                }

            }
            return true;
        },
        convert2CamelCase : function(underScore) {
            fChar = underScore.substring(0, 1);
            if(fChar == "_")
                return underScore;
            fChar2 = fChar.toLowerCase();

            if (underScore.indexOf('_') < 0 && fChar == fChar2) {
                return underScore;
            }
            var result = "";
            var nextUpper = false;
            var len = underScore.length;

            for (var i = 0; i < len; i++) {
                var currentChar = underScore.charAt(i);
                var uChar = currentChar.toUpperCase();
                var lChar = currentChar.toLowerCase();
                if (currentChar == '_') {
                    nextUpper = true;
                } else {
                    if (nextUpper) {
                        result += uChar;
                        nextUpper = false;
                    } else {
                        result += lChar;
                    }
                }
            }
            return result;
        },
        paddingZero : function(num, place) {
            var strNum = num + "";
            for (var i = strNum.length; i < place; i++) {
                strNum = "0" + strNum;
            }
            return strNum;
        },
        isNotEmpty : function(obj) {
            return !this.isEmpty(obj)
        },
        isEmpty : function(obj) {
            if (obj == null)
                return true;
            if (typeof (obj) == typeof ([])) {
                return obj.length == 0;
            }
            if (typeof (obj) == typeof ({})) {
                for (key in obj) {
                    return true;
                }
                return false;
            }
            if (typeof (obj) == typeof (" ")) {
                return obj.length == 0;
            }
            return false;
        },
        validatePasswordMsg : function(pw) {
            var o = {
                length : [ 6, 16 ],
                lower : 1,
                upper : 1,
                alpha : 1, /* lower + upper */
                numeric : 1,
                special : 1,
                custom : [ /* regexes and/or functions */],
                badWords : [],
                badSequenceLength : 5,
                noQwertySequences : true,
                spaceChk : true,
                noSequential : false
            };
            /* space bar check */
            if (o.spaceChk && /\s/g.test(pw)) {
                return "<p style='line-height:200%;'><span style='color:#E3691E; font-weight:bold;'>사용불가</span> : 비밀번호 재작성 필요" + "<br/>"
                        + "<span style='color:#999; font-weight:bold;'>영문 대소문자, 숫자 및 특수문자 사용</span></p>";
            }
            /* password 길이 체크 */
            if (pw.length < o.length[0])
                return "<p style='line-height:200%;'><span style='color:#E3691E; font-weight:bold;'>사용불가</span>" + "<br/>" + "<span style='color:#999; font-weight:bold;'>비밀번호는 " + o.length[0]
                        + "자 이상 입력하셔야 합니다.</span></p>";
            if (pw.length > o.length[1])
                return "<p style='line-height:200%;'><span style='color:#E3691E; font-weight:bold;'>사용불가</span>" + "<br/>" + "<span style='color:#999;'>비밀번호는 " + o.length[1]
                        + "자 이내로 입력하셔야 합니다.</span></p>";
            /* bad sequence check */
            if (o.badSequenceLength && pw.length >= o.length[0]) {
                var lower = "abcdefghijklmnopqrstuvwxyz", upper = lower.toUpperCase(), numbers = "0123456789", qwerty = "qwertyuiopasdfghjklzxcvbnm", start = o.badSequenceLength - 1, seq = "_"
                        + pw.slice(0, start);
                for (i = start; i < pw.length; i++) {
                    seq = seq.slice(1) + pw.charAt(i);
                    if (lower.indexOf(seq) > -1 || upper.indexOf(seq) > -1 || numbers.indexOf(seq) > -1 || (o.noQwertySequences && qwerty.indexOf(seq) > -1)) {
                        return "<p style='line-height:200%;'>비밀번호 안전도 <span style='color:#E5E5E5'>|</span> <span style='color:#E3691E; font-weight:bold;'>낮음</span> "
                                + "<span style='color:#E3691E; font-weight:bold; font-size:20px; position: relative; top: 1.5px;'>―</span>"
                                + "<span style='color:#E5E5E5; font-weight:bold; font-size:20px; position: relative; top: 1.5px;''>―</span>"
                                + "<span style='color:#E5E5E5; font-weight:bold; font-size:20px; position: relative; top: 1.5px;''>―</span>" + "<br/>"
                                + "<span style='color:#999; font-weight:bold;'>안전도가 높은 비밀번호를 권장합니다.</span></p>";
                    }
                }
            }
            /* password 정규식 체크 */
            var re = {
                lower : /[a-z]/g,
                upper : /[A-Z]/g,
                alpha : /[A-Z]/gi,
                numeric : /[0-9]/g,
                special : /[\W_]/g
            }, rule, i;
            var lower = (pw.match(re['lower']) || []).length > 0 ? 1 : 0;
            var upper = (pw.match(re['upper']) || []).length > 0 ? 1 : 0;
            var numeric = (pw.match(re['numeric']) || []).length > 0 ? 1 : 0;
            var special = (pw.match(re['special']) || []).length > 0 ? 1 : 0;
            /* 숫자로만 이루어지면 낮음 */
            if ((pw.match(re['numeric']) || []).length == pw.length) {
                return "<p style='line-height:200%;'><span style='color:#E3691E; font-weight:bold;'>사용불가</span> : 비밀번호 재작성 필요" + "<br/>"
                        + "<span style='color:#999; font-weight:bold;'>영문 대소문자, 숫자 및 특수문자 사용</span></p>";
            }
            /* 숫자, 알파벳(대문자, 소문자), 특수문자 2가지 조합 */
            else if (lower + upper + numeric + special <= 2) {
                return "<p style='line-height:200%;'>비밀번호 안전도 <span style='color:#E5E5E5'>|</span> <span style='color:#E3691E; font-weight:bold;'>낮음</span> "
                        + "<span style='color:#E3691E; font-weight:bold; font-size:20px; position: relative; top: 1.5px;'>―</span>"
                        + "<span style='color:#E5E5E5; font-weight:bold; font-size:20px; position: relative; top: 1.5px;''>―</span>"
                        + "<span style='color:#E5E5E5; font-weight:bold; font-size:20px; position: relative; top: 1.5px;''>―</span>" + "<br/>"
                        + "<span style='color:#999; font-weight:bold;'>안전도가 높은 비밀번호를 권장합니다.</span></p>";
            }
            /* 숫자, 알파벳(대문자, 소문자), 특수문자 4가지 조합 */
            else if (lower + upper + numeric + special <= 3) {
                return "<p style='line-height:200%;'>비밀번호 안전도 <span style='color:#E5E5E5'>|</span> <span style='color:#E3691E; font-weight:bold;'>적정</span> "
                        + "<span style='color:#E3691E; font-weight:bold; font-size:20px; position: relative; top: 1.5px;'>―</span>"
                        + "<span style='color:#E3691E; font-weight:bold; font-size:20px; position: relative; top: 1.5px;''>―</span>"
                        + "<span style='color:#E5E5E5; font-weight:bold; font-size:20px; position: relative; top: 1.5px;''>―</span>" + "<br/>"
                        + "<span style='color:#999; font-weight:bold;'>안전하게 사용하실 수 있는 비밀번호 입니다.</span></p>";
            }
            /* 숫자, 알파벳(대문자, 소문자), 특수문자 4가지 조합 */
            else {
                return "<p style='line-height:200%;'>비밀번호 안전도 <span style='color:#E5E5E5'>|</span> <span style='color:#E3691E; font-weight:bold;'>높음</span> "
                        + "<span style='color:#E3691E; font-weight:bold; font-size:20px; position: relative; top: 1.5px;'>―</span>"
                        + "<span style='color:#E3691E; font-weight:bold; font-size:20px; position: relative; top: 1.5px;''>―</span>"
                        + "<span style='color:#E3691E; font-weight:bold; font-size:20px; position: relative; top: 1.5px;''>―</span>" + "<br/>"
                        + "<span style='color:#999; font-weight:bold;'>예측하기 힘든 비밀번호로 더욱 안전합니다.</span></p>";
            }
            /* enforce the no sequential, identical characters rule */
            // if (o.noSequential && /([\S\s])\1/.test(pw))
            // return "no sequential";
            // /* enforce word ban (case insensitive) */
            // for (i = 0; i < o.badWords.length; i++) {
            // if (pw.toLowerCase().indexOf(o.badWords[i].toLowerCase()) > -1)
            // return "bad word";
            // }
            // /* enforce custom regex/function rules */
            // for (i = 0; i < o.custom.length; i++) {
            // rule = o.custom[i];
            // if (rule instanceof RegExp) {
            // if (!rule.test(pw))
            // return "custom";
            // } else if (rule instanceof Function) {
            // if (!rule(pw))
            // return "custom";
            // }
            // }
        },
        validatePassword : function(pw) {
            var o = {
                length : [ 6, 16 ],
                lower : 1,
                upper : 1,
                alpha : 1, /* lower + upper */
                numeric : 1,
                special : 1,
                custom : [ /* regexes and/or functions */],
                badWords : [],
                badSequenceLength : 5,
                noQwertySequences : true,
                spaceChk : true,
                noSequential : false
            };
            /* space bar check */
            if (o.spaceChk && /\s/g.test(pw)) {
                return false;
            }
            /* password 길이 체크 */
            if (pw.length < o.length[0])
                return false;
            if (pw.length > o.length[1])
                return false;
            /* bad sequence check */
            if (o.badSequenceLength && pw.length >= o.length[0]) {
                var lower = "abcdefghijklmnopqrstuvwxyz", upper = lower.toUpperCase(), numbers = "0123456789", qwerty = "qwertyuiopasdfghjklzxcvbnm", start = o.badSequenceLength - 1, seq = "_"
                        + pw.slice(0, start);
                for (i = start; i < pw.length; i++) {
                    seq = seq.slice(1) + pw.charAt(i);
                    if (lower.indexOf(seq) > -1 || upper.indexOf(seq) > -1 || numbers.indexOf(seq) > -1 || (o.noQwertySequences && qwerty.indexOf(seq) > -1)) {
                        return false;
                    }
                }
            }
            /* password 정규식 체크 */
            var re = {
                lower : /[a-z]/g,
                upper : /[A-Z]/g,
                alpha : /[A-Z]/gi,
                numeric : /[0-9]/g,
                special : /[\W_]/g
            }, rule, i;
            var lower = (pw.match(re['lower']) || []).length > 0 ? 1 : 0;
            var upper = (pw.match(re['upper']) || []).length > 0 ? 1 : 0;
            var numeric = (pw.match(re['numeric']) || []).length > 0 ? 1 : 0;
            var special = (pw.match(re['special']) || []).length > 0 ? 1 : 0;
            /* 숫자로만 이루어지면 낮음 */
            if ((pw.match(re['numeric']) || []).length == pw.length) {
                return false;
            }
            /* 숫자, 알파벳(대문자, 소문자), 특수문자 2가지 조합 */
            else if (lower + upper + numeric + special <= 2) {
                return false;
            }
            /* 숫자, 알파벳(대문자, 소문자), 특수문자 4가지 조합 */
            else if (lower + upper + numeric + special <= 3) {
                return true;
            }
            /* 숫자, 알파벳(대문자, 소문자), 특수문자 4가지 조합 */
            else {
                return true;
            }
        },
        formatXml : function(xml) {
            var reg = /(>)(<)(\/*)/g;
            var wsexp = / *(.*) +\n/g;
            var contexp = /(<.+>)(.+\n)/g;
            xml = xml.replace(reg, '$1\n$2$3').replace(wsexp, '$1\n').replace(contexp, '$1\n$2');
            var pad = 0;
            var formatted = '';
            var lines = xml.split('\n');
            var indent = 0;
            var lastType = 'other';
            // 4 types of tags - single, closing, opening, other (text, doctype,
            // comment) - 4*4 = 16 transitions
            var transitions = {
                'single->single' : 0,
                'single->closing' : -1,
                'single->opening' : 0,
                'single->other' : 0,
                'closing->single' : 0,
                'closing->closing' : -1,
                'closing->opening' : 0,
                'closing->other' : 0,
                'opening->single' : 1,
                'opening->closing' : 0,
                'opening->opening' : 1,
                'opening->other' : 1,
                'other->single' : 0,
                'other->closing' : -1,
                'other->opening' : 0,
                'other->other' : 0
            };

            for (var i = 0; i < lines.length; i++) {
                var ln = lines[i];
                var single = Boolean(ln.match(/<.+\/>/)); // is this line a
                // single tag? ex.
                // <br />
                var closing = Boolean(ln.match(/<\/.+>/)); // is this a closing
                // tag? ex. </a>
                var opening = Boolean(ln.match(/<[^!].*>/)); // is this even
                // a tag (that's
                // not
                // <!something>)
                var type = single ? 'single' : closing ? 'closing' : opening ? 'opening' : 'other';
                var fromTo = lastType + '->' + type;
                lastType = type;
                var padding = '';

                indent += transitions[fromTo];
                for (var j = 0; j < indent; j++) {
                    padding += '    ';
                }

                formatted += padding + ln + '\n';
            }

            return formatted;
        },
        getTopWindow : function(){
            try{
                if(top.Utilities)
                    return top;
            }catch(e){}
            
             var parentWin  = window.parent;
            try{
                if(parentWin.Utilities){
                    
                }
            }catch(e){
                return window;
            }
            
        while(parentWin && parentWin != window){
            try{
                if(parentWin.Utilities){
                    var par  = parentWin.parent;
                    if(par.Utilities){
                        parentWin = parentWin.parent;
                        continue;
                    }
                }
            }catch(e){
                
            }
            return parentWin;
        }     
                
            
            
            return window;
        },
        /**
         * 모달 오픈 함수
         * @param url : jsp주소
         * @param width : 너비
         * @param height : 높이
         * @param callFunc : 모달을 호출한 변수명(임의 지정 가능 - callback 함수에서 switch 문으로 분기처리하기 위한 값)
         * @param callbackFunc : callback 함수명
         * @param callbackWin : callback window명
         */
        openModal :function(url,width,height,callFunc,callbackFunc,callbackWin){
//            if(!callFunc){
//                console.error("openModal 함수의 4번째 변수(모달을 호출한 변수명)를 지정해주세요. 필수값입니다.");
//                return;
//            }
            if(!callbackWin){
                callbackWin = window;
            }

            var topWin = Utilities.getTopWindow(); // return top
            if(window != topWin)
            {
                // 부모창이 window가 아닐 경우, 모달안에서 모달 열기
                try{
                    topWin.Utilities.openModal(url,width,height,callFunc,callbackFunc,callbackWin);
                return;
                }catch(e){
                    
                }
            }

            var inst = $.fancybox.open({
                src  : url,
                type : 'iframe',
                width: width,
                height: height,
                toolbar  : false,
                smallBtn : true,
                keyboard: false ,
//              beforeClose : function( instance, current, e ) {
//                  instance.callbackWindow.함수
//               },

                touch : false,
                modal : true,
                iframe : {
                    preload : false
                },
                helpers:  {
                        overlay : {
                            closeClick  : false
                        }
                    }
            });

            inst.callFunc = callFunc; // 호출 함수명

             // 콜백함수명 : 따로 지정을 안하거나 지정한 이름이 함수가 아닌 경우, 콜백 윈도우의 modalCallbackFunc 의 이름으로 callback 함수 지정
            inst.callbackFunc = typeof(callbackFunc) == "function" ? callbackFunc : callbackWin.modalCallback;
//            if(Utilities.isEmpty(inst.callbackFunc)){
//                console.error("콜백함수가 부모창에 존재하지 않습니다. 확인해주세요.");
//                return false;
//            }
            inst.callbackWindow = callbackWin; // 콜백 윈도우명
        },
        closeModal : function(data){
            var topWin = Utilities.getTopWindow();
            if(window != topWin)
            {
                try{
                topWin.Utilities.closeModal(data);
                return;
                }catch(e){}
            }
            var inst = $.fancybox.getInstance();
            if(inst){
                if(inst.callbackFunc){
                    if(data){
                        inst.close(inst.callbackFunc(inst.callFunc, data));
                    }
                }
                inst.close();
            }

        },
         playAudio :function(url){
            var topWin = Utilities.getTopWindow(); // return top
            if(window != topWin)
            {
                // 부모창이 window가 아닐 경우, 모달안에서 모달 열기
                 try{
                topWin.Utilities.playAudio(video);
                return;
                }catch(e){}
            }            
            Utilities.openModal("/util/audioPlayer?playUrl="+encodeURIComponent(url),350,150);
        },
        showVideo:function(video){
        	var topWin = Utilities.getTopWindow(); // return top
            if(window != topWin)
            {
                // 부모창이 window가 아닐 경우, 모달안에서 모달 열기
                 try{
                topWin.Utilities.showVideo(video);
                return;
                }catch(e){}
                
                
            }

        	$.fancybox.open({src:video,type : "video"},{});
        },
        showImage:function(img){
        	Utilities.showImages([img]);
        },
        showImages:function(list){
       	 var topWin = Utilities.getTopWindow(); // return top
            if(window != topWin)
            {
                // 부모창이 window가 아닐 경우, 모달안에서 모달 열기
                 try{
                 topWin.Utilities.showImages(list);
                 return;
                }catch(e){}
                
            }

            
       	var imageList = [];
       	for(var i=0;i<list.length;i++){
       		var img = {
       				src : list[i],
       				type : "image",
       				opts : {
       					thumb   : list[i]
       					}
       				}
       		imageList.push(img);
       	}
       	$.fancybox.open(imageList,{
//        		thumbs : {
//        			autoStart : true
//        			,axis: "y"
//        		},
       		loop:false,
       		thumbs : {
    	        autoStart : true,
    	    },
       		 buttons: [
       			   // "zoom",
       			    //"share",
//        			    "slideShow",
       			    //"fullScreen",
       			    "download",
       			    "thumbs",
       			    "close"
       			  ]});
//        	const fancybox = new Fancybox(imageList);
       			
       },showPdf : function(url){
            Utilities.showPdfs([url]);
        },
        showPdfs : function(urlList){
            var topWin = Utilities.getTopWindow(); // return top
            if(window != topWin)
            {
                // 부모창이 window가 아닐 경우, 모달안에서 모달 열기
                
                 try{
                 topWin.Utilities.showPdfs(urlList);
                 return;
                }catch(e){}
                
                
            }
            var view = "/static/js/pdfjs/web/viewer.html?file=";
            var pdfList = [];
            for(var i=0;i<urlList.length;i++){
                var pdf = {
                    src : view + encodeURIComponent(urlList[i])
                };
                pdfList.push(pdf);
            }
            $.fancybox.open(pdfList,{
            	openEffect: 'elastic',
                closeEffect: 'elastic',
                autoSize: true,
                type: 'iframe',
                iframe: {
                    preload: false // fixes issue with iframe and IE
                }});
        },
        getOpener : function() {
            var topWin = Utilities.getTopWindow();
            if(window != topWin)
            {
              try{
                 return topWin.Utilities.getOpener();
                }catch(e){}
                
            }
            var inst = $.fancybox.getInstance();
            if(inst){
                return inst.callbackWindow;
            }

            else{
                return opener;
            }

        },
        openOrgPop: function(callbackName){
			if(!callbackName)
                callbackName = "onOrgSelect";
            var url = "/util/organization/popup?callback=" + callbackName;
            Utilities.openModal(url,1500,800); 
		},
		openEmpPop: function(callbackName){
			if(!callbackName)
                callbackName = "onOrgSelect";
            var url = "/util/employ/popup?callback=" + callbackName;
            Utilities.openModal(url,1500,800); 
		},
        openZipPop : function(callbackName){
            if(!callbackName)
                callbackName = "onAddressSelect";
            var url = "/util/address/addressPop?callback=" + callbackName;
            Utilities.openModal(url,1500,800);  
        },
        openFilePopup : function( fileId,fileCtgryCd,uploadMode) {
            if(!fileCtgryCd)
                fileCtgryCd = "";
            if(uploadMode=="N")
                uploadMode = "view";
               
            if(!fileId)
                fileId = "";
            var url = "/file/fileInfo?fileCd=" + fileId+"&fileCtgryCd=" + fileCtgryCd + "&mode=" + uploadMode;
            Utilities.openModal(url,900,600);    
                
        },
        getFileExt : function(fileName) {
            var idx = fileName.lastIndexOf(".");
            if (idx > -1 && fileName.length > idx + 2)
                return fileName.substring(idx + 1);
            else
                return "";
        },
        getFileUploader : function(options) {
            var uploader = {
                id : Utilities.uuid(),
                currentId : 0,
                fileList : [],
                acceptFiles : [],
                inputUpload : null,
                addCallback : options.addCallback,
                addFile : function() {
                    this.inputUpload.click();
                },
                removeFile : function(id) {
                    for (var i = 0; i < this.fileList.length; i++) {
                        if (this.fileList[i].id == id) {
                            var data = this.fileList[i];
                            this.cancelUpload(data.id);
                            this.fileList.splice(i, 1);
                            return true;
                        }
                    }
                    return false;
                },
                removeAllFile : function() {
                    this.cancelAllUpload();
                    fileList.length = 0;
                },
                upload : function(id, url, data, resultCallback, progressCallback) {
                    url = url || _file_upload_url;
                    var that = this
                    var fileInfo = this.findFile(id);
                    if (!fileInfo)
                        return false;
                    if (fileInfo.jqXHR) {
                        return false;
                    }
                    fileInfo.resultCallback = resultCallback;
                    fileInfo.progressCallback = progressCallback;
                    var result = false;
                    var resultData = null;
                    var formData = Utilities.mapToFormData(data);
                    formData.append("uploadFile", fileInfo.file, fileInfo.file.name);

                    $.ajax({
                        xhr : function() {
                            var xhr = new window.XMLHttpRequest();
                            fileInfo.jqXHR = xhr;
                            xhr.upload.addEventListener("progress", function(evt) {
                                if (evt.lengthComputable) {
                                    var percentComplete = evt.loaded / evt.total;
                                    that.onUploadProgress(fileInfo, evt.loaded, evt.total, percentComplete);
                                }
                            }, false);
                            return xhr;
                        },
                        type : 'POST',
                        url : url,
                        data : formData,
                        processData : false,
                        contentType : false,
                        success : function(data, textStatus, jqXHR) {
                            fileInfo.uploaded = true;
                            result = true;
                            resultData = data;
                        },
                        error : function(jqXHR, textStatus, errorThrown) {
                            fileInfo.uploaded = false;
                        },
                        complete : function(jqXHR, textStatus) {
                            fileInfo.jqXHR = null;
                            that.onUploadComplete(fileInfo, resultData, result);
                        }
                    });
                },
                cancelUpload : function(data) {
                    if (!data)
                        return;
                    if (typeof (data) != typeof ({}))
                        data = this.findFile(data);
                    if (data && data.jqXHR) {
                        data.jqXHR.abort();
                        data.jqXHR = null;
                    }
                },
                cancelAllUpload : function() {
                    for (var i = 0; i < this.fileList.length; i++) {
                        this.cancelUpload(this.fileList[i]);
                    }
                },
                findFile : function(id) {
                    for (var i = 0; i < this.fileList.length; i++) {
                        if (this.fileList[i].id == id)
                            return this.fileList[i];
                    }
                    return null;
                },
                onAddFile : function(e, data) {

                    data.id = ++this.currentId;
                    this.fileList.push(data);
                    if (this.options.addCallback) {
                        this.options.addCallback(e, data,this);
                    }
                },
                onChange : function(e, data) {
                    return true;
                },
                onUploadProgress : function(fileInfo, loaded, total, percent) {
                    // resultCallback, progressCallback
                    if (fileInfo.progressCallback) {
                        fileInfo.progressCallback(fileInfo.id, loaded, total, percent);
                    }
                },
                onUploadComplete : function(fileInfo, resultData, result) {
                    if (fileInfo.uploaded) {
                        this.removeFile(fileInfo.id);
                    }
                    if (fileInfo.resultCallback) {
                        fileInfo.resultCallback(fileInfo.id, resultData, result);
                    }
                    fileInfo.progressCallback = null;
                    fileInfo.resultCallback = null;
                },
                createUploader : function(options) {
                    this.options = options;
                    var multi = !options.singleFile;
                    
                    var accept = "";
                    if(options.acceptTypes){
                        var arr = options.acceptTypes.split(",");
                        for(var i=0;i<arr.length;i++){
                            if(accept)
                                accept +=",";
                            accept += arr[i]+"/*";
                        }
                    }
                    if(options.acceptExt){
                        var arr = options.acceptExt.split(",");
                        for(var i=0;i<arr.length;i++){
                            if(accept)
                                accept +=",";
                            accept +="." + arr[i];
                        }
                    }
                    if(accept)
                        accept = "accept='"+accept+"'";
                    var mHtml = "multiple";
                    if(!multi)
                        mHtml = ""
                    var inputUpload = $('<input id="fileupload_' + this.id + '" type="file" '+accept+' name="files" '+mHtml+' data-sequential-uploads="true" style="display:none"/>');
                    this.inputUpload = inputUpload;
                    var that = this;

                    inputUpload.change(function(e) {
                        var list = [];
                        for (var i = 0; i < e.target.files.length; i++) {
                            var data = {
                                file : e.target.files[i],
                                fileList : e.target.files
                            };
                            if (!that.onChange(e, data))
                                return false;
                            that.onAddFile(e, data);
                        }

                    });
                    return this;
                }
            };
            return uploader.createUploader(options);
        },
        blockUI : function() {
			if(top == window)
        		$.blockUI({message : '<img src="/static/images/loader.gif" />' ,overlayCSS: { opacity:0.2 } ,css : {border: 'none', background: 'rgba(255, 255, 255,0)'}});
        	else
        		top.Utilities.blockUI();
        },
        unblockUI : function() {
			if(top == window)
            	$.unblockUI();
            else
            	top.Utilities.unblockUI();
        },
        numberWithCommas : function (x) {
            return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        },
        sortList : function (list,textKey,callback){
            window["sortList"]={
                    list : list,
                    textKey : textKey
            }
            Utilities.openModal("/sort",800,600);
        },
        textDialog : function (text,title,readonly){
            window["textDialog"]={
                    text : text
                    ,title : title
                    , readonly : !!readonly
            }
            Utilities.openModal("/sort/textDialog",1000,500);
        },
        getDownloadUrl : function(data){
            if(!data || !data.fileUrl)
                return null;
           if(data.fileUrl.toUpperCase().indexOf("HTTP")>-1)
            return data.fileUrl;

            var url = _s3_download_url + data.fileUrl;
            return url;
        },
        setComboData : function(element,code,codeType){
        	if(!codeType)
        		codeType = "";
        	element = $(element);
        	let comboData = element.data();
        	codeType = codeType ||  comboData.codeType || "";
        	element.html("");
        	let url = comboData.url || window["_CODE_URL"] || "/util/commCode/getComboCode";
        	
        	var param = {codeCd : code ,codeType : codeType};
        	Utilities.getAjax(url, param, true, function(list,jqXHR){
        		if(Utilities.processResult(list,jqXHR,"콤보데이터를 가져올 수 없습니다.")){
					if(!code && !codeType){
						list = [];
					}
        			if(comboData.label){
        				let vl = comboData.labelValue ? comboData.labelValue : "";  
        				let opt =$('<option value="'+vl+'">'+comboData.label+'</option>');
        				element.append(opt);
        			}
        			for(var i=0;i<list.length;i++){
        				let cData = list[i];
        				let opt =$('<option value="'+(cData.codeCd || cData.comnCd)+'">'+(cData.codeNm || cData.comnCdNm)+'</option>');
        				opt.data(cData);
        				element.append(opt);
        			}
        			let defaultValue = comboData.defaultValue ? comboData.defaultValue : "";
        			if(defaultValue)
        				element.val(defaultValue);
        			else
        				element.prop("selectedIndex",0);
        			
        		}
        	});
        	
        },
        getFormattedJson : function(jsonStr){
            try{
                    var json = JSON.parse(jsonStr);
                    return JSON.stringify(json, null, 4);
//                    return JSON.stringify(json, null, 4).replaceAll("\\n","\n").replaceAll("\\t","    ");
                }catch(e){
                    return jsonStr;
                }
        }
        
    };
}

function makeAction(element) {
    var selector = null;

    if (element) {
        selector = $(element).find('input[data-check-all]');

    } else
        selector = $('input[data-check-all]');

    selector.click(function(e) {
        var name = $(this).attr("data-check-all");
        var checked = this.checked;
        $("input[type=checkbox][data-check-id=" + name + "]").each(function() {
            this.checked = checked;
        });
    });

    if (element) {
        selector = $(element).find('[data-change]');
        if(element.attr("data-change"))
            selector.push(element);
    } else
        selector = $('[data-change]');

    selector.each(function() {
        var action = $(this).attr("data-change");
        if (action) {
            $(this).change(function(e) {
                e.preventDefault();
                try {
                    if (window[action] && typeof window[action] === "function")
                        window[action]($(this), $(this).data());

                } catch (e) {
                    console.error(e);
                }
            });
        }
    });

    // --------------------------------------------------------------
    if (element) {
        selector = $(element).find('[data-click]');
        if(element.attr("data-click"))
            selector.push(selector);
    }

    else
        selector = $('[data-click]');

    selector.each(function() {
        var action = $(this).attr("data-click");
        if (action) {
            $(this).css("cursor", "pointer");
            $(this).click(function(e) {
                e.preventDefault();
                try {
                    if (window[action] && typeof window[action] === "function")
                        window[action]($(this), $(this).data());

                } catch (e) {
                    console.error(e);
                }
            });
        }
    });

    if (element) {
        selector = $(element).find('[data-enter]');
        if(element.attr("data-enter"))
        selector.push(selector);
    } else
        selector = $('[data-enter]');

    selector.each(function() {
        var action = $(this).attr("data-enter");
        if (action) {
            $(this).keydown(function(e) {
                try {
                    if (e.which == 13) {
                        e.preventDefault();
                        if (window[action] && typeof window[action] === "function")
                            window[action]($(this), $(this).data());

                    }
                } catch (e) {
                    console.error(e);
                }
            });
        }
    });

//    if (element) {
//        selector = $(element).find('[data-change]');
//        if(element.attr("data-change"))
//            selector.push(selector);
//    } else
//        selector = $('[data-change]');
//
//    selector.each(function() {
//        var action = $(this).attr("data-change");
//        if (action) {
//            $(this).change(function(e) {
//
//                if (window[action] && typeof window[action] === "function") {
//                    e.preventDefault();
//                    window[action]($(this), $(this).data());
//                }
//            });
//        }
//    });

    // 달력 시작
    if (element) {
        selector = $(element).find('[data-type=date]');
        if(element.attr("data-type")=="date")
            selector.push(element);
    } else
        selector = $('[data-type=date]');

    selector.each(function() {
//      $(this ).datepicker({
//                  dateFormat: "yy-mm-dd",
//                  changeMonth: true,
//                  changeYear: true,
//                  showButtonPanel: true,
//                  dayNamesMin: [ "일", "월", "화", "수", "목", "금", "토" ],
//                  showMonthAfterYear: true,
//                  monthNamesShort : [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ],
//                  monthNames : [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ],
//                  yearRange: "-150:+150",
//
//              });


        var element = $(this);
        if (element.attr("data-type") != "date")
            return;
        var val = element.val();
        element.wrap('<div class="tui-datepicker-input tui-datetime-input tui-has-focus"></div>');
        var divParent = element.parent();
        var ico = $('<span class="tui-ico-date"></span>');
        divParent.append(ico);

        var divPick = divParent.wrap('<div></div>').parent();
        var wrapper = $('<div style="margin-top: -1px;"></div>');
        divPick.append(wrapper);

        var datepicker = new tui.DatePicker(wrapper[0], {
            date : new Date(),
            input : {
                element : element[0],
                format : 'yyyyMMdd',
                syncFromInput : true
            },
            type : 'date',
            language : "ko",
            syncFromInput : true,
            openers : [ico[0]]
        });
        element.val(val);
        datepicker._onChangeInput();
        
    });
    
    // 달력(날짜 시간) 시작
    if (element) {
        selector = $(element).find('[data-type=datetime]');
        if(element.attr("data-type")=="datetime")
        selector.push(element);
    } else
        selector = $('[data-type=datetime]');
    
    selector.each(function() {
        var element = $(this);
        
        if (element.attr("data-type") != "datetime")
            return;
//     element.inputmask("datetime", {inputFormat: "yyyy-mm-dd HH:MM:ss",placeholder: '____-__-__ __:__:__'});
//  
//      var val = element.val();
//      element.wrap('<div class="tui-datepicker-input tui-datetime-input tui-has-focus"></div>');
//      var divParent = element.parent();
//      var ico = $('<span class="tui-ico-date"></span>');
//      divParent.append(ico);
//
//      var divPick = divParent.wrap('<div></div>');
//      var wrapper = $('<div style="margin-top: -1px;"></div>');
//      divPick.append(wrapper);
//
//      var datepicker = new tui.DatePicker(wrapper[0], {
//          date : new Date(),
//          input : {
//              element : element[0],
//              format: 'yyyyMMddHHmm',
//              syncFromInput : true
//          },
//          timePicker: {
//              showMeridiem: false,
//              inputType: 'spinbox'
//              },
//          language : "ko",
//          openers : [ico[0]]
//      });
//      element.val(val);
//      datepicker._onChangeInput();
    });
    
    
    
    // 달력(날짜 월) 시작
    if (element) {
        selector = $(element).find('[data-type=month]');
        if(element.attr("data-type")=="month")
            selector.push(element);
    } else
        selector = $('[data-type=month]');
    
    selector.each(function() {
        var element = $(this);
        if (element.attr("data-type") != "month")
            return;
        var val = element.val();
        element.wrap('<div class="tui-datepicker-input tui-datetime-input tui-has-focus"></div>');
        var divParent = element.parent();
        var ico = $('<span class="tui-ico-date"></span>');
        divParent.append(ico);

        divParent.wrap('<div></div>');
        var divPick = divParent.parent();
        var wrapper = $('<div style="margin-top: -1px;"></div>');
        divPick.append(wrapper);

        var datepicker = new tui.DatePicker(wrapper[0], {
            date : new Date(),
            input : {
                element : element[0],
                format: 'yyyyMM'
            },
            type: 'month',
            language : "ko",
            openers : [ico[0]]
        });
        element.val(val);
        datepicker._onChangeInput();
    });
    
    
    
    
    
    // 달력(날짜 년도) 시작
    if (element) {
        selector = $(element).find('[data-type=year]');
        if(element.attr("data-type")=="year")
            selector.push(element);
    } else
        selector = $('[data-type=year]');
    
    selector.each(function() {
        var element = $(this);
        if (element.attr("data-type") != "year")
            return;
        var val = element.val();
        element.wrap('<div class="tui-datepicker-input tui-datetime-input tui-has-focus"></div>');
        var divParent = element.parent();
        var ico = $('<span class="tui-ico-date"></span>');
        divParent.append(ico);
        
        var divPick = divParent.wrap('<div></div>');
        var wrapper = $('<div style="margin-top: -1px;"></div>');
        divPick.append(wrapper);
        
        var datepicker = new tui.DatePicker(wrapper[0], {
            input : {
                element : element[0],
                format: 'yyyy',
                syncFromInput : true
            },
            type: 'year',
            language : "ko",
            openers : [ico[0]]
        });
        element.val(val);
        datepicker._onChangeInput();
    });
    
    
    // 달력(날짜 년도) 시작
    if (element) {
        selector = $(element).find('[data-type=dateRangeStart]');
        if(element.attr("data-type")=="dateRangeStart")
            selector.push(element);
    } else
        selector = $('[data-type=dateRangeStart]');
    
    selector.each(function() {
        var elementStart = $(this);
        if (elementStart.attr("data-type") != "dateRangeStart")
            return;
        
        var elementEnd = $("#"+elementStart.attr("data-range-end"));
        if(elementEnd.length ==0)
            return;
            
        
        var valStart = elementStart.val();
        var valEnd  = elementEnd.val();
        
        elementStart.wrap('<div class="tui-datepicker-input tui-datetime-input tui-has-focus"></div>');
        var divParentStart = elementStart.parent();
        var icoStart = $('<span class="tui-ico-date"></span>');
        divParentStart.append(icoStart);

        //var divParentStart = divParentStart.wrap('<div></div>');
        var wrapperStart = $('<div style="margin-top: -1px;"></div>');
        divParentStart.parent().append(wrapperStart);
        

        
        elementEnd.wrap('<div class="tui-datepicker-input tui-datetime-input tui-has-focus"></div>');
        var divParentEnd = elementEnd.parent();
        var icoEnd = $('<span class="tui-ico-date"></span>');
        divParentEnd.append(icoEnd);

        //var divParentEnd = divParentEnd.wrap('<div></div>');
        var wrapperEnd = $('<div style="margin-top: -1px;"></div>');
        divParentEnd.parent().append(wrapperEnd);
        
        var endDate = null;//new Date();
        var startDate = null;//new Date();
        
        if(valStart)
        {
            elementStart.val(valStart);
            startDate = new Date();
            startDate.setFullYear(parseInt(valStart.substring(0,4)));
            startDate.setMonth(parseInt(valStart.substring(4,6))-1);
            startDate.setDate(parseInt(valStart.substring(6,8)));
        }   
        
        if(valEnd)
        {
            elementEnd.val(valEnd);
            endDate = new Date();
            endDate.setFullYear(parseInt(valEnd.substring(0,4)));
            endDate.setMonth(parseInt(valEnd.substring(4,6))-1);
            endDate.setDate(parseInt(valEnd.substring(6,8)));
        }   
//      startDate.setDate(1);
        var picker = tui.DatePicker.createRangePicker({
            startpicker: {
                date: startDate,
                input: elementStart[0],
                format : 'yyyyMMdd',
                container: wrapperStart[0],
                openers : [icoStart[0]]
            },
            endpicker: {
                date: endDate,
                input: elementEnd[0],
                format : 'yyyyMMdd',
                container: wrapperEnd[0],
                openers : [icoEnd[0]]
            },
            format : 'yyyyMMdd',
            language : "ko"
        });
        elementStart[0].picker = picker;
        elementEnd[0].picker = picker;
        elementStart.change(function(){
			if(this.picker)
			{
				if(this.value)
					this.picker.getStartpicker()._onChangeInput();
				else
					this.picker.getStartpicker().setNull();
			}	
		});
		elementEnd.change(function(){
			if(this.picker)
			{
				if(this.value)
					this.picker.getEndpicker()._onChangeInput();
				else
					this.picker.getEndpicker().setNull();
			}	
		});
        
        picker.getStartpicker()._onChangeInput();
        picker.getEndpicker()._onChangeInput();
    });
    
    
    if (element) {
        selector = $(element).find('[data-btn-type=reset]');

    } else
        selector = $('[data-btn-type="reset"]');

//리셋버튼
    selector.click(function() {
        var $this = $(this);
        // 1. 부모 폼을 찾는다
        var frm = $this.parents("form");
        //2 못찾으면
        if(frm.length == 0){
            //속성의 폼아이디를 찾는다 
            var formId = $this.attr("data-form-id");
            //속성의 폼아이디가 있으면 폼선택
            if(formId)
                frm = $("form#"+formId);
            //폼이 없으면
            if(frm.length == 0){
                //속성의 폼이름 
                var formName = $this.attr("data-form-name");
                //폼이름이 있으면 폼설정
                if(formName)
                    frm = $("form[name='"+formName+"']");
            }
            
        }
        //폼을 찾지 못하면 리턴
        if(frm.length == 0)
            return;
            
        //폼초기화
        frm[0].reset();
    });
    
    
    if (element) {
        selector = $(element).find('[data-btn-type=closeModal]');

    } else
        selector = $('[data-btn-type="closeModal"]');

//리셋버튼
    selector.click(function() {
        Utilities.closeModal();
    });
    
    
    
    if (element) {
        selector = $(element).find('[data-type=multitext]');

    } else
        selector = $('[data-type="multitext"]');
    
    selector.each(function() {
        var option = {
            plugins: ['remove_button'],
                        delimiter: ',',
                        persist: false,
                        create: function(input) {
                            return {
                                value: input,
                                text: input
                            }
                        }
        };
        var maxCnt = $(this).attr("data-max-cnt");
        if(maxCnt > 0)
            option.maxItems = maxCnt;

        $(this).selectize(option);
        
        
    }); 
    
    
    
    
    
    
    
    
    if (element) {
        selector = $(element).find('[data-type=int]');

    } else
        selector = $('[data-type=int]');
    
    selector.each(function() {
        $(this).inputmask("numeric", {
        digits: 0, // default: "*", 소수 크기 정의
        rightAlign: false,
         inputtype: "text"

        
        });
    }); 
    
    
    if (element) {
        selector = $(element).find('[data-type=number]');

    } else
        selector = $('[data-type=number]');
    
    selector.each(function() {
		var num = parseInt($(this).data("digits"));
		if(!num)
			num = 0;
		var opt = { rightAlign: false,
         inputtype: "text" };
         if(num)
         	opt.digits = num;
        $(this).inputmask("numeric", opt);
    }); 
    
    
    if (element) {
        selector = $(element).find('select[multiple]');

    } else
        selector = $('select[multiple]');
    
    selector.multiselect({
		selectedText : function(numChecked, inputCount,checkedItems){
			var val = [];//$(this.element).val();
			for(var i=0;i<checkedItems.length;i++){
				var value = $(checkedItems[i]).attr("value");
				var text = $(this.element).find("option[value="+value+"]").text();
				val.push(text);
			}
			return val.join("/");
		}
	}); 
    
    if(element){
    	selector = $(element).find('select[data-up-combo]');
    }else{
    	selector = $('select[data-up-combo]');
    }
    
    selector.each(function(){
    	var upId = $(this).attr("data-up-combo");
    	var childCombo = $(this);
    	
    	var upCombo = $("select#"+upId);
    	upCombo.change(function(){
    		var value = $(this).val();
    		Utilities.setComboData(childCombo,value);
    	});
    	upCombo.change();
    });
    
    if (element) {
        selector = $(element).find('[data-text-type=json]');

    } else
        selector = $('[data-text-type=json]');
    
    selector.each(function(){
        var text = $(this).val();
        text = Utilities.getFormattedJson(text);
        $(this).val(text);
    });
    
    
    
     
    if (element) {
        selector = $(element).find('[data-type=select2]');
//        selector.push($(element)[0]);

    } else
        selector = $('[data-type=select2]');
    
    selector.each(function() {
        $(this).select2();
    }); 
    
    
    if (element) {
        selector = $(element).find('[data-type=autocomplete]');
        selector.push($(element)[0]);

    } else
        selector = $('[data-type=autocomplete]');
    
    
    $.ui.autocomplete.prototype._renderItem = function (ul, item) {
    const lbl = item.label.replace(new RegExp("(?![^&;]+;)(?!<[^<>]*)(" + $.ui.autocomplete.escapeRegex(this.term) + ")(?![^<>]*>)(?![^&;]+;)", "gi"), "<strong style='text-decoration:underline'>$1</strong>");
    return $('<li class="ui-menu-item"></li>')
            .data("item.autocomplete", item)
            .append("<div class='ui-menu-item-wrapper'>" + lbl + "</div>")
            .appendTo(ul);
	};


    selector.each(function() {
		const el = $(this);
		let $data = el.data();
		let sUrl = $data.url;
		
		
		if(sUrl)
		{
			let labelName = $data.labelName || "label";
			let valueName = $data.valueName || "value";
			let paramName = $data.paramName || "keyword";
			let $callback = $data.callback;
			if(sUrl.indexOf("?")>-1)
				sUrl = sUrl + "&" + paramName + "=";
			else
				sUrl = sUrl + "?" + paramName + "=";

			el.autocomplete({
				source : function( request, response ) {
					const url = sUrl + encodeURIComponent(el.val()); 
					$.ajax({
	                        type: 'get',
	                        url: url,
	                        dataType: "json",
	                        success: function(data) {
	                            response(
	                                $.map(data, function(item) {    
										if(typeof item == 'string'){
											return {
												label : item,
												value : item
											}
										}
										
										let dt = {
											label: item[labelName],    
	                                        value: item[valueName]   };
										$.extend(dt,item);
										
	                                    return dt;
	                                })
	                            );
	                        }
	                   });
				},
				select : function(event, ui) {    //아이템 선택시
	                if($callback  && window[$callback ]){
						try{
							window[$callback ](ui.item,ui.item.label,ui.item.value,ui)
							}catch(e){console.error(e)}
//						el.val(ui.item.label);
						return false;
					}
	                
	            },
	            focus : function(event, ui) {    //포커스 가면
	            	el.val(ui.item.label);
	                return false;//한글 에러 잡기용도로 사용됨
	            },
	            minLength: 1,// 최소 글자수
	            autoFocus: true, //첫번째 항목 자동 포커스 기본값 false
	            classes: {    //잘 모르겠음
	                "ui-autocomplete": "highlight"
	            },
	            delay: 500,    //검색창에 글자 써지고 나서 autocomplete 창 뜰 때 까지 딜레이 시간(ms)
	//            disabled: true, //자동완성 기능 끄기
	            position: { my : "right top", at: "right bottom" },    //잘 모르겠음
	            close : function(event){    //자동완성창 닫아질때 호출
	            }
			});
		}	
		
        
        
    }); 
    
    
    
    var $gLeft = $("div.gLeft");
    var $gRight = $("div.gRight");
    if($gLeft.length==1 && $gRight.length == 1 && $gLeft.attr("data-has-size")!="N"){
		var gLeftWidth = $gLeft.width();
		var gRigthMargin = $gRight.css("margin-left");
		var btnWdth = 12;
//		var gRigthMarginNum = parseInt(gRigthMargin);
		var btnClose = $('<button class="btnClose">닫기</button>');
		var btnWide = $('<button class="btnWide">크게보기</button>');
		$gLeft.append(btnClose);
		$gLeft.append(btnWide);
		
		btnClose.click(function(){
			var mode = 2;
			if(btnClose.css("visibility") == "hidden")
				mode = 1;
			else if(btnWide.css("visibility")=="hidden")
				mode = 3;
			//move to 1
			if(mode ==2){
				btnClose.css("visibility","hidden");
				$gLeft.css("left","-"+(gLeftWidth-btnWdth)+"px");
				$gLeft.find(".mBox1").css("visibility","hidden");
				$gRight.css("margin-left",btnWdth + "px");
			}	
			//move to 2
			else{
				btnClose.css("visibility","");
				btnWide.css("visibility","");
				$gLeft.css("left","0px");
				$gLeft.css("width", gLeftWidth + "px");
				$gLeft.find(".mBox1").css("visibility","");
				$gRight.css("margin-left",gRigthMargin);
			} 

			return false;
		});
		btnWide.click(function(){
			var mode = 2;
			if(btnClose.css("visibility") == "hidden")
				mode = 1;
			else if(btnWide.css("visibility")=="hidden")
				mode = 3;
			//move to 1
			if(mode ==2){
				$gLeft.css("width","100%");
				btnWide.css("visibility","hidden");
			}	
			else{
				btnClose.css("visibility","");
				$gLeft.css("left","0px");
				$gLeft.find(".mBox1").css("visibility","");
				$gRight.css("margin-left",gRigthMargin);
			} 

				
			return false;
		});
		
	}
}
var _CURFILEDATA = null;
function onEzFileUploaderAddFile(e, data) {
    var fileCd = _CURFILEDATA.fileCd;
    var fileSeq = _CURFILEDATA.fileSeq;
    _CURFILEDATA.id = data.id;
    var file = data.file;
    var fileExt = Utilities.getFileExt(file.name);
    var fileInfo = {
        fileCd : fileCd,
        fileSeq : fileSeq,
        fileNm : file.name,
        fileSize : file.size,
        mimeType : file.type,
        saveNm : null,
        id : data.id,
        fileExt : fileExt,
        stat : 'c'
    };
    $("[data-file-info=remove][data-file-cd="+fileCd+"][data-file-seq="+fileSeq+"]").hide();
    $("[data-file-info=download][data-file-cd="+fileCd+"][data-file-seq="+fileSeq+"]").hide();
    $("[data-file-info=button][data-file-cd="+fileCd+"][data-file-seq="+fileSeq+"]").hide();
    $("[data-file-info=cancel][data-file-cd="+fileCd+"][data-file-seq="+fileSeq+"]").show();

    var fileCategory = _CURFILEDATA.fileCategory?_CURFILEDATA.fileCategory : "";
    var url = _basePath + "/file/uploadFile"+_urlSuffix+"?fileCtgryCd="+fileCategory;
    $("[data-file-info=info][data-file-cd="+fileCd+"][data-file-seq="+fileSeq+"]").html("전송준비중");
    ezFileUploader.upload(data.id, url, fileInfo
     , function(id, resultData, result){
        
        
        $("[data-file-info=button][data-file-cd="+fileCd+"][data-file-seq="+fileSeq+"]").show();
        $("[data-file-info=cancel][data-file-cd="+fileCd+"][data-file-seq="+fileSeq+"]").hide();
        
        
        if(result)
        {
            $("[data-file-info=info][data-file-cd="+fileCd+"][data-file-seq="+fileSeq+"]").html(resultData.fileNm);
            $("[data-file-info=remove][data-file-cd="+fileCd+"][data-file-seq="+fileSeq+"]").show();
            $("[data-file-info=download][data-file-cd="+fileCd+"][data-file-seq="+fileSeq+"]").show();
            $("[data-file-info=download][data-file-cd="+fileCd+"][data-file-seq="+fileSeq+"]").data("fileUrl",resultData.fileUrl);
            $("[data-file-info=download][data-file-cd="+fileCd+"][data-file-seq="+fileSeq+"]").attr("fileUrl",resultData.fileUrl);
            $("[data-file-info=download][data-file-cd="+fileCd+"][data-file-seq="+fileSeq+"]").data("fileNm",resultData.fileNm);
            $("[data-file-info=download][data-file-cd="+fileCd+"][data-file-seq="+fileSeq+"]").attr("fileNm",resultData.fileNm);
            var el = $("[data-click=onFileInfo][data-file-cd="+fileCd+"][data-file-seq="+fileSeq+"]");
            if(el.length){
                var dt = $(el[0]).data();
                if(dt.uploadCallback){
                        try{
                            window[dt.uploadCallback](resultData);  
                        }catch(e){
                            
                        }
                                            
                    }   
            }
            
        }   
        else
        {
            $("[data-file-info=info][data-file-cd="+fileCd+"][data-file-seq="+fileSeq+"]").html(_CURFILEDATA.orgFileInfo);
            if(_CURFILEDATA.orgFileInfo){
                $("[data-file-info=remove][data-file-cd="+fileCd+"][data-file-seq="+fileSeq+"]").show();
                $("[data-file-info=download][data-file-cd="+fileCd+"][data-file-seq="+fileSeq+"]").show();
            }
        }
        
        ezFileUploader.removeFile(_CURFILEDATA.id);
        _CURFILEDATA = null;
        

    }, function(id, loaded, total, percent) {
        var per = parseInt(percent*100);
        var info = "";
        if(total == loaded)
            info = "파일 처리중";
        else 
            info = Utilities.numberWithCommas(parseInt(loaded/1024)) + "KB/ "  + Utilities.numberWithCommas(parseInt(total/1024)) + "KB("+ per +"%)";
        $("[data-file-info=info][data-file-cd="+fileCd+"][data-file-seq="+fileSeq+"]").html(info);
//      console.log("["+id+"]["+loaded+"]["+total+"]["+percent+"]");
    });
    $("[data-file-info=button][data-file-cd="+fileCd+"][data-file-seq="+fileSeq+"]").html("첨부");
    
}
function onFileInfo(el,data){
    _CURFILEDATA = data;
    _CURFILEDATA.orgFileInfo = $("[data-file-info=info][data-file-cd="+data.fileCd+"][data-file-seq="+data.fileSeq+"]").html();
    if(data.fileSeq>0)
        ezSingFileUploader(el,data);
    else
        ezMultiFileUploader(el,data);
        
}
function onCancelFileInfo(el,data){
    if(_CURFILEDATA)
    {
        ezFileUploader.cancelUpload(_CURFILEDATA.id);
        ezFileUploader = null;
    }
    
}
function onDownloadFileInfo(el,data){
    if(data.fileUrl){
        Utilities.downloadFileUrl(data.fileUrl,data.fileNm);
    }
    
}
function onRemoveFileInfo(el,data){
    if(!confirm('한번 삭제한 파일은 복구하지 못합니다.\n계속 하시겠습니까?'))
        return;
var removeUrl = _basePath + "/file/removeFileInfo"+_urlSuffix;
            var fileCd = data.fileCd;
            var fileSeq = data.fileSeq;
                var param = {
                fileCd : fileCd+"",
                fileSeq :fileSeq    
                };
                Utilities.getAjax(removeUrl,param,true,function(result,jqXHR){
                    if(Utilities.processResult(result,jqXHR,"파일정보 삭제에 실패했습니다.")){
                        alert("파일 삭제에 성공했습니다.");
                            $("[data-file-info=remove][data-file-cd="+fileCd+"][data-file-seq="+fileSeq+"]").hide();
                            $("[data-file-info=download][data-file-cd="+fileCd+"][data-file-seq="+fileSeq+"]").hide();
                            $("[data-file-info=download][data-file-cd="+fileCd+"][data-file-seq="+fileSeq+"]").data("fileUrl","");
                            $("[data-file-info=download][data-file-cd="+fileCd+"][data-file-seq="+fileSeq+"]").attr("fileUrl","");
                            $("[data-file-info=download][data-file-cd="+fileCd+"][data-file-seq="+fileSeq+"]").data("fileNm","");
                            $("[data-file-info=download][data-file-cd="+fileCd+"][data-file-seq="+fileSeq+"]").attr("fileNm","");
                            $("[data-file-info=info][data-file-cd="+fileCd+"][data-file-seq="+fileSeq+"]").html("첨부없음");
                            if(data.removeCallback){
                                try{
                                    window[data.removeCallback](data);  
                                }catch(e){
                                    
                                }
                                
                            }
                    }
                });
}
function ezSingFileUploader(el,data){
        
//  if(!window["ezFileUploader"]){
        data.singleFile = true;
        data.addCallback = onEzFileUploaderAddFile;
        window["ezFileUploader"] = Utilities.getFileUploader(data);
//  }
    
    ezFileUploader.addFile();
}
function ezMultiFileUploader(el,data){
    
    if(!window["onFileClose"]){
        window["onFileClose"] = function(fileCd,fileList,fileInfo){
            $("[data-file-info=info][data-file-cd="+fileCd+"]").html(fileInfo);
        };
    }

    if(window["showFileInfo"])
        showFileInfo(el,data);
    else
        openFileModal(data.fileCd,data.fileCategory);
        
}
$(document).ready(function(e) {
    makeAction();
});