function getURLParameter(name) {
    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search) || [, ""])[1].replace(/\+/g, '%20')) || null;
}
function getDateArray(date){
    let d = date.toString().split('T')[0];
}
/*;!function(){
    layer.config({//加载扩展模块
        extend: 'extend/layer.ext.js'
    });
    layer.ready(function(){
    });
}();*/

function ityzl_SHOW_LOAD_LAYER(){
    return layer.msg('请稍等', {icon: 16,shade: [0.5, '#f5f5f5'],scrollbar: false,offset: '0px', time:100000}) ;
}
function ityzl_CLOSE_LOAD_LAYER(index){
    layer.close(index);
}
function ityzl_SHOW_TIP_LAYER(){
    layer.msg('等候完成！',{time: 1000,offset: '10px'});
}