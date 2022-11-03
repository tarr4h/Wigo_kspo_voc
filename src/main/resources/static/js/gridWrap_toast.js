$(document).ready(function() {
    var divGrd = $("[data-type=grid]");
    for (var i = 0; i < divGrd.length; i++) {
        var grd = $(divGrd[i]);
        initGrid(grd);
    }
    var treeList = $("[data-type=gridTree]");
    for (var i = 0; i < treeList.length; i++) {
        var tree = $(treeList[i]);
        initGrid(tree);
    }
    
    
//    var smartResize = {
//		init : function(){
//			var self = this;
//			$(window).on("resize", function(){
//				self.handler(); // 윈도우의 사이즈가 변하면 handler() 함수를 실행
//			});
//		},
//		timeOut : '',
//		handler : function(){
//			var self = this;
//			if(self.timeOut) clearTimeout(self.timeOut) 
//				//setTimeOut 이 걸려있다면 클리어
//			self.timeOut = setTimeout(self.action, 150); 
//				// 150ms 동안 이벤트가 반복 실행 되지 않으면 action() 함수를 실행
//		},
//		action : function(){
//			
//			var arr = window["gridArray"];
//			for(var i=0;i<arr.length;i++){
//				try{
//					arr[i].refresh();
//				}catch(e){
//					
//				}
//				
//				
//				
//			}
//		}
//	};
// 	
//	smartResize.init();
	divGrd.each(function(){
			const $this = $(this);
			$this.addClass("btnTopMargin");
			var divResize = {
			width : $this.width(),
			height : $this.height(),
			init : function(){
				var self = this;
				if(self.chkTimeout)
					clearTimeout(self.chkTimeout);
				if($this.width && $this.height){
					var wd =  $this.width();
					var ht = $this.height();
					if(this.width != wd || this.height != ht){
						this.handler();
					}
					this.width = wd;
					this.height = ht;	
				}
				
				self.chkTimeout = setTimeout(function(){
					self.init();
				}, 100);
			},
			timeOut : '',
			chkTimeout : '',
			handler : function(){
				var self = this;
				if(self.timeOut) clearTimeout(self.timeOut) 
					//setTimeOut 이 걸려있다면 클리어
				self.timeOut = setTimeout(self.action, 150); 
					// 150ms 동안 이벤트가 반복 실행 되지 않으면 action() 함수를 실행
			},
			action : function(){
				try{
					var gid = $this.attr("data-grid-id");
					if(window[gid])
						window[gid].refresh();
				
				}catch(e){
					console.error(e);
				}
			}
		};
		divResize.init();
	});
});

var _gridHeaderHeight = 35;
var _gridPaginationHeight = 28;
var _defaultRecordCountPerPage = 30;
tui.Grid.applyTheme('crm',{
//	outline : {
//		showVerticalBorder : true,
//		border : '#CCCCCC'
//	},
	area : {
		header : {
			 background: '#ffeded',
	            border: '#CCCCCC'
		},
		body : {
			 background: '#eeeeee',
	            border: '#CCCCCC'
		},
		summary : {
			 background: '#eeeeee',
	            border: '#CCCCCC'
		}
	},
      selection: {
            background: '#4daaf9',
            border: '#004082'
          },
          scrollbar: {
            background: '#f5f5f5',
//          border: '#ccc',
            thumb: '#d9d9d9',
            active: '#c1c1c1'
          },
          row: {
            odd: {
              background: '#ffffff',
              text : '#333333'
            },
            even: {
              background: '#f6f6f4',
              text : '#333333'
            },
            
            hover: {
              background: '#E8E8E8',
              text : '#333333'
            }
          },
          cell: {
            normal: {
              background: '#fbfbfb',
              border: '#ccc',
              text : '#333333',
              showVerticalBorder: true
            },
            header: {
              background: '#ffeded',
              border: '#ccc',
              text : '#333333',
              showVerticalBorder: true
            },
            summary : {
                background: '#F6F6F6',
                border: '#cccccc',
                text : '#333333',
                showVerticalBorder: true,
                showHorizontalBorder : true
              },
            rowHeader: {
                even: {
                      background: '#eeeeee',
                      text : '#333333'
                    },
             odd:{
                 background: '#ffffff',
                 text : '#333333',
             },
//            background: '#eee',
              border: '#ccc',
              showVerticalBorder: true
            },
            editable: {
              background: '#fbfbfb'
            },
            selectedHeader: {
              background: '#d8d8d8'
            },
            focused: {
              border: '#418ed4'
            },
            disabled: {
              text: '#b0b0b0'
            }
          }
        });

function initGrid(grd) {
    var config = createGrid(grd);
}
function createGridPaginationHtml(gridId){
	
	
	
	/*
	<div class="mPag1">
					<div class="mCount1">총 <strong>5</strong>건</div>

					<span title="처음 리스트" class="first">처음으로</span>
					<span title="이전 리스트" href="###" class="prev">이전</span>
					<!-- able 버튼
						<a title="처음 리스트" href="###" class="first">처음으로</a>
						<a title="이전 리스트" href="###" class="prev">이전</a>
					-->
					<a title="현재페이지" href="###" class="active">1</a>
					<a title="2 페이지" href="###">2</a>
					<a title="3 페이지" href="###">3</a>
					<a title="4 페이지" href="###">4</a>
					<a title="5 페이지" href="###">5</a>
					<a title="6 페이지" href="###">6</a>
					<a title="다음 리스트" href="###" class="next">다음</a>
					<a title="마지막 리스트" href="###" class="last">마지막으로</a>
					<!-- disabled 버튼
						<span title="다음 리스트" class="next">다음</span>
						<span title="마지막 리스트" class="last">마지막으로</span>
					-->
					<select class="select" title="갯수선택">
						<option>10</option>
						<option>20</option>
						<option>30</option>
					</select>
				</div>
	
	 */
	
    var html = '';
    html +='<div class="mPag1" id="'+gridId+'_pagination" data-grid-id="'+gridId+'">';
    html += '<div class="mCount1">총 <strong data-grid-id="'+gridId+'" data-pagination-type="totalCount">0</strong>건</div>';
    html += '<a href="#;" title="처음 리스트" class="first" data-pagination-type="first"  data-grid-id="'+gridId+'">처음으로</a>';
    html += '<a href="#;" title="이전 리스트"  class="prev"  data-pagination-type="prev"  data-grid-id="'+gridId+'">이전</a>';
//    html += '<span id="pagination'+gridId+'"><span>';
    html += '<a title="다음 리스트" href="#;" class="next" data-pagination-type="next"  data-grid-id="'+gridId+'">다음</a>';
    html += '<a title="마지막 리스트" href="#;" class="last" data-pagination-type="last"  data-grid-id="'+gridId+'">마지막으로</a>';
    html +='                            <select class="select" data-pagination-type="recordCountPerPage" data-grid-id="'+gridId+'">                                                                                  ';
    html +='                                <option value="10">10</option>                                                                            ';
    html +='                                <option value="20">20</option>                                                                            ';
    html +='                                <option value="30" selected >30</option>                                                                            ';
    html +='                                <option value="50">50</option>                                                                            ';
//    html +='                                <option value="100">100</option>                                                                            ';
//    html +='                                <option value="200">200</option>                                                                            ';
//    html +='                                <option value="300">300</option>                                                                            ';
//    html +='                                <option value="400">400</option>                                                                            ';
//    html +='                                <option value="500">500</option>                                                                            ';
//    html +='                                <option value="1000">1000</option>                                                                            ';
    html +='                            </select>                                                                                                   ';
    html += '</div>';
    return html;
}

function createGridPaginationHtml2(gridId){
   var html = '';
    html +='<div class="mPag1" id="'+gridId+'_pagination" data-grid-id="'+gridId+'">';
    html += '<div class="mCount1">총 <strong data-grid-id="'+gridId+'" data-pagination-type="totalCount">0</strong>건</div>';
    html += '<a style="visibility:hidden" title="처음 리스트" class="first" data-pagination-type="first"  data-grid-id="'+gridId+'">처음으로</a>';
    html += '</div>';
    return html;
}

