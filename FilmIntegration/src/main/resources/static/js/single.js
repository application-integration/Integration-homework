function GetQueryString(name)
{
	 // 获取参数
    var url = window.location.search;
    // 正则筛选地址栏
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    // 匹配目标参数
    var result = url.substr(1).match(reg);
    //返回参数值
    return result ? decodeURIComponent(result[2]) : null;
}
$(function(){

    $("#mytab").tab('show');

    var targetMovie = (GetQueryString("name"));

    
    $("#nuomi_table").bootstrapTable({
        url:'/api/filmInfo/nuomi',         //请求后台的URL（*）
        method:'get',
        striped: true,                      //是否显示行间隔色
        cache: false,
        pagination: true,                   //是否显示分页（*）
        sidePagination: "client",           //分页方式
        pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
        pageSize: 10,                     //每页的记录行数（*）
        search: true,                      //是否显示表格搜索
        showToggle: true,                   //卡片、表格切换按钮
        locale: "zh-CN",
        queryParams:{
            name:targetMovie
        },
        columns: [{
            field: 'name',
            title: '电影名称'
        }, {
            field: 'cinema',
            title: '电影院'
        }, {
            field: 'date',
            title: '日期'
        }, {
            field: 'auditorium',
            title: '厅'
        }, {
            field: 'time',
            title: '时间'
        }, {
            field: 'price',
            title: '票价'
        }]
    });


    $("#meituan_table").bootstrapTable({
        url:'/api/filmInfo/meituan',         //请求后台的URL（*）
        method:'get',
        striped: true,                      //是否显示行间隔色
        cache: false,
        pagination: true,                   //是否显示分页（*）
        sidePagination: "client",           //分页方式
        pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
        pageSize: 10,                     //每页的记录行数（*）
        search: true,                      //是否显示表格搜索
        showToggle: true,                   //卡片、表格切换按钮
        locale: "zh-CN",
        queryParams:{
            name:targetMovie
        },
        columns: [{
            field: 'name',
            title: '电影名称'
        }, {
            field: 'cinema',
            title: '电影院'
        }, {
            field: 'date',
            title: '日期'
        }, {
            field: 'auditorium',
            title: '厅'
        }, {
            field: 'time',
            title: '时间'
        }, {
            field: 'type',
            title: '种类'
        }]
    });
});