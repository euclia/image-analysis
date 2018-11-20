(function($){"use strict";var cfg={defAnimation:"fadeInUp",scrollDuration:800,mailChimpURL:''},$WIN=$(window);var doc=document.documentElement;doc.setAttribute('data-useragent',navigator.userAgent);var ssPreloader=function(){$WIN.on('load',function(){$('html, body').animate({scrollTop:0},'normal');$("#loader").fadeOut("slow",function(){$("#preloader").delay(300).fadeOut("slow")})})};var ssFitVids=function(){$(".fluid-video-wrapper").fitVids()};var ssMasonryFolio=function(){var containerBricks=$('.bricks-wrapper');containerBricks.imagesLoaded(function(){containerBricks.masonry({itemSelector:'.brick',resize:!0})})};var ssLightGallery=function(){$('#folio-wrap').lightGallery({showThumbByDefault:!1,hash:!1,selector:".item-wrap"})};var ssFlexSlider=function(){$WIN.on('load',function(){$('#testimonial-slider').flexslider({namespace:"flex-",controlsContainer:"",animation:'slide',controlNav:!0,directionNav:!1,smoothHeight:!0,slideshowSpeed:7000,animationSpeed:600,randomize:!1,touch:!0,})})};var ssOffCanvas=function(){var menuTrigger=$('#header-menu-trigger'),nav=$('#menu-nav-wrap'),closeButton=nav.find('.close-button'),siteBody=$('body'),mainContents=$('section, footer');menuTrigger.on('click',function(e){e.preventDefault();menuTrigger.toggleClass('is-clicked');siteBody.toggleClass('menu-is-open')});closeButton.on('click',function(e){e.preventDefault();menuTrigger.trigger('click')});siteBody.on('click',function(e){if(!$(e.target).is('#menu-nav-wrap, #header-menu-trigger, #header-menu-trigger span')){menuTrigger.removeClass('is-clicked');siteBody.removeClass('menu-is-open')}})};var ssSmoothScroll=function(){$('.smoothscroll').on('click',function(e){var target=this.hash,$target=$(target);e.preventDefault();e.stopPropagation();$('html, body').stop().animate({'scrollTop':$target.offset().top},cfg.scrollDuration,'swing').promise().done(function(){if($('body').hasClass('menu-is-open')){$('#header-menu-trigger').trigger('click')}
    window.location.hash=target})})};var ssPlaceholder=function(){$('input, textarea, select').placeholder()};var ssAlertBoxes=function(){$('.alert-box').on('click','.close',function(){$(this).parent().fadeOut(500)})};var ssAnimations=function(){if(!$("html").hasClass('no-cssanimations')){$('.animate-this').waypoint({handler:function(direction){var defAnimationEfx=cfg.defAnimation;if(direction==='down'&&!$(this.element).hasClass('animated')){$(this.element).addClass('item-animate');setTimeout(function(){$('body .animate-this.item-animate').each(function(ctr){var el=$(this),animationEfx=el.data('animate')||null;if(!animationEfx){animationEfx=defAnimationEfx}
        setTimeout(function(){el.addClass(animationEfx+' animated');el.removeClass('item-animate')},ctr*30)})},100)}
        this.destroy()},offset:'95%'})}};var ssIntroAnimation=function(){$WIN.on('load',function(){if(!$("html").hasClass('no-cssanimations')){setTimeout(function(){$('.animate-intro').each(function(ctr){var el=$(this),animationEfx=el.data('animate')||null;if(!animationEfx){animationEfx=cfg.defAnimation}
    setTimeout(function(){el.addClass(animationEfx+' animated')},ctr*300)})},100)}})};(function ssInit(){ssPreloader();ssFitVids();ssMasonryFolio();ssLightGallery();ssFlexSlider();ssOffCanvas();ssSmoothScroll();ssPlaceholder();ssAlertBoxes();ssAnimations();ssIntroAnimation()})()})(jQuery)