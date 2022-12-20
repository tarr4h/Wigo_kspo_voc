

function RowNumberRenderer(props) {
      this.constructor = function(props) {
          const el = document.createElement('span');
//          $(el).css("margin-right", "3px");
//          $(el).css("margin-left", "3px");
          this.el = el;
          this.render(props);
      }

      this.getElement = function() {
        return this.el;
      }

      this.render=function(props) {
        if(!props.grid.canRender(props)){
              return false;
        }
        var grid = props.grid;
        var rowData = grid.getRow(props.rowKey);
        if(rowData == null)
            return false;
        if(rowData.stat == "c"){
        	this.el.innerHTML = Number(rowData._attributes.rowNum);
            return; 
        }
//          if(grid.pagination&&grid.getPagination()&&grid.getPagination()._currentPage)
        if(grid.pagination&&grid.searchParam&&grid.searchParam.pagination)
        {
            var page = grid.searchParam.pagination.page;
            var perPage = grid.searchParam.pagination.perPage;
            var rowIndex = (page-1) * perPage + Number(props.formattedValue) ;
            this.el.innerHTML = "" + rowIndex;
            return;
        }
        
        this.el.innerHTML = props.formattedValue;
      }
      this.constructor(props);
    }




function RowIndexRenderer(props) {
      this.constructor=function(props) {
          const el = document.createElement('span');
          this.el = el;
          this.render(props);
      }

      this.getElement = function()  {
        return this.el;
      }

      this.render = function (props) {
        if(!props.grid.canRender(props)){
              return false;
        }
        var grid = props.grid;
        var rowData = grid.getRow(props.rowKey);
        if(rowData == null)
            return false;
        if(rowData.stat == "c"){
            this.el.innerHTML = "" ;
            return; 
        }
//          if(grid.pagination&&grid.getPagination()&&grid.getPagination()._currentPage)
        if(grid.pagination&&grid.searchParam&&grid.searchParam.pagination)
        {
            var page = grid.searchParam.pagination.page;
            var perPage = grid.searchParam.pagination.perPage;
            var rowIndex = (page-1) * perPage + Number(rowData._attributes.rowNum) ;
            this.el.innerHTML = "" + rowIndex;
            return;
        }
        
        this.el.innerHTML = Number(rowData._attributes.rowNum);
      }
      this.constructor(props);
    }


function FileRenderer(prop) {
    this.constructor=function(props) {
        
        const el = document.createElement('span');
//        $(el).css("width","100%");
//        $(el).css("height","100%");
        $(el).css("text-align","center");
        

        const col = props.columnInfo.renderer.colInfo;
        this.el = el;
        
        el.colInfo = col;

        var fileCd =  props.value;
        var fileSeq = col.fileSeq;
        
        if(col.fileCdField)
        {
            fileCd = props.grid.getRow(props.rowKey)[col.fileCdField];
        }
        if(col.fileSeqField)
        {
            fileSeq = props.grid.getRow(props.rowKey)[col.fileSeqField];
        }
        
        
        
        
        var fileCategory = col.fileCategory;
        if(!col.fileDataMap){
            col.fileDataMap = {
                
            };
        }
        if(fileSeq){
            var param ={
            fileCd: fileCd,
            fileOdrg:fileSeq,
            previewUrl : "Y"
            };
            var key = fileCd+"_" + fileSeq;
            var fileData = col.fileDataMap[key];
            if(fileData){
                return initFileCol(col,el,fileData.data,fileCd,fileSeq,fileCategory);
            }
            var url=_fileInfo_url;
           
            Utilities.getAjax(url,param,true,function(data,jqXHR){
                if(Utilities.processResult(data,jqXHR,""))
                {
                    col.fileDataMap[key] = {data:data};
                    initFileCol(col,el,data,fileCd,fileSeq,fileCategory);
                }
            }); 
        }
        
        
        
        this.render(props);
      }
    this.getElement = function() {
        return this.el;
      }

    this.render = function(props) {
//        if(!props.grid.canRender(props)){
//            return false;
//        }
//        var el =this.el;
//        var col = this.el.colInfo;
//        var value = props.value;
//        try{
//            value = props.grid.htmlRenderCallback(props.rowKey,col,value)
//
//        }
//        catch(e){
//            value = props.value;
//        }
//        $(el).html(value);
      }
      this.constructor(props);
}
function HtmlRenderer (props){
    this.constructor=function(props) {
        const el = document.createElement('span');
        $(el).css("width","100%");
        $(el).css("height","100%");
        $(el).css("text-align","center");
        
        const col = props.columnInfo.renderer.colInfo;
        this.el = el;
        el.colInfo = col;
        el.htmlCallback = props.columnInfo.renderer.htmlCallback;
        this.render(props);
      }

      this.getElement = function() {
        return this.el;
      }

      this.render = function(props) {
          if(!props.grid.canRender(props)){
              return false;
          }
          var el =this.el;
          var col = this.el.colInfo;
          var value = props.value;
          try{
              value = props.grid.htmlRenderCallback(props.rowKey,col,value)

          }
          catch(e){
              value = props.value;
          }
          $(el).html(value);
      }
      this.constructor(props);
}   