function ceatePaginationEvent(gridId){
    var $div = $("#"+gridId+"_pagination");
    $div.find('[data-pagination-type="first"]').click(function(){
        var grid = window[gridId];
        if(!grid)
            return;
        var curPage = grid.getCurrentPage();
        if(!curPage)
            return;
        grid.movePage(1);
    });
    $div.find('[data-pagination-type="prev"]').click(function(){
        var grid = window[gridId];
        if(!grid)
            return;
        var curPage = grid.getCurrentPage();
        if(!curPage)
            return;
        if(curPage<=1)
            return;
        grid.movePage(curPage - 1);
    });
    $div.find('[data-pagination-type="next"]').click(function(){
        var grid = window[gridId];
        if(!grid)
            return;
        var curPage =Number(grid.getCurrentPage());
        if(!curPage)
            return;
        var totalPageCount = grid.getTotalPageCount();
        
        if(curPage > totalPageCount)
            return;
        grid.movePage(curPage + 1);
    });
    $div.find('[data-pagination-type="last"]').click(function(){
        var grid = window[gridId];
        if(!grid)
            return;
        var totalPageCount = grid.getTotalPageCount();
        if(!totalPageCount)
            return;
        grid.movePage(totalPageCount);
    });
    $div.find('[data-pagination-type="recordCountPerPage"]').change(function(){
        var grid = window[gridId];
        if(!grid)
            return;
        var recordCountPerPage = $(this).val();
        grid.setPerPage(recordCountPerPage);
    });
    
    $div.find('input[data-pagination-type="pageNo"]').keydown(function(e){
        try {
            if (e.which == 13) {
                e.preventDefault();
            var grid = window[gridId];
            if(!grid)
                return;
            var page = Number($(this).val());
            if(!page)
                return;
            var totalPageCount = grid.getTotalPageCount();
            if(page>totalPageCount)
                return;
            if(page<1)
                return;
            grid.movePage(page);
        if(!totalPageCount)
            return;
        grid.movePage(totalPageCount);
                
        }
        } catch (e) {
            console.error(e);
        }
    });
    
    
    
    
}
function setPaginationInfo(gridId,pagination){
    var currentPageNo = pagination.page;
    var recourdCountPerPage = pagination.perPage;
    var totalCount = pagination.totalCount;
    var totalPageCount  = parseInt(totalCount/recourdCountPerPage);
    if(totalCount % recourdCountPerPage > 0)
        totalPageCount++;
    
    var dispPage = 5;
   var fPage = 0;
   var lPage = 0; 
   if(currentPageNo){
	fPage = parseInt((currentPageNo-1) /dispPage) * dispPage + 1
	}
    lPage =  fPage + dispPage - 1;
    
    if(lPage> totalPageCount)
    	lPage = totalPageCount;

	var grid = window[gridId];// grid.movePage(page);;
 	var nBtn = $("[data-pagination-type=next][data-grid-id="+gridId+"]");
 	$("[data-pagination-type=paging][data-grid-id="+gridId+"]").remove();
 	for(var i=fPage;i<=lPage;i++){
		var cls = ""; 
		if(i==currentPageNo)
			cls = "active";
		var pg = $('<a data-grid-id="'+gridId+'" data-page-no="'+i+'" data-pagination-type="paging" title="'+i+' 페이지" href="#;" class="'+cls+'">'+i+'</a>');
		pg.click(function(){
			var pageNo = $(this).attr("data-page-no");
			grid.movePage(pageNo);
		});
		nBtn.before(pg);	
	
	}
 	
    var $div = $("#"+gridId+"_pagination");
    var $page = $div.find('input[data-pagination-type="pageNo"]');
    $page.val(currentPageNo);
    var $totalPage = $div.find('[data-pagination-type="totalPage"]');
    $totalPage.html(Utilities.numberWithCommas(totalPageCount));
    
    var $totalCount = $div.find('[data-pagination-type="totalCount"]');
    $totalCount.html(Utilities.numberWithCommas(totalCount));
    
    var $totalCount = $div.find('[data-pagination-type="recordCountPerPage"]');
    $totalCount.val(recourdCountPerPage);
}
function setPaginationInfo2(gridId,list){
    var totalCount = list && list.length? list.length : 0 ;
    
    var $div = $("#"+gridId+"_pagination");
    var $totalCount = $div.find('[data-pagination-type="totalCount"]');
    $totalCount.html(Utilities.numberWithCommas(totalCount));

}
function createGridContextMenu(grdWrap){
    const grdData = grdWrap.data();
    if(grdData.contextMenuYn != "Y")
        return;
    grdWrap.contextmenu(function(ev){
        const gridView = window[$(this).attr("data-grid-id")];
        if(!gridView)
            return;
        
        const el = $(this).find("[data-row-key][data-column-name]:hover");
        if(!el.length )
            return;
         
        const elData = $(el[el.length-1]).data();
        
        if(gridView.createContextMenu(elData.rowKey,elData.columnName))
            ev.preventDefault();
    });
}

