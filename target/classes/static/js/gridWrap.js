$(document).ready(function() {
	var grdList = $("[data-type=grid]");
	for (var i = 0; i < grdList.length; i++) {
		var grd = $(grdList[i]);
		initGrid(grd);
	}
});
function initGrid(grd) {
	var config = createGrid(grd);

}
function createGridObject(grd) {

	var url = grd.attr("data-tpl-url");
	var gridId = grd.attr("data-grid-id");
	if (!gridId)
		gridId = Utilties.uuid();
	if (window[gridId]) {
		alert("동일한 그리드 아이디가 존재합니다.");
		return false;
	}
	grd.addClass("gridView");
	pagination = grd.attr("data-pagenation") == "Y";
	grd.wrap('<div id="' + gridId + '_wrap"></div>');
	return {
		pagination : pagination,
		currentRow : -1,
		gridId : gridId,
		tmplUrl : url,
		gridWrap : grd,
		gridConfig : {},
		columnMap : {},
		columns : [],
		gridJson : null,
		getColumnInfo : function(colName) {
			return this.columnMap[colName];
		},
		loadXml : function() {
			var grid = this;
			Utilities.ajax({
				url : grid.tmplUrl /* +"?nocachingParam="+Utilities.guid() */,
				success : function(data, textStatus, jqXHR) {
					grid.load(jqXHR.responseXML);
				},
				error : function(jqXHR, textStatus, errorThrown) {

				}
			});
		},
		load : function(xmlDocument) {
			if (!xmlDocument)
				return false;
			var root = xmlDocument.documentElement;
			for (var i = 0; i < root.childNodes.length; i++) {
				var child = root.childNodes[i];
				if (child.nodeType != "1")
					continue;
				if (child.tagName == "meta") {
					this.gridConfig = this.loadMeta(child);
				} else if (child.tagName == "column") {
					var col = this.loadColumn(child);
					this.columns.push(col);
					if (col.field)
						this.columnMap[col.field] = col;
				} else if (child.tagName == "group") {
					this.columns.push(this.loadGroup(child, this.columnMap));
				}
			}

			this.createGrid();
		},
		loadMeta : function(meta) {
			var gridConfig = {};
			for (var i = 0; i < meta.childNodes.length; i++) {
				var child = meta.childNodes[i];
				if (child.nodeType != "1")
					continue;
				gridConfig[child.tagName] = $.trim(child.getAttribute("value"));

			}
			return gridConfig;
		},
		loadColumn : function(col) {
			var atts = {};
			for (var i = 0; i < col.attributes.length; i++) {
				var attr = col.attributes[i];
				atts[attr.name] = attr.value;
			}

			var type = col.getAttribute("type") ? col.getAttribute("type").toLowerCase() : 'char';
			var field = col.getAttribute("field");
			var name = col.getAttribute("name");
			var width = col.getAttribute("width");
			var align = col.getAttribute("textAlignment") || col.getAttribute("align");
			var hidden = col.getAttribute("hidden") == "Y" ? "Y" : "N";
			var editable = col.getAttribute("edit") == "Y" ? "Y" : "N";
			var pattern = col.getAttribute("pattern");
			var place = col.getAttribute("place");
			var maxLength = Utilities.parseInt(col.getAttribute("maxLength"));
			var icon = col.getAttribute("icon");
			var image = col.getAttribute("image");
			var btnAlign = col.getAttribute("btnAlign");

			if (!width)
				width = "0";
			if (!align) {
				align = "left";
				if (type == "number" || type == "money")
					align = "right";
				else if (type == "date" || type == "datemonth" || type == "time" || type == "datetime")
					align = "center";
			}

			if (!pattern) {
				if (type == "number") {
					pattern = "#,##0.###";
					if (place) {
						place = Utilities.parseInt(place);
						pattern = "#,##0";
						for (var i = 0; i < place; i++) {
							if (i == 0)
								pattern += ".";
							pattern += "#";
						}
					}
				} else if (type == "int" || type == "integer") {
					pattern = "#,##0";
				} else if (type == "date") {
					pattern = "yyyyMMdd";
				} else if (type == "datetime") {
					pattern = "yyyyMMddHHmmss";
				} else if (type == "time") {
					pattern = "HHmmss";
				} else if (type == "datemonth") {
					pattern = "yyyyMM";
				}

			}
			var colInfo = {
				tagName : "column",
				type : type,
				field : field,
				name : name,
				width : width,
				align : align,
				hidden : hidden,
				editable : editable,
				pattern : pattern,
				place : place,
				maxLength : maxLength,
				image : image,
				icon : icon,
				btnAlign : btnAlign,
				getGridType : function() {
					var type = this.type;
					if (type == "char")
						return "text";
					else if (type == "number" || type == "int" || type == "integer")
						return "number";
					else if (type == "checkbox")
						return "boolean";
					else if (type == "date" || type == "time" || type == "datetime")
						return "datetime";
					else if (type == "combo")
						return "data";
					else if (type == "button")
						return "text";
					else
						return "data";
				}
			};
			$.extend(atts, colInfo);
			return atts;
		},
		loadGroup : function(group) {
			var atts = {};
			for (var i = 0; i < group.attributes.length; i++) {
				var attr = group.attributes[i];
				atts[attr.name] = attr.value;
			}
			var name = group.getAttribute("name");
			var width = group.getAttribute("width");
			var title = group.getAttribute("title");
			var hidden = group.getAttribute("hidden") == "Y" ? "Y" : "N";

			var direction = group.getAttribute("direction") == "V" ? "vertical" : "horizontal";
			var items = [];
			for (var i = 0; i < group.childNodes.length; i++) {
				var child = group.childNodes[i];
				if (child.nodeType != "1")
					continue;
				if (child.tagName == "column") {
					var col = this.loadColumn(child);
                    items.push(col);
                    this.columnMap[col.field] = col;
				} else if (child.tagName == "group") {
					items.push(this.loadGroup(child));
				}
			}
			var groupInfo = {
				tagName : "group",
				name : name,
				title : title,
				direction : direction,
				hidden : hidden,
				width : width,
				items : items
			};
			$.extend(atts, groupInfo);
			return groupInfo;
		},
		convertFieldsJson : function(columns, cols) {
			if (!columns)
				columns = this.columns;
			if (!cols)
				cols = [];
			for (var i = 0; i < columns.length; i++) {
				var col = columns[i];
				var json = this.convertField(col, cols);
				if (json)
					cols.push(json);
			}
			return cols;
		},
		convertField : function(col, arr) {
			if (col.tagName == "group") {
				this.convertFieldsJson(col.items, arr);
				return null;
			}
			if (!col.field)
				return null;
			var jsonType = col.getGridType();
			var json = {
				fieldName : col.field,
				dataType : jsonType,
			}
			if (jsonType == "boolean") {
				// json.booleanFormat = "N:Y";
			}
			if (jsonType == "datetime") {
				if (col.pattern)
					json.datetimeFormat = col.pattern;
				else {
					if (col.type == "date")
						json.datetimeFormat = "yyyyMMdd";
					else if (col.type == "datemonth")
						json.datetimeFormat = "yyyyMM";
					else if (col.type == "datetime")
						json.datetimeFormat = "yyyyMMddHHmmss";
					else if (col.type == "time")
						json.datetimeFormat = "HHmmss";
				}
			}
			return json;
		},
		convertColumnsJson : function(columns, cols) {
			if (!columns)
				columns = this.columns;
			if (!cols)
				cols = [];
			for (var i = 0; i < columns.length; i++) {
				var col = columns[i];
				if (col.tagName == "column") {
					cols.push(this.convertColumn(col));
				} else {
					this.hasLayout = true;
					this.convertColumnsJson(col.items, cols);
				}

			}
			return cols;
		},
		convertColumn : function(col) {
			var jsonType = col.getGridType();
			var editable = col.insertEditable == "Y" || col.readOnly != "Y";
			var hidden = col.hidden == "Y" || col.visible == "N";

			var json = {
				name : col.field,
				fieldName : col.field,
				type : jsonType,
				readOnly : !editable,
				editable : editable,
				visible : !hidden,
                
				header : {
                    text : col.name
				}
			}
			if (col.align == "right") {
				json.styleName = "right"
			} else if (col.align == "left") {
				json.styleName = "left"
			} else
				json.styleName = "center"
			if (jsonType == "number" && col.pattern)
				json.numberFormat = col.pattern;
			else if (jsonType == "datetime") {
				var pattern = "";
				var datetimeFormat = "";
				if (col.type == "date" || col.type == "datemonth") {
					var opt = {
						language : "ko"
					};
					var editMask = "";
					if (col.type == "date") {
						datetimeFormat = "yyyy-MM-dd";
						pattern = "yyyyMMdd";
						opt.format = "yyyy-mm-dd";
						editMask = "9999-99-99";
						// json.editor.mask = {
						//								
						// };
					} else if (col.type == "datemonth") {
						datetimeFormat = "yyyy-MM";
						pattern = "yyyyMM";
						opt.format = "yyyy-mm";
						opt.minViewMode = 1;
						editMask = "9999-99";
						// json.editor.mask = {
						// editMask : "9999-99"
						// };
					}
					json.editor = {
						// type : "date",
						type : "btdate",
						datetimeFormat : pattern,
						btOptions : opt,
						mask : {
							editMask : editMask
						}
					}
					json.datetimeFormat = datetimeFormat;

				} else if (col.type == "datetime") {
					pattern = "yyyy-MM-dd HH:mm:ss";
					json.datetimeFormat = pattern;
					json.editor = {
						datetimeFormat : "yyyyMMddHHmmss",
					}

				} else if (col.type == "time") {
					pattern = "HH:mm:ss";
					json.datetimeFormat = pattern;
					json.editor = {
						datetimeFormat : "HHmmss",

					}

				}
			} else if (jsonType == "boolean") {
				json.editable = false, json.renderer = {
					type : "check",
				};
			} else if (col.type == "combo") {

				json.lookupDisplay = true;
				var param = {
					rootCodeId : col.rootCodeId,
					upCodeId : col.upCodeId,
					codeLevel : col.codeLevel
				};
				var data = Utilities.getObject(_code_url, param, true);
				var labels = [];
				var values = [];
				for (var i = 0; i < data.length; i++) {
					labels.push(data[i].codeName);
					values.push(data[i].codeId);
				}
				json.labels = labels;
				json.values = values;
				json.sortable = false, json.type = "data";
				json.editor = {
					type : "dropdown",
					dropDownCount : values.length,
					domainOnly : true,
					values : values,
					labels : labels
				};
			} else if (col.type == "button") {
				json.editable = false;
				json.type = "data";
				json.renderer = {
					type : "html",
					callback : function(grid, cell, w, h) {

						var str = '<button type="button" onclick="_onGridButtonClick(this)" id="btn_' + cell.index.dataRow + '" data-field-name="' + col.field + '" data-grid-id="' + grid.gridId
								+ '" style="width:100%;heigth:100%" data-row="' + cell.index.dataRow + '" class="btn btn-xs btn-primary">' + col.name + '</button>'
						return str;
					}
				}
			} else if (col.type == "html") {
				var htmlCallback = col.htmlCallback;
				json.editable = false;
				json.type = "data";
				json.renderer = {
					type : "html",
					callback : function(grid, cell, w, h) {
						if (htmlCallback && window[htmlCallback]) {
							var html = "";
							try {
								var jsonData = grid.getJsonRow(cell.item.index);
								html = window[htmlCallback](grid, cell.item.index, cell.index.column.name, jsonData)
								return html;
							} catch (e) {

							}
						}

						if (col.html) {
							return col.html;
						} else
							return "";
					}
				}
			}

			return json;
		},
		getLayoutJson : function(columns) {
			if (!columns)
				columns = this.columns;
			var cols = [];
			for (var i = 0; i < columns.length; i++) {
				var col = columns[i];
				var json = null;
				if (col.tagName == "column")
					json = this.getColumnLayout(col);
				else
					json = this.getGroupLayout(col);
				if (json)
					cols.push(json);
			}
			return cols;
		},
		getColumnLayout : function(col) {
			if (col.hidden == "Y")
				return null;
			var json = {
				column : col.field,
				visible : col.hidden != "Y"
			};
			if (col.width)
				json.width = col.width;
			return json;
		},
		getGroupLayout : function(col) {
			if (col.hidden == "Y")
				return null;
			var visible = !!col.title;
			var groupInfo = {
				name : col.name,
				visible : col.hidden != "Y",
				direction : col.direction,
				header : {
					text : col.title,
					visible : visible
				},

			};
			if (col.width)
				groupInfo.width = col.width;
			groupInfo.items = this.getLayoutJson(col.items)
			return groupInfo;
		},
		convertGridOptions : function(meta) {
			if (!meta)
				meta = this.gridConfig;
			// <rowSelection value="Y" /> <!-- 클릭시 전체 로우 선택여부 -->
			// <rollbackable value="N" /> <!-- 로우 복구 가능 여부 -->
			// <gridfitStyle value="evenFill" /> <!-- 그리드 자동 맞춤 설정 -->
			var readOnly = meta.readOnly == "Y";
			var insertable = readOnly ? false : meta.insertable == "Y";
			var appendable = readOnly ? false : meta.appendable == "Y";
			var editable = readOnly ? false : meta.editable == "Y";
			var deletable = readOnly ? false : meta.deletable == "Y";

			var options = {

				checkBar : {
					visible : meta.showCheckbox == "Y" || meta.showCheckbox == "R",
					exclusive : meta.showCheckbox == "R"
				},
				rowIndicator : {
					visible : meta.showNumber == "Y"
				},
				stateBar : {
					visible : meta.showStateBar == "Y"
				},
				headerSummary : {
					visible : meta.showHeaderSum == "Y"
				},
				footer : {
					visible : meta.showFooter == "Y"
				},
				footers : {
					visible : meta.showFooter == "Y"
				},
				sorting : {
					sytle : meta.setSort ? meta.setSort : "exclusive"
				},
				stateBar : {
					visible : meta.showStateBar == "Y"
				},
				edit : {
					readOnly : readOnly,
					insertable : insertable,
					appendable : appendable,
					editable : editable,
					deletable : deletable
				},
				display : {
					columnMovable : meta.columnMove == "Y",
					selectionStyle : meta.rowSelection == "Y" ? "rows" : "block",
					fitStyle : meta.gridfitStyle ? meta.gridfitStyle : "none"
				},
			// displayOptions : {
			// columnMovable : meta.columnMove == "Y",
			// selectionStyle : meta.rowSelection == "Y" ? "rows" : "block",
			// fitStyle : meta.gridfitStyle ? meta.gridfitStyle : "none"
			// }
			};
			return options;
		},
		createPagination : function() {
			var divWrap = this.gridWrap.parent();
			divWrap.append('<div class="row gridview_pagination" id="' + this.gridId + '_pagination"><div data-type="pagination-info" class="col-sm-5" id="' + this.gridId
					+ '_paginationInfo">총 건 검색</div><div data-type="pagination" class="col-sm-7" id="' + this.gridId + '_paginationBtn"></div></div>');
			this.setPagination({
				currentPageNo : 1,
				totalPageCount : 0,
				firstPageNoOnPageList : 1,
				lastPageNoOnPageList : 0,
				totalRecordCount : 0,

			});
		},
		setPagination : function(pagination) {
			var divWrap = this.gridWrap.parent();
			var info = divWrap.find("div[data-type=pagination-info]");
			info.html("총 [" + pagination.totalRecordCount + "]건 검색 [" + pagination.currentPageNo + "/" + pagination.totalPageCount + "] page");
			pageInfo = divWrap.find("div[data-type=pagination]");
			pageInfo.find("ul").remove();
			pageInfo.append('<ul class="pagination pagination-sm float-right"></ui>');
			var ul = pageInfo.find("ul");
			ul.append('<li class="page-item"><a class="page-link" data-grid-id="' + this.gridId + '" data-type="previous" data-page-num="' + (pagination.firstPageNoOnPageList - 1)
					+ '" href="#">&laquo;</a></li>');
			for (var i = pagination.firstPageNoOnPageList; i <= pagination.lastPageNoOnPageList; i++) {
				var active = i == pagination.currentPageNo ? " active" : "";
				ul.append(' <li class="page-item ' + active + '"><a class="page-link" data-grid-id="' + this.gridId + '" data-type="page" data-page-num="' + i + '"  href="#">' + i + '</a></li>');
			}
			var nextPage = pagination.lastPageNoOnPageList + 1;
			if (nextPage > pagination.totalPageCount)
			{
                nextPage = 0;
            }	
			ul.append('<li class="page-item"><a class="page-link" data-grid-id="' + this.gridId + '" data-type="previous" data-page-num="' + nextPage + '" href="#">&raquo;</a></li>');

			ul.find("a.page-link").click(function() {

				var data = $(this).data();
				var gridId = data.gridId;
				var gridView = window[gridId];
				if (data.pageNum > 0) {

					if (window[gridId + "_pageMove"] && typeof (window[gridId + "_pageMove"]) == "function") {
						return window[gridId + "_pageMove"](gridView, data.pageNum);
					} else if (window["onGridPageMove"] && typeof (window["onGridPageMove"]) == "function") {
						return window["onGridPageMove"](gridView, data.pageNum);
					}
				}
			});

		},
		createGrid : function() {
			if (window[this.gridId]) {
				alert("동일한 그리드 아이디가 존재합니다.");
				return false;
			}
			var jsonFields = this.convertFieldsJson();
			var jsonColumns = this.convertColumnsJson();
			this.gridWrap.addClass("gridTable");
			var container = this.gridWrap[0];
			var provider = new RealGrid.LocalDataProvider(false);
			options = provider.getOptions();
			options.booleanFormat = "N:Y";
            options.datetimeFormat = "yyyyMMddHHmmss";
			provider.setOptions(options);
			provider.setFields(jsonFields);
			// provider.commitBeforeDataEdit = true;
			var gridView = new RealGrid.GridView(container);
			gridView.setFormatOptions({
				booleanFormat : 'N:Y'
			});
			gridView.setDataSource(provider);
			gridView.setColumns(jsonColumns);
			gridView.gridId = this.gridId;
			var gridOption = this.convertGridOptions();
			gridView.setOptions(gridOption);
			this.gridView = gridView;
			gridView.gridWrapper = this;
			if (this.hasLayout) {
				var layout = this.getLayoutJson();
				gridView.setColumnLayout(layout);
			}
			gridView.loadJson = function(data) {
				this.cancel();
				var list = null;
				if (Array.isArray (data)) {
					list = data;
				} else {
					list = data.list;
					if (this.pagination) {
						this.gridWrapper.setPagination(data.pagination);
					}
				}
				var dataProvider = this.getDataSource();
				dataProvider.fillJsonData(list, {
					fillMode : "set"
				});
			};
			gridView.loadUrl = function(url, param) {
				var grid = this;
				grid.showLoading();
				Utilities.getAjax(url, param, true, function(data, jqXHR) {
					grid.closeLoading();
                    if(Utilities.processResult(data,jqXHR,"데이터 로딩에 실패했습니다."))
                    {	grid.loadJson(data);
						
						if (window[gridId + "_dataLoaded"] && typeof (window[gridId + "_dataLoaded"]) == "function") {
							window[gridId + "_dataLoaded"](gridView,gridView.getJsonRows() ,data);
						} else if (window["onGridDataLoaded"] && typeof (window["onGridDataLoaded"]) == "function") {
							window["onGridDataLoaded"](gridView,gridView.getJsonRows() ,data);
						}
						
						
					}
				});
			};
			gridView.onCurrentRowChanged = function(gridView, oldRow, newRow) {
				var gridId = gridView.gridId;
				gridView.gridWrapper.currentRow = newRow;
				var rowData = gridView.getValues(newRow);

				if (window[gridId + "_rowChanged"] && typeof (window[gridId + "_rowChanged"]) == "function") {
					window[gridId + "_rowChanged"](gridView, oldRow, newRow, rowData);
				} else if (window["onGridRowChanged"] && typeof (window["onGridRowChanged"]) == "function") {
					window["onGridRowChanged"](gridView, oldRow, newRow, rowData);
				}

			}
			gridView.onCellClicked = function(gridView, clickedData) {
				var gridId = gridView.gridId;
				if (clickedData.cellType == "header") {
					if (window[gridId + "_columnClick"] && typeof (window[gridId + "_columnClick"]) == "function") {
						window[gridId + "_columnClick"](gridView, clickedData.colName || clickedData.subType);
					} else if (window["onGridColumnClick"] && typeof (window["onGridColumnClick"]) == "function") {
						window["onGridColumnClick"](gridView, clickedData.colName || clickedData.subType);
					}

				} else {
					if (window[gridId + "_cellClick"] && typeof (window[gridId + "_cellClick"]) == "function") {
						window[gridId + "_cellClick"](gridView, clickedData.itemIndex, clickedData.column, clickedData.index, clickedData.field);
					} else if (window["onGridCellClick"] && typeof (window["onGridCellClick"]) == "function") {
						window["onGridCellClick"](gridView, clickedData.itemIndex, clickedData.column, clickedData.index, clickedData.field);
					}

				}

			};
			gridView.onCellDblClicked = function(gridView, clickedData) {
				var gridId = gridView.gridId;
				if (clickedData.cellType == "header") {

					if (window[gridId + "_columnDblClick"] && typeof (window[gridId + "_columnDblClick"]) == "function") {
						window[gridId + "_columnDblClick"](gridView, clickedData.colName || clickedData.subType);
					} else if (window["onGridColumnDblClick"] && typeof (window["onGridColumnDblClick"]) == "function") {
						window["onGridColumnDblClick"](gridView, clickedData.colName || clickedData.subType);
					}
				} else {
                    var json = this.getJsonRow(clickedData.itemIndex);
					if (window[gridId + "_cellDblClick"] && typeof (window[gridId + "_cellDblClick"]) == "function") {
						window[gridId + "_cellDblClick"](gridView, clickedData.itemIndex, clickedData.column,json,json[clickedData.column]);
					} else if (window["onGridCellDblClick"] && typeof (window["onGridCellDblClick"]) == "function") {
						window["onGridCellDblClick"](gridView, clickedData.itemIndex, clickedData.column,json,json[clickedData.column]);
					}

				}

			};
			gridView.onGridButtonClick = function(gridView, data) {
				var col = data.fieldName;
				var row = data.row;
				var itemIndex = this.getItemIndex(row);
				var gridId = gridView.gridId;
				var cFunction = gridId + "_" + col + "_buttonClicked";
				var json = this.getJsonRow(itemIndex);
				if (window[cFunction] && typeof (window[cFunction]) == "function") {
					window[cFunction](gridView, itemIndex, col, json);
				} else if (window[gridId + "_buttonClicked"] && typeof (window[gridId + "_buttonClicked"]) == "function") {
					window[gridId + "_buttonClicked"](gridView, itemIndex, col, json);
				} else if (window["onGridButtonClicked"] && typeof (window["onGridButtonClicked"]) == "function") {
					window["onGridButtonClicked"](gridView, itemIndex, col, json);
				}

			};
			gridView.onCellButtonClicked = function(gridView, index, col) {
				var json = this.getJsonRow(index.itemIndex);
				var gridId = gridView.gridId;
				var cFunction = gridId + "_" + col + "_ButtonClicked";
				if (window[cFunction] && typeof (window[cFunction]) == "function") {
					window[cFunction](gridView, index.itemIndex, col, json);
				} else if (window[gridId + "_buttonClicked"] && typeof (window[gridId + "_buttonClicked"]) == "function") {
					window[gridId + "_buttonClicked"](gridView, index.itemIndex, col, json);
				} else if (window["onGridButtonClicked"] && typeof (window["onGridButtonClicked"]) == "function") {
					window["onGridButtonClicked"](gridView, index.itemIndex, col, json);
				}

			};
			gridView.onRowInserting = function(gridView, itemIndex, dataRow) {
				var gridId = gridView.gridId;
				if (window[gridId + "_rowInserting"] && typeof (window[gridId + "_rowInserting"]) == "function") {
					return window[gridId + "_rowInserting"](gridView, itemIndex, dataRow);
				} else if (window["onGridRowInserting"] && typeof (window["onGridRowInserting"]) == "function") {
					return window["onGridRowInserting"](gridView, itemIndex, dataRow);
				}
			};
			gridView.onEditCommit = function(gridView, columnInfo, oldValue, newValue) {
				var gridId = gridView.gridId;
				var colInfo = gridView.gridWrapper.getColumnInfo(columnInfo.column);

				if (colInfo.editable == "N" || colInfo.readOnly == "N") {
					if (colInfo.insertEditable == "Y") {
						var stat = gridView.getItemState(columnInfo.itemIndex);
						if (stat == "appending" || stat == "inserting")
							return true;
						stat = gridView.getDataSource().getRowState(this.getDataRow(columnInfo.itemIndex));
						return stat == "created";

					} else {
						// return false;
					}
				}

				if (window[gridId + "_editCommit"] && typeof (window[gridId + "_editCommit"]) == "function") {
					return window[gridId + "_editCommit"](gridView, index, oldValue, newValue);
				} else if (window["onGridEditCommit"] && typeof (window["onGridEditCommit"]) == "function") {
					return window["onGridEditCommit"](gridView, index, oldValue, newValue);
				} else {

					// gridView.gridWrapper

					// var rowIndex = gridView.getCurrent().itemIndex;
					// var stat =gridView.getDataSource().getRowState(rowIndex);
					//
					// //var data = gridView.getValues();
					// alert(stat);
					return true;
				}
			};
			gridView.resetRowState =function(row) {
				this.getDataSource().setRowState(this.getDataRow(row), 'none', true);

			};
			gridView.resetAllRowStatus = function() {
				var rowCount = this.getDataSource().getRowCount();
				for (var i = 0; i < rowCount; i++) {
					this.getDataSource().setRowState(this.getDataRow(i), 'none', true);
				}
			};
			gridView.addRow = function(data) {
                this.commit();
				var row = this.getDataSource().addRow(data);
				if (data.stat != 'c') {
					var dataProvider = this.getDataSource();
					dataProvider.setRowState(row, "none", true);
				}
			};
			gridView.addRows = function(list) {
				for (var i = 0; i < list.length; i++) {
					this.addRow(list[i]);
				}
			};
			gridView.normalize = function(json) {
				var fields = this.getDataSource().getFields();
				for (var i = 0; i < fields.length; i++) {
					var field = fields[i];
					if (field.dataType == "boolean") {
						var value = json[field.orgFieldName];
						json[field.orgFieldName] = value == 1 || value == 'Y' || value == true || value == 'true' ? "Y" : "N";
					}
					if (field.dataType == "datetime") {
						var value = json[field.orgFieldName];
						if (value) {
							var format = field.datetimeFormat;
							var year = value.getFullYear() + "";
							var month = Utilities.paddingZero(value.getMonth() + 1, 2);
							var day = Utilities.paddingZero(value.getDate(), 2);
							var hour = Utilities.paddingZero(value.getHours(), 2);
							var min = Utilities.paddingZero(value.getMinutes(), 2);
							var sec = Utilities.paddingZero(value.getSeconds(), 2);
							var ms = value.getMilliseconds();

							var dValue = format.replace('yyyy', year)
							var dValue = dValue.replace('MM', month)
							var dValue = dValue.replace('dd', day)
							var dValue = dValue.replace('HH', hour)
							var dValue = dValue.replace('mm', min)
							var dValue = dValue.replace('ss', sec)
							var dValue = dValue.replace('SSS', ms)
							json[field.orgFieldName] = dValue;
						}

						// yyyyMMddHHmmssSSS
					}
				}
				return json
			};
			gridView.getSaveJson = function() {
                this.commit();
				var rowCount = this.getDataSource().getRowCount();
				var arr = [];
				for (var i = 0; i < rowCount; i++) {
					var stat = this.getDataSource().getRowState(this.getDataRow(i));
					if (stat != "none") {
						var json = this.getJsonRow(i);
						json.stat = stat.substring(0, 1);
						arr.push(json);
					}

				}
				return arr;
			};
			gridView.getCheckedJson = function() {
				var rows = this.getCheckedItems(true);
				var arr = [];
				for (var i = 0; i < rows.length; i++) {
					arr.push(this.getJsonRow(rows[i]));
				}
				return arr;
			};
			gridView.getCurrentJson = function() {
				var current = this.getCurrent();
				if (current.itemIndex > -1) {
					return this.getJsonRow(current.itemIndex);
				} else
					return null;
			};
			gridView.getJsonRow = function(row) {
                this.commit();
				var itemIndex = row;
				row = this.getDataRow(row);
				var stat = this.getDataSource().getRowState(row);
				var json = this.getDataSource().getJsonRow(row);
				json.itemIndex = itemIndex;
				json.stat = stat.substring(0, 1)
				if (json.stat == "n")
					json.stat = "";

				// check boolean

				this.normalize(json);

				return json;
			};
			gridView.getJsonRows = function() {

				var cnt = this.getItemCount();
				var arr = [];
				for (var i = 0; i < cnt; i++) {
					arr.push(this.getJsonRow(i));
				}
				return arr;
				// return this.getDataSource().getJsonRows();
			};
			gridView.getRowCount = function() {
				var rowCount = this.getDataSource().getRowCount();
				return rowCount;
			};
			gridView.removeRow = function(row) {
                this.commit();
				row = this.getDataRow(row);
				this.getDataSource().removeRow(row);
			};
			gridView.removeRows = function(rows) {
                this.commit();
				var arr = [];
				for (var i = 0; i < rows.length; i++) {
					var row = this.getDataRow(rows[i]);
					arr.push(row);
				}
				if (arr.length)
					this.getDataSource().removeRows(arr);
			};
			gridView.removeCheckRow = function() {
				var rows = this.getCheckedItems(true);
				this.removeRows(rows);
			};
			
			gridView.pagination = this.pagination;
			if (this.pagination) {
				this.createPagination();
			}
			
            
            gridView.exportExcel = function(excelName) {
                this.exportGrid({
                    type: "excel",
                    target: "local",
                    fileName: excelName,
                    allColumns : true,
                    showProgress: true,
                    progressMessage: "엑셀 Export중입니다.",
                    indicator: "hidden",
                    footer: "hidden",
                });
            };
            gridView.importExcel = function() {
                var that = this;
                var inpExcelImport = $("#inpExcelImport");
                if (inpExcelImport.length == 0) {
                    inpExcelImport = $('<input type="file" style="display:none" id="inpExcelImport"/>');
                    inpExcelImport.change(function(e) {
                        var dataProvider = that.getDataSource();
                        var files = e.target.files;
                        var f = files[0];
                        var fixdata = function(data) {
                            var o = "", l = 0, w = 10240;
                            for (; l < data.byteLength / w; ++l) o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w, l * w + w)));
                            o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w)));
                            return o;
                        };
                        var process_wb = function(wb) {
                            var output = "";
                            output = to_json(wb);
                            var sheetNames = Object.keys(output);
                            if (sheetNames.length > 0) {
                                var colsObj = output[sheetNames][0];
                                if (colsObj) {
                                    dataProvider.fillJsonData(output, { rows: sheetNames[0] ,fillMode: "append" })
                                }
                            }
                        };
                        var to_json = function(workbook) {
                            var result = {};
                            var sheet = workbook.Sheets[workbook.SheetNames[0]];
                            var index = 0;
//                            var fNames = [] ;
//                            var cNames =[];
                            var cols = that.getColumns();
                            for(col in sheet){
                                if(col == "!ref")
                                    continue;
                                if(col.slice(-1) != 1)
                                    break;
                                var colData = sheet[col];
                                for(var i=0;i<cols.length;i++){
                                    if(cols[i].displayText == sheet[col].h){
                                        var fieldName =cols[i].fieldName;
                                        sheet[col].h= fieldName;
                                        sheet[col].r= "<t>"+fieldName+"</t>";
                                        sheet[col].v= fieldName;
                                        sheet[col].w= fieldName;
                                    }
                                }
                                
                                
//                                cNames.push(col);
//                                fNames.push(fieldName);               
                            }
//                            var arr = sheet[cNames[0]];
//                            for(var i=1;i<arr.length;i++){
//                                var data={};
//                                for(var j=0;j<fNames.length;j++){
//                                    data[fNames[j]] = sheet[cNames[j]][i];
//                                }
//                            }
                            workbook.SheetNames.forEach(function(sheetName) {
                                var roa = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[sheetName], {});
                                if (roa.length > 0) {
                                    result[sheetName] = roa;
                                }
                            });
                            return result;
                        };
                        var reader = new FileReader();
                        var name = f.name;
                        reader.onload = function(e) {
                            var data = e.target.result;

                            //var workbook = XLSX.read(data, { type: 'binary' });
                            var arr = fixdata(data);
                            workbook = XLSX.read(btoa(arr), { type: 'base64' });

                            process_wb(workbook);
                            /* DO SOMETHING WITH workbook HERE */
                        };
                        //reader.readAsBinaryString(f);
                        reader.readAsArrayBuffer(f);
                    });
                }

                inpExcelImport.click();
            };
            
            
            
			window[this.gridId] = gridView;
			var gridCallback = this.gridWrap.attr("data-grid-callback");
			if (!gridCallback)
				gridCallback = "onGridLoad";

			if (window[gridId + "_load"] && typeof (window[gridId + "_load"]) == "function") {
				window[gridId + "_load"](this, this.gridId);
			} else if (gridCallback && window[gridCallback] && typeof (window[gridCallback]) == "function") {
				window[gridCallback](this, this.gridId);
			}

		}
	};
}
function createGrid(grd) {
	var grid = createGridObject(grd);

	grid.loadXml();
}
function _onGridButtonClick(elem) {
	var data = $(elem).data();
	var gridView = window[data.gridId];
	if (gridView)
		gridView.onGridButtonClick(gridView, data);
}