function StatusRenderer (props){
          this.constructor=function(props) {
            const el = document.createElement('span');
            this.el = el;
            el.setAttribute("rowKey",props.rowKey);
            $(el).css("text-align","center");
            
            this.render(props);
          }

          this.getElement = function() {
            return this.el;
          }

          this.render=function(props) {
              if(!props.grid.canRender(props)){
                  return false;
              }
              // <i class="far fa-sticky-note"></i>
            var icon = '';
            if (props.value == "u")
                icon = '<i class="fas fa-pencil-alt"></i>';
            else if (props.value == "c")
                icon = '<i class="far fa-file-alt"></i>';
            else if (props.value == "d")
                icon = '<i class="fas fa-trash-alt"></i>';
            $(this.el).html(icon);
          }
          this.constructor(props);
        }
    function RadioRenderer(props) {
          this.constructor=function(props) {
            const el = document.createElement('input');
            const gridId = props.columnInfo.renderer.gridId;
            this.el = el;
            el.gridId = gridId;
            el.type = "radio";
            el.name = "rdo_"+gridId;
            el.setAttribute("rowKey",props.rowKey);
            $(el).css("text-align","center");
            $(el).click(function(){
            	props.grid.uncheckAll();
            	props.grid.check(props.rowKey);
            });
            this.render(props);
          }

          this.getElement=function() {
            return this.el;
          }

          this.render=function(props) {
              if(!props.grid.canRender(props)){
                  return false;
              }
              var json = props.grid.getRow(props.rowKey);
              this.el.checked = json._attributes.checked;
          }
          this.constructor(props);
        }
    
    function CheckboxRenderer(props) {
        this.constructor=function(props) {
            const elem = $('<img src="/static/voc/images/ico_checkbox1.png" style="cursor:pointer" />');
            const el = elem[0];
            this.el = el;
            const gridId = props.columnInfo.renderer.gridId;
            el.gridId = gridId;
//            el.type = "checkbox";
//            el.name = "checkbox_"+gridId;
            el.rowKey = props.rowKey;
            el.setAttribute("rowKey",props.rowKey);
            el.setAttribute("data-row-key",props.rowKey);
            elem.click(function(e){
                            e.stopPropagation();
                var checked = $(this).attr("src").indexOf("ico_checkbox1.png") > -1 ;
                if(checked)
                    props.grid.check(this.rowKey);
                else
                    props.grid.uncheck(this.rowKey);
                
    
                var chkAll = props.grid.gridWrapper.gridWrap.find("#chkgrd_"+gridId);
                
                if(!checked)
                {
                    chkAll.prop("checked",false);
                    return;
                }
            });
	/**
	<label class="mCheckbox1"><input type="checkbox" title="건강" checked="checked"><span class="label">&nbsp;</span></label>
	 */
//		 const lbl = $('<label class="mCheckbox1"><input type="checkbox"><span class="label">&nbsp;</span></label>');
//	 	  const el = lbl.find('input')[0];
//	 	  var grid = props.grid;
//	 	  var disableCol = grid.gridWrapper.gridConfig.disableCol;
//	 	  if(disableCol){
//				var rowData = grid.getRow(props.rowKey);
//				if(rowData[disableCol] == "Y")
//				{
//					el.disabled = true;
//					grid.disableRow(props.rowKey,true);
//				}	
//		   }
//
//	 	   
//	 	   
//          const gridId = props.columnInfo.renderer.gridId;
//          this.el = el;
//          this.lbl = lbl[0];
//          el.gridId = gridId;
//          el.type = "checkbox";
//          el.name = "checkbox_"+gridId;
//          el.rowKey = props.rowKey;
//          el.setAttribute("rowKey",props.rowKey);
//          el.setAttribute("data-row-key",props.rowKey);
//          $(el).css("text-align","center");
//          $(el).css("background-color","#d00e2d");
//          $(el).click(function(e){
//	e.preventDefault();
//        	  e.stopPropagation();
//        	  var checked = this.checked;
//        	  if(checked)
//        		  props.grid.check(this.rowKey);
//        	  else
//        		  props.grid.uncheck(this.rowKey);
//        	  
//
//        	  var chkAll = props.grid.gridWrapper.gridWrap.find("#chkgrd_"+gridId);
//        	  
//        	  if(!checked)
//        	  {
//        		  chkAll.prop("checked",false);
//        		  return;
//        	  }
//
//          });
          this.render(props);
        }

        this.getElement=function() {
          return this.el;
        }

        this.render=function(props) {
            const imgPath = '/static/voc/images/';

            var json = props.grid.getRow(props.rowKey);
            if(json)
            {
//                this.el.checked = json._attributes.checked;
                if(json._attributes.checked){
                    this.el.setAttribute("src",imgPath + "ico_checkbox1_a.png");
                } else {
                    this.el.setAttribute("src",imgPath + "ico_checkbox1.png");
                }
            }	
        }
        this.constructor(props);
      }
    
    
    function NumberRenderer(props) {
          this.constructor=function(props) {
            const el = document.createElement('div');
            const col = props.columnInfo.renderer.colInfo;
            this.el = el;
            el.col = col;
            
            var align = col.align;
            if(!align)
                align = "right";
            
            $(el).css("text-align",align);
            $(el).css("margin-right", "5px");
            $(el).css("margin-left", "5px");
            var maxLength = null;
            
            this.render(props);
          }

          this.getElement=function() {
            return this.el;
          }

          this.render=function(props) {
              if(!props.grid.canRender(props)){
                  return false;
              }
              var el =this.el;
              var col = this.el.col
              let val = props.value;
              val = Number(val);
              if(col.time)
              {
                if(!val){
                    $(el).html("00:00:00.000");
                    return ;
                }
                let ms = "000";
                if(col.time == "ms")
                {
                    ms = val % 1000;
                    ms = ("000"+ ms).substr(-3);
                    
                    val = parseInt(val/1000);
                }
                const hour = parseInt(val/3600) ;
                const min = parseInt(val%3600 /60) ;
                const sec = parseInt(val % 60);
                let timeStr = "";
                if(hour < 10)
                    timeStr += "0";
                timeStr += hour + ":";
                
                if(min < 10)
                    timeStr += "0";
                timeStr += min + ":";  
                
                if(sec < 10)
                    timeStr += "0";
                timeStr += sec + "." + ms;  
                
                
                $(el).html(timeStr);
                
                
                if(col.textUnderline == "Y"){
                $(el).css("text-decoration","underline");
                $(el).css("cursor","pointer");
                 }             
              
                return; 
              }
              if(!val){
                  $(el).html("0");
                  return;
              }
              var nums = (val+"").split(".");
              var value = "0";
             
              if(nums.length>0)
                  value= Utilities.numberWithCommas(nums[0]);
               
              if(nums.length>1 ){
                  
                  var place = col.place;
                  if( place || (place != null && place.toString()=="0"))
                  {
                      place = Utilities.parseInt(place);
                      if(place > 0 ){
                          if(place > nums[1].length)
                              value += "." + nums[1];
                          else
                              value += "." + nums[1].substring(0,2);
                      }
                  }
                  else
                  {
                      value += "." + nums[1];
                  }
                  
              }
                  
              $(el).html(value);
              
              if(val<0)
            	  $(el).css("color","#ff0000");
              if(col.textUnderline == "Y"){
                $(el).css("text-decoration","underline");
                $(el).css("cursor","pointer");
              }            	  
          }
          this.constructor(props);
        }
        
  
        
    function NumberEditor(props) {
          this.constructor = function(props) {
            const el = document.createElement('input');
            this.el =el;
            var col  = props.columnInfo.editor.colInfo;

            el.type = 'text';
            el.colInfo = col;
            var digit = col.digit;
            var place = col.place;
            var maxLength = 0;
            if(col.type=="int")
                place = 0;
            
            var opt = {
                    autoGroup: true, // default: false, 정수 부분 그룹화 사용 여부
                    groupSeparator: ",", // default: "", 그룹 구분자 정의
// digits: place, // default: "*", 소수 크기 정의
                    allowMinus: true, // default: true, 음수 사용 여부
// repeat: digit // 반복 기능? 나한테는 그냥 자리수..
                };
            var max = 0;
            
            if(digit)
            {
                opt.repeat = Number(digit);
                var str = Utilities.paddingZero("",Number(digit));
                max = str.replace(/0/g,'9');
            }
            if(place>0)
            {
                opt.digits = place;
            }
            if(place == 0)
                opt.digits = 0;
            
            if(place){
                var str = Utilities.paddingZero("",Number(place));
                max+="."+str.replace(/0/g,'9');
            }
            if(col.max)
                max = col.max;
            max = Number(max);
            var min = max * -1;
            if(col.min)
                min = col.min;
            min = Number(min);
            if(max){
                opt.max = max;
                opt.min = min;
            }
                
            $(this.el).inputmask("numeric", opt);
            var align = col.align;
            if(!align)
                align = "right";
            
            $(el).css("text-align",align);
            el.value = String(props.value).replace(/,/g, '');

          }

          this.getElement=function() {
            return this.el;
          }

          this.getValue=function() {
              var val =$(this.el).inputmask('unmaskedvalue');
            return val;
          }

          this.mounted=function() {
            var val = $(this.el).val();
            this.el.value = val;
            this.el.select();
          }
          
          this.constructor(props);
          
        }
        
        
        
        function PhoneNumberRenderer(props) {
          this.constructor = function(props) {
            const el = document.createElement('div');
            const col = props.columnInfo.renderer.colInfo;
            this.el = el;
            el.col = col;
            
            var align = col.align;
            if(!align)
                align = "center";
            
            $(el).css("text-align",align);
            
            var maxLength = null;
            
            this.render(props);
          }

          this.getElement= function() {
            return this.el;
          }

          this.render= function(props) {
              if(!props.grid.canRender(props)){
                  return false;
              }
              var el =this.el;
              var col = this.el.col
              var val = props.value;
              if(!val)
                    val = "";
              val = val.replace(/[^0-9]/g, "");
              if(val.length == 12){
                val = val.substring(0,4) +val.substring(3,8)+val.substring(7,12) ; 
              }
              else if(val.length == 11){
                if(val.substring(0,2) == "02")
                    val = val.substring(0,2) + "-" + val.substring(2,7)+"-" +val.substring(7,11);
                else
                    val = val.substring(0,3) +"-" +val.substring(3,7)+"-" +val.substring(7,11);  
              }
             else if(val.length == 10){
                if(val.substring(0,2) == "02")
                    val = val.substring(0,2) +"-" +val.substring(2,6)+"-" +val.substring(6,10);
                else
                    val = val.substring(0,3) +"-" +val.substring(3,6)+"-" +val.substring(7,10);
              }
             else if(val.length == 9){
                if(val.substring(0,2) == "02")
                    val = val.substring(0,2) +"-" +val.substring(2,5)+"-" +val.substring(5,9);
                else
                    val = val.substring(0,3) +"-" +val.substring(3,6)+"-" +val.substring(6,9);
              }
            else if(val.length > 4 && val.length < 9){
                val = val.substring(0,val.length-4) + val.substring(val.length-4) 
            }
              //var value = val.replace(/[^0-9]/g, "").replace(/(^02|^0505|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})$/,"$1-$2-$3").replace("--", "-"); 

            


                  
              $(el).html(val);
              
          }
          this.constructor(props);
        }

