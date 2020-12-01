$(function(){
	//总记录数
	var totalRecord;
	to_page(1);
	
	function reset_form(ele) {
		//清除表单数据
		$(ele)[0].reset();
		//清空表单样式
		$(ele).find("*").removeClass("has-error has-success");
		$(ele).find(".help-block").text("");
	}
	
	//新增按钮弹出模态框
	$("#emp_add_modal_btn").click(function(){
		//清除表单
		reset_form("#empAddModal form");
		
		//查询部门信息
		getDepts();
		
		$("#empAddModal").modal({
			backdrop:"static"
		});
	});
	
	//员工名字和邮箱校验
	function validate_add_form() {
		//正则表达式校验
		//字符a-z,A-Z,0-9 _ -(6-16位)，中文（2-5个）
		var empName = $("#empName_add_input").val();
		var regName = /(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5}$)/;
		if (!regName.test(empName)) {
			//alert("用户名可以是2-5位中文或者是6-16位英文和数字的组合");
			//$("#empName_add_input").parent().addClass("has-error");
			//$("#empName_add_input").next("span").text("用户名可以是2-5位中文或者是6-16位英文和数字的组合");
			show_validate_msg("#empName_add_input", "error", "用户名可以是2-5位中文或者是6-16位英文和数字的组合");
			return false;
		} else {
			//$("#empName_add_input").parent().removeClass("has-error");
			//$("#empName_add_input").parent().addClass("has-success");
			//$("#empName_add_input").next("span").text("");
			show_validate_msg("#empName_add_input", "success", "");
		}
		
		var email = $("#email_add_input").val();
		var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
		if (!regEmail.test(email)) {
			//alert("邮箱格式不正确");
			show_validate_msg("#email_add_input", "error", "邮箱格式不正确");
			return false;
		} else {
			show_validate_msg("#email_add_input", "success", "");
		}
		
		return true;
	}
	
	//显示校验信息
	function show_validate_msg(ele, status, msg) {
		//清楚状态
		$(ele).parent().removeClass("has-success has-error");
		$(ele).next("span").text("");
		if ("success" == status) {
			$(ele).parent().addClass("has-success");
			$(ele).next("span").text(msg);
		} else if ("error") {
			$(ele).parent().addClass("has-error");
			$(ele).next("span").text(msg);
		}
	}
	
	//校验用户名是否已存在
	$("#empName_add_input").change(function(){
		$.ajax({
			url: "/ssm-crud/checkuser",
			data: "empName="+this.value,
			type: "post",
			success: function(result) {
				if (result.code == 100) {
					show_validate_msg("#empName_add_input", "success", "用户名可用");
					$("emp_save_btn").attr("ajax-va", "success");
				} else {
					show_validate_msg("#empName_add_input", "error", result.extend.va_msg);
					$("emp_save_btn").attr("ajax-va", "error");
				}
			}
		});
	});
	
	$("#emp_save_btn").click(function(){
		//表单数据提交保存
		//alert($("#empAddModal form").serialize());
		//创建员工json对象
		var emp = {};
		emp.empName = $("#empName_add_input").val();
		emp.email = $("#email_add_input").val();
		emp.gender = $("input[name='gender']:checked").val();
		//获取选定的部门
		emp.dept = {
				deptId: $("#dept_add_select option:selected").val()
		};
		//console.log(emp);
		
		//数据校验
		if (!validate_add_form()) {
			return false;
		}
		
		//校验用户名是否已存在
		if ($(this).attr("ajax-va") == "error") {
			return false;
		}
		
		$.ajax({
			url: "/ssm-crud/emps",
			type: "post",
			data: JSON.stringify(emp),
			contentType : "application/json",
			processData : false,
			cache : false,
			success: function(result) {
				if (result.code == 100) {
					//1. 关闭模态框
					$("#empAddModal").modal("hide");
					//2. 显示最后一页
					//跳转到一个大于总页数的数值，总记录数肯定大于总页数
					to_page(totalRecord);
				} else {
					//console.log(result);
					//显示错误信息
					if (result.extend.errorFields.email != undefined) {
						//显示邮箱错误信息
						show_validate_msg("#email_add_input", "error", result.extend.errorFields.email);
					}
					
					if (result.extend.errorFields.empName != undefined) {
						//显示员工的错误信息
						show_validate_msg("#empName_add_input", "error", result.extend.errorFields.empName);
					}
					
				}
			}
		});
	});
	
	function getDepts() {
		$.ajax({
			url:"/ssm-crud/depts",
			type:"GET",
			success:function(result){
				//console.log(result);
				$("#empAddModal select").empty();
				//显示部门信息在下拉列表中
				$.each(result.extend.depts, function(){
					var optionEle = $("<option></option>")
						.append(this.deptName)
						.attr("value", this.deptId);
					optionEle.appendTo("#empAddModal select");
				});
			}
		});
	}

	function to_page(pn) {
		$.ajax({
			url:"/ssm-crud/emps",
		data:"pn="+pn,
		type:"GET",
		success:function(result) {
			//console.log(result);
			//1、解析并显示员工数据
			build_emps_table(result);
			//2、解析显示分页信息
			build_page_info(result);
			//3、解析显示分页条
				build_page_nav(result);
			}
		});
	}
	
	function build_emps_table(result) {
		
		//清空table
		$("#emps_table tbody").empty();
		
		var emps = result.extend.pageInfo.list;
		$.each(emps, function(index, item){
			var empId = $("<td></td>").append(item.empId);
		var empName = $("<td></td>").append(item.empName);
		var gender = $("<td></td>").append(item.gender == 'M' ? "男" : "女");
		var email = $("<td></td>").append(item.email);
		var deptName = $("<td></td>").append(item.dept.deptName);
		
		var editBtn = $("<button></button>").addClass("btn btn-info btn-sm")
			.append($("<span></span>")).addClass("glyphicon glyphicon-pencil")
			.append("编辑");
		
		var delBtn = $("<button></button>").addClass("btn btn-danger btn-sm")
		.append($("<span></span>")).addClass("glyphicon glyphicon-trash")
		.append("删除");
		
		var btn = $("<td></td>").append(editBtn).append(delBtn);
		
		$("<tr></tr>").append(empId)
					.append(empName)
					.append(gender)
					.append(email)
					.append(deptName)
					.append(btn)
					.appendTo("#emps_table tbody");
		});
	}
	
	function build_page_info(result) {
		$("#page_area_info").empty();
		
		$("#page_area_info").append("当前第"+result.extend.pageInfo.pageNum+"页，总共"
			+result.extend.pageInfo.pages+"页，总共"
			+result.extend.pageInfo.total+"条记录。");
		totalRecord = result.extend.pageInfo.total;
	}
	
	function build_page_nav(result) {
		$("#page_nav_info").empty();
		
		var ul = $("<ul></ul>").addClass("pagination");
	
		var firstPage = $("<li></li>").append($("<a></a>").append("首页").attr("href", "#"));
		firstPage.click(function(){
			to_page(1)
		});
		
		var previousPage = $("<li></li>").append($("<a></a>").append("&laquo;"));
		previousPage.click(function(){
			to_page(result.extend.pageInfo.pageNum - 1);
		});
		
		if (result.extend.pageInfo.hasPreviousPage == false) {
			firstPage.addClass("disabled")
			previousPage.addClass("disabled");
		}
		
		var nextPage = $("<li></li>").append($("<a></a>").append("&raquo;"));
		nextPage.click(function(){
			to_page(result.extend.pageInfo.pageNum + 1);
		});
		
		var lastPage = $("<li></li>").append($("<a></a>").append("末页").attr("href", "#"));
		lastPage.click(function(){
			to_page(result.extend.pageInfo.pages);
		});
		
		if (result.extend.pageInfo.hasNextPage == false) {
			nextPage.addClass("disabled")
			lastPage.addClass("disabled");
		}
		
		ul.append(firstPage).append(previousPage);
		
		$.each(result.extend.pageInfo.navigatepageNums, function(index, item){
			var numLi = $("<li></li>").append($("<a></a>").append(item));
			if (result.extend.pageInfo.pageNum == item) {
				numLi.addClass("active");
			}
			numLi.click(function(){
				to_page(item);
			});
			ul.append(numLi);
		});
		
		ul.append(nextPage).append(lastPage);
		
		var navEle = $("<nav></nav>").append(ul);
		navEle.appendTo("#page_nav_info");
	}
});	