/**
 * 
 */
layui.use(['laydate','form'], function(){
        var laydate = layui.laydate;
        var  form = layui.form;


        // 监听全选
        form.on('checkbox(checkall)', function(data){

            if(data.elem.checked){
                $('tbody input').prop('checked',true);
            }else{
                $('tbody input').prop('checked',false);
            }
            form.render('checkbox');
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#start' //指定元素
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#end' //指定元素
        });
    });

/*商品-删除*/
function deletegood(gid,order) {
//	alert(gid);
//	alert(order);
	console.log("delete");
	layer.confirm('提示：<br>点击确认后会将此商品删除！<br>您确定继续此操作吗？', {
		btn: ['确定', '取消'] //按钮
	}, function() {
		var encryptor = new JSEncrypt() // 创建加密对象实例
		var publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDstiYKiePxsEJOiLskFkxwcGPC"
			+ "SUIHE4cmO0WWzC60zRQYEkUxRauwQoVbe+6wiVgSyDn3yvezfdMja8wTPaUsPh3i"
			+ "GYJPWAxZlH5fjv5G02r73LbFJLgveUIjSgykN50smt6S4+fzXg41nrp5Vasg9t9S"
			+ "NzjjU7XTYSG+75EuJwIDAQAB";
		encryptor.setPublicKey(publicKey);//设置公钥
		var engid = encryptor.encrypt(gid+"");
		var enorder = encryptor.encrypt(order+"");
		var myForm = document.createElement("form");
		myForm.method = "post";
		myForm.action = "/E-shop/delete_servlet";
		var myInput1 = document.createElement("input");
		myInput1.setAttribute("name", "engid");
		myInput1.setAttribute("value", engid);
		myForm.appendChild(myInput1);
		var myInput2 = document.createElement("input");
		myInput2.setAttribute("name", "enorder");
		myInput2.setAttribute("value", enorder);
		myForm.appendChild(myInput2);
		document.body.appendChild(myForm);
		myForm.submit();
		document.body.removeChild(myForm); // 提交后移除创建的form
	}, function() {
		console.log("取消");
	});
}

function beforeSubmitAdd(form) {
	if (form.enname.value == '') {
		alert('商品名称不能为空！');
		form.enname.focus();
		return false;
	}
	if (form.enname.value.length > 30) {
		alert('商品名称不能超过15格汉字，30个字符！请重新输入！');
		form.enname.focus();
		return false;
	}
	if (form.enprice.value == null) {
		alert('价格不能为空！');
		form.enprice.focus();
		return false;
	}
	if (form.enquantity.value == null) {
		alert('库存不能为空！');
		form.enquantity.focus();
		return false;
	}
	var encryptor = new JSEncrypt() // 创建加密对象实例
	var publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDstiYKiePxsEJOiLskFkxwcGPC"
		+ "SUIHE4cmO0WWzC60zRQYEkUxRauwQoVbe+6wiVgSyDn3yvezfdMja8wTPaUsPh3i"
		+ "GYJPWAxZlH5fjv5G02r73LbFJLgveUIjSgykN50smt6S4+fzXg41nrp5Vasg9t9S"
		+ "NzjjU7XTYSG+75EuJwIDAQAB";
	encryptor.setPublicKey(publicKey);//设置公钥
	form.enorder.value = encryptor.encrypt(form.enorder.value);
//	form.enname.value = encryptor.encrypt(form.enname.value);
	form.enquantity.value = encryptor.encrypt(form.enquantity.value);
	form.enprice.value = encryptor.encrypt(form.enprice.value);
	return true;
}

function beforeSubmitModify(form) {
	if (form.enname.value == '') {
		alert('商品名称不能为空！');
		form.enname.focus();
		return false;
	}
	if (form.enname.value.length > 30) {
		alert('商品名称不能超过15格汉字，30个字符！请重新输入！');
		form.enname.focus();
		return false;
	}
	if (form.enprice.value == null) {
		alert('价格不能为空！');
		form.enprice.focus();
		return false;
	}
	if (form.enquantity.value == null) {
		alert('库存不能为空！');
		form.enquantity.focus();
		return false;
	}
	var encryptor = new JSEncrypt() // 创建加密对象实例
	var publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDstiYKiePxsEJOiLskFkxwcGPC"
		+ "SUIHE4cmO0WWzC60zRQYEkUxRauwQoVbe+6wiVgSyDn3yvezfdMja8wTPaUsPh3i"
		+ "GYJPWAxZlH5fjv5G02r73LbFJLgveUIjSgykN50smt6S4+fzXg41nrp5Vasg9t9S"
		+ "NzjjU7XTYSG+75EuJwIDAQAB";
	encryptor.setPublicKey(publicKey);//设置公钥
	form.engid.value = encryptor.encrypt(form.engid.value);
	form.enorder.value = encryptor.encrypt(form.enorder.value);
//	form.enname.value = encryptor.encrypt(form.enname.value);
	form.enquantity.value = encryptor.encrypt(form.enquantity.value);
	form.enprice.value = encryptor.encrypt(form.enprice.value);
	return true;
}

//过滤类型
function fileType(obj){
    var fileType=obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();//获得文件后缀名
    if(fileType != '.gif' && fileType != '.png' && fileType != '.jpg' && fileType != '.jpeg'){
        $(obj).tips({
            side:3,
            msg:'请上传图片格式的文件',
            bg:'#AE81FF',
            time:3
        });
        $(obj).val('');
    }else{
         var reader = new FileReader();
         reader.readAsDataURL(obj.files[0]);
         reader.onload = function (e) { 
            $("#img").attr("src",this.result);
         }
         $("#tu").show();
    }
}