function PhoneNumberEditor(props) {
          this.constructor=function(props) {
            const el = document.createElement('input');
            this.el =el;
            var col  = props.columnInfo.editor.colInfo;

            el.type = 'text';
            el.colInfo = col;


//          var moptions = {
//              placeholder: "(__) ____-____",
//              onKeyPress: function(cep, e, field, options) {
//                var masks = ["(00) 0000-0000", "(00) 0 0000-0000"];
//                var mask = cep.length > 14 ? masks[1] : masks[0];
//                $(".phone").mask(mask, options);
//              }
//            };
//            $(".phone").mask("(00) 0000-0000", moptions);
//                      
            $(this.el).inputmask({"mask": "99[9][9]9999999"}); 
            var align = col.align;
            if(!align)
                align = "center";
            
            $(el).css("text-align",align);
            el.value = String(props.value);

          }

          this.getElement=function() {
            return this.el;
          }

          this.getValue=function() {
              var val =this.el.value.replace(/[^0-9]/g, "")//Utilities.getNumberOnly(this.el.value);
            return val;
          }

          this.mounted=function() {
              // var val = this.el.value;
            var val = $(this.el).val();
            this.el.value = val;
            this.el.select();
          }
          this.constructor(props);
        }


    function ComboRenderer(props) {
         this.constructor=function(props) {
                const el = document.createElement('div');
                const col = props.columnInfo.renderer.colInfo;
                this.el = el;
                el.col = col;
                
                var align = col.align;
                if(!align)
                    align = "center";
                
                $(el).css("text-align",align);
                this.render(props);
              }
              this.getElement=function() {
                return this.el;
              }
              this.render=function(props) {
                  if(!props.grid.canRender(props)){
                      return false;
                  }
                  var el =this.el;
                  var col = this.el.col;
                  
                  var dic = col.codeMap;
                    if(!dic)
                     dic = {};
                  var val = props.value;
                  var value = dic[val] || val;
                  if(!value&&col.prefixLabel)
                    value = col.prefixLabel
                  
                  $(el).html(value);
//                  if(col.subjectColor){
//                    var sbjColors = {
//                                "KO" : "#00B050",
//                                "MA" : "#00B0F0",
//                                "TO" : "#ED7D31",
//                                "SO" : "#FF0066",
//                                "SC" : "#7030A0",
//                        };
//                     if(sbjColors[val]){
//                        $(el).css("color",sbjColors[val]);
//                    }
//                 }
                 
                 if(col.textUnderline == "Y"){
                    $(el).css("text-decoration","underline");
                    $(el).css("cursor","pointer");
                }
              }
              this.constructor(props);
    }
    
    
    
    function DateTimeRenderer(props) {
          this.constructor=function(props) {
            const el = document.createElement('div');
            const format = props.columnInfo.renderer.format;
            this.el = el;
            el.colInfo = props.columnInfo.renderer.colInfo;
            el.format = format;
            this.render(props);
          }

          this.getElement=function() {
            return this.el;
          }

          this.render=function(props) {
              if(!props.grid.canRender(props)){
                  return false;
              }
              var dValue = "";
              var col = this.el.colInfo;
              
              if(!props.value){
            	  dValue= "";
              }
              else {
            	  var value = !props.value  ? "" : typeof props.value == "string" ?Utilities.parseDate(props.value) : moment(props.value).toDate();
                  
            	  if(!value){
                	  dValue= "";
                  }
                  else {
    		                var year = value.getFullYear() + "";
    		                var month = Utilities.paddingZero(value.getMonth() + 1, 2);
    		                var day = Utilities.paddingZero(value.getDate(), 2);
    		                var hour = Utilities.paddingZero(value.getHours(),2);
    		                var min = Utilities.paddingZero(value.getMinutes(),2);
    		                var sec = Utilities.paddingZero(value.getSeconds(),2);
    		                var ms = value.getMilliseconds();
    		                
    		                var format = "yyyy-MM-dd";
    		                
    		                if(col.type == "date")
    		                {
    		                    format = "yyyy-MM-dd";
    		                }
    		                else if(col.type == "datemonth")
    		                {
    		                    format = "yyyy-MM";
    		                }
    		                else if(col.type == "dateyear")
    		                {
    		                    format = "yyyy";
    		                }
    		                else if(col.type == "datetime")
    		                {
    		                    format = "yyyy-MM-dd HH:mm:ss";
    		                }
    		                
    		                dValue = format.replace('yyyy', year);
    			            dValue = dValue.replace('MM', month);
    			            dValue = dValue.replace('dd', day);
    			            dValue = dValue.replace('HH', hour);
    			            dValue = dValue.replace('mm', min);
    			            dValue = dValue.replace('ss', sec);
    			            dValue = dValue.replace('SSS', ms);
                  }
              }
              
              
            var grid = props.grid;
            var colName = this.el.colInfo.field;
            var editable = this.el.colInfo.displayEditor =="Y" && this.el.colInfo.readOnly !='Y' && (grid.canEdit(colName,props.rowKey,grid.getRow(props.rowKey)));
            if(col.type == "date" && editable ){
//            	this.el.rowKey = props.rowKey;
//                this.el.columnName = props.columnInfo.name;
            	var span = $('<span style="width:90%;vertical-align:middle"/>');
                var btnSpan = '<span style="cursor:pointer" class="tui-ico-date"></span>';
                var div1=   $('<div  style="width:100%;vertical-align:middle;border:0px;height:auto" class="tui-datepicker-input tui-datetime-input tui-has-focus">'+dValue+' '+btnSpan+'</div>');
                var div2 =  $('<div  id="wrapper" style="margin-top: -1px;"></div>');
                
                span.append(div1);
                span.append(div2);
                $(this.el).html("");
                $(this.el).append(span);
//                $(this.el).click(function(){
//                	grid.startEditing(props.rowKey, colName);
//                });
//                
                span.find("span.tui-ico-date").click(function(){
                	grid.startEditing(props.rowKey, colName);
                  });
                
                return;
                
            }
            this.el.innerHTML = dValue;
            if(col.textUnderline == "Y"){
                    $(this.el).css("text-decoration","underline");
                    $(el).css("cursor","pointer");
                }
          }
          this.constructor(props);
        }

    function DateTimeEditor(props) {
          this.constructor =function (props) {
            const el = document.createElement('input');
            this.el =el;
            var col  = props.columnInfo.editor.colInfo;

            el.type = 'text';
            el.colInfo = col;
            
                
            $(this.el).inputmask("datetime", {inputFormat: "yyyy-mm-dd HH:MM:ss",placeholder: '____-__-__ __:__:__'});
            var align = col.align;
            if(!align)
                align = "center";
            
            $(el).css("text-align",align);
// if(maxLength)
// el.maxLength = maxLength;
            el.value = String(props.value);

          }

          this.getElement=function () {
            return this.el;
          }

          this.getValue=function () {
              var val =Utilities.getNumberOnly(this.el.value);
            return val;
          }

          this.mounted=function () {
              // var val = this.el.value;
            var val = $(this.el).val();
            this.el.value = val;
            this.el.select();
          }
          this.constructor(props);
        }
    function ComboEditor(props) {
          this.constructor=function(props) {
            var col  = props.columnInfo.editor.colInfo;
            const el = document.createElement('select');
            $(el).addClass("tui-select-box-input");
            $(el).css('width','100%');
            var align = col.align;
            if(!align)
                align = "center";
            
            $(el).css("text-align",align);
            
            
            this.el =el;
            
            
            var list = col.codeList;
            
//          if(col.uprCodeCol){
//              var uprCol = props.grid.gridWrapper.columnMap[col.uprCodeCol];
//              if(uprCol )
//              {
//                  var parentData = props.grid.getJsonRow(props.grid.getIndexOfRow(props.rowKey));
//                  var parentValue = parentData[col.uprCodeCol];
//                  if(!parentValue){
//                      list = [];
//                  }
//                  else{
//                      if(!col.comCodeData)
//                          col.comCodeData = {};
//                      if(!col.comCodeData[parentValue]){
//                          col.comCodeData[parentValue] = Utilities.getObject(_code_url,{codeCd:parentValue,codeType : uprCol.codeType},true);
//                      }
//                      list = col.comCodeData[parentValue];
//                  }
//              }
//          }

            el.colInfo = col;
            var hasParent = false;
            var parentValue = "";
            var grid = props.grid;
            
            if(col.uprCodeCol){
                
                
                var uprCol = props.grid.gridWrapper.columnMap[col.uprCodeCol];
                if(uprCol){
                    var parentData = props.grid.getJsonRow(props.grid.getIndexOfRow(props.rowKey));
                    parentValue = parentData[col.uprCodeCol];
                    hasParent = true;

                }

            }
            var label = "선택";
            if(col.prefixLabel)
                label = col.prefixLabel;
            $(el).append('<option value="">'+label+'</option>');
            for(var i=0;list&&i<list.length;i++){
                if(hasParent){
                    if(parentValue == list[i].ctgryCd)
                        $(el).append('<option value="'+list[i].comnCd+'">'+list[i].comnCdNm+'</option>');
                }
                else
                    $(el).append('<option value="'+list[i].comnCd+'">'+list[i].comnCdNm+'</option>');
            }
          
            $(el).val(props.value);
            var rowKey = props.rowKey;
            $(el).change(function(){
                var colMap = grid.gridWrapper.columnMap;
                for(var key in colMap)
                {
                    var child = colMap[key];
                    if(child.uprCodeCol && child.uprCodeCol == col.field){
                        grid.setValue(rowKey,child.field,"");
                    }
                }
            });
          }

          this.getElement=function() {
            return this.el;
          }

          this.getValue=function() {
            var val =$(this.el).val()
            return val;
          }

          this.mounted=function() {
            this.el.focus();
            $(this.el).click();
          }
          this.constructor(props);
        }
        
        
    function DateEditor(props) {
        this.constructor=function(props) {
              
         var col  = props.columnInfo.editor.colInfo;
           
         var span = $('<span style="width:100%;vertical-align:middle"/>');
         var btnSpan = '<span style="cursor:pointer" class="tui-ico-date"></span>';
            if(col.type == "datayear")
                btnSpan = "";   
          var div1=   $('<div  style="width:100%;vertical-align:middle" class="tui-datepicker-input tui-datetime-input tui-has-focus"><input style="width:100%" type="text" id="datepicker-input" aria-label="Date-Time">'+btnSpan+'</div>');
          var div2 =  $('<div  id="wrapper" style="margin-top: -1px;"></div>');
          span.append(div1);
          span.append(div2);
            const el =span[0];
            this.el =el;
            el.colInfo = col;   
            
            var inp = div1.find('input');
            var format = "yyyy-MM-dd";
            var pickerType = "date";

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
            this.pickerType = pickerType;
            
            inp.inputmask("datetime", {inputFormat:format,placeholder: format.replace("yyyy",'____').replace("MM","__").replace("dd","__")});
//          var datepicker = new tui.DatePicker(div2[0], {
//              date : new Date(),
//              input : {
//                  element : inp[0],
//                  format: format,
//                  syncFromInput : true
//              },
//              type: pickerType,
//              language : "ko",
//              openers : [div1.find(".tui-ico-date")[0]]
//          });
//          this.datepicker = datepicker;
////            
            if(this.pickerType == "date"){
                var opt = {
                    dateFormat: "yy-mm-dd",
                    changeMonth: true,
                    changeYear: true,
                    showButtonPanel: true,
                    dayNamesMin: [ "일", "월", "화", "수", "목", "금", "토" ],
                    showMonthAfterYear: true,
                    monthNamesShort : [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ],
                    monthNames : [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ],
                    yearRange: "-150:+150",

                };
                if(col.minDate){
                    var minDt = null;
                    if(col.minDate== "today"){
                        minDt = new Date();
                    }
                    else if(col.minDate== "tomorrow"){
                        minDt = moment().add(1,"day").toDate();
                    }
                    else{
                        minDt = moment(col.minDate,'YYYYMMDD').toDate();
                    }
                        
                    if(minDt)
                        opt.minDate = minDt;
                }
                $(inp ).datepicker(opt);
                span.find(".tui-ico-date").click(function(){
                    inp.datepicker("show");
                });
            } 
            else if(this.pickerType == "month")
            {
                var options = {
                        changeMonth: true,
                        changeYear: true,
                        showButtonPanel: true,
                        dateFormat: "yy-mm",
                        monthNamesShort : [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ],
                        monthNames : [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ],
                };
                inp.monthpicker(options);
                span.find(".tui-ico-date").click(function(){
                    inp.monthpicker("show");
                });
            }
            else if(this.pickerType == "year")
            {
//              inp.addClass("yearpicker");
//              inp.yearpicker({
//              });
//              span.find(".tui-ico-date").click(function(){
//                  inp.yearpicker("show");
//              });
                
            }
            
            var value = Utilities.parseDate(props.value);
            if(value){
                var year = value.getFullYear() + "";
                var month = Utilities.paddingZero(value.getMonth() + 1, 2);
                var day = Utilities.paddingZero(value.getDate(), 2);
                    
                var dValue = format.replace('yyyy', year)
                var dValue = dValue.replace('MM', month)
                var dValue = dValue.replace('dd', day)
                inp.val(dValue);
            }
            else
            {
                inp.val("");
            }
            
//          datepicker._onChangeInput();

          }

          this.getElement=function() {
            return this.el;
          }

          this.getValue=function() {
              var val = $(this.el).find('input').val().replace(/-/g,'');
              
            return val;
          }

          this.mounted=function() {
              var inp =  $(this.el).find('input');
//            this.datepicker._onChangeInput();
              inp[0].select();
              if(this.pickerType == "date"){inp.datepicker("show");}
              else if(this.pickerType == "month"){inp.monthpicker("show");}
//            else if(this.pickerType == "year"){inp.yearpicker("show");}
//            inp.datepicker("show");
//            this.datepicker.open();
// var val = this.el.value;
// var val = $(this.el).val();
// this.el.value = val;
// this.el.select();
          }
          this.constructor(props);
        }
    
    
    
    
    
        function BooleanRenderer(props) {
          this.constructor=function(props) {
			const lbl = $('<label class="mCheckbox1"><input type="checkbox"><span class="label">&nbsp;</span></label>');
            const el = lbl.find("input")[0];
             
            const grid = props.grid;
            el.editable = props.columnInfo.renderer.editable && (grid.canEdit(props.columnInfo.name,props.rowKey,grid.getRow(props.rowKey)));
            const disableCol = grid.gridWrapper.gridConfig.disableCol;
		 	  if(disableCol){
					var rowData = grid.getRow(props.rowKey);
					if(rowData[disableCol] == "Y")
					{
						el.editable = false;
					}	
			   }
			   
            $(el).attr("data-colname",props.columnInfo.name)
            $(el).attr("data-row-key",props.rowKey)
            $(el).attr("data-grid-id",props.grid.gridId)
            if(!el.editable){
				$(el).prop('disabled',true);
			}
            $(lbl).find("input[type=checkbox]").click(function(e){
                e.stopPropagation();
 
                if(el.editable )
                {
                    
                    var value =  el.checked ? "Y" : "N";
                    grid.setValue(el.rowKey, el.columnName,value, false);
                    var value = grid.getValue(el.rowKey,el.columnName);
                    grid.setModified(el.rowKey);
                    var chked = value == "Y";
                    if(chked){
                    	var list = grid.getData();
                    	for(var i=0;i<list.length;i++){
                    		if(list[i][el.columnName]!="Y")
                    		{
                    			chked = false;
                    			break;
                    		}
                    	}
                    }
					var chk = grid.gridWrapper.gridWrap.find('#'+el.columnName+'_' + grid.gridId);
					chk.prop("checked",chked);
					grid.onBooleanChecked(el.rowKey,el.columnName,el.checked);
					
                } 
                else
                	e.preventDefault();
               
            });
            this.el = el;
            this.lbl = lbl[0];
           
            this.render(props);
          }

          this.getElement=function() {
            return this.lbl;
          }

          this.render=function(props) {
              if(!props.grid.canRender(props)){
                  return false;
              }
            this.el.checked = props.value=="Y";
            this.el.rowKey = props.rowKey;
            this.el.columnName = props.columnInfo.name;
            
            
          }
          this.constructor(props);
        }
        function ButtonRenderer(props) {
          this.constructor=function(props) {
              const el = document.createElement('div');
              const gridId = props.columnInfo.renderer.gridId;
            el.gridId = gridId;
            this.el = el;
            el.displayName = props.columnInfo.renderer.displayName;
            this.render(props);
          }

          this.getElement=function() {
            return this.el;
          }

          this.render=function(props) {
              if(!props.grid.canRender(props)){
                  return false;
              }
            this.el.rowKey = props.rowKey;
            this.el.columnName = props.columnInfo.name;
            const gridId = props.grid.gridId;
            var grid = props.grid;
//            var btn = $('<a href="#" data-field-name="' + props.columnInfo.name + '" data-grid-id="' + gridId + '" style="text-decoration:underline;" data-row="' + props.rowKey+ '" >' + this.el.displayName +'</a>');
//            var btn = $('<button type="button" data-field-name="' + props.columnInfo.name + '" data-grid-id="' + gridId + '"  data-row="' + props.rowKey
//                    + '" class="btn_whites">' + this.el.displayName + '</button>');
            var btn = $('<a href="#;" data-field-name="' + props.columnInfo.name + '" data-grid-id="' + gridId + '"  data-row="' + props.rowKey +'" class="mBtn1 m lWhite">' + this.el.displayName + '</a>');
            $(this.el).html("");
            $(this.el).append(btn);
            var $el = this.el;
            btn.click(function() {
                var data = $(this).data();
                var gridView = grid;
                if (gridView)
                    gridView.onGridButtonClick(gridView, data);
            });
          }
          this.constructor(props);
        }
        
        function EzTextRenderer(props) {
            this.constructor=function(props) {
              const el = document.createElement('div');
              const col = props.columnInfo.renderer.colInfo;
              this.el = el;
              el.col = col;
              
              var align = col.align;
              if(!align)
                  align = "center";
              
              $(el).css("text-align",align);
              
              var maxLength = null;
              
              this.render(props);
            }

            this.getElement=function() {
              return this.el;
            }

            this.render=function(props) {
                if(!props.grid.canRender(props)){
                    return false;
                }
                var el =this.el;
                var col = this.el.col
                var val = props.value;

                if(val == null || val == undefined)
                    val = "";
                if(val && col["masking"]){
                    const masking = col["masking"];
                    if(masking == "phone"){
                        let tmp = val.replace("-","");
                        if(tmp.length > 7){
                            val = tmp.substring(0,3) + "-****-" + tmp.substring(7);
                        } else {
                            val = tmp.substring(0,3) + "-****" ;
                        }
                    } else if(masking == "name"){
                        if(val.length>2)
                            val = val.substring(0,1) + "*" + val.substring(2);
                         else
                            val = val.substring(0,1) + "*";
                        
                    } else if(masking == "address"){
                        val = "**********";
                    } else if(masking == "email"){
                        try{
                            if(val.indexOf("@") > 3){
                                const ar = val.split("@");
                                val = ar[0].substr(0,ar[0].length-3) + "***@" + ar[1];
                            } else if(val.indexOf("@")>-1){
                                const ar = val.split("@");
                                val = val+ "***@" + ar[1];
                            } else {
                                val = val.substring(0,val.length-3) + "***"; 
                            }    
                        }catch(e){

                        }
                        
                    }
                }
                var grid = props.grid;
                var colName = col.field;
                var editable = col.displayEditor =="Y" && col.readOnly !='Y' && (grid.canEdit(colName,props.rowKey,grid.getRow(props.rowKey)));
                if(editable){
                	var span = $('<span style="width:90%;vertical-align:middle"/>');
                    var btnSpan = $('<i style="cursor:pointer; position: absolute;top: 50%;right: 8px;margin: -6px 0 0 0" class="fas fa-pen"></i>');
                    var valSpan = $('<span>'+val+'</span>')
                    var div1=   $('<div  style="width:100%;vertical-align:middle;border:0px;height:auto" class="tui-datepicker-input tui-datetime-input tui-has-focus"></div>');
                    
                    span.append(div1);
                    div1.append(valSpan);
                    div1.append(btnSpan);
                    $(this.el).html("");
                    $(this.el).append(span);
                    btnSpan.click(function(){
                    	grid.startEditing(props.rowKey, colName);
                      });
                }
                else{
                     var html = '<div style="margin-left:3px;margin-right:3px; text-overflow: ellipsis;overflow: hidden;white-space: nowrap;">'+val+'</div>';
                        $(el).html(html);    
                    
                	if(col.textColor)
                     	$(el).css("color",col.textColor);
                     
                }
                if(col.textUnderline == "Y"){
                    $(el).css("text-decoration","underline");
                    $(el).css("cursor","pointer");
                }
                
                
                
                
                
                
               
                
            }
            this.constructor(props);
          }
          
        
        function EzTextEditor(props) {
            this.constructor=function(props) {
                const el = document.createElement('input');
                var colInfo = props.columnInfo.editor.colInfo;
                el.colInfo = colInfo;
                el.type = 'text';
                if(colInfo.maxLength)
                  el.maxLength = colInfo.maxLength;
                el.value = String(props.value==null ? "" :props.value );

                this.el = el;
            }

            this.getElement=function() {
              return this.el;
            }

            this.getValue=function() {
              return this.el.value;
            }

            this.mounted=function() {
              this.el.select();
            }
            this.constructor(props);
          }
    function TimeRenderer(props) {
        this.constructor=function(props) {
            const el = document.createElement('div');
            const format = props.columnInfo.renderer.format;
            this.el = el;
            el.colInfo = props.columnInfo.renderer.colInfo;
            el.format = format;
            this.render(props);
          }
          this.getElement=function() {
            return this.el;
          }
          
          this.render=function(props) {
            if(!props.grid.canRender(props)){
                  return false;
              }
              var dValue = "";
              var col = this.el.colInfo;
              if(!props.value){
                  dValue= "";
              }
              else{
                dValue = props.value;
                if(dValue.length == 4 || dValue.length == 6 )
                {
                    if(dValue.length == 4){
                        dValue = dValue.substring(0,2) + ":" + dValue.substring(2,4);
                    }
                    else
                        dValue = dValue.substring(0,2) + ":" + dValue.substring(2,4) + ":" + dValue.substring(4,6);
                }
              }
              
             this.el.innerHTML = dValue;
             if(col.textUnderline == "Y"){
                $(this.el).css("text-decoration","underline");
                $(el).css("cursor","pointer");
             }
          }
          this.constructor(props);
    }
    function TimeEditor(props) {
          this.constructor=function(props) {
            const el = document.createElement('input');
            this.el =el;
            var col  = props.columnInfo.editor.colInfo;

            el.type = 'text';
            el.colInfo = col;
            
            if(col.maxLength>=6)
                $(this.el).inputmask("datetime", {inputFormat: "HH:MM:ss",placeholder: '__:__:__'});
            else
                $(this.el).inputmask("datetime", {inputFormat: "HH:MM",placeholder: '__:__'});
            var align = col.align;
            if(!align)
                align = "center";
            
            $(el).css("text-align",align);
            el.value = String(props.value);
          }

          this.getElement=function() {
            return this.el;
          }

          this.getValue=function() {
              var val =Utilities.getNumberOnly(this.el.value);
            return val;
          }

          this.mounted=function() {
            var val = $(this.el).val();
            this.el.value = val;
            this.el.select();
          }
          this.constructor(props);
        }
    
    function initFileCol(col,el,data,fileCd,fileSeq,fileCategory)
    {
        var key = fileCd+"_"+fileSeq;
        var acceptTypes = col.acceptTypes;
                    var acceptExt = col.acceptExt;
                    if(acceptTypes)
                        acceptTypes = "data-accept-types='"+acceptTypes+"'";
                    if(acceptExt)
                        acceptExt = "data-accept-ext='"+acceptExt+"'";
                    if(!acceptExt)
                        acceptExt = "";
                    var info = $('<span data-type="fileInfo" '+acceptTypes+' '+acceptExt+' data-file-cd="'+fileCd+'" data-file-seq="'+fileSeq+'" style="display:none"></span>')
                    var btnAdd = $('<a href="#;"  data-type="btnFileAdd" '+acceptTypes+' '+acceptExt+' data-file-cd="'+fileCd+'" data-file-seq="'+fileSeq+'"><i class="fas fa-cloud-upload-alt" ></i></a>');
            //      var btnAdd =  $('<button style="width:30px"  data-type="btnFileAdd"  data-file-cd="'+fileCd+'" data-file-seq="'+fileSeq+'" class="btnBasic">첨부</button>');
                    var btnDown = $('<a href="#;"  data-type="btnFileAdd"  '+acceptTypes+' '+acceptExt+' data-file-cd="'+fileCd+'" data-file-seq="'+fileSeq+'"><i class="fas fa-cloud-download-alt"></i></a>');
                    var btnRemove = $('<a href="#;"  data-type="btnFileAdd" '+acceptTypes+' '+acceptExt+' data-file-cd="'+fileCd+'" data-file-seq="'+fileSeq+'"><i class="fas fa-trash-alt"></i></a>');
                    var btnPreview = $('<a href="#;"  data-type="btnFilePrev" '+acceptTypes+' '+acceptExt+' data-file-cd="'+fileCd+'" data-file-seq="'+fileSeq+'"><i class="fas fa-play"></i></a>');
                    
                    $(el).append(info);
                    $(el).append(btnAdd);
                    $(el).append("&nbsp;");
                    $(el).append(btnDown);
                    $(el).append("&nbsp;");
                    $(el).append(btnPreview);
                    $(el).append("&nbsp;");
                    $(el).append(btnRemove);
                    if(data == null){
                            btnDown.hide();
                            btnRemove.hide();
                            btnPreview.hide();
                    }
                    else{
                        el.fileUrl = data.fileUrl;
                        el.fileNm = data.fileNm;
                        el.fileInfo = data;
                    }
                    btnAdd.click(function(){
                        if(el.fileUrl){
                            if(!confirm('파일을 업로드 하면 기존 파일일 덮어 쓰게 됩니다.\n계속 하시겠습니까?'))
                                return;
                        }
                        var data = $(this).data();
                        var uploader = Utilities.getFileUploader({
                            acceptTypes : data.acceptTypes,
                            acceptExt : data.acceptExt,
                            singleFile : true,
                            addCallback : function(e, data){
                                var file = data.file;
                                var fileExt = Utilities.getFileExt(file.name);
                                var fileInfo = {
                                    fileCd : fileCd,
                                    fileOdrg : fileSeq,
                                    fileNm : file.name,
                                    fileSize : file.size,
                                    mimeType : file.type,
                                    saveNm : null,
                                    id : data.id,
                                    fileExt : fileExt,
                                    stat : 'c'
                                };
                                
                                if(!fileCategory)
                                fileCategory = "";
                                var uploadUrl = _basePath + "/file/uploadFile"+_urlSuffix+"?fileCtgryCd="+fileCategory;
                                uploader.upload(data.id, uploadUrl, fileInfo
                                                 , function(id, resultData, result){
                                                    if(result)
                                                    {
                                                        btnDown.show();
                                                        btnRemove.show();
                                                        btnPreview.show();
                                                        el.fileUrl = resultData.fileUrl;
                                                        el.fileNm = resultData.fileNm;
                                                        el.fileInfo = resultData;
                                                        col.fileDataMap[key] = null;
                                                        alert('파일업로드에 성공했습니다.');
                                                    }   
                                                    else
                                                    {
                                                        alert('파일업로드에 실패했습니다.');
                                                    }
                                            
                                                }, function(id, loaded, total, percent) {
//                                                  var per = parseInt(percent*100);
//                                                  var info = Utilities.numberWithCommas(parseInt(loaded/1024)) + "KB/ "  + Utilities.numberWithCommas(parseInt(total/1024)) + "KB("+ per +"%)";
//                                                  $("[data-file-info=info][data-file-cd="+fileCd+"][data-file-seq="+fileSeq+"]").html(info);
                                            //      console.log("["+id+"]["+loaded+"]["+total+"]["+percent+"]");
                                                });
                                
                            }
                            
                        });
                        uploader.addFile();
                    });
                    btnDown.click(function(){
//                        var fileUrl = el.fileInfo.previewUrl || el.fileUrl;
                        var fileNm = el.fileNm;
                        var downloadUrl = "/util/file/download?fileCd="+el.fileInfo.fileCd +"&fileOdrg="+el.fileInfo.fileOdrg;
                        Utilities.downloadFileUrl(downloadUrl,fileNm);
                    });
                    btnPreview.click(function(){
                    	if(!el.fileInfo)
                    		return;
						var downloadUrl = "/util/file/download?fileCd="+el.fileInfo.fileCd +"&fileOdrg="+el.fileInfo.fileOdrg;
	
//                    	if(!el.fileInfo.previewUrl)
//                    	{
//                    		alert("파일주소가 생성되지 않았습니다.");
//                    		return;
//                    	}
                    	var idx = el.fileNm.lastIndexOf(".");
                    	var ext = el.fileNm.substring(idx+1).toLowerCase();
                    	if(ext =="mp4" || ext =="m3u8"){
                    		Utilities.showVideo(downloadUrl);
                    		return;
                    	}
                    	else if(ext =="png" || ext =="thumb" || ext=="jpg" || ext=="gif" || ext=="bmp" || ext=="tiff" || ext=="psd"){
                    		Utilities.showImage(downloadUrl);
                    		return;
                    	}
                    	else if(ext =="mp3"){
                    		Utilities.playAudio(downloadUrl);
                    		return;
                    	}else if(ext =="pdf"){
//                    		var url = "/util/file/downloadFromUrl?url="+encodeURIComponent(downloadUrl);

                    		Utilities.showPdf(downloadUrl);
                    		return;
                    	}else {
                    		alert("미리볼 수 없는 파일입니다.")
                    	}
                    	
                    });
                    btnRemove.click(function(){
                        if(el.fileUrl){
                            if(!confirm('한번 삭제한 파일은 복구하지 못합니다.\n계속 하시겠습니까?'))
                                return;
                            
                        }
                        
                        
                        var removeUrl = _basePath + "/file/removeFileInfo"+_urlSuffix;
                        var param = el.fileInfo;
                        Utilities.getAjax(removeUrl,param,true,function(data,jqXHR){
                            if(Utilities.processResult(data,jqXHR,"파일정보 삭제에 실패했습니다.")){
                                alert("파일정보 삭제에 성공했습니다.")
                                el.fileUrl = null;
                                el.fileNm = null;
                                el.fileInfo = null;
                                btnDown.hide();
                                btnRemove.hide();
                                btnPreview.hide();
                                col.fileDataMap[key] = null;
                            }
                        });
                        
                        
                    });
    }