//上一页
function lastPage() {
	var currentNum=document.getElementById('currentNum').innerText;
	if (parseInt(currentNum) == 1) {
		console.log("lastPage=")
		return;
	}
	$("#pageNum").val(parseInt(currentNum)-1);
	document.getElementById("user_form").submit();
}

//下一页
function nextPage() {
	
	var currentNum=document.getElementById('currentNum').innerText;
	var maxPage=document.getElementById('maxPage').value;
	if (parseInt(maxPage) <= parseInt(currentNum)) {
		console.log("nextPage=")
		return;
	}
	console.log("11111:"+currentNum)
	$("#pageNum").val(parseInt(currentNum)+1);
	document.getElementById("user_form").submit();

}


//停用或启用用户
function stopUser(userId,isDel) {
	$.ajax({
		type : "POST",
		url : "http://127.0.0.1:8080/api/user/stopUser",
		data : {'userId':userId,'isDel':isDel},
		error : function(request) {
			parent.layer.alert("网络超时");
		},
		success : function(data) {
			console.log("data:"+data);
			if (data.code != 0) {
				alert("编辑失败");
			}else {
				alert("操作成功");
			}
			document.getElementById("user_form").submit();
		}
	});

}

//保存或编辑用户信息
function editUser() {
	var phonereg=/^[1][3,4,5,6,7,8][0-9]{9}$/;
	var emailreg = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
	
	var realname=$("#realname").val();
	var mobile=$("#mobile").val();
	var email=$("#email").val();
	var address=$("#address").val();
	var userId=$("#userId").val();
	if(realname==''){
		alert("姓名不合法");
		return;
	}else if(mobile == '' || !phonereg.test(mobile)){
		alert("手机号码不合法")
		return;
	}else if(email == '' || !emailreg.test(email)){
		alert("邮箱地址不合法")
		return;
	}else {
		document.getElementById("edit_user_form").submit();
	}
}


//重置密码
function resetPwd(userId) {
	$.ajax({
		type : "POST",
		url : "http://127.0.0.1:8080/api/user/resetPwd",
		data : {'userId':userId},
		error : function(request) {
			parent.layer.alert("网络超时");
		},
		success : function(data) {
			console.log("data:"+data);
			if (data.code != 0) {
				alert("重置失败");
			}else {
				alert("重置成功，密码"+data.data);
			}
		}
	});
}



//重置密码
function editPwd(userId) {
	var userId=$("#userId").val();
	var oldPwd=$("#oldPwd").val();
	var newPwd=$("#newPwd").val();
	var secPwd=$("#secPwd").val();
	if(oldPwd == '' || oldPwd.length < 6){
		alert("旧密码不合法")
		return;
	}else if(newPwd == '' || newPwd.length < 6) {
		alert("密码长度不足6位")
		return;
	}else if(secPwd == '' || secPwd.length < 6) {
		alert("二次密码长度不足6位")
		return;
	}else if(newPwd != secPwd) {
		alert("二次密码与新密码不一致")
		return;
	}
//	else {
//		document.getElementById("edit_user_pwd_form").submit();
//	}
	
	$.ajax({
		type : "POST",
		url : "http://127.0.0.1:8080/api/user/editPwd",
		data : {'userId':userId,'oldPwd':oldPwd,'password':secPwd},
		error : function(request) {
			parent.layer.alert("网络超时");
		},
		success : function(data) {
			console.log("data:"+data);
			if (data.code != 0) {
				alert(data.msg);
			}else {
				alert("保存成功");
			}
		}
	});
}