function createGridObject(grd) {
    
    var gridType = grd.attr("data-type");
    var gridHeight = grd.height();
    if(!gridHeight)
    {
        gridHeight = 500;
        grd.height(gridHeight);
    }
    var url = grd.attr("data-tpl-url");
    var getUrl = grd.attr("data-get-url");
    var gridId = grd.attr("data-grid-id");
    var isPost = grd.attr("data-post") != "N";
    var forceReadOnly = grd.attr("data-read-only") == "Y";
    var scrollX = grd.attr("data-scroll-x") == "Y";
    var autoResize = grd.attr("data-auto-resize") != "N";
    if(autoResize)
    	autoResize = !scrollX;
    if (!gridId)
        gridId = Utilties.uuid();
    if (window[gridId]) {
        alert("동일한 그리드 아이디가 존재합니다.");
        return false;
    }
    grd.addClass("gridView");
    pagination =( grd.attr("data-pagination") || grd.attr("data-pagenation") ) == "Y";
    var wId = gridId + "_wrap";
    grd.wrap('<div id="' + gridId + '_wrap" class="gridWrap"></div>');
    
    if(pagination)
    {
        grd.height(gridHeight-45);
        var html = createGridPaginationHtml(gridId);
        $("#"+wId).wrap('<div id="'+wId+'_top">');
        $("#"+wId).parent().append(html);
        ceatePaginationEvent(gridId);
    } else {
        grd.height(gridHeight-45);
        var html = createGridPaginationHtml2(gridId);
        $("#"+wId).wrap('<div id="'+wId+'_top">');
        $("#"+wId).parent().append(html);
    }   
    var blockUI = grd.attr("data-block-ui") =="Y";
    createGridContextMenu(grd);
    return {
        gridType : gridType,
        pagination : pagination,
        currentRow : -1,
        gridId : gridId,
        gridHeight : gridHeight,
        forceReadOnly : forceReadOnly,
        scrollX : scrollX,
        autoResize : autoResize,
        tmplUrl : url,
        getUrl : getUrl,
        gridWrap : grd,
        gridConfig : {},
        columnMap : {},
        columns : [],
        gridJson : null,
        isPost : isPost,
        blockUI : blockUI,
        getColumnInfo : function(colName) {
            return this.columnMap[colName];
        },
        loadXml : function() {
        	var grid = this;
            var tmplUrl = grid.tmplUrl;
            if(tmplUrl.indexOf("?")>0)
            	tmplUrl+="&";
            else
            	tmplUrl+="?";
            tmplUrl += "nocachingParam="+Utilities.guid();
            Utilities.ajax({
                url : tmplUrl /* +"?nocachingParam="+Utilities.guid() */,
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
            if(this.forceReadOnly)
                gridConfig.readOnly = "Y";
            if(this.autoResize)
            	this.autoResize = gridConfig.autoColumnWidth!="N"; 
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
            var width = Utilities.parseInt(col.getAttribute("width"));
            if(!width)
            	width= "auto";
            var align = col.getAttribute("textAlignment")   || col.getAttribute("align");
            var hidden = col.getAttribute("hidden") == "Y" ? "Y" : "N";
            var readOnly = col.getAttribute("readOnly") ;
            if(!readOnly){
                readOnly = this.gridConfig.readOnly == "Y" ?  "Y" : "N";
                
            }
            var editable = readOnly == "Y" ?  "N" : "Y";
            
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
                else if (type == "date" || type == "datemonth"
                        || type == "time" || type == "datetime")
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
                    pattern = "yyyyMMddHHmm";
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
                readOnly : readOnly,
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
                    else if (type == "number" || type == "int"
                            || type == "integer")
                        return "number";
                    else if (type == "checkbox")
                        return "boolean";
                    else if (type == "date" || type == "time"
                            || type == "datetime")
                        return "datetime";
                    else if (type == "combo")
                        return "data";
                    else if (type == "button")
                        return "text";
                    else if (type == "file")
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

            var direction = group.getAttribute("direction") == "V" ? "vertical"
                    : "horizontal";
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
//			col.width = parseInt(col.width);
            var jsonType = col.getGridType();
            var json = {
                name : col.field,
                header : col.name,
                align : col.align,
                resizable : col.resizableYn != 'N'
            }
            if (col.width)
            {
				json.width = col.width;
//				json.minWidth = col.width;
//				json.maxWidth= col.width;
			}    
            if (col.hidden == "Y")
                json.hidden = true;
            if (col.whiteSpace)
                json.whiteSpace = col.whiteSpace;
            else if(col.type == "html"){
                json.whiteSpace = "pre-line";
            }
            if (col.sortableYn=="Y")
                json.sortable = true;     
            var editable = (col.readOnly!="Y" && this.gridConfig.readOnly !="Y");
            
            if ( col.type.substring(0,4)=="date") {
                
                json.renderer = {
                        type : DateTimeRenderer,
                        format : col.pattern,
                        colInfo :col
                };
                if(editable){
                    if(col.type == "datetime" ){
                        json.editor = {
                                type: DateTimeEditor,
                                colInfo : col
                         }
                    }
                    else{
                        if(col.type == "date")
                        {
                            pickerType = "date";
                            format = "yyyy-MM-dd";
                        }
                        else if(col.type == "datemonth")
                        {
                            pickerType = "month";
                            format = "yyyy-MM";
                        }
                        else if(col.type == "dateyear")
                        {
                            pickerType = "year";
                            format = "yyyy";
                        }
                        json.editor = {
                                type: DateEditor,
                                colInfo : col
//                              type: 'datePicker',
//                              options: {
//                                format: format,
//                                type: pickerType
//                              }
                         }
                    }
                        
// json.editor = {
// type: 'datePicker',
// options: {
// format: 'yyyyMMddHHmm',
// timepicker: {
// layoutType: 'tab',
// showMeridiem: false,
// inputType: 'spinbox'
// }
// }
// }
                }
                json.align = "center";
            }
            else if (jsonType == "boolean") {
                json.renderer = {
                        type : BooleanRenderer,
                        editable : editable,
                };
                json.align = "center";
                if(editable){
//                  json.editor = {
//                      
//                    }
                }
            }
            else if (col.type == "int" || col.type == "number") {
                json.renderer = {
                        type : NumberRenderer,
                        colInfo : col
                };
                if(editable){
                    json.editor = {
                        type: NumberEditor,
                        colInfo : col
                      }
                }
            }
            else if (col.type == "button") {
                json.renderer = {
                        type : ButtonRenderer,
                        displayName: col.displayName ? col.displayName :( col.name=="선택"? "선택" : col.name)
                };
                json.align = "center";
            }
            else if (col.type == "file") {
                json.renderer = {
                        type : FileRenderer,
                        colInfo: col,
                };
                json.align = "center";
            }
            else if (col.type == "combo") {
                json.renderer = {
                        type : ComboRenderer,
                        colInfo: col
                };
                if(editable){
                    json.editor = {
                        type: ComboEditor,
                        colInfo : col
                      }
                }
                if(col.codeId || col.codeType){
                    //url, param, jsonBody, option
                    var data = Utilities.getObject(_code_url,{codeCd:col.codeId,codeType : col.codeType},true);
                    if(data){
                        col.codeMap = {};
                          for(var i=0;i<data.length;i++){
                              col.codeMap[data[i].comnCd] = data[i].comnCdNm;
                              data[i].codeId = data[i].comnCd;
                          }
                          col.codeList = data;
                    }
//                  Utilities.getAjax(_code_url,{codeCd:col.codeId,codeType : col.codeType},true,function(data,jqXHR){
//                      if(Utilities.processResult(data,jqXHR,"공통코드 로딩에 실패했습니다."))
//                      {
//                          col.codeMap = {};
//                          for(var i=0;i<data.length;i++){
//                              col.codeMap[data[i].codeCd] = data[i].codeNm;
//                              data[i].codeId = data[i].codeCd;
//                          }
//                          col.codeList = data;
//                      }
//                  });
                
                } else if(col.codeData){
					
				}
                
                
            }
            else if(col.type == "html"){
//              json.renderer ={
//                      type : HtmlRenderer,
//                      colInfo : col
//                      
//              }
                json.formatter = function(rowInfo){
                    var row = rowInfo.row;
                    var colName = rowInfo.column.name;
                    var value = rowInfo.value;
                    var gridView = window[gridId];
                    var colInfo = gridView.gridWrapper.columnMap[colName];
                    try{
                       return gridView.htmlRenderCallback(row.rowKey,colInfo ,value);
                    }catch(e){
                       return value;
                    }
                    
                }
            }
            else if(col.type == "phone"){
                json.renderer ={
                        type : PhoneNumberRenderer,
                        colInfo : col
                }
                if(editable){
                    json.editor = {
                            type: PhoneNumberEditor,
                            colInfo: col    
                    };
                }
            }
            else if(col.type == "time"){
                json.renderer = {
                        type : TimeRenderer,
                        format : col.pattern,
                        colInfo :col
                };
                if(editable){
                    json.editor = {
                        type: TimeEditor,
                        colInfo : col
                    };
                }
            }
            else {
                if(!col.whiteSpace){
                    json.renderer ={
                        type : EzTextRenderer,
                        colInfo : col
                    }    
                }
                                
                if(editable){
                    json.editor = {
                            type: EzTextEditor,
                            colInfo: col    
                    };
                }
                
            }
            if(this.scrollX)
            	json.minWidth = col.width;
            return json;
        },
        getLayoutJson : function(columns) {
            if (!columns)
                columns = this.columns;
            var cols = [];
            for (var i = 0; i < columns.length; i++) {
                var col = columns[i];
                var json = null;
                if (col.tagName == "group")
                    this.getGroupLayout(col,cols);
                else{

                }
            }
            return cols;
        },
        getGroupLayout : function(col,layouts) {
            if (col.hidden == "Y")
                return null;
            var arr = [];
            
            for(var i=0;i<col.items.length;i++){
                var item = col.items[i];
                var name = "";
                if(item.tagName == "column"){
                    name = item.field;
                }
                else{
                    name = item.name;
                    this.getGroupLayout(item,layouts);
                }
                arr.push(name);
            }
            
            var json = {
                    header : col.name,
                    name : col.name,
                    childNames : arr
            };
            layouts.push(json);
            return json;
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
            var headerRows = meta.headerRows ? meta.headerRows : 1;
            var container = this.gridWrap[0];
            var bodyHeight = this.gridHeight ? this.gridHeight : 500;
            var rowSelection = meta.rowSelection;
            var shwoFooter = meta.showFooter == "Y";
            
            const dataSource = {
                api : {
                    readData : {
                        url : this.getUrl,
                        method : 'GET'
                    }
                },
                initialRequest : false
            };
            var rowHeaders = [];
            if (meta.showNumber == "Y")
            {
//              rowHeaders.push("rowNum");
                var rowNum = {
                      type: 'rowNum',
                      renderer: {
                        type: RowNumberRenderer
                      }
                };
//              rowHeaders.push(rowNum);
            }
//          if (meta.showCheckbox == "Y") {
//              rowHeaders.push("checkbox")
//          }
//          
            var pageHeightOffset = this.pagination ? _gridPaginationHeight : 0;
//          _gridHeaderHeight += _gridHeaderHeight; 
            
            var options = {
                el : container,
                data : dataSource,
                minRowHeight : 35,
                rowHeight : 'auto',
//              bodyHeight : bodyHeight - pageHeightOffset /*fitToParent*/, 
                bodyHeight : 'fitToParent',
                // data: null,
                 scrollX : this.scrollX, 
                // scrollY : false,
                // columns : jsonColumns,
                
                
                 heightResizable : false,
                 columnOptions : {},
            };
            if(this.pagination){
//              options.pageOptions = {
//                  perPage : _defaultRecordCountPerPage
//              }
            }
            if(meta.rowSelection =="Y"){
                options.selectionUnit = "row"; 
            }
            if(meta.headerRows>1){
                options.header={
                    height: meta.headerRows * _gridHeaderHeight
                };
            }
            if (rowHeaders.length)
                options.rowHeaders = rowHeaders;
            
            if(this.gridConfig.showCheckbox  =="Y")
            {
                options.contextMenu = function(a,b,c){
                    return [
                        [
                            {
                                name: 'checkSelection',
                                label: "선택 체크",
                                action: function(ev){
                                    var grid = window[gridId];
                                    var rg = grid.getSelectionRange();
                                    if(rg == null){
                                        var row = grid.getFocusedCell();
                                        if(!row)
                                          return;
                                        grid.check(row.rowKey);
                                    }else{
                                        var startIndex = rg.start[0];
                                        var endIndex = rg.end[0];
                                        for(var i=startIndex;i<=endIndex;i++){
                                            var json = grid.getRowAt(i);
                                            grid.check(json.rowKey);
                                        }
                                    }
                                    

                                },
                            },
                            {
                                name: 'checkSelection',
                                label: "선택 체크 해제",
                                action: function(ev){
                                    var grid = window[gridId];
                                    var rg = grid.getSelectionRange();
                                    if(rg == null){
                                        var row = grid.getFocusedCell();
                                        if(!row)
                                          return;
                                        grid.uncheck(row.rowKey);
                                    }else{
                                        var startIndex = rg.start[0];
                                        var endIndex = rg.end[0];
                                        for(var i=startIndex;i<=endIndex;i++){
                                            var json = grid.getRowAt(i);
                                            grid.uncheck(json.rowKey);
                                        }
                                    }
                                    

                                },
                            }
                        ],
                    ];
                }
            }
            else {
                options.contextMenu = function(a,b,c){
                    return [
                        [
                            
                            {
                                name: 'copy',
                                label: "복사",
                                action: 'copy',
                            },
                            {
                                name: 'copyColumns',
                                label: "행복사",
                                action: 'copyColumns',
                            },
                            {
                                name: 'copyRows',
                                label: "열복사",
                                action: 'copyRows',
                            },
                            
                        ],
                    ];
                }
            }
            if(shwoFooter){
                var summary = {
                     height: 30,
                    position: 'top', // or 'top'
                };
                var columnContent = {};
                
                var fnTemplate = function(gridWrap,col,colName,sumType,valueMap){
                    return {
                        template : function(valueMap){
                            if(sumType == "sum")
                                return Utilities.numberWithCommas(`${valueMap.sum}`);
                            else if(sumType == "avg")
                                return Math.round(`${valueMap.avg}`);
                            else if(sumType == "min")
                                return Utilities.numberWithCommas(`${valueMap.min}`);
                            else if(sumType == "max")
                                return Utilities.numberWithCommas(`${valueMap.max}`);
                            else if(sumType == "cnt")
                                return Utilities.numberWithCommas(`${valueMap.cnt}`);
                            else if(sumType == "sumTitle")
                                return "합계";
                            else if(sumType == "avgTitle")
                                return "평균";
                            else if(sumType == "calcTitle")
                                return "집계";
                            else if(sumType == "cntTitle")
                                return "수량";
                            else if(sumType == "allRgTitle")
                                return "전국";
                            else {
                                if(window[sumType]){
                                    try{
                                        var gridView = window[gridWrap.gridId];
                                        if(!gridView)
                                            return "";
                                        var summaryMap = gridView.store.summary.summaryValues;
                                        if(!summaryMap)
                                            return "";
                                      return  window[sumType](window[gridWrap.gridId],colName,sumType,valueMap,summaryMap);
                                    }catch(e){
                                        
                                    }
                                }
                            }
                                return "";
                        }
                    };
                }
                
                for(col in this.columnMap){
                    if(!this.columnMap[col])
                        continue;
                    var colInfo = this.columnMap[col] ;
                    if(!colInfo.summary)
                        continue;
                    var sumType = colInfo.summary;
                    var fName = colInfo.field;
                    columnContent[fName] = fnTemplate(this,colInfo,fName,sumType);
                }
                summary.columnContent = columnContent;
                options.summary = summary;
            }
            if(meta.frozenCount){
            	options.columnOptions.frozenCount = meta.frozenCount;
            }
            return options;
        },
        createGrid : function() {
            if (window[this.gridId]) {
                alert("동일한 그리드 아이디가 존재합니다.");
                return false;
            }
            var jsonColumns = this.convertColumnsJson();
            
            var config = this.convertGridOptions();
            
            if(this.gridType == "gridTree"){
                var cn = "";
                for(var i=0;i<jsonColumns.length;i++){
                    var c = jsonColumns[i];
                    if(c.hidden==true)
                        continue;
                    cn = c.name;
                    break;
                    
                }
                config.treeColumnOptions={
                        name : cn,
                        useCascadingCheckbox : true,
                        useIcon : true
                        
                };
            }
                
            if (this.gridConfig.showStateBar == "Y") {
                var stat = {
                        name : "stat",
                        header : " ",
                        align : "center",
                        resizable : false,
                        width: 20,
                        minWidth : 20,
                        maxWidth : 20,
                        renderer : {
                                type : StatusRenderer,
                                gridId :this.gridId
                        }
                    };
                jsonColumns.unshift(stat);
            }
            if (this.gridConfig.showNumber == "Y")
            {

                var rowCol = {
                        name : "rowNumCol",
                        header : "No",
                        align : "center",
                        resizable : false,
                        width: 40,
                        minWidth : 40,
                        renderer : {
                                type : RowIndexRenderer,
                                gridId :this.gridId
                        }
                    };
                jsonColumns.unshift(rowCol);
            }
            
            if(this.gridConfig.showCheckbox =="R"){
                var rdoCol = {
                        name : "rdo",
                        header : " ",
                        align : "center",
                        resizable : false,
                        width: 20,
                        minWidth : 20,
                        renderer : {
                                type : RadioRenderer,
                                gridId :this.gridId
                        }
                    };
                jsonColumns.unshift(rdoCol);
            }else if(this.gridConfig.showCheckbox =="Y"){
                var chkCol = {
                        name : "chkgrd",
                        header : " ",
                        align : "center",
                        resizable : false,
                        width: 30,
                        minWidth : 30,
                        renderer : {
                                type : CheckboxRenderer,
                                gridId :this.gridId
                        }
                    };
                jsonColumns.unshift(chkCol);
            }
            
            
            
            
            config.columns = jsonColumns;
            
            

            if(this.hasLayout){
                if(!config.header)
                    config.header={};
                config.header.complexColumns =  this.getLayoutJson();
            }
            
            const gridView = new tui.Grid(config);
            window[this.gridId] = gridView;
            if(!window["gridArray"])
            	window["gridArray"] = [];
            window["gridArray"].push(gridView);
            gridView.gridWrapper = this;
            gridView.gridId = this.gridId;
            gridView.pagination = this.pagination;
            gridView.gridType = this.gridType;
            gridView.getUrl = this.getUrl;
            gridView.blockUI = this.blockUI;
            gridView.isPost = this.isPost;
            gridView.reload = function(){
                var grid = this;
                this.loadUrl();
            };
            gridView.getTotalPageCount = function(){
                if(this.searchParam && this.searchParam.pagination)
                {
                    var pagination = this.searchParam.pagination;
                    var recourdCountPerPage = pagination.perPage;
                    var totalCount = pagination.totalCount;
                    var totalPageCount  = parseInt(totalCount/recourdCountPerPage);
                    if(totalCount % recourdCountPerPage > 0)
                        totalPageCount++;
                    return totalPageCount;
                }   
                else
                    return 0;
            }
            gridView.getCurrentPage=function(){
                if(this.searchParam && this.searchParam.pagination)
                    return this.searchParam.pagination.page;
                else
                    return 0;
            };
            gridView.movePage = function(page){
                if(!this.searchParam)
                    return;
                var totalPageCount = this.getTotalPageCount();
                if(page < 1 || page > totalPageCount)
                    return;
                this.searchParam.param.page = page;
                this.loadUrl(this.searchParam.url,this.searchParam.param);  
            };
            gridView.setPerPage = function(recordCountPerPage){
                if(!this.searchParam)
                    return;
                this.searchParam.param.page = 1;
                this.searchParam.param.perPage = recordCountPerPage;
                this.loadUrl(this.searchParam.url,this.searchParam.param);  
            };
            gridView.loadUrl = function(url, param) {
                if(param && !param.perPage){
                    if(this.searchParam && this.searchParam.param &&this.searchParam.param.perPage )
                        param.perPage = this.searchParam.param.perPage;
                }
                url = url  || (this.searchParam && this.searchParam.url ?this.searchParam.url : this.getUrl);
                param = param  || (this.searchParam && this.searchParam.param ?this.searchParam.param : null );
                this.clear();
                this.searchParam = {
                        url: url,
                        param:param
                };
                var grid = this;
                if(this.gridType=="gridTree"){
                    this.clear();
                    var geturl = url || this.getUrl; 
                    var contentType = this.isPost ? "application/json" : 'application/x-www-form-urlencoded; charset=utf-8'
                    Utilities.ajax({
                        contentType : 'application/x-www-form-urlencoded; charset=utf-8',
                        url : url || this.gridWrapper.getUrl, 
                        success : function(data, textStatus, jqXHR) {
                            grid.loadJson(data);
                        },
                        error : function(jqXHR, textStatus, errorThrown) {
                            
                        }
                    });
                    
                }
//              else if (param.recordCountPerPage&&this.pagination) {
//                  grid.setPerPage(parseInt(param.recordCountPerPage));
//              } else
//                  grid.readData(1, param, true);
                else {
//                  grid.readData(1, param, true);
                    if(this.blockUI)
                        this.gridWrapper.gridWrap.block({message: '<img src="/static/images/loader.gif" />' ,overlayCSS: { opacity:0.2 } ,css : {border: 'none', background: 'rgba(255, 255, 255,0)', top:'165px'}});
                     Utilities.getAjax(url,param,!!this.isPost,function(data,jqXHR){
                         grid.gridWrapper.gridWrap.unblock();
                        if(Utilities.processResult(data,jqXHR,"데이터 불러오기에 실패했습니다."))
                        {
                            grid.loadJson(data);
                        //  grid.gridWrapper.gridWrap.unblock();
                        }

                        });


//                  Utilities.ajax({
//                      contentType : 'application/x-www-form-urlencoded; charset=utf-8',
//                      url : url || this.gridWrapper.getUrl, 
//                      param : param,
//                      success : function(data, textStatus, jqXHR) {
//                          grid.loadJson(data);
//                      },
//                      error : function(jqXHR, textStatus, errorThrown) {
//                          
//                      }
//                  });
                }
                

            };
//          gridView.clear = function(){
//              this.resetData([]);
//          };
            gridView.loadJson = function(data) {
                var grid = this;
                
                
                var list = null;
//              var pagination = null;
                if (Array.isArray(data)) {
                    list = data;
                } else {
                    data = data.data;
                    list = data.contents;
                    if(this.searchParam && data.pagination)
                        this.searchParam.pagination = data.pagination;
                    if (this.pagination&& data.pagination) {
                        setPaginationInfo(this.gridId,data.pagination);
                        
                    }
                    else {
                        setPaginationInfo2(this.gridId,list);
                    }
                }
                this.resetData(list);
                
            };
             gridView.setTextColor = function(row,colName,color){
                    if(color){
                        var rowKey = this.getDataRow(row);
                        this.gridWrapper.gridWrap.find("td[data-row-key="+rowKey+"][data-column-name="+colName+"]").attr("data-text-color",color);

                    }
                }; 
            gridView.setBackColor = function(row,colName,color){
                if(color){
                    var rowKey = this.getDataRow(row);
                    this.gridWrapper.gridWrap.find("td[data-row-key="+rowKey+"][data-column-name="+colName+"]").attr("data-background-color",color);
                }
            }; 
            
            gridView.commit = function(){
                if(!this.editingInfo)
                    return;
                
                this.finishEditing(this.editingInfo.rowKey,this.editingInfo.columnName);
            };
            gridView.rollaback = function(){
                this.cancelEditing();
            };
            
            gridView.getSaveJson = function() {
                const grid = this;
                this.commit();
                /*
                var modifiedRow = this.getModifiedRows();
                var list = [];
                for(var i=0;i<modifiedRow.createdRows.length;i++){
                    var row = modifiedRow.createdRows[i];
                    row.stat = "c";
                    list.push(row);
                }
                for(var i=0;i<modifiedRow.deletedRows.length;i++){
                    var row = modifiedRow.deletedRows[i];
                    row.stat = "d";
                    list.push(row);
                }
                for(var i=0;i<modifiedRow.updatedRows.length;i++){
                    var row = modifiedRow.updatedRows[i];
                    row.stat = "u";
                    list.push(row);
                }
                list.sort(function(a,b){
                    return grid.getIndexOfRow(a.rowKey) - grid.getIndexOfRow(b.rowKey); 
                });*/
                    
                return this.getJsonRows(true);
                

            };
            gridView.getJsonRow = function(row) {
                if(row < 0 )
                    return null;
                var json = this.getRowAt(row);
                json.itemIndex = row;
                return json;
            };
            gridView.getJsonRows = function(modifiedOnly) {

                var cnt = this.getRowCount();
                var arr = [];
                for (var i = 0; i < cnt; i++) {
                    var data = this.getJsonRow(i);
                    if(modifiedOnly && !data.stat)
                        continue;
                        
                    arr.push(this.getJsonRow(i));
                }
                return arr;
                // return this.getDataSource().getJsonRows();
            };
            
            gridView.getCheckedJson = function() {
                if(this.gridWrapper.gridConfig.showCheckbox == "R"){
                    var list =  this.getCheckedRows();
                    if(list && list.length)
                        return list[0];
                    else return null;
                    
                }
                return this.getCheckedRows();
            };
            gridView.getCurrentJson = function() {
                var current = this.getFocusedCell();
                if (current.rowKey > -1) {
                    return this.getJsonRow(this.getIndexOfRow(current.rowKey));
                } else
                    return null;
            };
            gridView.getDataRow = function(index){
                if(index<0)
                    return -1;
                if(index>=this.getRowCount())
                    return -1;
                return this.getRowAt(index).rowKey;
                
            };
            var fnRemoveRow = gridView.removeRow;
            gridView.removeRow = function(row) {
                this.commit();
                var rowKey = this.getDataRow(row);
                fnRemoveRow.call(this,rowKey);
            };
            
            gridView.removeRows = function(rows) {
                this.commit();
                var arr = [];
                for (var i = 0; i < rows.length; i++) {
                    var row = this.getDataRow(rows[i]);
//                    arr.push(row);
                    arr.push(rows[i]);
                }
                if (arr.length)
                {
                    for(var i=0;i<arr.length;i++)
                    {
                        this.removeRow(arr[i]);
                    }
                }
            };
            gridView.removeCheckRow = function() {
                var rows = this.getCheckedJson();
                for(var i=0;i<rows.length;i++){
                    try{
//                      var index = this.getIndexOfRow(rows[i]);
//                      if(index>-1)
                            fnRemoveRow.call(this,rows[i].rowKey);
                    }
                    catch(e){
                        
                    }
                }
                
            };

            gridView.resetRowState = function(row) {
                var rowKey = this.getDataRow(row);
                this.setValue(rowKey,"stat","");
            };
            gridView.resetAllRowStatus = function(type) {
                var cnt = this.getRowCount();
                for(var i=0;i<cnt;i++){
                    var rowKey = this.getDataRow(i);
                    this.setValue(rowKey,"stat","");
                }
                this.clearModifiedData(type);
            };
            
            gridView.addRow = function(data) {
                this.commit();
                const addDummy = this.getRowCount() ==0;
                if(addDummy){
                    this.appendRow({});
//                  this.removeRow(0);
                }
                if(!data){
                    data = {};
                    data.stat = "c";
                }
                if(!data.stat)
                	data.stat = "c";
                var cols = this.gridWrapper.columns;

                for(var i=0;i<cols.length;i++){
                    var col = cols[i];
                    if(col.defaultValue&&!data[col.field]){
                        data[col.field] = col.defaultValue;
                    }
                }
//              data.stat = "c";
                this.appendRow(data,{focus:true});
                if(this.gridType == "gridTree"){
                    if(!data._attributes)
                        data._attributes = {expanded: true};
                    
                }
                if(addDummy){
                    this.removeRow(0);
                }
                return this.getRowCount()-1;
            };
            gridView.addRows = function(list) {
                for(var i=0;i<list.length;i++){
                    this.addRow(list[i]);
                }
            };
            gridView.addTreeRow = function(data,option) {
                this.commit();
                if(!option ){
                    option = {};
                    var parentRow = this.getCurrentJson();
                    
                    if(parentRow && this.getIndexOfRow(parentRow.rowKey)>-1){
                        option.parentRowKey = parentRow.rowKey;
                        option.focus = true;
                    }
                    else
                    {
                        return this.addRow(data);
                    }
                    
                }
                if(!data){
                    data = {};
                    
                    data.stat = "c";
                }
                var cols = this.gridWrapper.columns;
                for(var i=0;i<cols.length;i++){
                    var col = cols[i];
                    if(col.defaultValue&&!data[col.field]){
                        data[col.field] = col.defaultValue;
                    }
                }
                var idx =  this.appendTreeRow(data,option);
                this.expand(option.parentRowKey);
                this.refresh();

                return this.getRowCount()-1;
            };
            gridView.checkItem = function(rowIndex,check){
//              var rowKey = this.getDataRow(rowIndex);
//              var chkList = this.gridWrapper.gridWrap.find("input[data-row-key="+rowKey+"][type=checkbox][name=checkbox_"+gridId+"]");
//              chkList.prop("checked",!!check);
                if(check)
                {
                    

                    this.check(this.getDataRow(rowIndex));
                }
                else
                {
                    this.uncheck(this.getDataRow(rowIndex));
                }
            };
            
            
            gridView.htmlRenderCallback=function(rowKey,colInfo ,value){
                
                var json = this.getRow(rowKey);
                if(!json)
                    return null;
                var cFunction = colInfo.htmlCallback; 
                var grid = this;
                var row = this.getIndexOfRow(rowKey);
                var col = colInfo.field;
                if (window[cFunction] && typeof (window[cFunction]) == "function") {
                    return window[cFunction](grid,row,col,json,value);
                } else if (window[gridId + "_htmlRender"] && typeof (window[gridId + "_htmlRender"]) == "function") {
                    return window[gridId + "_htmlRender"](grid,row,col,json,value);
                } else if (window["onGridHtmlRender"] && typeof (window["onGridHtmlRender"]) == "function") {
                    return window["onGridHtmlRender"](grid,row,col,json,value);
                }
            };
            gridView.canRender=function(props){
                const grid = props.grid;
                if(!grid.getRowCount())
                    return false;
                var fn= "onGridCellRender";
                if(window[fn] ){
                    try{
                        if(window[fn](grid,props.columnInfo.name,grid.getRow(props.rowKey)) == false)
                            return false;
                    }
                    catch(e){
                        
                    }
                }
                return true;
            }; 
            gridView.canEdit=function(colName,rowKey,data){
                const grid = this.grid;
                var fn= "onGridCellEdit";
                if(window[fn] ){
                    try{
                        if(window[fn](grid,colName,data) == false)
                            return false;
                    }
                    catch(e){
                        
                    }
                }
                return true;
            };
            
            gridView.checkHeader=function(colname) {
                var chk = null;
                if("chkgrd" == colname){
                    var chk = this.gridWrapper.gridWrap.find("#chkgrd_"+this.gridId);
                }
                
                if(chk && chk.length){
                      chk.click();
                }
            };
            gridView.checkCell=function(colname,rowKey) {
                var chk = null;
                if("chkgrd" == colname)
                  chk = this.gridWrapper.gridWrap.find("input[type=checkbox][name=checkbox_"+this.gridId+"][data-row-key="+rowKey+"]");
                else{
                  chk = this.gridWrapper.gridWrap.find("input[type=checkbox][data-colname="+colname+"][data-row-key="+rowKey+"][data-grid-id="+this.gridId+"]");
                }
                if(chk && chk.length){
                      chk.click();
                }
                
            };
            
            gridView.checkColHeader=function(){
                var grid = this;
                var chkth = grid.gridWrapper.gridWrap.find("#chkgrd_"+gridId);
                if(chkth && chkth.length){
                    chkth.prop("checked",false);
                }
                
                if(grid.gridWrapper.gridConfig.readOnly != "Y"){
                    for(var i=0;i<grid.gridWrapper.columns.length;i++){
                        var colInfo =  grid.gridWrapper.columns[i];
                        if(colInfo.readOnly == "Y" )
                            continue;
                        if(colInfo.hidden == "Y")
                            continue;
                        if(colInfo.type != "checkbox")
                            continue;
                        var chkth = grid.gridWrapper.gridWrap.find("th.tui-grid-cell-header[data-column-name="+colInfo.field+"]");
                        
                        
                        
                        if(chkth && chkth.length){
                            var chkList = grid.gridWrapper.gridWrap.find("input[type=checkbox][data-colname="+colInfo.field+"][data-row-key][data-grid-id='"+grid.gridId+"']");
                            var chked = chkList && chkList.length;
                            for(var j=0;j<chkList.length;j++){
                                if(!chkList[j].checked){
                                    chked = false;
                                    break;
                                }
                            }
                            
                            var chk = $('#'+colInfo.field+'_' + grid.gridId);
                            chk.prop("checked",chked);
                        }
                        
                    }
                }
            };
            
            gridView.copyColumns = function(){
                return this.dispatch("copyColumns",this.store);
            };
            gridView.copyRows = function(){
                return this.dispatch("copyColumns",this.store);
            };
            
            gridView.getGridEl = function(){
				return this.gridWrapper.gridWrap;
			};
            gridView.refresh =function(){
				
				this.refreshLayout();
				if(!this.gridWrapper.autoResize)
					return ;
				var divWidth = this.getGridEl().width();
				var scrollWidth = this.getGridEl().find(".tui-grid-scrollbar-right-top").width();
				var totalWidth = 0;
				var systemWidth = 0;
				var colList = this.getColumns();
				for(var i=0;i<colList.length;i++){
					var colInfo = colList[i];
					if(colInfo.hidden)
						continue;
					totalWidth += colInfo.width || colInfo.minWidth;
					if(colInfo.name == "chkgrd" || colInfo.name == "rowNumCol" || colInfo.name == "stat") 
						systemWidth += colInfo.width || colInfo.minWidth;
					
				}
				var curWidth = divWidth - scrollWidth - systemWidth;
				var colWidth = totalWidth - systemWidth;
				var rate =  curWidth / colWidth;
				
				if(rate == 1)
					return;
				var widthArr = [];
				for(var i=0;i<colList.length;i++){
					var colInfo = colList[i];
					if(colInfo.hidden)
					{
						widthArr.push(0);
						continue;
					}	
					if(colInfo.name == "chkgrd" || colInfo.name == "rowNumCol" || colInfo.name == "stat") 
					{
						widthArr.push(parseInt(colInfo.width || colInfo.minWidth));
						continue;
					}	
					var wd = parseInt(rate * (colInfo.width || colInfo.minWidth));
					widthArr.push(wd);
				}
				this.resetColumnWidths(widthArr);
//				console.log(rate);
				
			};
            
            
            
            
// 이벤트
           gridView.createContextMenu=function(rowKey,colName){
             alert(rowKey + "-" + colName);
                return false;
           };
            gridView.on("editingStart",function(ev){
                
                var grid = ev.instance;
                var colInfo = grid.gridWrapper.getColumnInfo(ev.columnName);
                var data = grid.getRow(ev.rowKey);
                if(!grid.canEdit(ev.columnName,ev.rowKey,data)){
                    ev.stopped = true;
                    return ;
                }
                if(colInfo ){
                    
                    if(data.stat != "c" && colInfo.Pk == "Y")
                    {
                        ev.stopped = true;
                        return;
                    }
                }
                grid.editingInfo={
                        rowKey : ev.rowKey,
                        columnName: ev.columnName,
                        value : ev.value
                };
            });
            gridView.on("editingFinish",function(ev){
                var grid = ev.instance;
                var editingInfo = grid.editingInfo;
                grid.editingInfo=null;
                if(!editingInfo)
                    return;
                var columnInfo =  {itemIndex : grid.getIndexOfRow(editingInfo.rowKey)
                    ,dataRow: editingInfo.rowKey 
                    ,column: editingInfo.columnName
                    ,Index : grid.getIndexOfColumn(editingInfo.columnName)
                    ,fieldIndex : editingInfo.columnName
                };
                grid.onEditCommit(grid , columnInfo , editingInfo.value,ev.value);

            });
            
            gridView.on('onGridMounted', function(ev) {
                var grid = ev.instance;
                $(grid.gridEl).ready(function(){
                	grid.refresh();
                });
                
                if(grid.gridWrapper.gridConfig.showCheckbox == "Y"){
                    var chkth = grid.gridWrapper.gridWrap.find("th.tui-grid-cell-header[data-column-name=chkgrd]");
                    if(chkth && chkth.length){
					const lbl = $('<label style="margin-left:3px" class="mCheckbox1"><input id="chkgrd_'+gridId+'" type="checkbox"><span class="label">&nbsp;</span></label>');
//                        var chk = $('<input type="checkbox" id="chkgrd_'+gridId+'"/>');
						var chk = lbl.find("input");
                        chkth.html("");
                        chkth.append(lbl);
                        lbl.click(function(e){
                            e.stopPropagation();
                            
                            var checked = chk[0].checked;
                            if(checked)
                                grid.checkAll();
                            else
                                grid.uncheckAll();
                            var chkList = grid.gridWrapper.gridWrap.find("input[type=checkbox][name=checkbox_"+gridId+"]");
                            chkList.prop("checked",checked);
                        });
                        
                    }
                }
//                
//                if(grid.gridWrapper.gridConfig.readOnly != "Y"){
//                    for(var i=0;i<grid.gridWrapper.columns.length;i++){
//                        var colInfo =  grid.gridWrapper.columns[i];
//                        if(colInfo.readOnly == "Y" )
//                            continue;
//                        if(colInfo.hidden == "Y")
//                            continue;
//                        if(colInfo.type != "checkbox")
//                            continue;
//                        var chkth = grid.gridWrapper.gridWrap.find("th.tui-grid-cell-header[data-column-name="+colInfo.field+"]");
//                        
//                        
//                        
//                        if(chkth && chkth.length){
//                            var chkList = grid.gridWrapper.gridWrap.find("input[type=checkbox][data-colname="+colInfo.field+"][data-row-key][data-grid-id='"+grid.gridId+"']");
//                            var chked = chkList && chkList.length;
//                            for(var j=0;j<chkList.length;j++){
//                                if(!chkList[i].checked){
//                                    chked = false;
//                                    break;
//                                }
//                            }
//                            
//                            var chk = $('<input type="checkbox" data-colname="'+colInfo.field+'" data-grid-id="'+gridId+'"  id="'+colInfo.field+'_'+gridId+'"/>');
//                            chkth.append(chk);
//                            if(chked)
//                                chk.prop("checked",chked);
//                            
//                            
//                            chk.click(function(e){
//                                e.stopPropagation();
//                                var colInfo = $(this).data();
//                                var checked = this.checked;
//                                var chkList = grid.gridWrapper.gridWrap.find("input[type=checkbox][data-colname="+colInfo.colname+"][data-row-key][data-grid-id='"+colInfo.gridId+"']");
//                                chkList.prop("checked",checked);
//                                var cnt = grid.getRowCount();
//                                var chkData = $(this).data();
//                                for(var i=0;i<cnt;i++){
//                                    var rowData = grid.getRowAt(i);
//                                    grid.setValue(rowData.rowKey,chkData.colname,checked?"Y":"N");
//                                }
//                            });
//                        }
//
//                        
//                    }
//                }

                //grid.gridWrapper.gridConfig

                
                
                grid.loaded = true;
                var gridCallback = grid.gridWrapper.gridWrap.attr("data-grid-callback");
                if (!gridCallback)
                    gridCallback = "onGridLoad";

                if (window[gridId + "_load"] && typeof (window[gridId + "_load"]) == "function") {
                    window[gridId + "_load"](grid, grid.gridId);
                } else if (gridCallback && window[gridCallback] && typeof (window[gridCallback]) == "function") {
                    window[gridCallback](grid, grid.gridId);
                }
                
            });
            
            
            gridView.on('onGridUpdated', function(ev) {
            	
                var grid = ev.instance;
                var gridId = grid.gridId;
                grid.refresh();
                grid.checkColHeader();
                
                
                
                if (window[gridId + "_dataLoaded"] && typeof (window[gridId + "_dataLoaded"]) == "function") {
                    var data = grid.getData();
                    window[gridId + "_dataLoaded"](grid,grid.getJsonRows() ,data);
                } else if (window["onGridDataLoaded"] && typeof (window["onGridDataLoaded"]) == "function") {
                    var data = grid.getData();
                    window["onGridDataLoaded"](grid,grid.getJsonRows() ,data);
                }
                
            });
            
            gridView.on('beforeChange', function(ev){
               var org = ev.origin;
               var list = ev.changes;
               var grid = ev.instance;
               if(org != "paste")
                    return;
               if(grid.gridWrapper.gridConfig.readOnly=="Y"){
                ev.stop();
                return;
               }
               for(var i=0;i<list.length;i++){
                   var colInfo = grid.gridWrapper.columnMap[list[i].columnName ];
                   if(!colInfo)
                       continue;
                   if(colInfo.editable != "Y" )
                       continue;
                    var row = grid.getRow(list[i].rowKey);
                    
                    if(row.stat != "c" && colInfo.Pk == "Y")
                        continue;
                    row[list[i].columnName ] = $.trim( list[i].nextValue.replace(/\n /g,'').replace(/\n/g,'').replace(/\r/g,''));
                    if(!row.stat)
                        row.stat = "u";
                    grid.setRow(list[i].rowKey,row);
                }
               ev.stop();
            });
            
            gridView.on('afterChange', function(ev){
                var list = ev.changes;
                for(var i=0;i<list.length;i++)
                {
                    var colInfo = list[i];
                    if(colInfo.columnName == "stat")
                        continue;
                    if(colInfo.columnName == "rowNumCol")
                        continue;
                    if(colInfo.columnName == "chkgrd")
                        continue;
                    
                    var rowKey = colInfo.rowKey;
                    ev.instance.setModified(rowKey);

                }
            });
            
            gridView.on("beforePageMove",function(ev){
                ev.instance.clear();
            });
            gridView.on("afterPageMove",function(ev){
                var grid = ev.instance;
            });
            gridView.on("error",function(ev){
                var grid = ev.instance;
            });
            gridView.setModified = function(rowKey){
                var value = this.getValue(rowKey, "stat");
                if(!value)
                    this.setValue(rowKey, "stat", "u", false);
            };
            
            gridView.on('dblclick', function(ev) {
                  var grid = ev.instance;
                  var rowKey = ev.rowKey;
                  var columnName = ev.columnName;
                  var targetType = ev.targetType;
                  if(!columnName)
                      return;
                  var clickedData = {
                          column : columnName,
                          field : columnName,
                          index : grid.getIndexOfColumn(columnName),
                          itemIndex : grid.getIndexOfRow(rowKey),
                          dataRow: rowKey 
                  };
                  clickedData.cellType = ev.targetType == "columnHeader" ?  "header" : "cell"

                  grid.onCellDblClicked(grid, clickedData);
            });

            
            gridView.on('click', function(ev) {

                  var grid = ev.instance;
                  var rowKey = ev.rowKey;
                  var columnName = ev.columnName;
                  var targetType = ev.targetType;
                  if(!columnName)
                      return;
                 
                  var clickedData = {
                          column : columnName,
                          field : columnName,
                          index : grid.getIndexOfColumn(columnName),
                          itemIndex : grid.getIndexOfRow(rowKey),
                          dataRow: rowKey 
                  };
                  clickedData.cellType = ev.targetType == "columnHeader" ?  "header" : "cell";
                  
                  if(clickedData.cellType == "cell"){
                      grid.checkCell(columnName,rowKey);
                  }
                  else{
                      grid.checkHeader(columnName);
                  }
                  if(columnName == "chkgrd"){
                      
                      
                      
                      return;
                  }
                  grid.onCellClicked(grid, clickedData);
            });

            
//          gridView.on('focusChange', function(ev) {
//                var grid = ev.instance;
//                var prevRowKey = ev.prevRowKey;
//                var oldRow = grid.getIndexOfRow(prevRowKey);
//                var rowKey = ev.rowKey;
//                this.currentRowKey = rowKey;
//                var newRow = grid.getIndexOfRow(rowKey);
//                var columnName = ev.columnName;
//                if(oldRow != newRow)
//                    grid.onCurrentRowChanged(grid, oldRow, newRow);
//          });
            
            gridView.on('focusChange', function(ev) {
                  var grid = ev.instance;
                  var prevRowKey = ev.prevRowKey;
                  var oldRow = grid.getIndexOfRow(prevRowKey);
                  var rowKey = ev.rowKey;
                  this.currentRowKey = rowKey;
                  var newRow = grid.getIndexOfRow(rowKey);
                  var columnName = ev.columnName;
                  if(oldRow != newRow)
                      grid.onCurrentRowChanged(grid, oldRow, newRow);
            });
            
            
            gridView.on('check', function(ev) {
                var rowKey = ev.rowKey;
                var grid = ev.instance;
                var row = grid.getRow(rowKey);
                row._attributes.className = {row : ['gridViewChecked']};
                grid.setRow(rowKey,row);  
            });
            
            gridView.on('checkAll', function(ev) {
                var rowKey = ev.rowKey;
                var grid = ev.instance;
                  var list = grid.getData();
                  for(var i=0;list && i<list.length;i++){
                      var row = list[i];
                      var rowKey = row.rowKey;
                      row._attributes.className = {row : ['gridViewChecked']};
                      grid.setRow(rowKey,row);  
                  }
            });
            
            gridView.on('uncheck', function(ev) {
                var rowKey = ev.rowKey;
                var grid = ev.instance;
                var row = grid.getRow(rowKey);
                row._attributes.className = null;
                grid.setRow(rowKey,row);  
            });
            
            gridView.on('uncheckAll', function(ev) {
                var rowKey = ev.rowKey;
                var grid = ev.instance;
                  var list = grid.getData();
                  for(var i=0;list && i<list.length;i++){
                      var row = list[i];
                      var rowKey = row.rowKey;
                      row._attributes.className = null;
                      grid.setRow(rowKey,row);  
                  }   
            });
            
            

            gridView.onCurrentRowChanged = function(gridView, oldRow, newRow) {
                var gridId = gridView.gridId;
                gridView.gridWrapper.currentRow = newRow;
                var rowData = gridView.getJsonRow(newRow);

                if (window[gridId + "_rowChanged"]
                        && typeof (window[gridId + "_rowChanged"]) == "function") {
                    window[gridId + "_rowChanged"](gridView, oldRow, newRow,
                            rowData);
                } else if (window["onGridRowChanged"]
                        && typeof (window["onGridRowChanged"]) == "function") {
                    window["onGridRowChanged"](gridView, oldRow, newRow,
                            rowData);
                }

            }
            gridView.onGridButtonClick = function(gridView, data) {
                var col = data.fieldName;
                var row = data.row;
                var gridId = gridView.gridId;
                var cFunction = gridId + "_" + col + "_buttonClicked";
                
                

                var json = this.getJsonRow(this.getIndexOfRow(row));
                if (window[cFunction]
                        && typeof (window[cFunction]) == "function") {
                    window[cFunction](gridView, row, col, json);
                } else if (window[gridId + "_buttonClicked"]
                        && typeof (window[gridId + "_buttonClicked"]) == "function") {
                    window[gridId + "_buttonClicked"](gridView, row, col,
                            json);
                } else if (window["onGridButtonClicked"]
                        && typeof (window["onGridButtonClicked"]) == "function") {
                    window["onGridButtonClicked"](gridView, row, col,
                            json);
                }

            };
            
            
            
            gridView.onEditCommit = function(gridView, columnInfo, oldValue,newValue) {
                if (window[gridId + "_editCommit"]
                        && typeof (window[gridId + "_editCommit"]) == "function") {
                    return window[gridId + "_editCommit"](gridView, columnInfo.dataRow,columnInfo.column,
                            oldValue, newValue);
                } else if (window["onGridEditCommit"]
                        && typeof (window["onGridEditCommit"]) == "function") {
                    return window["onGridEditCommit"](gridView, columnInfo.dataRow,columnInfo.column,
                            oldValue, newValue);
                } else {

                    return true;
                }
            };
            
            
            gridView.onCellClicked = function(gridView, clickedData) {
                var gridId = gridView.gridId;
                if (clickedData.cellType == "header") {
                    if (window[gridId + "_columnClick"]
                            && typeof (window[gridId + "_columnClick"]) == "function") {
                        window[gridId + "_columnClick"](gridView,
                                clickedData.colName || clickedData.subType);
                    } else if (window["onGridColumnClick"]
                            && typeof (window["onGridColumnClick"]) == "function") {
                        window["onGridColumnClick"](gridView,
                                clickedData.colName || clickedData.subType);
                    }

                } else {
                    if (window[gridId + "_cellClick"]
                            && typeof (window[gridId + "_cellClick"]) == "function") {
                        window[gridId + "_cellClick"](gridView,
                                clickedData.itemIndex, clickedData.column,
                                clickedData.index, clickedData.field);
                    } else if (window["onGridCellClick"]
                            && typeof (window["onGridCellClick"]) == "function") {
                        window["onGridCellClick"](gridView,
                                clickedData.itemIndex, clickedData.column,
                                clickedData.index, clickedData.field);
                    }

                }

            };
            gridView.onCellDblClicked = function(gridView, clickedData) {
                var gridId = gridView.gridId;
                if (clickedData.cellType == "header") {

                    if (window[gridId + "_columnDblClick"]
                            && typeof (window[gridId + "_columnDblClick"]) == "function") {
                        window[gridId + "_columnDblClick"](gridView,
                                clickedData.colName || clickedData.subType);
                    } else if (window["onGridColumnDblClick"]
                            && typeof (window["onGridColumnDblClick"]) == "function") {
                        window["onGridColumnDblClick"](gridView,
                                clickedData.colName || clickedData.subType);
                    }
                } else {
                    var json = this.getJsonRow(clickedData.itemIndex);
                    if (window[gridId + "_cellDblClick"]
                            && typeof (window[gridId + "_cellDblClick"]) == "function") {
                        window[gridId + "_cellDblClick"](gridView,
                                clickedData.itemIndex, clickedData.column,
                                json, json[clickedData.column]);
                    } else if (window["onGridCellDblClick"]
                            && typeof (window["onGridCellDblClick"]) == "function") {
                        window["onGridCellDblClick"](gridView,
                                clickedData.itemIndex, clickedData.column,
                                json, json[clickedData.column]);
                    }

                }

            };
            
            
            
            gridView.exportExcel = function(excelName,hideHidden,excelUrl,excelParam) {
                // 클라이언트 데이터로 엑셀 만들기
                if(this.pagination){
                    var searchUrl = this.searchUrl;
                    var searchParam = this.searchParam; 
                    if(!searchParam && excelParam != undefined ){
                        searchParam = {
                                url : excelUrl || this.getUrl,
                                param : excelParam
                        };
                    }
                    if(!searchParam)
                    {
                        return this.getUploadExcel(excelName,hideHidden);
                    }
                    var url = excelUrl || searchParam.url || this.getUrl;
                    var param = excelParam || searchParam.param;
                    if(!url)
                    {
                        return this.getUploadExcel(excelName,hideHidden);
                    }

                    var grid = this;
                    if(!param)
                        param = {};
                    param.page = 1;
                    param.perPage = 100000000;
                    Utilities.blockUI();
                    Utilities.getAjax(url,param,!!this.isPost,function(data,jqXHR){
                        if(Utilities.processResult(data,jqXHR,"데이터 불러오기에 실패했습니다."))
                        {
                            grid.toExcel(excelName,data,hideHidden);
                            try{addExcelLog(excelName)}catch(e){}
                        }
                        Utilities.unblockUI();
                    });
                    return true;
                }
                else{
                    Utilities.blockUI();
                    var ret =  this.getGridExcel(excelName,hideHidden);
                    try{addExcelLog(excelName)}catch(e){}
                    Utilities.unblockUI();
                    return ret;
                }

            };
            gridView.getGridExcel=function(excelName,hideHidden){
                return this.toExcel(excelName, this.getJsonRows(),hideHidden);
            };
            gridView.getUploadExcel = function(excelName,hideHidden){
                var cols = this.gridWrapper.columns;

                var columns = [];
                for(var i=0;i<cols.length;i++){
                        var col = cols[i];
                        if(!col.field)
                            continue;
                        if(hideHidden&&col.hidden=="Y")
                            continue;
                        columns.push({
                            header : col.name || col.field,
                            key : col.field
                        });
                }
                const workbook = new ExcelJS.Workbook();
                sheet = workbook.addWorksheet('sheet1');
                sheet.columns = columns;
                var row = sheet.getRow(1);
                row.eachCell({ includeEmpty: true },function(cell, colNumber){
                    cell.border = {
                               top: {style:'thin'},
                               left: {style:'thin'},
                               bottom: {style:'thin'},
                               right: {style:'thin'}
                    }; 
                    cell.fill = {
                           type: 'pattern',
                           pattern:'solid',
                           fgColor:{argb:'EEEEEE'}
                    };
                     cell.alignment = {
                             vertical: 'middle'
                           };
                });
                workbook.xlsx.writeBuffer().then(function (buffer){
                    Utilities.downloadFile(buffer,excelName);
                });
            };
            gridView.getGroupCols = function(list,groups,group){
                for(var i=0;i<groups.length;i++){
                    if(groups[i].tagName == "group"){
                        this.getGroupCols (list,groups[i].items);
                    }
                    else{
                    	groups[i].groupName = group.name;
                    	groups[i].groupTitle = group.title;
                        list.push(groups[i]);
                    }
                }
            };
            gridView.getFullcols = function(){
                var cols = this.gridWrapper.columns;
                var list = [];
                for(var i=0;i<cols.length;i++){
                    if(cols[i].tagName == "group"){
                        this.getGroupCols (list,cols[i].items,cols[i]);
                    }
                    else{
                        list.push(cols[i]);
                    }
                }
                return list;
            };
            gridView.createGroupHeader = function(sheet,groupList,columns){
            	var aCode = "A".charCodeAt(0);
            	var row1 = sheet.getRow(1);
            	var row2 = sheet.getRow(2);
            	for(var i=0;i<columns.length;i++){
            		row1.getCell(i+1);
            		row2.getCell(i+1).value=columns[i].header;
            	}
            	var curIndex = 0;
            	for(var i=0;i<groupList.length;i++){
            		var group = groupList[i];
//            		var alpha = curIndex + i;
                    var preA  = parseInt( (curIndex+1) /26 );
                    var prefixA= "";
                    if(preA > 0)
                        prefixA = String.fromCharCode(aCode +preA - 1 )
                        
                    var preB = parseInt((curIndex + group.colSpan)/26);
                    var prefixB = "";
                    if(preB> 0)
                        prefixB = String.fromCharCode(aCode +preB - 1 )
                    
                         
            		var fromName =prefixA + String.fromCharCode(aCode + (curIndex %26)) + "1" ;
            		var toName = group.rowSpan > 1 ? prefixA + String.fromCharCode(aCode +curIndex ) + "2" :  prefixB + String.fromCharCode(aCode +( (curIndex+group.colSpan ) %26) -1 ) + "1";
            		sheet.mergeCells(fromName,toName);
            		row1.getCell(curIndex+1).value = group.title;
            		curIndex += group.colSpan;
            		
            	}
            };
            gridView.toExcel = function(excelName,data,hideHidden){
                var list = null;
                if (Array.isArray(data)) {
                    list = data;
                } else {
                    data = data.data;
                    list = data.contents;
                }
                if(!list)
                    return false;
                var cols = this.getFullcols();

                var columns = [];
                var addCodeNameList = [];
                var groupRows = [];
                var groupRowsMap = {};
                var hasGroup = this.gridWrapper.gridConfig.headerRows==2;
                
                //3지원안함 나중에
                var dateMap = {};
                var dateTimeMap = {};
                for(var i=0;i<cols.length;i++){
                        var col = cols[i];
                        if(!col.field)
                            continue;
                        if(col.type == "button")
                            continue;
                            if(col.type == "file")
                            continue;
                        if(hideHidden&&col.hidden=="Y")
                            continue;
                        if(col.type=="date" ){
                            dateMap[col.field] = col;
                        }
                        if(col.type=="datetime" ){
                            dateTimeMap[col.field] = col;
                        }
                        if(col.type=="combo"){
//                          columns.push({
//                          header :( col.name|| col.field) + "코드" ,
//                          key : col.field
//                          });
                            
                            columns.push({
                            header :( col.name|| col.field) + "" ,
                            key : col.field + "Name"
                            });
                            
                            addCodeNameList.push({
                                codeMap : col.codeMap,
                                field : col.field,
                                fieldName : col.field+"Name"
                            });
                        }
                        else {
                            
                        	 columns.push({
                                 header : col.name || col.field,
                                 key : col.field
                             });
                        }
                        if(col.groupName){
                        	if(!groupRowsMap[col.groupName]){
                        		groupRowsMap[col.groupName] = {
                        			name : col.groupName,
                        			title : col.groupTitle,
                        			type : 'group',
                        			rowSpan : 1,
                        			colSpan : 0,
                        			columns : []
                        		};
                        		groupRows.push(groupRowsMap[col.groupName]);
                        	}
                        	var group = groupRowsMap[col.groupName];
                        	group.columns.push(col);
                        	group.colSpan++;
                        }
                        else {
                        	groupRows.push({
                        		name : col.field,
                    			title : col.name,
                    			colSpan : 1,
                    			rowSpan : 2,
                    			type : 'column'
                        	});
                        }
                       
                }
                
                const workbook = new ExcelJS.Workbook();
                var maxRows = 65000;
                var sheet = null;
                for(var i=0;i<list.length;i++){
                    var rowData = list[i];
                    for(var key in dateMap){
                        var val = rowData[key];
                        if(val)
                        {
                            val = moment(rowData[key]).format("YYYY-MM-DD");
                            if(val.indexOf("In")>-1)
                                val = moment(rowData[key],'YYYYMMDD').format("YYYY-MM-DD");
                            rowData[key]= val;    
                        }
                    }
                   for(var key in dateTimeMap){
                        var val = rowData[key];
                        if(val)
                        {
                            val = moment(rowData[key]).format("YYYY-MM-DD HH:mm:ss");
                            if(val.indexOf("In")>-1)
                                val = moment(rowData[key],'YYYYMMDDHHmmss').format("YYYY-MM-DD HH:mm:ss");
                            rowData[key]= val;    
                        }
                    }
                    if(addCodeNameList.length){
                        var eData = list[i];
                        for(var j=0;j<addCodeNameList.length;j++){
                            var colInfo = addCodeNameList[j];
                            var code = eData[colInfo.field];
                            var codeName = colInfo.codeMap[code];
                            eData[colInfo.fieldName] = codeName;
                        }
                    }
                    if(i%maxRows == 0){
                        if(sheet){
                            var row = sheet.getRow(1);
                            row.eachCell({ includeEmpty: true },function(cell, colNumber){
                                cell.border = {
                                           top: {style:'thin'},
                                           left: {style:'thin'},
                                           bottom: {style:'thin'},
                                           right: {style:'thin'}
                                }; 
                                cell.fill = {
                                       type: 'pattern',
                                       pattern:'solid',
                                       fgColor:{argb:'EEEEEE'}
                                };
                                 cell.alignment = {
                                         vertical: 'middle'
                                       };
                            });
                            
                            if(hasGroup){
                                var row2= sheet.getRow(2);

                            	 row2.eachCell({ includeEmpty: true },function(cell, colNumber){
                                     cell.border = {
                                                top: {style:'thin'},
                                                left: {style:'thin'},
                                                bottom: {style:'thin'},
                                                right: {style:'thin'}
                                     }; 
                                     cell.fill = {
                                            type: 'pattern',
                                            pattern:'solid',
                                            fgColor:{argb:'EEEEEE'}
                                     };
                                      cell.alignment = {
                                              vertical: 'middle'
                                            };
                                 });
                            	
                            	
                            }
                        }
                        sheet = workbook.addWorksheet('sheet' + (parseInt(i/maxRows,10)+1) );
                        sheet.columns = columns;
                        if(hasGroup)
                        	this.createGroupHeader(sheet,groupRows,columns);
//                        else
                        	
                    }
                    var row = sheet.addRow(list[i]);
                    row.eachCell({ includeEmpty: true },function(cell, colNumber){
                        cell.border = {
                                   top: {style:'thin'},
                                   left: {style:'thin'},
                                   bottom: {style:'thin'},
                                   right: {style:'thin'}
                        }; 
                    });
                }
                if(!sheet && list.length==0){
                    sheet = workbook.addWorksheet('sheet1' );
                    sheet.columns = columns;
                    if(hasGroup)
                    	this.createGroupHeader(sheet,groupRows,columns);
//                    else
                    	
                }
                if(sheet){
                    var row = sheet.getRow(1);
                    row.eachCell({ includeEmpty: true },function(cell, colNumber){
                        cell.border = {
                                   top: {style:'thin'},
                                   left: {style:'thin'},
                                   bottom: {style:'thin'},
                                   right: {style:'thin'}
                        }; 
                        cell.fill = {
                               type: 'pattern',
                               pattern:'solid',
                               fgColor:{argb:'EEEEEE'}
                        };
                         cell.alignment = {
                                 vertical: 'middle', horizontal: 'center'
                               };
                    });
                    
                    if(hasGroup){
                        var row2= sheet.getRow(2);

                    	 row2.eachCell({ includeEmpty: true },function(cell, colNumber){
                             cell.border = {
                                        top: {style:'thin'},
                                        left: {style:'thin'},
                                        bottom: {style:'thin'},
                                        right: {style:'thin'}
                             }; 
                             cell.fill = {
                                    type: 'pattern',
                                    pattern:'solid',
                                    fgColor:{argb:'EEEEEE'}
                             };
                              cell.alignment = {
                                      vertical: 'middle'
                                    };
                         });
                    	
                    	
                    }
                }
                
                workbook.xlsx.writeBuffer().then(function (buffer){
                    Utilities.downloadFile(buffer,excelName);
                });

                
            };
            // 아직안함

            gridView.importExcel = function() {
                var that = this;
                var inpExcelImport = $("#inpExcelImport");
                if (inpExcelImport.length == 0) {
                    inpExcelImport = $('<input type="file" style="display:none" id="inpExcelImport"/>');
                    inpExcelImport .change(function(e) {
                        var files = e.target.files;
                        var f = files[0];
                        var reader = new FileReader();
                        reader.readAsArrayBuffer(f);
                        $(reader).on("loadend",function(e){
                            var buffer = this.result;
                            that.fromExcel(buffer);
                        });
                    });
                }

                inpExcelImport.click();
            };
            
            gridView.fromExcel=function(buffer){
                var cols = this.gridWrapper.columns;
                const workbook = new ExcelJS.Workbook();
                var grid = this;
                workbook.xlsx.load(buffer).then(function(wb){
                    var dataList = [];
                    for(var i=0;i<wb.worksheets.length;i++){
                        var sheet = wb.worksheets[i];
                        var rowCount = sheet.rowCount;
                        
                        for(var j=2;j<=rowCount;j++){
                            var row = sheet.getRow(j);
                            var json = {stat:"c"};
                            for(var k=1;k<=row.cellCount ;k++){
                                if(cols.length >= k)
                                    json[cols[k-1].field] = row.getCell(k).text;
                            }
                            dataList.push(json);
                        }
                    }
                    grid.addRows(dataList);
                });
            }
            
            return gridView;

            
        }
    };
}
function createGrid(grd) {
    var grid = createGridObject(grd);
    tui.Grid.setLanguage('ko', { // set new language
     display: {
         noData: '내역이 없습니다.',
         loadingData: 'Loading data.',
         resizeHandleGuide: 'You can change the width of the column by mouse drag, ' +
                             'and initialize the width by double-clicking.'
     },
     net: {
         confirmCreate: 'Are you sure you want to create {{count}} data?',
         confirmUpdate: 'Are you sure you want to update {{count}} data?',
         confirmDelete: 'Are you sure you want to delete {{count}} data?',
         confirmModify: 'Are you sure you want to modify {{count}} data?',
         noDataToCreate: 'No data to create.',
         noDataToUpdate: 'No data to update.',
         noDataToDelete: 'No data to delete.',
         noDataToModify: 'No data to modify.',
         failResponse: 'An error occurred while requesting data.\nPlease try again.'
     }
    });
    grid.loadXml();
}
function _onGridButtonClick(elem) {
    var data = $(elem).data();
    var gridView = window[data.gridId];
    if (gridView)
        gridView.onGridButtonClick(gridView, data);
}