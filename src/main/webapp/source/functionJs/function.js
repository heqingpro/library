//���������¼� ��ֹ���˼���Backspace��������С������ı������
function banBackSpace(e){
var ev = e || window.event;//��ȡevent����
var obj = ev.target || ev.srcElement;//��ȡ�¼�Դ

var t = obj.type || obj.getAttribute('type');//��ȡ�¼�Դ����

//��ȡ��Ϊ�ж��������¼�����
var vReadOnly = obj.getAttribute('readonly');
var vEnabled = obj.getAttribute('enabled');
//����nullֵ���
vReadOnly = (vReadOnly == null) ? false : vReadOnly;
vEnabled = (vEnabled == null) ? true : vEnabled;

//����Backspace��ʱ���¼�Դ����Ϊ������С������ı��ģ�
//����readonly����Ϊtrue��enabled����Ϊfalse�ģ����˸��ʧЧ
var flag1=(ev.keyCode == 8 && (t=="password" || t=="text" || t=="textarea")
&& (vReadOnly==true || vEnabled!=true))?true:false;

//����Backspace��ʱ���¼�Դ���ͷ�������С������ı��ģ����˸��ʧЧ
var flag2=(ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea")
?true:false;

//�ж�
if(flag2){
return false;
}
if(flag1){
return false;
}
}

//��ֹ���˼� ������Firefox��Opera
document.onkeypress=banBackSpace;
//��ֹ���˼� ������IE��Chrome
document.onkeydown=banBackSpace; 