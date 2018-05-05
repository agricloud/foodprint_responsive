$(document).ready(function() {

	// magnific pop up
	$('[data-toggle="lightbox-image"]').magnificPopup({
		  type: "image",
		  image: {
			  titleSrc: "title"
		  }
	  }), $('[data-toggle="lightbox-gallery"]').magnificPopup({
		  delegate: "a.gallery-link",
		  type: "image",
		  gallery: {
			  enabled: !0,
			  navigateByImgClick: !0,
			  arrowMarkup: '<button type="button" class="mfp-arrow mfp-arrow-%dir%" title="%title%"></button>',
			  tPrev: "Previous",
			  tNext: "Next",
			  tCounter: '<span class="mfp-counter">%curr% of %total%</span>'
		  },
		  image: {
			  titleSrc: "title"
		  }
	  })

	  // flexslider
	  $('.flexslider').flexslider({
			animation: "slide",
			controlNav: false
		  });
});