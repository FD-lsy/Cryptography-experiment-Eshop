/**
 * 
 */
function beforeSubmit(form) {
		if (form.username.value == '') {
			alert('用户名不能为空！');
			form.username.focus();
			return false;
		}
		if (form.password.value == '') {
			alert('密码不能为空！');
			form.password.focus();
			return false;
		}
		if (form.password.value.length < 6) {
			alert('密码至少为6位，请重新输入！');
			form.password.focus();
			return false;
		}
		return true;
	}