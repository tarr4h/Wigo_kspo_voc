<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="code" uri="/WEB-INF/tlds/ezTagLib.tld"%>

<div class="mBox1">
 	<div class="gTitle1">
								<h3 class="mTitle1">테이블 정보</h3>
								<div class="gRt">
								</div>
			</div>
			<div class="mBoard2">
								<table>
									<caption>기본정보</caption>
									<colgroup>
										<col width="10%" />
										<col width="25%" />
										<col width="10%" />
										<col width="20%"  />
										<col width="35%" />
									</colgroup>
									<tbody>
										<tr>
											<th scope="row" class="left"><span class="iMust">package 명</span></th>
											<td>
												<input type="text" name="packageName" id="packageName" class="it" placeholder="package name" value="com.wigo.crm.example">
											</td>
											<th scope="row" class="left">테이블명</th>
											<td>
												<input type="text" name="tableName" id="tableName" class="it" placeholder="table name">
											</td>
											
											<th scope="row" class="left">
											
											<a href="#;" class="mBtn1 m lWhite" id="btnAdd" data-click="addColumn">컬럼추가</a>
											<a href="#;" class="mBtn1 m lWhite" id="btnDelete">컬럼삭제</a>
											<a href="#;" class="mBtn1 m lWhite" id="btnSave">Grid</a>
											<a href="#;" class="mBtn1 m lWhite" id="btnDb">Mapper</a>
											<a href="#;" class="mBtn1 m lWhite" id="btnModel">Model</a>
											<a href="#;" class="mBtn1 m lWhite" id="btnDao" data-click="generateDaoXml">Dao</a>
											<a href="#;" class="mBtn1 m lWhite" id="btnService" data-click="generateServiceXml">Service</a>
											<a href="#;" class="mBtn1 m lWhite" id="btnController" data-click="generateControllerXml">Controller</a>
											<a href="#;" class="mBtn1 m lWhite" id="btnSaveAll" data-click="saveAllFiles">전체저장</a>
											
											<a href="#;" class="mBtn1" id="btnSearch">검색</a>
				
				
											</th>
											
										</tr>
										
									</tbody>
								</table>
							</div>
							</div>
							<div class="mBox1 ">
							
							<div class="mBoard2">
							<form id="frmMeta">
					 <input id="deletable" name="deletable" type="hidden" value="N" /> 
					 <input id="rollbackable" name="rollbackable" type="hidden" value="N" /> 
					 <input id="appendable" name="appendable" type="hidden" value="N" />
								<table>
									<caption>기본정보</caption>
									<colgroup>
										<col width="10%" />
										<col width="15%" />
										<col width="10%" />
										<col width="15%"  />
										<col width="10%" />
										<col width="15%"  />
									</colgroup>
									<tbody>
										<tr>
											<th scope="row" class="left">읽기 전용</th>
											<td>
												<select id="readOnly" name="readOnly" class="select"> 
													<option value="Y">Y</option>
				                        			<option value="N" selected>N</option>
				                    			</select>
											</td>
											<th scope="row" class="left">체크박스 표시</th>
											<td>
												<select id="showCheckbox" name="showCheckbox" class="select">
				                        			<option value="Y" selected>Check</option>
				                        			<option value="R">Radio</option>
				                        			<option value="N">N</option>
				                    			</select>
											</td>
											
											<th scope="row" class="left">줄번호보이기</th>
											<td>
												<select id="showNumber" name="showNumber" class="select">
							                        <option value="Y" selected>Y</option>
							                        <option value="N">N</option>
							                   	</select>
											</td>
											<th scope="row" class="left">상태바표시</th>
											<td>
												<select id="showStateBar" name="showStateBar" class="select">
							                        <option value="Y" selected>Y</option>
							                        <option value="N">N</option>
							                    </select>
											</td>
										</tr>
										
										
										
										
										<tr>
											<th scope="row" class="left">줄전체선택</th>
											<td>
												<select id="rowSelection" name="rowSelection" class="select">
							                        <option value="Y">Y</option>
							                        <option value="N" selected>N</option>
							                    </select>
											</td>
											<th scope="row" class="left">컬럼자동크기</th>
<!-- 											<td> -->
<!-- 												<select id="gridfitStyle" name="gridfitStyle" class="select"> -->
<!-- 							                        <option value="none">없음</option> -->
<!-- 							                        <option value="even">비율로 맞춤</option> -->
<!-- 							                    </select> -->
<!-- 											</td> -->
											<td>
												<select id="autoColumnWidth" name="autoColumnWidth" class="select">
							                        <option value="Y">Y</option>
							                        <option value="N">N</option>
							                    </select>
											</td>
											<th scope="row" class="left">컬럼이동가능</th>
											<td>
												<select id="columnMove" name="columnMove" class="select">
							                        <option value="Y">Y</option>
							                        <option value="N" selected>N</option>
							                    </select>
											</td>
											<th scope="row" class="left">컬럼정렬</th>
											<td>
												<select id="setSort" name="setSort" class="select">
							                        <option value="exclusive">컬럼클릭시</option>
							                        <option value="none" selected>없음</option>
							                    </select>
											</td>
										</tr>
									</tbody>
								</table>
							</form>
							</div>
	
	</div> 
	<div class="mBox1 ">
		<div id="divGridList" style="height: 620px"
				data-get-url="<c:url value='${urlPrefix}/getList'/>${urlSuffix}"
				data-grid-id="grdList" data-pagenation="N" data-type="grid"
				data-grid-callback="onGridLoad"
				data-post="Y"
				data-tpl-url="<c:url value='/static/gridTemplate/system/genGrid.xml'/>">
		</div>
	</div>

