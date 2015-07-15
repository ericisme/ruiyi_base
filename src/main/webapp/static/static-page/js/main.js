


//首页slider参数
$(function() {
	   $('.carousel').carousel({
		   interval: 3500, //3.5秒间隔
		   pause: "hover" //鼠标悬停停止滚动
	   });
});

//下拉菜单
$('.dropdown-toggle').dropdown();

// canvas sidebar open
$('[data-toggle=offcanvas]').click(function() {
$('.row-offcanvas').toggleClass('active');
});

//文章翻页动画效果
function nextPage(){
    $("#article-content").addClass("fadeOutDown");
};



//展开全文
$(".btnDisplayall").click(function(){
 $(".game-description").addClass("display-all");
 $(".btnDisplayall").addClass("hidden");
});



////首页页面滚动加载动画
// function isScrolledIntoView(elem) {
//     var docViewTop = $(window).scrollTop();
//     var docViewBottom = docViewTop + $(window).height();
//     var elemTop = $(elem).offset().top + $(elem).height()*1.5;
//     return ((elemTop <= docViewBottom) && (elemTop >= docViewTop));
// }
//
// $(window).scroll(function () {
//     $('.animated').each(function (i) {
//         if (isScrolledIntoView(this)) {
//             $(this).addClass("bounceIn");
//         } 
//     });
//
// });
//


//ie的placeholder
$('input, textarea').placeholder();

/**
 * 控制按钮组active的通用方法
 * @param btn_group
 * @param btn_type
 */
function buttonGroupActive(btn_group, btn_type){
	$("."+btn_group+" "+btn_type).on('click', function() {
	      $(this).siblings().removeClass("active").end().addClass("active");
	    });
}