<script type="text/javascript">
	var cookie = Utilities.getCookie("gengrid.package.name");
	if(cookie)
		$("#packageName").val(cookie)
		
	cookie = Utilities.getCookie("gengrid.table.name");
	if(cookie)
		$("#tableName").val(cookie)
		
	var _NULL_ANN = false;
    var _INDEX_ = 0;
    var _TABLE_INFO = null;
    var _TABLE_NAME = null;
    var _SAVE_FILE_NAME = null;
    var _TABLE_COMMENT = null;
    $("#tableName").keydown(function(key) {
        if (key.keyCode == 13) {//키가 13이면 실행 (엔터는 13)
            searchTable();
        }
    });
    
    $("#packageName").keydown(function(key) {
        if (key.keyCode == 13) {//키가 13이면 실행 (엔터는 13)
            searchTable();
        }
    });
    
    $("#btnSearch").click(function() {
        searchTable();
    });
    $("#btnSave").click(function() {
        generateXml();
    });
    $("#btnDb").click(function() {
        generateDbXml();
    });
    $("#btnModel").click(function() {
    	generateModel();
    });
    
    $("#btnDelete").click(function() {
    	if(!validate())
    	{
    		return;
    	}
    	if(!grdList.getCheckedRows().length);{
    		alert("체크된 컬럼이 없습니다.");
    		return ;
    	}
    	grdList.removeCheckRow();
//         $("input[type=checkbox]:checked").each(function() {
//             var colId = $(this).attr("data-col-id");
//             $("tr[data-col-id=" + colId + "]").remove();
//         });
    });
    $("#copyClip").click(function() {
        $("#printXml").select();
        document.execCommand('copy');
    });
    $("#saveFile").click(function() {
        Utilities.downloadText($("#printXml").val(), _SAVE_FILE_NAME);
    });

    function addColumn() {
    	if(!validate())
    	{
    		return;
    	}
        ++_INDEX_;
        var id = "COLUMN" + _INDEX_;
        var data = {
            columnId : id,
            field : id,
            name : ""
        };
        appendRow(data);

    }
    function searchTable() {
        _TABLE_INFO = null;
        _TABLE_NAME = null;
        var url = "<c:url value='${urlPrefix}/selectColInfo${urlSuffix}'/>";
        var param = {
            tableName : $("#tableName").val()
        };

        if (!param.tableName) {
            alert("테이블명을 입력하세요");
            return false;
        }
        
        Utilities.blockUI();
        Utilities.getAjax(url, param, true, function(data, result) {
        	Utilities.unblockUI();
            if (Utilities.processResult(data, result, "에러발생.")) {
            	Utilities.setCookie("gengrid.package.name",$("#packageName").val(),1000);
            	Utilities.setCookie("gengrid.table.name",$("#tableName").val(),1000);
            	
                _TABLE_INFO = data;
                _TABLE_NAME = param.tableName.toUpperCase();
                parseTable(data);
            }
        });
    }
    function parseTable(list) {
        $("#tbdCols").html("");
        for (var i = 0; i < list.length; i++) {
        	if(i==0)
        		_TABLE_COMMENT = list[i].tblCmt;
            var data = convertColumn(list[i]);
            appendRow(data);
        }
    }
    function convertColumn(col) {
        var data = {
            columnId : col.columnId,
            field : Utilities.convert2CamelCase(col.columnName),
            name : col.comments,
            required : col.nullable == "Y" ? "N" : "Y",
            readOnly : col.pk ? "Y" : "N",
            Pk : col.pk ? "Y" : "N",
            pk : col.pk ? "Y" : "N"
        };
        getColType(col, data);
        return data;
    }
    function getColType(col, data) {
        var dataType = col.dataType.toUpperCase();
        data.defaultValue =$.trim( col.dataDefault);
        if(data.defaultValue == "SYSDATE")
        	data.defaultValue = "";
        data.textAlignment = "left";
        if (col.columnName.indexOf("_YN") > 0) {
            data.type = "checkbox";
            data.textAlignment = "center";
            data.maxLength = col.dataLength;
        } else if (dataType.indexOf("CHAR") > -1 || dataType == "CLOB") {
            data.type = "char";
            if (dataType != "CLOB")
                data.maxLength = col.dataLength;
        } else if (dataType == "NUMBER") {
            data.textAlignment = "right";
            var length = col.dataPrecision;
            var place = col.dataScale;
            data.digit = length - place;
            data.place = place;
            if (place == 0) {
                data.type = "int";
                data.maxLength = length;

            } else {
                data.type = "number";
                data.maxLength = length + 1;
            }
        } else if (dataType == "DATE" || dataType == "TIMESTAMP") {
            data.textAlignment = "center";
            data.type = "date";
        } else
            data.taype = "char";
    }

    function appendRow(data) {
    	grdList.addRow(data);
    	return;
        var tbdCols = $("#tbdCols");
        var tr = $("<tr/>");
        tr.attr("data-col-id", data.columnId);
        tbdCols.append(tr);
        var td = $("<td/>");
        tr.append(td);
        var element = $('<input type="checkbox" />');
        element.attr("data-col-id", data.columnId);
        td.append(element);

        appendColName(tr, data);
        appendColField(tr, data);
        appendColType(tr, data);
        appendColLength(tr, data);
        appendColWidth(tr, data);
        appendColAlign(tr, data);
        appendColRequired(tr, data);
        appendColPk(tr, data);
        appendColReadOnly(tr, data);
        appendColDefault(tr, data);
        appendColDigit(tr, data);
        appendColPlace(tr, data);
        appendColPattern(tr, data);
        appendColCommCode(tr, data);
        appendColHidden(tr, data);

    }
    function appendColName(tr, data) {
        var td = $("<td/>");
        td.attr("data-col-id", data.columnId);
        var element = $('<input type="text"  name="colName"  />');
        element.attr("data-col-id", data.columnId);
        element.attr("data-col-type", "name");
        element.val(data.name);
        td.append(element);
        tr.append(td);
    }
    function appendColField(tr, data) {
        var td = $("<td/>");
        td.attr("data-col-id", data.columnId);
        var element = $('<input type="text"  name="colFiled"  />');
        element.attr("data-col-id", data.columnId);
        element.attr("data-col-type", "field");
        element.val(data.field);
        td.append(element);
        tr.append(td);

    }
    function appendColType(tr, data) {
        var td = $("<td/>");
        td.attr("data-col-id", data.columnId);
        var element = $('<select  name="colType"  />');
        element.attr("data-col-id", data.columnId);
        element.attr("data-col-type", "type");
        var opt1 = $("<option/>");
        opt1.attr("value", "char");
        opt1.html("char");
        var opt2 = $("<option/>");
        opt2.attr("value", "number");
        opt2.html("number");
        var opt3 = $("<option/>");
        opt3.attr("value", "int");
        opt3.html("int");
        var opt4 = $("<option/>");
        opt4.attr("value", "checkbox");
        opt4.html("checkbox");
        var opt5 = $("<option/>");
        opt5.attr("value", "combo");
        opt5.html("combo");
        var opt6 = $("<option/>");
        opt6.attr("value", "date");
        opt6.html("date");
        var opt6_1 = $("<option/>");
        opt6_1.attr("value", "datemonth");
        opt6_1.html("datemonth");
        var opt6_2 = $("<option/>");
        opt6_2.attr("value", "datetime");
        opt6_2.html("datetime");
        var opt6_3 = $("<option/>");
        opt6_3.attr("value", "time");
        opt6_3.html("time");
        
        var opt7 = $("<option/>");
        opt7.attr("value", "button");
        opt7.html("button");
        
        var opt8 = $("<option/>");
        opt8.attr("value", "html");
        opt8.html("html");
       
        var opt9 = $("<option/>");
        opt9.attr("value", "phone");
        opt9.html("phone");

        element.val(data.type);
        element.append(opt1);
        element.append(opt2);
        element.append(opt3);
        element.append(opt4);
        element.append(opt5);
        element.append(opt6);
        element.append(opt6_1);
        element.append(opt6_2);
        element.append(opt6_3);
        element.append(opt7);
        element.append(opt8);
        element.append(opt9);
        element.val(data.type);
        td.append(element);
        tr.append(td);

    }
    function appendColLength(tr, data) {
        var td = $("<td/>");
        td.attr("data-col-id", data.columnId);
        var element = $('<input type="text"  name="colMaxLength"  />');
        element.attr("data-col-id", data.columnId);
        element.attr("data-col-type", "maxLength");
        element.val(data.maxLength);
        td.append(element);
        tr.append(td);
    }
    function appendColWidth(tr, data) {
        var td = $("<td/>");
        td.attr("data-col-id", data.columnId);
        var element = $('<input type="text"  name="colWidth"  />');
        element.attr("data-col-id", data.columnId);
        element.attr("data-col-type", "width");
        element.val(80);
        td.append(element);
        tr.append(td);
    }
    function appendColAlign(tr, data) {
        var td = $("<td/>");
        td.attr("data-col-id", data.columnId);
        var element = $('<select  name="colAlign"  />');
        element.attr("data-col-id", data.columnId);
        element.attr("data-col-type", "textAlignment");

        var opt1 = $("<option/>");
        opt1.attr("value", "left");
        opt1.html("left");
        var opt2 = $("<option/>");
        opt2.attr("value", "center");
        opt2.html("center");
        var opt3 = $("<option/>");
        opt3.attr("value", "right");
        opt3.html("right");

        element.append(opt1);
        element.append(opt2);
        element.append(opt3);

        element.val(data.textAlignment);
        td.append(element);
        tr.append(td);
    }
    function appendColRequired(tr, data) {
        var td = $("<td/>");
        td.attr("data-col-id", data.columnId);
        var element = $('<select  name="colRequired"  />');
        element.attr("data-col-id", data.columnId);
        element.attr("data-col-type", "required");
        var opt1 = $("<option/>");
        opt1.attr("value", "Y");
        opt1.html("Y");
        var opt2 = $("<option/>");
        opt2.attr("value", "N");
        opt2.html("N");

        element.append(opt1);
        element.append(opt2);
        element.val(data.required == "Y" ? "Y" : "N");
        td.append(element);
        tr.append(td);
    }
    function appendColPk(tr, data) {
        var td = $("<td/>");
        td.attr("data-col-id", data.columnId);
        var element = $('<select  name="colPk"  />');
        element.attr("data-col-id", data.columnId);
        element.attr("data-col-type", "Pk");
        var opt1 = $("<option/>");
        opt1.attr("value", "Y");
        opt1.html("Y");
        var opt2 = $("<option/>");
        opt2.attr("value", "");
        opt2.html("N");

        element.append(opt1);
        element.append(opt2);
        element.val(data.readOnly == "Y" ? "Y" : "");
        td.append(element);
        tr.append(td);
    }
    function appendColReadOnly(tr, data) {
        var td = $("<td/>");
        td.attr("data-col-id", data.columnId);
        var element = $('<select  name="colReadOnly"  />');
        element.attr("data-col-id", data.columnId);
        element.attr("data-col-type", "readOnly");
        var opt1 = $("<option/>");
        opt1.attr("value", "Y");
        opt1.html("Y");
        var opt2 = $("<option/>");
        opt2.attr("value", "");
        opt2.html("N");

        element.append(opt1);
        element.append(opt2);
        element.val(data.readOnly == "Y" ? "Y" : "");
        td.append(element);
        tr.append(td);
    }

    function appendColDefault(tr, data) {

        var td = $("<td/>");
        td.attr("data-col-id", data.columnId);
        var element = $('<input type="text"  name="colDefaultValue"  />');
        element.attr("data-col-id", data.columnId);
        element.attr("data-col-type", "defaultValue");
        if (data.defaultValue)
            element.val($.trim(data.defaultValue.replace(/\'/gi, "")));
        td.append(element);
        tr.append(td);
    }
    function appendColDigit(tr, data) {

        var td = $("<td/>");
        td.attr("data-col-id", data.columnId);
        var element = $('<input type="text"  name="colDigit"  />');
        element.attr("data-col-id", data.columnId);
        element.attr("data-col-type", "digit");
        element.val(data.digit);
        td.append(element);
        tr.append(td);
    }
    function appendColPlace(tr, data) {

        var td = $("<td/>");
        td.attr("data-col-id", data.columnId);
        var element = $('<input type="text"  name="colPlace"  />');
        element.attr("data-col-id", data.columnId);
        element.attr("data-col-type", "place");
        element.val(data.place);
        td.append(element);
        tr.append(td);
    }
    function appendColPattern(tr, data) {

        var td = $("<td/>");
        td.attr("data-col-id", data.columnId);
        var element = $('<input type="text"  name="colPattern"  />');
        element.attr("data-col-id", data.columnId);
        element.attr("data-col-type", "pattern");
        element.val(data.pattern);
        td.append(element);
        tr.append(td);
    }
    function appendColCommCode(tr, data) {

        var td = $("<td/>");
        td.attr("data-col-id", data.columnId);
        var element = $('<input type="text"  name="codeId"  />');
        element.attr("data-col-id", data.columnId);
        element.attr("data-col-type", "codeId");
        td.append(element);
        tr.append(td);

//         var td = $("<td/>");
//         td.attr("data-col-id", data.columnId);
//         var element = $('<input type="text"  name="colUpCodeId"  />');
//         element.attr("data-col-id", data.columnId);
//         element.attr("data-col-type", "upCodeId");
//         td.append(element);
//         tr.append(td);

//         var td = $("<td/>");
//         td.attr("data-col-id", data.columnId);
//         var element = $('<input type="text"  name="colCodeLevel"  />');
//         element.attr("data-col-id", data.columnId);
//         element.attr("data-col-type", "codeLevel");
//         td.append(element);
//         tr.append(td);

    }
    function appendColHidden(tr, data) {
        var td = $("<td/>");
        td.attr("data-col-id", data.columnId);
        var element = $('<select  name="colHidden"  />');
        element.attr("data-col-id", data.columnId);
        element.attr("data-col-type", "hidden");
        var opt1 = $("<option/>");
        opt1.attr("value", "Y");
        opt1.html("Y");
        var opt2 = $("<option/>");
        opt2.attr("value", "N");
        opt2.html("N");

        element.append(opt1);
        element.append(opt2);
        element.val("N");
        td.append(element);
        tr.append(td);
    }
    function generateXml(cancelPopup) {
    	if(!validate())
    	{
    		return;
    	}
    	
    	var prfIdx = getPrefixIndex();
        _SAVE_FILE_NAME = Utilities.convert2CamelCase(_TABLE_NAME.substring(prfIdx)) + ".xml";
//         _SAVE_FILE_NAME = _SAVE_FILE_NAME.substring(0,1).toUpperCase() + _SAVE_FILE_NAME.substring(1);
        var doc = $.parseXML('<grid/>');
        var grid = doc.getElementsByTagName("grid")[0];
        var meta = doc.createElement("meta");
        grid.appendChild(meta);
        var metaJson = Utilities.formToMap("frmMeta");
        for (key in metaJson) {
            if (metaJson.hasOwnProperty(key)) {
                elem = doc.createElement(key);
                $(elem).attr("value", metaJson[key]);
                meta.appendChild(elem)
            }
        }
		var list = grdList.getJsonRows();
		var cols = [
			"name",
			"field",
			"type",
			"maxLength",
			"width",
			"textAlignment",
			"required",
			"Pk",
			"readOnly",
			"defaultValue",
			"digit",
			"place",
			"pattern",
			"codeId",
			"hidden"
		]
		for(var i=0;i<list.length;i++){
			var column = doc.createElement("column");
			for(var j=0;j<cols.length;j++){
				var val = list[i][cols[j]];
				$(column).attr(cols[j],val);
			}
			grid.appendChild(column);
		}
//         $("tr[data-col-id]").each(function() {
//             var tr = $(this);

//             var column = doc.createElement("column");
//             tr.find("[data-col-type]").each(function() {
//                 if ($(this).val())
//                     $(column).attr($(this).attr("data-col-type"), $(this).val());
//             });
//             grid.appendChild(column);
//         });

        var gXml = (new XMLSerializer()).serializeToString(grid);

        //      var xml = $(grid.documentElement);
        //      var rootChildXml=$('<root />').append(xml).html();

        var xml = '<?xml version="1.0" encoding="UTF-8" standalone="no"?>\n' + Utilities.formatXml(gXml, "    ");
//         $("#printXml").val(xml);
//         $("#xmlModal").dialog('open');
		if(cancelPopup)
			return xml;
        openPop(xml);
    }
    function paddingRight(str, len) {

        for (var i = str.length; i < len; i++) {
            str += ' ';
        }
        return str;
    }

    function generateDbXml(cancelPopup) {
    	if(!validate())
    	{
    		return;
    	}
    	
    	var prfIdx = getPrefixIndex();
        _SAVE_FILE_NAME = Utilities.convert2CamelCase(_TABLE_NAME).substring(prfIdx)  + "_SqlMapper.xml";
        _SAVE_FILE_NAME = _SAVE_FILE_NAME.substring(0,1).toUpperCase() + _SAVE_FILE_NAME.substring(1);
        var list = _TABLE_INFO;
        var tableName = Utilities.convert2CamelCase(_TABLE_NAME.substring(prfIdx+1));
        var doc = $.parseXML('<mapper/>');
        var mapper = doc.getElementsByTagName("mapper")[0];
        mapper.setAttribute("namespace", getPackageName() + ".dao." + getDaoName());

        var pkCond = doc.createElement("sql");
        pkCond.setAttribute("id", "sqlPkConditions");
        $(pkCond).append(getDbPKCondSql(pkCond));
        mapper.appendChild(pkCond);

        var cols = doc.createElement("sql");
        cols.setAttribute("id", "sqlCols");
        $(cols).append(getColsSql(cols));
        mapper.appendChild(cols);

        var cond = doc.createElement("sql");
        cond.setAttribute("id", "sqlConditions");
        getDbCondSql(cond, doc)
        mapper.appendChild(cond);

        getSelectSql(mapper, doc);
        
        var tblName = Utilities.convert2CamelCase(_TABLE_NAME).substring(prfIdx);

        var ins = doc.createElement("insert");
        ins.setAttribute("id", "insert" /*+ tblName*/);
        $(ins).append("\n        INSERT INTO "+_TABLE_NAME+" (\n");
        var inc = doc.createElement("include");
        $(inc).attr("refid", "sqlCols");
        $(ins).append(inc);
        $(ins).append("\n         ) VALUES (\n");

        for (var i = 0; i < list.length; i++) {
            var col = list[i];
            var column = col.columnName;
            var name = Utilities.convert2CamelCase(col.columnName);
            var prefix = i == 0 ? "               " : "             , ";
            if (name == "regDt" || name == "amdDt")
                $(ins).append(prefix + "SYSDATE\n");
            else
                $(ins).append(prefix + "#" + "{" + name + "}\n");
        }
        $(ins).append("         )\n");
        mapper.appendChild(ins);

        var upd = doc.createElement("update");
        upd.setAttribute("id", "update" /*+ tblName*/);
        $(upd).append("\n       UPDATE " + _TABLE_NAME + "\n");
        var fst = false;
        for (var i = 0; i < list.length; i++) {
            var col = list[i];

            if (col.pk)
                continue;

            var column = col.columnName;
            var name = Utilities.convert2CamelCase(col.columnName);
            if (name == "regDt" || name == "regrId")
                continue;

            var prefix = !fst ? "          SET " : "            , ";
            fst = true;
            var str = prefix + paddingRight(column, 20) + "=         ";
            if (name == "amdDt")
                $(upd).append(str + "SYSDATE\n");
            else
                $(upd).append(str + "#" + "{" + name + "}\n");
        }
        var inc = doc.createElement("include");
        $(inc).attr("refid", "sqlPkConditions");
        upd.appendChild(inc);

        mapper.appendChild(upd);

        var del = doc.createElement("delete");
        del.setAttribute("id", "delete" /*+ tblName*/);
        $(del).append("\n       DELETE FROM " + _TABLE_NAME + "\n");
        var inc = doc.createElement("include");
        $(inc).attr("refid", "sqlPkConditions");
        del.appendChild(inc);
        mapper.appendChild(del);

        var gXml = (new XMLSerializer()).serializeToString(mapper);
        var xml = '<?xml version="1.0" encoding="UTF-8"?>\n<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">\n'
                + Utilities.formatXml(gXml, "    ");
//         $("#printXml").val(xml);
//         $("#xmlModal").dialog('open');
		if(cancelPopup)
			return xml;
        openPop(xml);
    }

    function getDbPKCondSql() {
        var list = _TABLE_INFO;
        var w = "\n        WHERE ";
        var a = "\n          AND ";
        var sql = "";
        for (var i = 0; i < list.length; i++) {
            var col = list[i];
            if (!col.pk)
                continue;
            var col = list[i];
            var column = col.columnName;
            var name = Utilities.convert2CamelCase(col.columnName);
            sql += sql == "" ? w : a;
            sql += paddingRight(column, 18) + "=       " + "#" + "{" + name + "}";
        }
        return sql + "\n";
    }
    function getColsSql() {
        var list = _TABLE_INFO;
        var w = "\n              ";
        var a = "\n            , ";
        var sql = "";
        for (var i = 0; i < list.length; i++) {
            var col = list[i];
            var column = col.columnName;
            var ext = "";
            if(col.comments)
            	ext = col.comments+"        ";
            var code = $('input[name="codeId"][data-col-type="codeId"][data-col-id="'+col.columnId+'"]').val();
            if(code)
                ext += "공통코드 : " + code + "        "
            sql += sql == "" ? w : a;
            sql += column;
            sql += "                    /*"+ext+"*/";
        }
        return sql + "\n";
    }
    function getDbCondSql(cont, doc) {
        var list = _TABLE_INFO;
        $(cont).append("\n        WHERE 1 = 1\n");
        for (var i = 0; i < list.length; i++) {
            var col = list[i];
            var column = col.columnName;
            var name = Utilities.convert2CamelCase(col.columnName);
            if (name == "regrId" || name == "regDt" || name == "amdrId" || name == "amdDt")
                continue;
            var iF = $(doc.createElement("if"));
            iF.attr("test", name + " != null and " + name + " != ''");
            var idx = name.indexOf("Cd");
            if(idx == name.length - 2){
            	choose = $(doc.createElement("choose"));
            	whn = $(doc.createElement("when"));
            	whn.attr("test", name + " instanceof String");
            	$(whn).append("\n          AND " + paddingRight(column, 18) + "=       " + "#" + "{" + name + "}\n");
            	choose.append(whn);

            	otherwise = $(doc.createElement("otherwise"));
            	$(otherwise).append("\n          AND " + paddingRight(column, 18) + "IN \n");
            	feach = $(doc.createElement("foreach"));
            	feach.attr('item','item');
            	feach.attr('index','index');
            	feach.attr('collection',name);
            	feach.attr('open','(');
            	feach.attr('separator',',');
            	feach.attr('close',')');
            	feach.append("#"+"{item}\n")
            	otherwise.append(feach);
            	choose.append(otherwise);
            	
            	 iF.append(choose);
            }
            else {
            	$(iF).append("\n          AND " + paddingRight(column, 18) + "=       " + "#" + "{" + name + "}\n");
             }
            
            $(cont).append(iF);
        }

    }
    function getSelectSql(mapper, doc) {

        var s1 = doc.createElement("select");
        $(s1).attr("id", "selectListCount");
        $(s1).attr("resultType", "int");
        $(s1).append("\n        SELECT COUNT(1)\n");
        $(s1).append("         FROM " + _TABLE_NAME + "\n");
        var inc = doc.createElement("include");
        $(inc).attr("refid", "sqlConditions");
        s1.appendChild(inc);
        mapper.appendChild(s1);

        var s1 = doc.createElement("select");
        $(s1).attr("id", "selectList");
        $(s1).attr("resultType", getModelName(true));

        var inc = doc.createElement("include");
        if(isApiMode())
        	$(inc).attr("refid", "com.wigo.api.crm.dao.CrmCommonDao.pagingHeader");
        else
        	$(inc).attr("refid", "com.wigo.crm.sys.dao.CrmCommonDao.pagingHeader");
        s1.appendChild(inc);
        $(s1).append("\n       SELECT ");

        var inc = doc.createElement("include");
        $(inc).attr("refid", "sqlCols");
        s1.appendChild(inc);

        $(s1).append("         FROM " + _TABLE_NAME + "\n");
        
        var inc = doc.createElement("include");
        $(inc).attr("refid", "sqlConditions");
        s1.appendChild(inc);
        $(s1).append('\n       ORDER BY REG_DT DESC\n');

        var inc = doc.createElement("include");
        if(isApiMode())
        	$(inc).attr("refid", "com.wigo.api.crm.dao.CrmCommonDao.pagingFooter");
        else
        	$(inc).attr("refid", "com.wigo.crm.sys.dao.CrmCommonDao.pagingFooter");
        s1.appendChild(inc);
        mapper.appendChild(s1);
        var prfIdx = getPrefixIndex();
        var tbl = Utilities.convert2CamelCase(_TABLE_NAME).substring(prfIdx);

        var s1 = doc.createElement("select");
        $(s1).attr("id", "select" /* + tbl */);
        $(s1).attr("resultType", getModelName(true));
        $(s1).append("\n       SELECT ");

        var inc = doc.createElement("include");
        $(inc).attr("refid", "sqlCols");
        s1.appendChild(inc);

        var inc = doc.createElement("include");
        $(inc).attr("refid", "sqlPkConditions");
        $(s1).append("\n         FROM "+_TABLE_NAME+"\n");
        s1.appendChild(inc);
        mapper.appendChild(s1);
    }

    function generateModel(cancelPopup){
    
    	if(!validate())
    	{
    		return;
    	}
    	
        var strJava = "";
        _SAVE_FILE_NAME =  getModelName()  + ".java";
    	

        var list = _TABLE_INFO;
        var str = "";
        var hasNull = false;
        for (var i = 0; i < list.length; i++) {
            var col = list[i];
            var nullable = _NULL_ANN && col.nullable=="N"
            var column = col.columnName;
            var name = Utilities.convert2CamelCase(col.columnName);
            if (name == "regDt" || name == "regrId" || name == "amdDt" || name == "amdrId")
                continue;
            var jData = convertColumn(col);
            var defaultValue = jData.defaultValue? jData.defaultValue.replace("NULL","").replaceAll("'","").replace("SYSDATE","") : "";
			if(defaultValue)
            var code = $('input[name="codeId"][data-col-type="codeId"][data-col-id="'+col.columnId+'"]').val();
            if(col.comments ||code )
        		str+= "    /**\n";
            if(col.comments){
            	str+= "    * "+col.comments+" \n";
            }
            if(code){
            	str += "    * 공통코드 : " + code + " \n";
            }
            if(col.comments ||code )
            str+= "    */\n";
            if(isApiMode())
            {
            	var exampleValue =defaultValue?defaultValue : "";
            	if(!exampleValue){
            		
            	}
            	str +=  '    @Schema(description = "'+col.comments+'", example = "'+exampleValue+'", hidden = false, required = '+nullable+', nullable = '+!nullable+')\n';
            }
            
            if(jData.type == "int")
            {
                if(defaultValue)
                	str +=  "    private " +"Integer " + name + " = "+ defaultValue +";\n";
                else
                	str +=  "    private " +"Integer " + name + ";\n";
            }
            else if(jData.type == "number")
            {
            	if(defaultValue)
                	str +=  "    private " +"Double " + name + " = "+ defaultValue +";\n";
                else
                	str +=  "    private " +"Double " + name + ";\n";
            }
            else
            {
            	

                
            	if(nullable)
                {
                    str+="    @NonNull\n";
                    hasNull= true;
                }
            	if(defaultValue && defaultValue.toLowerCase() == "sysdate")
                	str +=  "    private " +"String " + name + " = \""+ defaultValue +"\";\n";
                else
                	str +=  "    private " +"String " + name + ";\n";
            } 
        }
        
    	
		strJava += "package "+getPackageName()+".model;\n"
		strJava += "\n"
		if(isApiMode())
			strJava += "import com.wigo.api.base.model.ApiBaseVo;\n"
		else
			strJava += "import com.wigo.crm.common.model.BaseVo;\n"
			
		if(hasNull)
			strJava += "import lombok.NonNull;\n"
		if(isApiMode())
			strJava += "import io.swagger.v3.oas.annotations.media.Schema;\n"
		strJava += "import lombok.Getter;\n"
		strJava += "import lombok.Setter;\n"
			
		strJava += "\n"
		strJava += getClassAnn("Vo");
		strJava += "@Getter\n"
		strJava += "@Setter\n"
		if(isApiMode())
		{
			strJava += '@Schema(description = "'+_TABLE_COMMENT+' 객체")\n'
			strJava += "public class "+getModelName()+" extends ApiBaseVo {\n";
		}
		else
			strJava += "public class "+getModelName()+" extends BaseVo {\n";
		strJava += "    /**\n";
		strJava += "    *\n"; 
		strJava += "    */\n";
		strJava += "private static final long serialVersionUID = 1L;\n";
		strJava += str ;
		strJava += "}\n"

  		if(cancelPopup)
  			return strJava;
// 	$("#printXml").val(strJava);	
       
//         $("#xmlModal").dialog('open');
			openPop(strJava);
   
}
    
    
    function generateModelSo(cancelPopup){
    	if(!validate())
    	{
    		return;
    	}
    	
        var strJava = "";
        var soName = getJavaName(false,"So");
        _SAVE_FILE_NAME =  getJavaName(false,"So")  + ".java";
    	

        var list = _TABLE_INFO;
        var str = "";
        var hasNull = false;
        for (var i = 0; i < list.length; i++) {
            var col = list[i];
            var nullable = _NULL_ANN && col.nullable=="N"
            var column = col.columnName;
            var name = Utilities.convert2CamelCase(col.columnName);
            if (name == "regDt" || name == "regrId" || name == "amdDt" || name == "amdrId")
                continue;
            var jData = convertColumn(col);
            var defaultValue = jData.defaultValue? jData.defaultValue.replace("NULL","").replaceAll("'","").replace("SYSDATE","") : "";
			if(defaultValue)
            var code = $('input[name="codeId"][data-col-type="codeId"][data-col-id="'+col.columnId+'"]').val();
            if(col.comments ||code )
        		str+= "    /**\n";
            if(col.comments){
            	str+= "    * "+col.comments+" \n";
            }
            if(code){
            	str += "    * 공통코드 : " + code + " \n";
            }
            if(col.comments ||code )
            str+= "    */\n";
            if(isApiMode())
            {
            	var exampleValue =defaultValue?defaultValue : "";
            	if(!exampleValue){
            		
            	}
            	str +=  '    @Schema(description = "'+col.comments+'", example = "'+exampleValue+'", hidden = false, required = '+nullable+', nullable = '+!nullable+')\n';
            }
            
            if(jData.type == "int")
            {
                if(defaultValue)
                	str +=  "    private " +"Integer " + name + " = "+ defaultValue +";\n";
                else
                	str +=  "    private " +"Integer " + name + ";\n";
            }
            else if(jData.type == "number")
            {
            	if(defaultValue)
                	str +=  "    private " +"Double " + name + " = "+ defaultValue +";\n";
                else
                	str +=  "    private " +"Double " + name + ";\n";
            }
            else
            {
            	

                
            	if(nullable)
                {
                    str+="    @NonNull\n";
                    hasNull= true;
                }
            	if(defaultValue && defaultValue.toLowerCase() == "sysdate")
                	str +=  "    private " +"String " + name + " = \""+ defaultValue +"\";\n";
                else
                	str +=  "    private " +"String " + name + ";\n";
            } 
        }
        
    	
		strJava += "package "+getPackageName()+".model;\n"
		strJava += "\n"
		if(hasNull)
			strJava += "import lombok.NonNull;\n"
		if(isApiMode())
		{
			strJava += "import io.swagger.v3.oas.annotations.media.Schema;\n";
				strJava += "import com.wigo.api.base.model.ApiPagination;\n";
		}
		strJava += "import lombok.Getter;\n"
		strJava += "import lombok.Setter;\n"
			
		strJava += "\n"
		strJava += getClassAnn("So");
		strJava += "@Getter\n"
		strJava += "@Setter\n"
		if(isApiMode())
			strJava += '@Schema(description = "'+_TABLE_COMMENT+' 검색 객체")\n'
		strJava += "public class "+soName+" extends ApiPagination {\n";
		if(!isApiMode()){
			strJava += "    /**\n";
			strJava += "    *\n"; 
			strJava += "    */\n";
			strJava += "private static final long serialVersionUID = 1L;\n";
		}

		strJava += str ;
		strJava += "}\n"

  		if(cancelPopup)
  			return strJava;
// 	$("#printXml").val(strJava);	
       
//         $("#xmlModal").dialog('open');
			openPop(strJava);
   
}
    
    function generateServiceXml(){
    	generateService();
    }
    function generateService(cancelPopup){
    	if(!validate())
    	{
    		return;
    	}
    	
    	 var strJava = "";
         _SAVE_FILE_NAME =  getJavaName(false,"service")  + ".java";
         strJava += "package "+getPackageName()+".service;\n";
         strJava += "\n";
         strJava += "import org.springframework.beans.factory.annotation.Autowired;\n";
         strJava += "import org.springframework.stereotype.Service;\n";
         
         if(isApiMode())
         	strJava += "import com.wigo.api.base.service.AbstractCrmService;\n";
        else
        	strJava += "import com.wigo.crm.sys.service.AbstractCrmService;\n";
         strJava += "import "+getJavaName(true,"dao")+";\n";
         if(isApiMode())
         	strJava += "import com.wigo.api.crm.dao.ICrmDao;\n";
        else
        	strJava += "import com.wigo.crm.sys.dao.ICrmDao;\n";
         strJava += "\n";
         strJava += getClassAnn("Service");
         strJava += "@Service\n";
         strJava += "public class "+getJavaName(false,"service")+" extends AbstractCrmService {\n";
         strJava += "   @Autowired\n";
         strJava += "   "+getJavaName(false,"dao")+" dao;\n";
         strJava += "\n";
         strJava += "   @Override\n";
         strJava += "   public ICrmDao getDao() {\n";
         strJava += "       return dao;\n";
         strJava += "   }\n";
         strJava += "}\n";
         if(cancelPopup)
   			return strJava;
         openPop(strJava);
   
    }
    function generateDaoXml(){
    	generateDao();
    }
    
    function generateDao(cancelPopup){
    	if(!validate())
    	{
    		return;
    	}
    	
        var strJava = "";
        _SAVE_FILE_NAME =  getJavaName(false,"dao")  + ".java";
        strJava += "package "+getPackageName()+".dao;\n";
        strJava += "\n";
        if(isApiMode())
        	strJava += "import com.wigo.api.config.annotation.CrmMapper;\n";
        else
           	strJava += "import com.wigo.crm.sys.mapper.CrmMapper;\n";
		if(isApiMode())
		 	strJava += "import com.wigo.api.crm.dao.ICrmDao;\n";
		else
			strJava += "import com.wigo.crm.sys.dao.ICrmDao;\n";
        strJava += "\n";
//         strJava += getClassAnn("Dao");
       	strJava += "@CrmMapper\n";
        strJava += "public interface "+getJavaName(false,"dao")+" extends ICrmDao {\n";
        strJava += "\n";
        strJava += "}\n";
        if(cancelPopup)
   			return strJava;
        openPop(strJava);
   
    }
    function generateControllerXml(){
    	generateController();
    }
    function generateController(cancelPopup){
    	if(!validate())
    	{
    		return;
    	}
    	var apiMode = isApiMode();
    	
    	var pack = getPackageName().split(".");
    	var lastPack = pack[pack.length-1];
    	var prfIdx = getPrefixIndex();
        var javaName = Utilities.convert2CamelCase(_TABLE_NAME).substring(prfIdx);
        var moduleName = javaName.substring(0,1).toUpperCase() + javaName.substring(1)+"";
    	var voName = getModelName(false);
        var strJava = "";
        _SAVE_FILE_NAME =  getJavaName(false,"controller")  + ".java";
        strJava += "package "+getPackageName()+".controller;\n";
        strJava += "\n";
        
        
        if(apiMode)
        {
        	
        	
        	strJava += 'import java.util.List;\n';
        	strJava += 'import java.util.Map;\n';
        	strJava += 'import javax.validation.Valid;\n';
        	strJava += 'import org.springframework.beans.factory.annotation.Autowired;\n';
        	strJava += 'import org.springframework.http.ResponseEntity;\n';
        	strJava += 'import org.springframework.web.bind.annotation.GetMapping;\n';
        	strJava += 'import org.springframework.web.bind.annotation.RequestMapping;\n';
        	strJava += 'import org.springframework.web.bind.annotation.RequestParam;\n';
        	strJava += 'import org.springframework.web.bind.annotation.RestController;\n';
        	strJava += 'import com.wigo.api.base.controller.BaseRestController;\n';
        	strJava += 'import com.wigo.api.base.model.ApiResultVo;\n';
        	strJava += 'import io.swagger.v3.oas.annotations.Operation;\n';
        	strJava += 'import io.swagger.v3.oas.annotations.Parameter;\n';
        	strJava += 'import io.swagger.v3.oas.annotations.tags.Tag;\n';
        	strJava += 'import com.wigo.api.crm.model.'+moduleName+'So;\n';
        	strJava += 'import com.wigo.api.crm.model.'+moduleName+'Vo;\n';
        	strJava += 'import com.wigo.api.crm.service.'+moduleName+'Service;\n';
        }
        else
        {
        	strJava += "import java.util.List;\n";
            strJava += "import java.util.Map;\n";
            strJava += "import org.springframework.beans.factory.annotation.Autowired;\n";
            strJava += "import org.springframework.stereotype.Controller;\n";
        	strJava += "import org.springframework.ui.ModelMap;\n";
        	strJava += "import com.wigo.crm.common.util.Utilities;\n";

            strJava += "import org.springframework.web.bind.annotation.GetMapping;\n";
            strJava += "import org.springframework.web.bind.annotation.PostMapping;\n";
            strJava += "import org.springframework.web.bind.annotation.RequestBody;\n";
            strJava += "import org.springframework.web.bind.annotation.RequestMapping;\n";
            strJava += "import org.springframework.web.bind.annotation.RequestParam;\n";
            strJava += "import org.springframework.web.bind.annotation.ResponseBody;\n";
            strJava += "import com.wigo.crm.common.model.EzMap;\n";
            strJava += "import com.wigo.crm.common.model.EzPaginationInfo;\n";
            
            strJava += "import "+getModelName(true)+";\n";
            strJava += "import "+getJavaName(true,"service")+";\n";
        }
        
        strJava += "\n";
        strJava += getClassAnn("Controller");
        
        strJava += "\n";
        
        if(apiMode)
        {
        	var mappingUrl = _TABLE_NAME.replaceAll("_","-").toLowerCase();
        	strJava += "@Tag(name = \""+_TABLE_COMMENT+"\", description = \""+_TABLE_COMMENT+" API입니다.\")\n";
        	strJava += "@RestController\n";
        	strJava += "@RequestMapping(\"/crm/v1.0/"+mappingUrl+"\")\n";
        	 strJava += "public class "+getJavaName(false,"controller")+" extends BaseRestController {\n";
        	
        }
        else 
        {
        	strJava += "@Controller\n";
        	strJava += '@RequestMapping(value = { "'+javaName+'", "{menuCd}/'+javaName+'" })\n';
        	 strJava += "public class "+getJavaName(false,"controller")+"{\n";
        }
        
        strJava += "\n";
        strJava += "@Autowired\n";
        strJava += getJavaName(false,"service") + " service;\n";
        strJava += "\n";
        if(apiMode)
        {
			strJava += '	@GetMapping("get'+moduleName+'List")\n';
			strJava += '	@Operation(summary = "멤버쉽 검색", description = "통합회원을 검색 검색합니다.")\n';
			strJava += '	public ResponseEntity<ApiResultVo<List<'+getModelName()+'>>> get'+moduleName+'List(\n';
			strJava += '			@Parameter(description = "'+_TABLE_COMMENT+' 검색객체") @Valid '+moduleName+'So so,\n';
			strJava += '			@Parameter(description = "검색객체", hidden = true) @RequestParam Map<String, Object> param)\n';
			strJava += '			throws Exception {\n';
			strJava += '		List<'+getModelName()+'> list = service.getList(param);\n';
			strJava += '		return successResponse(list);\n';
			strJava += "}\n";
		    strJava += "\n";
        }
        else{
        	
            strJava += "@GetMapping(value = { \"\", \"index\" })\n";
            strJava += "public String init(@RequestParam Map<String, Object> param, ModelMap model) throws Exception {\n";
            strJava += "    model.addAllAttributes(param);\n";
            strJava += "    return Utilities.getProperty(\"tiles.crm\") + \""+lastPack+"/"+javaName+"List\";\n";
            strJava += "}\n";
            strJava += "\n";
            
            strJava += "@PostMapping(value = { \"getList\" })\n";
            strJava += "public @ResponseBody Object getList(@RequestBody EzMap param) throws Exception {\n";
            strJava += "    EzPaginationInfo page = param.getPaginationInfo();\n";
            strJava += "    List<"+getModelName()+"> list = service.getList(param);\n";
            strJava += "    page.setTotalRecordCount(service.getListCount(param));\n";
            strJava += "    return Utilities.getGridData(list,page);\n";
            strJava += "}\n";
            strJava += "\n";
            strJava += "@GetMapping(value = { \"get\" })\n";
            strJava += "public @ResponseBody Object get(@RequestParam Map<String, Object> rparam) throws Exception {\n";
            strJava += "    EzMap param = new EzMap(rparam);\n";
            strJava += "    return service.get(param);\n";
            strJava += "}\n";
            strJava += "@PostMapping(value = {\"save\" })\n";
            strJava += "public @ResponseBody Object save(@RequestBody "+voName+" vo) throws Exception {\n";
            strJava += "    return service.save(vo);\n";
            strJava += "}\n";   
            strJava += "\n";
            strJava += "@PostMapping(value = { \"saveList\" })\n";
            strJava += "public @ResponseBody Object saveList(@RequestBody List<"+voName+"> list) throws Exception {\n";
            strJava += "    return service.saveList(list);\n";
            strJava += "}\n";
            strJava += "@PostMapping(value = { \"deleteList\" })\n";
            strJava += "public @ResponseBody Object deleteList(@RequestBody List<"+voName+"> list) throws Exception {\n";
            strJava += "    return service.deleteList(list);\n";
            strJava += "}\n";
        }
        
        
        
        
        
        strJava += "}\n";
        if(cancelPopup)
   			return strJava;
        
        openPop(strJava);
   
    }
	function getModelName(fullName){
		var prfIdx = getPrefixIndex();
    	var javaName = Utilities.convert2CamelCase(_TABLE_NAME).substring(prfIdx);
    	javaName = javaName.substring(0,1).toUpperCase() + javaName.substring(1)+"Vo";
    	if(fullName)
    		return getPackageName() + ".model." + javaName;
    	else
    		return javaName;
	} 
	function getModelSoName(fullName){
		var prfIdx = getPrefixIndex();
    	var javaName = Utilities.convert2CamelCase(_TABLE_NAME).substring(prfIdx);
    	javaName = javaName.substring(0,1).toUpperCase() + javaName.substring(1)+"So";
    	if(fullName)
    		return getPackageName() + ".model." + javaName;
    	else
    		return javaName;
	} 
	
	function getJavaName(fullName,type){
        var prfIdx = getPrefixIndex();
        var javaName = Utilities.convert2CamelCase(_TABLE_NAME).substring(prfIdx);
        var pre = type.substring(0,1).toUpperCase() + type.substring(1);
        javaName = javaName.substring(0,1).toUpperCase() + javaName.substring(1)+pre;
        if(fullName)
            return getPackageName() + "."+type+"." + javaName;
        else
            return javaName;
    }
    
	function getDaoName(){
		var prfIdx = getPrefixIndex();
    	var javaName = Utilities.convert2CamelCase(_TABLE_NAME).substring(prfIdx);
    	javaName = javaName.substring(0,1).toUpperCase() + javaName.substring(1)+"Dao";
    	return javaName;
	}

    function getPackageName(){
        return $("#packageName").val();
    }
    
    function getPrefixIndex(){
    	return 0;
//     	return _TABLE_NAME.indexOf("_");
    }
    
    function getClassAnn(typeName){
    	var javaName = Utilities.convert2CamelCase(_TABLE_NAME).substring(getPrefixIndex());
    	javaName = javaName.substring(0,1).toUpperCase() + javaName.substring(1)+typeName;
    	var dt = moment().format('YYYY. M. D.');
    	 var username = 'user';
    	var ann = ''
				+ '/**\n'
				+ ' * \n'
				+ ' * @ClassName    '+javaName+'\n'
				+ ' * @author    '+username+'\n'
				+ ' * @date    '+dt +'\n'
				+ ' * @Version    1.0\n'
				+ ' * @description    '+_TABLE_COMMENT+ ' '+typeName+'\n'
				+ ' * @Company    Copyright ⓒ wigo.ai. All Right Reserved\n'
				+ ' */\n';
    	return ann;
    }
    
//     $("#xmlModal").dialog({ autoOpen:false,
// 						    width:1200,
// 						    height:800
//     });

    function openPop(text){
    	window["genGridText"] = text;
    	Utilities.openModal("/genGrid/text",1500,800);  
    }
    
    function saveAllFiles(){
    	Utilities.downloadText(generateXml(true), _SAVE_FILE_NAME);
    	Utilities.downloadText(generateDbXml(true), _SAVE_FILE_NAME);
    	Utilities.downloadText(generateModel(true), _SAVE_FILE_NAME);
    	if(isApiMode()){
    		Utilities.downloadText(generateModelSo(true), _SAVE_FILE_NAME);	
    	}
    	Utilities.downloadText(generateDao(true), _SAVE_FILE_NAME);
    	Utilities.downloadText(generateService(true), _SAVE_FILE_NAME);
    	Utilities.downloadText(generateController(true), _SAVE_FILE_NAME);
    	
    }
    
	function isApiMode(){
		return $("#packageName").val().indexOf("com.wigo.api.") == 0;
	}
	function validate(){
		if(!_TABLE_NAME)
		{
			alert("먼저 테이블을 검색해 주세요");
			return false;
		}
		return true;
	}
</script>